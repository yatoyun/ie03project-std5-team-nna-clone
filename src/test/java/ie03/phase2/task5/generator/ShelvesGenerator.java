package ie03.phase2.task5.generator;

import ie03.phase2.task5.Grid;

import java.awt.*;
import java.util.*;

public class ShelvesGenerator implements IGenerator<ArrayList<Object[]>> {
    private final Grid grid;
    private final ArrayList<Object[]> shelves;
    private Set<Point> candidatePoints;
    private static final Point ENTRANCE = new Point(1, 0);
    private static Point EXIT;
    private final int n;

    public ShelvesGenerator(Grid grid, ArrayList<Object[]> shelves, int n) {
        this.grid = grid;
        this.shelves = shelves;
        this.n = n;
        EXIT = new Point(grid.w - 2, 0);
    }

    private void reset() {
        shelves.clear();
        grid.clearGrid();
    }

    public void generate(){
        shelves.clear();
        Random rand = new Random();
        // points where shelves cannot be placed
        candidatePoints = new HashSet<>();
        for (int cnt = 0; cnt < n;) {
            if (cnt == 0) {
                initializeCandidatePoints();
            }

            if (candidatePoints.isEmpty()) {
                // no more possible points
                // reset and try again
                System.out.println("reset");
                reset();
                cnt = 0;
                continue;
            }

            Point p = (Point) candidatePoints.toArray()[rand.nextInt(candidatePoints.size())];

            // decide direction
            Point dpos = new Point(p.x, p.y);
            String d = decideDirection(rand, dpos, p);

            String s = "S" + cnt;

            if (!candidatePoints.contains(dpos)) {
                // if the direction is not possible, try again
                // no need to reset
                continue;
            }

            candidatePoints.remove(p);
            candidatePoints.remove(dpos);
            shelves.add(new Object[]{p.x, p.y, s, d});
            grid.shelves.put(s, p);
            grid.setShelf(p, s, d);
            cnt++;

            if (cnt == n && !ShelvesValidator.isAllShelvesReachable(grid)) {
                // if unreachable, reset and try again
                reset();
                cnt = 0;
            }
        }
        // set entry point
        grid.shelves.put("EN", ENTRANCE);

        // set exit point
        grid.shelves.put("EX", EXIT);
    }



    private void initializeCandidatePoints(){
        candidatePoints.clear();
        // initialize set of possible points
        for (int i = 0; i < grid.w; i++) {
            for (int j = 0; j < grid.h; j++) {
                Point p = new Point(i, j);
                if (grid.isValid(p)) {
                    candidatePoints.add(p);
                }
            }
        }
        // remove points where shelves cannot be placed
        // on and in front of the entrance and exit
        candidatePoints.remove(ENTRANCE);
        candidatePoints.remove(new Point(ENTRANCE.x, ENTRANCE.y + 1));
        candidatePoints.remove(EXIT);
        candidatePoints.remove(new Point(EXIT.x, EXIT.y + 1));
    }

    private String decideDirection(Random rand, Point dpos, Point p){
        int dnum = rand.nextInt(4);
        String d = "";

        switch (dnum) {
            case 0 -> {
                d = "N";
                dpos.y = p.y + 1;
            }
            case 1 -> {
                d = "S";
                dpos.y = p.y - 1;
            }
            case 2 -> {
                d = "E";
                dpos.x = p.x + 1;
            }
            case 3 -> {
                d = "W";
                dpos.x = p.x - 1;
            }
        }
        return d;
    }
}
