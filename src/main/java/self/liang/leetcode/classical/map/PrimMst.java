package self.liang.leetcode.classical.map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PrimMst {

    public static void main(String[] args) {

        int N = 6; //节点数量
        int M = 10; //边的数量
        HashMap<Integer, HashMap<Integer, Integer>> edgeMap = new HashMap<>(); //图
        //边数组
        int[][] graph = {
                {1, 6, 2}, {1, 1, 3}, {1, 5, 4}, {2, 5, 3},
                {2, 3, 5}, {3, 5, 4}, {3, 6, 5}, {3, 4, 6},
                {4, 2, 6}, {5, 6, 6}
        }; //边数组

        for (int i = 0; i < M; i++) { //生成无向图
            int n1 = graph[i][0]; //开始节点
            int n2 = graph[i][2]; //结束节点
            int weight = graph[i][1]; //权重

            if (edgeMap.containsKey(n1)) {
                edgeMap.get(n1).put(n2, weight);
            } else {
                HashMap<Integer, Integer> edge = new HashMap<>();
                edge.put(n2, weight);
                edgeMap.put(n1, edge);
            }

            if (edgeMap.containsKey(n2)) {
                edgeMap.get(n2).put(n1, weight);
            } else {
                HashMap<Integer, Integer> edge = new HashMap<>();
                edge.put(n1, weight);
                edgeMap.put(n2, edge);
            }
        }

        int S = 1;//开始节点

        Comparator<CostNode> cmp = new Comparator<CostNode>() {
            @Override
            public int compare(CostNode o1, CostNode o2) {
                return (o1.cost - o2.cost);
            }
        };

        PriorityQueue<CostNode> costQueue = new PriorityQueue<>(cmp);
        boolean[] visited = new boolean[N + 1];
        int[] costArray = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            costArray[i] = Integer.MAX_VALUE;
        }

        int numVisited = 0;
        costQueue.add(new CostNode(S, 0));
        costArray[S] = 0;

        while (numVisited < N) {
            CostNode costNode = costQueue.poll();
            int minNode = costNode.node;

            if (!visited[minNode]) {
                visited[minNode] = true;
                numVisited++;
                for (int neighbor : edgeMap.get(minNode).keySet()) {
                    if (!visited[neighbor] && edgeMap.get(minNode).get(neighbor) < costArray[neighbor]) {
                        costArray[neighbor] = edgeMap.get(minNode).get(neighbor);
                        costQueue.add(new CostNode(neighbor, costArray[neighbor]));
                    }
                }
            }
        }

        int totalCost = 0;
        for (int i = 1; i <= N; i++) {
            totalCost += costArray[i];
        }

    }


}

class CostNode {

    int node;
    int cost;

    public CostNode(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }
}
