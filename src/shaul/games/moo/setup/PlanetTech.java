package shaul.games.moo.setup;

import shaul.games.moo.model.Planet.PlanetTechBonus;
import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.PlayerBonus;
import shaul.games.moo.model.Research.TechModule;

/**
 * Created by Shaul on 3/1/2015.
 */
public class PlanetTech {

    public static TechModule roboticsControls(int level) {
        return new PlanetTechBonus("Robotics Controls " + level,
                new PlanetTechBonus.Bonus.Builder()
                        .maxFactoriesPerPopulation(level)
                        .build());
    }

    public static class FactoriesPerPopulation extends TechModule {

        public FactoriesPerPopulation(String name, final int factoriesPerPopulation) {
            super(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.factoriesPerPopulation =
                            Math.max(techState.factoriesPerPopulation, factoriesPerPopulation);
                }
            });
        }
    }

    public static class PlanetSpaceScanner extends TechModule {

        public PlanetSpaceScanner(String name, final int planetShipSensorRange, final int planetStarSensorRange) {
            super(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.planetShipSensorRange = Math.max(techState.planetShipSensorRange, planetShipSensorRange);
                    techState.planetStarSensorRange = Math.max(techState.planetStarSensorRange, planetStarSensorRange);
                }
            });
        }
    }

}
