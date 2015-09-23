package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import actors.Tile;

public class MapLoader {
    private static final String[] VALID_KEYWORDS = {"width", "height", "tile"};
    private static int tileSize = 32;//TODO: uppenbart
    private static Tile[][] loadedMap;
    private static int width = 0;
    private static int height = 0;

    public static Tile[][] readMap(String path) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("res/" + path));
            setDimensions(br);
            loadedMap = new Tile[width][height];

            String line = br.readLine();
            while(line != null) {
                if (width != -1 && height != -1) {

                }
                //System.out.println(line);
                String[] data = line.split(" ");
                String keyword= data[0];
                if (keyword.equals("tile")) {
                   readTile(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                } else if (keyword.equals("width")) {
                    setWidth(Integer.parseInt(data[1]));
                } else if (keyword.equals("height")) {
                    setHeight(Integer.parseInt(data[1]));
                }
                line = br.readLine();
            }
            //String everything = sb.toString();
            br.close();//Fixa senare
        } catch(Exception e) {
            System.out.println("File read error: " + e);
        } finally {
            return loadedMap;
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    private static void setWidth(int w) {
        width = w;
    }

    private static void setHeight(int h) {
        height = h;
    }

    private static void readTile(int x, int y, int type) {
        loadedMap[x][y] = new Tile(tileSize, type);
        loadedMap[x][y].setX(x);
        loadedMap[x][y].setY(y);

    }

    private static void setDimensions(BufferedReader br) {
        try {
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

        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
