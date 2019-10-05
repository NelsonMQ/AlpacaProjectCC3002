package model.factory.IUnitFactory;

import model.units.Cleric;

public class ClericFactory implements IUnitFactory {

    @Override
    public Cleric create() {
        return new Cleric(50,1,null);
    }
}
