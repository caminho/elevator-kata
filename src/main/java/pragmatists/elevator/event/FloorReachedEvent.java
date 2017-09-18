package pragmatists.elevator.event;

public class FloorReachedEvent extends ElevatorEvent {

    private final int level;

    public FloorReachedEvent(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloorReachedEvent that = (FloorReachedEvent) o;

        return level == that.level;
    }

    @Override
    public int hashCode() {
        return level;
    }
}
