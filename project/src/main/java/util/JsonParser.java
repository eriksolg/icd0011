package util;

import java.util.HashMap;

public class JsonParser {

    public static HashMap<String, String> parseJson(String json) {
        HashMap<String, String> dataContainer = new HashMap<String, String>();

        json = json.replace('{', ' ').replace('}', ' ').trim();
        for (String property : json.split(",")) {
            String propertyName = property.split(":")[0].replace('"', ' ').trim();
            String propertyValue = property.split(":")[1].replace('"', ' ').trim();
            dataContainer.put(propertyName, propertyValue);
        }
        return dataContainer;
    }

}
