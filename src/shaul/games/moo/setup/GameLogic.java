package shaul.games.moo.setup;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Research.ITechnologyLogic;

/**
 * Created by Shaul on 3/24/2015.
 */
public class GameLogic implements IGameLogic {

    private ITechnologyLogic technologyLogic;

    public GameLogic() {
        technologyLogic = new TechnologyLogic();
    }

    @Override
    public ITechnologyLogic getTechnologyLogic() {
        return technologyLogic;
    }
}
