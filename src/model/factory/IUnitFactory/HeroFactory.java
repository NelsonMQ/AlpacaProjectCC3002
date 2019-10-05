package model.factory.IUnitFactory;

import model.units.Hero;

public class HeroFactory implements IUnitFactory {

    @Override
    public Hero create() {
        return new Hero(50,1,null);
    }
}
