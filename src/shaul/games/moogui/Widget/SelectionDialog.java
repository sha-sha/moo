package shaul.games.moogui.Widget;

import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Utils;
import shaul.games.moogui.GenericUi;
import shaul.games.moogui.UiFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by Shaul on 7/12/2015.
 */
public class SelectionDialog<T> implements Element.UiListener {

    private final ArrayList<GenericUi<T>> moduleUiList;
    private final Supplier<List<Utils.Available<T>>> listSupplier;
    //    private final JPanel main;
    private T selected = null;
    private final Timer autoKillTimer;
    private final ActionListener timerKillListener;
    private boolean autoKillInOProgress;
    private boolean hasResult;
    private JDialog dialog;

    public SelectionDialog(final List<Utils.Available<T>> list) {
        this(new Supplier<List<Utils.Available<T>>>() {
            @Override
            public List<Utils.Available<T>> get() {
                return list;
            }
        });
    }

    public SelectionDialog(Supplier<List<Utils.Available<T>>> listSupplier) {
        this.listSupplier = listSupplier;
//        this.main = new JPanel();
//        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        moduleUiList = new ArrayList<>();
        timerKillListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autoKillTimer.stop();
                if (dialog != null) {
                    dialog.dispose();
                }
            }
        };
        autoKillTimer = new Timer(100, timerKillListener);
//        addElements(main, optionalTypes);
//        add(main);
//        pack();
    }

    public final void showDialog(JComponent parent) {
        dialog = new JDialog(SwingUtilities.windowForComponent(parent));
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        ArrayList<Object> moduleUiList = new ArrayList<>();
//        ActionListener timerKillListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                autoKillTimer.stop();
//                dialog.dispose();
//            }
//        };
        Timer autoKillTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();
                dialog.dispose();
            }
        });
        addElements(main, listSupplier.get());
        dialog.add(main);
        dialog.setModal(true);
        //dialog.setSelected(shipDesigner.getCurrentModule(slotType));
        dialog.setLocationRelativeTo(parent);
        dialog.pack();
        dialog.setVisible(true);
    }

    protected void addElements(JPanel main, List<Utils.Available<T>> optionalTypes) {
        for (Utils.Available<T> optionalType : optionalTypes) {
            GenericUi<T> moduleUi = createElements(optionalType);
            moduleUi.setEnabled(optionalType.isAvailable());
            if (optionalType.isAvailable()) {
                moduleUi.setListener(this);
            }
            moduleUiList.add(moduleUi);
            main.add(moduleUi);
        }
    }

    protected GenericUi<T> createElements(Utils.Available<T> optionalType) {
        return UiFactory.create(optionalType.get());
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
