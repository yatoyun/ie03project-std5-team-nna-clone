package ie03.phase2.task5;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class GetMinimumRoute {
    public static int w, h;
    public static int[][] grid;

    GetMinimumRoute(int w, int h, int[][] grid) {
        GetMinimumRoute.w = w;
        GetMinimumRoute.h = h;
        GetMinimumRoute.grid = grid;

    }

    public static int[][] solveTotalDist(Point cpos, Point dpos) {
        // solve distance using dikstra
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

        q.add(new AbstractMap.SimpleEntry<>(0, cpos));

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

                if (isVisitable(npos, cpos, visited, totaldist)) {
                    totaldist[npos.x][npos.y] = totaldist[cpos.x][cpos.y] + grid[npos.x][npos.y];
                    q.add(new AbstractMap.SimpleEntry<>(totaldist[npos.x][npos.y], npos));
                }
            }
        }
        return totaldist;
    }

    public static boolean isVisitable(Point npos, Point cpos, boolean[][] visited, int[][] totaldist) {
        // check if out of bounds
        if (npos.x < 0 || npos.x >= w || npos.y < 0 || npos.y >= h) {
            return false;
        }

        // check if visited
        if (visited[npos.x][npos.y]) {
            return false;
        }

        // check if wall
        if (grid[npos.x][npos.y] == Integer.MAX_VALUE) {
            return false;
        }

        // main logic
        return (totaldist[npos.x][npos.y] > totaldist[cpos.x][cpos.y] + grid[npos.x][npos.y]);
    }

    public int solveDist(Point cpos, Point dpos) {

        int[][] totaldist = solveTotalDist(cpos, dpos);
        return totaldist[dpos.x][dpos.y];
    }
}
