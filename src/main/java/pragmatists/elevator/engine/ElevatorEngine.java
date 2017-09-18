package pragmatists.elevator.engine;

import pragmatists.elevator.Direction;
import pragmatists.elevator.EventLogger;
import pragmatists.elevator.event.EngineStartedEvent;

public class ElevatorEngine implements Engine {

    private final EventLogger logger;

    public ElevatorEngine(EventLogger logger) {
        this.logger = logger;
    }

    @Override
    public void start(Direction direction) {
        logger.logEvent(new EngineStartedEvent(direction));
    }
}
