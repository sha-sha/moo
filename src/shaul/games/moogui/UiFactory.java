package shaul.games.moogui;

import shaul.games.moo.model.Ship.HullType;
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
        } else if (data instanceof HullType) {
            return (GenericUi<T>) new HullUi((HullType) data);
        } else {
            return null;
        }
    }

    private static GenericUi<ShipModule> createShipModuleUi(ShipModule data) {
        switch (data.getShipComponentType()) {
            case COMPUTER:
                return new ShipModuleComputerUi(data);
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

    private static class HullUi extends GenericUi<HullType> {

        private final JLabel label;

        public HullUi(HullType data) {
            super(data);
            this.label = new JLabel(data.getName());
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

}