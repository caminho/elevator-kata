package pragmatists.elevator.panel;

import pragmatists.elevator.Floor;

public class ElevatorButtonPanel implements ButtonPanel {
    private final ButtonListener buttonListener;

    public ElevatorButtonPanel(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    @Override
    public void buttonPressed(int buttonNumber) {
        buttonListener.floorRequested(Floor.ofLevel(buttonNumber));
    }
}
