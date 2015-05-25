package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Ship.*;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * Created by shaul on 4/13/15.
 */
public class ShipDesignerWindow {

    private final InfoPanel infoPanel;
    private ArrayList<InfoUi> infoUis;
    private final JFrame guiFrame;

    public ShipDesignerWindow(IGameLogic gameLogic, IPlayer player) {

        final ShipDesigner shipDesigner = new ShipDesigner(gameLogic, player);

        guiFrame = new JFrame();
        this.infoUis = new ArrayList<InfoUi>();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Ship Designer");
        guiFrame.setSize(640, 480);
        guiFrame.setBounds(100, 100, 600, 300);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        double size[][] =  {{320, 320}, {120, 140, 110, 110}};
        guiFrame.setLayout(new TableLayout(size));



        double topleftSizes[][] =  {{315}, {33, 33, 33}};
        final JPanel topLeftPanel = new JPanel(new TableLayout(topleftSizes));
        topLeftPanel.setBorder(new LineBorder(Color.BLACK, 1));

        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Computer,
                " Computer:",
                new GenericInfo(shipDesigner, ATTACK_INFO)), "0, 0");
        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Shield,
                " Shield:",
                new GenericInfo(shipDesigner, HIT_ABSORBS)), "0, 1");
        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Ecm,
                " Ecm:",
                new GenericInfo(shipDesigner, MISSLE_DEFENCE)), "0, 2");
        guiFrame.add(topLeftPanel, "0, 0");

        double topRightSizes[][] =  {{315}, {33, 33, 33}};
        final JPanel topRightPanel = new JPanel(new TableLayout(topRightSizes));
        topRightPanel.setBorder(new LineBorder(Color.BLACK, 1));

        topRightPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Armor,
                " Armor:",
                new GenericInfo(shipDesigner, HIT_POINT)), "0, 0");
        topRightPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Engine,
                " Engine:",
                new GenericInfo(shipDesigner, WRAP_AND_DEFENCE)), "0, 1");
        guiFrame.add(topRightPanel, "1, 0");

        guiFrame.add(new Test(Color.YELLOW), "0, 1, 1, 1");
        guiFrame.add(new Test(Color.GREEN), "0, 2, 1, 2");

        final JPanel hullPanel = new JPanel(new TableLayout(new double[][] {{320}, {120}}));

        /*
        hullPanel.add(createNameSelect(
                        shipDesigner,
                        " Hull: ",
                        new HullSelectorListener(),
                        shipDesigner.getHullType().getName()), "0, 0");
        guiFrame.add(hullPanel, "0, 3");
        */
        infoPanel = new InfoPanel(shipDesigner);
        guiFrame.add(infoPanel, "1, 3");
        infoPanel.update();


        //make sure the JFrame is visible
        guiFrame.pack();
        guiFrame.setVisible(true);

    }

    private JPanel createShipModuleSelectAndInfo(
            final ShipDesigner shipDesigner,
            final ShipDesign.SlotType slotType,
            final String title,
            final InfoUi infoUi) {
        double[][] sizes = {{70, 135, 105}, {35}};
        final UiSelectorListener moduleSelectorListener = new UiSelectorListener() {
            @Override
            public void onSelect(UiSelection uiSelection, ShipDesigner shipDesigner) {
                openShipComponentSelection(uiSelection, shipDesigner, slotType);
            }
        };
        JPanel panel = new JPanel();
        panel.setLayout(new TableLayout(sizes));
        panel.add(new JLabel(title), "0, 0");
        final UiSelection<GenericUi<ShipModule>> slot = new UiSelection<>();
        slot.setListener(new UiSelection.Listener() {
            @Override
            public void onClick() {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slotType));
        initialValue.setListener(new UiElement.UiListener() {
            @Override
            public void onClick(UiElement shipModuleUi) {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        slot.setBorder(null);
        slot.add(initialValue, BorderLayout.CENTER);
        panel.add(slot, "1, 0");
        panel.add((JComponent) infoUi, "2, 0");
        infoUis.add(infoUi);
        infoUi.update();
        return panel;
    }


    private List<Utils.Available<ShipModule>> buildAvailableModuleList(
            ShipDesigner shipDesigner, ShipDesign.SlotType slotType) {
        List<ShipModule> shipModules = shipDesigner.getAvailableModules(ShipDesign.getAllowedModule(slotType));
        List<Utils.Available<ShipModule>> availableList = new ArrayList<>();
        for (ShipModule module : shipModules) {
            if (shipDesigner.canInstall(slotType, module)) {
                availableList.add(Utils.Available.of(module));
            } else {
                availableList.add(Utils.Available.no(module));
            }
        }
        return availableList;
    }

    private void openShipComponentSelection(
            UiSelection moduleSelection, ShipDesigner shipDesigner, ShipDesign.SlotType slotType) {
        ShipModuleSelectionDialog dialog =
                new ShipModuleSelectionDialog(moduleSelection, buildAvailableModuleList(shipDesigner, slotType));
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getCurrentModule(slotType));
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        System.out.println(" res: " + dialog.getSelected());
        if (dialog.hasResult() && !shipDesigner.getCurrentModule(slotType).equals(dialog.getSelected())) {
            shipDesigner.install(slotType, dialog.getSelected());
            updateAll();
            moduleSelection.removeAll();
            moduleSelection.add(UiFactory.create(shipDesigner.getCurrentModule(slotType)));
            guiFrame.pack();
        }
    }

    private void updateAll() {
        //infoPanel.update();
        for (InfoUi infoUi : infoUis) {
            infoUi.update();
        }
    }

    private static interface InfoUi {
        void update();
    }

    private static class GenericInfo extends JLabel implements InfoUi {
        private final ShipDesigner shipDesigner;
        private final Update update;

        private interface Update {
            String update(ShipDesigner shipDesigner);
        }

        GenericInfo(ShipDesigner shipDesigner, Update update) {
            this.shipDesigner = shipDesigner;
            this.update = update;
            setForeground(Color.red);
            setBackground(Color.RED);
        }

        public void update() {
            setText(this.update.update(shipDesigner));
        }
    }

    private static class InfoPanel extends JPanel implements InfoUi {
        private final ShipDesigner shipDesigner;
        private final JLabel label;

        InfoPanel(ShipDesigner shipDesigner) {
            this.shipDesigner = shipDesigner;
            this.label = new JLabel("Empty");
            add(label);
        }

        public void update() {
            int spaceUsed = shipDesigner.getUsedSpace();
            this.label.setText("Space used: " + spaceUsed + " out of " + shipDesigner.getTotalSpace());
        }
    }

    private static final GenericInfo.Update ATTACK_INFO = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Attack Level: " + shipDesigner.getAttackLevel();
        }};
    private static final GenericInfo.Update HIT_POINT = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Hit Points: " + shipDesigner.getHitPoints();
        }};
    private static final GenericInfo.Update HIT_ABSORBS = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            int hitsAbsorbs = shipDesigner.getHitsAbsorbs();
            return "Hit Absorbs: " + shipDesigner.getHitsAbsorbs();
        }};
    private static final GenericInfo.Update MISSLE_DEFENCE = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Missle Defence: " + shipDesigner.getMissleDefence();
        }};
    private static final GenericInfo.Update WRAP_AND_DEFENCE = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Wrap ? Defence ?";
        }};

    private static class Test extends JPanel {
        Test(Color c) {
            super();
            setBackground(c);
        }
    }

    private interface UiSelectorListener {
        void onSelect(UiSelection uiSelection, ShipDesigner shipDesigner);
    }

    private static class UiSelection<T extends JComponent & UiElement>
            extends JPanel implements MouseListener, UiElement.UiListener {

        private Listener listener;

        @Override
        public void onClick(UiElement ui) {
            doClickAction();
        }

        public interface Listener {
            void onClick();
        }

        public UiSelection() {
            super();
            addMouseListener(this);
        }

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        public void add(T ui) {
            super.add(ui);
            ui.setListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(contains(e.getPoint())){
                doClickAction();
            }
        }

        private void doClickAction() {
            System.out.println("UiSelection.doClickAction");
            if (listener != null) {
                listener.onClick();
            } else {
                System.out.print("ModuleSelection has mo listener!");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
