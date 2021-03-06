package shaul.games.moogui;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;
import shaul.games.moogui.Widget.Element;
import shaul.games.moogui.Widget.SelectionDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 5/3/2015.
 */
public class BasicSelectionDialog<T> extends JDialog implements Element.UiListener {

    private final ArrayList<GenericUi<T>> moduleUiList;
    private final UiFactory.Settings settings;
    private final IPlayerState playerState;
    private final ShipDesigner shipDesigner;
    private T selected = null;
    private final Timer autoKillTimer;
    private final ActionListener timerKillListener;
    private boolean autoKillInOProgress;
    private boolean hasResult;

    public BasicSelectionDialog(JComponent parent, List<Utils.Available<T>> optionalTypes,
                                UiFactory.Settings settings, IPlayerState playerState, ShipDesigner shipDesigner) {
        super(SwingUtilities.windowForComponent(parent));
        this.settings = settings;
        this.playerState = playerState;
        this.shipDesigner = shipDesigner;
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        moduleUiList = new ArrayList<>();
        timerKillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoKillTimer.stop();
                BasicSelectionDialog.this.dispose();
            }
        };
        autoKillTimer = new Timer(100, timerKillListener);

        if (settings != null) {
            main.add(UiFactory.createTitles(settings));
        }
        for (Utils.Available<T> optionalType : optionalTypes) {
            GenericUi<T> moduleUi = UiFactory.create(optionalType.get(), settings, playerState, shipDesigner);
            moduleUi.setEnabled(optionalType.isAvailable());
            if (optionalType.isAvailable()) {
                moduleUi.setListener(this);
            }
            moduleUiList.add(moduleUi);
            main.add(moduleUi);
        }
        add(main);
        pack();
    }

    public T getSelected() {
        return selected;
    }
    public boolean hasResult() {
        return hasResult;
    }

    public void setSelected(T selected) {
        GenericUi<T> shipModuleUi = getUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(false);
        }
        this.selected = selected;
        shipModuleUi = getUi(this.selected);
        if (shipModuleUi != null) {
            shipModuleUi.setSelected(true);
        }
    }

    private GenericUi<T> getUi(T selected) {
        for (GenericUi<T> shipModuleUi : moduleUiList) {
            if (shipModuleUi.getData().equals(selected)) {
                return shipModuleUi;
            }
        }
        return null;
    }

    @Override
    public void onClick(Element stringUi) {
        if (!autoKillInOProgress) {
            autoKillInOProgress = true;
            setSelected(((GenericUi<T>) stringUi).getData());
            hasResult = true;
            autoKillTimer.start();
        }
    }
}
