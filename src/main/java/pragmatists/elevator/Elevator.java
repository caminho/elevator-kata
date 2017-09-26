package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.Door.DoorState;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.Engine.Direction;
import pragmatists.elevator.engine.EngineListener;
import pragmatists.elevator.panel.ButtonListener;

public class Elevator implements
        ButtonListener, DoorListener, EngineListener {

    private enum ElevatorState {WAITING, GOING_UP, GOING_DOWN}
    private ElevatorState state = ElevatorState.WAITING;

    private final Door door;
    private final Engine engine;

    private Floor currentFloor = Floor.ofLevel(0);
    private Floor requestedFloor;


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
        if (state == ElevatorState.WAITING) {
            requestedFloor = floor;
            if (floor.isGreaterThan(currentFloor)) {
                state = ElevatorState.GOING_UP;
            }
            if (floor.isLowerThan(currentFloor)) {
                state = ElevatorState.GOING_DOWN;
            }
            door.close();
        }
    }

    @Override
    public void doorStateChanged(DoorState doorState) {
        if (doorState == DoorState.OPENED) {

            state = ElevatorState.WAITING;
            requestedFloor = null;

        } else if (doorState == DoorState.CLOSED) {

            if (requestedFloor == null) {
                return;
            }

            if (state == ElevatorState.GOING_UP) {
                engine.start(Direction.UP);
            } else if (state == ElevatorState.GOING_DOWN) {
                engine.start(Direction.DOWN);
            }
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
        return currentFloor.equals(requestedFloor);
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
}
