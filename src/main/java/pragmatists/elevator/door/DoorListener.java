package pragmatists.elevator.door;

import pragmatists.elevator.door.Door.DoorState;

public interface DoorListener {

    void doorStateChanged(DoorState closed);
}
