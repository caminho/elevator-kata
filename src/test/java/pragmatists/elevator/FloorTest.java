package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FloorTest {

    private static final int SOME_LEVEL = 4;

    @Test
    public void shouldCreateFloorOfGivenLevel() {

        Floor floor = Floor.ofLevel(SOME_LEVEL);

        assertThat(floor).isEqualTo(new Floor(SOME_LEVEL));
    }

    @Test
    public void shouldReturnNextFloor() {

        Floor floor = Floor.ofLevel(SOME_LEVEL);

        assertThat(floor.nextFloor())
                .isEqualTo(Floor.ofLevel(SOME_LEVEL + 1));
    }

    @Test
    public void shouldReturnPreviousFloor() {

        Floor floor = Floor.ofLevel(SOME_LEVEL);

        assertThat(floor.previousFloor())
                .isEqualTo(Floor.ofLevel(SOME_LEVEL - 1));
    }

    @Test
    @Parameters({"1, 2", "0, 5", "-5, -2"})
    public void shouldCheckIfFloorIsGreaterThanGiven(int lowerLevel, int greaterLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor greaterFloor = Floor.ofLevel(greaterLevel);

        assertThat(greaterFloor.isGreaterThan(lowerFloor)).isTrue();
        assertThat(lowerFloor.isGreaterThan(greaterFloor)).isFalse();
    }

    @Test
    @Parameters({"1, 2", "0, 5", "-5, -2"})
    public void shouldCheckIfFloorIsLowerThanGiven(int lowerLevel, int greaterLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor greaterFloor = Floor.ofLevel(greaterLevel);

        assertThat(lowerFloor.isLowerThan(greaterFloor)).isTrue();
        assertThat(greaterFloor.isLowerThan(lowerFloor)).isFalse();
    }
}
