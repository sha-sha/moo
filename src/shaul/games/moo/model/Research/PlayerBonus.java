package shaul.games.moo.model.Research;

import shaul.games.moo.model.Player.IPlayerStateModifier;

/**
 * Created by Shaul on 3/1/2015.
 */
public interface PlayerBonus {

    public static final PlayerBonus NO_BONUS = new PlayerBonus() {
        @Override public void apply(IPlayerStateModifier playerStateModifier) {}};

    void apply(IPlayerStateModifier playerStateModifier);
}
