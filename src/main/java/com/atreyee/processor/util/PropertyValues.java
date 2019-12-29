package com.atreyee.processor.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValues {
    private static Properties prop;

    private PropertyValues(){}

    public static void loadProperties(String propertyFileName) throws IOException {

        try (InputStream input = PropertyValues.class.getClassLoader().getResourceAsStream(propertyFileName)) {

            if (input == null) {
                throw new FileNotFoundException(ApplicationMessage.PROPERTIES_FILE_NOT_FOUND_MESSAGE+propertyFileName);
            }
            prop = new Properties();
            prop.load(input);

        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public static String getPropertyValue(String property) {
        return prop.getProperty(property);
    }
}
