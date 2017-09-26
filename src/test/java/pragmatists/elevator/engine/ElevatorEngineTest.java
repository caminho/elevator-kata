package pragmatists.elevator.engine;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.engine.Engine.Direction;
import pragmatists.elevator.event.EngineStoppedEvent;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

        engine.start(Direction.UP);

        verifyZeroInteractions(engineSensor);
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveUpToNextFloor(int startLevel) {

        Floor startFloor = Floor.ofLevel(startLevel);

        engine.reset(startFloor);
        engine.start(Direction.UP);
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(startLevel + 1));
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveDownToPreviousFloor(int startLevel) {

        Floor startFloor = Floor.ofLevel(startLevel);

        engine.reset(startFloor);
        engine.start(Direction.DOWN);
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(startLevel - 1));
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveUpToSecondNextFloor(int startLevel) {

        Floor startFloor = Floor.ofLevel(startLevel);

        engine.reset(startFloor);
        engine.start(Direction.UP);
        engine.step();
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(startLevel + 1));
        verify(engineSensor).floorReached(Floor.ofLevel(startLevel + 2));
    }

    @Test
    @Parameters({"-1", "0", "1", "2", "3"})
    public void shouldMoveDownToSecondPreviousFloor(int startLevel) {

        Floor startFloor = Floor.ofLevel(startLevel);

        engine.reset(startFloor);
        engine.start(Direction.DOWN);
        engine.step();
        engine.step();

        verify(engineSensor).floorReached(Floor.ofLevel(startLevel - 1));
        verify(engineSensor).floorReached(Floor.ofLevel(startLevel - 2));
    }

    @Test
    public void shouldNotifyEngineStopped() {

        engine.stop();

        verify(logger).logEvent(isA(EngineStoppedEvent.class));
        verify(engineSensor).engineStopped();
    }

    @Test
    public void shouldNotMoveWhenStopped() {

        engine.reset(Floor.ofLevel(3));
        engine.start(Direction.UP);
        engine.stop();
        engine.step();

        verify(engineSensor, times(0)).floorReached(any(Floor.class));
    }

    @Test
    public void shouldThrowExceptionWhenStartedAgain() {

        engine.reset(Floor.ofLevel(2));
        engine.start(Direction.UP);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() ->
                engine.start(Direction.DOWN)
        );
    }
}
