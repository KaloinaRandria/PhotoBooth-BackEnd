package org.photobooth.restapi.util;

import org.springframework.context.ApplicationContext;

public abstract class AppProperties {
    public static String getSavedFileLocation(ApplicationContext applicationContext) {
        return applicationContext.getEnvironment().getProperty("spring.servlet.multipart.location");
    }
}
