package shaul.games.moo.setup;

import shaul.games.moo.model.Research.ITechnologyLogic;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.HullType;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public class TechnologyLogic implements ITechnologyLogic {

    private static final List<HullType> HULL_TYPES = Arrays.asList(
            new HullType(1, "Tiny"),
            new HullType(2, "Small"),
            new HullType(3, "Big"),
            new HullType(4, "Huge"));

    public TechnologyLogic() {
    }

    @Override
    public float getCostReduction() {
        return 0;
    }

    @Override
    public List<HullType> getAvailableHullTypes() {
        return HULL_TYPES;
    }

    @Override
    public int getHullTotalSpace(int hullSize) {
        return 0;
    }

    @Override
    public Technology getTechnology(String name) {
        return TechnologyTree.getTechnologiesMap().get(name);
    }

    @Override
    public ShipModule getShipModule(String name) {
        TechModule module = TechModules.getModule(name);
        if (module == null || module.getType() != TechModule.Type.Ship) {
            return null;
        }
        return (ShipModule)module;
    }

    @Override
    public ShipModule getLowestArmor() {
        return null;
    }

    @Override
    public ShipModule getLowestEngine() {
        return null;
    }
}
