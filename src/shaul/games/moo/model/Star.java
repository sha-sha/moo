package shaul.games.moo.model;

/**
 * Created by Shaul on 2/24/2015.
 */
public class Star {
    private final String color;
    private final String name;
    private final String resourceLevel;
    private final String habitabilityLevel;
    private final String player;

    public Star(
            String name,
            String color,
            String resourceLevel,
            String habitabilityLevel,
            String player) {
        this.color = color;
        this.name = name;
        this.resourceLevel = resourceLevel;
        this.habitabilityLevel = habitabilityLevel;
        this.player = player;
    }

    public String toString() {
        return String.format(
                "%s is a %s star {r:%s h:%s p:%s}", name, color, resourceLevel, habitabilityLevel, player);
    }
}
