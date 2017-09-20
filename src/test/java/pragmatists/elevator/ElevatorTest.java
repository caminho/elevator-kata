package pragmatists.elevator;

import org.junit.Test;
import pragmatists.elevator.door.DoorState;
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

    private ElevatorBuilder anElevator() {
        return ElevatorBuilder.anElevator(door, engine);
    }
    
    @Test
    public void shouldRegisterItselfToCollaborators() {

        Elevator elevator = anElevator().build();

        verify(door).setListener(elevator);
        verify(engine).setListener(elevator);
    }

    @Test
    public void shouldOpenDoorAfterStart() {

        Elevator elevator = anElevator().build();

        elevator.run();

        verify(door).open();
    }

    @Test
    public void shouldCloseDoorWhenFloorRequested() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(ANY_FLOOR_LEVEL));

        verify(door).close();
    }

    @Test
    public void shouldStartEngineWhenFloorRequestedAndDoorClosed() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorStateChanged(DoorState.CLOSED);

        verify(engine).start(eq(Floor.ofLevel(0)), any(Direction.class));
    }

    @Test
    public void shouldStopEngineWhenRequestedFloorReached() {

        Elevator elevator = anElevator().build();
        Floor requestedFloor = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.floorReached(requestedFloor);

        verify(engine).stop();
    }

    @Test
    public void shouldNotStopEngineWhenReachedNotRequestedFloor() {

        Elevator elevator = anElevator().build();
        Floor requestedFloor = Floor.ofLevel(2);
        Floor reachedFloor = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.floorReached(reachedFloor);

        verify(engine, times(0)).stop();
    }

    @Test
    public void shouldOpenDoorAfterStopAtRequestedLevel() {

        Elevator elevator = anElevator().build();
        Floor requestedFloor = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.floorReached(requestedFloor);
        elevator.engineStopped();

        verify(door).open();
    }

    @Test
    public void shouldNotOpenDoorWhenStopAtNotRequestedLevel() {

        Elevator elevator = anElevator().build();
        Floor requestedFloor = Floor.ofLevel(2);
        Floor reachedLevel = Floor.ofLevel(1);

        elevator.floorRequested(requestedFloor);
        elevator.floorReached(reachedLevel);
        elevator.engineStopped();

        verify(door, times(0)).open();
    }
}
