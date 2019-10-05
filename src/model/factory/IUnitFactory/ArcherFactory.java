package model.factory.IUnitFactory;

import model.units.Archer;

public class ArcherFactory implements IUnitFactory {

    @Override
    public Archer create() {
        return new Archer(50,1,null);
    }
}
