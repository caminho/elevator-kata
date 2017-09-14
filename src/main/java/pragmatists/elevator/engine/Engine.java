package pragmatists.elevator.engine;

import pragmatists.elevator.Floor;

public interface Engine {
    void start(Floor floor, Direction direction);

    void stop();
}
