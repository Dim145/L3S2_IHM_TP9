package edu.mermet.tp9;


import java.awt.event.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import edu.mermet.tp9.dialogs.AideContextuel;
import edu.mermet.tp9.dialogs.CommentFaire;
import edu.mermet.tp9.dialogs.ConfigMenu;
import edu.mermet.tp9.fenetres.FenetreBoutons;
import edu.mermet.tp9.fenetres.FenetreConversion;
import edu.mermet.tp9.fenetres.FenetreDiaporama;
import edu.mermet.tp9.fenetres.FenetreTexte;
import edu.mermet.tp9.properties.PropertiesManager;

/**
 * @author brunomermet
 */
public class Application extends JFrame
{
    public static final int COMPETENCE_DIVISEUR = 10;

    private final ActionCommentFaire actionCommentFaire;
    private final ActionConfigMenu   actionConfigMenu;

    private final CommentFaire dialogCommentFaire;
    private final ConfigMenu   dialogConfigMenu;

    private final JInternalFrame conversion;
    private final JInternalFrame texte;
    private final JInternalFrame diaporama;
    private final JInternalFrame boutons;

    private final Action actionAfficherConversion;
    private final Action actionAfficherTexte;
    private final Action actionAfficherDiaporama;
    private final Action actionAfficherBoutons;

    private final AideContextuel aideContextuel;

    private float valNiveau;

    public Application()
    {
        super("multi-fenêtres");
        this.setContentPane(new JDesktopPane());

        // load properties
        String niveau = PropertiesManager.getInstance().getPropertie("niveau");

        if( niveau == null )
        {
            niveau = "1";
            PropertiesManager.getInstance().setPropertie("niveau", niveau);
        }

        this.valNiveau = Float.parseFloat(niveau);

        // ****** Barre de menu ******
        JMenuBar barre = new JMenuBar();
        // ------ menu Fichier ------
        JMenu menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F);
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(aev ->
        {
            if( this.valNiveau < 4 )
            {
                this.valNiveau += 1.0 / COMPETENCE_DIVISEUR;
                PropertiesManager.getInstance().setPropertie("niveau", String.valueOf(this.valNiveau));
            }

            this.saveAndExit();
        });
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        menuFichier.add(quitter);
        barre.add(menuFichier);
        this.setJMenuBar(barre);
        // ------ menu Applications ------
        JMenu menuApplication = new JMenu("Applications");
        menuApplication.setMnemonic(KeyEvent.VK_A);
        actionAfficherConversion = new ActionAfficherConversion();
        JMenuItem itemConversion = new JMenuItem(actionAfficherConversion);
        menuApplication.add(itemConversion);
        actionAfficherTexte = new ActionAfficherTexte();
        JMenuItem itemTexte = new JMenuItem(actionAfficherTexte);
        menuApplication.add(itemTexte);
        actionAfficherDiaporama = new ActionAfficherDiaporama();
        JMenuItem itemDiaporama = new JMenuItem(actionAfficherDiaporama);
        menuApplication.add(itemDiaporama);
        actionAfficherBoutons = new ActionAfficherBoutons();
        JMenuItem itemBoutons = new JMenuItem(actionAfficherBoutons);
        menuApplication.add(itemBoutons);
        barre.add(menuApplication);

        //--------- menu Aide -----------------
        JMenu menuAide = new JMenu("Aide");

        this.actionCommentFaire = new ActionCommentFaire();
        JMenuItem commentFaire = new JMenuItem(actionCommentFaire);
        menuAide.add(commentFaire);

        this.actionConfigMenu = new ActionConfigMenu();
        JMenuItem configMenu = new JMenuItem(actionConfigMenu);
        menuAide.add(configMenu);

        barre.add(menuAide);
        // ****** Fin barre de menu ******

        // ****** Création des fenêtres ******
        // ------ fenêtre conversion ------
        conversion = new FenetreConversion(this, actionAfficherConversion);
        this.add(conversion);
        // ------ fenêtre texte ------
        texte = new FenetreTexte(actionAfficherTexte);
        this.add(texte);
        // ------ fenêtre diaporama ------
        diaporama = new FenetreDiaporama(actionAfficherDiaporama);
        this.add(diaporama);
        // ------ fenêtre boutons ------
        boutons = new FenetreBoutons(this, actionAfficherBoutons);
        this.add(boutons);

