package edu.mermet.tp9.dialogs;

import edu.mermet.tp9.resources.ResourceManager;
import edu.mermet.tp9.resources.Resources;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class CommentFaire extends JDialog
{
    private final static ResourceManager MANAGER = ResourceManager.getInstance();

    private final JSplitPane splitPane;

    private JTree tree;
    private final JLabel label;

    public CommentFaire()
    {
        this.setTitle("Comment Faire ?");

        this.splitPane = new JSplitPane();

        this.label = new JLabel();

        this.label.setFont(this.label.getFont().deriveFont(Font.PLAIN));

        this.configureJTree();

        this.splitPane.add(this.tree , JSplitPane.LEFT);
        this.splitPane.add(this.label, JSplitPane.RIGHT);

        this.label.setVerticalAlignment(JLabel.TOP);

        this.add(this.splitPane, BorderLayout.CENTER);

        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
    }

    private void configureJTree()
    {
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("racine");

        DefaultMutableTreeNode guide  = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_GUIDE));
        DefaultMutableTreeNode quit   = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_QUITTER));

        DefaultMutableTreeNode sousMenuConv        = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_CONVERTION));
        DefaultMutableTreeNode sousMenuSaisieTexte = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_SAISIETEXT));

        DefaultMutableTreeNode convCF = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_CONV_C_TO_F));
        DefaultMutableTreeNode convFC = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_CONV_F_TO_C));
        DefaultMutableTreeNode gras   = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_GRAS));
        DefaultMutableTreeNode coul   = new DefaultMutableTreeNode(MANAGER.getTitreString(Resources.TITRE_COULEUR));

        sousMenuConv.add(convCF);
        sousMenuConv.add(convFC);

        sousMenuSaisieTexte.add(gras);
        sousMenuSaisieTexte.add(coul);

        guide.add(sousMenuConv);
        guide.add(sousMenuSaisieTexte);

        racine.add(guide);
        racine.add(quit);

        this.tree = new JTree(racine);
        this.tree.setRootVisible(false);
        this.tree.expandRow(0);
        this.tree.setPreferredSize(new Dimension(200, 500));

        this.tree.addTreeSelectionListener(event ->
        {
            Object selected = this.tree.getLastSelectedPathComponent();

            if     ( selected == convCF ) this.setLabelHTML(Resources.HTML_C_TO_F);
            else if( selected == convFC ) this.setLabelHTML(Resources.HTML_F_TO_C);
            else if( selected == gras   ) this.setLabelHTML(Resources.HTML_GRAS);
            else if( selected == coul   ) this.setLabelHTML(Resources.HTML_COUL);
            else if( selected == quit   ) this.setVisible(false);
        });
    }

    private void setLabelHTML(Resources res)
    {
        String str = "<html><head><style>body{margin: 10px;}</style></head><body>";

        str += MANAGER.getHTMLHowTOText(res) + "</body></html>";

        this.label.setText(str);
    }
}