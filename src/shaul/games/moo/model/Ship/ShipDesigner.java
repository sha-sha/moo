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
    private int usedSpace;
    private int totalSpace;
    private Utils.Availalbe<ShipModule> computerModule;

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

    public void setComputerModule(ShipModule computerModule) {
        builder.setComputerSlot(computerModule);
    }

    public List<HullType> getHullTypes() {
        return gameLogic.getTechnologyLogic().getAvailableHullTypes();
    }

    public boolean canChangeHullSize(int hullSize) {
        return calcAvailableSpace(hullSize) <= getTotalSpace(hullSize);
    }

    public int getTotalSpace(int hullSize) {
        int constructionTechLevel =
                player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
        // XXX: Fix
        return gameLogic.getTechnologyLogic().getHullTotalSpace(hullSize) * (100 + constructionTechLevel) / 100;
    }

    public int getUsedSpace() {
        return usedSpace;
    }

    public int getTotalSpace() {
        return totalSpace;
    }

    public int getAvailableSpace() {
        return totalSpace - usedSpace;
    }

    private int calcAvailableSpace(int hullSize) {
        //gameLogic.getTechnologyLogic()
        //for ()
        return 10 * hullSize;
    }

    public void changeHullSize(int hullSize) {
        Utils.check(canChangeHullSize(hullSize));
        this.builder.setHullSize(hullSize);
    }

    public List<Utils.Availalbe<ShipModule>> getAvailableComputerModules() {
        ShipModule current = builder.getComputerSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Availalbe<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_COMPUTER_MODULE)) {
            modules.add(new Utils.Availalbe<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
        }
        return modules;
    }

    public List<Utils.Availalbe<ShipModule>> getAvailableArmorModules() {
        ShipModule current = builder.getArmorSlot();
        int maxExtraSpace = getAvailableSpace();
        List<Utils.Availalbe<ShipModule>> modules = new ArrayList<>();
        for (ShipModule module : player.getPlayerState().getTechnologies().getShipModule(
                TechnologiesDb.IS_ARMOR_MODULE)) {
            modules.add(new Utils.Availalbe<ShipModule>(module,
                    getRequiredSpaceForReplacing(current, module) <= maxExtraSpace));
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
                deltaSpace -= oldModule.getModuleData().getSpace(builder.getHullSize());
            }
        }
        deltaSpace += newModule.getModuleData().getSpace(builder.getHullSize());
        return deltaSpace;
    }

    private ShipDesign.Builder resetBuilder(int hullSize) {
        return new ShipDesign.Builder()
                .setHullSize(hullSize)
                .setArmorSlot(gameLogic.getTechnologyLogic().getLowestArmor())
                .setEngineSlot(gameLogic.getTechnologyLogic().getLowestEngine())
                .setWeaponSlots(Arrays.asList(NO_MODULE, NO_MODULE, NO_MODULE, NO_MODULE))
                .setSpecialSlots(Arrays.asList(ShipModule.EMPTY, ShipModule.EMPTY, ShipModule.EMPTY));
    }
}
