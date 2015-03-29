package shaul.games.moo.model.commands;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Player;

/**
 * Created by Shaul on 2/25/2015.
 */
public class FleetCommand implements ICommand {
    private String player;
    private long fleetId;
    private Fleet.Ships ships;
    private Fleet.Order order;

    public FleetCommand(String player, long fleetId, Fleet.Ships ships, Fleet.Order order) {
        this.player = player;
        this.fleetId = fleetId;
        this.ships = ships;
        this.order = order;
    }

    public String getPlayer() {
        return player;
    }

    public long getFleetId() {
        return fleetId;
    }

    public Fleet.Ships getShips() {
        return ships;
    }

    public Fleet.Order getOrder() {
        return order;
    }
}
