package shaul.games.moo.setup;

import shaul.games.moo.model.GalaxySettings;
import shaul.games.moo.model.Generators.IStarMapGenerator;
import shaul.games.moo.model.Position;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 2/25/2015.
 */
public class StarMapGenerator implements IStarMapGenerator {
    @Override
    public List<Position> generate(GalaxySettings settings) {
        return Arrays.asList(
                new Position(23, 45),
                new Position(3, 8),
                new Position(53, 85),
                new Position(8, 34),
                new Position(42, 12)
        );
    }
}
