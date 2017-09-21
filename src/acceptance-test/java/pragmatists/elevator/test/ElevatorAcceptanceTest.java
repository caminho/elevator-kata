package pragmatists.elevator.test;

import org.junit.Test;
import pragmatists.elevator.engine.Engine.Direction;

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

        // go to 1st floor
        elevator.whenButtonPressed(1);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevatorShouldVisitFloorsInDifferentDirections() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to 3rd floor
        elevator.whenButtonPressed(3);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenFloorReached(3);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go back to 1st floor
        elevator.whenButtonPressed(1);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(2);
        elevator.thenFloorReached(1);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevatorShouldVisitFloorsInOrder() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // choose floors 4, 2, 5 before run
        elevator.whenButtonPressed(4);
        elevator.whenButtonPressed(2);
        elevator.whenButtonPressed(5);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);

        // cross 1st floor
        elevator.thenFloorReached(1);

        // stop at floor 2
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);

        // cross 3rd floor
        elevator.thenFloorReached(3);

        // stop at floor 4
        elevator.thenFloorReached(4);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);

        // stop at floor 5
        elevator.thenFloorReached(5);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }
}
