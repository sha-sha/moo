package shaul.games.moo.setup;

import shaul.games.moo.model.Research.TechBonus;
import shaul.games.moo.model.Research.TechModule;

/**
 * Created by Shaul on 3/1/2015.
 */
public class PlanetTech {

    public static TechModule roboticsControls(int value) {
        return new TechModule(TechModule.PLANET_MODULE_ROBOTIC_CONTROL, TechModule.Type.Planet, value,
                new TechBonus(TechBonus.Type.INC_PLANET_MAX_FACTORY_POPULATION, value));
    }

}
