package platformer;

import java.util.ArrayList;
import java.util.List;

public class ChunkHandler {
    public World world;
    public WorldGenerator worldgenerator;
    public List<Chunk> chunks;
    public List<Chunk> loading;
    public int minChunks = 400, maxChunks = 500;
    
    public ChunkHandler (World world, WorldGenerator worldgenerator)
    {
        this.world = world;
        this.worldgenerator = worldgenerator;
        
        chunks  = new ArrayList<Chunk>();
        loading = new ArrayList<Chunk>();
    }
    public void cleanUp ()
    {
        int px = world.thePlayer.getChunkX();
        int py = world.thePlayer.getChunkY();
        
        for (int mindistance = 50; getChunkAmount() > minChunks; mindistance--) {
            for (int i = getChunkAmount() - 1; i >= 0; i--) {
                Chunk chunk = chunks.get(i);
                if ((Math.abs(chunk.x - px) > mindistance) || (Math.abs(chunk.y - py) > mindistance)) {
                    removeChunk(i);
                }
            }
        }
    }
    public void removeChunk (Chunk chunk)
    {
        chunks.remove(chunk);
    }
    public void removeChunk (int index)
    {
        chunks.remove(index);
    }
    public void removeChunk (int x, int y)
    {
        for (Chunk chunk : chunks) {
            if (chunk.x == x && chunk.y == y) chunks.remove(chunk); 
        }
    }
    public int getChunkAmount ()
    {
        return chunks.size();
    }
    public void generateChunk (int chunkX, int chunkY)
    {
        if (isChunkOnList(chunkX, chunkY)) return;
        addChunk(worldgenerator.generateChunk(chunkX, chunkY, world.chunkSize));
    }
    public void addChunk (Chunk chunk)
    {
        chunks.add(chunk);
        if (getChunkAmount() > maxChunks) cleanUp();
    }
    public boolean isChunkOnList (int chunkX, int chunkY)
    {
        for (Chunk chunk : chunks) {
            if (chunk.x == chunkX && chunk.y == chunkY) return true;
        }
        for (Chunk chunk : loading) {
            if (chunk.x == chunkX && chunk.y == chunkY) return true;
        }
        return false;
    }
    public List<Chunk> getChunks()
    {
        return new ArrayList<Chunk>(chunks);
    }
    public void generateChunksAround(int chunkX, int chunkY, int radius)
    {
        for (int x = chunkX - radius; x <= (chunkX + radius); x++) {
            for (int y = chunkY - radius; y <= (chunkY + radius); y++) {
                generateChunk(x, y);
            }
        }
    }
    public Chunk getChunk(int x, int y)
    {
        for (Chunk chunk : chunks) {
            if (chunk.x == x && chunk.y == y) return chunk;
        }
        return null;
    }
}
