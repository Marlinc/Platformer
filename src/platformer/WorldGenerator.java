package platformer;

import java.util.Random;

import perlin.PerlinNoise;

public class WorldGenerator {
    public World world;
    public int   seed;
    
    public WorldGenerator (World world, int seed)
    {
        this.world = world;
        this.seed  = seed;
    }
    public WorldGenerator (World world)
    {
        this(world, new Random().nextInt());
    }
    public Tile[][] generateTiles (int cx, int cy, int chunkSize)
    {
        Tile[][] tiles = new Tile[chunkSize][chunkSize];
        if ((cy * chunkSize) > 30) {
            for (int x = 0; x < chunkSize; x++) {
                for (int y = 0; y < chunkSize; y++) {
                    tiles[x][y] = Tile.air;
                }
            }
            return tiles;
        }
        PerlinNoise perlin = new PerlinNoise(seed);
        perlin.zoom = 12;
        perlin.persistence = 0.5;
        
        double[][] map = perlin.getNoiseMap2d(
                cx * chunkSize,
                cy * chunkSize,
                chunkSize,
                chunkSize);
        for (int x = 0; x < chunkSize; x++) {
            for (int y = 0; y < chunkSize; y++) {
                if (map[x][y] >= 0.4) {
                    tiles[x][y] = Tile.dirt;
                } else {
                    tiles[x][y] = Tile.stone;
                }
            }
        }
        perlin.persistence = 0.1;
        perlin.zoom = 12;
        double[] map1d = perlin.getNoiseMap1d(chunkSize, cx * chunkSize);
        for (int x = 0; x < chunkSize; x++) {
            int height = (int) ((map1d[x] / 2 + 0.5) * 30);
            for (int ty = 0; ty < tiles[x].length; ty++) {
                int tty = cy * chunkSize + ty;
                if      (tty > height && tty < 12) tiles[x][ty] = Tile.water;
                else if (tty > height) tiles[x][ty] = Tile.air;
                else if (tty == height && height < 11) tiles[x][ty] = Tile.dirt;
                else if (tty == height) tiles[x][ty] = Tile.grass;
                else if (tty > (height - 4)) tiles[x][ty] = Tile.dirt;
            }
        }
        return tiles;
    }
    public Chunk generateChunk (int x, int y, int chunkSize)
    {
        Chunk chunk = new Chunk(x, y, world);
        chunk.generate();
        return chunk;
    }
}
