package edu.mermet.tp9.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ConfigMenu extends JDialog
{
    private final JButton valider;
    private final JButton annuler;

    private final HashMap<JMenuItem, JRadioButton[]> hashMapRadioBtn;
    private final HashMap<JMenuItem, Boolean[]>      hashMapBaseValue;

    public ConfigMenu(JMenuItem[] jMenuItems)
    {
        this.setTitle("Configuration des menus");

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

            tab[0].setSelected(true);// todo lecture des preferences

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

    private void execValidScript()
    {
        for (JMenuItem item : this.hashMapRadioBtn.keySet())
        {
            JRadioButton[] tab = this.hashMapRadioBtn.get(item);

            if( tab[1].isSelected() ) item.setVisible(false);
            if( tab[2].isSelected() ) item.setVisible(true );
            if( tab[0].isSelected() )
            {
                // Auto
            }

            Boolean[] values = this.hashMapBaseValue.get(item);

            for (int i = 0; i < tab.length; i++)
                values[i] = tab[i].isSelected();
        }

        if( this.isVisible() )
            this.setVisible(false);
    }
}
