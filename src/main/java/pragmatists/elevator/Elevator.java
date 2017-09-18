package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.event.DoorOpenEvent;
import pragmatists.elevator.panel.ButtonListener;

public class Elevator implements ButtonListener {

    private final EventLogger logger;
    private final Door door;

    public Elevator(EventLogger eventLogger, Door door) {
        this.logger = eventLogger;
        this.door = door;
    }

    public void run() {
        openDoor();
    }

    private void openDoor() {
        logger.logEvent(new DoorOpenEvent());
    }

    @Override
    public void floorRequested(Floor floor) {
        door.close();
    }
}
