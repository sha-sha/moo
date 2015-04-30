package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesign {
    public static final Utils.Countable<ShipModule> NO_WEAPON =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    private final int hullSize;
    private final ShipModule computerSlot;
    private final ShipModule shieldSlot;
    private final ShipModule ecmSlot;
    private final ShipModule armorSlot;
    private final ShipModule engineSlot;
    private final ShipModule maneuverSlot;
    private final List<Utils.Countable<ShipModule>> weaponSlot;
    private final List<ShipModule> specialSlot;
    private final int attackLevel;

    ShipDesign(int hullSize,
               ShipModule computerSlot,
               ShipModule shieldSlot,
               ShipModule ecmSlot,
               ShipModule armorSlot,
               ShipModule engineSlot,
               ShipModule maneuverSlot,
               List<Utils.Countable<ShipModule>> weaponSlot,
               List<ShipModule> specialSlot) {
        this.hullSize = hullSize;
        this.computerSlot = Utils.checkNotNull(computerSlot);
        this.shieldSlot = Utils.checkNotNull(shieldSlot);
        this.ecmSlot = Utils.checkNotNull(ecmSlot);
        this.armorSlot = Utils.checkNotNull(armorSlot);
        this.engineSlot = Utils.checkNotNull(engineSlot);
        this.maneuverSlot = Utils.checkNotNull(maneuverSlot);
        this.weaponSlot = new ArrayList<Utils.Countable<ShipModule>>(weaponSlot);
        this.specialSlot = new ArrayList<ShipModule>(Utils.checkNotNull(specialSlot));

        List<ShipModule> allModules = new ArrayList<>(Arrays.asList(
                computerSlot, shieldSlot, ecmSlot, armorSlot, engineSlot, maneuverSlot));
        allModules.addAll(specialSlot);

        int attackLevel = 0;
        for (ShipModule module : allModules) {
            Utils.assertNotNull(module);
            Utils.check("Module " + module + " has no data", module.getModuleData() != null);
            attackLevel += module.getModuleData().getAttackLevel();
        }

        this.attackLevel = attackLevel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ship: {hull: " + hullSize);
        if (computerSlot != null) { sb.append(", computer: " + computerSlot.getName()); }
        if (shieldSlot != null) { sb.append(", shield: " + shieldSlot.getName()); }
        if (ecmSlot != null) { sb.append(", ecm: " + ecmSlot.getName()); }
        if (armorSlot != null) { sb.append(", armor: " + armorSlot.getName()); }
        if (engineSlot != null) { sb.append(", engine: " + engineSlot.getName()); }
        if (maneuverSlot != null) { sb.append(", maneuver: " + maneuverSlot.getName()); }
        sb.append(", weapons: { ");
        for (int i = 0 ; i < weaponSlot.size(); i++) {
            Utils.Countable<ShipModule> weapon = weaponSlot.get(i);
            if (weapon.getCount() > 0 && weapon.get() != ShipModule.EMPTY) {
                sb.append(weapon.getCount() + ":'" + weapon.get().getName() + "' ");
            }
        }
        sb.append("} ");
        sb.append(", special: { ");
        for (int i = 0 ; i < specialSlot.size(); i++) {
            ShipModule special = specialSlot.get(i);
            if (special != ShipModule.EMPTY) {
                sb.append("'" + special.getName() + "' ");
            }
        }
        sb.append("} ");
        sb.append("}");
        return sb.toString();
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public static class Builder {

        private int hullSize;
        private ShipModule computerSlot = ShipModule.NO_COMPUTER;
        private ShipModule shieldSlot = ShipModule.NO_SHIELD;
        private ShipModule ecmSlot = ShipModule.NO_ECM;
        private ShipModule armorSlot = ShipModule.EMPTY;
        private ShipModule engineSlot = ShipModule.EMPTY;
        private ShipModule maneuverSlot = ShipModule.EMPTY;
        private List<Utils.Countable<ShipModule>> weaponSlots =
                Arrays.asList(NO_WEAPON, NO_WEAPON, NO_WEAPON, NO_WEAPON);
        private List<ShipModule> specialSlots =
                Arrays.asList(ShipModule.EMPTY, ShipModule.EMPTY, ShipModule.EMPTY);

        public Builder setHullSize(int hullSize) {
            this.hullSize = hullSize;
            return this;
        }

        public int getHullSize() {
            return hullSize;
        }

        public Builder setComputerSlot(ShipModule computerSlot) {
            this.computerSlot = computerSlot;
            return this;
        }

        public ShipModule getComputerSlot() {
            return computerSlot;
        }

        public Builder setShieldSlot(ShipModule shieldSlot) {
            this.shieldSlot = shieldSlot;
            return this;
        }

        public Builder setEcmSlot(ShipModule ecmSlot) {
            this.ecmSlot = ecmSlot;
            return this;
        }

        public Builder setArmorSlot(ShipModule armorSlot) {
            this.armorSlot = armorSlot;
            return this;
        }

        public ShipModule getArmorSlot() {
            return armorSlot;
        }

        public Builder setEngineSlot(ShipModule engineSlot) {
            this.engineSlot = engineSlot;
            return this;
        }

        public Builder setManeuverSlot(ShipModule maneuverSlot) {
            this.maneuverSlot = maneuverSlot;
            return this;
        }

        public Builder setWeaponSlots(List<Utils.Countable<ShipModule>> weaponSlots) {
            this.weaponSlots = weaponSlots;
            return this;
        }

        public Builder setWeaponSlot(int index, Utils.Countable<ShipModule> weaponSlot) {
            Utils.check(index >= 0 && index < weaponSlots.size());
            this.weaponSlots.set(index, weaponSlot);
            return this;
        }

        public int getWeaponSlotCount(int index) {
            Utils.check(index >= 0 && index < weaponSlots.size());
            return this.weaponSlots.get(index).getCount();
        }

        public ShipModule getWeaponSlot(int index) {
            Utils.check(index >= 0 && index < weaponSlots.size());
            return this.weaponSlots.get(index).get();
        }

        public Builder setWeaponSlotCount(int index, int newCount) {
            Utils.check(index >= 0 && index < weaponSlots.size());
            Utils.check(newCount >= 0);
            this.weaponSlots.set(index, new Utils.Countable<ShipModule>(
                    this.weaponSlots.get(index).get(), newCount));
            return this;
        }

        public Builder setSpecialSlots(List<ShipModule> specialSlots) {
            this.specialSlots = specialSlots;
            return this;
        }

        public Builder setSpecialSlot(int index, ShipModule specialSlot) {
            Utils.check(index >= 0 && index < specialSlots.size());
            this.specialSlots.set(index, specialSlot);
            return this;
        }

        public ShipDesign build() {
            return new ShipDesign(hullSize, computerSlot, shieldSlot, ecmSlot, armorSlot, engineSlot,
                    maneuverSlot, weaponSlots, specialSlots);
        }
    }
}
