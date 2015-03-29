package shaul.games.moo.model.Research;

import shaul.games.moo.model.Ship.HullType;

import java.util.List;

/**
 * Created by Shaul on 3/17/2015.
 */
public interface ITechnologyLogic {

    float getCostReduction();

    List<HullType> getAvailableHullTypes();

    int getHullTotalSpace(int hullSize);

    Technology getTechnology(String name);

}
