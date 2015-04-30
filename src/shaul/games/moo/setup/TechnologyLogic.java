package shaul.games.moo.setup;

import shaul.games.moo.model.Research.ITechnologyLogic;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.HullType;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

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
    private static final int[] HULL_SIZES = {120, 300, 700, 1200};


    public TechnologyLogic() {
    }

    @Override
    public double getCostReduction(int deltaTechLevel) {
        return 1.0 - (deltaTechLevel / 10 * 0.1);
    }

    @Override
    public List<HullType> getAvailableHullTypes() {
        return HULL_TYPES;
    }

    @Override
    public int getHullTotalSpace(int hullSize) {
        Utils.check(hullSize >= 1 && hullSize <= HULL_SIZES.length);
        return HULL_SIZES[hullSize - 1];
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
    public Technology getTechnologyOfTechModule(String techModuleName) {
        Technology tech = TechnologyTree.getModuleToTechnologyMap().get(techModuleName);
        return Utils.checkNotNull(tech);
    }

    @Override
    public List<ShipModule> getEmptyShipModules() {
        return TechModules.getEmptyModules();
    }

    @Override
    public ShipModule getLowestEngine() {
        return null;
    }

    @Override
    public double getModuleCostReduction(String module, TechnologiesDb playerTechs) {
        Utils.assertNotNull(module);
        Utils.assertNotNull(playerTechs);
        Technology tech = getTechnologyOfTechModule(module);
        Utils.check("Module " + module + " has no tech!", tech != null);
        int moduleTechLevel = tech.getTechLevel();
        String moduleCategory = tech.getCategory();
        int currentTechLevel = playerTechs.getTechLevel(moduleCategory);
        return getCostReduction(currentTechLevel - moduleTechLevel);
    }
}
