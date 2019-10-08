package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class equipItemHandler implements PropertyChangeListener {
    private GameController controller;

    public equipItemHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.equipItem((int)evt.getNewValue());
    }
}
