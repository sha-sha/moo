package shaul.games.moo.setup;

import shaul.games.moo.model.Research.Technology;

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
        add(battleScanner());
        add(battleComputer(1));
        add(roboticsControls(2, 2));
        add(ecmJammer(1));
        add(spaceScanner("Deep Space Scanner", 1, 5, 0, 1, false));
        add(battleComputer(2));
        add(ecmJammer(2));
        add(roboticsControls(3, 3));
        add(battleComputer(3));
        add(ecmJammer(3));
        add(spaceScanner("Improved Space Scanner", 2, 7, 0, 2, true));
        add(battleComputer(4));
        add(ecmJammer(4));
        add(roboticsControls(4, 4));
        add(battleComputer(5));
        add(ecmJammer(5));
        add(spaceScanner("Advanced Space Scanner", 3, 9, 9, 3, true));
        add(battleComputer(6));
        add(ecmJammer(6));
        add(roboticsControls(5, 5));
        add(battleComputer(7));
        add(ecmJammer(7));
        //add(new Technology(CATEGORY_COMPUTERS, "Hyperspace Communications", "", GlobalTech.hyperComm()));
        add(battleComputer(8));
        add(ecmJammer(8));
        add(roboticsControls(6, 6));
        add(battleComputer(9));
        add(ecmJammer(9));
        add(battleComputer(10));
        // Oracle Interface ??  Allows all "direct fire" weapons on the equipped ship to act as if they have the
        //   "armor piercing" quality. This includes pretty much everything except missiles, torpedoes, bombs, and
        //   anything that counts as a special system.
        add(ecmJammer(10));
        add(roboticsControls(7, 7));
        // Technology Nullifier - Decreases the enemy's attack rating by 2 - 6 each time it is fired.
        add(battleComputer(11));

        add(armor("Titanium", 3, 18, 100, 600, 15));

    }}};


    public static List<String> getCategories() {
        return CATEGORIES;
    }

    public static List<Technology> getTechnologies() {
        return TECHNOLOGIES;
    }

    private static Technology ecmJammer(int level) {
        //return new Technology(CATEGORY_COMPUTERS, "ECM Jammer Mark " + level, "", ShipTech.ecm(level));
        return null;
    }

    private static Technology battleComputer(int level) {
        return null;
        //return new Technology(CATEGORY_COMPUTERS, "Battle Computer Mark " + level, "", ShipTech.computer(level));
    }

    private static Technology battleScanner() {
        return null;
        //return new Technology(CATEGORY_COMPUTERS, "Battle Scanner", "", "ShipScanner" /*ShipTech.scanner()*/);
    }

    private static Technology roboticsControls(int level, int factoryPerPopulation) {
        return null;
        //return new Technology(CATEGORY_COMPUTERS, "Robotics Controls " + level, "",
        //        PlanetTech.roboticsControls(factoryPerPopulation));
    }

    private static Technology spaceScanner(String name, int level, int planetShipSensorRange, int planetStarSensorRange,
                                           int shipSensorRange, boolean enemyShipDestinationAndEta) {
        return null;
        //return new Technology(CATEGORY_COMPUTERS, name, "",
        //        GlobalTech.spaceScanner(level, planetShipSensorRange, planetStarSensorRange,
        //                shipSensorRange, enemyShipDestinationAndEta));
    }

    private static Technology armor(String type, int small, int medium, int large, int huge, int missleBase) {
        return null;
        //return new Technology(CATEGORY_CONSTRUCTION, type + " Armor", "",
        //        ShipTech.roboticsControls(factoryPerPopulation));
    }

}
