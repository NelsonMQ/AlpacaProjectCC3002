package model.factory.IUnitFactory;

import model.items.IEquipableItem;
import model.units.Alpaca;

import java.util.ArrayList;
import java.util.List;

public class AlpacaFactory implements IUnitFactory {

    @Override
    public Alpaca create() {
        IEquipableItem[] items = new IEquipableItem[0];
        return new Alpaca(50,1,null, items);
    }
}
