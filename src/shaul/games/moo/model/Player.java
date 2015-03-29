package shaul.games.moo.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Shaul on 2/25/2015.
 */
public class xPlayer {

    public static final Utils.ElementKey<String, xPlayer> ELEMENT_KEY =
            new Utils.ElementKey<String, xPlayer>() {
        @Override
        public String getKey(xPlayer player) {
            return player.getPlayerInfo().getName();
        }
    };

    private PlayerInfo playerInfo;
    private List<ShipDesign> shipDesigns;

    // science tree
    // science discoveries
    // current research and current progress

    private List<Fleet> fleets;

    // helpers
    private Map<Long, Fleet> fleetMap;

    public xPlayer(PlayerInfo playerInfo, List<Fleet> fleets) {
        this.playerInfo = playerInfo;
        this.fleets = fleets;
        fleetMap = Utils.map(fleets, Fleet.ELEMENT_KEY);
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public List<Fleet> getFleets() {
        return fleets;
    }

    public Fleet getFleet(long id) {
        return fleetMap.get(id);
    }
}
