package pragmatists.elevator;

import com.google.common.collect.Sets;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.Door.DoorState;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.Engine.Direction;
import pragmatists.elevator.engine.EngineListener;
import pragmatists.elevator.panel.ButtonListener;

import java.util.TreeSet;

public class Elevator implements
        ButtonListener, DoorListener, EngineListener {

    private final Door door;
    private final Engine engine;
    private ElevatorState elevatorState = ElevatorState.WAITING;
    private Floor currentFloor = Floor.ofLevel(0);

    private TreeSet<Floor> requestedFloorsUp = Sets.newTreeSet();
    private TreeSet<Floor> requestedFloorsDown = Sets.newTreeSet();

    public Elevator(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
        this.door.setListener(this);
        this.engine.setListener(this);
    }

    public Elevator(Door door, Engine engine, Floor startingFloor) {
        this(door, engine);
        this.currentFloor = startingFloor;
    }

    public void run() {
        engine.reset(currentFloor);
        door.open();
    }

    @Override
    public void floorRequested(Floor floor) {

        if (floor.isGreaterThan(currentFloor)) {
            requestedFloorsUp.add(floor);
        }
        if (floor.isLowerThan(currentFloor)) {
            requestedFloorsDown.add(floor);
        }

        if (elevatorState == ElevatorState.WAITING) {

            if (floor.isGreaterThan(currentFloor)) {
                elevatorState = ElevatorState.GOING_UP;
            }
            if (floor.isLowerThan(currentFloor)) {
                elevatorState = ElevatorState.GOING_DOWN;
            }
            door.close();
        }
    }

    @Override
    public void doorStateChanged(DoorState doorState) {

        if (doorState == DoorState.OPENED) {
            doorOpened();
        } else if (doorState == DoorState.CLOSED) {
            doorClosed();
        }
    }

    private void doorClosed() {
        engine.start(elevatorState.direction());
    }

    private void doorOpened() {

        if (elevatorState == ElevatorState.GOING_UP
                && requestedFloorsUp.contains(currentFloor)) {
            requestedFloorsUp.remove(currentFloor);
        }

        if (elevatorState == ElevatorState.GOING_DOWN
                && requestedFloorsDown.contains(currentFloor)) {
            requestedFloorsDown.remove(currentFloor);
        }

        if (!requestedFloorsUp.isEmpty() || !requestedFloorsDown.isEmpty()) {

            if (requestedFloorsUp.isEmpty()) {
                elevatorState = ElevatorState.GOING_DOWN;
            }
            if (requestedFloorsDown.isEmpty()) {
                elevatorState = ElevatorState.GOING_UP;
            }

            door.close();

        } else {
            elevatorState = ElevatorState.WAITING;
        }
    }

    @Override
    public void floorReached(Floor floor) {
        currentFloor = floor;
        if (reachedRequestedFloor()) {
            engine.stop();
        }
    }

    private boolean reachedRequestedFloor() {
        if (elevatorState == ElevatorState.GOING_UP) {
            return currentFloor.equals(requestedFloorsUp.first());
        } else if (elevatorState == ElevatorState.GOING_DOWN) {
            return currentFloor.equals(requestedFloorsDown.last());
        }
        return false;
    }

    @Override
    public void engineStopped() {
        door.open();
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                '}';
    }

    private enum ElevatorState {

        WAITING(Direction.NONE),
        GOING_UP(Direction.UP),
        GOING_DOWN(Direction.DOWN),;

        private final Direction direction;

        ElevatorState(Direction direction) {
            this.direction = direction;
        }

        public Direction direction() {
            return direction;
        }
    }
}
