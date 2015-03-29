package shaul.games.moo.setup;

import shaul.games.moo.model.GalaxySettings;
import shaul.games.moo.model.Generators.GalaxyGenerator;
import shaul.games.moo.model.Generators.IStarGenerator;
import shaul.games.moo.model.Position;
import shaul.games.moo.model.Star;

/**
 * Created by Shaul on 2/25/2015.
 */
public class GameSetup {

    public GalaxyGenerator createGalaxyGenerator() {
        return new GalaxyGenerator(new StarMapGenerator(), new SimpleStarGenerator());
    }

    private static class SimpleStarGenerator implements IStarGenerator {

        @Override
        public Star generate(Position position, GalaxySettings settings) {
            return null;
        }
    }
}
