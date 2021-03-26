package edu.mermet.tp9.dialogs;

import edu.mermet.tp9.Application;
import edu.mermet.tp9.fenetres.*;
import edu.mermet.tp9.properties.PropertiesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Properties;

public class ConfigMenu extends JDialog
{
    private final JButton valider;
    private final JButton annuler;

    private final HashMap<JMenuItem, JRadioButton[]> hashMapRadioBtn;
    private final HashMap<JMenuItem, Boolean[]>      hashMapBaseValue;

    private PropertiesManager propertiesManager;

    public ConfigMenu(JMenuItem[] jMenuItems)
    {
        this.setTitle("Configuration des menus");

        this.propertiesManager = PropertiesManager.getInstance();

        JPanel panelCenter = new JPanel();
        JPanel panelBottom = new JPanel();

        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

        this.valider = new JButton("valider");
        this.annuler = new JButton("annuler");

        this.valider.addActionListener(event -> this.execValidScript());
        this.annuler.addActionListener(event -> this.reset());

        this.hashMapRadioBtn  = new HashMap<>();
        this.hashMapBaseValue = new HashMap<>();

        for (JMenuItem item : jMenuItems)
        {
            JRadioButton[] tab      = new JRadioButton[3];
            JPanel         panelTMP = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            ButtonGroup    group    = new ButtonGroup();

            panelTMP.add(new JLabel(item.getText()));

            tab[0] = new JRadioButton("Auto");
            tab[1] = new JRadioButton("Caché");
            tab[2] = new JRadioButton("Affiché");

            String defValue = this.propertiesManager.getPropertie(item.getText());

            if( defValue != null )
            {
                int val = Integer.parseInt(defValue);

                if( val >= 0 && val < tab.length )
                    tab[val].setSelected(true);
            }

            for (JRadioButton btn : tab)
            {
                group.add(btn);
                panelTMP.add(btn);
            }

            Boolean[] baseValue = new Boolean[tab.length];

            for (int i = 0; i < tab.length; i++)
                baseValue[i] = tab[i].isSelected();

            this.hashMapBaseValue.put(item, baseValue);
            this.hashMapRadioBtn .put(item, tab);

            panelCenter.add(panelTMP);
        }

        panelBottom.add(this.valider);
        panelBottom.add(this.annuler);

        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelBottom, BorderLayout.SOUTH);

        this.setModal(true);
        this.pack();
        this.setLocationRelativeTo(null);

        execValidScript();
    }

    private void reset()
    {
        for (JMenuItem item : this.hashMapBaseValue.keySet())
        {
            JRadioButton[] tab        = this.hashMapRadioBtn .get(item);
            Boolean[]      baseValues = this.hashMapBaseValue.get(item);

            for (int i = 0; i < tab.length; i++)
                tab[i].setSelected(baseValues[i]);
        }

        if( this.isVisible() )
            this.setVisible(false);
    }

    public void execValidScript()
    {
        float valCompetence = Float.parseFloat(this.propertiesManager.getPropertie("niveau"));

        for (JMenuItem item : this.hashMapRadioBtn.keySet())
        {
            JRadioButton[] tab = this.hashMapRadioBtn.get(item);

            int value = -1;

            if( tab[1].isSelected() )
            {
                value = 1;
                item.setVisible(false);
            }
            else if( tab[2].isSelected() )
            {
                value = 2;
                item.setVisible(true );
            }
            else if( tab[0].isSelected() )
            {
                value = 0;

                int complexiter = 0;

                switch (item.getText())
                {
                    case "Diaporama"                   : complexiter = FenetreDiaporama.COMPLEXITER;break;
                    case "Saisie de texte"             : complexiter = FenetreTexte.COMPLEXITER;break;
                    case "Conversion Celsius/Farenheit": complexiter = FenetreConversion.COMPLEXITER;break;
                    case "Boutons"                     : complexiter = FenetreBoutons.COMPLEXITER;break;
                }

                item.setVisible(valCompetence >= complexiter);
            }

            Boolean[] values = this.hashMapBaseValue.get(item);

            for (int i = 0; i < tab.length; i++)
                values[i] = tab[i].isSelected();

            this.propertiesManager.setPropertie(item.getText(), value);
        }

        if( this.isVisible() )
            this.setVisible(false);
    }
}
