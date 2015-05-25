package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by shaul on 4/2/15.
 */
public class PlayerStateImpl implements IPlayerState {

    private final List<Fleet> fleets;
    private final TechnologiesDb technologies;

    private final List<TechModule> techModules;

    public PlayerStateImpl(List<Fleet> fleets, TechnologiesDb technologies) {
        this.technologies = technologies;
        this.fleets = fleets;
        this.techModules = new ArrayList<>();
        for (Technology tech : technologies.getTechnologies()) {
            techModules.addAll(tech.getTechModules());
        }
    }

    @Override
    public TechnologiesDb getTechnologies() {
        return technologies;
    }

    @Override
    public List<Fleet> getFleets() {
        return fleets;
    }

    @Override
    public List<TechModule> getModulesOfType(final Class<? extends TechModule> type) {
        List<TechModule> modules = new ArrayList<>();
        modules.addAll(Utils.filter(technologies.getEmptyModules(), new Predicate<ShipModule>() {
            @Override
            public boolean test(ShipModule techModule) {
                return type.isInstance(techModule);
            }
        }));
        modules.addAll(Utils.filter(techModules, new Predicate<TechModule>() {
            @Override
            public boolean test(TechModule techModule) {
                return type.isInstance(techModule);
            }
        }));
        return modules;
    }

    @Override
    public double getModuleSizeReduction(String module) {
        return 1.0;

        /*
                Utils.assertNotNull(module);
        Utils.assertNotNull(playerTechs);
        Technology tech = getTechnologyOfTechModule(module);
        Utils.check("Module " + module + " has no tech!", tech != null);
        int moduleTechLevel = tech.getTechLevel();
        String moduleCategory = tech.getCategory();
        int currentTechLevel = playerTechs.getTechLevel(moduleCategory);
        return getCostReduction(currentTechLevel - moduleTechLevel);

         */
    }

    @Override
    public double getModuleCostReduction(String module) {
        return 1.0;
    }
}
