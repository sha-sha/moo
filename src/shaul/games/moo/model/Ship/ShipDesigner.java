package shaul.games.moo.model.Ship;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Research.Category;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesigner {
    private final IPlayer player;
    private final IGameLogic gameLogic;

    private int currentHullSize;
    List<Utils.LimitedCountable<Module>> modules;
    private int availableSpace;

    public ShipDesigner(IGameLogic gameLogic, IPlayer player) {
        this.gameLogic = gameLogic;
        this.player = player;
        this.currentHullSize = 1;
        this.availableSpace = getTotalSpace(this.currentHullSize);
        this.modules = calcAvailableModules(currentHullSize, new ArrayList<Utils.LimitedCountable<Module>>());
        this.availableSpace = calcAvailableSpace(this.currentHullSize);
    }

    public List<HullType> getHullTypes() {
        return gameLogic.getTechnologyLogic().getAvailableHullTypes();
    }

    public boolean canChangeHullType(int hullSize) {
        return calcAvailableSpace(hullSize) <= getTotalSpace(hullSize);
    }

    public int getTotalSpace(int hullSize) {
        int constructionTechLevel =
                player.getPlayerState().getTechnologies().getTechLevel(Category.Construction.getName());
        // XXX: Fix
        return gameLogic.getTechnologyLogic().getHullTotalSpace(hullSize) * (100 + constructionTechLevel) / 100;
    }

    private int calcAvailableSpace(int hullSize) {
        //gameLogic.getTechnologyLogic()
        //for ()
        return 10 * hullSize;
    }

    private List<Utils.LimitedCountable<Module>> calcAvailableModules(
            int hullSize,
            List<Utils.LimitedCountable<Module>> current) {
        int currentUsedSpace = 0;
        for (Utils.LimitedCountable<Module> limitedCountable : current) {
            currentUsedSpace += limitedCountable.get().getModuleData().getSpace()
        }
    }

    public void changeHullSize(int hullSize) {
        Utils.check(canChangeHullType(hullSize));
        this.currentHullSize = hullSize;
    }

    public List<Utils.LimitedCountable<Module>> getComputerModules() {

    }


}
