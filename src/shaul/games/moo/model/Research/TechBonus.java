package shaul.games.moo.model.Research;

/**
 * Created by Shaul on 3/1/2015.
 */
public class TechBonus {
    public enum Type {
        INC_SHIP_ATTACK,
        INC_SHIP_INITIATIVE,
        INC_SHIP_MISSLE_DEF,
        SET_PLANET_SCAN_SHIP_RANGE,
        SET_PLANET_SCAN_STAR_RANGE,
        SET_SHIP_SCAN_RANGE,
        SCAN_SHIPS_IN_BATTLE,
        INC_PLANET_MAX_FACTORY_POPULATION,
        CAN_DETECT_ENEMY_SHIP_DESTINATION_AND_ETA,
        ENABLE_HYPER_COMM,
    }

    private final Type type;
    private final int value;

    public TechBonus(Type type, int value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() { return type; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return String.format("%s val: %d", type, value);
    }
}
