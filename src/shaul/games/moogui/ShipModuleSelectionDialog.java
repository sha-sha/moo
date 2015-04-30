package shaul.games.moogui;

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
public class ShipModuleSelectionDialog extends JDialog implements ShipModuleUi.Listener {

    private final ArrayList<ShipModuleUi> moduleUiList;
    private ShipModule selected = null;
    private final Timer autoKillTimer;
    private final ActionListener timerKillListener;
    private boolean autoKillInOProgress;

    public ShipModuleSelectionDialog(JComponent parent, List<Utils.Available<ShipModule>> optionalModules) {
        super(SwingUtilities.windowForComponent(parent));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        moduleUiList = new ArrayList<ShipModuleUi>();
        timerKillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoKillTimer.stop();
                ShipModuleSelectionDialog.this.dispose();
            }
        };
        autoKillTimer = new Timer(100, timerKillListener);

        for (Utils.Available<ShipModule> optionalModule : optionalModules) {
            ShipModuleUi moduleUi = ShipModuleUiFactory.create(optionalModule.get());
            final ShipModule selectedModule = optionalModule.get();
            moduleUi.setListener(this);
            moduleUiList.add(moduleUi);
            main.add(moduleUi);
        }
        add(main);
        pack();
    }

    public ShipModule getSelected() {
        return selected;
    }

    public void setSelected(ShipModule selected) {
        ShipModuleUi shipModuleUi = getShipModuleUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(false);
        }
        this.selected = selected;
        shipModuleUi = getShipModuleUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(true);
        }
    }

    private ShipModuleUi getShipModuleUi(ShipModule shipModule) {
        for (ShipModuleUi shipModuleUi : moduleUiList) {
            if (shipModuleUi.getShipModule().equals(shipModule)) {
                return shipModuleUi;
            }
        }
        return null;
    }

    @Override
    public void onClick(ShipModuleUi shipModuleUi) {
        if (!autoKillInOProgress) {
            autoKillInOProgress = true;
            setSelected(shipModuleUi.getShipModule());
            autoKillTimer.start();
        }
    }
}
