package pragmatists.elevator.test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pragmatists.elevator.Elevator;
import pragmatists.elevator.door.ElevatorDoor;
import pragmatists.elevator.door.LazyDoorDriver;
import pragmatists.elevator.engine.ElevatorEngine;
import pragmatists.elevator.engine.Engine.Direction;
import pragmatists.elevator.event.*;
import pragmatists.elevator.panel.ButtonPanel;
import pragmatists.elevator.panel.ElevatorButtonPanel;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorDriver {

    private final EventBus eventBus;
    private Queue<ElevatorEvent> eventQueue = new LinkedList<>();

    private ButtonPanel buttonPanel;
    private Elevator elevator;
    private LazyDoorDriver door;
    private ElevatorEngine engine;
    private EventBusLogger logger;

    ElevatorDriver() {
        this.eventBus = new EventBus();
        this.eventBus.register(this);
    }

    @Subscribe
    public void eventOccured(ElevatorEvent event) {
        eventQueue.offer(event);
    }

    void whenRun() {
        logger = new EventBusLogger(eventBus);
        door = new LazyDoorDriver(new ElevatorDoor(logger));
        engine = new ElevatorEngine(logger);
        elevator = new Elevator(door, engine);
        buttonPanel = new ElevatorButtonPanel(elevator);
        elevator.run();
    }

    void whenButtonPressed(int buttonNum) {
        buttonPanel.buttonPressed(buttonNum);
    }

    void thenDoorOpened() {
        makeDoorStep();
        assertThat(nextEvent()).isInstanceOf(DoorOpenEvent.class);
    }

    void thenDoorClosed() {
        makeDoorStep();
        assertThat(nextEvent()).isInstanceOf(DoorClosedEvent.class);
    }

    void thenEngineStarted(Direction direction) {
        makeEngineStep();
        assertThat(nextEvent()).isInstanceOf(EngineStartedEvent.class)
                .isEqualTo(new EngineStartedEvent(direction));

    }

    void thenEngineStopped() {
        makeEngineStep();
        assertThat(nextEvent()).isInstanceOf(EngineStoppedEvent.class);
    }

    void thenFloorReached(int level) {
        makeEngineStep();
        assertThat(nextEvent()).isInstanceOf(FloorReachedEvent.class)
                .isEqualTo(new FloorReachedEvent(level));
    }

    private Object nextEvent() {
        return eventQueue.poll();
    }

    private void makeDoorStep() {
        door.moveIfRequested();
    }

    private void makeEngineStep() {
        engine.step();
    }
}
