package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import actors.Tile;

public class MapLoader {

    private static final String[] VALID_KEYWORDS = {"width", "height", "tile"};
    private static int tileSize = 32;//TODO: uppenbart
    public static Tile[][] readMap() {
        int width = 0;
        int height = 0;
        Tile[][] map = new Tile[0][0];
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("res/level_1.tmap"));
            String line = br.readLine();
            int count = 0;
            while (line != null) {
                String[] data = line.split(" ");
                if (count == 0) {
                    width = Integer.parseInt(data[1]);
                } else if (count == 1) {
                    height = Integer.parseInt(data[1]);
                    break;
                }
                line = br.readLine();
                count++;
            }
            map = new Tile[width][height];

            line = br.readLine();
            while(line != null) {
                System.out.println(line);
                String[] data = line.split(" ");
                String keyword= data[0];
                int x         = Integer.parseInt(data[1]);
                int y         = Integer.parseInt(data[2]);
                int type      = Integer.parseInt(data[3]);
                System.out.printf("Type: %d \n x: %d \n y:%d\n\n", type, x, y);
                map[x][y] = new Tile(tileSize, type);
                map[x][y].setX(x);
                map[x][y].setY(y);
                line = br.readLine();
            }
            //String everything = sb.toString();
        } catch(Exception e) {
            System.out.println("File read error: " + e);
        } finally {
            //br.close();//Fixa senare
        }
        return map;
    }
}
