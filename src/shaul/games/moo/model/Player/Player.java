package shaul.games.moo.model.Player;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class Player implements IPlayer {
    @Override
    public IPlayerState getIPlayerState() {
        return new IPlayerState() {

            @Override
            public List<String> getTechnologies() {
                return Arrays.asList("dd");
            }
        };
    }
}
