package Bombercraft2.Bombercraft2.components.path;

import utils.math.GVector2f;

import java.util.*;

public class PathFinder {
    private final static int START   = 3;
    private final static int CIEL    = 2;
    private final static int WALL    = 1;
    private final static int NOTHING = 0;
    private final static int PATH    = 4;

    public static ArrayList<GVector2f> findPath(HashMap<String, Integer> map,
                                                String start,
                                                String ciel,
                                                boolean diagonal
                                               ) {
        diagonal = !diagonal;
        Map<String, Float> dists = new HashMap<>();
        Set<String> checked = new HashSet<>();
        Set<String> act = new HashSet<>();
        ArrayList<GVector2f> res = new ArrayList<>();
        dists.put(start, 0f);
        act.add(start);

        float d = 0;
        while (!act.isEmpty()) {
            Set<String> newAct = new HashSet<>();
            for (String a : act) {
                GVector2f p = new GVector2f(a);
                checked.add(a);
                dists.put(a, d);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        if (i != 0 && j != 0) {
                            continue;
                        }
                        String b = p.add(new GVector2f(i, j)).toString();
                        if (!map.containsKey(b) || map.get(b) != NOTHING) {
                            continue;
                        }
                        if (!act.contains(b) && !checked.contains(b)) {
                            newAct.add(b);
                        }
                    }
                }


            }
            d++;
            act = new HashSet<>(newAct);
        }

        String current = ciel;
        if (!dists.containsKey(ciel)) { return res; }

        float shortestD = dists.get(ciel);
        String shortestB = ciel;
        while (current.equals(start)) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (diagonal && i != 0 && j != 0) {
                        continue;
                    }
                    String b = new GVector2f(current).add(new GVector2f(i, j)).toString();
                    if (!map.containsKey(b) || map.get(b) != NOTHING) {
                        continue;
                    }
                    if (dists.get(b) < shortestD) {
                        shortestD = dists.get(b);
                        shortestB = b;
                    }
                }
            }
            current = shortestB;
            if (shortestD == 0) { break; }
            res.add(new GVector2f(current));
            map.put(current, PATH);
        }
        return res;
    }

    public static void testPathFinder(int size, String start, String ciel) {
        HashMap<String, Integer> m = new HashMap<>();

        for (float i = 0; i < size; i++) {
            for (float j = 0; j < size; j++) {
                m.put(new GVector2f(i + "_" + j).toString(), NOTHING);
            }
        }

        m.put(new GVector2f(0 + "_" + 1).toString(), WALL);
        m.put(new GVector2f(1 + "_" + 1).toString(), WALL);
        m.put(new GVector2f(2 + "_" + 1).toString(), WALL);
        m.put(new GVector2f(3 + "_" + 1).toString(), WALL);

        m.put(new GVector2f(1 + "_" + 3).toString(), WALL);
        m.put(new GVector2f(2 + "_" + 3).toString(), WALL);
        m.put(new GVector2f(3 + "_" + 3).toString(), WALL);
        m.put(new GVector2f(4 + "_" + 3).toString(), WALL);
        System.out.println(findPath(m, new GVector2f(start).toString(), new GVector2f(ciel).toString(), true));
    }
}
