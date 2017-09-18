package pragmatists.elevator;

import org.junit.Test;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.event.DoorOpenEvent;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorTest {

    private static final int ANY_FLOOR_LEVEL = 5;
    private final EventLogger eventLogger = mock(EventLogger.class);
    private final Door door = mock(Door.class);
    private final Elevator elevator = new Elevator(eventLogger, door);

    @Test
    public void shouldOpenDoorAfterStart() {

        elevator.run();

        verify(eventLogger).logEvent(isA(DoorOpenEvent.class));
    }

    @Test
    public void shouldCloseDoorWhenFloorRequested() {

        elevator.floorRequested(Floor.of(ANY_FLOOR_LEVEL));

        verify(door).close();
    }
}
