package shaul.games.moo.model.Research;

/**
 * Created by Shaul on 3/15/2015.
 */
public class ShipEngine {
    private final String name;
    private final int wrapSpeed;
    private final int addedManeuverability;
    private final Properties[] properties;

    public ShipEngine(String name, int wrapSpeed, int addedManeuverability, Properties[] properties) {
        this.name = name;
        //this.properties
        Properties[] propertiesCopy = new Properties[properties.length];
        System.arraycopy(properties, 0, propertiesCopy, 0, properties.length);
        this.properties = propertiesCopy;
        this.wrapSpeed = wrapSpeed;
        this.addedManeuverability = addedManeuverability;
    }

    public String getName() {
        return name;
    }

    public int getWrapSpeed() { return this.wrapSpeed; }

    public int getAddedManeuverability() { return this.addedManeuverability; }

    public int getDefencePoints() { return 0; }

    public Properties getProp(int hullSize) {
        return properties[hullSize];
    }

    public class Properties {
        private final int cost;
        private final int size;
        private final float numberOfEngines;
        private final int space;

        public Properties(int cost, int size, float numberOfEngines, int space, int wrapSpeed) {
            this.cost = cost;
            this.size = size;
            this.numberOfEngines = numberOfEngines;
            this.space = space;
        }

        public int getCost() { return this.cost; }
        public int getSize() { return this.size; }
        public float getNumberOfEngines() { return this.numberOfEngines; }
        public int getSpace() { return this.space; }
    }



}
