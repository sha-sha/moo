package shaul.games.moo.model.Ship;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Utils;

import java.util.*;

/**
 * Created by Shaul on 5/16/2015.
 */
public class ShipDesigner {
    private final IPlayer player;
    private final IGameLogic gameLogic;

    private static final int MIN_MANEUVER = 1;
    private static final Utils.Countable<ShipModule> NO_MODULE =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    private ShipDesign.Builder builder;
    private int totalSpace;
    private int usedSpace;
    private int cost;
    private int requiredPower;
    private int maxManeuverability = MIN_MANEUVER;

    public ShipDesigner(IGameLogic gameLogic, IPlayer player) {
        this.gameLogic = gameLogic;
        this.player = player;
        this.maxManeuverability = MIN_MANEUVER;
        this.builder = resetBuilder(Hull.Small);
        update();
    }

    public int getAttackLevel() {
        return builder.build().getAttackLevel();
    }

    public int getHitPoints() {
        return builder.build().getHitPoints();
    }

    public int getHitsAbsorbs() {
        return builder.build().getHitsAbsorbs();
    }

    public int getMissleDefence() {
        return builder.build().getMissleDefence();
    }

    private ShipDesign.Builder resetBuilder(Hull hull) {
        ShipDesign.Builder builder = new ShipDesign.Builder();
        ShipModule.EngineShipModule engine =
                (ShipModule.EngineShipModule) player.getPlayerState().getTechnologies().getLowestEngine();
        builder.hull = hull;
        builder.set(ShipDesign.SlotType.Armor,
                player.getPlayerState().getTechnologies().getLowestArmor());
        builder.set(ShipDesign.SlotType.Computer, ShipModule.NO_COMPUTER);
        builder.set(ShipDesign.SlotType.Engine, engine);
        builder.set(ShipDesign.SlotType.Maneuver, engine.getManeuverShipModules().get(0));
        builder.set(ShipDesign.SlotType.Ecm, ShipModule.NO_ECM);
        builder.set(ShipDesign.SlotType.Shield, ShipModule.NO_SHIELD);
        builder.set(ShipDesign.SlotType.Special1, ShipModule.NO_SPECIAL);
        builder.set(ShipDesign.SlotType.Special2, ShipModule.NO_SPECIAL);
        builder.set(ShipDesign.SlotType.Special3, ShipModule.NO_SPECIAL);
        builder.set(ShipDesign.SlotType.Weapon1, ShipModule.NO_WEAPON);
        builder.set(ShipDesign.SlotType.Weapon2, ShipModule.NO_WEAPON);
        builder.set(ShipDesign.SlotType.Weapon3, ShipModule.NO_WEAPON);
        builder.set(ShipDesign.SlotType.Weapon4, ShipModule.NO_WEAPON);
        return builder;
    }

    public int getRequiredSpaceForModule(ShipModule shipModule) {
        return 0;
    }

    public List<ShipModule> getAvailableModules(Class<? extends ShipModule> type) {
        List<ShipModule> modules = new ArrayList<>();
        for (TechModule techModule : player.getPlayerState().getModulesOfType(type)) {
            if (techModule instanceof ShipModule) {
                modules.add((ShipModule) techModule);
            }
        }
        ShipModule.EngineShipModule engine = (ShipModule.EngineShipModule) getCurrentModule(ShipDesign.SlotType.Engine);
        for (ShipModule shipModule : engine.getManeuverShipModules()) {
            if (type.isInstance(shipModule)) {
                modules.add(shipModule);
            }
        }
        Collections.sort(modules, new Comparator<ShipModule>() {
            @Override
            public int compare(ShipModule o1, ShipModule o2) {
                if (o1.getTechLevel() == o2.getTechLevel()) {
                    return o1.getBasicCost(Hull.Huge) - o2.getBasicCost(Hull.Huge);
                }
                return o1.getTechLevel() - o2.getTechLevel();
            }
        });
        return modules;
    }

    public boolean canChangeCount(ShipDesign.SlotType slotType, int newCount) {
        if (!slotType.canStack) { return false; }
        if (getCurrentModule(slotType).isEmpty()) { return false; }
        int currentModuleSpace = getCurrentModuleCount(slotType) *
                getSpaceOfModule(getCurrentModule(slotType), builder.getHull());
        int newModuleSpace = newCount * getSpaceOfModule(getCurrentModule(slotType), builder.getHull());
        return (totalSpace - usedSpace) >= (newModuleSpace - currentModuleSpace);
    }

    public boolean changeCount(ShipDesign.SlotType slotType, int count) {
        if (canChangeCount(slotType, count)) {
            builder.set(slotType, builder.get(slotType), count);
            update();
            return true;
        }
        return false;
    }

    public boolean canInstall(ShipDesign.SlotType slotType, ShipModule module) {
        // Check if module can be installed in the slot.
        if (!ShipDesign.isModuleAllowed(slotType, module)) {
            return false;
        }
        // Check if already installed in this slot.
        if (getCurrentModule(slotType).equals(module)) {
            return true;
        }

        if (module.isEmpty()) {
            return true;
        }

        if (!slotType.canExistInOtherSlots) {
            for (ShipDesign.SlotType otherSlot : ShipDesign.SlotType.values()) {
                ShipModule other = getCurrentModule(otherSlot);
                if (other.equals(module)) {
                    return false;
                }
                if (other.getExclusionGroup().equals(module.getExclusionGroup()) &&
                        module.getExclusionGroup() != ShipModule.ExclusionGroup.None) {
                    return false;
                }
            }
        }

        // TODO: When changing engine module, we also need to change maneuver module.


        int currentModuleSpace = getSpaceOfModule(getCurrentModule(slotType), builder.getHull());
        int newModuleSpace = getSpaceOfModule(module, builder.getHull());

        return (totalSpace - usedSpace) >= (newModuleSpace - currentModuleSpace);
    }

