package shaul.games.moo.model;

import java.util.*;
import java.util.function.Predicate;

/**
 * Created by Shaul on 2/25/2015.
 */
public class Utils {
    private static final String ROMAN_NUMBERS[] = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XV", "XVI"};

    public static interface ElementKey<K, V> {
        K getKey(V v);
    }

    public static interface Function<A, B> {
        B apply(A a);
    }

    public static interface Int2Int {
        int apply(int a);
    }

    public static<T> Set<T> setOf(T... t) {
        return new HashSet<>(Arrays.asList(t));
    }

    public static<K> List<K> filter(Collection<K> list, Predicate<K> predicate) {
        List<K> newList = new ArrayList<>();
        for (K k : list) {
            if (predicate.test(k)) {
                newList.add(k);
            }
        }
        return newList;
    }

    public static<K,V> List<K> convert(Collection<V> list, Function<V, K> converter) {
        List<K> newList = new ArrayList<>();
        for (V v : list) {
            newList.add(converter.apply(v));
        }
        return newList;
    }

    public static<K, V> Map<K, V> map(List<V> list, Function<V, K> elementKey) {
        Map<K, V> map = new HashMap<>();
        for (V v : list) {
            map.put(elementKey.apply(v), v);
        }
        return map;
    }

    public static<K, V> Map<K, List<V>> collect(List<V> list, Function<V, K> elementKey) {
        Map<K, List<V>> map = new HashMap<>();
        for (V v : list) {
            K key = elementKey.apply(v);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<V>());
            }
            map.get(key).add(v);
        }
        return map;
    }


    public static String toRomanNumber(int i) {
        if (i <= 0 || i > ROMAN_NUMBERS.length) {
            throw new GameRunTimeError("Cannot convert " + i + " to roman number");
        }
        return ROMAN_NUMBERS[i - 1];
    }

    public static<K, V, W extends V> Map<K, List<W>> collectByClass(
            List<V> list, Function<V, K> elementKey) {
        Map<K, List<W>> map = new HashMap<>();
        for (V v : list) {
            K key = elementKey.apply(v);
            if (key != null) {
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<W>());
                }
                map.get(key).add((W) v);
            }
        }
        return map;
    }


    public static void check(boolean exp) {
        failIf(null, !exp);
    }

    public static void check(boolean exp, String message) {
        failIf(message, !exp);
    }

    public static<T> T checkNotNull(T object) {
        check(object != null);
        return object;
    }

    public static<T> T checkNotNull(T object, String errorStr) {
        check(object != null, errorStr);
        return object;
    }

    public static void check(String str, boolean exp) {
        failIf(str, !exp);
    }

    public static<V> void assertEquals(V x, V y) {
        failIf(null, (x == null && y != null) || !x.equals(y));
    }

    public static<V> void assertEquals(String str, V x, V y) {
        failIf(str, (x == null && y != null) || !x.equals(y));
    }

    public static<V> void assertNotNull(String str, V x) {
        failIf(str, (x == null));
    }

    public static<V> void assertNotNull(V x) {
        failIf("Variable is null", (x == null));
    }

    public static void failIf(String str, boolean exp) {
        if (exp) {
            throw new GameRunTimeError(str != null ? str : "Assertion failed");
        }
    }

    public static void fail(String str) {
        throw new GameRunTimeError(str != null ? str : "Assertion failed");
    }

    public static class Countable<T> {

        public static<T> Countable<T> of(T t) {
            return new Countable<>(t, 1);
        }

        public static<T> Countable<T> of(T t, int count) {
            return new Countable<>(t, count);
        }

        private final T t;
        private final int count;

        public Countable(T t, int count) {
            this.t = t;
            this.count = count;
        }

        public T get() { return t; }
        public int getCount() { return count; }

        @Override
        public String toString() {
            return " " + getCount() + ":" + get().toString();
        }

    }

    public static class Available<T> extends Countable<T>{

        public Available(T t, boolean available) {
            super(t, available ? 1 : 0);
        }

        public static<T> Available<T> of(T t) { return new Available<T>(t, true); }
        public static<T> Available<T> no(T t) { return new Available<T>(t, false); }

        public boolean isAvailable() {
            return getCount() > 0;
        }

        @Override
        public String toString() {
            return (isAvailable() ? "Available " : "Not Available ") + get().toString();
        }
    }

    public static class LimitedCountable<T> {

        private final T t;
        private final int count;
        private final int limit;

        public LimitedCountable(T t, int count, int limit) {
            this.t = t;
            this.count = count;
            this.limit = limit;
        }

        public T get() { return t; }
        public int getCount() { return count; }
        public int getLimit() { return limit; }
    }
}
