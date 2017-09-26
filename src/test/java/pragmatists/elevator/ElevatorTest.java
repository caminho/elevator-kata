package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.Door.DoorState;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.Engine.Direction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnitParamsRunner.class)
public class ElevatorTest {

    private final Door door = mock(Door.class);
    private final Engine engine = mock(Engine.class);

    private ElevatorBuilder anElevator() {
        return ElevatorBuilder.anElevator(door, engine);
    }

    @Test
    public void should_register_itself_to_collaborators() {

        Elevator elevator = anElevator().build();

        verify(door).setListener(elevator);
        verify(engine).setListener(elevator);
    }

    @Test
    public void should_reset_engine_at_start() {

        Elevator elevator = anElevator().startingAt(3).build();

        elevator.run();

        verify(engine).reset(Floor.ofLevel(3));
    }

    @Test
    public void should_open_door_at_start() {

        Elevator elevator = anElevator().build();

        elevator.run();

        verify(door).open();
    }

    // elevator_goes_to_floor_1st

    @Test
    public void should_close_door_after_request() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(1));

        verify(door).close();
    }

    @Test
    public void should_start_engine_up_after_closing_door() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorStateChanged(DoorState.CLOSED);

        verify(engine).start(Direction.UP);
    }

    @Test
    public void should_stop_engine_at_requested_floor() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorStateChanged(DoorState.CLOSED);
        elevator.floorReached(Floor.ofLevel(1));

        verify(engine).stop();
    }

    @Test
    public void should_open_door_when_engine_stopped_at_requested_floor() {

        Elevator elevator = anElevator().build();

        elevator.floorRequested(Floor.ofLevel(1));
        elevator.doorStateChanged(DoorState.CLOSED);
        elevator.floorReached(Floor.ofLevel(1));
        elevator.engineStopped();

        verify(door).open();
    }
}
