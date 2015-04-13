package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Research.TechModule;

import java.util.Arrays;

/**
 * Created by Shaul on 3/8/2015.
 */
public class ShipModule extends TechModule {

    public static final ShipModule EMPTY = new ShipModule();

    public enum ShipComponent {NONE, COMPUTER, SHIELD, ECM, ARMOR, ENGINE, SPECIAL};
    public enum ShipScanLevel {NONE, BASIC, ADVANCE};

    private final Base moduleData;
    private final ShipComponent shipComponent;

    public enum WeaponType {None, Laser, Kinetic};

    public interface Base {
        int getCost(int hullSize);
        int getSize(int hullSize);
        int getPower(int hullSize);
        int getSpace(int hullSize);
    }

    public interface All extends Base {
        int getAttackLevel();

        int getHitAbsorbs();

        int getMissileDefence();

        int getShipHitPoints(int hullSize);

        int getWrapSpeed();

        int getOptionalManeuvers();

        String getNumberOfEngines(int hullSize);

        int getShipInitiative();

        ShipScanLevel getShipScanLevel();

        int getWeaponDamage();

        int getWeaponSpeed();

        int getWeaponShots();

        int getWeaponRange();

        WeaponType getWeaponType();

        int getWeaponCoolDown();
    }

    public interface Computer extends Base {
        int getAttackLevel(int hullSize);
    }

    public interface Shield extends Base {
        int getHitAbsorbs(int hullSize);
    }

    public interface Ecm extends Base {
        int getMissileDefence(int hullSize);
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
        int getMissileDefence();
        String getEnvironmentColonyType(); //returns the hardest environment
        ShipScanLevel getShipScanLevel();
    }

    public static class ShipData implements All {

        private final String desc;
        private final int[] cost;
        private final int[] size;
        private final int[] power;
        private final int[] space;
        private final int attackLevel;
        private final int hitAbsorbs;
        private final int missileDefence;
        private final int[] shipHitPoints;
        private final int wrapSpeed;
        private final int optionalManeuvers;
        private final float numberOfEngine;
        private final int shipInitiative;
        private final ShipScanLevel shipScanLevel;
        private final int weaponDamage;
        private final int weaponSpeed;
        private final int weaponShots;
        private final int weaponRange;
        private final WeaponType weaponType;
        private final int weaponCooldown;

        public ShipData(Builder builder) {
            this.cost = builder.cost;
            this.size = builder.size;
            this.power = builder.power;
            this.space = builder.space;
            this.attackLevel = builder.attackLevel;
            this.hitAbsorbs = builder.hitAbsorbs;
            this.missileDefence = builder.missileDefence;
            this.shipHitPoints = builder.shipHitPoints;
            this.wrapSpeed = builder.wrapSpeed;
            this.optionalManeuvers = builder.optionalManeuvers;
            this.numberOfEngine = builder.numberOfEngine;
            this.shipInitiative = builder.shipInitiative;
            this.shipScanLevel = builder.shipScanLevel;
            this.weaponDamage = builder.weaponDamage;
            this.weaponSpeed = builder.weaponSpeed;
            this.weaponShots = builder.weaponShots;
            this.weaponRange = builder.weaponRange;
            this.weaponType = builder.weaponType;
            this.weaponCooldown = builder.weaponCooldown;

            desc = new StringBuilder()
                    .append(getAttribute("cost", cost))
                    .append(getAttribute("size", size))
                    .append(getAttribute("power", power))
                    .append(getAttribute("space" ,space))
                    .append(getAttribute(attackLevel > 0, "attackLevel", attackLevel))
                    .append(getAttribute(hitAbsorbs > 0, "hitAbsorbs", hitAbsorbs))
                    .append(getAttribute(missileDefence > 0, "missileDefence", missileDefence))
                    .append(getAttribute("shipHitPoints", shipHitPoints))
                    .append(getAttribute(wrapSpeed > 0, "wrapSpeed", wrapSpeed))
                    .append(getAttribute(optionalManeuvers > 0, "optionalManeuvers", optionalManeuvers))
                    .append(getAttribute(numberOfEngine > 0f, "numberOfEngine", String.valueOf(numberOfEngine)))
                    .append(getAttribute(shipInitiative > 0, "shipInitiative", shipInitiative))
                    .append(getAttribute(
                            shipScanLevel != ShipScanLevel.NONE, "shipScanLevel", shipScanLevel.toString()))
                    .append(getAttribute(weaponDamage > 0, "weaponDamage", weaponDamage))
                    .append(getAttribute(weaponSpeed > 0, "weaponSpeed", weaponSpeed))
                    .append(getAttribute(weaponShots > 0, "weaponShots", weaponShots))
                    .append(getAttribute(weaponRange > 0, "weaponRange", weaponRange))
                    .append(getAttribute(weaponType != WeaponType.None, "weaponType", weaponType.toString()))
                    .append(getAttribute(weaponCooldown > 0, "weaponCooldown", weaponCooldown))
                    .toString();
        }

        @Override public int getAttackLevel() { return attackLevel; }

        @Override public int getHitAbsorbs() { return hitAbsorbs; }

        @Override public int getMissileDefence() { return missileDefence; }

        @Override public int getShipHitPoints(int hullSize) { return shipHitPoints[hullSize]; }

        @Override public int getWrapSpeed() { return wrapSpeed; }

        @Override public int getOptionalManeuvers() { return optionalManeuvers; }

