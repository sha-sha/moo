package shaul.games.moo.setup;

import shaul.games.moo.model.Research.TechBonus;
import shaul.games.moo.model.Research.TechModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class GlobalTech {

    public static TechModule spaceScanner(int level, int planetShipSensorRange, int planetStarSensorRange,
                                          int shipSensorRange, boolean enemyShipDestinationAndEta) {
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
    }

    public static TechModule hyperComm() {
        return new TechModule(TechModule.GLOBAL_MODULE_COMMUNICATION, TechModule.Type.Global, 0,
                new TechBonus(TechBonus.Type.ENABLE_HYPER_COMM, 0));
    }
}
