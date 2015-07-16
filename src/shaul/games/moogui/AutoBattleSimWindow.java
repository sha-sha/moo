package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Ship.ShipDesign;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;
import shaul.games.moogui.Widget.SelectionDialog;
import shaul.games.moogui.Widget.Selector;
import shaul.games.moogui.Widget.UpDown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 6/28/2015.
 *
 * +-------------------------------------+
 * | Ships  |    Battle    |  Ships      |
 * | 3 Mice |              |  2 Fearless |
 * +-------------------------------------+
 * | Start | Exit ....                   |
 * +-------------------------------------+
 *
 */
public class AutoBattleSimWindow extends JPanel implements MainGameWindow.MainPanel {

    private final List<ShipDesign> shipDesigns;
    private MainGameWindow.PanelAction panelActionListener;

    AutoBattleSimWindow(IGameLogic gameLogic, IPlayer player) {
        ShipDesigner shipDesigner = new ShipDesigner(gameLogic, player);
        final List<ShipDesign> designs = Arrays.asList(shipDesigner.build());
        this.shipDesigns = designs;

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                if (panelActionListener != null) {
                    panelActionListener.onExit();
                }
            }
        });

        setLayout(new TableLayout(new double[][] {{TableLayout.FILL}, {TableLayout.FILL, 80}}));

        add(createTopPanel(), "0, 0");



        JPanel commandsPanel = new JPanel();
        add(commandsPanel, "0, 1");

        commandsPanel.setBackground(Color.red);

        //ShipDesignSelectorDialog dlg = new ShipDesignSelectorDialog(shipDesigns);
        //dlg.setModal(true);
        //dlg.setVisible(true);


    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();

        topPanel.setLayout(new TableLayout(new double[][] {{120, TableLayout.FILL, 120}, {TableLayout.FILL}}));
        topPanel.setBackground(Color.blue);
        topPanel.add(createShipsPanel(), "0, 0");
        topPanel.add(createShipsPanel(), "2, 0");
        return topPanel;
    }

    private JPanel createShipsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));
        for (int i=0; i<6; i++) {
            panel.add(createShipPanel(), "0, " + i);
        }
        panel.setBackground(Color.cyan);
        return panel;
    }

    private JPanel createShipPanel() {
        Selector<GenericUi<String>> shipSlot = UiFactory.createSelector(Arrays.asList(
                Utils.Available.of("Ship 1"),
                Utils.Available.of("Ship 2"),
                Utils.Available.of("Ship 3"),
                Utils.Available.of("Ship 4")), "None");
        final JPanel panel = new JPanel();

        UpDown upDown = new UpDown(Color.green);
        upDown.setListener(new UpDown.Listener() {
            @Override
            public boolean onChange(UpDown upDown, int newValue) {
                return false;
            }
        });
        panel.add(upDown);
        panel.add(shipSlot);

        panel.setBackground(Color.green);
        return panel;
    }

    @Override
    public void setPanelActionListener(MainGameWindow.PanelAction panelActionListener) {
        this.panelActionListener = panelActionListener;
    }



    private static class ShipDesignSelectorDialog extends JDialog {

        ShipDesignSelectorDialog(List<ShipDesign> shipDesigns) {
            List<String> names = Utils.convert(shipDesigns, new Utils.Function<ShipDesign, String>() {
                @Override
                public String apply(ShipDesign shipDesign) {
                    return shipDesign.getName();
                }
            });
            JList items;
            items = new JList(names.toArray());
            setContentPane(items);
        }

    }

}
