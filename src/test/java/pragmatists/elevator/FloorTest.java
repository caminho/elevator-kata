package pragmatists.elevator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class FloorTest {

    private static final int SOME_LEVEL = 4;

    @Test
    public void shouldCreateFloorOfGivenLevel() {

        Floor floor = Floor.ofLevel(SOME_LEVEL);

        assertThat(floor).isEqualTo(new Floor(SOME_LEVEL));
    }

    @Test
    public void shouldDefineNextFloor() {

        Floor floor = Floor.ofLevel(SOME_LEVEL);

        assertThat(floor.nextFloor())
                .isEqualTo(Floor.ofLevel(SOME_LEVEL + 1));
    }
}
