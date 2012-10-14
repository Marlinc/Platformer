package platformer;

public class TileGrass extends Tile {
    public TileGrass (int id, int texture)
    {
        super(id, texture);
    }
    public void onRandomTick (World world, int x, int y)
    {
        int xa = (world.random.nextBoolean()) ? 1 : -1;
        int ya = world.random.nextInt(3) - 1;
            
        Tile tilea = world.getTile(x + xa, y + ya); 
        if (tilea != null && tilea.id == Tile.dirt.id) {
            Tile tileb = world.getTile(x + xa, y + ya + 1);
            if (tileb != null && tileb.id == Tile.air.id) {
                world.setTile(x + xa, y + ya, Tile.grass);
                return;
            }
        }   
    }
    public void onNeighbourTileChange (World world, int x, int y, int x2, int y2)
    {
        if (x == x2 && y == (y2 - 1)) world.setTile(x, y, Tile.dirt);
    }
}
