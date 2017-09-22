package pragmatists.elevator;

public class Floor implements Comparable<Floor> {
    private final int level;

    public Floor(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }

    public static Floor ofLevel(int level) {
        return new Floor(level);
    }

    public Floor nextFloor() {
        return Floor.ofLevel(level + 1);
    }

    public Floor previousFloor() {
        return Floor.ofLevel(level - 1);
    }

    public boolean isGreaterThan(Floor floor) {
        return compareTo(floor) > 0;
    }

    public boolean isLowerThan(Floor floor) {
        return compareTo(floor) < 0;
    }

    @Override
    public int compareTo(Floor floor) {
        return Integer.compare(level, floor.level);
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
}
