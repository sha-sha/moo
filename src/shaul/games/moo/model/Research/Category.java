package shaul.games.moo.model.Research;

/**
 * Created by Shaul on 3/24/2015.
 */
public enum Category {
    Computers("Computers"),
    Construction("Construction"),
    ForceFields("ForceFields"),
    Planetology("Planetology"),
    Propulsion("Propulsion"),
    Weapons("Weapons");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
