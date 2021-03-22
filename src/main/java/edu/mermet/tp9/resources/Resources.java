package edu.mermet.tp9.resources;

public enum Resources
{
    TITRE_CONV_C_TO_F("con_c_f"),
    TITRE_CONV_F_TO_C("con_f_c"),
    TITRE_GRAS       ("gras"),
    TITRE_COULEUR    ("couleur"),

    TITRE_CONVERTION("convertion"),
    TITRE_SAISIETEXT("saisie_text"),
    TITRE_GUIDE     ("guide"),
    TITRE_QUITTER   ("quitter"),

    HTML_GRAS("html_bold"),
    HTML_COUL("html_color"),
    HTML_C_TO_F("html_cf"),
    HTML_F_TO_C("html_fc");

    private final String resourceKey;

    Resources(String resourceKey )
    {
        this.resourceKey = resourceKey;
    }

    public String getKey()
    {
        return this.resourceKey;
    }

    @Override
    public String toString()
    {
        return this.getKey();
    }
}
