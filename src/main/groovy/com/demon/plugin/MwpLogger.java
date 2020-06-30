package com.demon.plugin;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

public class MwpLogger {

    private static Logger logger;

    public MwpLogger(Project project) {
        logger = project.getLogger();
    }


    public static void error(String s) {
        if (logger != null)
            logger.error("error:" + s);
    }


    public static void i(String s) {
        if (logger != null)
            logger.error("info:" + s);
    }
}
