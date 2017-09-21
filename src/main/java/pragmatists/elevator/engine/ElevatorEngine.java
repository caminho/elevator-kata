package pragmatists.elevator.engine;

import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.event.EngineStartedEvent;
import pragmatists.elevator.event.EngineStoppedEvent;
import pragmatists.elevator.event.FloorReachedEvent;

public class ElevatorEngine implements Engine {

    private final EventLogger logger;
    private EngineListener engineSensor;

    private Floor floor;
    private Direction direction = Direction.NONE;

    public ElevatorEngine(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void start(Floor startingFloor, Direction direction) {
        if (elevatorIsMoving()) {
            throw new IllegalStateException("elevator is moving");
        }
        this.floor = startingFloor;
        this.direction = direction;
        notifyEngineStarted();
    }

    @Override
    public void setListener(EngineListener engineSensor) {
        this.engineSensor = engineSensor;
    }

    @Override
    public void stop() {
        direction = Direction.NONE;
        notifyElevatorStopped();
    }

    public void step() {
        if (elevatorIsMoving()) {
            goToNextFloor();
            notifyElevatorMoved();
        }
    }

    private void notifyEngineStarted() {
        logger.logEvent(new EngineStartedEvent(direction));
    }

    private void notifyElevatorStopped() {
        logger.logEvent(new EngineStoppedEvent());
        if (engineSensor != null) {
            engineSensor.engineStopped();
        }
    }

    private void notifyElevatorMoved() {
        logger.logEvent(new FloorReachedEvent(floor.level()));
        if (engineSensor != null) {
            engineSensor.floorReached(floor);
        }
    }

    private void goToNextFloor() {
        switch (direction) {
            case UP:
                floor = floor.nextFloor();
                break;
            case DOWN:
                floor = floor.previousFloor();
                break;
        }
    }

    private boolean elevatorIsMoving() {
        return direction != Direction.NONE;
    }
}
