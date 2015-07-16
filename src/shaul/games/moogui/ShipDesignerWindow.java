package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Ship.*;
import shaul.games.moo.model.Utils;
import shaul.games.moogui.Widget.Element;
import shaul.games.moogui.Widget.Selector;
import shaul.games.moogui.Widget.UpDown;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;
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
                put(ShipDesign.SlotType.Engine, new UiFactory.Settings(
                        new UiFactory.Settings.Field[] {UiFactory.Settings.Field.Name, UiFactory.Settings.Field.Cost,
                                UiFactory.Settings.Field.Size, UiFactory.Settings.Field.NumberOfEngine,
                                UiFactory.Settings.Field.Space},
                        new double[] {120, 40, 80, 60, 40}));
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
    private final JPanel mainPanel;
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
        double size[][] =  {{160, 240, 160, 240}, {120, 190, 150, 140}};
        mainPanel = new JPanel(new TableLayout(size));

        //guiFrame.setLayout(new TableLayout(size));


        double topleftSizes[][] =  {{395}, {33, 33, 33}};
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
        mainPanel.add(topLeftPanel, "0, 0, 1, 0");

        double topRightSizes[][] =  {{395}, {33, 33, 33}};
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
        mainPanel.add(topRightPanel, "2, 0, 3, 0");


        double weaponLayoutSizes[][] =  {{80, 120, 480, 80}, {30, 40, 40, 40, 40}};
        final JPanel weaponPanel = new JPanel(new TableLayout(weaponLayoutSizes));
        weaponPanel.setBackground(new Color(160, 190, 190));
        weaponPanel.add(new JLabel("Count"), "1, 0");
        weaponPanel.add(new JLabel("Weapon"), "2, 0");
        weaponPanel.add(new JLabel("Notes"), "3, 0");
        initWeapon(shipDesigner, ShipDesign.SlotType.Weapon1, 1, weaponPanel);
        initWeapon(shipDesigner,ShipDesign.SlotType.Weapon2, 2, weaponPanel);
        initWeapon(shipDesigner,ShipDesign.SlotType.Weapon3, 3, weaponPanel);
        initWeapon(shipDesigner, ShipDesign.SlotType.Weapon4, 4, weaponPanel);

        mainPanel.add(weaponPanel, "0, 1, 3, 1");

        double specialLayoutSizes[][] =  {{80, 80, 480, 80}, {40, 40, 40}};
        final JPanel specialPanel = new JPanel(new TableLayout(specialLayoutSizes));
        specialPanel.setBackground(new Color(160, 190, 160));
        initSpecial(shipDesigner, ShipDesign.SlotType.Special1, 0, specialPanel);
        initSpecial(shipDesigner, ShipDesign.SlotType.Special2, 1, specialPanel);
        initSpecial(shipDesigner, ShipDesign.SlotType.Special3, 2, specialPanel);

        mainPanel.add(specialPanel, "0, 2, 3, 2");

        final JPanel lowerPanel = new JPanel(new TableLayout(new double[][] {{200, 200, 240, 160}, {TableLayout.FILL}}));
        mainPanel.add(lowerPanel, "0, 3, 3, 3");

        final JPanel hullPanel = new JPanel(new TableLayout(new double[][] {{200}, {120}}));

        hullPanel.add(createNameSelect(
                        shipDesigner,
                        " Hull: ",
                        new HullSelectorListener(),
                        shipDesigner.getHull().toString()), "0, 0");
        lowerPanel.add(hullPanel, "0, 0");

        infoPanel = new InfoPanel(shipDesigner);
        lowerPanel.add(infoPanel, "2, 0");
        infoPanel.update();

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.add(createButton("Cancel", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelActionListener != null) {
                    panelActionListener.onExit();
                }
            }
        }, null));
        toolbarPanel.add(createButton("Clear", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipDesigner.reset(shipDesigner.getHull());
                refresh(false);
            }
        }, Color.red));
        lowerPanel.add(toolbarPanel, "3, 0");

        mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        mainPanel.getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
            public void actionPerformed(ActionEvent e)
            {
                if (panelActionListener != null) {
                    panelActionListener.onExit();
                }
            }
        });

        //make sure the JFrame is visible
        //mainPanel.pack();
        //mainPanel.setVisible(true);

    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void refresh(boolean debugMode) {
        shipDesigner.update(debugMode);
        updateAll();
    }

    private JButton createButton(String text, ActionListener actionListener, Color color) {
        JButton button = new JButton(text);
        if (color != null) {
            button.setBackground(color);
        }
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

        final Selector<GenericUi<ShipModule>> weapon = new Selector<>();
        weapon.setListener(new Selector.Listener() {
            @Override
            public void onClick() {
                if (openShipComponentSelection(weapon, shipDesigner, slot)) {
                    upDown.setValue(shipDesigner.getCurrentModule(slot).isEmpty() ? 0 : 1);
                }
                //moduleSelectorListener.onSelect(weapon, shipDesigner);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slot));
        initialValue.setListener(new Element.UiListener() {
            @Override
            public void onClick(Element shipModuleUi) {
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
        final Selector<GenericUi<ShipModule>> special = new Selector<>();
        special.setListener(new Selector.Listener() {
            @Override
            public void onClick() {
                openShipComponentSelection(special, shipDesigner, slot);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slot));
        initialValue.setListener(new Element.UiListener() {
            @Override
            public void onClick(Element shipModuleUi) {
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
        double[][] sizes = {{70, 135, 185}, {35}};
        final UiSelectorListener moduleSelectorListener = new UiSelectorListener() {
            @Override
            public void onSelect(Selector Selector, ShipDesigner shipDesigner) {
                openShipComponentSelection(Selector, shipDesigner, slotType);
            }
        };
        JPanel panel = new JPanel();
        panel.setLayout(new TableLayout(sizes));
        panel.add(new JLabel(title), "0, 0");
        final Selector<GenericUi<ShipModule>> slot = new Selector<>();
        slot.setListener(new Selector.Listener() {
            @Override
            public void onClick() {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        final GenericUi<ShipModule> initialValue = UiFactory.create(shipDesigner.getCurrentModule(slotType));
        initialValue.setListener(new Element.UiListener() {
            @Override
            public void onClick(Element shipModuleUi) {
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
            Selector moduleSelection, ShipDesigner shipDesigner, ShipDesign.SlotType slotType) {
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
            //mainPanel.pack();
            mainPanel.invalidate();
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
        final Selector<GenericUi<String>> slot = new Selector<>();
        slot.setListener(new Selector.Listener() {
            @Override
            public void onClick() {
                moduleSelectorListener.onSelect(slot, shipDesigner);
            }
        });
        GenericUi<String> initialValueUi = UiFactory.create(initialValue);
        initialValueUi.setListener(new Element.UiListener() {
            @Override
            public void onClick(Element shipModuleUi) {
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

    private void openHullSelection(Selector moduleSelection, ShipDesigner shipDesigner) {
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
        public void onSelect(Selector moduleSelection, ShipDesigner shipDesigner) {
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

    private static abstract class SlotUpdate implements GenericInfo.Update {

        private final ShipDesign.SlotType slotType;

        SlotUpdate(ShipDesign.SlotType slotType) {
            this.slotType = slotType;
        }

        @Override
        public String update(ShipDesigner shipDesigner) {
            return getValue(shipDesigner) + " [" + shipDesigner.getPowerRequired(slotType) + "]";
        }

        abstract protected String getValue(ShipDesigner shipDesigner);
    }

    private static class InfoPanel extends JPanel implements InfoUi {
        private final ShipDesigner shipDesigner;
        private final JLabel label1;
        private final JLabel label2;
        private final JLabel label3;
        private final JLabel label4;

        InfoPanel(ShipDesigner shipDesigner) {
            setLayout(new GridLayout(4, 2, 0, 0));
            this.shipDesigner = shipDesigner;
            this.label1 = new JLabel("", SwingConstants.RIGHT);
            this.label2 = new JLabel("", SwingConstants.RIGHT);
            this.label3 = new JLabel("", SwingConstants.RIGHT);
            this.label4 = new JLabel("", SwingConstants.RIGHT);
            add(new JLabel("Ship Cost"));
            add(label1);
            add(new JLabel("Total Space"));
            add(label2);
            add(new JLabel("Available Space"));
            add(label3);
            add(new JLabel("Power [Reactors]"));
            add(label4);
        }

        public void update() {
            int spaceUsed = shipDesigner.getUsedSpace();
            ShipModule.EngineShipModule engine =
                    (ShipModule.EngineShipModule) shipDesigner.getCurrentModule(ShipDesign.SlotType.Engine);
            this.label1.setText(shipDesigner.getCost() + " BC");
            this.label2.setText(String.valueOf(shipDesigner.getTotalSpace()));
            this.label3.setText(String.valueOf(shipDesigner.getTotalSpace() - spaceUsed));
            this.label4.setText(String.format("%d  [%.1f]", shipDesigner.getRequiredPower(),
                    engine.getNumberOfEngine(shipDesigner.getRequiredPower())));
        }
    }

    private static final GenericInfo.Update ATTACK_INFO = new SlotUpdate(ShipDesign.SlotType.Computer) {
        @Override public String getValue(ShipDesigner shipDesigner) {
            return "Attack Level: " + shipDesigner.getAttackLevel();
        }};
    private static final GenericInfo.Update HIT_POINT = new SlotUpdate(ShipDesign.SlotType.Armor) {
        @Override public String getValue(ShipDesigner shipDesigner) {
            return "Hit Points: " + shipDesigner.getHitPoints();
        }};
    private static final GenericInfo.Update HIT_ABSORBS = new SlotUpdate(ShipDesign.SlotType.Shield) {
        @Override public String getValue(ShipDesigner shipDesigner) {
            int hitsAbsorbs = shipDesigner.getHitsAbsorbs();
            return "Hit Absorbs: " + shipDesigner.getHitsAbsorbs();
        }};
    private static final GenericInfo.Update MISSLE_DEFENCE = new SlotUpdate(ShipDesign.SlotType.Ecm) {
        @Override public String getValue(ShipDesigner shipDesigner) {
            return "Missle Defence: " + shipDesigner.getMissleDefence();
        }};
    private static final GenericInfo.Update WRAP_AND_DEFENCE = new GenericInfo.Update() {
        @Override public String update(ShipDesigner shipDesigner) {
            return "Wrap: " + shipDesigner.getWrapSpeed() + " Defence ?";
        }};
    private static final GenericInfo.Update COMBAT_SPEED = new SlotUpdate(ShipDesign.SlotType.Maneuver) {
        @Override public String getValue(ShipDesigner shipDesigner) {
            return "Mnver: " + shipDesigner.getManeuverability() + " Spd: " + shipDesigner.getCombatSpeed();
        }};

    private static class Test extends JPanel {
        Test(Color c) {
            super();
            setBackground(c);
        }
    }

    private interface UiSelectorListener {
        void onSelect(Selector uiSelection, ShipDesigner shipDesigner);
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
