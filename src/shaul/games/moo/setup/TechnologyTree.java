package shaul.games.moo.setup;

import shaul.games.moo.model.Planet.Environment;
import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Research.PlayerBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Utils;

import java.util.*;

/**
 * Created by Shaul on 3/1/2015.
 */
public final class TechnologyTree {

    private static final List<Technology> TECHNOLOGIES = new ArrayList<Technology>() {{{
        add(new BattleScanner(1));
        add(new BattleComputer(1, 1));
        add(new RoboticControls(1, 2));
        add(new EcmJammer(2, 1));
        add(new SpaceScannerTechnology(3, 1));
        add(new BattleComputer(4, 2));
        add(new EcmJammer(5, 2));
        add(new RoboticControls(6, 3));
        add(new BattleComputer(7, 3));
        add(new EcmJammer(8, 3));
        add(new SpaceScannerTechnology(9, 2));
        add(new BattleComputer(10, 4));
        add(new EcmJammer(11, 4));
        add(new RoboticControls(12, 4));
        add(new BattleComputer(13, 5));
        add(new EcmJammer(14, 5));
        add(new SpaceScannerTechnology(15, 3));
        add(new BattleComputer(16, 6));
        add(new EcmJammer(17, 6));
        add(new RoboticControls(18, 5));
        add(new BattleComputer(19, 7));
        add(new EcmJammer(20, 7));
        add(new HyperspaceCommunications(21));
        add(new BattleComputer(22, 8));
        add(new EcmJammer(23, 8));
        add(new RoboticControls(24, 6));
        add(new BattleComputer(25, 9));
        add(new EcmJammer(26, 9));
        add(new BattleComputer(27,10));
        // Oracle Interface ??  Allows all "direct fire" weapons on the equipped ship to act as if they have the
        //   "armor piercing" quality. This includes pretty much everything except missiles, torpedoes, bombs, and
        //   anything that counts as a special system.
        add(new EcmJammer(29, 10));
        add(new RoboticControls(30, 7));
        // Technology Nullifier - Decreases the enemy's attack rating by 2 - 6 each time it is fired.
        add(new BattleComputer(31, 11));


        add(new Armor(1, 1, "Titanium"));
        add(new IndustrialTech(3, 9));
        add(new ReducedIndustrialWaste(5, 80));
        add(new IndustrialTech(8, 8));
        add(new Armor(10, 2, "Duralloy"));
        add(new CombatSuits(10, "Battle Suits", 10));
        add(new IndustrialTech(13, 7));
        add(new AutomatedRepairUnit(14, "Automated Repair Unit", 15));
        add(new ReducedIndustrialWaste(15, 60));
        add(new Armor(17, 3, "Zoritum"));
        add(new IndustrialTech(18, 6));
        add(new IndustrialTech(23, 5));
        add(new CombatSuits(24, "Armored Exoskeleton", 20));
        add(new ReducedIndustrialWaste(25, 40));
        add(new Armor(26, 4, "Andrium"));
        add(new IndustrialTech(28, 4));
        add(new IndustrialTech(33, 3));
        add(new Armor(34, 5, "Tritanium"));
        add(new ReducedIndustrialWaste(35, 20));
        add(new AutomatedRepairUnit(36, "Advanced Damage Control Unit", 30));
        add(new IndustrialTech(38, 2));
        add(new CombatSuits(40, "Powered Armor", 30));
        add(new Armor(42, 6, "Adamantium"));
        add(new ReducedIndustrialWaste(45, 0));
        add(new Armor(50, 7, "Neutronium"));

        add(new DeflectorShield(1, 1));
        add(new DeflectorShield(4, 2));
        add(new CombatShield(8, "Personal Deflector Shield", 10));
        add(new DeflectorShield(10, 3));
        add(new PlanetaryShield(12, "Class IV Deflector Shield", 5));
        add(new DeflectorShield(14, 4));
        //add(new RepulsorBeam(16,
        add(new DeflectorShield(20, 5));
        add(new CombatShield(21, "Personal Absorption Shield", 20));
        add(new PlanetaryShield(22, "Class X Planetary Shield", 10));
        add(new DeflectorShield(24, 6));
        //add(new Cloaking Device(27
        add(new DeflectorShield(30, 7));
        // add(new ZyroShield(31)
        add(new PlanetaryShield(32, "Class XV Planetary Shield", 15));
        add(new DeflectorShield(34, 9));
        // add(new StasisField (37)
        add(new CombatShield(38, "Personal Barrier Shield", 30));
        add(new DeflectorShield(40, 11));
        add(new PlanetaryShield(42, "Class XX Planetary Shield", 20));
        //add(new BlackHoleGenerator(43)
        add(new DeflectorShield(44, 13));
        // add(new LightningShield(46
        add(new DeflectorShield(50, 15));

        add(new ControlledEnvironment(1, "Standard Colony", Environment.Minimal));
        add(new EcologicalRestoration(1, "Ecological Restoration", 2));
        add(new Terraforming(2, "Terraforming +10", 10));
        add(new ControlledEnvironment(3, "Controlled Barren Environment", Environment.Barren));
        add(new EcologicalRestoration(5, "Improved Ecological Restoration", 3));
        add(new ControlledEnvironment(6, "Controlled Tundra Environment", Environment.Tundra));
        add(new Terraforming(9, "Terraforming +20", 20));
        add(new ControlledEnvironment(10, "Controlled Dead Environment", Environment.Dead));
        add(new BioWeapon(11, "Death Spores", 1));
        add(new ControlledEnvironment(12, "Controlled Inferno Environment", Environment.Inferno));
        add(new EcologicalRestoration(13, "Enhanced Ecological Restoration", 5));
        add(new Terraforming(14, "Terraforming +30", 30));
        add(new ControlledEnvironment(15, "Controlled Toxic Environment", Environment.Toxic));
        // TODO: complete ...


        add(new RetroEngine(1, "Retros Engines"));
        add(new FuelCells(2, "Hydrogen Fuel Cells", 4));
        add(new FuelCells(5, "Deuterium Fuel Cells", 5));
        add(new Engine(6, "Nuclear Engine", 2));
        add(new FuelCells(9, "Irridium  Fuel Cells", 6));
        add(new InertialStabilizer(10));
        add(new Engine(12, "Sub Light Drives", 3));
        add(new FuelCells(14, "Dotomite Crystals", 7));
        //add(new EnergyPulsar(16))
        add(new Engine(18, "Fusion Drives", 4, 1));
        add(new FuelCells(19, "Uridium Fuel Cells", 8));
        // 20 Warp Dissipator
        add(new FuelCells(23, "Reajax II Fuel Cells", 9));
        add(new Engine(24, "Impulse Drives", 5, 1));







        add(new Laser(1, 1));




    }}};
    private static Map<String, Technology> TECHNOLOGIES_MAP = null;
    private static Map<String, Technology> MODULE_TECHNOLOGIES_MAP = null;

