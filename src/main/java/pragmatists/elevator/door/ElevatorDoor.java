package pragmatists.elevator.door;

import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.DoorClosedEvent;

public class ElevatorDoor implements Door {

    private final EventLogger eventLogger;

    public ElevatorDoor(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    @Override
    public void close() {
        eventLogger.logEvent(new DoorClosedEvent());
    }
}
