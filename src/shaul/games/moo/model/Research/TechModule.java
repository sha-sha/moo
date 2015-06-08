package shaul.games.moo.model.Research;

import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class TechModule {

    private final PlayerBonus playerBonus;

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

    public TechModule(String name, Type type) {
        this.name = name;
        this.type = type;
        playerBonus = PlayerBonus.NO_BONUS;
    }

    public TechModule(String name, PlayerBonus playerBonus) {
        this.name = name;
        this.type = Type.Global;
        this.playerBonus = playerBonus;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public PlayerBonus getPlayerBonus() { return playerBonus; }

    @Override
    public String toString() {
        return String.format("%s %s", type, name);
    }

}
