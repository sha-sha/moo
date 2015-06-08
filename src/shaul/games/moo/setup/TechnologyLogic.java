package shaul.games.moo.setup;

import shaul.games.moo.model.Research.ITechnologyLogic;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public class TechnologyLogic implements ITechnologyLogic {

    //private static final int[] HULL_SPACE = {40, 200, 1000, 5000 };


    public TechnologyLogic() {
    }

    @Override
    public double getCostReduction(int deltaTechLevel) {
        return 1.0 - (deltaTechLevel / 10 * 0.1);
    }

    @Override
    public Technology getTechnology(String name) {
        return TechnologyTree.getTechnologiesMap().get(name);
    }

    @Override
    public Technology getTechnologyOfTechModule(String techModuleName) {
        Technology tech = TechnologyTree.getModuleToTechnologyMap().get(techModuleName);
        return Utils.checkNotNull(tech);
    }

    @Override
    public List<ShipModule> getBaseShipModules() {
        return TechModules.getBaseModules();
    }
/*
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
*/}
