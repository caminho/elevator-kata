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

    public ElevatorEngine(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void start(Floor startingFloor, Direction direction) {
        logger.logEvent(new EngineStartedEvent(direction));

        // moving to the next level
        Floor nextFloor = startingFloor.nextFloor();
        logger.logEvent(new FloorReachedEvent(nextFloor.level()));
        if (engineSensor != null) {
            engineSensor.floorReached(nextFloor);
        }
    }

    @Override
    public void setListener(EngineListener engineSensor) {
        this.engineSensor = engineSensor;
    }

    @Override
    public void stop() {
        logger.logEvent(new EngineStoppedEvent());
        if (engineSensor != null) {
            engineSensor.engineStopped();
        }
    }
}
