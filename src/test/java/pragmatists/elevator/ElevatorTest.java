package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.engine.Engine;

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

}
