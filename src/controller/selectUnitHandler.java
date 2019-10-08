package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class selectUnitHandler implements PropertyChangeListener {
    private GameController controller;

    public selectUnitHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.selectUnitIn((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
