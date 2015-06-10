package shaul.games.moo.setup;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.PlayerBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Ship.ShipModuleData;
import shaul.games.moo.model.Utils;

/**
 * Created by Shaul on 3/1/2015.
 */
public class ShipTech {



    public static class BattleScanner extends ShipModule.SpecialShipModule {

        public BattleScanner() {
            super(Technology.Category.Computers,
                    "Battle Scanner", new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 80, 120, 400)
                    .setPower(20, 80, 120, 400)
                    .setShipInitiative(3)
                    .setMissleDefence(1)
                    .setShipScanLevel(ShipModule.ShipScanLevel.BASIC)
                    .build());
        }
    }

    public static class Ecm extends ShipModule.EcmShipModule {

        public Ecm(int level) {
            super("ECM " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 80, 120, 400)
                    .setPower(20, 80, 120, 400)
                    .setMissleDefence(level)
                    .build());
        }
    }

    public static class BattleComputer extends ShipModule.ComputerShipModule {

        public BattleComputer(int level) {
            super("Battle Computer " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setAttackLevel(level)
                    .build());
        }
    }

    public static class Shield extends ShipModule.ShieldShipModule {

        public Shield(int level) {
            super("Shield " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setHitAbsorbs(level)
                    .build());
        }
    }

    public static class Armor extends ShipModule.ArmorShipModule {

        public Armor(int techLevel, String name, int level, boolean doubleHull) {
            super(name, new ShipModuleData.Builder()
                    .setCost(20 * (level - 1), 80 * (level - 1), 120 * (level - 1), 400 * (level - 1))
                    .setSize(doubleHull ? calcDoubleHull(Hull.Small, techLevel) : 1,
                            doubleHull ? calcDoubleHull(Hull.Medium, techLevel) : 1,
                            doubleHull ? calcDoubleHull(Hull.Large, techLevel) : 1,
                            doubleHull ? calcDoubleHull(Hull.Huge, techLevel) : 1)
                    .setShipHitPointsModifier(
                            doubleHull ? 1.0 + level * 0.5 : 0.5 + level * 0.5) // 50% increase for each level > 1.
                    .build());
        }

        private static int calcDoubleHull(Hull hull, int techLevel) {
            double armorPerHull = hull.equals(Hull.Small) ? 0.5 :  0.4;
            armorPerHull *= 1.0 + techLevel * 0.02;
            return (int) Math.floor(hull.getBaseSpace() * armorPerHull);
        }
    }

    public static class Engine extends ShipModule.EngineShipModule{

        public Engine(String name, int level) {
            super(name, new ShipModuleData.Builder()
                    .setCost(level * 2, level * 2, level * 2, level * 2)
                    .setSize(10 * level, 10 * level, 10 * level, 10 * level)
                    .setWrapSpeed(level)
                    .setOptionalManeuvers(level)
                    .build());
        }
    }

    public static class ReserveFuelTanks extends ShipModule.SpecialShipModule{

        public ReserveFuelTanks(String name) {
            super(Technology.Category.Propulsion,
                    name,
                    new ShipModuleData.Builder()
                            .setCost(2, 6, 31, 155)
                            .setSize(16, 82, 410, 2050)
                            .setFuelTravelDistance(3)
                            .build());
        }
    }

    public static class Laser extends ShipModule.WeaponShipModule {

        public Laser(int level) {
            super("Laser " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 30, 40, 50)
                    .setPower(20, 80, 120, 400)
                    .setWeaponDamage(1)
                    .build());
        }
    }

    public static class Maneuver extends ShipModule.ManeuverShipModule {

        public Maneuver(int level) {
            super("Class " + Utils.toRomanNumber(level), new ShipModuleData.Builder()
                    .setCost(1, 1, 10, 70)
                    .setSize(1, 1, 10, 70)
                    .setPower(20, 80, 120, 400)
                    .setCombatSpeed(level)
                    .build());
        }
    }

    public static class FuelCell extends TechModule {

        public FuelCell(String name, final int shipFuelRange) {
            super(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.shipFuelRange = Math.max(techState.shipFuelRange, shipFuelRange);
                }
            });
        }
    }


    public static class ShipSpaceScanner extends TechModule {

        public ShipSpaceScanner(String name, final int shipSensorRange) {
            super(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.shipSensorRange = Math.max(techState.shipSensorRange, shipSensorRange);
                }
            });
        }
    }
}
