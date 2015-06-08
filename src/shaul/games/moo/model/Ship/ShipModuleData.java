package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Utils;

import java.util.Arrays;

/**
 * Created by Shaul on 5/25/2015.
 */
public class ShipModuleData {

    private static final double NO_MODIFIER = 1.0;

    private final String desc;
    private final int[] cost;
    private final int[] size;
    private final int[] power;
    private final int attackLevel;
    private final int hitAbsorbs;
    private final int missileDefence;
    private final double shipHitPointsModifier;
    private final int wrapSpeed;
    private final int optionalManeuvers;
    private final float numberOfEngine;
    private final int shipInitiative;
    private final ShipModule.ShipScanLevel shipScanLevel;
    private final int weaponDamage;
    private final int weaponSpeed;
    private final int weaponShots;
    private final int weaponRange;
    private final ShipModule.WeaponType weaponType;
    private final int weaponCooldown;
    private final int combatSpeed;
    private final int fuelTravelDistance;

    public ShipModuleData(Builder builder) {
        this.cost = builder.cost;
        this.size = builder.size;
        this.power = builder.power;
        this.attackLevel = builder.attackLevel;
        this.hitAbsorbs = builder.hitAbsorbs;
        this.missileDefence = builder.missileDefence;
        this.shipHitPointsModifier = builder.shipHitPointsModifier;
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
        this.combatSpeed = builder.combatSpeed;
        this.fuelTravelDistance = builder.fuelTravelDistance;

        desc = new StringBuilder().append(getAttribute("cost", cost))
                .append(getAttribute("size", size))
                .append(getAttribute("power", power))
                .append(getAttribute(attackLevel > 0, "attackLevel", attackLevel))
                .append(getAttribute(hitAbsorbs > 0, "hitAbsorbs", hitAbsorbs))
                .append(getAttribute(missileDefence > 0, "missileDefence", missileDefence))
                .append(getAttribute(shipHitPointsModifier != NO_MODIFIER,
                        "shipHitPointsModifier", shipHitPointsModifier))
                .append(getAttribute(wrapSpeed > 0, "wrapSpeed", wrapSpeed))
                .append(getAttribute(optionalManeuvers > 0, "optionalManeuvers", optionalManeuvers))
                .append(getAttribute(numberOfEngine > 0f, "numberOfEngine", String.valueOf(numberOfEngine)))
                .append(getAttribute(shipInitiative > 0, "shipInitiative", shipInitiative))
                .append(getAttribute(
                        shipScanLevel != ShipModule.ShipScanLevel.NONE, "shipScanLevel", shipScanLevel.toString()))
                .append(getAttribute(weaponDamage > 0, "weaponDamage", weaponDamage))
                .append(getAttribute(weaponSpeed > 0, "weaponSpeed", weaponSpeed))
                .append(getAttribute(weaponShots > 0, "weaponShots", weaponShots))
                .append(getAttribute(weaponRange > 0, "weaponRange", weaponRange))
                .append(getAttribute(weaponType != ShipModule.WeaponType.None, "weaponType", weaponType.toString()))
                .append(getAttribute(weaponCooldown > 0, "weaponCooldown", weaponCooldown))
                .append(getAttribute(fuelTravelDistance > 0, "fuelTravelDistance", fuelTravelDistance))
                .toString();
    }

    public int getAttackLevel() { return attackLevel; }

    public int getHitAbsorbs() { return hitAbsorbs; }

    public int getMissileDefence() { return missileDefence; }

    public double getShipHitPointsModifier() { return shipHitPointsModifier; }
    //public int getShipHitPoints(Hull hull) { return shipHitPoints[hull.ordinal()]; }

    public int getWrapSpeed() { return wrapSpeed; }

    public int getOptionalManeuvers() { return optionalManeuvers; }

    public String getNumberOfEngines(int hullSize) { return String.valueOf(numberOfEngine); }

    public int getShipInitiative() { return shipInitiative; }

    public ShipModule.ShipScanLevel getShipScanLevel() { return shipScanLevel;}

    public int getWeaponDamage() { return weaponDamage; }

