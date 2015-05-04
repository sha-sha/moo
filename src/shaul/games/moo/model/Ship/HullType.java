package shaul.games.moo.model.Ship;

/**
 * Created by Shaul on 3/24/2015.
 */
public class HullType {

    private final int size;
    private final String name;

    // size - 0-smallest
    public HullType(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public int getHullSize() { return size; }
    public String getName() { return name; }
}
