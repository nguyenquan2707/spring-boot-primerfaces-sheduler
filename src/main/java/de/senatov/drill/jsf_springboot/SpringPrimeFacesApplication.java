package de.senatov.drill.jsf_springboot;



import de.senatov.drill.jsf_springboot.annotations.Loggable;
import de.senatov.drill.jsf_springboot.util.BrowserUtl;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import java.io.IOException;
import java.util.Optional;



@SpringBootApplication
public class SpringPrimeFacesApplication {

    public static final String MSG = "Application started ... launching browser now";

    @Loggable
    private Logger log;



    /**
     * start point of App.
     *
     * @param args
     */
    public static void main(String... args) {

        SpringApplication.run(SpringPrimeFacesApplication.class, args);
    }



    /**
     * start point of browser.
     */
    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {

        this.log.info(MSG);
        try {
            Optional<MutablePropertySources> oPropSrc = Optional.of(event).map(ApplicationReadyEvent::getApplicationContext)
                                                                .map(ConfigurableApplicationContext::getEnvironment)
                                                                .map(ConfigurableEnvironment::getPropertySources);
            if (oPropSrc.isPresent()) {
                new BrowserUtl().start(oPropSrc.get());
            }
        }
        catch (IOException e) {
            log.error("onApplicationReadyEvent", e);
        }
    }

}



