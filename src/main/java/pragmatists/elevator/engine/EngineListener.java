package pragmatists.elevator.engine;

import pragmatists.elevator.Floor;

public interface EngineListener {
    void floorReached(Floor floor);

    void engineStopped();
}
