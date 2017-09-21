package pragmatists.elevator.door;

public interface Door {
    void close();

    void open();

    void setListener(DoorListener doorListener);

    enum DoorState {
        CLOSED
    }

}
