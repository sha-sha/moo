package shaul.games.moo.model.Player;

import shaul.games.moo.model.Fleet;
import shaul.games.moo.model.Research.TechnologiesDb;

import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public interface IPlayerState {
    TechnologiesDb getTechnologies();
    List<Fleet> getFleets();
}
