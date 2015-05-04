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
public class ShipModuleSelectionDialog extends JDialog implements UiElement.UiListener {

    private final ArrayList<GenericUi<ShipModule>> moduleUiList;
    private ShipModule selected = null;
    private final Timer autoKillTimer;
    private final ActionListener timerKillListener;
    private boolean autoKillInOProgress;

    public ShipModuleSelectionDialog(JComponent parent, List<Utils.Available<ShipModule>> optionalModules) {
        super(SwingUtilities.windowForComponent(parent));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        moduleUiList = new ArrayList<>();
        timerKillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoKillTimer.stop();
                ShipModuleSelectionDialog.this.dispose();
            }
        };
        autoKillTimer = new Timer(100, timerKillListener);

        for (Utils.Available<ShipModule> optionalModule : optionalModules) {
            GenericUi<ShipModule> moduleUi = UiFactory.create(optionalModule.get());
            moduleUi.setEnabled(optionalModule.isAvailable());
            if (optionalModule.isAvailable()) {
                moduleUi.setListener(this);
            }
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
        GenericUi<ShipModule> shipModuleUi = getShipModuleUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(false);
        }
        this.selected = selected;
        shipModuleUi = getShipModuleUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(true);
        }
    }

    private GenericUi<ShipModule> getShipModuleUi(ShipModule shipModule) {
        for (GenericUi<ShipModule> shipModuleUi : moduleUiList) {
            if (shipModuleUi.getData().equals(shipModule)) {
                return shipModuleUi;
            }
        }
        return null;
    }

    @Override
    public void onClick(UiElement shipModuleUi) {
        if (!autoKillInOProgress) {
            autoKillInOProgress = true;
            setSelected(((GenericUi<ShipModule>) shipModuleUi).getData());
            autoKillTimer.start();
        }
    }
}
