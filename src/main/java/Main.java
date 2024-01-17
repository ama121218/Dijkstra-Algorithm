package main.java;
import java.util.*;

public class Main {

    /*
    6
    A B 6
    A D 1
    B C 5
    B D 2
    C E 5
    D E 1
    */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        HashMap<String, HashMap<String, Integer>> all_map = new HashMap<>();
        HashSet<String> all_set = new HashSet<>();

        for (int i = 0; i < N; i++) {//データの追加
            String s = sc.next();
            String g = sc.next();
            all_set.add(s);
            all_set.add(g);
            int distance = sc.nextInt();
            HashMap<String, Integer> map1 = all_map.getOrDefault(s, new HashMap<>());
            HashMap<String, Integer> map2 = all_map.getOrDefault(g, new HashMap<>());
            map1.put(g, distance);
            map2.put(s, distance);
            all_map.put(s, map1);
            all_map.put(g, map2);
        }
        String start = "A";

        HashMap<String, Integer> distance = new HashMap<>();
        HashMap<String, String> parent_node = new HashMap<>();
        for (String s : all_set) {
            distance.put(s, Integer.MAX_VALUE);
            parent_node.put(s, null);
        }
        distance.put(start, 0);

        PriorityQueue<String> q = new PriorityQueue<>(Comparator.comparingInt(distance::get));
        HashSet<String> determine_node = new HashSet<>();

        q.add(start);
        while (!q.isEmpty()) {
            String this_node = q.poll();
            if (!determine_node.contains(this_node)) {
                if (all_map.containsKey(this_node)) {
                    HashMap<String, Integer> connection_node = all_map.get(this_node);
                    for (String neighbor_node : connection_node.keySet()) {
                        int new_distance = distance.get(this_node) + connection_node.get(neighbor_node);
                        if (new_distance < distance.get(neighbor_node)) {
                            distance.put(neighbor_node, new_distance);
                            parent_node.put(neighbor_node, this_node);
                            q.add(neighbor_node);
                        }
                    }
                    determine_node.add(this_node);
                }
            }
        }

        for (String node : all_set) {
            ArrayList<String> path = new ArrayList<>();
            for (String prev = node; prev != null; prev = parent_node.get(prev)) {
                path.add(prev);
            }
            Collections.reverse(path);

            System.out.println(node + ": " + path);
        }
        for (String node : all_set) {
            System.out.println("Aから" + node + "の距離:" + distance.get(node));
        }
    }
}