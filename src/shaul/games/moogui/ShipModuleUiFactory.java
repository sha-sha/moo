package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;

/**
 * Created by Shaul on 4/26/2015.
 */
public class ShipModuleUiFactory {

    public static ShipModuleUi create(ShipModule shipModule) {
        switch (shipModule.getShipComponentType()) {
            case COMPUTER:
                return new ShipModuleComputerUi(shipModule);
            default:
                return new Stub(shipModule);
        }
    }


    private static class Stub extends ShipModuleUi {

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

}
