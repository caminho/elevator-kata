package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.engine.Engine;

final class ElevatorBuilder {
    private Door door;
    private Engine engine;
    private Floor startingFloor = new Floor(0);

    private ElevatorBuilder(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
    }

    static ElevatorBuilder anElevator(Door door, Engine engine) {
        return new ElevatorBuilder(door, engine);
    }

    ElevatorBuilder startingAt(int startingLevel) {
        this.startingFloor = new Floor(startingLevel);
        return this;
    }

    Elevator build() {
        return new Elevator(door, engine, startingFloor);
    }
}
