package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import javax.swing.*;
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
    private final AttackLevelInfo attackLevelInfo;
    private ArrayList<InfoUi> infoUis;

    public ShipDesignerWindow(IGameLogic gameLogic, IPlayer player) {

        final ShipDesigner shipDesigner = new ShipDesigner(gameLogic, player);
        shipDesigner.changeHullSize(2);

        JFrame guiFrame = new JFrame();
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



        double topleftSizes[][] =  {{320}, {33, 33, 33}};
        final JPanel topLeftPanel = new JPanel(new TableLayout(topleftSizes));
        //topLeftPanel.add(new JLabel("Computer:"), "0, 0");


        topLeftPanel.add(createNameSelectAndInfo(shipDesigner,
                " Computer:",
                new ComputerSelectorListener(),
                ShipModuleUiFactory.create(shipDesigner.getComputerModule()),
                new AttackLevelInfo(shipDesigner)), "0, 0");
        topLeftPanel.add(createNameSelectAndInfo(shipDesigner,
                " Shield:",
                new ShieldSelectorListener(),
                ShipModuleUiFactory.create(shipDesigner.getShieldModule()),
                new HitAbsorbsInfo(shipDesigner)), "0, 1");
        topLeftPanel.add(createNameSelectAndInfo(shipDesigner,
                " Ecm:",
                new EcmSelectorListener(),
                ShipModuleUiFactory.create(shipDesigner.getEcmModule()),
                new MissleDefenceInfo(shipDesigner)), "0, 2");


        final ModuleSelection computerSlot = new ModuleSelection();
        computerSlot.setListener(new ModuleSelection.Listener() {
            @Override
            public void onClick() {
                openComputerSelection(computerSlot, shipDesigner);
            }
        });
        computerSlot.setBorder(null);
        computerSlot.add(ShipModuleUiFactory.create(shipDesigner.getComputerModule()), BorderLayout.CENTER);
        //topLeftPanel.add(computerSlot, "1, 0");
        this.attackLevelInfo = new AttackLevelInfo(shipDesigner);
        //topLeftPanel.add(attackLevelInfo, "2, 0");
        guiFrame.add(topLeftPanel, "0, 0");



        guiFrame.add(new Test(Color.RED), "1, 0");
        guiFrame.add(new Test(Color.YELLOW), "0, 1, 1, 1");
        guiFrame.add(new Test(Color.GREEN), "0, 2, 1, 2");
        guiFrame.add(new Test(Color.ORANGE), "0, 3");
        infoPanel = new InfoPanel(shipDesigner);
        guiFrame.add(infoPanel, "1, 3");

        infoPanel.update();


/*
        final JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLUE);
        topPanel.setMinimumSize(new Dimension(640, 450));
        guiFrame.add(topPanel, BorderLayout.PAGE_START);

        final JPanel equipmentsPanel = new JPanel();
        equipmentsPanel.setBackground(Color.GREEN);
        equipmentsPanel.setSize(640, 150);
        guiFrame.add(equipmentsPanel, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.RED);
        bottomPanel.setSize(640, 180);
        guiFrame.add(bottomPanel, BorderLayout.PAGE_END);

        //        .setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
*/
        //make sure the JFrame is visible
        guiFrame.pack();
        guiFrame.setVisible(true);

    }

    private JPanel createNameSelectAndInfo(
            final ShipDesigner shipDesigner,
            final String title,
            final ModuleSelectorListener moduleSelectorListener,
            final ShipModuleUi initialValue,
            final InfoUi infoUi) {
        double[][] sizes = {{80, 140, 100}, {35}};
        JPanel panel = new JPanel();
        panel.setLayout(new TableLayout(sizes));
        panel.add(new JLabel(title), "0, 0");
        final ModuleSelection slot = new ModuleSelection();
        slot.setListener(new ModuleSelection.Listener() {
            @Override
            public void onClick() {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        initialValue.setListener(new ShipModuleUi.Listener() {
            @Override
            public void onClick(ShipModuleUi shipModuleUi) {
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

    private void openComputerSelection(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
        ShipModuleSelectionDialog dialog =
                new ShipModuleSelectionDialog(
                        moduleSelection, shipDesigner.getAvailableComputerModules());
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getComputerModule());
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        shipDesigner.setComputerModule(dialog.getSelected().getName() );
        updateAll();
        moduleSelection.removeAll();
        moduleSelection.add(ShipModuleUiFactory.create(shipDesigner.getComputerModule()));
    }

    private void openShieldSelection(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
        ShipModuleSelectionDialog dialog =
                new ShipModuleSelectionDialog(
                        moduleSelection, shipDesigner.getAvailableShieldModules());
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getShieldModule());
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        shipDesigner.setShieldModule(dialog.getSelected().getName());
        updateAll();
        moduleSelection.removeAll();
        moduleSelection.add(ShipModuleUiFactory.create(shipDesigner.getShieldModule()));
    }

    private void openEcmSelection(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
        ShipModuleSelectionDialog dialog =
                new ShipModuleSelectionDialog(
                        moduleSelection, shipDesigner.getAvailableEcmModules());
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getEcmModule());
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        shipDesigner.setEcmModule(dialog.getSelected().getName());
        updateAll();
        moduleSelection.removeAll();
        moduleSelection.add(ShipModuleUiFactory.create(shipDesigner.getEcmModule()));
    }

    private void updateAll() {
        infoPanel.update();
        for (InfoUi infoUi : infoUis) {
            infoUi.update();
        }
    }

    private interface ModuleSelectorListener {
        void onSelect(ModuleSelection moduleSelection, ShipDesigner shipDesigner);
    }

    private class ComputerSelectorListener implements ModuleSelectorListener {
        @Override
        public void onSelect(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
            openComputerSelection(moduleSelection, shipDesigner);
        }
    }

    private class ShieldSelectorListener implements ModuleSelectorListener {
        @Override
        public void onSelect(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
            openShieldSelection(moduleSelection, shipDesigner);
        }
    }

    private class EcmSelectorListener implements ModuleSelectorListener {
        @Override
        public void onSelect(ModuleSelection moduleSelection, ShipDesigner shipDesigner) {
            openEcmSelection(moduleSelection, shipDesigner);
        }
    }

    private JPanel createInfoPanel(ShipDesigner shipDesigner) {
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Test"));

        return infoPanel;
    }

    private static interface InfoUi {
        void update();
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
            int spaceUsed = shipDesigner.getTotalSpace() - shipDesigner.getAvailableSpace();
            this.label.setText("Space used: " + spaceUsed + " out of " + shipDesigner.getTotalSpace());
        }
    }

    private static class AttackLevelInfo extends JLabel implements InfoUi {
        private final ShipDesigner shipDesigner;

        public AttackLevelInfo(ShipDesigner shipDesigner) {
            this.shipDesigner = shipDesigner;
            setForeground(Color.red);
            setBackground(Color.RED);
        }

        public void update() {
            int attackLevel = shipDesigner.getAttackLevel();
            setText("Attack Level: " + attackLevel);
        }

    }

    private static class HitAbsorbsInfo extends JLabel implements InfoUi {
        private final ShipDesigner shipDesigner;

        public HitAbsorbsInfo(ShipDesigner shipDesigner) {
            this.shipDesigner = shipDesigner;
            setForeground(Color.red);
            setBackground(Color.RED);
        }

        public void update() {
            int hitsAbsorbs = shipDesigner.getHitsAbsorbs();
            setText("Hit Absorbs: " + hitsAbsorbs);
        }

    }

    private static class MissleDefenceInfo extends JLabel implements InfoUi {
        private final ShipDesigner shipDesigner;

        public MissleDefenceInfo(ShipDesigner shipDesigner) {
            this.shipDesigner = shipDesigner;
            setForeground(Color.red);
            setBackground(Color.RED);
        }

        public void update() {
            int missleDefence = shipDesigner.getMissleDefence();
            setText("Missle Defence: " + missleDefence);
        }

    }

    private static class Test extends JPanel {
        Test(Color c) {
            super();
            setBackground(c);
        }
    }

    private static class AvailableOptions extends JComboBox<String> {

        //private List<Utils.Available<? extends TechModule>> options;
        private List<Utils.Available<ShipModule>> options;

        AvailableOptions(List<Utils.Available<ShipModule>> options) {
            super(getList(options));
            this.options = options;
            setRenderer(new CellRenderer(options));
        }

        public void setOptions(List<Utils.Available<ShipModule>> options) {
            setModel(new DefaultComboBoxModel<String>(getList(options)));
            this.options = options;
            setRenderer(new CellRenderer(options));
        }

        private static String[] getList(List<Utils.Available<ShipModule>> options) {
            List<String> list = new ArrayList<>();
            for (Utils.Available<? extends TechModule> option : options) {
                list.add(option.get().getName());
            }
            return list.toArray(new String[list.size()]);
        }

        @Override
        public void setSelectedIndex(int index) {
            if (options.get(index).isAvailable()) {
                super.setSelectedIndex(index);
            }
        }

        private static class CellRenderer extends JLabel implements ListCellRenderer {

            private final List<Utils.Available<ShipModule>> options;

            CellRenderer(List<Utils.Available<ShipModule>> options) {
                this.options = options;
            }

            @Override
            public Component getListCellRendererComponent(
                    JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                setText(value.toString());
                if (index < 0 || options.get(index).isAvailable()) {
                    setFocusable(true);
                    setEnabled(true);
                } else {
                    setFocusable(false);
                    setEnabled(false);
                }
                return this;
            }
        }
    }

    private static class ModuleSelection extends JPanel implements MouseListener, ShipModuleUi.Listener {

        private Listener listener;

        @Override
        public void onClick(ShipModuleUi shipModuleUi) {
            doClickAction();
        }

        public interface Listener {
            void onClick();
        }

        public ModuleSelection() {
            super();
            addMouseListener(this);
        }

        public void setListener(Listener listener) {
            this.listener = listener;
        }

        public void add(ShipModuleUi shipModuleUi) {
            super.add(shipModuleUi);
            shipModuleUi.setListener(this);
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
