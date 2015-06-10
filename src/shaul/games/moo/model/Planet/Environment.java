package shaul.games.moo.model.Planet;

/**
 * Created by Shaul on 3/22/2015.
 */
public enum Environment {
    Terran,
    Jungle,
    Ocean,
    Arid,
    Steppe,
    Desert,
    Minimal,
    Barren,
    Tundra,
    Dead,
    Inferno,
    Toxic,
    Radiated,
    None;


    public boolean isHostile() {
        return ordinal() >= Barren.ordinal();
    }
}
