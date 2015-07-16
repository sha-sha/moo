package shaul.games.moogui.Widget;

import layout.TableLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Shaul on 7/10/2015.
 */
public class UpDown extends JPanel implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int newVal = current;
        if (e.getSource() == up) {
            newVal++;
        } else if (e.getSource() == down) {
            newVal--;
        }
        if (listener != null && listener.onChange(this, newVal)) {
            setValue(newVal);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public interface Listener {
        boolean onChange(UpDown upDown, int newValue);
    }

    private final BasicArrowButton up;
    private final BasicArrowButton down;
    private final JLabel text;
    private Listener listener;
    private int current = 0;

    public UpDown(Color back) {
        JPanel buttons = new JPanel();
        double buttonSizes[][] = {{TableLayout.FILL}, {0.5, 0.5}};
        buttons.setLayout(new TableLayout(buttonSizes));
        this.up = new BasicArrowButton(SwingConstants.NORTH);
        up.addMouseListener(this);
        buttons.add(up, "0, 0");

        down = new BasicArrowButton(SwingConstants.SOUTH);
        down.addMouseListener(this);
        buttons.add(down, "0, 1");
        buttons.setBackground(back);

        double sizes[][] = {{16, TableLayout.FILL}, {TableLayout.FILL}};
        setLayout(new TableLayout(sizes));
        setBorder(new EmptyBorder(2, 2, 2, 2));
        setBackground(back);
        add(buttons, "0, 0");
        text = new JLabel("0");
        add(text, "1, 0");
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setValue(int val) {
        this.current = val;
        text.setText("" + val);
    }

    public int getValue() {
        return this.current;
    }

}
