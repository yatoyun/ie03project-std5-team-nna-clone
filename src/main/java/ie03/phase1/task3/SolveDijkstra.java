package ie03.phase1.task3;

import java.util.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.AbstractMap.SimpleEntry;
import java.awt.*;

public class SolveDijkstra {
    Grid grid;
    int h;
    int w;
    HashMap<String, Point> shelves;

    public SolveDijkstra(Grid g) {
        this.grid = g;
        this.w = g.w;
        this.h = g.h;
        this.shelves = g.shelves;
    }

    private int[][] solveTotalDist(Point cpos, Point dpos) {
        // solve distance using dijkstra
        int[][] totaldist = new int[w][h];
        boolean[][] visited = new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                totaldist[i][j] = Integer.MAX_VALUE;
                visited[i][j] = false;
            }
        }

        totaldist[cpos.x][cpos.y] = 0;

        Queue<Map.Entry<Integer, Point>> q = new PriorityQueue<>(Map.Entry.comparingByKey());

        q.add(new SimpleEntry<>(0, cpos));

        while (!q.isEmpty()) {
            Map.Entry<Integer, Point> entry = q.poll();
            cpos = entry.getValue();
            visited[cpos.x][cpos.y] = true;

            if (cpos == dpos) {
                break;
            }

            int[] dx = {1, 0, -1, 0}; // E, N, W, S
            int[] dy = {0, 1, 0, -1}; // E, N, W, S

            if (totaldist[dpos.x][dpos.y] < entry.getKey()) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                Point npos = new Point();
                npos.x = cpos.x + dx[i];
                npos.y = cpos.y + dy[i];

                if (!grid.isValid(npos)) {
                    // check if invalid
                    continue;
                }

                if (visited[npos.x][npos.y]) {
                    // check if visited
                    continue;
                }


                if (totaldist[npos.x][npos.y] > totaldist[cpos.x][cpos.y] + grid.weight[npos.x][npos.y]) {
                    // main logic
                    // check if shorter path
                    totaldist[npos.x][npos.y] = totaldist[cpos.x][cpos.y] + grid.weight[npos.x][npos.y];
                    q.add(new SimpleEntry<>(totaldist[npos.x][npos.y], npos));
                }

            }
        }
        return totaldist;
    }

    public int solveDist(Point cpos, Point dpos) {
        int[][] td = solveTotalDist(cpos, dpos);
        return td[dpos.x][dpos.y];
    }

}
