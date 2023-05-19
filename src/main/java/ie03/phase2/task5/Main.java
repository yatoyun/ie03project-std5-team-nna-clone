package ie03.phase2.task5;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // input 01
        Grid grid = new Grid();
        grid.warehouseSetup();

        // input 02 and solve
        Outputs.inputSecond(grid.w, grid.h, grid.grid, grid.shelves);
    }
}