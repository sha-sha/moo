package shaul.games.moo.model;

/**
 * Created by Shaul on 3/1/2015.
 */
public class ShipAttribute {
    public enum Type {
        AttackLevel,
        MissleDefence,
        ScanEnemy
    }

    private final Type type;
    private final int scale;

    public ShipAttribute(Type type, int scale) {
        this.type = type;
        this.scale = scale;
    }

    public Type getType() { return type; }
    public int getScale() { return scale; }

    @Override
    public String toString() {
        return String.format("%s level %d", type, scale);
    }
}
