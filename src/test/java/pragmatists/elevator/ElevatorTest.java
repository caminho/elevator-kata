package pragmatists.elevator;

import org.junit.Test;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.door.Door;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ElevatorTest {

    private static final int ANY_FLOOR_LEVEL = 5;

    private final Door door = mock(Door.class);
    private final Engine engine = mock(Engine.class);

    @Test
    public void shouldRegisterItselfToCollaborators() {

        Elevator elevator = new Elevator(door, engine);

        verify(door).setListener(elevator);
        verify(engine).setListener(elevator);
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

        elevator.floorRequested(Floor.ofLevel(ANY_FLOOR_LEVEL));

        verify(door).close();
    }

    @Test
    public void shouldStartEngineWhenFloorRequestedAndDoorClosed() {

        Elevator elevator = new Elevator(door, engine);

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

        verify(engine).start(eq(Floor.ofLevel(0)), any(Direction.class));
    }

    @Test
    public void shouldStopEngineWhenRequestedFloorReached() {

        Elevator elevator = new Elevator(door, engine);
        Floor requestedFloor = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.doorClosed();
        elevator.floorReached(requestedFloor);

        verify(engine).stop();
    }

    @Test
    public void shouldNotStopEngineWhenReachedMiddleFloor() {

        Elevator elevator = new Elevator(door, engine);
        Floor requestedFloor = Floor.ofLevel(2);
        Floor reachedFloor = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.doorClosed();
        elevator.floorReached(reachedFloor);

        verify(engine, times(0)).stop();
    }
}
