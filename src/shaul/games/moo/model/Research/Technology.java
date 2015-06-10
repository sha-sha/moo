package shaul.games.moo.model.Research;

import shaul.games.moo.model.Player.IPlayerState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class Technology {

    public enum Category {
        Computers("Computers"),
        Construction("Construction"),
        ForceFields("Force Fields"),
        Planetology("Planetology"),
        Propulsion("Propulsion"),
        Weapons("Weapons"),
        None("None");

        private final String name;

        Category(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Category category;
    private final String name;
    private final int researchPoints;
    private final List<TechModule> techModules;
    private final List<String> modules;
    private final int techLevel;

    public Technology(Category category, int techLevel, String name, TechModule... modules) {
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

    public Technology(Category category, int techLevel, String name, PlayerBonus playerBonus) {
        this(category, techLevel, name, new TechModule(name, playerBonus));
    }

    public String getName() { return  name; }
    public Category getCategory() { return category; }
    public int getTechLevel() { return techLevel; }
    public int getResearchPoints() { return researchPoints; }
    public List<String> getModules() { return modules; }
    public List<TechModule> getTechModules() { return techModules; }

    @Override
    public String toString() {
        return String.format("%s [%s technology %d, %d RP] modules: {%s}",
                name, category, techLevel, researchPoints, modules.toString());
    }

//    public static final String CATEGORY_COMPUTERS = "Computers";
//    public static final String CATEGORY_CONSTRUCTION = "Construction";
//    public static final String CATEGORY_FORCE_FIELDS = "Force Fields";
//    public static final String CATEGORY_PLANETOLOGY = "Planetology";
//    public static final String CATEGORY_PROPULSION = "Propulsion";
//    public static final String CATEGORY_WEAPONS = "Weapons";
//    public static final String CATEGORY_NONE = "None";

    public static class Computer extends Technology {

        public Computer(int techLevel, String name, TechModule... modules) {
            super(Category.Computers, techLevel, name, modules);
        }

        public Computer(int techLevel, String name, PlayerBonus playerBonus) {
            super(Category.Computers, techLevel, name, playerBonus);
        }
    }

    public static class ForceField extends Technology {

        public ForceField(int techLevel, String name, TechModule... modules) {
            super(Category.ForceFields, techLevel, name, modules);
        }
    }

    public static class Construction extends Technology {

        public Construction(int techLevel, String name, TechModule... modules) {
            super(Category.Construction, techLevel, name, modules);
        }
    }

    public static class Propulsion extends Technology {

        public Propulsion(int techLevel, String name, TechModule... modules) {
            super(Category.Propulsion, techLevel, name, modules);
        }
    }

    public static class Weapons extends Technology {

        public Weapons(int techLevel, String name, TechModule... modules) {
            super(Category.Weapons, techLevel, name, modules);
        }
    }
}
