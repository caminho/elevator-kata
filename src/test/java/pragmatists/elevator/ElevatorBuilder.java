package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.engine.Engine;

final class ElevatorBuilder {
    private Door door;
    private Engine engine;

    private ElevatorBuilder(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
    }

    static ElevatorBuilder anElevator(Door door, Engine engine) {
        return new ElevatorBuilder(door, engine);
    }

    Elevator build() {
        return new Elevator(door, engine);
    }
}
