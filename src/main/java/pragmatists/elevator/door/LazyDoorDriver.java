package pragmatists.elevator.door;

public class LazyDoorDriver implements Door {

    private final Door door;

    private boolean openDeferred;
    private boolean closeDeferred;

    public LazyDoorDriver(Door door) {
        this.door = door;
    }

    @Override
    public void close() {
        if (openDeferred) {
            throw new IllegalStateException("open already requested");
        }
        closeDeferred = true;
    }

    @Override
    public void open() {
        if (closeDeferred) {
            throw new IllegalStateException("close already requested");
        }
        openDeferred = true;
    }

    public void moveIfRequested() {
        if (openDeferred && closeDeferred) {
            throw new AssertionError("something went wrong");
        } else if (openDeferred) {
            openDeferred = false;
            door.open();
        } else if (closeDeferred) {
            closeDeferred = false;
            door.close();
        }
    }

    @Override
    public void setListener(DoorListener doorListener) {
        door.setListener(doorListener);
    }
}
