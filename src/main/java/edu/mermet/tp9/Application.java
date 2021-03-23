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

    public Application()
    {
        super("multi-fenêtres");
        this.setContentPane(new JDesktopPane());

        // ****** Barre de menu ******
        JMenuBar barre = new JMenuBar();
        // ------ menu Fichier ------
        JMenu menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F);
        JMenuItem quitter = new JMenuItem("Quitter");
        quitter.addActionListener(aev -> this.saveAndExit());
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
        conversion = new FenetreConversion(actionAfficherConversion);
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
