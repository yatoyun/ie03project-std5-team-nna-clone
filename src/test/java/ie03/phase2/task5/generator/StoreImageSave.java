package ie03.phase2.task5.generator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class StoreImageSave {

    private static HashMap<String, String> map = new HashMap<>() {
        {put("W", "←"); put("E", "→"); put("S", "↓"); put("N", "↑");}
    };
    public static BufferedImage create(String intputText){
        int cellWidth = 35;
        int margin = 10;

        String[] inputList = intputText.split("\n");
        int[] size = Stream.of(inputList[0].split(" ")).mapToInt(Integer::parseInt).toArray();


        // 画像のサイズを計算
        int imageWidth = cellWidth * size[0] + margin * 2;
        int imageHeight = cellWidth * size[1] + margin * 2;

        // 画像を生成
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 背景を白に設定
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imageWidth, imageHeight);

        // 文字色を黒に設定
        g.setColor(Color.BLACK);
        int x,y;

        // shelves
        for (int i = 1; i < size[2]+1; i++) {
            String[] input = inputList[i].split(" ");
            x = Integer.parseInt(input[0]) * cellWidth + margin;
            y = (size[1] - Integer.parseInt(input[1]) - 1) * cellWidth + margin;

            String text = getMark(input[2], input[3]);

            g.drawString(text, x + cellWidth / 2 - margin - 3, y + cellWidth / 2);
        }

        // X: points can't be entered
        ArrayList<int[]> everyEndPoints = new ArrayList<>(Arrays.asList(new int[]{0, size[1]-1}, new int[]{size[0]-1, size[1]-1})); // top left and right
        for (int i = 0; i<size[0];i++){
            if (i!=1 && i!=size[0]-2)
                everyEndPoints.add(new int[]{i, 0});
        }
        for (int[] endPoint : everyEndPoints){
            x = endPoint[0] * cellWidth + margin;
            y = (size[1] - endPoint[1] - 1) * cellWidth + margin;
            g.drawString("X", x + cellWidth / 2, y + cellWidth / 2);
        }

        // EN
        x = 1 * cellWidth + margin;
        y = (size[1] - 1) * cellWidth + margin;
        g.drawString("EN", x + cellWidth / 2, y + cellWidth / 2);
        //EX
        x = (size[1] - 2) * cellWidth + margin;
        y = (size[1] - 1) * cellWidth + margin;
        g.drawString("EX", x + cellWidth / 2, y + cellWidth / 2);

        // grid
        for (int i = 0; i<size[0]; i++){
            for (int j = 0; j<size[1]; j++){
                x = i * cellWidth + margin;
                y = j * cellWidth + margin;
                g.drawRect(x, y, cellWidth, cellWidth);
            }
        }
        g.dispose();

        // 画像をファイルに出力
        return image;
    }

    private static String getMark(String name, String direction){
        return name+map.get(direction);
    }
}

