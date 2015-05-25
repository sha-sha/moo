package shaul.games.moo.model.Ship;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Research.Category;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Utils;

import java.util.*;

/**
 * Created by Shaul on 5/16/2015.
 */
public class ShipDesigner {
    private final IPlayer player;
    private final IGameLogic gameLogic;

    private static final Utils.Countable<ShipModule> NO_MODULE =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    private ShipDesign.Builder builder;
    private int totalSpace;
    private int usedSpace;


    public ShipDesigner(IGameLogic gameLogic, IPlayer player) {
        this.gameLogic = gameLogic;
        this.player = player;
        this.builder = resetBuilder(1);
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

    private ShipDesign.Builder resetBuilder(int hullSize) {
        ShipDesign.Builder builder = new ShipDesign.Builder();
        builder.hull = ShipModule.HullType.Tiny;
        builder.set(ShipDesign.SlotType.Armor,
                player.getPlayerState().getTechnologies().getLowestArmor());
        builder.set(ShipDesign.SlotType.Computer, ShipModule.NO_COMPUTER);
        builder.set(ShipDesign.SlotType.Engine,
                player.getPlayerState().getTechnologies().getLowestEngine());
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

    public List<TechModule> getAvailableModules(Class<? extends ShipModule> type) {
        return player.getPlayerState().getModulesOfType(type);
    }

    public boolean canInstall(ShipDesign.SlotType slotType, ShipModule module) {
        // Check if module can be installed in the slot.
        if (!ShipDesign.isModuleAllowed(slotType, module)) {
            return false;
        }
        // Check if already installed.
        if (getCurrentModule(slotType).equals(module)) {
            return true;
        }
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

    public ShipModule.HullType getHull() {
        return builder.getHull();
    }

    public boolean setHull(ShipModule.HullType hull) {
        if (!canChangeHull(hull)) { return false; }
        builder.setHull(hull);
        update();
        return true;
    }

    private int getSpaceOfModule(ShipModule shipModule, ShipModule.HullType hull) {
        return shipModule.getSpace(player.getPlayerState(), hull);
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    private void update() {
        totalSpace = getTotalSpace(builder.getHull());
        usedSpace = 0;
        for (Utils.Countable<ShipModule> module : builder.getModules()) {
            usedSpace += module.getCount() * getSpaceOfModule(module.get(), builder.getHull());
        }
    }

    private int getTotalSpace(ShipModule.HullType hull) {
        int constructionTechLevel =
                player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
        // XXX: Fix
        return gameLogic.getTechnologyLogic().getHullTotalSpace(hull) * (100 + constructionTechLevel) / 100;
    }

    public boolean canChangeHull(ShipModule.HullType hull) {
        int usedSpace = 0;
        for (Utils.Countable<ShipModule> module : builder.getModules()) {
            usedSpace += module.getCount() * getSpaceOfModule(module.get(), hull);
        }
        return getTotalSpace(hull) >= usedSpace;
    }
}
