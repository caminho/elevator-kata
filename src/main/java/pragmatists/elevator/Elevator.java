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
        if (!floor.equals(currentFloor)) {
            queueFloor(floor);
            if (elevatorIsWaiting()) {
                moveToGoingState();
            }
        }
    }

    private void moveToGoingState() {
        if (!requestedFloorsUp.isEmpty()) {
            elevatorState = ElevatorState.GOING_UP;
        }
        if (!requestedFloorsDown.isEmpty()) {
            elevatorState = ElevatorState.GOING_DOWN;
        }
        door.close();
    }

    private void queueFloor(Floor floor) {
        if (floor.isGreaterThan(currentFloor)) {
            requestedFloorsUp.add(floor);
        }
        if (floor.isLowerThan(currentFloor)) {
            requestedFloorsDown.add(floor);
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
        removeCurrentFromRequestedFloors();
        if (floorRequestsExist()) {
            moveToGoingState();
        } else {
            moveToWaitingState();
        }
    }

    private void removeCurrentFromRequestedFloors() {
        if (elevatorState == ElevatorState.GOING_UP
                && requestedFloorsUp.contains(currentFloor)) {
            requestedFloorsUp.remove(currentFloor);
        }
        if (elevatorState == ElevatorState.GOING_DOWN
                && requestedFloorsDown.contains(currentFloor)) {
            requestedFloorsDown.remove(currentFloor);
        }
    }

    private void moveToWaitingState() {
        elevatorState = ElevatorState.WAITING;
    }

    @Override
    public void floorReached(Floor floor) {
        currentFloor = floor;
        if (requestedFloorReached()) {
            engine.stop();
        }
    }

    private boolean requestedFloorReached() {
        Floor requestedFloor = nextRequestedFloor();
        return currentFloor.equals(requestedFloor);
    }

    private Floor nextRequestedFloor() {
        Floor requestedFloor = null;
        if (elevatorState == ElevatorState.GOING_UP) {
            requestedFloor = requestedFloorsUp.first();
        } else if (elevatorState == ElevatorState.GOING_DOWN) {
            requestedFloor = requestedFloorsDown.last();
        }
        return requestedFloor;
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

    private boolean floorRequestsExist() {
        return !requestedFloorsUp.isEmpty() || !requestedFloorsDown.isEmpty();
    }

    private boolean elevatorIsWaiting() {
        return elevatorState == ElevatorState.WAITING;
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
