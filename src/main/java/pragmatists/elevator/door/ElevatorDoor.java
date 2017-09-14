package pragmatists.elevator.door;

public class ElevatorDoor implements Door {

    private final DoorListener listener;

    public ElevatorDoor(DoorListener listener) {
        this.listener = listener;
    }

    @Override
    public void close() {
        listener.doorClosed();
    }

    @Override
    public void open() {

    }
}
