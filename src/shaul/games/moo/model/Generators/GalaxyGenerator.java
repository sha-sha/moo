package shaul.games.moo.model.Generators;

import shaul.games.moo.model.GalaxySettings;
import shaul.games.moo.model.Position;
import shaul.games.moo.model.Star;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 2/25/2015.
 */
public class GalaxyGenerator {

    private final IStarMapGenerator starMapGenerator;
    private final IStarGenerator starGenerator;

    public GalaxyGenerator(IStarMapGenerator starMapGenerator, IStarGenerator starGenerator) {
        this.starMapGenerator = starMapGenerator;
        this.starGenerator = starGenerator;
    }

    public List<Star> generate(GalaxySettings settings) {
        List<Position> positions = starMapGenerator.generate(settings);
        List<Star> stars = new ArrayList<>();
        for (Position pos : positions) {
            stars.add(starGenerator.generate(pos, settings));
        }
        return stars;
    }

}
