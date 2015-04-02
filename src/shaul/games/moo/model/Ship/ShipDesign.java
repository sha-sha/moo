package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesign {
    private final int hullSize;
    private final ShipModule computerSlot;
    private final ShipModule shieldSlot;
    private final ShipModule ecmSlot;
    private final ShipModule armorSlot;
    private final ShipModule engineSlot;
    private final ShipModule maneuverSlot;
    private final List<Utils.Countable<ShipModule>> weaponSlot;
    private final List<ShipModule> specialSlot;

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
        this.computerSlot = computerSlot;
        this.shieldSlot = shieldSlot;
        this.ecmSlot = ecmSlot;
        this.armorSlot = armorSlot;
        this.engineSlot = engineSlot;
        this.maneuverSlot = maneuverSlot;
        this.weaponSlot = new ArrayList<Utils.Countable<ShipModule>>(weaponSlot);
        this.specialSlot = new ArrayList<ShipModule>(specialSlot);
    }

    public static class Builder {
        private int hullSize;
        private ShipModule computerSlot;
        private ShipModule shieldSlot;
        private ShipModule ecmSlot;
        private ShipModule armorSlot;
        private ShipModule engineSlot;
        private ShipModule maneuverSlot;
        private List<Utils.Countable<ShipModule>> weaponSlots;
        private List<ShipModule> specialSlots;

        public Builder setHullSize(int hullSize) {
            this.hullSize = hullSize;
            return this;
        }

        public int getHullSize() {
            return hullSize;
        }

        public Builder setComputerSlot(ShipModule computerSlot) { this.computerSlot = computerSlot;
            return this;
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

        public ShipModule getComputerSlot() {
            return computerSlot;
        }
    }
}
