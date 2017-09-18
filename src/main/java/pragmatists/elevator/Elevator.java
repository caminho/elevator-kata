package pragmatists.elevator;

import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.door.Door;
import pragmatists.elevator.panel.ButtonListener;

public class Elevator implements ButtonListener, DoorListener {

    private final Door door;
    private final Engine engine;

    public Elevator(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
        door.setListener(this);
    }

    public void run() {
        door.open();
    }

    @Override
    public void floorRequested(Floor floor) {
        door.close();
    }

    public void doorClosed() {
        engine.start(Direction.UP);
    }
}
