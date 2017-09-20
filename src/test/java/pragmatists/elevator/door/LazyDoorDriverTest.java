package pragmatists.elevator.door;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LazyDoorDriverTest {

    private final Door door = mock(Door.class);
    private final LazyDoorDriver lazyDoorDriver = new LazyDoorDriver(door);

    @Test
    public void shouldNotOpenDoorImmediately() {

        lazyDoorDriver.open();

        verifyZeroInteractions(door);
    }

    @Test
    public void shouldNotCloseDoorImmediately() {

        lazyDoorDriver.close();

        verifyZeroInteractions(door);
    }

    @Test
    public void shouldNotOpenDoorIfNotRequested() {

        lazyDoorDriver.moveIfRequested();

        verifyZeroInteractions(door);
    }

    @Test
    public void shouldOpenDoorIfRequested() {

        lazyDoorDriver.open();
        lazyDoorDriver.moveIfRequested();

        verify(door).open();
    }

    @Test
    public void shouldCloseDoorIfRequested() {

        lazyDoorDriver.close();
        lazyDoorDriver.moveIfRequested();

        verify(door).close();
    }

    @Test
    public void shouldOnlyOnceOpenDoor() {

        lazyDoorDriver.open();
        lazyDoorDriver.moveIfRequested();
        lazyDoorDriver.moveIfRequested();

        verify(door).open();
    }


    @Test
    public void shouldOnlyOnceCloseDoor() {

        lazyDoorDriver.close();
        lazyDoorDriver.moveIfRequested();
        lazyDoorDriver.moveIfRequested();

        verify(door).close();
    }

    @Test
    public void shouldOnlyOnceOpenDoorIfOpenIvokedManyTimes() {

        lazyDoorDriver.open();
        lazyDoorDriver.open();
        lazyDoorDriver.moveIfRequested();

        verify(door).open();
    }

    @Test
    public void shouldOnlyOnceCloseDoorIfCloseIvokedManyTimes() {

        lazyDoorDriver.close();
        lazyDoorDriver.close();
        lazyDoorDriver.moveIfRequested();

        verify(door).close();
    }

    @Test
    public void shouldThrowISEIfOpenedAndThenClosedWithoutMoving() {

        lazyDoorDriver.open();

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(lazyDoorDriver::close);
    }

    @Test
    public void shouldThrowISEIfClosedAndThenOpenedWithoutMoving() {

        lazyDoorDriver.close();

        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(lazyDoorDriver::open);
    }
}
