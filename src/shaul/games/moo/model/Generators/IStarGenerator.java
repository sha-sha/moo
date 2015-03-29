package shaul.games.moo.model.Generators;

import shaul.games.moo.model.GalaxySettings;
import shaul.games.moo.model.Position;
import shaul.games.moo.model.Star;

/**
 * Created by Shaul on 2/25/2015.
 */
public interface IStarGenerator {
    Star generate(Position position, GalaxySettings settings);
}
