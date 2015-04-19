package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.setup.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

/**
 * Created by shaul on 4/13/15.
 */
public class ShipDesignerWindow {

    private final InfoPanel infoPanel;

    public ShipDesignerWindow(IGameLogic gameLogic, IPlayer player) {

        ShipDesigner shipDesigner = new ShipDesigner(gameLogic, player);
        shipDesigner.changeHullSize(2);

        JFrame guiFrame = new JFrame();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Ship Designer");
        guiFrame.setSize(640, 480);
        guiFrame.setBounds(100, 100, 600, 300);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        double size[][] =  {{320, 320}, {120, 140, 110, 110}};
        guiFrame.setLayout(new TableLayout(size));


        guiFrame.add(new Test(Color.BLUE), "0, 0");
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

    private JPanel createInfoPanel(ShipDesigner shipDesigner) {
        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Test"));

        return infoPanel;
    }

    private static class InfoPanel extends JPanel {
        private final ShipDesigner shipDesigner;
        private final JLabel label;

        InfoPanel(ShipDesigner shipDesigner) {
            this.shipDesigner = shipDesigner;
            this.label = new JLabel("Empty");
            add(label);
        }

        void update() {
            int spaceUsed = shipDesigner.getTotalSpace() - shipDesigner.getAvailableSpace();
            this.label.setText("Space used: " + spaceUsed + " out of " + shipDesigner.getTotalSpace());
        }
    }

    private static class Test extends JPanel {
        Test(Color c) {
            super();
            setBackground(c);
        }
    }

}
