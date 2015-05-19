package shaul.games.moo.setup;

import static shaul.games.moo.model.Research.TechModule.HullFunction;

import shaul.games.moo.model.Research.TechBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Ship.ShipModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class ShipTech {

    public static ShipModule computer(final int level) {
        return new ShipModule.ComputerShipModule("Battle Computer " + level,
                new ShipModule.ShipData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setAttackLevel(level)
                    .build());
    }

    public static TechModule scanner() {
        return new ShipModule.SpecialShipModule("Battle Scanner",
                new ShipModule.ShipData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20, 80, 120, 400)
                        .setPower(20, 80, 120, 400)
                        .setShipInitiative(3)
                        .setMissleDefence(1)
                        .setShipScanLevel(ShipModule.ShipScanLevel.BASIC)
                        .build());
    }

    public static ShipModule engine(final int level, final String name) {
        return new ShipModule.EngineShipModule(name,
                new ShipModule.ShipData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                        .setPower(20, 80, 120, 400)
                        .setWrapSpeed(level)
                        .build());
    }

    public static TechModule energyWeapon(String name) {
        return new ShipModule.WeaponShipModule(name,
                new ShipModule.ShipData.Builder()
                        .setCost(20, 80, 120, 400)
                        .setSize(20, 80, 120, 400)
                        .setPower(20, 80, 120, 400)
                        .setWeaponDamage(1)
                        .build());
    }

    public static TechModule armor(final String name, final int level, final boolean extraThick) {

        return new ShipModule.ArmorShipModule(name,
                new ShipModule.ShipData.Builder()
                        .setCost(20 * (level - 1), 80 * level, 120 * level, 400 * level)
                        .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                        .setShipHitPoints(3, 60, 200, 4000)
                        .build());
    }

    public static ShipModule shield(final int level) {
        return new ShipModule.ShieldShipModule("Shield " + level,
                new ShipModule.ShipData.Builder()
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
            super("Battle Scanner", new ShipModule.ShipData.Builder()
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
            super("ECM " + level, new ShipModule.ShipData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20, 80, 120, 400)
                    .setPower(20, 80, 120, 400)
                    .setMissleDefence(level)
                    .build());
        }
    }

    public static class BattleComputer extends ShipModule.ComputerShipModule {

        public BattleComputer(int level) {
            super("Battle Computer " + level, new ShipModule.ShipData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setAttackLevel(level)
                    .build());
        }
    }

    public static class Shield extends ShipModule.ShieldShipModule {

        public Shield(int level) {
            super("Shield " + level, new ShipModule.ShipData.Builder()
                    .setCost(20, 80, 120, 400)
                    .setSize(20 * level, 80 * level, 120 * level, 400 * level)
                    .setPower(20, 80, 120, 400)
                    .setHitAbsorbs(level)
                    .build());
        }
    }
}
