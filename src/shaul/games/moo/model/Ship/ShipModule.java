package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Shaul on 3/8/2015.
 */
public class ShipModule extends TechModule {

    public static final ShipModule EMPTY = new ShipModule();
    public static final ShipData NO_DATA = new ShipData.Builder().build();
    public static final ShipModule NO_COMPUTER = new ComputerShipModule("No Computer", NO_DATA);
    public static final ShipModule NO_SHIELD = new ShieldShipModule("No Shield", NO_DATA);
    public static final ShipModule NO_ECM = new EcmShipModule("No ECM", NO_DATA);
    public static final ShipModule NO_WEAPON = new WeaponShipModule("No Weapon", NO_DATA);
    public static final Utils.Countable<ShipModule> ZERO_WEAPON = new Utils.Countable<>(NO_WEAPON, 0);
    public static final ShipModule NO_SPECIAL = new SpecialShipModule(Technology.CATEGORY_COMPUTERS, "None", NO_DATA);

    public enum ShipComponent {NONE, COMPUTER, SHIELD, ECM, ARMOR, ENGINE, WEAPON, SPECIAL}
    public enum ShipScanLevel {NONE, BASIC, ADVANCE}

    private final String technologyCategory;
    private final ShipData moduleData;
    private final ShipComponent shipComponent;
    private final Set<ShipDesign.SlotType> possibleSlotType;

    public enum WeaponType {None, Laser, Kinetic}

    public enum HullType {
        Tiny, Small, Medium, Huge
    };



    public static class ShipData {

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

        public int getAttackLevel() { return attackLevel; }

        public int getHitAbsorbs() { return hitAbsorbs; }

        public int getMissileDefence() { return missileDefence; }

        public int getShipHitPoints(HullType hull) { return shipHitPoints[hull.ordinal()]; }

        public int getWrapSpeed() { return wrapSpeed; }

        public int getOptionalManeuvers() { return optionalManeuvers; }

        public String getNumberOfEngines(int hullSize) { return String.valueOf(numberOfEngine); }

        public int getShipInitiative() { return shipInitiative; }

        public ShipScanLevel getShipScanLevel() { return shipScanLevel;}

        public int getWeaponDamage() { return weaponDamage; }

        public int getWeaponSpeed() { return weaponSpeed; }

        public int getWeaponShots() { return weaponShots; }

        public int getWeaponRange() { return weaponRange; }

        public WeaponType getWeaponType() { return weaponType; }

        public int getWeaponCoolDown() { return weaponCooldown; }

        public int getCost(HullType hull) { return cost[hull.ordinal()]; }

        public int getSize(HullType hull) { return size[hull.ordinal()]; }

        public int getPower(HullType hull) { return power[hull.ordinal()]; }

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
/*
    public ShipModule(String name, ShipComponent shipComponent, ShipData moduleData) {
        super(name, TechModule.Type.Ship);
        this.shipComponent = shipComponent;
        this.moduleData = moduleData;
    }

    private ShipModule(String name, ShipComponent shipComponent) {
        super(name, TechModule.Type.Ship);
        this.shipComponent = shipComponent;
        this.moduleData = new ShipData.Builder().build();
    }
*/
    private ShipModule(String technologyCategory,
                       String name,
                       Set<ShipDesign.SlotType> possibleSlotTypes,
                       ShipComponent shipComponent,
                       ShipData moduleData) {
        super(name, TechModule.Type.Ship);
        this.technologyCategory = technologyCategory;
        this.possibleSlotType = possibleSlotTypes;
        this.shipComponent = shipComponent;
        this.moduleData = moduleData;
    }

    private ShipModule() {
        super("EMPTY", TechModule.Type.Ship);
        this.shipComponent = ShipComponent.NONE;
        this.technologyCategory = Technology.CATEGORY_COMPUTERS;
        this.possibleSlotType = new HashSet<>();
        this.moduleData = new ShipData.Builder().build();
    }

    public ShipComponent getShipComponentType() { return shipComponent; }
    public ShipData getModuleData() { return moduleData; }
    public boolean isEmpty() { return this == EMPTY; }
    public Set<ShipDesign.SlotType> getPossibleSlotType() { return possibleSlotType; }

    public int getSpace(IPlayerState playerState, HullType hull) {
        int baseSize = moduleData.getSize(hull);
        if (baseSize == 0) {
            return baseSize;
        }
        return (int) (baseSize * playerState.getModuleCostReduction(technologyCategory));
    }

    @Override
    public String toString() {
        return String.format("%s %s data: %s", getType(), getName(), moduleData);
    }


//    public static class EmptyShipModule extends ShipModule {
//
//        EmptyShipModule(String name, ShipComponent shipComponent) {
//            super(name, shipComponent);
//        }
//
//        public boolean isEmpty() { return true; }
//    }

    public static class ComputerShipModule extends ShipModule {
        public ComputerShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_COMPUTERS,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Computer),
                    ShipComponent.COMPUTER, moduleData);
        }
    }

    public static class ShieldShipModule extends ShipModule {
        public ShieldShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_FORCE_FIELDS,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Shield),
                    ShipComponent.SHIELD, moduleData);
        }
    }

    public static class ArmorShipModule extends ShipModule {
        public ArmorShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_CONSTRUCTION,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Armor),
                    ShipComponent.ARMOR,
                    moduleData);
        }
    }

    public static class EcmShipModule extends ShipModule {
        public EcmShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_COMPUTERS,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Ecm),
                    ShipComponent.ECM, moduleData);
        }
    }

    public static class EngineShipModule extends ShipModule {
        public EngineShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_PROPULSION,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Engine),
                    ShipComponent.ENGINE, moduleData);
        }
    }

    public static class WeaponShipModule extends ShipModule {
        public WeaponShipModule(String name, ShipData moduleData) {
            super(Technology.CATEGORY_WEAPONS,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Weapon1,
                            ShipDesign.SlotType.Weapon2,
                            ShipDesign.SlotType.Weapon3,
                            ShipDesign.SlotType.Weapon4),
                    ShipComponent.WEAPON,
                    moduleData);
        }
    }

    public static class SpecialShipModule extends ShipModule {
        public SpecialShipModule(String category, String name, ShipData moduleData) {
            super(category,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Special1,
                            ShipDesign.SlotType.Special2,
                            ShipDesign.SlotType.Special3),
                    ShipComponent.SPECIAL,
                    moduleData);
        }
    }
}
