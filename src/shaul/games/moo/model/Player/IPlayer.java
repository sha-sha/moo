package shaul.games.moo.model.Player;

import shaul.games.moo.model.Utils;

/**
 * Created by Shaul on 3/24/2015.
 */
public interface IPlayer {

    public static final Utils.Function<IPlayer, String> ELEMENT_KEY = new Utils.Function<IPlayer, String>() {
        @Override public String apply(IPlayer iPlayer) { return iPlayer.getPlayerName(); }};

    String getPlayerName();
    IPlayerState getPlayerState();
    void setPlayerState(IPlayerState newState);

}
