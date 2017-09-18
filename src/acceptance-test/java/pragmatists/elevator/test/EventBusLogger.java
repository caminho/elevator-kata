package pragmatists.elevator.test;

import com.google.common.eventbus.EventBus;
import pragmatists.elevator.EventLogger;

public class EventBusLogger implements EventLogger {
    private final EventBus eventBus;

    EventBusLogger(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void logEvent(Object event) {
        eventBus.post(event);
    }
}
