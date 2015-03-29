package shaul.games.moo.model.commands;

import shaul.games.moo.model.GameData;

import java.util.List;

/**
 * Created by Shaul on 2/25/2015.
 */
public interface ICommandsExecutor {
    GameData execute(GameData gameData, List<ICommand> commands);
}
