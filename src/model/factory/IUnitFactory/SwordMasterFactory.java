package model.factory.IUnitFactory;

import model.factory.IUnitFactory.IUnitFactory;
import model.units.SwordMaster;

public class SwordMasterFactory implements IUnitFactory {

    @Override
    public SwordMaster create() {
        return new SwordMaster(50,1,null);
    }
}
