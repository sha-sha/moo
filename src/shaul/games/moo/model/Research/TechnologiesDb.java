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

    public static final Predicate<ShipModule> IS_COMPUTER_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.COMPUTER);
    public static final Predicate<ShipModule> IS_ARMOR_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.ARMOR);
    public static final Predicate<ShipModule> IS_ENGINE_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.ENGINE);
    public static final Predicate<ShipModule> IS_WEAPON_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.WEAPON);

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
            Utils.assertNotNull("Technology " + tech + " is not in tree!", technology);
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
            Utils.assertNotNull("Technology " + technologyName + " not exist!", technology);
            for (String moduleName : technology.getModules()) {
                ShipModule module = technologyLogic.getShipModule(moduleName);
                Utils.assertNotNull("Module " + moduleName + " not exist!", module);
                if (predicate.test(module)) {
                    modules.add(module);
                }
            }
        }
        return modules;
    }


    private static class ModuleTypePredicate implements Predicate<ShipModule> {

        private final ShipModule.ShipComponent type;

        private ModuleTypePredicate(ShipModule.ShipComponent type) {
            this.type = type;
        }

        @Override
        public boolean test(ShipModule module) {
            Utils.assertNotNull(module);
            return module.getShipComponentType().equals(type);
        }
    }
}
