package shaul.games.moo.setup;

import shaul.games.moo.model.Ship.ShipModule;

/**
 * Created by Shaul on 3/21/2015.
 */
public class ModuleFactory {

    public static ShipModule createComputerModule(String name, final int level) {
        return new ShipModule(name, ShipModule.Type.COMPUTER, new Computer(level));
    }

    private static class BaseModule implements ShipModule.Base {
        private final int[] baseCost;
        private final int[] baseSize;
        private final int[] basePower;
        private final int[] baseSpace;

        BaseModule(int[] baseCost, int[] baseSize, int[] basePower, int[] baseSpace) {
            this.baseSize = baseSize;
            this.basePower = basePower;
            this.baseCost = baseCost;
            this.baseSpace = baseSpace;
        }
        @Override public int getCost(int hullSize) { return baseCost != null ? baseCost[hullSize] : 0; }
        @Override public int getSize(int hullSize) { return baseSize != null ? baseSize[hullSize] : 0; }
        @Override public int getPower(int hullSize) { return basePower != null ? basePower[hullSize] : 0; }
        @Override public int getSpace(int hullSize) { return baseSpace != null ? baseSpace[hullSize] : 0; }
        int calcSpace(int hullSize, int currentTechLevel) {
            return getSize(hullSize) + getPower(hullSize) * (1 - currentTechLevel / 100);
        }
    }

    private static class Computer extends BaseModule implements ShipModule.Computer {
        private static final int[] BASE_COST = {5, 22, 104, 520};
        private static final int[] BASE_SIZE_POWER = {5, 10, 20, 100};
        private static final int[] BASE_SPACE = {10, 20, 40, 200};
        private final int level;

        Computer(int level) {
            super(BASE_COST, BASE_SIZE_POWER, BASE_SIZE_POWER, BASE_SPACE);
            this.level = level;
        }
        @Override public int getAttackLevel(int hullSize) { return level; }
    }



}
