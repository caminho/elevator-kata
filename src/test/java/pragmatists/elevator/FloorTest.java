package pragmatists.elevator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class FloorTest {

    @Test
    @Parameters({"1", "2", "3", "4", "0", "-1"})
    public void floorsWithEqualLevelAreAlsoEqual(int level) {

        assertThat(Floor.ofLevel(level)).isNotNull()
                .isEqualTo(Floor.ofLevel(level));
    }

    @Test
    @Parameters({"1, 2", "0, 2", "5, 4"})
    public void floorWithDifferentLevelsAreAlsoDifferent(int levelA, int levelB) {

        assertThat(Floor.ofLevel(levelA))
                .isNotEqualTo(Floor.ofLevel(levelB));
    }

    @Test
    @Parameters({"1, 2", "3, 4", "4, 5"})
    public void nextFloorShouldBeOneLevelHigher(int level, int nextLevel) {

        assertThat(Floor.ofLevel(level).nextFloor()).
                isNotNull().isEqualTo(Floor.ofLevel(nextLevel));
    }

    @Test
    @Parameters({"2, 1", "4, 3", "0, -1"})
    public void previousFloorShouldBeOneLevelLower(int level, int lowerLevel) {

        assertThat(Floor.ofLevel(level).floorBelow()).isNotNull()
                .isEqualTo(Floor.ofLevel(lowerLevel));
    }

    @Test
    @Parameters({"5, 4", "8, 3", "0, -1"})
    public void higherFloorShouldHaveHigherLevel(int higherLevel, int lowerLevel) {

        assertTrue(Floor.ofLevel(higherLevel)
                .isHigherThan(Floor.ofLevel(lowerLevel)));
    }

    @Test
    @Parameters({"4, 5", "3, 8", "-1, 0"})
    public void lowerFloorShouldHaveLowerLevel(int lowerLevel, int higherLevel) {

        assertTrue(Floor.ofLevel(lowerLevel)
                .isLowerThan(Floor.ofLevel(higherLevel)));
    }
}
