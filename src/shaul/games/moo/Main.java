package shaul.games.moo;


import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Player.PlayerImpl;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Ship.HullType;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Star;
import shaul.games.moo.model.Utils;
import shaul.games.moo.setup.GameLogic;
import shaul.games.moo.setup.TechnologyTree;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        Star s1 = new Star("sol", "yellow", "normal", "normal", "shaul");
        System.out.println(s1);
        for (Technology tech : TechnologyTree.getTechnologies()) {
            System.out.println(tech);
        }


        IGameLogic gameLogic = new GameLogic();

        // Ship
        IPlayer currentPlayer = new PlayerImpl();


        ShipDesigner shipDesigner = new ShipDesigner(gameLogic, currentPlayer);
        List<HullType> hullTypes = shipDesigner.getHullTypes();

        if (shipDesigner.canChangeHullSize(2)) {
            shipDesigner.changeHullSize(2);
        }
        List<Utils.Availalbe<ShipModule>> computerModules = shipDesigner.getAvailableComputerModules();
        System.out.println(computerModules);



    }
}
