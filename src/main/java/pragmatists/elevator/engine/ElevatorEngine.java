package pragmatists.elevator.engine;

import pragmatists.elevator.EventLogger;
import pragmatists.elevator.Floor;
import pragmatists.elevator.event.EngineStartedEvent;
import pragmatists.elevator.event.EngineStoppedEvent;
import pragmatists.elevator.event.FloorReachedEvent;

public class ElevatorEngine implements Engine {

    private final EventLogger logger;
    private EngineListener engineSensor;

    private Floor currentFloor;
    private Direction direction = Direction.NONE;

    public ElevatorEngine(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void setListener(EngineListener engineSensor) {
        this.engineSensor = engineSensor;
    }

    @Override
    public void reset(Floor floor) {
        this.currentFloor = floor;
    }

    @Override
    public void start(Direction direction) {
        if (elevatorIsMoving()) {
            throw new IllegalStateException("elevator is moving");
        }
        this.direction = direction;
        notifyEngineStarted();
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
        logger.logEvent(new FloorReachedEvent(currentFloor.level()));
        if (engineSensor != null) {
            engineSensor.floorReached(currentFloor);
        }
    }

    private void goToNextFloor() {
        switch (direction) {
            case UP:
                currentFloor = currentFloor.nextFloor();
                break;
            case DOWN:
                currentFloor = currentFloor.previousFloor();
                break;
        }
    }

    private boolean elevatorIsMoving() {
        return direction != Direction.NONE;
    }
}
