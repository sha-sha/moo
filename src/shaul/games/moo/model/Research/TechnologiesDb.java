package shaul.games.moo.model.Research;

import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Shaul on 3/24/2015.
 */
public class TechnologiesDb {

    public static final Predicate<ShipModule> IS_COMPUTER_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.COMPUTER);
    public static final Predicate<ShipModule> IS_SHIELD_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.SHIELD);
    public static final Predicate<ShipModule> IS_ECM_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.ECM);
    public static final Predicate<ShipModule> IS_ARMOR_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.ARMOR);
    public static final Predicate<ShipModule> IS_ENGINE_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.ENGINE);
    public static final Predicate<ShipModule> IS_WEAPON_MODULE =
            new ModuleTypePredicate(ShipModule.ShipComponent.WEAPON);

    private final Comparator<ShipModule> COMPARE_TECH_LEVEL;

    private final List<String> technologiesNames;
    private final ITechnologyLogic technologyLogic;
    private final ArrayList<Technology> technologies;
    private final List<ShipModule> emptyModules;
    private ShipModule lowestArmor;
    private ShipModule lowestEngine;

    public static Predicate<ShipModule> isModuleOf(ShipModule.ShipComponent shipComponent) {
        return new ModuleTypePredicate(shipComponent);
    }

    public TechnologiesDb(final ITechnologyLogic technologyLogic, List<String> technologies) {
        // TODO: create immutable list.
        this.technologiesNames = new ArrayList<String>(technologies);
        this.technologies = new ArrayList<Technology>();
        for (String technology : technologies) {
            this.technologies.add(
                    Utils.checkNotNull(technologyLogic.getTechnology(technology), "Can't find " + technology));
        }
        this.technologyLogic = technologyLogic;
        COMPARE_TECH_LEVEL = new Comparator<ShipModule>() {
            @Override
            public int compare(ShipModule o1, ShipModule o2) {
                return technologyLogic.getTechnologyOfTechModule(o1.getName()).getTechLevel() -
                        technologyLogic.getTechnologyOfTechModule(o2.getName()).getTechLevel();
            }
        };
        emptyModules = new ArrayList<>(technologyLogic.getEmptyShipModules());
    }

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public List<ShipModule> getEmptyModules() {
        return emptyModules;
    }

    public int getTechLevel(String category) {
        int highestTechLevel = 0;
        Utils.assertNotNull(category);
        for (String tech : technologiesNames) {
            Technology technology = technologyLogic.getTechnology(tech);
            Utils.assertNotNull("Technology " + tech + " is not in tree!", technology);
            if (category.equals(technology.getCategory())) {
                highestTechLevel = Math.max(highestTechLevel, technology.getTechLevel());
            }
        }
        return highestTechLevel;
    }

    public ShipModule getShipModule(final String name) {
        List<ShipModule> modules = getShipModule(new Predicate<ShipModule>() {
            @Override
            public boolean test(ShipModule shipModule) {
                return shipModule.getName().equals(name);
            }
        });
        return modules.size() > 0 ? modules.get(0) : ShipModule.EMPTY;
    }

    public List<ShipModule> getShipModule(Predicate<ShipModule> predicate) {
        List<ShipModule> modules = new ArrayList<ShipModule>();
        for (ShipModule emptyModules : technologyLogic.getEmptyShipModules()) {
            if (predicate.test(emptyModules)) {
                modules.add(emptyModules);
            }
        }
        for (Technology technology : technologies) {
            for (TechModule module : technology.getTechModules()) {
                if (module instanceof ShipModule && predicate.test((ShipModule) module)) {
                    modules.add((ShipModule) module);
                }
            }
        }
        return modules;
    }

    public ShipModule getLowestArmor() {
        return select(getShipModule(IS_ARMOR_MODULE), COMPARE_TECH_LEVEL);
    }

    public ShipModule getLowestEngine() {
        return select(getShipModule(IS_ENGINE_MODULE), COMPARE_TECH_LEVEL);
    }

    private ShipModule select(List<ShipModule> modules, Comparator<ShipModule> comparator) {
        if (modules.isEmpty()) {
            return ShipModule.EMPTY;
        }
        ShipModule best = modules.get(0);
        for (ShipModule module : modules) {
            if (comparator.compare(best, module) > 0) {
                best = module;
            }
        }
        return best;
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
