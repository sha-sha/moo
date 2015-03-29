package shaul.games.moo.model.Generators;

import shaul.games.moo.model.GalaxySettings;
import shaul.games.moo.model.Position;

import java.util.List;

/**
 * Created by Shaul on 2/25/2015.
 */
public interface IStarMapGenerator {
    List<Position> generate(GalaxySettings settings);
}
