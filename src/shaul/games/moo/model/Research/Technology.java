package shaul.games.moo.model.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class Technology {

    private final String category;
    private final String name;
    private final int researchPoints;
    private final List<TechModule> techModules;
    private final List<String> modules;
    private final int techLevel;

    public Technology(String category, int techLevel, String name, String... modules) {
        this.category = category;
        this.techLevel = techLevel;
        this.name = name;
        this.modules = Arrays.asList(modules);
        techModules = new ArrayList<>();
        researchPoints = 0;
    }

    public Technology(String category, int techLevel, String name, TechModule... modules) {
        this.category = category;
        this.techLevel = techLevel;
        this.name = name;
        this.modules = new ArrayList<>();
        this.techModules = Arrays.asList(modules);
        for (TechModule module : modules) {
            this.modules.add(module.getName());
        }
        researchPoints = 0;
    }

    public String getName() { return  name; }
    public String getCategory() { return category; }
    public int getTechLevel() { return techLevel; }
    public int getResearchPoints() { return researchPoints; }
    public List<String> getModules() { return modules; }
    public List<TechModule> getTechModules() { return techModules; }

    @Override
    public String toString() {
        return String.format("%s [%s technology %d, %d RP] modules: {%s}",
                name, category, techLevel, researchPoints, modules.toString());
    }

    public static final String CATEGORY_COMPUTERS = "Computers";
    public static final String CATEGORY_CONSTRUCTION = "Construction";
    public static final String CATEGORY_FORCE_FIELDS = "Force Fields";
    public static final String CATEGORY_PLANETOLOGY = "Planetology";
    public static final String CATEGORY_PROPULSION = "Propulsion";
    public static final String CATEGORY_WEAPONS = "Weapons";

    public static class Computer extends Technology {

        public Computer(int techLevel, String name, TechModule... modules) {
            super(CATEGORY_COMPUTERS, techLevel, name, modules);
        }
    }

    public static class ForceField extends Technology {

        public ForceField(int techLevel, String name, TechModule... modules) {
            super(CATEGORY_FORCE_FIELDS, techLevel, name, modules);
        }
    }

    public static class Construction extends Technology {

        public Construction(int techLevel, String name, TechModule... modules) {
            super(CATEGORY_CONSTRUCTION, techLevel, name, modules);
        }
    }

    public static class Propulsion extends Technology {

        public Propulsion(int techLevel, String name, TechModule... modules) {
            super(CATEGORY_PROPULSION, techLevel, name, modules);
        }
    }
}
