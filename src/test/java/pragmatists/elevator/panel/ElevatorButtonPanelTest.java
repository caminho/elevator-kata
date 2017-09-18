package pragmatists.elevator.panel;

import org.junit.Test;
import pragmatists.elevator.Floor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ElevatorButtonPanelTest {

    private static final int ANY_BUTTON_NUMBER = 3;

    @Test
    public void shouldNotifyWhenButtonPressed() {

        ButtonListener listener = mock(ButtonListener.class);
        ButtonPanel buttonPanel = new ElevatorButtonPanel(listener);

        buttonPanel.buttonPressed(ANY_BUTTON_NUMBER);

        verify(listener).floorRequested(Floor.of(ANY_BUTTON_NUMBER));
    }
}