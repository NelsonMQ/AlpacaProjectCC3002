package model.factory.IUnitFactory;

import model.units.Fighter;

public class FighterFactory implements IUnitFactory {

    @Override
    public Fighter create() {
        return new Fighter(50,1,null);
    }
}
