package shaul.games.moo.model.Ship;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Research.Category;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesigner {
    private final IPlayer player;
    private final IGameLogic gameLogic;
    private static final Utils.Countable<ShipModule> NO_MODULE =
            new Utils.Countable<ShipModule>(ShipModule.EMPTY, 0);

    private ShipDesign.Builder builder;
    private int availableSpace;
    private Utils.Available<ShipModule> computerModule;

    public ShipDesigner(IGameLogic gameLogic, IPlayer player) {
        this.gameLogic = gameLogic;
        this.player = player;
        this.builder = resetBuilder(1);
        this.availableSpace = getTotalSpace(builder.getHullSize());
        this.availableSpace = calcAvailableSpace(builder.getHullSize());
    }

    public ShipDesign complete() {
        return builder.build();
    }

    public void setComputerModule(String name) {
        ShipModule module = player.getPlayerState().getTechnologies().getShipModule(name);
        Utils.check(module.getShipComponentType().equals(ShipModule.ShipComponent.COMPUTER));
        builder.setComputerSlot(module);
    }

    public void setShieldModule(String name) {
        ShipModule module = player.getPlayerState().getTechnologies().getShipModule(name);
        Utils.check(module.getShipComponentType().equals(ShipModule.ShipComponent.SHIELD));
        builder.setShieldSlot(module);
    }

    public void setEcmModule(String name) {
        ShipModule module = player.getPlayerState().getTechnologies().getShipModule(name);
        Utils.check(module.getShipComponentType().equals(ShipModule.ShipComponent.ECM));
        builder.setEcmSlot(module);
    }

    private void setComputerModule(ShipModule computerModule) {
        builder.setComputerSlot(computerModule);
    }

    public void setWeaponModule(int weaponSlot, ShipModule weapon, int count) {
        builder.setWeaponSlot(weaponSlot, new Utils.Countable<ShipModule>(weapon, count));
    }

    public List<HullType> getHullTypes() {
        return gameLogic.getTechnologyLogic().getAvailableHullTypes();
    }

    public boolean canChangeHullSize(int hullSize) {
        return getUsedSpace(hullSize) <= getTotalSpace(hullSize);
    }

    private int getTotalSpace(int hullSize) {
        int constructionTechLevel =
                player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
        // XXX: Fix
        return gameLogic.getTechnologyLogic().getHullTotalSpace(hullSize) * (100 + constructionTechLevel) / 100;
    }

    public int getUsedSpace() {
        return getUsedSpace(builder.getHullSize());
    }

    private int getUsedSpace(int hullSize) {
        int usedSpace = 0;
        usedSpace += getNonEngineModuleSpace(builder.getComputerSlot(), hullSize);
        usedSpace += getNonEngineModuleSpace(builder.getShieldSlot(), hullSize);
        usedSpace += getNonEngineModuleSpace(builder.getEcmSlot(), hullSize);
        return usedSpace;
    }

    public int getTotalSpace() {
        return getTotalSpace(builder.getHullSize());
    }

    public int getAvailableSpace() {
        return getTotalSpace() - getUsedSpace();
    }

    public int getAttackLevel() {
        return builder.build().getAttackLevel();
    }

    public int getHitsAbsorbs() {
        return builder.build().getHitsAbsorbs();
    }

    public int getMissleDefence() {
        return builder.build().getMissleDefence();
    }

    private int calcAvailableSpace(int hullSize) {
        //gameLogic.getTechnologyLogic()
        //for ()
        return 10000 * hullSize;
    }

    public void changeHullSize(int hullSize) {
        Utils.check(canChangeHullSize(hullSize));
        this.builder.setHullSize(hullSize);
    }

    public ShipModule getComputerModule() {
        return builder.getComputerSlot();
    }

    public ShipModule getShieldModule() {
        return builder.getShieldSlot();
    }

    public ShipModule getEcmModule() {
        return builder.getEcmSlot();
    }

    public List<Utils.Available<ShipModule>> getAvailableComputerModules() {
        ShipModule current = builder.getComputerSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Available<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_COMPUTER_MODULE)) {
            modules.add(new Utils.Available<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
        }
        return modules;
    }

    public List<Utils.Available<ShipModule>> getAvailableShieldModules() {
        ShipModule current = builder.getShieldSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Available<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_SHIELD_MODULE)) {
            modules.add(new Utils.Available<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
        }
        return modules;
    }

    public List<Utils.Available<ShipModule>> getAvailableEcmModules() {
        ShipModule current = builder.getEcmSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Available<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_ECM_MODULE)) {
            modules.add(new Utils.Available<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
        }
        return modules;
    }

    public List<Utils.Available<ShipModule>> getAvailableArmorModules() {
        ShipModule current = builder.getArmorSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Available<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_ARMOR_MODULE)) {
            modules.add(new Utils.Available<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
        }
        return modules;
    }

    public List<Utils.Countable<ShipModule>> getAvailableWeaponModulesAtSlot(int slot) {
        ShipModule current = builder.getWeaponSlot(slot);
        int maxExtraSpace = getAvailableSpace();
        maxExtraSpace -= getRequiredSpace(current) * builder.getWeaponSlotCount(slot);
        List<Utils.Countable<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_WEAPON_MODULE)) {
            Utils.check("Module need zero space " + module, getRequiredSpace(module) > 0);
            int count = maxExtraSpace / getRequiredSpace(module);
            modules.add(new Utils.Countable<ShipModule>(module, count));
        }
        return modules;
    }

    private int getRequiredSpace(ShipModule module) {
        return getRequiredSpaceForReplacing(ShipModule.EMPTY, module);
    }

    private int getRequiredSpaceForReplacing(ShipModule oldModule, ShipModule newModule) {
        int deltaSpace = 0;
        if (oldModule != null) {
            if (oldModule.getShipComponentType().equals(ShipModule.ShipComponent.ENGINE)) {
                //...
            } else {
                deltaSpace -= getNonEngineModuleSpace(oldModule, builder.getHullSize());
            }
        }
        if (newModule != ShipModule.EMPTY) {
            deltaSpace += getNonEngineModuleSpace(newModule, builder.getHullSize());
        }
        return deltaSpace;
    }

    private int getNonEngineModuleSpace(ShipModule module, int hullSize) {
        if (module == ShipModule.EMPTY) {
            return 0;
        }
        ShipModule.ShipData moduleData = module.getModuleData();
        int baseSize = moduleData.getSize(hullSize);
        if (baseSize == 0) {
            return baseSize;
        }
        return (int) (baseSize *
                gameLogic.getTechnologyLogic().getModuleCostReduction(
                        module.getName(), player.getPlayerState().getTechnologies()));
    }

    private ShipDesign.Builder resetBuilder(int hullSize) {
        return new ShipDesign.Builder()
                .setHullSize(hullSize)
                .setArmorSlot(player.getPlayerState().getTechnologies().getLowestArmor())
                .setEngineSlot(player.getPlayerState().getTechnologies().getLowestEngine())
                .setWeaponSlots(Arrays.asList(
                        ShipModule.ZERO_WEAPON, ShipModule.ZERO_WEAPON, ShipModule.ZERO_WEAPON, ShipModule.ZERO_WEAPON))
                .setSpecialSlots(Arrays.asList(ShipModule.NO_SPECIAL, ShipModule.NO_SPECIAL, ShipModule.NO_SPECIAL));
    }
}
