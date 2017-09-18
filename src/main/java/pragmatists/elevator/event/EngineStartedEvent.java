package pragmatists.elevator.event;

import pragmatists.elevator.Direction;

public class EngineStartedEvent extends ElevatorEvent {

    private Direction direction;

    public EngineStartedEvent(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EngineStartedEvent that = (EngineStartedEvent) o;

        return direction == that.direction;
    }

    @Override
    public int hashCode() {
        return direction != null ? direction.hashCode() : 0;
    }
}
