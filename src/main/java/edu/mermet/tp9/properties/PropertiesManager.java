package edu.mermet.tp9.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager
{
    private static PropertiesManager instance;

    public static void initInstanceWithName(String name)
    {
        if( instance == null ) instance = new PropertiesManager(name);
    }

    public static PropertiesManager getInstance()
    {
        if( instance == null ) initInstanceWithName(System.getProperty("user.name"));

        return instance;
    }

    private final Properties properties;
    private final File       propertiesFile;

    public PropertiesManager(String name)
    {
        File ihmRep         = new File(System.getProperty("user.home") + "/.ihm");
        this.propertiesFile = new File(ihmRep.getPath() + "/" + name + ".xml");

        this.properties = new Properties();
        try
        {
            boolean isExist = ihmRep.exists();

            if( !isExist )
                isExist = ihmRep.mkdir();

            if( !isExist )
                throw new IOException("file .ihm not created");

            isExist = propertiesFile.exists();

            if( isExist ) properties.loadFromXML(new FileInputStream(propertiesFile));
            else          isExist = propertiesFile.createNewFile();

            if( !isExist )
            {
                throw new IOException("fichier inexistant et impossible a creer: " + propertiesFile.getPath());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getPropertie( String propertieName )
    {
        return this.properties.getProperty(propertieName, null);
    }

    public void setPropertie( String propertieName, String value )
    {
        this.properties.setProperty(propertieName, value);
    }

    public void setPropertie( String propertieName, int value )
    {
        this.setPropertie(propertieName, String.valueOf(value));
    }

    public void savePropertiesToXML()
    {
        try
        {
            this.properties.storeToXML(new FileOutputStream(this.propertiesFile), "saved");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
