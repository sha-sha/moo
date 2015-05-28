package shaul.games.moo.model.Research;

import shaul.games.moo.model.Ship.ShipDesign;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public interface ITechnologyLogic {

    double getCostReduction(int deltaTechLevel);

    int getHullTotalSpace(ShipModule.HullType hullSize);

    Technology getTechnology(String name);
    ShipModule getShipModule(String name);
    Technology getTechnologyOfTechModule(String techModuleName);
    List<ShipModule> getBaseShipModules();

    ShipModule getLowestEngine();

    double getModuleCostReduction(String module, TechnologiesDb playerTechs);
}
