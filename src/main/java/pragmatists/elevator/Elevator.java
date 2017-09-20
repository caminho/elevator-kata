package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.EngineListener;
import pragmatists.elevator.panel.ButtonListener;

public class Elevator implements
        ButtonListener, DoorListener, EngineListener {

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

    public void run() {
        door.open();
    }

    @Override
    public void floorRequested(Floor floor) {
        this.requestedFloor = floor;
        door.close();
    }

    @Override
    public void doorClosed() {
        engine.start(currentFloor, Direction.UP);
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
}
