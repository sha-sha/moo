package shaul.games.moo.model.Research;

/**
 * Created by shaul on 4/7/15.
 */
public class GlobalTechBonus extends TechModule {
    private final Bonus bonus;

    public GlobalTechBonus(String name, Bonus bonus) {
        super(name, Type.Global);
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return super.toString() + " bonus: " + bonus;
    }

    public Bonus getBonus() { return bonus; }

    public static class Bonus {

        private final String desc;
        private final int shipSensorRange;
        private final int planetShipDetectionRange;
        private final int planetStarDetectionRange;
        private final boolean detectEnemyShipDestination;
        private final boolean canChangeFleetDestinationOnRoute;

        public Bonus(Builder builder) {
            this.shipSensorRange = builder.shipSensorRange;
            this.planetShipDetectionRange = builder.planetShipDetectionRange;
            this.planetStarDetectionRange = builder.planetStarDetectionRange;
            this.detectEnemyShipDestination = builder.detectEnemyShipDestination;
            this.canChangeFleetDestinationOnRoute = builder.canChangeFleetDestinationOnRoute;

            desc = new StringBuilder()
                    .append(getAttribute(
                            shipSensorRange > 0, "shipSensorRange", shipSensorRange))
                    .append(getAttribute(
                            planetShipDetectionRange > 0, "planetShipDetectionRange", planetShipDetectionRange))
                    .append(getAttribute(
                            planetStarDetectionRange > 0, "planetStarDetectionRange", planetStarDetectionRange))
                    .append(getAttribute("detectEnemyShipDestination", detectEnemyShipDestination))
                    .append(getAttribute("canChangeFleetDestinationOnRoute", canChangeFleetDestinationOnRoute))
                    .toString();
        }

        public int getShipSensorRange() { return shipSensorRange; }
        public int getPlanetShipDetectionRange() { return planetShipDetectionRange; }
        public int getPlanetStarDetectionRange() { return planetStarDetectionRange; }
        public boolean isDetectEnemyShipDestination() { return detectEnemyShipDestination; }

        @Override
        public String toString() {
            return desc;
        }

        private String getAttribute(boolean condition, String name, int i) {
            return condition ? " " + name + ":" + i : "";
        }

        private String getAttribute(boolean condition, String name, String i) {
            return condition ? " " + name + ":" + i : "";
        }

        private String getAttribute(String name, boolean i) {
            return i ? " " + name + ":" + i : "";
        }

        public static class Builder {
            private int shipSensorRange = 0;
            private int planetShipDetectionRange = 0;
            private int planetStarDetectionRange = 0;
            private boolean detectEnemyShipDestination = false;
            private boolean canChangeFleetDestinationOnRoute = false;

            public Builder shipSensorRange(int shipSensorRange) {
                this.shipSensorRange = shipSensorRange; return this; }
            public Builder planetShipDetectionRange(int planetShipDetectionRange) {
                this.planetShipDetectionRange = planetShipDetectionRange; return this; }
            public Builder planetStarDetectionRange(int planetStarDetectionRange) {
                this.planetStarDetectionRange = planetStarDetectionRange; return this; }
            public Builder detectEnemyShipDestination(boolean detectEnemyShipDestination) {
                this.detectEnemyShipDestination = detectEnemyShipDestination; return this; }
            public Builder canChangeFleetDestinationOnRoute(boolean canChangeFleetDestinationOnRoute) {
                this.canChangeFleetDestinationOnRoute = canChangeFleetDestinationOnRoute; return this; }

            public Bonus build() {
                return new Bonus(this);
            }


        }
    }
}
