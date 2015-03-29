package shaul.games.moo.model.Research;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class TechModule {

    public enum Type {Global, Ship, Planet};

    public static final String SHIP_MODULE_COMPUTER = "computer";
    public static final String SHIP_MODULE_ECM = "ecm";
    public static final String SHIP_MODULE_SCANNER = "scanner";
    public static final String SHIP_MODULE_ARMOR = "armor";

    public static final String PLANET_MODULE_ROBOTIC_CONTROL = "robotic-control";

    public static final String GLOBAL_MODULE_SPACE_SCANNER = "space-scanner";
    public static final String GLOBAL_MODULE_COMMUNICATION = "communication";

    private final String name;
    private final Type type;
    private final int level;
    private final List<TechBonus> bonuses;

    public TechModule(String name, Type type) {
        this(name, type, 0, new ArrayList<TechBonus>());
    }

    public TechModule(String name, Type type, int level, TechBonus bonus) {
        this(name, type, level, Arrays.asList(bonus));
    }

    public TechModule(String name, Type type, int level, List<TechBonus> bonuses) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.bonuses = bonuses;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public int getLevel() { return level; }
    public List<TechBonus> getBonuses() { return bonuses; }

    @Override
    public String toString() {
        return String.format("%s %s lvl: %d bonus: %s", type, name, level, bonuses);
    }

}
