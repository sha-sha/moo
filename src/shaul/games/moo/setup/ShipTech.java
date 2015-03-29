package shaul.games.moo.setup;

import shaul.games.moo.model.IShipComponent;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Research.TechBonus;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.ShipAttribute;
import shaul.games.moo.model.ShipComponentCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public class ShipTech {

    // Ship modules.

    private static final List<ShipModule> MODULES = new ArrayList<ShipModule>() {{{
        add(new ShipModule("Ship Scanner", ShipModule.Category.Special, ))
    }}};

    public static List<ShipModule> getTechnologies() {
        return MODULES;
    }

    public static TechModule ecm(int level) {
        return new TechModule(TechModule.SHIP_MODULE_ECM, TechModule.Type.Ship, level, shipMissleDef(level));
    }

    public static TechModule computer(int level) {
        return new TechModule(TechModule.SHIP_MODULE_COMPUTER, TechModule.Type.Ship, level, shipAttack(level));
    }

    public static ShipModule scanner(int cost, int size, int power, int space) {
        return new TechModule(TechModule.SHIP_MODULE_SCANNER, TechModule.Type.Ship, 1,
                Arrays.asList(shipMissleDef(1), shipScan(), shipInitiative(3)));
    }

    public static TechModule armor(int level) {
        return new TechModule(TechModule.SHIP_MODULE_ARMOR, TechModule.Type.Ship, level, shipMissleDef(level));
    }

    // Ship technology bonuses.

    public static TechBonus shipAttack(int value) {
        return new TechBonus(TechBonus.Type.INC_SHIP_ATTACK, value);
    }

    public static TechBonus shipScan() {
        return new TechBonus(TechBonus.Type.SCAN_SHIPS_IN_BATTLE, 1);
    }

    public static TechBonus shipInitiative(int val) {
        return new TechBonus(TechBonus.Type.INC_SHIP_INITIATIVE, val);
    }

    public static TechBonus shipMissleDef(int value) {
        return new TechBonus(TechBonus.Type.INC_SHIP_MISSLE_DEF, value);
    }

    private static class ShipComponent implements IShipComponent {
        private final ShipComponentCategory category;
        private final List<ShipAttribute> attributes;

        ShipComponent(ShipComponentCategory category, List<ShipAttribute> attributes) {
            this.category = category;
            this.attributes = attributes;
        }

        @Override
        public ShipComponentCategory getCategory() { return category; }

        @Override
        public List<ShipAttribute> getAttributes() { return attributes; }

        @Override
        public String toString() {
            return String.format("%s component with %s", category, attributes);
        }
    }
}
