package edu.mermet.tp9.dialogs;

import edu.mermet.tp9.properties.PropertiesManager;
import edu.mermet.tp9.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AideContextuel extends JWindow
{
    private JButton fermer;
    private JButton nePlusAfficher;

    public AideContextuel( JFrame frame )
    {
        super(frame);

        fermer = new JButton("fermer");
        fermer.addActionListener(event -> this.setVisible(false));

        JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        this.add(panelSouth, BorderLayout.SOUTH);

        String   banValues   = PropertiesManager.getInstance().getPropertie("BanValues");

        banValues = banValues == null ? "" : banValues;

        ArrayList<Integer> listBanValue = new ArrayList<>();

        if( banValues.startsWith(",") )
            banValues = banValues.substring(1);

        if( !banValues.isEmpty() )
        {
            int fin = banValues.endsWith(",") ? banValues.lastIndexOf(",") : banValues.length();

            String[] tabBanValue = banValues.substring(0, fin == -1 ? banValues.length() : fin).split(",");
            Arrays.stream(tabBanValue).mapToInt(Integer::parseInt).forEach(listBanValue::add);
        }

        int nbAideDisponible = ResourceManager.getInstance().getNbTexteAideContextuel();

        //

        if( nbAideDisponible == listBanValue.size() )
        {
            this.setEnabled(false);
            return;
        }

        ArrayList<Integer> listBanCompetence = new ArrayList<>();

        float valCompetence = Float.parseFloat(PropertiesManager.getInstance().getPropertie("niveau"));

        for (int i = 0; i < nbAideDisponible; i++)
        {
            String res = ResourceManager.getInstance().getTexteAideContextuel(i);
            int val = Integer.parseInt(res.substring(res.lastIndexOf("-")+1).trim());

            if( val > valCompetence && !listBanValue.contains(i) )
                listBanCompetence.add(i);
        }

        if( listBanValue.size() + listBanCompetence.size() >= nbAideDisponible )
        {
            this.setEnabled(false);
            return;
        }

        int randomAide;
        do
        {
            randomAide = (int) (Math.random()*(nbAideDisponible));
        }
        while (listBanValue.contains(randomAide) || listBanCompetence.contains(randomAide));

        nePlusAfficher = new JButton("Ne Plus Afficher");

        String texteToAdd = banValues + (banValues.isEmpty() || banValues.endsWith(",") ? "" : ",") + randomAide + ",";
        nePlusAfficher.addActionListener(event ->
        {
            PropertiesManager.getInstance().setPropertie("BanValues", texteToAdd);
            fermer.doClick();
        });

        String res = ResourceManager.getInstance().getTexteAideContextuel(randomAide);

        JLabel label = new JLabel(res.substring(0, res.lastIndexOf("-")));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);

        panelSouth.add(this.fermer);
        panelSouth.add(this.nePlusAfficher);

        this.add(label, BorderLayout.CENTER);

        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setSize(500, 150);
        this.setLocationRelativeTo(frame);
    }
}
