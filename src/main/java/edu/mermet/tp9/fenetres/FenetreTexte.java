package edu.mermet.tp9.fenetres;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author brunomermet
 */
public class FenetreTexte extends AbstractFenetreInterne
{
    private JCheckBox gras;
    private JCheckBox rouge;
    private Action actionGras;
    private Action actionRouge;
    private JTextArea texte;

    public FenetreTexte(Action action)
    {
        super(action, "Texte");
        actionGras = new ActionGras();
        gras = new JCheckBox(actionGras);
        actionRouge = new ActionRouge();
        rouge = new JCheckBox(actionRouge);
        JPanel panneauBouton = new JPanel();
        panneauBouton.add(gras);
        panneauBouton.add(rouge);
        add(panneauBouton, BorderLayout.NORTH);
        texte = new JTextArea(6, 20);
        texte.setLineWrap(true);
        texte.setWrapStyleWord(true);
        JScrollPane panneauTexte = new JScrollPane(texte);
        add(panneauTexte, BorderLayout.CENTER);
        JMenuBar barre = new JMenuBar();
        JMenu style = new JMenu("Style");
        style.add(new JMenuItem(actionGras));
        style.add(new JCheckBoxMenuItem(actionRouge));
        barre.add(style);
        this.setJMenuBar(barre);
        pack();

        gras .addMouseListener(new MousePopUpMenu("Met le texte en gras"));
        rouge.addMouseListener(new MousePopUpMenu("Met le texte en rouge"));
        texte.addMouseListener(new MousePopUpMenu("Zone d'edition de texte. Du texte peut etre saisie"));
    }


    private class ActionGras extends AbstractAction
    {
        private boolean gras;

        public ActionGras()
        {
            super("gras");
            gras = false;
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.SELECTED_KEY, false);
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            Font police = texte.getFont();
            if (!gras)
            {
                police = police.deriveFont(Font.BOLD);//|Font.ITALIC);
                //police = police.deriveFont((float)24.0);
            }
            else
            {
                police = police.deriveFont(Font.PLAIN);
            }
            gras = !gras;
            putValue(Action.SELECTED_KEY, gras);
            texte.setFont(police);
        }
    }

    private class ActionRouge extends AbstractAction
    {
        private boolean rouge;

        public ActionRouge()
        {
            super("rouge");
            rouge = false;
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
            putValue(Action.SELECTED_KEY, false);

        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            if (!rouge)
            {
                texte.setForeground(Color.RED);
            }
            else
            {
                texte.setForeground(Color.BLACK);
            }
            rouge = !rouge;
            //putValue(Action.SELECTED_KEY,rouge);
        }
    }

    private static class MousePopUpMenu extends MouseAdapter
    {
        private final String texteAide;

        public MousePopUpMenu( String texteAide )
        {
            this.texteAide = texteAide;
        }

        @Override
        public void mouseClicked(MouseEvent e)
        {
            if( SwingUtilities.isRightMouseButton(e))
            {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem aide = new JMenuItem("Aide");

                menu.add(aide);

                aide.addActionListener(event -> JOptionPane.showMessageDialog(null, this.texteAide, "Aide", JOptionPane.INFORMATION_MESSAGE));

                menu.show((Component) e.getSource(), e.getX(), e.getY());
            }
            else
            {
                super.mouseClicked(e);
            }
        }
    }
}
