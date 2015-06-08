package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Utils;

import java.util.*;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesign {
    public static final Utils.Countable<ShipModule> NO_WEAPON =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    public enum SlotType {
        Computer(false, false),
        Shield(false, false),
        Ecm(false, false),
        Armor(false, false),
        Engine(false, false),
        Maneuver(false, false),
        Weapon1(true, true),
        Weapon2(true, true),
        Weapon3(true, true),
        Weapon4(true, true),
        Special1(false, false),
        Special2(false, false),
        Special3(false, false);

        final boolean canStack;
        final boolean canExistInOtherSlots;

        SlotType(boolean canStack, boolean canExistInOtherSlots) {
            this.canStack = canStack;
            this.canExistInOtherSlots = canExistInOtherSlots;
        }
    };

    private static final Map<SlotType, Class> ALLOWED_MODULES = new HashMap<>();
    static {
        ALLOWED_MODULES.put(SlotType.Computer, ShipModule.ComputerShipModule.class);
        ALLOWED_MODULES.put(SlotType.Shield, ShipModule.ShieldShipModule.class);
        ALLOWED_MODULES.put(SlotType.Ecm, ShipModule.EcmShipModule.class);
        ALLOWED_MODULES.put(SlotType.Armor, ShipModule.ArmorShipModule.class);
        ALLOWED_MODULES.put(SlotType.Engine, ShipModule.EngineShipModule.class);
        ALLOWED_MODULES.put(SlotType.Maneuver, ShipModule.ManeuverShipModule.class);
        ALLOWED_MODULES.put(SlotType.Weapon1, ShipModule.WeaponShipModule.class);
        ALLOWED_MODULES.put(SlotType.Weapon2, ShipModule.WeaponShipModule.class);
        ALLOWED_MODULES.put(SlotType.Weapon3, ShipModule.WeaponShipModule.class);
        ALLOWED_MODULES.put(SlotType.Weapon4, ShipModule.WeaponShipModule.class);
        ALLOWED_MODULES.put(SlotType.Special1, ShipModule.SpecialShipModule.class);
        ALLOWED_MODULES.put(SlotType.Special2, ShipModule.SpecialShipModule.class);
        ALLOWED_MODULES.put(SlotType.Special3, ShipModule.SpecialShipModule.class);
    }

    public static boolean isModuleAllowed(SlotType slotType, ShipModule shipModule) {
        return ALLOWED_MODULES.get(slotType).isInstance(shipModule);
    }

    public static Class getAllowedModule(SlotType slotType) {
        return Utils.checkNotNull(ALLOWED_MODULES.get(slotType), "Slot " + slotType + " allows no module!");
    }

    private final Map<SlotType, Utils.Countable<ShipModule>> shipModules;

    private final Hull hull;
    private final int attackLevel;
    private final int hitAbsorbs;
    private final int missleDefence;
    private final int hitPoints;
    private final int wrapSpeed;
    private final int combatSpeed;


    ShipDesign(Hull hull, Map<SlotType, Utils.Countable<ShipModule>> shipModules) {
        this.hull = hull;
        this.shipModules = new HashMap<>(shipModules);
        List<Utils.Countable<ShipModule>> allModules = new ArrayList<>(shipModules.values());

        int attackLevel = 0;
        int hitAbsorbs = 0;
        int missleDefence = 0;
        int wrapSpeed = 0;
        int combatSpeed = 0;
        double hitPointModifier = 1.0;
        for (Utils.Countable<ShipModule> moduleCountable : allModules) {
            Utils.assertNotNull(moduleCountable);
            ShipModule module = Utils.checkNotNull(moduleCountable.get());
            Utils.check("Module " + module + " has no data", module.getModuleData() != null);
            attackLevel += module.getModuleData().getAttackLevel();
            hitAbsorbs += module.getModuleData().getHitAbsorbs();
            missleDefence += module.getModuleData().getMissileDefence();
            hitPointModifier *= module.getModuleData().getShipHitPointsModifier();
            wrapSpeed += module.getModuleData().getWrapSpeed();
            combatSpeed += module.getModuleData().getCombatSpeed();
        }
        this.attackLevel = attackLevel;
        this.hitAbsorbs = hitAbsorbs;
        this.missleDefence = missleDefence;
        this.hitPoints = (int) Math.floor(hull.hits * hitPointModifier);
        this.wrapSpeed = wrapSpeed;
        this.combatSpeed = combatSpeed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ship: {hull: " + hull);
        for (SlotType t : shipModules.keySet()) {
            if (shipModules.get(t).getCount() > 1) {
                sb.append(String.format(
                        " , %s: %d of %s", t, shipModules.get(t).getCount(), shipModules.get(t).get().getName()));
            } else {
                sb.append(String.format(" , %s: %s", t, shipModules.get(t).get().getName()));
            }
        }
        return sb.toString();
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getHitsAbsorbs() {
        return hitAbsorbs;
    }

    public int getMissleDefence() {
        return missleDefence;
    }

    public int getWrapSpeed() {
        return wrapSpeed;
    }

    public int getCombatSpeed() {
        return combatSpeed;
    }

    public static class Builder {
        public Hull hull;
        private Map<SlotType, Utils.Countable<ShipModule>> shipModules = new HashMap<>();

        public Builder set(SlotType slotType, ShipModule shipModule) {
            shipModules.put(slotType, Utils.Countable.of(shipModule));
            return this;
        }

        public Builder set(SlotType slotType, ShipModule shipModule, int count) {
            shipModules.put(slotType, Utils.Countable.of(shipModule, count));
            return this;
        }

        public ShipModule get(SlotType slotType) {
            return shipModules.get(slotType).get();
        }

        public int getCount(SlotType slotType) {
            return shipModules.get(slotType).getCount();
        }

        public Collection<Utils.Countable<ShipModule>> getModules() {
            return shipModules.values();
        }

        public Builder setHull(Hull hull) {
            this.hull = hull;
            return this;
        }

        public Hull getHull() {
            return hull;
        }

        public ShipDesign build() {
            return new ShipDesign(hull, shipModules);
        }

    }
}
