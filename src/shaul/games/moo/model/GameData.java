package shaul.games.moo.model;

import shaul.games.moo.model.Player.IPlayer;

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
    private List<IPlayer> players;
    private long lastFleetId = 0;

    // helpers.
    Map<String, IPlayer> playersMap = new HashMap<>();

    public GameData(List<Star> galaxy, List<IPlayer> players) {
        this.galaxy = galaxy;
        this.players = players;
        playersMap = Utils.map(players, IPlayer.ELEMENT_KEY);
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public IPlayer getPlayer(String name) {
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
