package platformer;

public class EntityPlayer extends EntityLiving {
    public Inventory inventory;
    
    public EntityPlayer (World world)
    {
        super(world);
        
        inventory   = new Inventory();
        height = 20;
        width  = 10;
    }
}
