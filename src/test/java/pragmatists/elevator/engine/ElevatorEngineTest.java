package pragmatists.elevator.engine;

import org.junit.Before;
import org.junit.Test;
import pragmatists.elevator.Direction;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.event.EngineStartedEvent;
import pragmatists.elevator.event.EngineStoppedEvent;
import pragmatists.elevator.event.FloorReachedEvent;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorEngineTest {

    private static final Direction SOME_DIRECTION = Direction.UP;
    private static final Floor ANY_FLOOR = Floor.ofLevel(1);

    private EventLogger logger = mock(EventLogger.class);
    private EngineListener engineSensor = mock(EngineListener.class);

    private Engine engine = new ElevatorEngine(logger);

    @Before
    public void setUp() {
        engine.setListener(engineSensor);
    }

    @Test
    public void shouldLogEngineStartedEvent() {

        engine.start(ANY_FLOOR, SOME_DIRECTION);

        verify(logger).logEvent(eq(
                new EngineStartedEvent(SOME_DIRECTION)));
    }

    @Test
    public void shouldLogReachingNextFloorEvent() {

        Floor currentLevel = Floor.ofLevel(1);
        engine.start(currentLevel, Direction.UP);

        verify(logger).logEvent(
                new FloorReachedEvent(currentLevel.nextFloor().level()));
    }

    @Test
    public void shouldNotifyAboutReachingNextFloor() {

        Floor currentLevel = Floor.ofLevel(1);
        engine.start(currentLevel, Direction.UP);

        verify(engineSensor).floorReached(currentLevel.nextFloor());
    }

    @Test
    public void shouldLogEngineStoppedEvent() {

        engine.stop();

        verify(logger).logEvent(isA(EngineStoppedEvent.class));
    }

    @Test
    public void shouldNotifyEngineStopped() {

        engine.stop();

        verify(engineSensor).engineStopped();
    }
}
