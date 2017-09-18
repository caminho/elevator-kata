package pragmatists.elevator;

import pragmatists.elevator.event.DoorOpenEvent;

public class Elevator {

    private final EventLogger logger;

    public Elevator(EventLogger eventLogger) {
        this.logger = eventLogger;
    }

    public void run() {
        openDoor();
    }

    private void openDoor() {
        logger.logEvent(new DoorOpenEvent());
    }
}
