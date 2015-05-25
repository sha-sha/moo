package shaul.games.moo.setup;

import static shaul.games.moo.model.Research.TechModule.HullFunction;

import shaul.games.moo.model.Research.TechBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Ship.ShipModuleData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class ShipTech {

    public static ShipModule computer(final int level) {
        return new ShipModule.ComputerShipModule("Battle Computer " + level,
                new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setAttackLevel(level)
                    .build());
    }

    public static ShipModule engine(final int level, final String name) {
        return new ShipModule.EngineShipModule(name,
                new ShipModuleData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                        .setPower(20, 80, 120, 400)
                        .setWrapSpeed(level)
                        .build());
    }

    public static TechModule energyWeapon(String name) {
        return new ShipModule.WeaponShipModule(name,
                new ShipModuleData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20, 80, 120, 400)
                        .setPower(20, 80, 120, 400)
                        .setWeaponDamage(1)
                        .build());
    }

    public static TechModule armor(final String name, final int level, final boolean extraThick) {

        return new ShipModule.ArmorShipModule(name,
                new ShipModuleData.Builder()
                        .setCost(20 * (level - 1), 80 * level, 120 * level, 400 * level)
                        .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                        .setShipHitPoints(3, 60, 200, 4000)
                        .build());
    }

    public static ShipModule shield(final int level) {
        return new ShipModule.ShieldShipModule("Shield " + level,
                new ShipModuleData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                        .setPower(20, 80, 120, 400)
                        .setHitAbsorbs(level)
                        .build());
    }

    // Ship technology bonuses.

    public static TechBonus shipAttack(int value) {
        return new TechBonus(TechBonus.Type.INC_SHIP_ATTACK, value);
    }

    public static TechBonus shipScan() {
        return new TechBonus(TechBonus.Type.SCAN_SHIPS_IN_BATTLE, 1);
    }

    public static TechBonus shipInitiative(int val) {
        return new TechBonus(TechBonus.Type.INC_SHIP_INITIATIVE, val);
    }

    public static TechBonus shipMissleDef(int value) {
        return new TechBonus(TechBonus.Type.INC_SHIP_MISSLE_DEF, value);
    }


    public static class BattleScanner extends ShipModule.SpecialShipModule {

        public BattleScanner() {
            super(Technology.CATEGORY_COMPUTERS,
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

        public Armor(String name, int level, boolean extra) {
            super(name, new ShipModuleData.Builder()
                    .setCost(20 * (level - 1), 80 * level, 120 * level, 400 * level)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setShipHitPoints(3, 60, 200, 4000)
                    .build());
        }
    }

    public static class Engine extends ShipModule.EngineShipModule{

        public Engine(String name, int level) {
            super(name, new ShipModuleData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setWrapSpeed(level)
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

}
