package shaul.games.moo.model.Research;

import shaul.games.moo.model.Utils;

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


    public static class HullFunction implements Utils.Int2Int {
        private final double factor;
        private final int offset;

        HullFunction(double factor) { this(factor, 0); }
        HullFunction(double factor, int offset) {
            this.factor = factor;
            this.offset = offset;
        }

        @Override
        public int apply(int a) {
            return ((int) Math.floor(factor * a)) + offset;
        }
    }

    private final String name;
    private final Type type;

    public TechModule(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public Type getType() { return type; }

    @Override
    public String toString() {
        return String.format("%s %s", type, name);
    }

}
