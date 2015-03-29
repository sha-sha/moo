package shaul.games.moo.model;

/**
 * Created by Shaul on 2/25/2015.
 */
public class GalaxySettings {
    public enum Type {Normal};
    public enum Size {Tiny, Small};

    public Type getType() {
        return Type.Normal;
    }

    public Size getSize() {
        return Size.Small;
    }
}
