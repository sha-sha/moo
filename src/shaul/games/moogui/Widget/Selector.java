package shaul.games.moogui.Widget;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Shaul on 7/12/2015.
 */
public class Selector<T extends JComponent & Element>
        extends JPanel implements MouseListener, Element.UiListener {

    private Listener listener;

    @Override
    public void onClick(Element ui) {
        doClickAction();
    }

    public interface Listener {
        void onClick();
    }

    public Selector() {
        super();
        addMouseListener(this);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void add(T ui) {
        super.add(ui);
        ui.setListener(this);
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
        System.out.println("UiSelection.doClickAction");
        if (listener != null) {
            listener.onClick();
        } else {
            System.out.print("ModuleSelection has mo listener!");
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
