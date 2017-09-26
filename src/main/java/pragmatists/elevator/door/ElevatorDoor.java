package pragmatists.elevator.door;

import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.DoorClosedEvent;
import pragmatists.elevator.event.DoorOpenEvent;

public class ElevatorDoor implements Door {

    private final EventLogger eventLogger;
    private DoorListener doorListener;

    public ElevatorDoor(EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    @Override
    public void close() {
        eventLogger.logEvent(new DoorClosedEvent());
        if (doorListener != null) {
            doorListener.doorStateChanged(DoorState.CLOSED);
        }
    }

    @Override
    public void open() {
        eventLogger.logEvent(new DoorOpenEvent());
        if (doorListener != null) {
            doorListener.doorStateChanged(DoorState.OPENED);
        }
    }

    @Override
    public void setListener(DoorListener doorListener) {
        this.doorListener = doorListener;
    }
}
