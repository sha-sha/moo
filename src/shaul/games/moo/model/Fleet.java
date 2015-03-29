package shaul.games.moo.model;

import static shaul.games.moo.model.Utils.*;

import java.util.Arrays;

/**
 * Created by Shaul on 2/25/2015.
 */
public class Fleet {

    private static long nextFleetId = 1;
    public static void setNextFleetId(long id) {
        nextFleetId = id;
    }

    public static long getNextFleetId() {
        return nextFleetId++;
    }

    public static final Utils.ElementKey<Long, Fleet> ELEMENT_KEY =
            new Utils.ElementKey<Long, Fleet>() {
                @Override
                public Long getKey(Fleet fleet) { return fleet.getId(); }
            };

    public static class Order {
        public enum Type { ORBIT, GOTO};
        private Type type;
        private String starName;
        public Order(Type type, String starName) {
            this.type = type;
            this.starName = starName;
        }

        public Type getType() {
            return type;
        }

        public String getStarName() {
            return starName;
        }
    }

    public static class Ships {
        private int[] count;
        public Ships(int[] count) {
            check(count != null);
            this.count = Arrays.copyOf(count, count.length);
        }

//        int getCount(int index) {
//            return count[index];
//        }

        public int size() { return count.length; }
        public int get(int i) { return count[i]; }

        public Ships getDelta(Ships others) {
            assertNotNull(others);
            assertEquals(count.length, others.count.length);
            int[] remaining = null;
            for (int i = 0; i < count.length; i++) {
                int r = count[i] - others.count[i];
                check(r >= 0);
                if (r > 0) {
                    if (remaining == null) {
                        remaining = new int[count.length];
                    }
                    remaining[i] = r;
                }
            }
            return remaining != null ? new Ships(remaining) : null;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof Ships)) {
                return false;
            }
            Ships other = (Ships)o;
            return Arrays.equals(count, other.count);
        }

    }

    long id;
    Ships ships;
    Position position;
    Order order;

    public Fleet(Ships ships, Position position, Order order) {
        this(getNextFleetId(), ships, position, order);
    }

    public Fleet(long id, Ships ships, Position position, Order order) {
        this.id = id;
        this.ships = ships;
        this.position = position;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public Ships getShips() {
        return ships;
    }

    public Position getPosition() {
        return position;
    }

    public Order getOrder() {
        return order;
    }
}
