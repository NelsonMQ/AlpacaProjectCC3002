package model.factory.IUnitFactory;

import model.factory.IUnitFactory.IUnitFactory;
import model.units.Sorcerer;

public class SorcererFactory implements IUnitFactory {

    @Override
    public Sorcerer create() {
        return new Sorcerer(50,1,null);
    }
}
