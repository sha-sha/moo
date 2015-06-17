package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Ship.*;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * Created by shaul on 4/13/15.
 */
public class ShipDesignerWindow implements MainGameWindow.MainPanel {

    private static final Map<ShipDesign.SlotType, UiFactory.Settings> SLOT_UI_SETTINGS =
            new HashMap<ShipDesign.SlotType, UiFactory.Settings>(){{{
                put(ShipDesign.SlotType.Armor, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size},
                        new double[] {100, 40, 40}));
                put(ShipDesign.SlotType.Computer, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size, UiFactory.Settings.Field.Power,
                                UiFactory.Settings.Field.Space},
                        new double[] {100, 40, 40, 40, 40}));
                put(ShipDesign.SlotType.Ecm, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size},
                        new double[] {100, 40, 40}));
                put(ShipDesign.SlotType.Maneuver, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name,
                                UiFactory.Settings.Field.CombatSpeed, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Power, UiFactory.Settings.Field.Space},
                        new double[] {100, 50, 40, 40, 40}));
                put(ShipDesign.SlotType.Shield, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size, UiFactory.Settings.Field.Power},
                        new double[] {80, 80, 80, 80}));
                put(ShipDesign.SlotType.Weapon1, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size},
                        new double[] {100, 40, 40}));
                put(ShipDesign.SlotType.Weapon1, get(ShipDesign.SlotType.Weapon1));
                put(ShipDesign.SlotType.Weapon2, get(ShipDesign.SlotType.Weapon2));
            }}};

    private final InfoPanel infoPanel;
    private final IPlayer player;
    private final ShipDesigner shipDesigner;
    //private ArrayList<InfoUi> infoUis;
    private Updater uiUpdater = new Updater();
    // private final JFrame guiFrame;
    private final JPanel topPanel;
    private MainGameWindow.PanelAction panelActionListener;

    public ShipDesignerWindow(IGameLogic gameLogic, IPlayer player) {
        this.player = player;

        this.shipDesigner = new ShipDesigner(gameLogic, player);
/*
        guiFrame = new JFrame();

        guiFrame.setTitle("Ship Designer");
        guiFrame.setSize(640, 480);
        guiFrame.setBounds(100, 100, 600, 300);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);
*/
        double size[][] =  {{160, 160, 160, 160}, {120, 140, 110, 110}};
        topPanel = new JPanel(new TableLayout(size));
        //guiFrame.setLayout(new TableLayout(size));


        double topleftSizes[][] =  {{315}, {33, 33, 33}};
        final JPanel topLeftPanel = new JPanel(new TableLayout(topleftSizes));
        topLeftPanel.setBorder(new LineBorder(Color.BLACK, 1));

        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Computer,
                " Computer:",
                new GenericInfo(shipDesigner, ATTACK_INFO), uiUpdater), "0, 0");
        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Shield,
                " Shield:",
                new GenericInfo(shipDesigner, HIT_ABSORBS), uiUpdater), "0, 1");
        topLeftPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Ecm,
                " Ecm:",
                new GenericInfo(shipDesigner, MISSLE_DEFENCE), uiUpdater), "0, 2");
        topPanel.add(topLeftPanel, "0, 0, 1, 0");

        double topRightSizes[][] =  {{315}, {33, 33, 33}};
        final JPanel topRightPanel = new JPanel(new TableLayout(topRightSizes));
        topRightPanel.setBorder(new LineBorder(Color.BLACK, 1));

        topRightPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Armor,
                " Armor:",
                new GenericInfo(shipDesigner, HIT_POINT), uiUpdater), "0, 0");
        topRightPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Engine,
                " Engine:",
                new GenericInfo(shipDesigner, WRAP_AND_DEFENCE), uiUpdater), "0, 1");
        topRightPanel.add(createShipModuleSelectAndInfo(shipDesigner,
                ShipDesign.SlotType.Maneuver,
                " Maneuver:",
                new GenericInfo(shipDesigner, COMBAT_SPEED),
                uiUpdater), "0, 2");
        topPanel.add(topRightPanel, "2, 0, 3, 0");


        double weaponLayoutSizes[][] =  {{80, 40, 400, 80}, {20, 30, 30, 30, 30}};
        final JPanel weaponPanel = new JPanel(new TableLayout(weaponLayoutSizes));
        weaponPanel.setBackground(new Color(160, 190, 190));
        weaponPanel.add(new JLabel("Count"), "1, 0");
        weaponPanel.add(new JLabel("Weapon"), "2, 0");
        weaponPanel.add(new JLabel("Notes"), "3, 0");
        initWeapon(shipDesigner, ShipDesign.SlotType.Weapon1, 1, weaponPanel);
        initWeapon(shipDesigner,ShipDesign.SlotType.Weapon2, 2, weaponPanel);
        initWeapon(shipDesigner,ShipDesign.SlotType.Weapon3, 3, weaponPanel);
        initWeapon(shipDesigner,ShipDesign.SlotType.Weapon4, 4, weaponPanel);

        topPanel.add(weaponPanel, "0, 1, 3, 1");

        double specialLayoutSizes[][] =  {{80, 40, 400, 80}, {40, 40, 40}};
        final JPanel specialPanel = new JPanel(new TableLayout(specialLayoutSizes));
        specialPanel.setBackground(new Color(160, 190, 160));
        initSpecial(shipDesigner, ShipDesign.SlotType.Special1, 0, specialPanel);
        initSpecial(shipDesigner, ShipDesign.SlotType.Special2, 1, specialPanel);
        initSpecial(shipDesigner, ShipDesign.SlotType.Special3, 2, specialPanel);

        topPanel.add(specialPanel, "0, 2, 3, 2");

        final JPanel hullPanel = new JPanel(new TableLayout(new double[][] {{160}, {120}}));

        hullPanel.add(createNameSelect(
                        shipDesigner,
                        " Hull: ",
                        new HullSelectorListener(),
                        shipDesigner.getHull().toString()), "0, 0");
        topPanel.add(hullPanel, "0, 3");

        infoPanel = new InfoPanel(shipDesigner);
        topPanel.add(infoPanel, "1, 3");
        infoPanel.update();

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.add(createButton("Exit", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelActionListener != null) {
                    panelActionListener.onExit();
                }
            }
        }));
        toolbarPanel.add(createButton("Reset", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipDesigner.reset();
            }
        }));
        topPanel.add(toolbarPanel, "3, 3");


        //make sure the JFrame is visible
        //topPanel.pack();
        //topPanel.setVisible(true);

    }

    public JPanel getPanel() {
        return topPanel;
    }

    public void refresh(boolean debugMode) {
        shipDesigner.update(debugMode);
        updateAll();
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void initWeapon(final ShipDesigner shipDesigner, final ShipDesign.SlotType slot, int row, final JPanel weaponPanel) {
        weaponPanel.add(new JLabel("Weapon " + row), "0, " + row);
        final UpDown upDown = new UpDown(weaponPanel.getBackground());
        upDown.setListener(new UpDown.Listener() {
            @Override
            public boolean onChange(UpDown upDown, int newValue) {
                if (newValue >= 0 && shipDesigner.canChangeCount(slot, newValue)) {
                    shipDesigner.changeCount(slot, newValue);
                    updateAll();
                    return true;
                }
                return false;
            }
        });
        weaponPanel.add(upDown, "1, " + row);

        final UiSelection<GenericUi<ShipModule>> weapon = new UiSelection<>();
        weapon.setListener(new UiSelection.Listener() {
            @Override
            public void onClick() {
                if (openShipComponentSelection(weapon, shipDesigner, slot)) {
                    upDown.setValue(shipDesigner.getCurrentModule(slot).isEmpty() ? 0 : 1);
                }
                //moduleSelectorListener.onSelect(weapon, shipDesigner);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slot));
        initialValue.setListener(new UiElement.UiListener() {
            @Override
            public void onClick(UiElement shipModuleUi) {
                if (openShipComponentSelection(weapon, shipDesigner, slot)) {
                    upDown.setValue(shipDesigner.getCurrentModule(slot).isEmpty() ? 0 : 1);
                }
            }
        });
        initialValue.setBackground(weaponPanel.getBackground());
        weapon.setBackground(weaponPanel.getBackground());
        weapon.setBorder(null);
        weapon.add(initialValue, BorderLayout.CENTER);
        weaponPanel.add(weapon, "2, " + row);
        uiUpdater.register(new InfoUi() {
            @Override
            public void update() {
                weapon.removeAll();
                weapon.revalidate();
                weapon.repaint();
                GenericUi<ShipModule> newUi = UiFactory.create(shipDesigner.getCurrentModule(slot));
                newUi.setBackground(weaponPanel.getBackground());
                weapon.add(newUi);

            }
        });
    }

    private void initSpecial(final ShipDesigner shipDesigner, final ShipDesign.SlotType slot, int row, final JPanel panel) {
        panel.add(new JLabel("Special " + row), "0, " + row);
        final UiSelection<GenericUi<ShipModule>> special = new UiSelection<>();
        special.setListener(new UiSelection.Listener() {
            @Override
            public void onClick() {
                openShipComponentSelection(special, shipDesigner, slot);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slot));
        initialValue.setListener(new UiElement.UiListener() {
            @Override
            public void onClick(UiElement shipModuleUi) {
                openShipComponentSelection(special, shipDesigner, slot);
            }
        });
        initialValue.setBackground(panel.getBackground());
        special.setBackground(panel.getBackground());
        special.setBorder(null);
        special.add(initialValue, BorderLayout.CENTER);
        panel.add(special, "2, " + row);
        uiUpdater.register(new InfoUi() {
            @Override
            public void update() {
                special.removeAll();
                special.revalidate();
                special.repaint();
                GenericUi<ShipModule> newUi = UiFactory.create(shipDesigner.getCurrentModule(slot));
                newUi.setBackground(panel.getBackground());
                special.add(newUi);

            }
        });
    }

    private JPanel createShipModuleSelectAndInfo(
            final ShipDesigner shipDesigner,
            final ShipDesign.SlotType slotType,
            final String title,
            final InfoUi infoUi,
            Updater uiUpdater) {
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
        uiUpdater.register(new InfoUi() {
            @Override
            public void update() {
                infoUi.update();
                slot.removeAll();
                slot.revalidate();
                slot.repaint();
                slot.add(UiFactory.create(shipDesigner.getCurrentModule(slotType)));

            }
        });
        infoUi.update();
        return panel;
    }

    private UiFactory.Settings getUiSettingsOf(ShipDesign.SlotType slotType) {
        return SLOT_UI_SETTINGS.get(slotType);
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

    private boolean openShipComponentSelection(
            UiSelection moduleSelection, ShipDesigner shipDesigner, ShipDesign.SlotType slotType) {
        ShipModuleSelectionDialog dialog = new ShipModuleSelectionDialog(
                moduleSelection,
                buildAvailableModuleList(shipDesigner, slotType),
                getUiSettingsOf(slotType),
                player.getPlayerState(),
                shipDesigner);
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getCurrentModule(slotType));
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        System.out.println(" res: " + dialog.getSelected());
        if (dialog.hasResult() && !shipDesigner.getCurrentModule(slotType).equals(dialog.getSelected())) {
            shipDesigner.install(slotType, dialog.getSelected());
            updateAll();
//            moduleSelection.removeAll();
//            moduleSelection.revalidate();
//            moduleSelection.repaint();
//            moduleSelection.add(UiFactory.create(shipDesigner.getCurrentModule(slotType)));
            //topPanel.pack();
            topPanel.invalidate();
            moduleSelection.invalidate();
            return true;
        }
        return false;
    }

    private JPanel createNameSelect(
            final ShipDesigner shipDesigner,
            final String title,
            final UiSelectorListener moduleSelectorListener,
            final String initialValue) {
        double[][] sizes = {{80, 80}, {35}};
        JPanel panel = new JPanel();
        panel.setLayout(new TableLayout(sizes));
        panel.add(new JLabel(title), "0, 0");
        final UiSelection<GenericUi<String>> slot = new UiSelection<>();
        slot.setListener(new UiSelection.Listener() {
            @Override
            public void onClick() {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        GenericUi<String> initialValueUi = UiFactory.create(initialValue);
        initialValueUi.setListener(new UiElement.UiListener() {
            @Override
            public void onClick(UiElement shipModuleUi) {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        slot.setBorder(null);
        slot.add(initialValueUi, BorderLayout.CENTER);
        panel.add(slot, "1, 0");
        return panel;
    }

    private void updateAll() {
        infoPanel.update();
        uiUpdater.update();
    }

    private void openHullSelection(UiSelection moduleSelection, ShipDesigner shipDesigner) {
        List<Utils.Available<Hull>> availableHullTypes = new ArrayList<>();
        for (Hull hull : Hull.values()) {
            availableHullTypes.add(new Utils.Available<Hull>(
                    hull, shipDesigner.getHull().equals(hull) || shipDesigner.canChangeHull(hull)));
        }
        BasicSelectionDialog<Hull> dialog =
                new BasicSelectionDialog<Hull>(
                        moduleSelection, availableHullTypes, null, null, null);
        dialog.setModal(true);
        dialog.setSelected(shipDesigner.getHull());
        dialog.setLocationRelativeTo(moduleSelection);
        dialog.pack();
        dialog.setVisible(true);
        shipDesigner.setHull(dialog.getSelected());
        updateAll();
        moduleSelection.removeAll();
        moduleSelection.add(UiFactory.create(shipDesigner.getHull()));
    }

    @Override
    public void setPanelActionListener(MainGameWindow.PanelAction panelActionListener) {
        this.panelActionListener = panelActionListener;
    }

    private class HullSelectorListener implements UiSelectorListener {
        @Override
        public void onSelect(UiSelection moduleSelection, ShipDesigner shipDesigner) {
            openHullSelection(moduleSelection, shipDesigner);
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
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("Space used: " + spaceUsed + " out of " + shipDesigner.getTotalSpace());
            sb.append("<br>");
            sb.append("Cost: " + shipDesigner.getCost());
            sb.append("</html>");
            this.label.setText(sb.toString());
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
            return "Wrap: " + shipDesigner.getWrapSpeed() + " Defence ?";
        }};
    private static final GenericInfo.Update COMBAT_SPEED = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Mnver: " + shipDesigner.getManeuverability() + " Spd: " + shipDesigner.getCombatSpeed();
        }};
    private static final GenericInfo.Update MANEUVER = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Wrap: " + shipDesigner.getWrapSpeed() + " Defence ?";
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

    private static class UpDown extends JPanel implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int newVal = current;
            if (e.getSource() == up) {
                newVal++;
            } else if (e.getSource() == down) {
                newVal--;
            }
            if (listener != null && listener.onChange(this, newVal)) {
                setValue(newVal);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        interface Listener {
            boolean onChange(UpDown upDown, int newValue);
        }

        private final BasicArrowButton up;
        private final BasicArrowButton down;
        private final JLabel text;
        private Listener listener;
        private int current = 0;

        UpDown(Color back) {
            JPanel buttons = new JPanel();
            double buttonSizes[][] = {{TableLayout.FILL}, {0.5, 0.5}};
            buttons.setLayout(new TableLayout(buttonSizes));
            this.up = new BasicArrowButton(SwingConstants.NORTH);
            up.addMouseListener(this);
            buttons.add(up, "0, 0");

            down = new BasicArrowButton(SwingConstants.SOUTH);
            down.addMouseListener(this);
            buttons.add(down, "0, 1");
            buttons.setBackground(back);

            double sizes[][] = {{16, TableLayout.FILL}, {TableLayout.FILL}};
            setLayout(new TableLayout(sizes));
            setBorder(new EmptyBorder(2, 2, 2, 2));
            setBackground(back);
            add(buttons, "0, 0");
            text = new JLabel("0");
            add(text, "1, 0");
        }

        void setListener(Listener listener) {
            this.listener = listener;
        }

        void setValue(int val) {
            this.current = val;
            text.setText("" + val);
        }

        int getValue() {
            return this.current;
        }

    }

    private static class Updater {
        List<InfoUi> infoUiList = new ArrayList<>();

        void register(InfoUi infoUi) { infoUiList.add(infoUi); }

        void update() {
            for (InfoUi infoUi : infoUiList) {
                infoUi.update();
            }
        }
    }

}
