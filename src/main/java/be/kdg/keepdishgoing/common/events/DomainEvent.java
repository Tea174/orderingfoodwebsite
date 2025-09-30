package be.kdg.keepdishgoing.common.events;

import java.time.LocalDateTime;

public interface DomainEvent {

    LocalDateTime eventPit();

}
