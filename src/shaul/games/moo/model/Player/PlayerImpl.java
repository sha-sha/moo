package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.setup.GameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class PlayerImpl implements IPlayer {

    private final String name;
    private IPlayerState playerState;

    public PlayerImpl(IGameLogic gameLogic, String name) {
        this.name = name;
        playerState = new PlayerStateImpl(new ArrayList<Fleet>(),
                new TechnologiesDb(gameLogic.getTechnologyLogic(), new ArrayList<String>()));
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    @Override
    public IPlayerState getPlayerState() {
        return playerState;
    }

    @Override
    public void setPlayerState(IPlayerState newState) {
        this.playerState = newState;
    }
}
