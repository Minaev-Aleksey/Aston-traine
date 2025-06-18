package org.example.Algo;

public class AlgoDekstra {

    public static void dijkstra(int[][] graph, int startNode) {
        int count = graph.length;
        boolean[] visited = new boolean[count];
        int[] distances = new int[count];

        // Инициализация расстояний
        for (int i = 0; i < count; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[startNode] = 0;

        for (int i = 0; i < count; i++) {
            // Находим вершину с минимальным расстоянием
            int u = findMinDistance(distances, visited);
            visited[u] = true;

            // Обновляем расстояния для соседних вершин
            for (int v = 0; v < count; v++) {
                if (!visited[v] && graph[u][v] != 0 &&
                        distances[u] != Integer.MAX_VALUE &&
                        distances[u] + graph[u][v] < distances[v]) {
                    distances[v] = distances[u] + graph[u][v];
                }
            }
        }

        // Вывод результатов
        printDistances(distances, startNode);
    }

    private static int findMinDistance(int[] distances, boolean[] visited) {
        int minDistance = Integer.MAX_VALUE;
        int minNode = -1;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                minNode = i;
            }
        }

        return minNode;
    }

    private static void printDistances(int[] distances, int startNode) {
        System.out.println("Кратчайшие расстояния от вершины " + startNode + ":");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("До вершины " + i + ": " + distances[i]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = new int[][] {
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        dijkstra(graph, 0);
    }
}
