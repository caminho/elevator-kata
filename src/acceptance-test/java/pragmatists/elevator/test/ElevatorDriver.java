package pragmatists.elevator.test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.ElevatorDoor;
import pragmatists.elevator.panel.ButtonPanel;
import pragmatists.elevator.Direction;
import pragmatists.elevator.Elevator;
import pragmatists.elevator.event.*;
import pragmatists.elevator.panel.ElevatorButtonPanel;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorDriver {

    private final EventBus eventBus;
    private Queue<ElevatorEvent> eventQueue = new LinkedList<>();

    private ButtonPanel buttonPanel;
    private Elevator elevator;

    ElevatorDriver() {
        this.eventBus = new EventBus();
        this.eventBus.register(this);
    }

    @Subscribe
    public void eventOccured(ElevatorEvent event) {
        eventQueue.offer(event);
    }

    void whenRun() {
        EventBusLogger eventLogger = new EventBusLogger(eventBus);
        Door door = new ElevatorDoor(eventLogger);
        elevator = new Elevator(eventLogger, door);
        buttonPanel = new ElevatorButtonPanel(elevator);
        elevator.run();
    }

    void whenButtonPressed(int buttonNum) {
        buttonPanel.buttonPressed(buttonNum);
    }

    void thenDoorOpened() {
        assertThat(nextEvent()).isInstanceOf(DoorOpenEvent.class);
    }

    void thenDoorClosed() {
        assertThat(nextEvent()).isInstanceOf(DoorClosedEvent.class);
    }

    void thenEngineStarted(Direction direction) {
        assertThat(nextEvent()).isInstanceOf(EngineStartedEvent.class)
                .isEqualTo(new EngineStartedEvent(direction));

    }

    void thenEngineStopped() {
        assertThat(nextEvent()).isInstanceOf(EngineStoppedEvent.class);
    }

    void thenFloorReached(int level) {
        assertThat(nextEvent()).isInstanceOf(FloorReachedEvent.class)
                .isEqualTo(new FloorReachedEvent(level));
    }

    private Object nextEvent() {
        return eventQueue.poll();
    }
}