    public static Map<String, Technology> getTechnologiesMap() {
        if (TECHNOLOGIES_MAP == null) {
            TECHNOLOGIES_MAP = new HashMap<>();
            for (Technology t : TECHNOLOGIES) {
                TECHNOLOGIES_MAP.put(t.getName(), t);
            }
        }
        return TECHNOLOGIES_MAP;
    }

    public static Map<String, Technology> getModuleToTechnologyMap() {
        if (MODULE_TECHNOLOGIES_MAP == null) {
            MODULE_TECHNOLOGIES_MAP = new HashMap<>();
            for (Technology t : TECHNOLOGIES) {
                for (String module : t.getModules()) {
                    MODULE_TECHNOLOGIES_MAP.put(module, t);
                }
            }
        }
        return MODULE_TECHNOLOGIES_MAP;
    }

    private static class BattleScanner extends Technology.Computer {
        public BattleScanner(int techLevel) {
            super(techLevel, "Battle Scanner", new ShipTech.BattleScanner());
        }
    }

    private static class BattleComputer extends Technology.Computer {
        public BattleComputer(int techLevel, int level) {
            super(techLevel, "Battle Computer Mark " + level, new ShipTech.BattleComputer(level));
        }
    }

    private static class RoboticControls extends Technology.Computer {
        public RoboticControls(int techLevel, int level) {
            super(techLevel, "Robotic Controls " + Utils.toRomanNumber(level),
                    new PlanetTech.FactoriesPerPopulation("Robotic Controls " + Utils.toRomanNumber(level), level));
        }
    }

