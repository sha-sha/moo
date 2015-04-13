package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechnologiesDb;

import java.util.List;

/**
 * Created by shaul on 4/2/15.
 */
public class PlayerStateImpl implements IPlayerState {

    private final List<Fleet> fleets;
    private final TechnologiesDb technologies;

    public PlayerStateImpl(List<Fleet> fleets, TechnologiesDb technologies) {
        this.technologies = technologies;
        this.fleets = fleets;
    }

    @Override
    public TechnologiesDb getTechnologies() {
        return technologies;
    }

    @Override
    public List<Fleet> getFleets() {
        return fleets;
    }
}
