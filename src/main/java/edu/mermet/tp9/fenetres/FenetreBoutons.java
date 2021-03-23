package edu.mermet.tp9.fenetres;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import edu.mermet.tp9.Application;

/**
 * @author brunomermet
 */
public class FenetreBoutons extends AbstractFenetreInterne
{
    private JButton boutonTexte;
    private JButton boutonDiaporama;
    private JButton boutonDegres;

    public FenetreBoutons(Application appli, Action action)
    {
        super(action, "Boutons");
        setLayout(new FlowLayout());
        boutonTexte = new JButton(appli.getActionAfficherTexte());
        boutonDiaporama = new JButton(appli.getActionAfficherDiaporama());
        boutonDegres = new JButton(appli.getActionAfficherConversion());
        add(boutonDegres);
        add(boutonTexte);
        add(boutonDiaporama);
        pack();

        boutonTexte    .addMouseListener(new MousePopUpMenu("affiche une saisie de texte"));
        boutonDiaporama.addMouseListener(new MousePopUpMenu("affiche un Diaporama"));
        boutonDegres   .addMouseListener(new MousePopUpMenu("affiche une fenetre de convertion"));
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
