package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Shaul on 6/14/2015.
 */
public class MainGameWindow {
    private final IPlayer player;
    private final JFrame guiFrame;

    interface MainPanel {
        void setPanelActionListener(PanelAction panelActionListener);
    }

    interface PanelAction {
        void onExit();
    }

    public MainGameWindow(final IGameLogic gameLogic, final IPlayer player) {
        this.player = player;
        guiFrame = new JFrame();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Master Of Orion");
        guiFrame.setSize(800, 600);
        guiFrame.setBounds(100, 100, 600, 300);

        //double size[][] =  {{640}, {100, 340, 40}};
        final JPanel topPanel = new JPanel(new CardLayout());
        guiFrame.add(topPanel);
        //guiFrame.setLayout(new TableLayout(size));
        //guiFrame.setLayout(new CardLayout());
        //LayoutManager p = guiFrame.getLayout();



        double size[][] =  {{800}, {100, 460, 40}};
        JPanel panel = new JPanel(new TableLayout(size));
        //guiFrame.setLayout(new TableLayout(size));

        final ShipDesignerWindow shipDesign = new ShipDesignerWindow(gameLogic, player);

        PanelAction exitPanelAction = new PanelAction() {
            @Override
            public void onExit() {
                CardLayout cl = (CardLayout) (topPanel.getLayout());
                cl.show(topPanel, "1");
            }
        };

        double toolbarSizes[][] =  {{80, 80, 80, 80, 80, 80, 80, 80, 80, 80}, {40}};
        final JPanel toolbarPanel = new JPanel(new TableLayout(toolbarSizes));
        toolbarPanel.setBorder(new LineBorder(Color.BLACK, 1));
        JButton designButton = new JButton("Design");
        designButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipDesign.refresh(true);
                CardLayout cl = (CardLayout)(topPanel.getLayout());
                cl.show(topPanel, "2");
            }
        });
        toolbarPanel.add(designButton, "0, 0");

        JButton configTechsButton = new JButton("Techs");
        configTechsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DebugTechSelectionWindow(gameLogic, player);
            }
        });
        toolbarPanel.add(configTechsButton, "1, 0");

        final AutoBattleSimWindow battleSimWindow = new AutoBattleSimWindow(gameLogic, player);
        battleSimWindow.setPanelActionListener(exitPanelAction);

        JButton testBattleButton = new JButton("Battle");
        testBattleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipDesign.refresh(true);
                CardLayout cl = (CardLayout)(topPanel.getLayout());
                cl.show(topPanel, "3");
            }
        });
        toolbarPanel.add(testBattleButton, "2, 0");

        panel.add(toolbarPanel, "0, 2");

        //make sure the JFrame is visible
        //guiFrame.pack();
        //panel.setVisible(true);

        shipDesign.setPanelActionListener(exitPanelAction);
        topPanel.add(panel, "1");
        topPanel.add(shipDesign.getPanel(), "2");


        topPanel.add(battleSimWindow, "3");
        guiFrame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - (guiFrame.getWidth() / 2),
                middle.y - (guiFrame.getHeight() / 2));
        guiFrame.setLocation(newLocation);

        guiFrame.setVisible(true);

    }
}
