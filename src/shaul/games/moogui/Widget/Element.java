package shaul.games.moogui.Widget;

import javax.swing.*;

/**
 * Created by Shaul on 7/12/2015.
 */
public interface Element {
    public interface UiListener {
        void onClick(Element t);
    }
    void setListener(UiListener listener);


    public static class Label extends JLabel implements Element {

        private UiListener listener;

        @Override
        public void setListener(UiListener listener) {
            this.listener = listener;
        }
    }
}
