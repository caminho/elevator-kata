package pragmatists.elevator;

public class Elevator {

    private final Door door;
    private final Engine engine;

    private Floor requestedFloor;
    private Floor currentFloor = new Floor(0);

    public Elevator(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
    }

    public Floor currentFloor() {
        return currentFloor;
    }

    public void floorRequested(Floor floor) {
        if (!floor.equals(currentFloor())) {
            requestedFloor = floor;
            door.close();
        }
    }

    public void doorClosed() {
        if (requestedFloor != null) {
            if (requestedFloor.lowerThan(currentFloor())) {
                engine.start(Engine.Direction.DOWN);
            }
            if (requestedFloor.higherThan(currentFloor())) {
                engine.start(Engine.Direction.UP);
            }
        }
    }

    public void floorReached(Floor currentFloor) {
        this.currentFloor = currentFloor;
        if (requestedFloor.equals(currentFloor)) {
            engine.stop();
        }
    }

    public void engineStopped() {
        door.open();
    }

    public void doorOpened() {

    }
}
