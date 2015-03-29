package shaul.games.moo.model.Research;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class Technology {

    private final String category;
    private final String name;
    private final String desc;
    private final int researchPoints;
    //private final List<TechModule> modules;
    private final List<String> modules;
    private final int techLevel;

    public Technology(String category, int techLevel, String name, String desc, String module) {
        this(category, techLevel, name, desc, Arrays.asList(module));
    }

    public Technology(String category, int techLevel, String name, String desc, List<String> modules) {
        this.category = category;
        this.techLevel = techLevel;
        this.name = name;
        this.desc = desc;
        this.modules = modules;
        researchPoints = 0;
    }

    public String getName() { return  name; }
    public String getDescription() { return desc; }
    public String getCategory() { return category; }
    public int getTechLevel() { return techLevel; }
    public int getResearchPoints() { return researchPoints; }
    public List<String> getModules() { return modules; }

    @Override
    public String toString() {
        return String.format("%s [%s technology %d, %d RP]: %s. modules: {%s}",
                name, category, techLevel, researchPoints, desc, modules.toString());
    }
}
