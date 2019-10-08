package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class moveUnitHandler implements PropertyChangeListener {
    private GameController controller;

    public moveUnitHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.moveSelectedUnitTo((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
