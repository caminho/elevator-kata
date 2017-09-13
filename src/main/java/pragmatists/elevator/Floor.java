package pragmatists.elevator;

public class Floor {
    private final int level;

    public Floor(int level) {
        this.level = level;
    }

    public static Floor ofLevel(int level) {
        return new Floor(level);
    }

    public boolean lowerThan(Floor otherFloor) {
        return level < otherFloor.level;
    }

    public boolean higherThan(Floor otherFloor) {
        return level > otherFloor.level;
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
