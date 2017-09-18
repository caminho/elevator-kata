package pragmatists.elevator.test;

import org.junit.Test;
import pragmatists.elevator.Direction;

public class ElevatorAcceptanceTest {

    private ElevatorDriver elevator = new ElevatorDriver();

    @Test
    public void elevatorShouldOpenDoorWhenStart() {
        elevator.whenRun();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevatorShouldGoToNextFloor() {
        elevator.whenRun();
        elevator.thenDoorOpened();

        elevator.whenButtonPressed(1);

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

}
