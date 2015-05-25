package shaul.games.moo;


import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Player.PlayerImpl;
import shaul.games.moo.model.Player.PlayerStateImpl;
import shaul.games.moo.model.Research.TechModule;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Star;
import shaul.games.moo.setup.GameLogic;
import shaul.games.moo.setup.TechModules;
import shaul.games.moo.setup.TechnologyTree;

import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        Star s1 = new Star("sol", "yellow", "normal", "normal", "shaul");
        System.out.println(s1);

        System.out.println("Modules: ");
        for (String module: TechModules.TECH_NAME_MAP.keySet()) {
            System.out.println(" " + module);
        }

        System.out.println("Techs:");
        for (Technology tech : TechnologyTree.getTechnologies()) {
            System.out.println(" " + tech);
            if (tech == null) continue;
            for (String moduleName : tech.getModules()) {
                TechModule techModule = TechModules.getModule(moduleName);
                System.out.println("    " + techModule);
            }
        }

        IGameLogic gameLogic = new GameLogic();

        // Ship
        IPlayer currentPlayer = new PlayerImpl(gameLogic, "shaul");
        currentPlayer.setPlayerState(new PlayerStateImpl(
                null, new TechnologiesDb(gameLogic.getTechnologyLogic(), Arrays.asList(
                "Battle Computer Mark 1", "Battle Scanner", "Titanium", "Laser"))));

    }
}
