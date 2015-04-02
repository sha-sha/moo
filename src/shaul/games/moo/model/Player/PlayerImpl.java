package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechnologiesDb;

import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class PlayerImpl implements IPlayer {

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public IPlayerState getPlayerState() {
        return new IPlayerState() {

            @Override
            public TechnologiesDb getTechnologies() {
                return null;
            }

            @Override
            public List<Fleet> getFleets() {
                return null;
            }
        };
    }
}
