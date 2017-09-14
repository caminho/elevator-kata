package pragmatists.elevator;

public class Floor implements Comparable<Floor> {
    private final int level;

    private Floor(int level) {
        this.level = level;
    }

    static Floor ofLevel(int level) {
        return new Floor(level);
    }

    Floor nextFloor() {
        return Floor.ofLevel(level + 1);
    }

    Floor floorBelow() {
        return Floor.ofLevel(level - 1);
    }

    public boolean isLowerThan(Floor floor) {
        return compareTo(floor) < 0;
    }

    public boolean isHigherThan(Floor floor) {
        return compareTo(floor) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Floor floor = (Floor) o;

        return level == floor.level;
    }

    @Override
    public int hashCode() {
        return level;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "level=" + level +
                '}';
    }

    @Override
    public int compareTo(Floor floor) {
        return this.level - floor.level;
    }
}