    public boolean install(ShipDesign.SlotType slotType, ShipModule module) {
        if (canInstall(slotType, module)) {
            builder.set(slotType, module);
            update();
            return true;
        }
        return false;
    }

    public ShipModule getCurrentModule(ShipDesign.SlotType slotType) {
        return builder.get(slotType);
    }

    public int getCurrentModuleCount(ShipDesign.SlotType slotType) {
        return builder.getCount(slotType);
    }

    public Hull getHull() {
        return builder.getHull();
    }

    public boolean setHull(Hull hull) {
        if (!canChangeHull(hull)) { return false; }
        builder.setHull(hull);
        update();
        return true;
    }

    private int getSpaceOfModule(ShipModule shipModule, Hull hull) {
        return shipModule.getSize(player.getPlayerState(), hull);
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public int getRequiredPower() {
        return requiredPower;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    public void update() {
        update(false);
    }

    public void update(boolean inDebugMode) {
        totalSpace = builder.getHull().getSpace(player.getPlayerState());
        usedSpace = 0;
        cost = 0; // should be hull cost.
        requiredPower = 0;
        maxManeuverability = MIN_MANEUVER;

        if (inDebugMode) {
            validateAllModules();
        }

        ShipModule.EngineShipModule engine = (ShipModule.EngineShipModule) builder.get(ShipDesign.SlotType.Engine);
        for (Utils.Countable<ShipModule> module : builder.getModules()) {
            if (module.get() == engine) {
                continue;
            }
            usedSpace += module.getCount() * getSpaceOfModule(module.get(), builder.getHull());
            cost += module.getCount() * getCostOfModule(module.get(), builder.getHull(), engine);
            maxManeuverability = Math.max(maxManeuverability, module.get().getModuleData().getOptionalManeuvers());
            requiredPower += module.getCount() * module.get().getModuleData().getPower(builder.getHull());
        }

        double numEngines = (double) requiredPower / engine.getModuleData().getGeneratedPower();
        usedSpace += numEngines * getSpaceOfModule(engine, builder.getHull());
        cost += numEngines * getCostOfModule(engine, builder.getHull(), engine);
        maxManeuverability = Math.max(maxManeuverability, engine.getModuleData().getOptionalManeuvers());

        // Re-apply maneuverability in case engine had changed.
        int currCombatSpeed = builder.get(ShipDesign.SlotType.Maneuver).getModuleData().getCombatSpeed();
        builder.set(ShipDesign.SlotType.Maneuver, getManeuverModule(engine, currCombatSpeed));
    }

    private void validateAllModules() {
        for (ShipDesign.SlotType slotType : ShipDesign.SlotType.values()) {
            ShipModule curr = builder.get(slotType);
            if (curr.isEmpty()) {
                continue;
            }
            List<ShipModule> modules = getAvailableModules(ShipDesign.getAllowedModule(slotType));
            if (!modules.contains(curr) || !canInstall(slotType, curr)) {
                for (ShipModule m : modules) {
                    if (canInstall(slotType, m)) {
                        builder.set(slotType, m);
                        break;
                    }
                }
            }
        }
    }

    private ShipModule getManeuverModule(ShipModule engine, int combatSpeed) {
        Utils.check(engine instanceof ShipModule.EngineShipModule,
                engine.getClass() + "is not ShipModule.EngineShipModule!");
        List<ShipModule.ManeuverShipModule> maneuverShipModules =
                ((ShipModule.EngineShipModule) engine).getManeuverShipModules();
        ShipModule maxManeuverShipModule = maneuverShipModules.get(0);
        for (ShipModule.ManeuverShipModule maneuverShipModule: maneuverShipModules) {
            if (combatSpeed == maneuverShipModule.getModuleData().getCombatSpeed()) {
                return maneuverShipModule;
            }
            if (maxManeuverShipModule.getModuleData().getCombatSpeed() <
                    maneuverShipModule.getModuleData().getCombatSpeed()) {
                maxManeuverShipModule = maneuverShipModule;
            }
        }
        //Utils.fail("No maneuver of combat speed " + combatSpeed);
        return maxManeuverShipModule;
    }

    private int getCostOfModule(ShipModule shipModule, Hull hull, ShipModule engine) {
        Utils.check(engine instanceof ShipModule.EngineShipModule, "engine is not ShipModule.EngineShipModule!");
        return shipModule.getCost(player.getPlayerState(), hull, (ShipModule.EngineShipModule) engine);
    }

    //private int getTotalSpace(Hull hull) {
    //    int constructionTechLevel =
    //            player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
    //    // XXX: Fix
    //    return gameLogic.getTechnologyLogic().getHullTotalSpace(hull) * (100 + constructionTechLevel) / 100;
    //}

    public boolean canChangeHull(Hull hull) {
        int usedSpace = 0;
        for (Utils.Countable<ShipModule> module : builder.getModules()) {
            usedSpace += module.getCount() * getSpaceOfModule(module.get(), hull);
        }
        return hull.getSpace(player.getPlayerState()) >= usedSpace;
    }

    public int getCost() {
        return cost;
    }

    public int getWrapSpeed() {
        return builder.build().getWrapSpeed();
    }

    public int getCombatSpeed() {
        return builder.build().getCombatSpeed();
    }

    public int getManeuverability() {
        return builder.build().getManeuverability();
    }

    public void reset(Hull hull) {
        this.builder = resetBuilder(hull);
        update();
    }
}
