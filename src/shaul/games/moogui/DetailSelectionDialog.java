package shaul.games.moogui;

import shaul.games.moo.model.Utils;

import javax.swing.*;
import java.util.List;

/**
 * Created by Shaul on 5/7/2015.
 */
public class DetailSelectionDialog<T> extends BasicSelectionDialog<T> {

    private final Describe<T> describe;

    public interface Describe<T> {
        List<String> getTitles();
        List<String> getValues(T t);
    }

    public DetailSelectionDialog(JComponent parent, List<Utils.Available<T>> optionalTypes, Describe<T> describe) {
        super(parent, optionalTypes);
        this.describe = describe;
    }
}
