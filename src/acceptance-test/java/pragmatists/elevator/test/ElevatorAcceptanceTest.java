package pragmatists.elevator.test;

import com.google.common.eventbus.EventBus;
import org.junit.Ignore;
import org.junit.Test;
import pragmatists.elevator.Direction;

public class ElevatorAcceptanceTest {

    private EventBus eventBus = new EventBus();
    private UserDriver user = new UserDriver(eventBus);
    private ElevatorDriver elevator = new ElevatorDriver(eventBus);

    @Test
    public void elevatorShouldOpenDoorWhenStart() {
        elevator.run();
        elevator.thenDoorOpened();
    }

    @Ignore
    @Test
    public void elevatorShouldGoToNextFloor() {
        elevator.run();
        elevator.thenDoorOpened();

        user.pressed(1);

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

}
