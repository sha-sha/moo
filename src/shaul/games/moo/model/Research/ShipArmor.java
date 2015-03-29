package shaul.games.moo.model.Research;

/**
 * Created by Shaul on 3/15/2015.
 */
public class ShipArmor {

    private final String name;
    private final Properties[] properties;

    public ShipArmor(String name, Properties[] properties) {
        this.name = name;
        //this.properties
        Properties[] propertiesCopy = new Properties[properties.length];
        System.arraycopy(properties, 0, propertiesCopy, 0, properties.length);
        this.properties = propertiesCopy;
    }

    public String getName() {
        return name;
    }

    public Properties getProp(int hullSize) {
        return properties[hullSize];
    }

    public class Properties {
        private final int cost;
        private final int size;
        private final int hitPoints;

        public Properties(int cost, int size, int hitPoints) {
            this.cost = cost;
            this.size = size;
            this.hitPoints = hitPoints;
        }

        public int getCost() { return this.cost; }
        public int getSize() { return this.size; }
        public int getHitPoints() { return this.hitPoints; }
    }

}
