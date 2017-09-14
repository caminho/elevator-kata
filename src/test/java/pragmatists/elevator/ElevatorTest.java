package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.engine.Direction;
import pragmatists.elevator.engine.Engine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(JUnitParamsRunner.class)
public class ElevatorTest {

    private final Door door = mock(Door.class);
    private final Engine engine = mock(Engine.class);
    private final Elevator elevator = new Elevator(door, engine);

    @Test
    public void shouldDontStartEngineWhenRequestedSameFloorAsCurrent() {

        elevator.floorRequested(Floor.ofLevel(0));

        verifyZeroInteractions(door, engine);
    }

    @Test
    @Parameters({"-1", "2", "3"})
    public void shouldCloseDoorBeforeMoving(int level) {

        elevator.floorRequested(Floor.ofLevel(level));

        verify(door).close();
    }

    @Test
    @Parameters({"1", "2", "3"})
    public void shouldStartEngineUpWhenHigherFloorRequestedAndDoorClosed(int level) {

        elevator.floorRequested(Floor.ofLevel(level));
        elevator.doorClosed();

        verify(engine).start(Floor.ofLevel(0), Direction.UP);
    }

    @Test
    @Parameters({"-1", "-2", "-3"})
    public void shouldStartEngineDownWhenLowerFloorRequestedAndDoorClosed(int level) {

        elevator.floorRequested(Floor.ofLevel(level));

        elevator.doorClosed();

        verify(engine).start(Floor.ofLevel(0), Direction.DOWN);
    }


    @Test
    public void shouldNotStopEngineNorOpenDoorWhenRequestedFloorNotReached() {

        elevator.floorRequested(Floor.ofLevel(2));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));

        verify(engine, Mockito.never()).stop();
        verify(door, Mockito.never()).open();
    }


    @Test
    public void shouldStopEngineWhenRequestedFloorReached() {

        elevator.floorRequested(Floor.ofLevel(2));
        elevator.doorClosed();

        elevator.floorReached(Floor.ofLevel(1));
        elevator.floorReached(Floor.ofLevel(2));

        verify(engine).stop();
    }

    @Test
    public void shouldOpenDoorWhenEngineStoppedAtRequestedLevel() {

        elevator.floorRequested(Floor.ofLevel(1));

        elevator.floorReached(Floor.ofLevel(1));
        elevator.engineStopped();

        verify(door).open();
    }

}
