package pragmatists.elevator;

public interface Engine {

    enum Direction {UP, DOWN}

    void start(Direction direction);

    void stop();
}
