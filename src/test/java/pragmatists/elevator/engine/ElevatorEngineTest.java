package pragmatists.elevator.engine;

import org.junit.Test;
import pragmatists.elevator.Direction;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.EngineStartedEvent;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorEngineTest {

    private static final Direction SOME_DIRECTION = Direction.UP;

    private EventLogger logger = mock(EventLogger.class);
    private Engine engine = new ElevatorEngine(logger);

    @Test
    public void shouldNotifyEngineStarted() {

        engine.start(SOME_DIRECTION);

        verify(logger).logEvent(eq(
                new EngineStartedEvent(SOME_DIRECTION)));
    }

}