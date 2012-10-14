package platformer;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public double x, y;
    public double prevX, prevY;
    public double height, width;
    public boolean onground;
    public boolean dead;
    public World world;
    
    public Entity (World world)
    {
        dead = false;
        this.world = world;
    }
    public boolean isWet ()
    {
        List<Tile> tiles = getTouchingTiles();
        for (Tile tile : tiles) {
            if (tile == null) continue;
            if (tile.id == Tile.water.id) return true; 
        }
        return false;
    }
    public List<Tile> getTouchingTiles ()
    {
        List<Tile> tiles = new ArrayList<Tile>();
        tiles.add(world.getTile((int) Math.ceil(x / 16.0), (int) Math.ceil(y / 16.0)));
        tiles.add(world.getTile((int) Math.ceil(x / 16.0), (int) Math.ceil((y - height) / 16.0)));
        tiles.add(world.getTile((int) Math.ceil((x - width) / 16.0), (int) Math.ceil((y - height) / 16.0)));
        tiles.add(world.getTile((int) Math.ceil((x - width) / 16.0), (int) Math.ceil(y / 16.0)));
        if (height > 16.0) {
            for (int h = 16; h < height; h += 16) {
                tiles.add(world.getTile((int) Math.ceil(x / 16.0), (int) Math.ceil((y - h) / 16.0)));
                tiles.add(world.getTile((int) Math.ceil((x - width) / 16.0), (int) Math.ceil((y - h) / 16.0)));
            }
        }
        if (width > 16.0) {
            for (int w = 16; w < width; w += 16) {
                tiles.add(world.getTile((int) Math.ceil((x - w) / 16.0), (int) Math.ceil(y / 16.0)));
                tiles.add(world.getTile((int) Math.ceil((x - w) / 16.0), (int) Math.ceil((y - height) / 16.0)));
            }
        }
        return tiles;
    }
    public void move (double xadd, double yadd)
    {
        //if (isColliding()) return;
        if (xadd != 0 && yadd != 0) { move(xadd, 0); move(0, yadd); return; }
        if (Math.abs(xadd) > 1) {
            for (int xa = 0; xa < Math.abs(xadd); xa++) move(Math.signum(xadd), 0);
            move(xadd % 1, 0);
            return;
        }
        if (Math.abs(yadd) > 1) {
            for (int ya = 0; ya < Math.abs(yadd); ya++) move(0, Math.signum(yadd));
            move(0, yadd % 1);
            return;
        }
        x += xadd;
        y += yadd;
        if (!isColliding()) return;
        x -= xadd;
        y -= yadd;
        return;
    }
    public boolean isColliding ()
    {
        List<Tile> touchingTiles = getTouchingTiles();
        for (Tile tile : touchingTiles) {
            if (tile == null) continue;
            if (tile.solid)   return true;
        }
        return false;
    }
    public int getChunkX () { return world.exactToChunkCord(x); }
    public int getChunkY () { return world.exactToChunkCord(y); }
    public void tick (double delta)
    {
        prevX = x;
        prevY = y;
        
        y -= 1;
        onground = isColliding();
        y += 1;
    }
    public void spawned() {}
    public void render () {}
    public void removed () {}
    public void die ()
    {
        dead = true;
    }
}