package shaul.games.moogui;

import shaul.games.moo.model.Ship.ShipModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Shaul on 4/26/2015.
 */
public class GenericUi<DATA> extends JPanel implements MouseListener, UiElement {

    private Style style;
    private DATA data;
    private UiListener listener;
    private boolean selected;

    enum Style { NONE, SELECTED};

    public GenericUi(DATA data) {
        this(data, Style.NONE);
    }

    public GenericUi(DATA data, Style style) {
        this.data = data;
        this.style = style;
        addMouseListener(this);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.setBackground(selected ? Color.red : Color.LIGHT_GRAY);
    }

    public DATA getData() {
        return data;
    }

    @Override
    public void setListener(UiListener listener) {
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
            System.out.println("GenericUi " + this.getClass().toString() + " has no listener!");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
