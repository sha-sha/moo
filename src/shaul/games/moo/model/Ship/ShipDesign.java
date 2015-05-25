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
        Computer(false),
        Shield(false),
        Ecm(false),
        Armor(false),
        Engine(false),
        Weapon1(true),
        Weapon2(true),
        Weapon3(true),
        Weapon4(true),
        Special1(false),
        Special2(false),
        Special3(false);

        final boolean canStack;

        SlotType(boolean canStack) {
            this.canStack = canStack;
        }
    };

    private static final Map<SlotType, Class> ALLOWED_MODULES = new HashMap<>();
    static {
        ALLOWED_MODULES.put(SlotType.Computer, ShipModule.ComputerShipModule.class);
        ALLOWED_MODULES.put(SlotType.Shield, ShipModule.ShieldShipModule.class);
        ALLOWED_MODULES.put(SlotType.Ecm, ShipModule.EcmShipModule.class);
        ALLOWED_MODULES.put(SlotType.Armor, ShipModule.ArmorShipModule.class);
        ALLOWED_MODULES.put(SlotType.Engine, ShipModule.EngineShipModule.class);
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
        return ALLOWED_MODULES.get(slotType);
    }

    private final Map<SlotType, Utils.Countable<ShipModule>> shipModules;

    private final ShipModule.HullType hull;
    private final int attackLevel;
    private final int hitAbsorbs;
    private final int missleDefence;
    private final int hitPoints;

    ShipDesign(ShipModule.HullType hull, Map<SlotType, Utils.Countable<ShipModule>> shipModules) {
        this.hull = hull;
        this.shipModules = new HashMap<>(shipModules);
        List<Utils.Countable<ShipModule>> allModules = new ArrayList<>(shipModules.values());

        int attackLevel = 0;
        int hitAbsorbs = 0;
        int missleDefence = 0;
        int hitPoints = 0;
        for (Utils.Countable<ShipModule> moduleCountable : allModules) {
            Utils.assertNotNull(moduleCountable);
            ShipModule module = Utils.checkNotNull(moduleCountable.get());
            Utils.check("Module " + module + " has no data", module.getModuleData() != null);
            attackLevel += module.getModuleData().getAttackLevel();
            hitAbsorbs += module.getModuleData().getHitAbsorbs();
            missleDefence += module.getModuleData().getMissileDefence();
            hitPoints += module.getModuleData().getShipHitPoints(hull);
        }

        this.attackLevel = attackLevel;
        this.hitAbsorbs = hitAbsorbs;
        this.missleDefence = missleDefence;
        this.hitPoints = hitPoints;
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

    public static class Builder {
        public ShipModule.HullType hull;
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

        public Builder setHull(ShipModule.HullType hull) {
            this.hull = hull;
            return this;
        }

        public ShipModule.HullType getHull() {
            return hull;
        }

        public ShipDesign build() {
            return new ShipDesign(hull, shipModules);
        }

    }
}
