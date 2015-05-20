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
public class NewShipDesigner {
    private final IPlayer player;
    private final IGameLogic gameLogic;

    private static final Utils.Countable<ShipModule> NO_MODULE =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    private ShipDesign.Builder builder;
    private int totalSpace;
    private int usedSpace;


    public NewShipDesigner(IGameLogic gameLogic, IPlayer player) {
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
        builder.hullSize = 1;
        builder.shipModules.put(ShipDesign.SlotType.Armor,
                player.getPlayerState().getTechnologies().getLowestArmor());
        builder.shipModules.put(ShipDesign.SlotType.Computer, ShipModule.NO_COMPUTER);
        builder.shipModules.put(ShipDesign.SlotType.Engine,
                player.getPlayerState().getTechnologies().getLowestEngine());
        builder.shipModules.put(ShipDesign.SlotType.Ecm, ShipModule.NO_ECM);
        builder.shipModules.put(ShipDesign.SlotType.Shield, ShipModule.NO_SHIELD);
        builder.shipModules.put(ShipDesign.SlotType.Special1, ShipModule.NO_SPECIAL);
        builder.shipModules.put(ShipDesign.SlotType.Special2, ShipModule.NO_SPECIAL);
        builder.shipModules.put(ShipDesign.SlotType.Special3, ShipModule.NO_SPECIAL);
        builder.shipModules.put(ShipDesign.SlotType.Weapon1, ShipModule.NO_WEAPON);
        builder.shipModules.put(ShipDesign.SlotType.Weapon2, ShipModule.NO_WEAPON);
        builder.shipModules.put(ShipDesign.SlotType.Weapon3, ShipModule.NO_WEAPON);
        builder.shipModules.put(ShipDesign.SlotType.Weapon4, ShipModule.NO_WEAPON);
        return builder;
    }

    public int getRequiredSpaceForModule(ShipModule shipModule) {
        return 0;
    }

    public List<TechModule> getAvailableModules(Class<? extends ShipModule> type) {
        return player.getPlayerState().getModulesOfType(type);
    }

    public List<ShipModule> getInstalledModules(Class<? extends ShipModule> type) {
        List<ShipModule> shipModules = new ArrayList<>();
        ShipModule[] singleSlots = {builder.computerSlot, builder.ecmSlot};
        for (ShipModule module : singleSlots) {
            if (type.isInstance(module)) {
                shipModules.add(module);
            }
        }
        return shipModules;
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
        int currentModuleSpace = getSpaceOfModule(getCurrentModule(slotType), builder.getHullSize());
        int newModuleSpace = getSpaceOfModule(module, builder.getHullSize());

        return (totalSpace - usedSpace) >= (newModuleSpace - currentModuleSpace);
    }

    public boolean install(ShipDesign.SlotType slotType, ShipModule module) {
        if (canInstall(slotType, module)) {
            builder.shipModules.put(slotType, module);
            update();
            return true;
        }
        return false;
    }

    public ShipModule getCurrentModule(ShipDesign.SlotType slotType) {
        return builder.shipModules.get(slotType);
    }

    private int getSpaceOfModule(ShipModule shipModule, int hullSize) {
        return shipModule.getSpace(player.getPlayerState(), hullSize);
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public int getUsedSpace() {
        return usedSpace;
    }
    private void update() {
        totalSpace = getTotalSpace(builder.getHullSize());
        usedSpace = 0;
        for (ShipModule module : builder.shipModules.values()) {
            usedSpace += getSpaceOfModule(module, builder.getHullSize());
        }
    }

    private int getTotalSpace(int hullSize) {
        int constructionTechLevel =
                player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
        // XXX: Fix
        return gameLogic.getTechnologyLogic().getHullTotalSpace(hullSize) * (100 + constructionTechLevel) / 100;
    }

}
