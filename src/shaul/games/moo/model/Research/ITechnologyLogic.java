package shaul.games.moo.model.Research;

import shaul.games.moo.model.Ship.HullType;
import shaul.games.moo.model.Ship.ShipDesign;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public interface ITechnologyLogic {

    double getCostReduction(int deltaTechLevel);

    List<HullType> getAvailableHullTypes();

    int getHullTotalSpace(int hullSize);

    Technology getTechnology(String name);
    ShipModule getShipModule(String name);

    ShipModule getLowestArmor();
    ShipModule getLowestEngine();

    double getModuleCostReduction(String module, TechnologiesDb playerTechs);
}
