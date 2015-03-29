package shaul.games.moo.model.commands;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.GameData;
import shaul.games.moo.model.Player;
import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Shaul on 2/25/2015.
 */
public class CommandsExecutor implements ICommandsExecutor {

    private static final Utils.ElementKey<String, ICommand> COMMAND_ELEMENT_KEY =
            new Utils.ElementKey<String, ICommand>() {
                @Override
                public String getKey(ICommand iCommand) {
                    return iCommand.getPlayer();
                }
            };

    private static final Utils.ElementKey<Long, ICommand> FLEET_COMMAND_ELEMENT_KEY =
            new Utils.ElementKey<Long, ICommand>() {
                @Override
                public Long getKey(ICommand iCommand) {
                    if (iCommand instanceof FleetCommand) {
                        return ((FleetCommand)iCommand).getFleetId();
                    }
                    return 0l;
                }
            };

    @Override
    public GameData execute(GameData gameData, List<ICommand> commands) {
        Map<String, List<ICommand>> commandsPerPlayer = Utils.collect(
                commands, COMMAND_ELEMENT_KEY);
        List<Player> players = gameData.getPlayers();
        List<Player> newPlayers = new ArrayList<>();
        for (Player player : players) {
            newPlayers.add(
                    execute(player, commandsPerPlayer.get(player.getPlayerInfo().getName())));
        }
        return new GameData(gameData.getGalaxy(), newPlayers);
    }

    private Player execute(Player player, List<ICommand> commands) {
        if (commands == null) {
            return player;
        }
        Map<Long, List<FleetCommand>> commandsPerFleet = Utils.collectByClass(
                commands, FLEET_COMMAND_ELEMENT_KEY);
        List<Fleet> fleets = player.getFleets();
        List<Fleet> newFleets = new ArrayList<>();
        for (Fleet fleet : fleets) {
            List<FleetCommand> fleetCommands = commandsPerFleet.get(fleet.getId());
            if (fleetCommands == null) {
                newFleets.add(fleet);
                continue;
            }
            newFleets.addAll(execute(fleet, fleetCommands));
        }
        return new Player(player.getPlayerInfo(), newFleets);
    }

    private List<Fleet> execute(Fleet fleet, List<FleetCommand> fleetCommands) {
        List<Fleet> newFleets = new ArrayList<>();
        Fleet.Ships remaining = fleet.getShips();
        for (FleetCommand fleetCommand : fleetCommands) {
            Utils.check(remaining != null);
            remaining = remaining.getDelta(fleetCommand.getShips());
            if (remaining == null) {
                newFleets.add(new Fleet(
                        fleet.getId(),
                        fleetCommand.getShips(),
                        fleet.getPosition(),
                        fleetCommand.getOrder()));
            } else {
                newFleets.add(new Fleet(
                        fleetCommand.getShips(),
                        fleet.getPosition(),
                        fleetCommand.getOrder()));
            }
        }
        if (remaining != null) {
            newFleets.add(new Fleet(
                    fleet.getId(),
                    remaining,
                    fleet.getPosition(),
                    fleet.getOrder()));
        }
        return newFleets;
    }

}
