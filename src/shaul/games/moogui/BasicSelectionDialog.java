package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaul on 5/3/2015.
 */
public class BasicSelectionDialog<T> extends JDialog implements UiElement.UiListener {

    private final ArrayList<GenericUi<T>> moduleUiList;
    private T selected = null;
    private final Timer autoKillTimer;
    private final ActionListener timerKillListener;
    private boolean autoKillInOProgress;
    private boolean hasResult;

    public BasicSelectionDialog(JComponent parent, List<Utils.Available<T>> optionalTypes) {
        super(SwingUtilities.windowForComponent(parent));
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

        for (Utils.Available<T> optionalType : optionalTypes) {
            GenericUi<T> moduleUi = UiFactory.create(optionalType.get());
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
    public void onClick(UiElement stringUi) {
        if (!autoKillInOProgress) {
            autoKillInOProgress = true;
            setSelected(((GenericUi<T>) stringUi).getData());
            hasResult = true;
            autoKillTimer.start();
        }
    }
}
