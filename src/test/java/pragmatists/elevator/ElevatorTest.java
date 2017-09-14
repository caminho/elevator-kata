package pragmatists.elevator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.Engine.Direction;

@RunWith(JUnitParamsRunner.class)
public class ElevatorTest {

    private final Door door = mock(Door.class);
    private final Engine engine = mock(Engine.class);
    private final Elevator elevator = new Elevator(door, engine);



    @Test
    public void elevatorShouldStartAtGroundZero() {

        assertThat(elevator.currentFloor()).isEqualTo(
                Floor.ofLevel(0));
    }

    @Test
    public void shouldCloseDoorWhenFloorRequested() {

        elevator.doorOpened();
        elevator.floorRequested(Floor.ofLevel(5));

        verify(door).close();
    }

    @Test
    public void shouldNotCloseDoorWhenRequestedCurrentFloor() {

        elevator.doorOpened();
        elevator.floorRequested(Floor.ofLevel(0));

        verify(door, never()).close();
    }

    @Test
    public void shouldNotCloseDoorIfAlreadyClosed() {

        elevator.doorClosed();
        elevator.floorRequested(Floor.ofLevel(1));

        verify(door, never()).close();
    }

    @Test
    public void shouldStartGoingUpWhenHigherFloorRequestedAndDoorClosed() {

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

        verify(engine).start(Direction.UP);
    }

    @Test
    public void shouldStartGoingUpWhenDoorClosedAndFloorRequested() {

        elevator.doorClosed();
        elevator.floorRequested(Floor.ofLevel(1));

        verify(engine).start(Direction.UP);
    }

    @Test
    public void shouldStartGoingDownWhenLowerFloorRequestedAndDoorClosed() {

        elevator.floorRequested(Floor.ofLevel(-1));
        elevator.doorClosed();

        verify(engine).start(Direction.DOWN);
    }

    @Test
    public void shouldNotStartEngineWhenNoFloorRequested() {

        elevator.doorClosed();

        verify(engine, never()).start(any(Direction.class));
    }

    @Test
    @Parameters({"2, 1", "-2, -1"})
    public void shouldNotStopEngineAtReachedLevelIfNotRequested(int requestedLevel, int reachedLevel) {

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(reachedLevel));

        verify(engine, never()).stop();
    }

    @Test
    @Parameters({"1", "-1"})
    public void shouldStopEngineAtRequestedLevelNextToCurrentFloor(int requestedLevel) {

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(requestedLevel));

        verify(engine).stop();
    }

    @Test
    public void shouldStopEngineOnceAtRequestedLevelIgnoringFloorsBetween() {

        elevator.floorRequested(Floor.ofLevel(3));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));
        elevator.floorReached(Floor.ofLevel(2));
        elevator.floorReached(Floor.ofLevel(3));

        verify(engine).stop();
    }

    @Test
    public void shouldOpenDoorAfterEngineStoppedAtRequestedFloor() {

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));
        elevator.engineStopped();

        verify(door).open();
    }

    @Test
    public void shouldMoveToNextRequestedFloorInTheSameDirection() {

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));
        elevator.engineStopped();
        elevator.doorOpened();

        elevator.floorRequested(Floor.ofLevel(2));
        elevator.doorClosed();

        verify(engine, times(2)).start(Direction.UP);
    }

    @Test
    public void shouldMoveToNextRequestedFloorInOppositeDirection() {

        elevator.floorRequested(Floor.ofLevel(2));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));
        elevator.floorReached(Floor.ofLevel(2));
        elevator.engineStopped();
        elevator.doorOpened();

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

        verify(engine).start(Direction.UP);
        verify(engine).start(Direction.DOWN);
    }

//    @Test
//    public void shouldRequestManyFloorsBeforeMove() {
//
//        elevator.floorRequested(Floor.ofLevel(1));
//        elevator.floorRequested(Floor.ofLevel(2));
//        elevator.doorClosed();
//
//        elevator.floorReached(Floor.ofLevel(1));
//        elevator.floorReached(Floor.ofLevel(2));
//        elevator.engineStopped();
//        elevator.doorOpened();
//
//        elevator.floorRequested(Floor.ofLevel(1));
//        elevator.doorClosed();
//
//
//    }
}
