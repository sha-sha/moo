package shaul.games.moo.model;

import java.util.List;

/**
 * Created by Shaul on 3/1/2015.
 */
public interface IShipComponent {
    ShipComponentCategory getCategory();
    List<ShipAttribute> getAttributes();
}
