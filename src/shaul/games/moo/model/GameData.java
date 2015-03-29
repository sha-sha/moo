package shaul.games.moo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Immutable !
 *
 * Created by Shaul on 2/25/2015.
 */
public class GameData {

    private List<Star> galaxy;
    private List<Player> players;
    private long lastFleetId = 0;

    // helpers.
    Map<String, Player> playersMap = new HashMap<>();

    public GameData(List<Star> galaxy, List<Player> players) {
        this.galaxy = galaxy;
        this.players = players;
        playersMap = Utils.map(players, Player.ELEMENT_KEY);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String name) {
        return playersMap.get(name);
    }

    public long getNextFleetId() {
        return ++lastFleetId;
    }

    // Diplomacy ..

    public GameData() {

    }

    public List<Star> getGalaxy() {
        return galaxy;
    }
}
