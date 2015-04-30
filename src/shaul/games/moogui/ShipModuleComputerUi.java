package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;

/**
 * Created by Shaul on 4/26/2015.
 */
public class ShipModuleComputerUi extends ShipModuleUi {
    public ShipModuleComputerUi(ShipModule shipModule) {
        super(shipModule);

        add(new JLabel(shipModule.getName()));
    }

    //
}
