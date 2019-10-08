package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class selectItemHandler implements PropertyChangeListener {
    private GameController controller;

    public selectItemHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        controller.selectItem((int)evt.getNewValue());
    }
}
