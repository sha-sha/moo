package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public interface IPlayerState {
    TechnologiesDb getTechnologies();
    List<Fleet> getFleets();
    List<TechModule> getModulesOfType(final Class<? extends TechModule> type);
    double getModuleCostReduction(Technology.Category category);
    double getModuleSizeReduction(Technology.Category category);

    int getTechLevel(Technology.Category category);

    int getShipFuelRange();


    public static class TechState {
        public int shipFuelRange = 0;
        public int factoriesPerPopulation = 0;
        public int planetShipSensorRange = 0;
        public int planetStarSensorRange = 0;
        public int shipSensorRange = 0;
        public int missleBaseHitPoints = 0;
        public boolean canDetectEnemyShipDestinationAndEta = false;
        public boolean canChangeShipDestinationInHyperSpace = false;
        public int groundCombatArmorDefence = 0;
        public int factoryCost = 10;
        public int industrialWaste = 90; // 90%
        public int groundCombatSuite = 0;
        public int shipAutoRepairsPercentage = 0;
        public int groundCombatShield = 0;
        public int planetaryShield = 0;
        public int pollutionPerBcEliminated = 0;
        public int terraformLevel = 0; // cost is 50BC per 10
    }

}
