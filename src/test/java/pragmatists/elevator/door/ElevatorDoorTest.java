package pragmatists.elevator.door;

import org.junit.Test;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.DoorClosedEvent;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorDoorTest {

    @Test
    public void shouldNotifyAboutDoorClosed() {

        EventLogger eventLogger = mock(EventLogger.class);
        Door door = new ElevatorDoor(eventLogger);

        door.close();

        verify(eventLogger).logEvent(isA(DoorClosedEvent.class));
    }
}
