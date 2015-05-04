package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;

/**
 * Created by Shaul on 4/26/2015.
 */
public class ShipModuleComputerUi extends GenericUi<ShipModule> {
    private final JLabel label;

    public ShipModuleComputerUi(ShipModule shipModule) {
        super(shipModule);

        this.label = new JLabel(shipModule.getName());
        add(label);
    }

    public void setEnabled(boolean enabled) {
        this.label.setEnabled(enabled);
    }

    //
}
