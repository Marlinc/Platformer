package platformer;

public class TileTNT extends Tile {
    public TileTNT (int id, int texture)
    {
        super(id, texture);
    }
    
    public void onInteract (World world, int x, int y)
    {
        world.addEntity(new EntityPrimedTNT(world, x, y, 4000));
        world.setTile(x, y, Tile.air);
    }
    public void onExplosion (World world, int x, int y, int sx, int sy)
    {
        double vx = 4 / ((x - sx) + 0.5);
        double vy = 5 / ((y - sy) + 0.5);
        world.addEntity(new EntityPrimedTNT(world, 
                                    x, y, 
                                    (int) (2000 + Math.random() * 1000),
                                    vx, vy));
    }
}
