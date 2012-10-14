package platformer;

import static org.lwjgl.opengl.GL11.*;

public class EntityParticleBlock extends EntityParticle {
    public Tile tile;
    public double xspeed, yspeed;
    
    public EntityParticleBlock (World world, Tile tile, int x, int y)
    {
        super(world);
        
        this.tile = tile;
        this.life = (int) (750 + Math.random() * 500);
        
        this.x = x * 16.0 + Math.random() * 16.0 - 16;
        this.y = y * 16.0 + Math.random() * 16.0 - 16;
        xspeed = (Math.random() - 0.5) * 10;
        yspeed = Math.random() * 3;
        
        width  = (int) (Math.random() * 5 + 1.0);
        height = (int) (Math.random() * 5 + 1.0);
    }
    public void tick (double delta)
    {
        super.tick(delta);
        
        xspeed *= Math.pow(0.99, delta);
        yspeed -= 0.02 * delta;
        move(xspeed, yspeed);
    }
    public void render ()
    {
        int x = (int) Math.floor(this.x);
        int y = (int) Math.floor(this.y);
        
        double tx0, ty0, tx1, ty1;
        tx0 = ty0 = tx1 = ty1 = 0.0;
        int txtr = tile.getTileTexture();
        if (txtr < 0) return;
        if (txtr == 0) tx1 = ty1 = 1.0 / 16.0;
        else {
            int size = Sheetholder.tilestxtr.getImageWidth() / 16;
            tx0 = (int) (txtr % size) * (1.0 / size);
            ty0 = (int) Math.floor((txtr / size) * (1.0 / size));
            tx1 = tx0 + (1.0 / size);
            ty1 = ty0 + (1.0 / size);
        }
        //System.out.println(world.thePlayer.y + " " + txtr + " " + tx0 + " " + ty0 + " " + tx1 + " " + ty1);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, Sheetholder.tilestxtr.getTextureID());
        //Sheetholder.tilestxtr.bind();
        glColor3d(1.0, 1.0, 1.0);
        glBegin(GL_QUADS);
            glTexCoord2d(tx0, ty0);
            glVertex2d(x,         y);
            glTexCoord2d(tx0, ty1);
            glVertex2d(x,         y - height);
            glTexCoord2d(tx1, ty1);
            glVertex2d(x - width, y - height);
            glTexCoord2d(tx1, ty0);
            glVertex2d(x - width, y);
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }
}
