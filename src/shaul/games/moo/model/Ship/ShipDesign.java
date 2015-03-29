package shaul.games.moo.model.Ship;

import shaul.games.moo.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 3/24/2015.
 */
public class ShipDesign {
    private final int hullSize;
    private final Module computerSlot;
    private final Module shieldSlot;
    private final Module ecmSlot;
    private final Module armorSlot;
    private final Module engineSlot;
    private final Module maneuverSlot;
    private final List<Utils.Countable<Module>> weaponSlot;
    private final List<Module> specialSlot;

    ShipDesign(int hullSize,
               Module computerSlot,
               Module shieldSlot,
               Module ecmSlot,
               Module armorSlot,
               Module engineSlot,
               Module maneuverSlot,
               List<Utils.Countable<Module>> weaponSlot,
               List<Module> specialSlot) {
        this.hullSize = hullSize;
        this.computerSlot = computerSlot;
        this.shieldSlot = shieldSlot;
        this.ecmSlot = ecmSlot;
        this.armorSlot = armorSlot;
        this.engineSlot = engineSlot;
        this.maneuverSlot = maneuverSlot;
        this.weaponSlot = new ArrayList<>(weaponSlot);
        this.specialSlot = new ArrayList<>(specialSlot);
    }

    private Module[] copy(Module[] array) {
        return Arrays.copyOf(array, array.length);
    }

    public static class Builder {
        private int hullSize;
        private Module computerSlot;
        private Module shieldSlot;
        private Module ecmSlot;
        private Module armorSlot;
        private Module engineSlot;
        private Module maneuverSlot;
        private Module[] weaponSlot;
        private Module[] specialSlot;
    }
}
