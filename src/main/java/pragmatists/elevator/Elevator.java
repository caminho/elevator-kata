package pragmatists.elevator;

public class Elevator {

    private final Door door;
    private final Engine engine;
    private Floor requestedFloor;

    public Elevator(Door door, Engine engine) {

        this.door = door;
        this.engine = engine;
    }

    public Floor currentFloor() {
        return new Floor(0);
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

    public void reachedFloor(Floor currentFloor) {
        if (requestedFloor.equals(currentFloor)) {
            engine.stop();
        }
    }
}
