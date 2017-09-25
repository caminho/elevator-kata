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

    private enum ElevatorState {
        GOING_UP{
            public Direction direction() {
                return Direction.UP;
            }
        },
        GOING_DOWN {
            public Direction direction() {
                return Direction.DOWN;
            }
        },
        WAITING {
            public Direction direction() {
                return Direction.NONE;
            }
        };

        public abstract Direction direction();
    }

    private final Door door;
    private final Engine engine;

    private Floor currentFloor = Floor.ofLevel(0);


    private TreeSet<Floor> requestedFloorsUp = Sets.newTreeSet();
    private TreeSet<Floor> requestedFloorsDown = Sets.newTreeSet();

    private Floor requestedFloor;

    private ElevatorState state = ElevatorState.WAITING;

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
        door.open();
    }

    @Override
    public void floorRequested(Floor requestedFloor) {
        if (state == ElevatorState.WAITING) {
            assert requestedFloorsDown.isEmpty();
            assert requestedFloorsUp.isEmpty();

            this.requestedFloor = requestedFloor;
            //requestedFloors.add(requestedFloor);

            if (requestedFloor.isGreaterThan(currentFloor)) {
                requestedFloorsUp.add(requestedFloor);
                state = ElevatorState.GOING_UP;
            }
            if (requestedFloor.isLowerThan(currentFloor)) {
                requestedFloorsDown.add(requestedFloor);
                state = ElevatorState.GOING_DOWN;
            }

            door.close();

        } else if (state == ElevatorState.GOING_UP) {

            if (requestedFloor.isGreaterThan(currentFloor)) {
                requestedFloorsUp.add(requestedFloor);
            }
            if (requestedFloor.isLowerThan(currentFloor)) {
                requestedFloorsDown.add(requestedFloor);
            }

            this.requestedFloor = requestedFloorsUp.first();

        } else if (state == ElevatorState.GOING_DOWN) {

            if (requestedFloor.isGreaterThan(currentFloor)) {
                requestedFloorsUp.add(requestedFloor);
            }
            if (requestedFloor.isLowerThan(currentFloor)) {
                requestedFloorsDown.add(requestedFloor);
            }

            this.requestedFloor = requestedFloorsDown.last();
        }
    }

    @Override
    public void doorStateChanged(DoorState doorState) {
        if (requestExists()) {
            return;
        }
        if (doorState.equals(DoorState.CLOSED)) {
            engine.start(currentFloor, requestedDirection());
        }
    }

    private Direction requestedDirection() {
        if (state == ElevatorState.GOING_UP) {
            return Direction.UP;
        }
        if (state == ElevatorState.GOING_DOWN) {
            return Direction.DOWN;
        }
        return Direction.NONE;
    }

    @Override
    public void floorReached(Floor floor) {
        this.currentFloor = floor;
        if (atRequestedFloor()) {
            engine.stop();
        }
    }

    @Override
    public void engineStopped() {
        if (atRequestedFloor()) {
            door.open();
        }
    }

    private boolean atRequestedFloor() {
        return currentFloor.equals(requestedFloor);
    }

    private boolean requestExists() {
        return requestedFloor == null;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                '}';
    }
}
