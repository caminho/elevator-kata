package pragmatists.elevator.engine;

import pragmatists.elevator.Direction;
import pragmatists.elevator.Floor;

public interface Engine {
    void start(Floor of, Direction direction);

    void stop();

    void setListener(EngineListener engineSensor);
}
