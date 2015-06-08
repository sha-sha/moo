package shaul.games.moo.setup;

import shaul.games.moo.model.Planet.PlanetTechBonus;
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


}
