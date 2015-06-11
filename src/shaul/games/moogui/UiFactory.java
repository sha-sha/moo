package shaul.games.moogui;

import shaul.games.moo.model.GameRunTimeError;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;

/**
 * Created by Shaul on 4/26/2015.
 */
public class UiFactory {

    public static<T> GenericUi<T> create(T data) {
        if (data instanceof ShipModule) {
            return (GenericUi<T>) createShipModuleUi((ShipModule) data);
        } else if (data instanceof String) {
            return (GenericUi<T>) new StringStub((String) data);
        } else if (data instanceof Hull) {
            return (GenericUi<T>) new HullUi((Hull) data);
        } else {
            throw new GameRunTimeError("Failed to create UI for : " + data);
        }
    }

    private static GenericUi<ShipModule> createShipModuleUi(ShipModule data) {
        switch (data.getShipComponentType()) {
            case COMPUTER:
                return new ShipModuleComputerUi(data);
            case WEAPON:
                return new ShipModuleWeaponUi(data);
            default:
                return new Stub(data);
        }
    }


    private static class Stub extends GenericUi<ShipModule> {

        private final JLabel label;

        public Stub(ShipModule shipModule) {
            super(shipModule);

            this.label = new JLabel(shipModule.getName());
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

    private static class ShipModuleWeaponUi extends GenericUi<ShipModule> {

        private final JLabel label;

        public ShipModuleWeaponUi(ShipModule shipModule) {
            super(shipModule);

            String text = shipModule.getName() + " DMG: " + shipModule.getModuleData().getWeapon().getMaxDamage();
            if (shipModule.isEmpty()) {
                text = shipModule.getName();
            }

            this.label = new JLabel(text);
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }


    private static class StringStub extends GenericUi<String> {

        private final JLabel label;

        public StringStub(String data) {
            super(data);
            this.label = new JLabel(data);
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

    private static class HullUi extends GenericUi<Hull> {

        private final JLabel label;

        public HullUi(Hull data) {
            super(data);
            this.label = new JLabel(data.toString());
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

}