        //------- Dialog comment faire --------------
        this.dialogCommentFaire = new CommentFaire();

        //------- Dialog config menu ----------------
        JMenuItem[] tabMenuItem = new JMenuItem[]{itemConversion, itemDiaporama, itemBoutons, itemTexte};

        this.dialogConfigMenu = new ConfigMenu(tabMenuItem);

        //********* Aide contextuel ***********
        this.aideContextuel = new AideContextuel(this);

        // ****** Fin création fenêtres ******

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                saveAndExit();
            }
        });

        setSize(600, 300);
        this.setLocationRelativeTo(null);
        setVisible(true);

        if( aideContextuel.isEnabled() )
            aideContextuel.setVisible(true);
    }

    private void saveAndExit()
    {
        PropertiesManager.getInstance().savePropertiesToXML();
        System.exit(0);
    }

    private class ActionConfigMenu extends AbstractAction
    {
        public ActionConfigMenu()
        {
            super("Configuration des menus");

            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            dialogConfigMenu.setVisible(true);
        }
    }

    private void augmenterCompetence( float competence )
    {
        this.valNiveau += competence;

        PropertiesManager.getInstance().setPropertie("niveau", String.valueOf(this.valNiveau));
    }

    private class ActionCommentFaire extends AbstractAction
    {
        public ActionCommentFaire()
        {
            super("Comment faire ?");

            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            dialogCommentFaire.setVisible(true);
        }
    }

    private class ActionAfficherBoutons extends AbstractAction
    {
        public ActionAfficherBoutons()
        {
            super("Boutons");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            boutons.setVisible(true);
            enableBoutons(false);

            if( valNiveau < FenetreBoutons.COMPLEXITER*4 )
            {
                augmenterCompetence(FenetreBoutons.COMPLEXITER / (COMPETENCE_DIVISEUR * 1f));

                dialogConfigMenu.execValidScript();
            }
        }
    }

    private class ActionAfficherDiaporama extends AbstractAction
    {
        public ActionAfficherDiaporama()
        {
            super("Diaporama");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            diaporama.setVisible(true);
            enableDiaporama(false);

            if( valNiveau < FenetreDiaporama.COMPLEXITER*4 )
            {
                augmenterCompetence(FenetreDiaporama.COMPLEXITER / (COMPETENCE_DIVISEUR * 1f));

                dialogConfigMenu.execValidScript();
            }
        }
    }

    private class ActionAfficherTexte extends AbstractAction
    {
        public ActionAfficherTexte()
        {
            super("Saisie de texte");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            texte.setVisible(true);
            enableTexte(false);

            if( valNiveau < FenetreTexte.COMPLEXITER*4 )
            {
                augmenterCompetence(FenetreTexte.COMPLEXITER / (COMPETENCE_DIVISEUR * 1f));

                dialogConfigMenu.execValidScript();
            }
        }
    }

    public class ActionAfficherConversion extends AbstractAction
    {
        public ActionAfficherConversion()
        {
            super("Conversion Celsius/Farenheit");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            conversion.setVisible(true);
            enableConversion(false);

            if( valNiveau < FenetreConversion.COMPLEXITER*4 )
            {
                augmenterCompetence(FenetreConversion.COMPLEXITER / (COMPETENCE_DIVISEUR * 1f));

                dialogConfigMenu.execValidScript();
            }
        }
    }

    public void enableConversion(boolean b)
    {
        actionAfficherConversion.setEnabled(b);
    }

    public void enableTexte(boolean b)
    {
        actionAfficherTexte.setEnabled(b);
    }

    public void enableDiaporama(boolean b)
    {
        actionAfficherDiaporama.setEnabled(b);
    }

    public void enableBoutons(boolean b)
    {
        actionAfficherBoutons.setEnabled(b);
    }

    public Action getActionAfficherConversion()
    {
        return actionAfficherConversion;
    }

    public Action getActionAfficherTexte()
    {
        return actionAfficherTexte;
    }

    public Action getActionAfficherDiaporama()
    {
        return actionAfficherDiaporama;
    }

    public static void main(String[] args)
    {
        PropertiesManager.initInstanceWithName(args.length > 0 ? args[0] : System.getProperty("user.name"));

        SwingUtilities.invokeLater(Application::new);
    }


}
