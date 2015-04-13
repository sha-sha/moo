package shaul.games.moo.model.Planet;

import shaul.games.moo.model.Research.TechModule;

import java.util.Arrays;

/**
 * Created by shaul on 4/7/15.
 */
public class PlanetTechBonus extends TechModule {

    private final Bonus bonus;

    public PlanetTechBonus(String name, Bonus bonus) {
        super(name, Type.Planet);
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return super.toString() + " bonus: " + bonus;
    }

    public Bonus getBonus() { return bonus; }

    public static class Bonus {

        private final String desc;
        private final int maxFactoriesPerPopulation;

        public Bonus(Builder builder) {
            this.maxFactoriesPerPopulation = builder.maxFactoriesPerPopulation;

            desc = new StringBuilder()
                    .append(getAttribute(maxFactoriesPerPopulation > 0, "factPerPop", maxFactoriesPerPopulation))
                    .toString();
        }

        public int getMaxFactoriesPerPopulation() { return maxFactoriesPerPopulation; }

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

        public static class Builder {
            private int maxFactoriesPerPopulation = 0;

            public Builder maxFactoriesPerPopulation(int maxFactoriesPerPopulation) {
                this.maxFactoriesPerPopulation = maxFactoriesPerPopulation; return this; }

            public Bonus build() {
                return new Bonus(this);
            }
        }
    }

}
