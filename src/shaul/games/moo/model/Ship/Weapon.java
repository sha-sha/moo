package shaul.games.moo.model.Ship;

/**
 * Created by Shaul on 6/10/2015.
 */
public class Weapon {

    public static final Weapon NONE = new Weapon(Type.Beam, 0, 0, 0, 0, false, false, 0);

    public enum Type { Beam, Rocket, Bomb };

    private final Type type;
    private final int minDamage;
    private final int maxDamage;
    private final int range;
    private final int nomShots;
    private final boolean halvesShields;
    private final boolean carryOverDamage;
    private final int bombPopReduction;

    public Weapon(Type type, int minDamage, int maxDamage, int range,
                  int nomShots, boolean halvesShields, boolean carryOverDamage,
                  int bombPopReduction) {
        this.type = type;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.range = range;
        this.nomShots = nomShots;
        this.halvesShields = halvesShields;
        this.carryOverDamage = carryOverDamage;
        this.bombPopReduction = bombPopReduction;
    }

    public Type getType() {
        return type;
    }

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getRange() {
        return range;
    }

    public int getNomShots() {
        return nomShots;
    }

    public boolean isHalvesShields() {
        return halvesShields;
    }

    public boolean isCarryOverDamage() {
        return carryOverDamage;
    }

    public int getBombPopReduction() {
        return bombPopReduction;
    }
}
