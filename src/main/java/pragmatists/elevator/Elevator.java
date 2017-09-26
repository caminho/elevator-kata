package pragmatists.elevator;

import pragmatists.elevator.door.Door;
import pragmatists.elevator.door.Door.DoorState;
import pragmatists.elevator.door.DoorListener;
import pragmatists.elevator.engine.Engine;
import pragmatists.elevator.engine.Engine.Direction;
import pragmatists.elevator.engine.EngineListener;
import pragmatists.elevator.panel.ButtonListener;

public class Elevator implements
        ButtonListener, DoorListener, EngineListener {

    private final Door door;
    private final Engine engine;

    private Floor currentFloor = Floor.ofLevel(0);


    public Elevator(Door door, Engine engine) {
        this.door = door;
        this.engine = engine;
        this.door.setListener(this);
        this.engine.setListener(this);
    }

    public Elevator(Door door, Engine engine, Floor startingFloor) {
        this(door, engine);
        this.currentFloor = startingFloor;
    }

    public void run() {
        engine.reset(currentFloor);
        door.open();
    }

    @Override
    public void doorStateChanged(DoorState closed) {
        engine.start(Direction.UP);
    }

    @Override
    public void floorRequested(Floor floor) {
        door.close();
    }

    @Override
    public void floorReached(Floor floor) {
        engine.stop();
    }

    @Override
    public void engineStopped() {
        door.open();
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "currentFloor=" + currentFloor +
                '}';
    }
}
