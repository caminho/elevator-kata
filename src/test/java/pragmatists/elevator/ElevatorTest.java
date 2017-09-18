package pragmatists.elevator;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import pragmatists.elevator.event.DoorOpenEvent;

public class ElevatorTest {

    private final EventLogger eventLogger = mock(EventLogger.class);
    private final Elevator elevator = new Elevator(eventLogger);

    @Test
    public void shouldOpenDoorAfterStart() {

        elevator.run();

        verify(eventLogger).logEvent(isA(DoorOpenEvent.class));
    }
}
