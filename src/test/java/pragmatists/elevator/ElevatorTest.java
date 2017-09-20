package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.DoorState;
import pragmatists.elevator.engine.Engine;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(JUnitParamsRunner.class)
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
    @Parameters({
            "0, 1, UP", "0, 2, UP", "2, 3, UP",
            "0, -1, DOWN", "3, -2, DOWN", "5, 4, DOWN"
    })
    public void shouldStartEngineInCorrectDirectionWhenFloorRequestedAndDoorClosed(
            int startingLevel, int targetLevel, Direction expectedDirection) {

        Elevator elevator = anElevator()
                .startingAt(startingLevel).build();

        elevator.floorRequested(Floor.ofLevel(targetLevel));
        elevator.doorStateChanged(DoorState.CLOSED);

        verify(engine).start(eq(Floor.ofLevel(startingLevel)), eq(expectedDirection));
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
