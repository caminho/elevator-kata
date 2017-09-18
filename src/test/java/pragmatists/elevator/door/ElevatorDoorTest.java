package pragmatists.elevator.door;

import org.junit.Before;
import org.junit.Test;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.DoorClosedEvent;
import pragmatists.elevator.event.DoorOpenEvent;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorDoorTest {

    private final EventLogger eventLogger = mock(EventLogger.class);
    private final DoorListener doorListener = mock(DoorListener.class);

    private final Door door = new ElevatorDoor(eventLogger);

    @Before
    public void setUp() {
        door.setListener(doorListener);
    }

    @Test
    public void shouldLogWhenDoorOpened() {

        door.open();

        verify(eventLogger).logEvent(isA(DoorOpenEvent.class));
    }

    @Test
    public void shouldLogWhenDoorClosed() {

        door.close();

        verify(eventLogger).logEvent(isA(DoorClosedEvent.class));
    }

    @Test
    public void shouldNotifyListenerWhenDoorClosed() {

        door.close();

        verify(doorListener).doorClosed();
    }
}
