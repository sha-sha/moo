package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.Technology;

/**
 * Created by Shaul on 5/30/2015.
 */
public enum Hull {
    Small(40, 3, 2, 6),
    Medium(200, 18, 1, 36),
    Large(1000, 100, 0, 200),
    Huge(5000, 600, -1, 1200);

    private final int space;
    public final int hits;
    public final int defence;
    public final int cost;

    Hull(int space, int hits, int defence, int cost) {
        this.space = space;
        this.hits = hits;
        this.defence = defence;
        this.cost = cost;
    }

    public int getSpace(IPlayerState playerState) {
        return getSpaceOnTechLevel(playerState.getTechLevel(Technology.CATEGORY_CONSTRUCTION));
    }

    public int getBaseSpace() {
        return space;
    }
    // find another way ...
    public int getSpaceOnTechLevel(int techLevel) {
        return space * (100 + 2 * techLevel) / 100;
    }
}
