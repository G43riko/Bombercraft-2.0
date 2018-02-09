package Bombercraft2.Bombercraft2.components.path;

import utils.math.GVector2f;

import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {
    private static class Node {
        String  key;
        int     value;
        int     distance = -1;
        boolean checked;

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && ((Node) obj).key.equals(key);
        }
        private Node(String key, int value) {
            this(key, value, -1);
        }
        private Node(String key, int value, int distance) {
            this.key = key;
            this.value = value;
            this.distance = distance;
        }
    }

    private final static int START   = 3;
    private final static int CIEL    = 2;
    private final static int WALL    = 1;
    private final static int NOTHING = 0;
    private final static int PATH    = 4;

    public static ArrayList<GVector2f> findPath2(HashMap<String, Integer> map,
                                                String start,
                                                String ciel,
                                                boolean diagonal
                                               ) {
        Map<String, Node> res = map.entrySet()
                                   .stream()
                                   .collect(Collectors.toMap(Map.Entry::getKey,
                                                             (a) -> new Node(a.getKey(), a.getValue())));

        PriorityQueue<Node> queue = new PriorityQueue<>(11, Comparator.comparingInt(a -> a.distance));

        final Node startNode = res.get(start);
        startNode.distance = 0;
        queue.add(startNode);


        while (!queue.isEmpty()) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (i != 0 && j != 0) {
                        continue;
                    }
                }
            }
        }

        return null;
    }

    public static ArrayList<GVector2f> findPath(HashMap<String, Integer> map,
                                                String start,
                                                String ciel,
                                                boolean diagonal
                                               ) {
        diagonal = !diagonal;
        final Map<String, Float> distances = new HashMap<>();
        final Set<String> checked = new HashSet<>();
        Set<String> act = new HashSet<>();
        final ArrayList<GVector2f> res = new ArrayList<>();
        distances.put(start, 0f);
        act.add(start);

        float d = 0;
        while (!act.isEmpty()) {
            final Set<String> newAct = new HashSet<>();
            for (String a : act) {
                final GVector2f p = new GVector2f(a);
                checked.add(a);
                distances.put(a, d);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) {
                            continue;
                        }
                        if (i != 0 && j != 0) {
                            continue;
                        }
                        final String b = p.add(new GVector2f(i, j)).toString();
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
        if (!distances.containsKey(ciel)) {
            return res;
        }

        float shortestD = distances.get(ciel);
        String shortestB = ciel;
        while (!current.equals(start)) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (diagonal && i != 0 && j != 0) {
                        continue;
                    }
                    final String b = new GVector2f(current).add(new GVector2f(i, j)).toString();
                    if (!map.containsKey(b) || map.get(b) != NOTHING) {
                        continue;
                    }
                    if (distances.get(b) < shortestD) {
                        shortestD = distances.get(b);
                        shortestB = b;
                    }
                }
            }
            current = shortestB;
            if (shortestD == 0) {
                break;
            }
            res.add(new GVector2f(current));
            map.put(current, PATH);
        }
        return res;
    }

    public static void testPathFinder(int size, String start, String ciel) {
        final HashMap<String, Integer> m = new HashMap<>();

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
