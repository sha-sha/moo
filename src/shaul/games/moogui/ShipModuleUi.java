package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Shaul on 4/26/2015.
 */
public class ShipModuleUi extends JPanel implements MouseListener {

    private Style style;
    private ShipModule shipModule;
    private Listener listener;
    private boolean selected;

    enum Style { NONE, SELECTED};

    public interface Listener {
        void onClick(ShipModuleUi shipModuleUi);
    }

    public ShipModuleUi(ShipModule shipModule) {
        this(shipModule, Style.NONE);
    }

    public ShipModuleUi(ShipModule shipModule, Style style) {
        this.shipModule = shipModule;
        this.style = style;
        addMouseListener(this);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.setBackground(selected ? Color.red : Color.LIGHT_GRAY);
    }

    public ShipModule getShipModule() {
        return shipModule;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
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
            listener.onClick(this);
        } else {
            System.out.println("ShipModuleUi has no listener!");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
