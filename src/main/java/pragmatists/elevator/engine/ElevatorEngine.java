package pragmatists.elevator.engine;

import pragmatists.elevator.Direction;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.event.EngineStartedEvent;
import pragmatists.elevator.event.EngineStoppedEvent;
import pragmatists.elevator.event.FloorReachedEvent;

public class ElevatorEngine implements Engine {

    private final EventLogger logger;
    private EngineListener engineSensor;
    private Floor floor;
    private Direction direction;

    public ElevatorEngine(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void start(Floor startingFloor, Direction direction) {
        logger.logEvent(new EngineStartedEvent(direction));
        this.floor = startingFloor;
        this.direction = direction;
    }

    @Override
    public void setListener(EngineListener engineSensor) {
        this.engineSensor = engineSensor;
    }

    @Override
    public void stop() {
        direction = Direction.NONE;
        logger.logEvent(new EngineStoppedEvent());
        if (engineSensor != null) {
            engineSensor.engineStopped();
        }
    }

    public void step() {
        if (notMoving()) {
            return;
        }
        goToNextFloor();
        logger.logEvent(new FloorReachedEvent(floor.level()));
        if (engineSensor != null) {
            engineSensor.floorReached(floor);
        }
    }

    private void goToNextFloor() {
        if (direction == Direction.UP) {
            floor = floor.nextFloor();
        }
        if (direction == Direction.DOWN) {
            floor = floor.previousFloor();
        }
    }

    private boolean notMoving() {
        return direction == Direction.NONE;
    }
}
