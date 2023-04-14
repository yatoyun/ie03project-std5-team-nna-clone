import java.awt.Point;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class RouteSimulation {
    static int w, h;
    static int weights[][];

    public static int solvedist(Point cpos, Point dpos) {
        // solve distance using dikstra
        int totaldist[][] = new int[w][h];
        boolean visited[][] = new boolean[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                totaldist[i][j] = Integer.MAX_VALUE;
                visited[i][j] = false;
            }
        }

        totaldist[cpos.x][cpos.y] = 0;

        Queue<Map.Entry<Integer, Point>> q = new PriorityQueue<>(Map.Entry.comparingByKey());

        q.add(new SimpleEntry<Integer, Point>(0, cpos));

        while (!q.isEmpty()) {
            Map.Entry<Integer, Point> entry = q.poll();
            cpos = entry.getValue();
            visited[cpos.x][cpos.y] = true;

            if (cpos == dpos) {
                break;
            }

            int dx[] = {1, 0, -1, 0}; // E, N, W, S
            int dy[] = {0, 1, 0, -1}; // E, N, W, S

            if (totaldist[dpos.x][dpos.y] < entry.getKey()) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                Point npos = new Point();
                npos.x = cpos.x + dx[i];
                npos.y = cpos.y + dy[i];

                // check if out of bounds
                if (npos.x < 0 || npos.x >= w || npos.y < 0 || npos.y >= h) {
                    continue;
                // check if visited
                } else if (visited[npos.x][npos.y] == true) {
                    continue;
                } else if (weights[npos.x][npos.y] == Integer.MAX_VALUE) {
                    continue;
                // main logic
                } else if ( totaldist[npos.x][npos.y] > totaldist[cpos.x][cpos.y] + weights[npos.x][npos.y] ) {
                    totaldist[npos.x][npos.y] = totaldist[cpos.x][cpos.y] + weights[npos.x][npos.y];
                    q.add(new SimpleEntry<Integer, Point>(totaldist[npos.x][npos.y], npos));
                }
            }
        }
        return totaldist[dpos.x][dpos.y];
    }

    public static void main(String[] args) {

        // input 01

        Scanner sc = new Scanner(System.in);
        w = sc.nextInt();
        h = sc.nextInt();
        int n = sc.nextInt();
        weights = new int[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if ((i == 0 && j == h-1) || (i == w-1 && j == h-1)) {
                    // upper left and lower right corner
                    weights[i][j] = Integer.MAX_VALUE;
                } else if ((i != 1 && i != w-2) && j == 0) {
                    // lower wall
                    weights[i][j] = Integer.MAX_VALUE;
                } else {
                    weights[i][j] = 1;
                }
            }
        }

        HashMap<String, Point> shelves = new HashMap<String, Point>();

        for (int i = 0; i < n; i++) {

            int x = sc.nextInt();
            int y = sc.nextInt();
            String s = sc.next();
            String d = sc.next();

            weights[x][y] = Integer.MAX_VALUE;

            Point pos = new Point();

            if (d.compareTo("N") == 0) {
                pos.x = x;
                pos.y = y + 1;
            } else if (d.compareTo("S") == 0) {
                pos.x = x;
                pos.y = y - 1;
            } else if (d.compareTo("E") == 0) {
                pos.x = x + 1;
                pos.y = y;
            } else if (d.compareTo("W") == 0) {
                pos.x = x - 1;
                pos.y = y;
            }

            shelves.put(s, pos);
        }


        // input 02 and solve

        int q = sc.nextInt();
        for (int i = 0; i < q; i++) {
            int m = sc.nextInt();
            int dist = 0;

            Point cpos = new Point(1, 0);
            Point npos = new Point();

            // solve

            for (int j = 0; j < m; j++) {
                String p = sc.next();
                npos = shelves.get(p);
                dist += solvedist(cpos, npos);
                cpos = npos;
            }

            npos = new Point(w-2, 0);
            dist += solvedist(cpos, npos);

            System.out.println(dist);
        }

    }
}