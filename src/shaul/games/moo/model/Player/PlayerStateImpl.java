package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechnologiesDb;

import java.util.List;

/**
 * Created by shaul on 4/2/15.
 */
public class PlayerStateImpl implements IPlayerState {

    private final List<Fleet> fleets;

    public PlayerStateImpl(List<Fleet> fleets) {
        this.fleets = fleets;
    }

    @Override
    public TechnologiesDb getTechnologies() {
        return null;
    }

    @Override
    public List<Fleet> getFleets() {
        return fleets;
    }
}
