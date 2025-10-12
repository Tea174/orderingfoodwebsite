package be.kdg.keepdishgoing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@Modulith
//@EnableScheduling
@SpringBootApplication
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
