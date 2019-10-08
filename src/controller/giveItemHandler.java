package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class giveItemHandler implements PropertyChangeListener {
    private GameController controller;

    public giveItemHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.giveItemTo((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
