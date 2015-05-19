package shaul.games.moo.setup;

import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Utils;

import java.util.*;

/**
 * Created by Shaul on 3/1/2015.
 */
public final class TechnologyTree {

    public static final String CATEGORY_COMPUTERS = "Computers";
    public static final String CATEGORY_CONSTRUCTION = "Construction";
    public static final String CATEGORY_FORCE_FIELDS = "Force Fields";
    public static final String CATEGORY_PLANETOLOGY = "Planetology";
    public static final String CATEGORY_PROPULSION = "Propulsion";
    public static final String CATEGORY_WEAPONS = "Weapons";

    private static final List<String> CATEGORIES = Arrays.asList(
            CATEGORY_COMPUTERS,
            CATEGORY_CONSTRUCTION,
            CATEGORY_FORCE_FIELDS,
            CATEGORY_PLANETOLOGY,
            CATEGORY_PROPULSION,
            CATEGORY_WEAPONS);

    public static final String BONUS_CATEGORY_PRODUCTION = "Computers";


    private static final List<Technology> TECHNOLOGIES = new ArrayList<Technology>() {{{
        add(new BattleScanner(1));
        add(new BattleComputer(1, 1));
        add(roboticsControls(1, 2, 2));
        add(new EcmJammer(2, 1));
        add(computerTech(3, "Deep Space Scanner", "Space Scanner 1"));
        add(new BattleComputer(4, 2));
        add(new EcmJammer(5, 2));
        add(roboticsControls(6, 3, 3));
        add(new BattleComputer(7, 3));
        add(new EcmJammer(8, 3));
        add(computerTech(9, "Improved Space Scanner", "Space Scanner 2"));
        add(new BattleComputer(10, 4));
        add(new EcmJammer(11, 4));
        add(roboticsControls(12, 4, 4));
        add(new BattleComputer(13, 5));
        add(new EcmJammer(14, 5));
        add(computerTech(15, "Advanced Space Scanner", "Space Scanner 3"));
        add(new BattleComputer(16, 6));
        add(new EcmJammer(17, 6));
        add(roboticsControls(18, 5, 5));
        add(new BattleComputer(19, 7));
        add(new EcmJammer(20, 7));
        add(computerTech(21, "Hyperspace Communications", "Hyperspace Communications"));
        add(new BattleComputer(22, 8));
        add(new EcmJammer(23, 8));
        add(roboticsControls(24, 6, 6));
        add(new BattleComputer(25, 9));
        add(new EcmJammer(26, 9));
        add(new BattleComputer(27,10));
        // Oracle Interface ??  Allows all "direct fire" weapons on the equipped ship to act as if they have the
        //   "armor piercing" quality. This includes pretty much everything except missiles, torpedoes, bombs, and
        //   anything that counts as a special system.
        add(new EcmJammer(29, 10));
        add(roboticsControls(30, 7, 7));
        // Technology Nullifier - Decreases the enemy's attack rating by 2 - 6 each time it is fired.
        add(new BattleComputer(31, 11));

        add(constructionTech(1, "Titanium", "Titanium", "Titanium II"));
        add(new DeflectorShield(1, 1));
        add(new DeflectorShield(4, 2));
        add(weaponTech(1, "Laser", "Laser", "Heavy Laser"));
        add(propulsionTech(1, "Nuclear Engine", "Nuclear Engine"));

    }}};
    private static Map<String, Technology> TECHNOLOGIES_MAP = null;
    private static Map<String, Technology> MODULE_TECHNOLOGIES_MAP = null;

    public static List<String> getCategories() {
        return CATEGORIES;
    }

    public static List<Technology> getTechnologies() {
        return TECHNOLOGIES;
    }

    public static Map<String, Technology> getTechnologiesMap() {
        if (TECHNOLOGIES_MAP == null) {
            TECHNOLOGIES_MAP = new HashMap<>();
            for (Technology t : TECHNOLOGIES) {
                TECHNOLOGIES_MAP.put(t.getName(), t);
            }
        }
        return TECHNOLOGIES_MAP;
    }

    public static Map<String, Technology> getModuleToTechnologyMap() {
        if (MODULE_TECHNOLOGIES_MAP == null) {
            MODULE_TECHNOLOGIES_MAP = new HashMap<>();
            for (Technology t : TECHNOLOGIES) {
                for (String module : t.getModules()) {
                    MODULE_TECHNOLOGIES_MAP.put(module, t);
                }
            }
        }
        return MODULE_TECHNOLOGIES_MAP;
    }

    private static Technology roboticsControls(int techLevel, int level, int factoryPerPopulation) {
        return new Technology(CATEGORY_COMPUTERS, techLevel, "Robotics Controls", "Robotics Controls " + level);
    }

    private static Technology computerTech(int techLevel, String name, String module) {
        return new Technology(CATEGORY_COMPUTERS, techLevel, name, module);
    }

    private static Technology constructionTech(int techLevel, String name, String... module) {
        return new Technology(CATEGORY_CONSTRUCTION, techLevel, name, module);
    }

    private static Technology forceFieldsTech(int techLevel, String name, String... module) {
        return new Technology(CATEGORY_FORCE_FIELDS, techLevel, name, module);
    }

    private static Technology weaponTech(int techLevel, String name, String... module) {
        return new Technology(CATEGORY_WEAPONS, techLevel, name, module);
    }

    private static Technology propulsionTech(int techLevel, String name, String... module) {
        return new Technology(CATEGORY_PROPULSION, techLevel, name, module);
    }

    private static Technology spaceScannerzz(int techLevel, String name, int level, int planetShipSensorRange, int planetStarSensorRange,
                                           int shipSensorRange, boolean enemyShipDestinationAndEta) {
        return new Technology(CATEGORY_COMPUTERS, techLevel, "Space Scanner",
                "Planet Sensor " + level,
                "Ship Sensor " + level);

        //return new Technology(CATEGORY_COMPUTERS, name, "",
        //        GlobalTech.spaceScanner(level, planetShipSensorRange, planetStarSensorRange,
        //                shipSensorRange, enemyShipDestinationAndEta));
    }

    private static Technology armor(int techLevel, String type, int small, int medium, int large, int huge, int missleBase) {
        return null;
        //return new Technology(CATEGORY_CONSTRUCTION, type + " Armor", "",
        //        ShipTech.roboticsControls(factoryPerPopulation));
    }


    private static class BattleScanner extends Technology.Computer {
        public BattleScanner(int techLevel) {
            super(techLevel, "Battle Scanner", new ShipTech.BattleScanner());
        }
    }

    private static class BattleComputer extends Technology.Computer {
        public BattleComputer(int techLevel, int level) {
            super(techLevel, "Battle Computer Mark " + level, new ShipTech.BattleComputer(level));
        }
    }

    private static class EcmJammer extends Technology.Computer {
        public EcmJammer(int techLevel, int level) {
            super(techLevel, "ECM Jammer Mark " + level, new ShipTech.Ecm(level));
        }
    }

    private static class DeflectorShield extends Technology.ForceField {
        public DeflectorShield(int techLevel, int level) {
            super(techLevel, "Class " + Utils.toRomanNumber(level) + " Deflector Shield", new ShipTech.Shield(level));
        }
    }
}
