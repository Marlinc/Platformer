package platformer;

import static org.lwjgl.opengl.GL11.*;

public class HUD {
    public World world;
    
    public HUD (World world)
    {
        this.world = world;
    }
    
    public void tick (double delta)
    {
        
    }
    public void render ()
    {
        Tile tile = world.thePlayer.inventory.holdingTile();
        double tx0, ty0, tx1, ty1;
        tx0 = ty0 = tx1 = ty1 = 0.0;
        int txtr = tile.getTileTexture();
        if (txtr < 0) return;
        if (txtr == 0) tx1 = ty1 = 1.0 / 16.0;
        else {
            int size = Sheetholder.tilestxtr.getImageWidth() / 16;
            tx0 = (int) (txtr % size) * (1.0 / size);
            ty0 = (int) Math.floor(txtr / (Sheetholder.tilestxtr.getImageWidth() / 16) * (1.0 / size));
            tx1 = tx0 + (1.0 / size);
            ty1 = ty0 + (1.0 / size);
        }
        
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, Sheetholder.tilestxtr.getTextureID());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glColor3d(1.0, 1.0, 1.0);
        glBegin(GL_QUADS);
            glTexCoord2d(tx0, ty0);
            glVertex2d(-Utilities.tX, -Utilities.tY);
            glTexCoord2d(tx0, ty1);
            glVertex2d(-Utilities.tX, -Utilities.tY-64);
            glTexCoord2d(tx1, ty1);
            glVertex2d(-Utilities.tX+64, -Utilities.tY-64);
            glTexCoord2d(tx1, ty0);
            glVertex2d(-Utilities.tX+64, -Utilities.tY);
        glEnd();
        glDisable(GL_TEXTURE_2D);
        
        
        
        
        
        
        
        
        
        /*glBegin(GL_QUADS);
            glColor3d(Math.random(), Math.random(), Math.random());
            glVertex2d(-Utilities.tX, -Utilities.tY);
            glVertex2d(-Utilities.tX, -Utilities.tY-50);
            glVertex2d(-Utilities.tX+50, -Utilities.tY-50);
            glVertex2d(-Utilities.tX+50, -Utilities.tY);
        glEnd();*/
    }
}
