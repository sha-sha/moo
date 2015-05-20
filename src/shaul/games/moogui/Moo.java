package shaul.games.moogui;

import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Player.PlayerImpl;
import shaul.games.moo.model.Player.PlayerStateImpl;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Ship.HullType;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.setup.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by shaul on 4/13/15.
 */
public class Moo {

    public Moo() {
        JFrame guiFrame = new JFrame();

        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI"); guiFrame.setSize(300,250);

        //This will center the JFrame in the middle of the screen
        guiFrame.setLocationRelativeTo(null);

        //Options for the JComboBox
        String[] fruitOptions = {"Apple", "Apricot", "Banana" ,"Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"};

        //Options for the JList
        String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage" , "Carrot", "Celery", "Cucumber", "Leek",
                "Mushroom" , "Pepper", "Radish", "Shallot", "Spinach", "Swede" , "Turnip"};
        //The first JPanel contains a JLabel and JCombobox
        final JPanel comboPanel = new JPanel();
        JLabel comboLbl = new JLabel("Fruits:");
        JComboBox fruits = new JComboBox(fruitOptions);

        comboPanel.add(comboLbl);
        comboPanel.add(fruits);

        // Create the second JPanel. Add a JLabel and JList and
        // make use the JPanel is not visible.
        final JPanel listPanel = new JPanel();
        listPanel.setVisible(false);
        JLabel listLbl = new JLabel("Vegetables:");
        JList vegs = new JList(vegOptions);
        vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        listPanel.add(listLbl); listPanel.add(vegs);

        JButton vegFruitBut = new JButton( "Fruit or Veg");
        // The ActionListener class is used to handle the
        // event that happens when the user clicks the button.
        // As there is not a lot that needs to happen we can
        // define an anonymous inner class to make the code simpler.
        vegFruitBut.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent event) {
                // When the fruit of veg button is pressed
                // the setVisible value of the listPanel and
                // comboPanel is switched from true to
                // value or vice versa.
                listPanel.setVisible(!listPanel.isVisible());
                comboPanel.setVisible(!comboPanel.isVisible());
            }
        });

        // The JFrame uses the BorderLayout layout manager.
        // Put the two JPanels and JButton in different areas.
        guiFrame.add(comboPanel, BorderLayout.NORTH);
        guiFrame.add(listPanel, BorderLayout.CENTER);
        guiFrame.add(vegFruitBut,BorderLayout.SOUTH);

        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }

    public static void main(String[] args) {
        IGameLogic gameLogic = new GameLogic();

        // Ship
        IPlayer currentPlayer = new PlayerImpl(gameLogic, "shaul");
        currentPlayer.setPlayerState(new PlayerStateImpl(
                null, new TechnologiesDb(gameLogic.getTechnologyLogic(), Arrays.asList(
                "Battle Computer Mark 1", "Battle Computer Mark 2", "Battle Computer Mark 3",
                "Battle Scanner", "Titanium Armor", "Laser", "Class I Deflector Shield", "Class II Deflector Shield",
                "ECM Jammer Mark 1", "Nuclear Engine"))));

        new ShipDesignerWindow(gameLogic, currentPlayer);
    }
}
