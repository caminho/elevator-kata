package pragmatists.elevator.test;

import org.junit.Ignore;
import org.junit.Test;
import pragmatists.elevator.Direction;

public class ElevatorAcceptanceTest {

    private ElevatorDriver elevator = new ElevatorDriver();

    @Test
    public void elevatorShouldOpenDoorWhenStart() {
        elevator.whenRun();

        elevator.makeDoorStep();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevatorShouldGoToNextFloor() {
        elevator.whenRun();

        elevator.makeDoorStep();
        elevator.thenDoorOpened();

        elevator.whenButtonPressed(1);
        elevator.makeDoorStep();

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);

        elevator.makeEngineStep();
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();

        elevator.makeDoorStep();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevatorShouldVisitFloorsInDifferentDirections() {

        elevator.whenRun();
        elevator.makeDoorStep();
        elevator.thenDoorOpened();

        // go to 3rd floor
        elevator.whenButtonPressed(3);
        elevator.makeDoorStep();

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);

        elevator.makeEngineStep();
        elevator.thenFloorReached(1);
        elevator.makeEngineStep();
        elevator.thenFloorReached(2);
        elevator.makeEngineStep();
        elevator.thenFloorReached(3);
        elevator.makeEngineStep();
        elevator.thenEngineStopped();

        elevator.makeDoorStep();
        elevator.thenDoorOpened();

        // go back to 1st floor
        elevator.whenButtonPressed(1);
        elevator.makeDoorStep();

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(2);
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();

        elevator.makeDoorStep();
        elevator.thenDoorOpened();
    }

    @Ignore
    @Test
    public void elevatorShouldVisitFloorsInOrder() {
        elevator.whenRun();
        elevator.makeDoorStep();
        elevator.thenDoorOpened();

        elevator.whenButtonPressed(4);
        elevator.whenButtonPressed(2);
        elevator.whenButtonPressed(5);
        elevator.makeDoorStep();

        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenFloorReached(4);
        elevator.thenFloorReached(5);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }
}
