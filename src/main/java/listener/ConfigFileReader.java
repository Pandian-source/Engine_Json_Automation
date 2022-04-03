package listener;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFileReader {

    public static Properties prop = new Properties();

    public ConfigFileReader() {
        try {
            FileInputStream ip = new FileInputStream(Location.ConfigFilePath);
            prop.load(ip);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String engineUrl() {
        String endpoint = prop.getProperty("ENDPOINT");
        if (endpoint != null)
            return endpoint;
        else
            throw new RuntimeException("ENDPOINT not specified in the Configuration.properties file.");
    }

    public String airShopping() {
        String airShopping = prop.getProperty("AirShopping");
        if (airShopping != null)
            return airShopping;
        else
            throw new RuntimeException("AirShopping not specified in the Configuration.properties file.");
    }

    public String airOfferPrice() {
        String airOfferPrice = prop.getProperty("AirOfferPrice");
        if (airOfferPrice != null)
            return airOfferPrice;
        else
            throw new RuntimeException("AirOfferPrice not specified in the Configuration.properties file.");
    }

    public String airOrderCreate() {
        String airOrderCreate = prop.getProperty("AirOrderCreate");
        if (airOrderCreate != null)
            return airOrderCreate;
        else
            throw new RuntimeException("AirOrderCreate not specified in the Configuration.properties file.");
    }
    public String getProperty(String key) {
        String value = prop.getProperty(key);
        if (value != null)
            return value;
        else
            throw new RuntimeException("key not specified in the Configuration.properties file.");
    }
}
