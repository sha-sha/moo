package shaul.games.moo.setup;

import shaul.games.moo.model.Planet.Environment;
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

        public BattleScanner(int techLevel) {
            super(Technology.Category.Computers,
                    techLevel,
                    "Battle Scanner", new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 80, 120, 400)
                    .setPower(20, 80, 120, 400)
                    .setShipInitiative(3)
                    .setMissleDefence(1)
                    .setShipScanLevel(ShipModule.ShipScanLevel.BASIC)
                    .build(),
                    ExclusionGroup.None);
        }
    }

    public static class Ecm extends ShipModule.EcmShipModule {

        public Ecm(int techLevel, int level) {
            super(techLevel, "ECM " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 80, 120, 400)
                    .setPower(20, 80, 120, 400)
                    .setMissleDefence(level)
                    .build());
        }
    }

    public static class BattleComputer extends ShipModule.ComputerShipModule {

        public BattleComputer(int techLevel, int level) {
            super(techLevel, "Battle Computer " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setAttackLevel(level)
                    .build());
        }
    }

    public static class Shield extends ShipModule.ShieldShipModule {

        public Shield(int techLevel, int level) {
            super(techLevel, "Shield " + level, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setHitAbsorbs(level)
                    .build());
        }
    }

    public static class Armor extends ShipModule.ArmorShipModule {

        public Armor(int techLevel, String name, int level, boolean doubleHull) {
            super(techLevel, name, new ShipModuleData.Builder()
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

        public Engine(int techLevel, String name, int level) {
            this(techLevel, name, level, 0);
        }
        public Engine(int techLevel, String name, int level, int additionalCombatSpeed) {
            super(techLevel, name, new ShipModuleData.Builder()
                    .setCost(level * 2, level * 2, level * 2, level * 2)
                    .setSize(10 * level, 10 * level, 10 * level, 10 * level)
                    .setWrapSpeed(level)
                    .setOptionalManeuvers(level)
                    .setCombatSpeed(additionalCombatSpeed)
                    .build());
        }
    }

    public static class ReserveFuelTanks extends ShipModule.SpecialShipModule{

        public ReserveFuelTanks(int techLevel, String name) {
            super(Technology.Category.Propulsion,
                    techLevel,
                    name,
                    new ShipModuleData.Builder()
                            .setCost(2, 6, 31, 155)
                            .setSize(16, 82, 410, 2050)
                            .setFuelTravelDistance(3)
                            .build(),
                    ExclusionGroup.None);
        }
    }

    public static class BeamModule extends ShipModule.WeaponShipModule {

        public BeamModule(int techLevel, String name, ShipWeapons.Beam beam) {
            super(techLevel, name, new ShipModuleData.Builder()
                    .setCost(beam.cost, beam.cost, beam.cost, beam.cost)
                    .setSize(beam.size, beam.size, beam.size, beam.size)
                    .setPower(beam.power, beam.power, beam.power, beam.power)
                    .setWeapon(beam.weapon)
                    .build());
        }
    }

    public static class BombModule extends ShipModule.WeaponShipModule {

        public BombModule(int techLevel, String name, ShipWeapons.Bomb bomb) {
            super(techLevel, name, new ShipModuleData.Builder()
                    .setCost(bomb.cost, bomb.cost, bomb.cost, bomb.cost)
                    .setSize(bomb.size, bomb.size, bomb.size, bomb.size)
                    .setPower(bomb.power, bomb.power, bomb.power, bomb.power)
                    .setWeapon(bomb.weapon)
                    .build());
        }
    }

    public static class Maneuver extends ShipModule.ManeuverShipModule {

        public Maneuver(int techLevel, int level) {
            super(techLevel, "Class " + Utils.toRomanNumber(level), new ShipModuleData.Builder()
                    .setCost(1, 1, 10, 70)
                    .setSize(1, 1, 10, 70)
                    .setPower(20, 80, 120, 400)
                    .setCombatSpeed(level)
                    .setManeuvers(level)
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

    public static class InertialStabilizer extends ShipModule.SpecialShipModule {
        public InertialStabilizer(int techLevel, String name) {
            super(Technology.Category.Propulsion, techLevel, name, new ShipModuleData.Builder()
                    .setCost(1, 1, 10, 70)
                    .setSize(1, 1, 10, 70)
                    .setPower(20, 80, 120, 400)
                    .setManeuvers(2)
                    .setCombatSpeed(1)
                    .setMissleDefence(2)
                            .build(),
                    ExclusionGroup.None);
        }
    }

    public static class ColonyBase extends ShipModule.SpecialShipModule {

        public ColonyBase(int techLevel, String name, Environment environment) {
            super(Technology.Category.Planetology, techLevel, name, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 30, 40, 50)
                    .setPower(20, 80, 120, 400)
                    .setColonyModuleEnv(environment)
                    .build(),
                    ExclusionGroup.Colony);
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
