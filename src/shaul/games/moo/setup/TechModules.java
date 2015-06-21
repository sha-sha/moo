package shaul.games.moo.setup;

import shaul.games.moo.model.Planet.PlanetTechBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.*;

/**
 * Created by shaul on 4/1/15.
 */
public class TechModules {
    private static final List<ShipModule> EMPTY_MODULES = Arrays.asList(
            ShipModule.NO_COMPUTER, ShipModule.NO_ECM, ShipModule.NO_SHIELD, ShipModule.NO_WEAPON, ShipModule.NO_SPECIAL);
    private static final List<ShipModule> BASE_MODULES = new ArrayList<>();

    public static List<ShipModule> getBaseModules() {
        if (BASE_MODULES.isEmpty()) {
            BASE_MODULES.addAll(EMPTY_MODULES);
        }
        return BASE_MODULES;
    }


}
