package shaul.games.moo.setup;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.GlobalTechBonus;
import shaul.games.moo.model.Research.PlayerBonus;
import shaul.games.moo.model.Research.TechModule;

/**
 * Created by Shaul on 3/1/2015.
 */
public class GlobalTech {

    public static class AdvancedSpaceScanner extends TechModule {

        public AdvancedSpaceScanner(String name) {
            super(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.canDetectEnemyShipDestinationAndEta = true;
                }
            });
        }
    }


    public static TechModule spaceScanner(int level, int planetShipSensorRange, int planetStarSensorRange,
                                          int shipSensorRange, boolean enemyShipDestinationAndEta) {
        return new GlobalTechBonus("Space Scanner " + level, new GlobalTechBonus.Bonus.Builder()
                .planetShipDetectionRange(planetShipSensorRange)
                .planetStarDetectionRange(planetStarSensorRange)
                .shipSensorRange(shipSensorRange)
                .detectEnemyShipDestination(enemyShipDestinationAndEta)
                .build());
    }

    public static TechModule hyperspaceCommunications() {
        return new GlobalTechBonus("Hyperspace Communications", new GlobalTechBonus.Bonus.Builder()
                .canChangeFleetDestinationOnRoute(true)
                .build());
    }

    public static TechModule spaceScannerzzz(int level, int planetShipSensorRange, int planetStarSensorRange,
                                          int shipSensorRange, boolean enemyShipDestinationAndEta) {
        return null;
        /*
        List<TechBonus> bonuses = new ArrayList<>();
        bonuses.add(new TechBonus(TechBonus.Type.SET_PLANET_SCAN_SHIP_RANGE, planetShipSensorRange));
        bonuses.add(new TechBonus(TechBonus.Type.SET_SHIP_SCAN_RANGE, shipSensorRange));
        if (enemyShipDestinationAndEta) {
            bonuses.add(new TechBonus(TechBonus.Type.CAN_DETECT_ENEMY_SHIP_DESTINATION_AND_ETA, 0));
        }
        if (planetStarSensorRange > 0) {
            bonuses.add(new TechBonus(TechBonus.Type.SET_PLANET_SCAN_STAR_RANGE, planetStarSensorRange));
        }
        return new TechModule(TechModule.GLOBAL_MODULE_SPACE_SCANNER, TechModule.Type.Global, level, bonuses);
        */
    }

    public static TechModule hyperComm() {
        return null;
        /*return new TechModule(TechModule.GLOBAL_MODULE_COMMUNICATION, TechModule.Type.Global, 0,
                new TechBonus(TechBonus.Type.ENABLE_HYPER_COMM, 0));
                */
    }
}
