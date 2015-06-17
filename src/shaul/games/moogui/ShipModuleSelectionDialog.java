package shaul.games.moogui;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 4/23/2015.
 */
public class ShipModuleSelectionDialog extends BasicSelectionDialog {


    public ShipModuleSelectionDialog(JComponent parent, List<Utils.Available<ShipModule>> optionalModules,
                                     UiFactory.Settings settings, IPlayerState playerState, ShipDesigner shipDesigner) {
        super(parent, optionalModules, settings, playerState, shipDesigner);
    }

    public ShipModule getSelected() {
        return (ShipModule) super.getSelected();
    }

}
