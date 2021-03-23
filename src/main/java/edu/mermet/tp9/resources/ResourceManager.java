package edu.mermet.tp9.resources;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager
{
    private static ResourceManager instance;

    public static ResourceManager getInstance()
    {
        if( instance == null ) instance = new ResourceManager();

        return instance;
    }

    private Locale locale;
    private ResourceBundle texteBundle;
    private ResourceBundle titreBundle;
    private ResourceBundle aideContextuelBundle;

    public ResourceManager()
    {
        this.setLocale(Locale.getDefault());
    }

    public void setLocale( Locale locale )
    {
        if( locale == null ) return;

        this.locale               = locale;
        this.texteBundle          = ResourceBundle.getBundle("howTo.textes", locale);
        this.titreBundle          = ResourceBundle.getBundle("howTo.titre" , locale);
        this.aideContextuelBundle = ResourceBundle.getBundle("aideContextuel.texteAide", locale);
    }

    public Locale getLocale()
    {
        return this.locale;
    }

    public String getTitreString(Resources res)
    {
        return this.titreBundle.getString(res.getKey());
    }

    public String getTexteAideContextuel( int val )
    {
        return this.aideContextuelBundle.getString(String.valueOf(val));
    }

    public int getNbTexteAideContextuel()
    {
        return this.aideContextuelBundle.keySet().size();
    }

    public String getHTMLHowTOText(Resources res)
    {
        return this.texteBundle.getString(res.getKey());
    }
}