    private static class SpaceScannerTechnology extends Technology.Computer {
        private static final String[] NAMES =
                { "Deep Space Scanner", "Improved Space Scanner", "Advanced Space Scanner" };
        // TODO: Need real values.
        private static final int[] PLANET_STAR_RANGES = { 0, 0, 0 };
        private static final int[] PLANET_SHIP_RANGES = { 0, 0, 0 };
        private static final int[] SHIP_SHIP_RANGES = { 0, 0, 0 };
        private static final boolean[] DETECT_ETA = {false, true, true};
        private static final boolean[] CHANGE_HYPER_DESTINATION = {false, false, true};
        public SpaceScannerTechnology(int techLevel, final int level) {
            super(techLevel, NAMES[level - 1], new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.planetShipSensorRange = Math.max(
                            techState.planetShipSensorRange, PLANET_SHIP_RANGES[level - 1]);
                    techState.planetStarSensorRange = Math.max(
                            techState.planetStarSensorRange, PLANET_STAR_RANGES[level - 1]);
                    techState.canDetectEnemyShipDestinationAndEta = techState.canDetectEnemyShipDestinationAndEta ||
                            DETECT_ETA[level - 1];
                    techState.canChangeShipDestinationInHyperSpace = techState.canChangeShipDestinationInHyperSpace ||
                            CHANGE_HYPER_DESTINATION[level - 1];
                }
            });
        }
    }

    private static class HyperspaceCommunications extends Technology.Computer {
        public HyperspaceCommunications(int techLevel) {
            super(techLevel, "Hyperspace Communications",
                    new TechModule("Hyperspace Communications", new PlayerBonus() {
                        @Override
                        public void apply(IPlayerState.TechState techState) {
                            techState.canChangeShipDestinationInHyperSpace = true;
                        }
                    }));
        }
    }

    private static class IndustrialTech extends Technology.Construction {
        public IndustrialTech(int techLevel, final int cost) {
            super(techLevel, "Industrial Tech " + cost,
                    new TechModule("Industrial Tech " + cost, new PlayerBonus() {
                        @Override
                        public void apply(IPlayerState.TechState techState) {
                            techState.factoryCost = Math.min(techState.factoryCost, cost);
                        }
                    }));
        }
    }

    private static class ReducedIndustrialWaste extends Technology.Construction {
        public ReducedIndustrialWaste(int techLevel, final int waste) {
            super(techLevel, waste > 0 ? "Reduced Industrial Waste " + waste : "Industrial Waste Elimination",
                    new TechModule("Reduced Industrial Waste " + waste, new PlayerBonus() {
                        @Override
                        public void apply(IPlayerState.TechState techState) {
                            techState.industrialWaste = Math.min(techState.industrialWaste, waste);
                        }
                    }));
        }
    }

    private static class CombatSuits extends Technology.Construction {
        public CombatSuits(int techLevel, final String name, final int bonus) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.groundCombatSuite = Math.max(techState.groundCombatSuite, bonus);
                }
            }));
        }
    }

    private static class CombatShield extends Technology.ForceField {
        public CombatShield(int techLevel, final String name, final int bonus) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.groundCombatShield = Math.max(techState.groundCombatShield, bonus);
                }
            }));
        }
    }

    private static class AutomatedRepairUnit extends Technology.Construction {
        public AutomatedRepairUnit(int techLevel, final String name, final int repairsPercentage) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.shipAutoRepairsPercentage =
                            Math.max(techState.shipAutoRepairsPercentage, repairsPercentage);
                }
            }));
        }
    }

    private static class EcmJammer extends Technology.Computer {
        public EcmJammer(int techLevel, int level) {
            super(techLevel, "ECM Jammer Mark " + level, new ShipTech.Ecm(level));
        }
    }

    private static class DeflectorShield extends Technology.ForceField {
        public DeflectorShield(int techLevel, int level) {
            super(techLevel, "Class " + Utils.toRomanNumber(level) + " Deflector Shield", new ShipTech.Shield(level));
        }
    }

    private static class PlanetaryShield extends Technology.ForceField {
        public PlanetaryShield(int techLevel, String name, final int shield) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.planetaryShield = Math.max(techState.planetaryShield, shield);
                }
            }));
        }
    }


    private static class Armor extends Technology.Construction {
        private static final int[] GROUND_DEF = {0, 5, 10, 15, 20, 25, 30};
        public Armor(int techLevel, final int level, String name) {
            super(techLevel, name + " Armor",
                    new ShipTech.Armor(techLevel, name, level, false),
                    new ShipTech.Armor(techLevel, name + " II", level, true),
                    new TechModule(name, new PlayerBonus() {
                        @Override
                        public void apply(IPlayerState.TechState techState) {
                            // TODO: missle base hit point is flat?
                            techState.missleBaseHitPoints = 15;
                            techState.groundCombatArmorDefence =
                                    Math.max(techState.groundCombatArmorDefence, GROUND_DEF[level - 1]);
                        }
                    }));
        }
    }

    private static class RetroEngine extends Technology.Propulsion {
        public RetroEngine(int techLevel, String name) {
            super(techLevel, name,
                    new ShipTech.Engine(name, 1),
                    new ShipTech.ReserveFuelTanks("Reserve Fuel Tanks"),
                    new ShipTech.FuelCell("Basic Fuel Cells", 3));
        }
    }

    private static class Engine extends Technology.Propulsion {
        public Engine(int techLevel, String name, int level) {
            super(techLevel, name, new ShipTech.Engine(name, level));
        }
        public Engine(int techLevel, String name, int level, int combatSpeed) {
            super(techLevel, name, new ShipTech.Engine(name, level, combatSpeed));
        }
    }

    private static class FuelCells extends Technology.Propulsion {
        public FuelCells(int techLevel, String name, int range) {
            super(techLevel, name, new ShipTech.FuelCell(name, range));
        }
    }

    private static class InertialStabilizer extends Technology.Propulsion {
        public InertialStabilizer(int techLevel) {
            super(techLevel, "Inertial Stabilizer", new ShipTech.InertialStabilizer("Inertial Stabilizer"));
        }
    }

    private static class Laser extends Technology.Weapons {
        public Laser(int techLevel, int level) {
            super(techLevel, "Laser " + Utils.toRomanNumber(level), new ShipTech.Laser(level));
        }
    }

    private static class EcologicalRestoration extends Technology.Planetology {
        public EcologicalRestoration(int techLevel, String name, final int pollutionPerBcEliminated) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.pollutionPerBcEliminated =
                            Math.max(techState.pollutionPerBcEliminated, pollutionPerBcEliminated);
                }
            }));
        }
    }

    private static class Terraforming extends Technology.Planetology {
        public Terraforming(int techLevel, String name, final int terraformLevel) {
            super(techLevel, name, new TechModule(name, new PlayerBonus() {
                @Override
                public void apply(IPlayerState.TechState techState) {
                    techState.terraformLevel = Math.max(techState.terraformLevel, terraformLevel);
                }
            }));
        }
    }

    private static class ControlledEnvironment extends Technology.Planetology {
        public ControlledEnvironment(int techLevel, String name, Environment environment) {
            super(techLevel, name, new ShipTech.ColonyBase(
                    environment.isHostile() ? environment.toString() + "  Colony Base" : "Standard Colony Base",
                    environment));
        }
    }

    private static class BioWeapon extends Technology.Planetology {
        public BioWeapon(int techLevel, String name, int level) {
            super(techLevel, name, new ShipTech.BioBomb(name, level));
        }
    }

}
