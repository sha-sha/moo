package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Ship.ShipDesigner;

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
        guiFrame.setSize(640, 480);
        guiFrame.setBounds(100, 100, 600, 300);


        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //double size[][] =  {{640}, {100, 340, 40}};
        final JPanel topPanel = new JPanel(new CardLayout());
        guiFrame.add(topPanel);
        //guiFrame.setLayout(new TableLayout(size));
        //guiFrame.setLayout(new CardLayout());
        //LayoutManager p = guiFrame.getLayout();



        double size[][] =  {{640}, {100, 340, 40}};
        JPanel panel = new JPanel(new TableLayout(size));
        //guiFrame.setLayout(new TableLayout(size));

        final ShipDesignerWindow shipDesign = new ShipDesignerWindow(gameLogic, player);

        double toolbarSizes[][] =  {{80, 80, 80, 80, 80, 80, 80, 80}, {40}};
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

        panel.add(toolbarPanel, "0, 2");

        //make sure the JFrame is visible
        //guiFrame.pack();
        //panel.setVisible(true);

        shipDesign.setPanelActionListener(new PanelAction() {
            @Override
            public void onExit() {
                CardLayout cl = (CardLayout)(topPanel.getLayout());
                cl.show(topPanel, "1");
            }
        });

        topPanel.add(panel, "1");
        topPanel.add(shipDesign.getPanel(), "2");
        guiFrame.pack();
        guiFrame.setVisible(true);

    }
}
