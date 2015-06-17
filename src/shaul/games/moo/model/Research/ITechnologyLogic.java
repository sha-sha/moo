package shaul.games.moo.model.Research;

import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipDesign;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public interface ITechnologyLogic {

    double getCostReduction(int deltaTechLevel);

    Technology getTechnology(String name);
    Technology getTechnologyOfTechModule(String techModuleName);
    List<ShipModule> getBaseShipModules();
    List<Technology> getTechnologies(Technology.Category category);
}
