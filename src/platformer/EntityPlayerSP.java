package platformer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class EntityPlayerSP extends EntityPlayer {
    public double jumpspeed;
    public boolean mouse0;
    
    public EntityPlayerSP(World world)
    {
        super(world);
        y = 30 * 16;
        mouse0 = false;
        jumpspeed = 0.0;
    }
    public void tick (double delta)
    {
        super.tick(delta);
        
        double speed = 0.1 * delta;
        double xadd = 0, yadd = 0;
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) { y = 16 * 30; x = 0; } 
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))   speed /= 5;
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) speed *= 5;
        boolean up    = Keyboard.isKeyDown(Keyboard.KEY_W)
                     || Keyboard.isKeyDown(Keyboard.KEY_UP)
                     || Keyboard.isKeyDown(Keyboard.KEY_SPACE);
        //boolean down  = Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
        boolean right = Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
        boolean left  = Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
        //if (up)    yadd += speed;
        //if (down)  yadd -= speed;
        boolean isWet = isWet();
        if (onground)  jumpspeed = 0;
        if (up && onground) jump(10);
        if (!onground) {
            yadd -= delta / 20.0;
            jumpspeed -= delta / 30.0;
            if (isWet) jumpspeed = 0;
            if (isWet) yadd /= 3;
        }
        if (isWet && up) {
            yadd += delta / 20.0;
        }
        yadd += jumpspeed;
        if (right) xadd += speed;
        if (left)  xadd -= speed;
        
        move(xadd, yadd);
        if (isWet && yadd != 0 && !isWet()) jump(5);
        int mousedwheel = Mouse.getDWheel();
        if (mousedwheel > 0) inventory.scrollUp();
        if (mousedwheel < 0) inventory.scrollDown();
        
        boolean breakblock = Mouse.isButtonDown(0);
        boolean placeblock = Mouse.isButtonDown(1);
        int mx = Mouse.getX();
        int my = Mouse.getY();
        int tmx = (int) Math.ceil((-Utilities.tX + mx) / 16.0);
        int tmy = (int) Math.ceil((-Utilities.tY - (Utilities.getHeight() - my)) / 16.0);
        double distanceX = Math.ceil(x / 16.0) - tmx;
        double distanceY = Math.ceil(y / 16.0) - tmy;
        double distance = Math.sqrt(distanceX*distanceX + distanceY*distanceY);
        if        (distance < 5 && breakblock && world.getTile(tmx, tmy).solid) {
            world.setTile(tmx, tmy, Tile.air, true);
        } else if (distance < 5 && placeblock) {
            if (!world.getTile(tmx, tmy).solid) {
                if (!world.isEntityAt(tmx, tmy)) world.setTile(tmx, tmy, inventory.holdingTile());
            } else if (mouse0 != placeblock) {
                world.getTile(tmx, tmy).onInteract(world, tmx, tmy);
            }
        }
        mouse0 = placeblock;
    }
    public void jump (int height)
    {
        jumpspeed += height;
    }
    public void render ()
    {
        super.render();
        int x = (int) Math.floor(this.x);
        int y = (int) Math.floor(this.y);
        
        glColor3d(1.0, 1.0, 0.0);
        glBegin(GL_QUADS);
            glVertex2d(x,         y);
            glVertex2d(x,         y - height);
            glVertex2d(x - width, y - height);
            glVertex2d(x - width, y);
        glEnd();
    }
}

