package shaul.games.moo.model.Research;

import com.sun.org.apache.xpath.internal.operations.Mod;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Shaul on 3/24/2015.
 */
public class TechnologiesDb {

    public static final Predicate<ShipModule> IS_COMPUTER_MODULE = new ModuleTypePredicate(ShipModule.Type.COMPUTER);
    public static final Predicate<ShipModule> IS_ENGINE_MODULE = new ModuleTypePredicate(ShipModule.Type.ENGINE);

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
            if (category.equals(technology.getCategory())) {
                highestTechLevel = Math.max(highestTechLevel, technology.getTechLevel());
            }
        }
        return highestTechLevel;
    }

    public List<ShipModule> getShipModule(Predicate<ShipModule> predicate) {
        List<ShipModule> modules = new ArrayList<ShipModule>();
        for (String technologyName : technologies) {
            Technology technology = technologyLogic.getTechnology(technologyName);
            for (String moduleName : technology.getModules()) {
                ShipModule module = technologyLogic.getShipModule(moduleName);
                if (predicate.test(module)) {
                    modules.add(module);
                }
            }
        }
        return modules;
    }


    private static class ModuleTypePredicate implements Predicate<ShipModule> {

        private final ShipModule.Type type;

        private ModuleTypePredicate(ShipModule.Type type) {
            this.type = type;
        }

        @Override
        public boolean test(ShipModule module) {
            return module.getType().equals(type);
        }
    }
}
