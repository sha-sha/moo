package shaul.games.moo.model.Research;

import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class TechnologiesDb {

    private final List<String> technologies;
    private final ITechnologyLogic technologyLogic;

    public TechnologiesDb(ITechnologyLogic technologyLogic, List<String> technologies) {
        // TODO: create immutable list.
        this.technologies = new ArrayList<String>(technologies);
        this.technologyLogic = technologyLogic;
    }

    public int getTechLevel(String category) {
        int highestTechLevel = 0;
        Utils.assertNotNull(category);
        for (String tech : technologies) {
            Technology technology = technologyLogic.getTechnology(tech);
            if (category.equals(technology.getCategory()) {
                highestTechLevel = Math.max(highestTechLevel, technology.getTechLevel());
            }
        }
        return highestTechLevel;
    }

}
