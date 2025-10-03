package be.kdg.keepdishgoing.common.events;

import java.time.LocalDateTime;

public record LogInEvent() implements DomainEvent {
    @Override
    public LocalDateTime eventPit() {
        return null;
    }
}
