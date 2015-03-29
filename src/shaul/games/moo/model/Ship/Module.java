package shaul.games.moo.model.Ship;

/**
 * Created by Shaul on 3/8/2015.
 */
public class Module {

    private final String name;
    private final Base moduleData;

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

    public Module(String name, Base moduleData) {
        this.name = name;
        this.moduleData = moduleData;
    }

    public String getName() { return name; }
    public Base getModuleData() { return moduleData; }
}
