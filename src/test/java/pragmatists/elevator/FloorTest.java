package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class FloorTest {

    private static Object[][] lowerAndHigherFloorLevels() {
        return new Object[][] {
                {2, 5}, {-1, 0}, {0,10}
        };
    }

    @Test
    @Parameters(method = "lowerAndHigherFloorLevels")
    public void floorWithLowerLevelShouldBeLower(int lowerLevel, int higherLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor higherFloor = Floor.ofLevel(higherLevel);

        assertThat(lowerFloor.lowerThan(higherFloor)).isTrue();
    }

    @Test
    @Parameters(method = "lowerAndHigherFloorLevels")
    public void floorWithLowerLevelShouldNotBeHigher(int lowerLevel, int higherLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor higherFloor = Floor.ofLevel(higherLevel);

        assertThat(lowerFloor.higherThan(higherFloor)).isFalse();
    }

    @Test
    @Parameters(method = "lowerAndHigherFloorLevels")
    public void floorWithHigherLevelShouldBeHigher(int lowerLevel, int higherLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor higherFloor = Floor.ofLevel(higherLevel);

        assertThat(higherFloor.higherThan(lowerFloor)).isTrue();
    }

    @Test
    @Parameters(method = "lowerAndHigherFloorLevels")
    public void floorWithHigherLevelShouldNotBeLower(int lowerLevel, int higherLevel) {

        Floor lowerFloor = Floor.ofLevel(lowerLevel);
        Floor higherFloor = Floor.ofLevel(higherLevel);

        assertThat(higherFloor.lowerThan(lowerFloor)).isFalse();
    }
}
