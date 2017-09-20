package pragmatists.elevator.engine;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.Direction;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.event.EngineStoppedEvent;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class ElevatorEngineTest {

    private EventLogger logger = mock(EventLogger.class);
    private EngineListener engineSensor = mock(EngineListener.class);

    private ElevatorEngine engine = new ElevatorEngine(logger);

    @Before
    public void setUp() {
        engine.setListener(engineSensor);
    }

    @Test
    public void shouldStartEngineWithoutMoving() {

        Floor currentLevel = Floor.ofLevel(1);

        engine.start(currentLevel, Direction.UP);

        verifyZeroInteractions(engineSensor);
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveUpToNextFloor(int level) {

        Floor startFloor = Floor.ofLevel(level);

        engine.start(startFloor, Direction.UP);
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(level + 1));
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveUpToSecondNextFloor(int level) {

        Floor startFloor = Floor.ofLevel(level);

        engine.start(startFloor, Direction.UP);
        engine.step();
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(level + 1));
        verify(engineSensor).floorReached(Floor.ofLevel(level + 2));
    }

    @Test
    public void shouldNotifyEngineStopped() {

        engine.stop();

        verify(logger).logEvent(isA(EngineStoppedEvent.class));
        verify(engineSensor).engineStopped();
    }

    @Test
    public void shouldNotMoveWhenStopped() {

        engine.start(Floor.ofLevel(3), Direction.UP);
        engine.stop();
        engine.step();

        verify(engineSensor, times(0)).floorReached(any(Floor.class));
    }
}
