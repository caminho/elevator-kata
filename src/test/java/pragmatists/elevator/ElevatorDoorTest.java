package pragmatists.elevator;

import org.junit.Test;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.door.ElevatorDoor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorDoorTest {

    private final DoorListener listener = mock(DoorListener.class);
    private final Door door = new ElevatorDoor(listener);

    @Test
    public void shouldNotifyClosed() {

        door.close();

        verify(listener).doorClosed();
    }
}
