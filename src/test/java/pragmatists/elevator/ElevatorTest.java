package pragmatists.elevator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

        elevator.floorRequested(Floor.ofLevel(5));

        verify(door).close();
    }

    @Test
    public void shouldNotCloseDoorWhenRequestedCurrentFloor() {

        elevator.floorRequested(Floor.ofLevel(0));

        verify(door, never()).close();
    }

    @Test
    public void shouldStartGoingUpWhenHigherFloorRequestedAndDoorClosed() {

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorClosed();

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

        elevator.reachedFloor(Floor.ofLevel(reachedLevel));

        verify(engine, never()).stop();
    }

    @Test
    @Parameters({"1", "-1"})
    public void shouldStopEngineAtRequestedLevelNextToCurrentFloor(int requestedLevel) {

        elevator.floorRequested(Floor.ofLevel(requestedLevel));
        elevator.doorClosed();

        elevator.reachedFloor(Floor.ofLevel(requestedLevel));

        verify(engine).stop();
    }

    @Test
    public void shouldStopEngineOnceAtRequestedLevelIgnoringFloorsBetween() {

        elevator.floorRequested(Floor.ofLevel(3));
        elevator.doorClosed();

        elevator.reachedFloor(Floor.ofLevel(1));
        elevator.reachedFloor(Floor.ofLevel(2));
        verify(engine, never()).stop();

        elevator.reachedFloor(Floor.ofLevel(3));
        verify(engine).stop();
    }
}
