package pragmatists.elevator;


import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Direction;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.EngineListener;

public class Elevator implements DoorListener, EngineListener {

    private final Door door;
    private final Engine engine;

    private Floor requestedFloor;
    private Floor currentFloor = Floor.ofLevel(0);

    public Elevator(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
    }

    public void floorRequested(Floor requestedFloor) {
        if (!requestedCurrentFloor(requestedFloor)) {
            this.requestedFloor = requestedFloor;
            door.close();
        }
    }

    private boolean requestedCurrentFloor(Floor requestedFloor) {
        return currentFloor.equals(requestedFloor);
    }

    @Override
    public void doorClosed() {
        if (requestedFloor.isLowerThan(currentFloor)) {
            engine.start(currentFloor, Direction.DOWN);
        }
        engine.start(currentFloor, Direction.UP);
    }

    @Override
    public void floorReached(Floor floor) {
        if (reachedRequestedFloor(floor)) {
            engine.stop();
        }
    }

    @Override
    public void engineStopped() {
        door.open();
    }

    private boolean reachedRequestedFloor(Floor floor) {
        return floor.equals(requestedFloor);
    }

}

