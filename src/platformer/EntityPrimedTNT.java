package platformer;

import static org.lwjgl.opengl.GL11.*;

public class EntityPrimedTNT extends Entity {
    public int life;
    public double xspeed, yspeed;
    
    public EntityPrimedTNT (World world, int x, int y, int life, double vx, double vy)
    {
        this(world, x, y, life);
        this.xspeed = vx;
        this.yspeed = vy;
    }
    public EntityPrimedTNT (World world, int x, int y, int life)
    {
        super(world);
        this.x = x * 16.0;
        this.y = y * 16.0;
        
        width  = 15;
        height = 15;
        
        xspeed = Math.random() * 6 - 3;
        yspeed = Math.random() * 3;
        
        this.life = life;
    }
    public void tick (double delta)
    {
        super.tick(delta);

        xspeed *= Math.pow(0.995, delta);
        yspeed -= 0.02 * delta;
        if (onground) yspeed = 0;
        move(xspeed, yspeed);
        life -= delta;
        if (life <= 0) {
            if (!isWet()) {
                world.explosion((int) (x / 16.0),
                        (int) (y / 16.0), 
                        8);
            }
            die();
        }
    }
    public void render ()
    {
        boolean white = false;
        if ((life % 300) <= 100) white = true;
        
        glColor3d(1.0, 1.0, 1.0);
        if (white) {
            glDisable(GL_TEXTURE_2D);
            glBegin(GL_QUADS);
                glVertex2d(x,         y);
                glVertex2d(x,         y - height);
                glVertex2d(x - width, y - height);
                glVertex2d(x - width, y);
            glEnd();
            return;
        }
        double tx0, ty0, tx1, ty1;
        tx0 = ty0 = tx1 = ty1 = 0.0;
        int txtr = Tile.tnt.getTileTexture();
        if (txtr < 0) return;
        if (txtr == 0) tx1 = ty1 = 1.0 / 16.0;
        else {
            int size = Sheetholder.tilestxtr.getImageWidth() / 16;
            tx0 = (int) (txtr % size) * (1.0 / size);
            ty0 = (int) Math.floor((txtr / size) * (1.0 / size));
            tx1 = tx0 + (1.0 / size);
            ty1 = ty0 + (1.0 / size);
        }
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, Sheetholder.tilestxtr.getTextureID());
        glBegin(GL_QUADS);
            glTexCoord2d(tx1, ty0);
            glVertex2d(x,         y);
            glTexCoord2d(tx1, ty1);
            glVertex2d(x,         y - height);
            glTexCoord2d(tx0, ty1);
            glVertex2d(x - width, y - height);
            glTexCoord2d(tx0, ty0);
            glVertex2d(x - width, y);
        glEnd();
        glDisable(GL_TEXTURE_2D);
    }
}
