package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class useItemHandler implements PropertyChangeListener {
    private GameController controller;

    public useItemHandler(GameController controller) {
        this.controller =  controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.useItemOn((int)evt.getOldValue(),(int)evt.getNewValue());
    }
}
