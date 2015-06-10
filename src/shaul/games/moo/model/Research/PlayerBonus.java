package shaul.games.moo.model.Research;

import shaul.games.moo.model.Player.IPlayerState;

/**
 * Created by Shaul on 3/1/2015.
 */
public interface PlayerBonus {

    public static final PlayerBonus NO_BONUS = new PlayerBonus() {
        @Override public void apply(IPlayerState.TechState techState) {}};

    void apply(IPlayerState.TechState techState);
}
