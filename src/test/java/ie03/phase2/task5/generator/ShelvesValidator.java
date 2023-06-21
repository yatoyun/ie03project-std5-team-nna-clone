package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;

import java.awt.*;
import java.util.*;

public class ShelvesValidator {
    public static boolean isAllShelvesReachable(Grid grid) {
        Set<Point> dposSet = grid.getShelfPoints();
        Set<Point> visited = new HashSet<>();
        // use queue
        Queue<ArrayList<Point>> q = new LinkedList<>();
        // add start point as initialisation
        q.add(new ArrayList<>(Collections.singletonList(new Point(1, 0))));
        while (!q.isEmpty()) {
            ArrayList<Point> path = q.poll();
            Point lastPoint = path.get(path.size()-1);

            if (lastPoint.equals(new Point(grid.w-2, 0)) || path.size() > grid.w*grid.h) {
                continue;
            }

            dposSet.remove(lastPoint);

            int[] dx = {1, 0, -1, 0}; // E, N, W, S
            int[] dy = {0, 1, 0, -1}; // E, N, W, S
            for (int i = 0; i < 4; i++) {
                Point npos = new Point(lastPoint.x + dx[i], lastPoint.y + dy[i]);
                if ((!grid.isValid(npos)) || path.contains(npos) || visited.contains(npos)) {
                    continue;
                }
                ArrayList<Point> newPath = new ArrayList<>(path);
                newPath.add(npos);
                visited.add(npos);
                q.add(new ArrayList<>(newPath));
            }
        }
        return dposSet.isEmpty();
    }
}
