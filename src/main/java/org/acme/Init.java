/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import io.quarkus.runtime.StartupEvent;
import java.io.InputStream;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author seseso
 */
@Singleton
public class Init {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Init.class);
//
//    static {
//        ((LoggerContext) LoggerFactory.getILoggerFactory()).stop();
//    }
    
    public void onStart(@Observes StartupEvent ev) {
        
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            
            JoranConfigurator jc = new JoranConfigurator();
            jc.setContext(lc);            
            lc.reset(); // override default configuration
            // inject the name of the current application as "application-name"
            // property of the LoggerContext
//            lc.putProperty("application-name", "c3-integration-cmd");
//            lc.putProperty("LOG_FILE_NAME", "c3-integration-cmd-" + df.format(new Date()) + ".log");
//            lc.putProperty("DEFAULT_LOG_FOLDER", config.getValue("Inbound.Temp.Folder", String.class));
            
            jc.doConfigure(getClass().getResourceAsStream("/logback.xml"));
            LOGGER.info("Logback was configured with sucess");
            
//            lc.stop();
//            StatusPrinter.print(lc);
        } catch(JoranException e) {
            LOGGER.warn(null, e);
            e.printStackTrace();
        } catch(ClassCastException e) {
            LOGGER.error("Could not configure Logback. Is is quarkus:dev fault? ERROR: {}", e.getMessage());
        }
        
        LOGGER.info("Initializing app...");
    }
}
