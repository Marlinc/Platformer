package platformer;

public class EntityParticle extends Entity {
    public int life = 1000;
    
    public EntityParticle (World world)
    {
        super(world);
    }
    public void tick (double delta)
    {
        super.tick(delta);
        
        life -= delta;
        if (life < 0) die();
    }
}