        @Override public String getNumberOfEngines(int hullSize) { return String.valueOf(numberOfEngine); }

        @Override public int getShipInitiative() { return shipInitiative; }

        @Override public ShipScanLevel getShipScanLevel() { return shipScanLevel;}

        @Override public int getWeaponDamage() { return weaponDamage; }

        @Override public int getWeaponSpeed() { return weaponSpeed; }

        @Override public int getWeaponShots() { return weaponShots; }

        @Override public int getWeaponRange() { return weaponRange; }

        @Override public WeaponType getWeaponType() { return weaponType; }

        @Override public int getWeaponCoolDown() { return weaponCooldown; }

        @Override public int getCost(int hullSize) { return cost[hullSize]; }

        @Override public int getSize(int hullSize) { return size[hullSize]; }

        @Override public int getPower(int hullSize) { return power[hullSize]; }

        @Override public int getSpace(int hullSize) { return space[hullSize]; }

        @Override
        public String toString() {
            return desc;
        }

        private String getAttribute(boolean condition, String name, int i) {
            return condition ? " " + name + ":" + i : "";
        }

        private String getAttribute(boolean condition, String name, String i) {
            return condition ? " " + name + ":" + i : "";
        }

        private String getAttribute(String name, int[] i) {
            return i != null && i.length > 0 && i[0] > 0 ? " " + name + ":" + Arrays.toString(i) : "";
        }

        public static class Builder {
            private int[] cost = {0, 0, 0, 0};
            private int[] size = {0, 0, 0, 0};
            private int[] power = {0, 0, 0, 0};
            private int[] space = {0, 0, 0, 0};
            private int attackLevel = 0;
            private int hitAbsorbs = 0;
            private int missileDefence = 0;
            private int[] shipHitPoints = {0, 0, 0, 0};
            private int wrapSpeed = 0;
            private int optionalManeuvers = 0;
            private float numberOfEngine = 0f;
            private int shipInitiative = 0;
            private ShipScanLevel shipScanLevel = ShipScanLevel.NONE;
            private int weaponDamage = 0;
            private int weaponSpeed = 0;
            private int weaponShots = 0;
            private int weaponRange = 0;
            private WeaponType weaponType = WeaponType.None;
            private int weaponCooldown = 0;

            public Builder setCost(int... cost) { this.cost = cost; return this; }
            public Builder setSize(int... size) { this.size = size; return this; }
            public Builder setPower(int... power) { this.power = power; return this; }
            public Builder setSpace(int... space) { this.space = space; return this; }
            public Builder setAttackLevel(int attackLevel) { this.attackLevel = attackLevel; return this; }
            public Builder setHitAbsorbs(int hitAbsorbs) { this.hitAbsorbs = hitAbsorbs; return this; }
            public Builder setMissleDefence(int missleDefence) { this.missileDefence = missleDefence; return this; }
            public Builder setShipHitPoints(int... shipHitPoints) { this.shipHitPoints = shipHitPoints; return this; }
            public Builder setWrapSpeed(int wrapSpeed) { this.wrapSpeed = wrapSpeed; return this; }
            public Builder setOptionalManeuvers(int optionalManeuvers) {
                this.optionalManeuvers = optionalManeuvers; return this; }
            public Builder setNumberOfEngine(float numberOfEngine) {
                this.numberOfEngine = numberOfEngine; return this; }
            public Builder setShipInitiative(int shipInitiative) { this.shipInitiative = shipInitiative; return this;}
            public Builder setShipScanLevel(ShipScanLevel shipScanLevel) {
                this.shipScanLevel = shipScanLevel; return this;}
            public Builder setWeaponDamage(int weaponDamage) { this.weaponDamage = weaponDamage; return this; }
            public Builder setWeaponSpeed(int weaponSpeed) { this.weaponSpeed = weaponSpeed; return this; }
            public Builder setWeaponShots(int weaponShots) { this.weaponShots = weaponShots; return this; }
            public Builder setWeaponRange(int weaponRange) { this.weaponRange = weaponRange; return this; }
            public Builder setWeaponType(WeaponType weaponType) { this.weaponType = weaponType; return this; }
            public Builder setWeaponCooldown(int weaponCooldown) { this.weaponCooldown = weaponCooldown; return this; }

            public ShipData build() {
                return new ShipData(this);
            }
        }
    }

    public static abstract class AbstractSpecial implements Special {

        @Override
        public int getExtraFuelRange() {
            return 0;
        }

        @Override
        public int getMissileDefence() {
            return 0;
        }

        @Override
        public String getEnvironmentColonyType() {
            return null;
        }
        @Override public ShipScanLevel getShipScanLevel() { return ShipScanLevel.NONE; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getMissileDefence() > 0 ? " missle defence: +" + getMissileDefence() : "");
            sb.append(getShipScanLevel() != ShipScanLevel.NONE ? " ship scan: " + getShipScanLevel() : "");
            return sb.toString();
        }

    }


    public ShipModule(String name, ShipComponent shipComponent, Base moduleData) {
        super(name, TechModule.Type.Ship);
        this.shipComponent = shipComponent;
        this.moduleData = moduleData;
    }

    private ShipModule() {
        super("EMPTY", TechModule.Type.Ship);
        this.shipComponent = ShipComponent.NONE;
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

    public ShipComponent getShipComponentType() { return shipComponent; }
    public Base getModuleData() { return moduleData; }

    @Override
    public String toString() {
        return String.format("%s %s data: %s", getType(), getName(), moduleData);
    }

}
