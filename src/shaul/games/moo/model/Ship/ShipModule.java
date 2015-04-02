package shaul.games.moo.model.Ship;

/**
 * Created by Shaul on 3/8/2015.
 */
public class ShipModule {

    public static final ShipModule EMPTY = new ShipModule();

    public enum Type {NONE, COMPUTER, SHIELD, ECM, ARMOR, ENGINE, SPECIAL};

    private final String name;
    private final Base moduleData;
    private final Type type;

    public enum WeaponType {Laser, Kinetic};

    public interface Base {
        int getCost(int hullSize);
        int getSize(int hullSize);
        int getPower(int hullSize);
        int getSpace(int hullSize);
    }

    public interface Computer extends Base {
        int getAttackLevel(int hullSize);
    }

    public interface Shield extends Base {
        int getHitAbsorbs(int hullSize);
    }

    public interface Ecm extends Base {
        int getMissleDefence(int hullSize);
    }

    public interface Armor extends Base {
        int getHitPoints(int hullSize);
    }

    public interface Engine extends Base {
        int getWrapSpeed(int hullSize);
        int getOptionalManeuvers(int hullSize);
        String getNumberOfEngines(int hullSize);
    }

    public interface Weapon extends Base {
        int getWeaponDamage();
        int getWeaponSpeed();
        int getWeaponShots();
        int getWeaponRange();
        WeaponType getWeaponType();
        int getWeaponCoolDown();
    }

    public interface Special extends Base {
        int getExtraFuelRange();
        String getEnvironmentColonyType(); //returns the hardest environment

    }

    public ShipModule(String name, Type type, Base moduleData) {
        this.name = name;
        this.type = type;
        this.moduleData = moduleData;
    }

    private ShipModule() {
        this.name = "EMPTY";
        this.type = Type.NONE;
        this.moduleData = new Base() {
            @Override
            public int getCost(int hullSize) {
                return 0;
            }

            @Override
            public int getSize(int hullSize) {
                return 0;
            }

            @Override
            public int getPower(int hullSize) {
                return 0;
            }

            @Override
            public int getSpace(int hullSize) {
                return 0;
            }
        };
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public Base getModuleData() { return moduleData; }
}
