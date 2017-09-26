package pragmatists.elevator.engine;

import pragmatists.elevator.Floor;

public interface Engine {

    void reset(Floor floor);

    void setListener(EngineListener engineSensor);

    void start(Direction direction);

    void stop();

    enum Direction {
        UP, NONE, DOWN
    }
}
