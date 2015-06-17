package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.IGameLogic;
import shaul.games.moo.model.Player.IPlayer;
import shaul.games.moo.model.Player.PlayerStateImpl;
import shaul.games.moo.model.Research.TechnologiesDb;
import shaul.games.moo.model.Research.Technology;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Shaul on 6/14/2015.
 */
public class DebugTechSelectionWindow {

    private final IPlayer player;
    private final JDialog guiFrame;

    public DebugTechSelectionWindow(final IGameLogic gameLogic, final IPlayer player) {

        List<JCheckBox> allChecks = new ArrayList<>();

        this.player = player;
        guiFrame = new JDialog();
        guiFrame.setTitle("Select techs");
        guiFrame.setModal(true);
        //guiFrame.setSize(840, 480);
        //guiFrame.setBounds(100, 100, 600, 300);

        //This will center the JFrame in the middle of the screen
        //guiFrame.setLocationRelativeTo(null);

        double size[][] = {{140, 140, 140, 140, 140, 140}, {480}};
        //guiFrame.setLayout(new TableLayout(size));
        guiFrame.setLayout(new GridLayout(1, 6, 5, 5));

        for (int i = 0; i < 6; i++) {
            Technology.Category category = Technology.Category.values()[i];
            JPanel techCat = new JPanel(new GridLayout(35, 1));
            for (Technology t : gameLogic.getTechnologyLogic().getTechnologies(category)) {
                JCheckBox check = new JCheckBox(t.getTechLevel() + ": " + t.getName());
                check.setName(t.getName());
                check.setFont(check.getFont().deriveFont(9.0f));
                for (Technology ct : player.getPlayerState().getTechnologies().getTechnologies()) {
                    if (ct == t) {
                        check.setSelected(true);
                        break;
                    }
                }
                techCat.add(check);
                allChecks.add(check);
            }
            guiFrame.add(techCat);//, String.valueOf(i) + ", 0");
        }

        //make sure the JFrame is visible
        guiFrame.pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        Point newLocation = new Point(middle.x - (guiFrame.getWidth() / 2),
                middle.y - (guiFrame.getHeight() / 2));
        guiFrame.setLocation(newLocation);
        guiFrame.setVisible(true);

        List<String> techs = Utils.convert(Utils.filter(allChecks, new Predicate<JCheckBox>() {
                    @Override
                    public boolean test(JCheckBox jCheckBox) {
                        return jCheckBox.isSelected();
                    }
                }), new Utils.Function<JCheckBox, String>() {
                    @Override
                    public String apply(JCheckBox jCheckBox) {
                        return jCheckBox.getName();
                    }
                });
        player.setPlayerState(new PlayerStateImpl(
                null, new TechnologiesDb(gameLogic.getTechnologyLogic(), techs)));
        //gameLogic.
    }
}
