package gameplay;

import gameplay.al_movement.RandomShortestPathAl;
import gameplay.al_movement.ShortestPathAl;
import gameplay.al_movement.SmartShortestPathAl;
import gameplay.builder.*;
import gameplay.builder.object.*;
import gameplay.builder.ghosts.*;
import engine.al.BFS;
import engine.core_kernel.builder.*;
import engine.al.MapRep;
import engine.core_kernel.Entity;
import engine.core_kernel.Map;
import engine.phy.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LevelGenerator {
    public static ArrayList<Entity> ghosts = new ArrayList<>();
    public final static ArrayList<Entity> CopyGhosts = new ArrayList<>();
    public static ArrayList<Entity> coins = new ArrayList<>();
    public static ArrayList<Entity> copyCoins = new ArrayList<>();
    private Entity pacman;
    private Entity ghost;
    private List<Entity>[][] matrix;
    private final double w;
    private final double l;
    private double width;
    private double length;
    private final Map map;
    private final ShortestPathAl shortestPathAl;
    private final RandomShortestPathAl randomShortestPathAl;
    private final SmartShortestPathAl smartShortestPathAl;
    private final HashMap<Entity, Position> initPositionEntities;
    private int counter = 0;
    private MapRep mapRep;

    /**
     * Class used to generate a level
     * @param length Level length
     * @param width Level width
     * @param path Path of the level file
     */
    public LevelGenerator(double length, double width, String path) {
        this.w = length;
        this.l = width;
        initPositionEntities = new HashMap<>();
        BFS bfs = new BFS(Arrays.asList(EntityType.Blue.name, EntityType.GIFT.name, EntityType.GHOST.name, EntityType.RED.name));
        shortestPathAl = new ShortestPathAl(new BFS((List<String>) this.mapRep));
        randomShortestPathAl = new RandomShortestPathAl();
        smartShortestPathAl = new SmartShortestPathAl();
        shortestPathAl.setBfs(bfs);
        randomShortestPathAl.setBfs(bfs);
        smartShortestPathAl.setPathFinder(bfs);
        map = new Map(new Position(0,0), new Position(length , width ));

        readFile(path);

        map.setLimitBottomRight(new Position(this.width * matrix[0].length, this.length * matrix.length));
        map.setMatrix(matrix);
        MapRep mapRep = new MapRep(map);
        bfs.setMap(mapRep);
    }


    /**
     * Read the level file and initialize the game map
     * @param path Level file path
     */
    public void readFile(String path){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(LevelGenerator.class.getResourceAsStream(path)));

            String line;
            line = in.readLine();
            String[] arr = line.split(";");
            int nX = Integer.parseInt(arr[0]);
            int nY = Integer.parseInt(arr[1]);
            width = Math.floor(w / nX);
            length = Math.floor(l / nY);

            int i = 0;
            matrix = new ArrayList[nY][nX];
            while ((line = in.readLine()) != null) {
                arr = line.split("");
                putEntity(arr, i);
                ++i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Browse the game level table and create the corresponding entity by initializing the game map
     * @param tab Table row
     * @param i Table row number
     */
    private void putEntity(String[] tab, int i) {
        int j = 0;
        double posY = (length * i);

        Director director = new Director(width, length);
        EntityBuilder builder;
        for (String str : tab){
            double posX = (width * j);
            matrix[i][j] = new ArrayList<>();
            switch (str) {
                case "#" :
                    builder = new WallBuilder();
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    break;
                case "p" :
                    builder = new PacmanBuilder(map);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    pacman = builder.getEntity();
                    initPositionEntities.put(pacman, new Position(j,i));

                    shortestPathAl.setTarget(pacman);
                    randomShortestPathAl.setTarget(pacman);
                    smartShortestPathAl.setTarget(pacman);
                    break;
                case "r" :
                    builder = new RedGhostBuilder(shortestPathAl);
                    director.constructEntity(builder, new Position(posX,posY ));
                    Entity e = builder.getEntity();
                    setMatrix(i,j, e);
                    initPositionEntities.put(e, new Position(j,i));

                    shortestPathAl.setOrigin(e);
                    ghosts.add(e);
                    CopyGhosts.add(new Entity(e));

                    break;
                case "g" :
                    builder = new GreenGhostBuilder(map);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    ghost = builder.getEntity();
                    initPositionEntities.put(ghost, new Position(j,i));
                    ghosts.add(ghost);
                    CopyGhosts.add(new Entity(ghost));
                    break;
                case "y" :
                    builder = new YellowGhostBuilder(smartShortestPathAl);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    initPositionEntities.put(builder.getEntity(), new Position(j,i));
                    smartShortestPathAl.setOrigin(builder.getEntity());
                    ghosts.add(builder.getEntity());
                    CopyGhosts.add(new Entity(builder.getEntity()));

                    break;
                case "b" :
                    builder = new BlueGhostBuilder(randomShortestPathAl);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    initPositionEntities.put(builder.getEntity(), new Position(j,i));
                    randomShortestPathAl.setOrigin(builder.getEntity());
                    ghosts.add(builder.getEntity());
                    CopyGhosts.add(new Entity(builder.getEntity()));

                    break;
                case "c" :
                    builder = new GiftBuilder();
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    break;
                case "." :
                    builder = new CoinBuilder();
                    director.constructEntity(builder, new Position(posX ,posY));
                    Random random = new Random();
                    if (i == random.nextInt(18) && counter < 2) {
                        counter ++;
                        builder.getEntity().getGraphicsComponent().setImage("/Image/object/cerise.png");
                        builder.getEntity().getGraphicsComponent().setHeight(length);
                        builder.getEntity().getGraphicsComponent().setWidth(width);
                        coins.add(builder.getEntity());
                        copyCoins.add(new Entity(builder.getEntity()));

                    }

                    setMatrix(i,j, builder.getEntity());
                    break;
                case "s" :
                    builder = new SpecialCoinBuilder();
                    director.constructEntity(builder, new Position(posX ,posY));
                    setMatrix(i,j, builder.getEntity());
                    break;
                case "v" :
                    builder = new VerticalGhostBuilder(map);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    ghost = builder.getEntity();
                    initPositionEntities.put(ghost, new Position(j,i));
                    ghosts.add(ghost);
                    CopyGhosts.add(new Entity(ghost));

                    break;
                case "h" :
                    builder = new HorizontalGhostBuilder(map);
                    director.constructEntity(builder, new Position(posX,posY));
                    setMatrix(i,j, builder.getEntity());
                    ghost = builder.getEntity();
                    initPositionEntities.put(ghost, new Position(j,i));
                    ghosts.add(ghost);
                    CopyGhosts.add(new Entity(ghost));
                    break;
                case " " :
                    break;
            }
            ++j;
        }
    }


    public void setMatrix(int i, int j, Entity entity) { this.matrix[i][j].add(entity); }

    public Map getMap(){ return map; }

    public Entity getPacman() {
        return pacman;
    }

    public Entity getGhost(){
        return ghost;
    }

    public HashMap<Entity, Position> getInitPositionEntities(){
        return initPositionEntities;
    }

    public List<Entity>[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Entity>[][] matrix) {
        this.matrix = matrix;
    }

    public MapRep getMapRepresentation() {
        return mapRep;
    }

    public void setMapRepresentation(MapRep mapRep) {
        this.mapRep = mapRep;
    }
}
