package pragmatists.elevator;

import org.junit.Test;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.door.Door;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorTest {

    private static final int ANY_FLOOR_LEVEL = 5;

    private final Door door = mock(Door.class);
    private final Engine engine = mock(Engine.class);

    @Test
    public void shouldRegisterItselfAsDoorListener() {

        Elevator elevator = new Elevator(door, engine);

        verify(door).setListener(elevator);
    }

    @Test
    public void shouldOpenDoorAfterStart() {

        Elevator elevator = new Elevator(door, engine);

        elevator.run();

        verify(door).open();
    }

    @Test
    public void shouldCloseDoorWhenFloorRequested() {

        Elevator elevator = new Elevator(door, engine);

        elevator.floorRequested(Floor.of(ANY_FLOOR_LEVEL));

        verify(door).close();
    }

    @Test
    public void shouldStartEngineWhenFloorRequestedAndDoorClosed() {

        Elevator elevator = new Elevator(door, engine);
        elevator.floorRequested(Floor.of(ANY_FLOOR_LEVEL));

        elevator.doorClosed();

        verify(engine).start(any(Direction.class));
    }
}
