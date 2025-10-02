package be.kdg.keepdishgoing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.Modulith;
//import org.springframework.modulith.cor;
import org.springframework.scheduling.annotation.EnableScheduling;


@Modulith
@EnableScheduling
public class KeepdishgoingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeepdishgoingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KeepdishgoingApplication.class, args);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    void onApplicationStarted() {
//        ApplicationModules modules = ApplicationModules.of(KeepdishgoingApplication.class);
//        modules.forEach(module -> LOGGER.info("\n{}", module));
//    }

}
