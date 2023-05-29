package ie03.phase2.task5;

import java.awt.*;

public class Grid extends ie03.phase1.task3.Grid {

    public Grid(int w, int h) {
        super(w, h);
    }

    public void shelvesInitializer(int n) {
        for (int i = 0; i < n; i++) {

            int x = Input.nextInt();
            int y = Input.nextInt();
            String s = Input.next();
            String d = Input.next();

            Point p = new Point(x, y);
            setShelf(p, s, d);
        }

        // set entry point
        Point pos = new Point(1, 0);
        shelves.put("EN", pos);

        // set exit point
        pos = new Point(w-2, 0);
        shelves.put("EX", pos);
    }
}
