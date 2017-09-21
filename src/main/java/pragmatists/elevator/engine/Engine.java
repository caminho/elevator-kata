package pragmatists.elevator.engine;

import pragmatists.elevator.Floor;

public interface Engine {
    void start(Floor startFloor, Direction direction);

    void stop();

    void setListener(EngineListener engineSensor);

    enum Direction {
        UP, NONE, DOWN
    }
}
