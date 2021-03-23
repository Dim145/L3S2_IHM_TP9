package edu.mermet.tp9.fenetres;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author brunomermet
 */
public class FenetreConversion extends AbstractFenetreInterne
{
    private JTextField champCelsius;
    private JTextField champFarenheit;
    private JButton boutonConvertir;
    private Action actionConvertir;
    private boolean celsiusAFocus;

    public FenetreConversion(JFrame frame, Action action)
    {
        super(action, "Conversion celsius/Farenheit");
        this.setSize(new Dimension(100, 50));
        this.setLayout(new GridLayout(3, 1));

        JPanel ligneCelsius = new JPanel();
        ligneCelsius.setLayout(new FlowLayout(FlowLayout.TRAILING));
        JLabel labCelsius = new JLabel("Celsius :");
        champCelsius      = new JTextField(15);

        champCelsius.addMouseListener(new MousePopUpMenu(frame, "aide Celcius"));
        champCelsius.setToolTipText("aide Celcius");

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/aide.png"));
        icon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        JButton labelAideCelcius = new JButton(icon);

        labelAideCelcius.setBorder(BorderFactory.createEmptyBorder());
        labelAideCelcius.setContentAreaFilled(false);
        labelAideCelcius.setPreferredSize(new Dimension(30, 30));

        labelAideCelcius.addActionListener(event -> JOptionPane.showInternalMessageDialog(frame.getRootPane(), "aide celcius", "Aide", JOptionPane.INFORMATION_MESSAGE));

        labCelsius.setLabelFor(champCelsius);
        ligneCelsius.add(labCelsius);
        ligneCelsius.add(champCelsius);
        ligneCelsius.add(labelAideCelcius);
        this.add(ligneCelsius);
        celsiusAFocus = true;
        champCelsius.addFocusListener(new EcouteurFocus(true));

        JPanel ligneFarenheit = new JPanel();
        ligneFarenheit.setLayout(new FlowLayout(FlowLayout.TRAILING));
        JLabel labFarenheit = new JLabel("Farenheit :");
        champFarenheit = new JTextField(15);

        champFarenheit.setToolTipText("aide Farenheit");

        JButton labelAideFaren = new JButton(icon);

        labelAideFaren.setBorder(BorderFactory.createEmptyBorder());
        labelAideFaren.setContentAreaFilled(false);
        labelAideFaren.setPreferredSize(new Dimension(30, 30));

        labelAideFaren.addActionListener(event -> JOptionPane.showInternalMessageDialog(frame.getRootPane(), "aide Faren", "Aide", JOptionPane.INFORMATION_MESSAGE));
        champFarenheit.addMouseListener(new MousePopUpMenu(frame, "aide Farenjeit"));

        labFarenheit.setLabelFor(champFarenheit);
        ligneFarenheit.add(labFarenheit);
        ligneFarenheit.add(champFarenheit);
        ligneFarenheit.add(labelAideFaren);
        this.add(ligneFarenheit);
        champFarenheit.addFocusListener(new EcouteurFocus(false));

        JPanel ligneValider = new JPanel();
        ligneValider.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionConvertir = new ActionConvertir();
        boutonConvertir = new JButton(actionConvertir);
        ligneValider.add(boutonConvertir);
        this.add(ligneValider);

        pack();
        getRootPane().setDefaultButton(boutonConvertir);
    }

    private class EcouteurFocus extends FocusAdapter
    {
        private boolean aStocker;

        public EcouteurFocus(boolean b)
        {
            aStocker = b;
        }

        @Override
        public void focusGained(FocusEvent fe)
        {
            celsiusAFocus = aStocker;
        }
    }

    private class ActionConvertir extends AbstractAction
    {

        public ActionConvertir()
        {
            super("Convertir");
        }

        @Override
        public void actionPerformed(ActionEvent ae)
        {
            double tempCelsius = 0;
            double tempFarenheit = 0;
            if (celsiusAFocus)
            {
                try
                {
                    tempCelsius = Double.parseDouble(champCelsius.getText());
                    tempFarenheit = 9. / 5 * tempCelsius + 32;
                    champFarenheit.setText("" + tempFarenheit);
                }
                catch (NumberFormatException nfe)
                {
                    champFarenheit.setText("Format incorrect");
                }
            }
            else
            {
                try
                {
                    tempFarenheit = Double.parseDouble(champFarenheit.getText());
                    tempCelsius = (tempFarenheit - 32) * 5. / 9;
                    champCelsius.setText("" + tempCelsius);
                }
                catch (NumberFormatException nfe)
                {
                    champCelsius.setText("Format incorrect");
                }

            }
        }
    }

    private static class MousePopUpMenu extends MouseAdapter
    {
        private String texteAide;
        private JFrame frame;

        public MousePopUpMenu( JFrame frame, String texteAide )
        {
            this.frame     = frame;
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

                aide.addActionListener(event -> JOptionPane.showInternalMessageDialog(this.frame.getRootPane(), this.texteAide, "Aide", JOptionPane.INFORMATION_MESSAGE));

                menu.show((Component) e.getSource(), e.getX(), e.getY());
            }
            else
            {
                super.mouseClicked(e);
            }
        }
    }

}
