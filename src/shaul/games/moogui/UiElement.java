package shaul.games.moogui;

import javax.swing.*;

/**
 * Created by Shaul on 5/2/2015.
 */
public interface UiElement {
    interface UiListener {
        void onClick(UiElement t);
    }
    void setListener(UiListener listener);


    public static class UiLabel extends JLabel implements UiElement {

        private UiListener listener;

        @Override
        public void setListener(UiListener listener) {
            this.listener = listener;
        }
    }
}
