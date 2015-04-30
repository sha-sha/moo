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

    public static final Map<String, TechModule> TECH_NAME_MAP = new TreeMap<>();
    static {
        init();
    }

    private static List<ShipModule> EMPTY_MODULES = Arrays.asList(
            ShipModule.NO_COMPUTER, ShipModule.NO_ECM, ShipModule.NO_SHIELD);

    public static TechModule getModule(String moduleName) {
        return TECH_NAME_MAP.get(moduleName);
    }


    private static void init() {
        int i;
        for (i = 1; i <= 11; i++) {
            add(ShipTech.ecm(i));
            add(ShipTech.computer(i));
            if (i > 1 && i <= 7) {
                add(PlanetTech.roboticsControls(i));
            }
        }
        add(ShipTech.scanner());
        add(GlobalTech.spaceScanner(1, 5, 0, 1, false));
        add(GlobalTech.spaceScanner(2, 7, 0, 2, true));
        add(GlobalTech.spaceScanner(3, 9, 9, 3, true));
        add(GlobalTech.hyperspaceCommunications());

        String[] armors = {"Titanium", "Duralloy", "Zortium", "Andrium", "Tritanium", "Adamantium", "Neutronium"};
        for (i = 0; i < armors.length; i++) {
            add(ShipTech.armor(armors[i], i, false));
            add(ShipTech.armor(armors[i] + " II", i, true));
        }

        add(ShipTech.energyWeapon("Laser"));
        add(ShipTech.energyWeapon("Heavy Laser"));

    }

    private static void add(TechModule techModule) {
        TECH_NAME_MAP.put(techModule.getName(), techModule);
    }

    public static List<ShipModule> getEmptyModules() {
        return EMPTY_MODULES;
    }

    //public static List<Technology> getTechnologies() {
    //    return TECHNOLOGIES;
   // }

}
