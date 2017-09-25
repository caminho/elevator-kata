package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.Door.DoorState;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.Engine.Direction;

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
    public void shouldCloseDoorOnceWhenFloorRequestedMultipleTimes() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(4));
        elevator.floorRequested(Floor.ofLevel(5));

        verify(door, times(1)).close();
    }

    @Test
    @Parameters({
            "0, 1,  UP",
            "0, 2,  UP",
            "2, 3,  UP",
            "0, -1, DOWN",
            "3, -2, DOWN",
            "5, 4,  DOWN"
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
    @Parameters({"1", "2", "3"})
    public void shouldStopEngineWhenRequestedFloorReached(int requestedLevel) {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.floorReached(Floor.ofLevel(requestedLevel));

        verify(engine).stop();
    }

    @Test
    @Parameters({
            "3, 4, 5, 4",
            "3, 5, 4, 4",
            "6, 4, 5, 5",
            "6, 5, 4, 5",
            "3, 4, 2, 4",
            "3, 2, 4, 2",
    })
    public void shouldStopEngineAtNearestRequestedFloor(
            int startLevel, int firstRequest, int secondRequest, int reached) {

        Elevator elevator = anElevator().startingAt(startLevel).build();

        elevator.floorRequested(Floor.ofLevel(firstRequest));
        elevator.floorRequested(Floor.ofLevel(secondRequest));
        elevator.floorReached(Floor.ofLevel(reached));

        verify(engine).stop();
    }

    @Test
    @Parameters({"2, 1", "2, 3", "5, 4", "8, -1"})
    public void shouldNotStopEngineWhenReachedMiddleFloor(
            int requestedLevel, int middleLevel) {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.floorReached(Floor.ofLevel(middleLevel));

        verify(engine, times(0)).stop();
    }

    @Test
    @Parameters({
            "2, 4, 5, 3",
            "2, 5, 4, 3",
            "7, 4, 5, 6",
            "7, 5, 4, 6",
    })
    public void shouldNotStopEngineWhenReachedMiddleFloorWhenMultipleFloorsRequested(
            int startLevel, int firstRequest, int secondRequest, int reached) {

        Elevator elevator = anElevator().startingAt(startLevel).build();

        elevator.floorRequested(Floor.ofLevel(firstRequest));
        elevator.floorRequested(Floor.ofLevel(secondRequest));
        elevator.floorReached(Floor.ofLevel(reached));

        verify(engine, times(0)).stop();
    }

    @Test
    @Parameters({"1", "2", "3"})
    public void shouldOpenDoorAfterStopAtRequestedLevel(int requestedLevel) {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.floorReached(Floor.ofLevel(requestedLevel));
        elevator.engineStopped();

        verify(door).open();
    }

    @Test
    @Parameters({
            "3,   5, 4,    4",
            "3,   4, 5,    4",
            "6,   4, 5,    5",
            "6,   5, 4,    5",
    })
    public void shouldOpenDoorAfterStopAtNearestRequestedFloor(
            int startingLevel, int firstRequest, int secondRequest, int reachedLevel) {

        Elevator elevator = anElevator().startingAt(startingLevel).build();

        elevator.floorRequested(Floor.ofLevel(firstRequest));
        elevator.floorRequested(Floor.ofLevel(secondRequest));

        elevator.floorReached(Floor.ofLevel(reachedLevel));
        elevator.engineStopped();

        verify(door).open();
    }

    @Test
    @Parameters({"2, 1", "2, 3", "5, 4", "8, -1"})
    public void shouldNotOpenDoorWhenStopAtMiddleLevel(
            int requestedLevel, int middleLevel) {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.floorReached(Floor.ofLevel(middleLevel));
        elevator.engineStopped();

        verify(door, times(0)).open();
    }

    @Test
    @Parameters({
            "3, 5, 4, 4",
            "3, 4, 5, 4",
            "6, 4, 5, 5",
            "6, 5, 4, 5",

    })
    public void shouldCloseDoorWhenGoingToNextRequestedFloor(
            int startingLevel, int firstRequest, int secondRequest, int reachedFloor) {

        Elevator elevator = anElevator().startingAt(startingLevel).build();

        elevator.floorRequested(Floor.ofLevel(firstRequest));
        elevator.floorRequested(Floor.ofLevel(secondRequest));

        elevator.floorReached(Floor.ofLevel(reachedFloor));
        elevator.engineStopped();
        elevator.doorStateChanged(DoorState.OPENED);

        verify(door).close();
    }

    @Test
    public void shouldStartEngineWithGivenDirectionWhenVisitingNextFloor() {

        Elevator elevator = anElevator().startingAt(3).build();

        elevator.floorRequested(Floor.ofLevel(5));
        elevator.floorRequested(Floor.ofLevel(4));

        elevator.floorReached(Floor.ofLevel(4));
        elevator.engineStopped();
        elevator.doorStateChanged(DoorState.OPENED);
        elevator.doorStateChanged(DoorState.CLOSED);

        verify(engine).start(Floor.ofLevel(4), Direction.UP);
    }
}
