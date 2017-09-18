package pragmatists.elevator.test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import pragmatists.elevator.Direction;
import pragmatists.elevator.Elevator;
import pragmatists.elevator.event.DoorOpenEvent;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorDriver {

    private final EventBus eventBus;
    private Queue<Object> eventQueue = new LinkedList<>();
    private Elevator elevator;

    public ElevatorDriver(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void run() {
        elevator = new Elevator(new EventBusLogger(eventBus));
        elevator.run();
    }

    @Subscribe public void eventOccured(Object event) {
        eventQueue.offer(event);
    }


    public void thenDoorOpened() {
        assertThat(nextEvent()).isInstanceOf(DoorOpenEvent.class);
    }

    private Object nextEvent() {
        return eventQueue.poll();
    }





    public void thenDoorClosed() {

    }

    public void thenEngineStarted(Direction direction) {

    }

    public void thenEngineStopped() {

    }

    public void thenFloorReached(int level) {

    }
}
