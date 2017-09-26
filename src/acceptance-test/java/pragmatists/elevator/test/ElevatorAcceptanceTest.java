package pragmatists.elevator.test;

import org.junit.Ignore;
import org.junit.Test;
import pragmatists.elevator.engine.Engine.Direction;

public class ElevatorAcceptanceTest {

    private ElevatorDriver elevator = new ElevatorDriver();

    @Test
    public void elevator_opens_door_at_beginning() {

        elevator.whenRun();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_1st() {

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
    public void elevator_goes_to_floor_minus_1st() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to -1st floor
        elevator.whenButtonPressed(-1);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-1);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_2nd() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to 2nd floor
        elevator.whenButtonPressed(2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_minus_2nd() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to -2nd floor
        elevator.whenButtonPressed(-2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(-2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_2nd_and_then_to_floor_4th() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to 2nd floor
        elevator.whenButtonPressed(2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to 4th floor
        elevator.whenButtonPressed(4);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(3);
        elevator.thenFloorReached(4);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_minus_2nd_and_then_to_floor_minus_4th() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to -2nd floor
        elevator.whenButtonPressed(-2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(-2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to -4th floor
        elevator.whenButtonPressed(-4);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-3);
        elevator.thenFloorReached(-4);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_2nd_and_then_to_floor_4th_ignoring_request_order() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // request at beginning
        elevator.whenButtonPressed(4);
        elevator.whenButtonPressed(2);

        // go to 2nd floor
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to 4th floor
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(3);
        elevator.thenFloorReached(4);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_minus_2nd_and_then_to_floor_minus_4th_ignoring_request_order() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // request at beginning
        elevator.whenButtonPressed(-4);
        elevator.whenButtonPressed(-2);

        // go to -2nd floor
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(-2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to -4th floor
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-3);
        elevator.thenFloorReached(-4);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_2nd_and_then_go_down_to_floor_minus_2nd() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to 2nd floor
        elevator.whenButtonPressed(2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to -2nd floor
        elevator.whenButtonPressed(-2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(0);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(-2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Test
    public void elevator_goes_to_floor_minus_2nd_and_then_go_up_to_floor_2nd() {

        elevator.whenRun();
        elevator.thenDoorOpened();

        // go to -2nd floor
        elevator.whenButtonPressed(-2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.DOWN);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(-2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();

        // go to 2nd floor
        elevator.whenButtonPressed(2);
        elevator.thenDoorClosed();
        elevator.thenEngineStarted(Direction.UP);
        elevator.thenFloorReached(-1);
        elevator.thenFloorReached(0);
        elevator.thenFloorReached(1);
        elevator.thenFloorReached(2);
        elevator.thenEngineStopped();
        elevator.thenDoorOpened();
    }

    @Ignore
    @Test
    public void elevator_visits_once_requested_floors_in_order() {

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
