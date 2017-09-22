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
        GOING_UP, GOING_DOWN, WAITING
    }

    private final Door door;
    private final Engine engine;

    private Floor currentFloor = Floor.ofLevel(0);

    private TreeSet<Floor> requestedFloors = Sets.newTreeSet();
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
    public void floorRequested(Floor floor) {
        if (state == ElevatorState.WAITING) {
            assert requestedFloors.isEmpty();

            requestedFloor = floor;
            requestedFloors.add(floor);

            if (floor.isGreaterThan(currentFloor)) {
                state = ElevatorState.GOING_UP;
            }
            if (floor.isLowerThan(currentFloor)) {
                state = ElevatorState.GOING_DOWN;
            }

            door.close();

        } else if (state == ElevatorState.GOING_UP) {

            if (floor.isGreaterThan(currentFloor)) {

                requestedFloors.add(floor);
                requestedFloor = requestedFloors.first();
            }
        }
    }

    @Override
    public void doorStateChanged(DoorState doorState) {
        if (requestedFloor == null) {
            return;
        }
        engine.start(currentFloor, requestedDirection());
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

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                '}';
    }
}