    public int getWeaponSpeed() { return weaponSpeed; }

    public int getWeaponShots() { return weaponShots; }

    public int getWeaponRange() { return weaponRange; }

    public ShipModule.WeaponType getWeaponType() { return weaponType; }

    public int getWeaponCoolDown() { return weaponCooldown; }

    public int getCombatSpeed() { return combatSpeed; }

    public int getCost(Hull hull) { return cost[hull.ordinal()]; }

    public int getSize(Hull hull) { return size[hull.ordinal()]; }

    public int getPower(Hull hull) { return power[hull.ordinal()]; }

    public int getFuelTravelDistance() { return fuelTravelDistance; }

    @Override
    public String toString() {
        return desc;
    }

    private String getAttribute(boolean condition, String name, int i) {
        return condition ? " " + name + ":" + i : "";
    }

    private String getAttribute(boolean condition, String name, double i) {
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
        private int attackLevel = 0;
        private int hitAbsorbs = 0;
        private int missileDefence = 0;
        private double shipHitPointsModifier = NO_MODIFIER;
        private int wrapSpeed = 0;
        private int optionalManeuvers = 0;
        private float numberOfEngine = 0f;
        private int shipInitiative = 0;
        private ShipModule.ShipScanLevel shipScanLevel = ShipModule.ShipScanLevel.NONE;
        private int weaponDamage = 0;
        private int weaponSpeed = 0;
        private int weaponShots = 0;
        private int weaponRange = 0;
        private ShipModule.WeaponType weaponType = ShipModule.WeaponType.None;
        private int weaponCooldown = 0;
        private int combatSpeed = 0;
        private int fuelTravelDistance = 0;

        public Builder setCost(int... cost) { this.cost = cost; return this; }
        public Builder setSize (int... size){
            Utils.check("length " + size.length, size.length == 4);
            this.size = size; return this;
        }
        public Builder setPower(int... power) { this.power = power; return this; }
        public Builder setAttackLevel(int attackLevel) { this.attackLevel = attackLevel; return this; }
        public Builder setHitAbsorbs(int hitAbsorbs) { this.hitAbsorbs = hitAbsorbs; return this; }
        public Builder setMissleDefence(int missleDefence) { this.missileDefence = missleDefence; return this; }
        public Builder setShipHitPointsModifier(double shipHitPointsModifier) {
            this.shipHitPointsModifier = shipHitPointsModifier; return this; }
        public Builder setWrapSpeed(int wrapSpeed) { this.wrapSpeed = wrapSpeed; return this; }
        public Builder setOptionalManeuvers(int optionalManeuvers) {
            this.optionalManeuvers = optionalManeuvers; return this; }
        public Builder setNumberOfEngine(float numberOfEngine) {
            this.numberOfEngine = numberOfEngine; return this; }
        public Builder setShipInitiative(int shipInitiative) { this.shipInitiative = shipInitiative; return this;}
        public Builder setShipScanLevel(ShipModule.ShipScanLevel shipScanLevel) {
            this.shipScanLevel = shipScanLevel; return this;}
        public Builder setWeaponDamage(int weaponDamage) { this.weaponDamage = weaponDamage; return this; }
        public Builder setWeaponSpeed(int weaponSpeed) { this.weaponSpeed = weaponSpeed; return this; }
        public Builder setWeaponShots(int weaponShots) { this.weaponShots = weaponShots; return this; }
        public Builder setWeaponRange(int weaponRange) { this.weaponRange = weaponRange; return this; }
        public Builder setWeaponType(ShipModule.WeaponType weaponType) { this.weaponType = weaponType; return this; }
        public Builder setWeaponCooldown(int weaponCooldown) { this.weaponCooldown = weaponCooldown; return this; }
        public Builder setCombatSpeed(int combatSpeed) { this.combatSpeed = combatSpeed; return this; }
        public Builder setFuelTravelDistance(int fuelTravelDistance) { this.fuelTravelDistance = fuelTravelDistance; return this; }

        public ShipModuleData build() {
            return new ShipModuleData(this);
        }
    }
}
