package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Shaul on 3/8/2015.
 */
public class ShipModule extends TechModule {

    public static final ShipModule EMPTY = new ShipModule();
    public static final ShipModuleData NO_DATA = new ShipModuleData.Builder().build();
    public static final ShipModule NO_COMPUTER = new ComputerShipModule("No Computer", NO_DATA);
    public static final ShipModule NO_SHIELD = new ShieldShipModule("No Shield", NO_DATA);
    public static final ShipModule NO_ECM = new EcmShipModule("No ECM", NO_DATA);
    public static final ShipModule NO_WEAPON = new WeaponShipModule("No Weapon", NO_DATA);
    public static final Utils.Countable<ShipModule> ZERO_WEAPON = new Utils.Countable<>(NO_WEAPON, 0);
    public static final ShipModule NO_SPECIAL = new SpecialShipModule(Technology.Category.Computers, "None", NO_DATA);

    public enum ShipComponent {NONE, COMPUTER, SHIELD, ECM, ARMOR, ENGINE, MANEUVER, WEAPON, SPECIAL}
    public enum ShipScanLevel {NONE, BASIC, ADVANCE}

    private final Technology.Category technologyCategory;
    private final ShipModuleData moduleData;
    private final ShipComponent shipComponent;
    private final Set<ShipDesign.SlotType> possibleSlotType;

    public enum WeaponType {None, Laser, Kinetic}

    private ShipModule(Technology.Category technologyCategory,
                       String name,
                       Set<ShipDesign.SlotType> possibleSlotTypes,
                       ShipComponent shipComponent,
                       ShipModuleData moduleData) {
        super(name, TechModule.Type.Ship);
        this.technologyCategory = technologyCategory;
        this.possibleSlotType = possibleSlotTypes;
        this.shipComponent = shipComponent;
        this.moduleData = moduleData;
    }

    private ShipModule() {
        super("EMPTY", TechModule.Type.Ship);
        this.shipComponent = ShipComponent.NONE;
        this.technologyCategory = Technology.Category.Computers;
        this.possibleSlotType = new HashSet<>();
        this.moduleData = new ShipModuleData.Builder().build();
    }

    public ShipComponent getShipComponentType() { return shipComponent; }
    public ShipModuleData getModuleData() { return moduleData; }
    public boolean isEmpty() {
        return this == EMPTY || moduleData == NO_DATA;
    }
    public Set<ShipDesign.SlotType> getPossibleSlotType() { return possibleSlotType; }

    public int getSpace(IPlayerState playerState, Hull hull) {
        int baseSize = moduleData.getSize(hull);
        if (baseSize == 0) {
            return baseSize;
        }
        return (int) (baseSize * playerState.getModuleSizeReduction(technologyCategory));
    }

    public int getCost(IPlayerState playerState, Hull hull) {
        int baseCost = moduleData.getCost(hull);
        if (baseCost == 0) {
            return baseCost;
        }
        return (int) (baseCost * playerState.getModuleCostReduction(technologyCategory));
    }

    @Override
    public String toString() {
        return String.format("%s %s data: %s", getType(), getName(), moduleData);
    }


//    public static class EmptyShipModule extends ShipModule {
//
//        EmptyShipModule(String name, ShipComponent shipComponent) {
//            super(name, shipComponent);
//        }
//
//        public boolean isEmpty() { return true; }
//    }

    public static class ComputerShipModule extends ShipModule {
        public ComputerShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.Computers,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Computer),
                    ShipComponent.COMPUTER, moduleData);
        }
    }

    public static class ShieldShipModule extends ShipModule {
        public ShieldShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.ForceFields,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Shield),
                    ShipComponent.SHIELD, moduleData);
        }
    }

    public static class ArmorShipModule extends ShipModule {
        public ArmorShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.Construction,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Armor),
                    ShipComponent.ARMOR,
                    moduleData);
        }
    }

    public static class EcmShipModule extends ShipModule {
        public EcmShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.Computers,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Ecm),
                    ShipComponent.ECM, moduleData);
        }
    }

    public static class EngineShipModule extends ShipModule {
        public EngineShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.Propulsion,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Engine),
                    ShipComponent.ENGINE, moduleData);
        }
    }

    public static class WeaponShipModule extends ShipModule {
        public WeaponShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.Weapons,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Weapon1,
                            ShipDesign.SlotType.Weapon2,
                            ShipDesign.SlotType.Weapon3,
                            ShipDesign.SlotType.Weapon4),
                    ShipComponent.WEAPON,
                    moduleData);
        }
    }

    public static class SpecialShipModule extends ShipModule {
        public SpecialShipModule(Technology.Category category, String name, ShipModuleData moduleData) {
            super(category,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Special1,
                            ShipDesign.SlotType.Special2,
                            ShipDesign.SlotType.Special3),
                    ShipComponent.SPECIAL,
                    moduleData);
        }
    }
    
    public static class ManeuverShipModule extends ShipModule {
        public ManeuverShipModule(String name, ShipModuleData moduleData) {
            super(Technology.Category.None,
                    name,
                    Utils.setOf(ShipDesign.SlotType.Maneuver),
                    ShipComponent.MANEUVER, moduleData);
        }
    }
}
