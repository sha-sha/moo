package shaul.games.moogui;

import layout.TableLayout;
import shaul.games.moo.model.GameRunTimeError;
import shaul.games.moo.model.Player.IPlayerState;
import shaul.games.moo.model.Ship.Hull;
import shaul.games.moo.model.Ship.ShipDesign;
import shaul.games.moo.model.Ship.ShipDesigner;
import shaul.games.moo.model.Ship.ShipModule;
import shaul.games.moo.model.Utils;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shaul on 4/26/2015.
 */
public class UiFactory {

    public static class Settings {
        public enum Field {
            Name("Name"), Cost("Cost"), Size("Size"), Power("Power"), Space("Space"), NumberOfEngine("Reactors"),
            CombatSpeed("+Speed");

            private final String name;

            Field(String name) {
                this.name = name;
            }

            @Override public String toString() { return name; }
        };

        public final Field[] columns;
        public final double[] columnsWidth;

        public Settings(Field[] columns, double[] columnsWidth) {
            this.columns = columns;
            this.columnsWidth = columnsWidth;
        }

        public List<String> getValues(
                final ShipModule shipModule, final IPlayerState playerState, final ShipDesigner shipDesigner) {
            return Utils.convert(Arrays.asList(columns), new Utils.Function<Field, String>() {
                @Override
                public String apply(Field field) {
                    return getValue(shipModule, playerState, shipDesigner, field);
                }
            });
        }

        public static String getValue(
                ShipModule shipModule, IPlayerState playerState,  ShipDesigner shipDesigner, Field field) {
            switch (field) {
                case Name: return shipModule.getName();
                case Cost: return String.valueOf(shipModule.getCost(playerState, shipDesigner.getHull(),
                        (ShipModule.EngineShipModule) shipDesigner.getCurrentModule(ShipDesign.SlotType.Engine)));
                case Size: return String.valueOf(shipModule.getSize(playerState, shipDesigner.getHull()));
                case Power: return String.valueOf(shipModule.getModuleData().getPower(shipDesigner.getHull()));
                case Space: return String.valueOf(shipModule.getRequiredSpace(playerState, shipDesigner.getHull(),
                        shipDesigner.getCurrentModule(ShipDesign.SlotType.Engine)));
                case CombatSpeed: return String.valueOf(shipModule.getModuleData().getCombatSpeed());
                case NumberOfEngine: return String.valueOf(shipModule instanceof ShipModule.EngineShipModule ?
                        ((ShipModule.EngineShipModule) shipModule).getNumberOfEngine(shipDesigner.getRequiredPower()) :
                        0);
                default:
                    throw new GameRunTimeError("UiFactory.Settings doesn't support " + field);
            }
        }
    }

    public static<T> GenericUi<T> create(T data) {
        return create(data, null, null, null);
    }

    public static JPanel createTitles(Settings settings) {
        JPanel panel = new JPanel();
        double sizes[][] =  {settings.columnsWidth, {TableLayout.FILL}};
        panel.setLayout(new TableLayout(sizes));
        for (int i = 0; i < settings.columnsWidth.length; i++) {
            panel.add(new JLabel(settings.columns[i].toString()), String.valueOf(i) + ", 0");
        }
        return panel;
    }

    public static<T> GenericUi<T> create(
            T data, Settings settings, IPlayerState playerState, ShipDesigner shipDesigner) {
        if (data instanceof ShipModule) {
            return (GenericUi<T>) createShipModuleUi((ShipModule) data, settings, playerState, shipDesigner);
        } else if (data instanceof String) {
            return (GenericUi<T>) new StringStub((String) data);
        } else if (data instanceof Hull) {
            return (GenericUi<T>) new HullUi((Hull) data);
        } else {
            throw new GameRunTimeError("Failed to create UI for : " + data);
        }
    }

    private static GenericUi<ShipModule> createShipModuleUi(
            ShipModule data, Settings settings, IPlayerState playerState, ShipDesigner shipDesigner) {
        switch (data.getShipComponentType()) {
            default:
                return settings == null ? new OldStub(data) : new Stub(data, settings, playerState, shipDesigner);
        }
    }

    private static class OldStub extends GenericUi<ShipModule> {

        private final JLabel label;

        public OldStub(ShipModule shipModule) {
            super(shipModule);
            this.label = new JLabel(shipModule.getName());
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

    private static class Stub extends GenericUi<ShipModule> {

        //private List<JLabel>

        public Stub(ShipModule shipModule, Settings settings, IPlayerState playerState, ShipDesigner shipDesigner) {
            super(shipModule);
            double sizes[][] =  {settings.columnsWidth, {TableLayout.FILL}};
            setLayout(new TableLayout(sizes));
            List<String> values = settings.getValues(shipModule, playerState, shipDesigner);
            for (int i = 0; i < settings.columnsWidth.length; i++) {
                add(new JLabel(values.get(i)), String.valueOf(i) + ", 0");
            }
        }

        public void setEnabled(boolean enabled) {
            for (int i=0; i<getComponentCount(); i++) {
                ((JLabel) getComponents()[i]).setEnabled(enabled);
            }
            //this.setEnabled(enabled);
        }

    }

    private static class ShipModuleWeaponUi extends GenericUi<ShipModule> {

        private final JLabel label;

        public ShipModuleWeaponUi(ShipModule shipModule) {
            super(shipModule);

            String text = shipModule.getName() + " DMG: " + shipModule.getModuleData().getWeapon().getMaxDamage();
            if (shipModule.isEmpty()) {
                text = shipModule.getName();
            }

            this.label = new JLabel(text);
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }


    private static class StringStub extends GenericUi<String> {

        private final JLabel label;

        public StringStub(String data) {
            super(data);
            this.label = new JLabel(data);
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

    private static class HullUi extends GenericUi<Hull> {

        private final JLabel label;

        public HullUi(Hull data) {
            super(data);
            this.label = new JLabel(data.toString());
            add(label);
        }

        public void setEnabled(boolean enabled) {
            this.label.setEnabled(enabled);
        }

    }

}
