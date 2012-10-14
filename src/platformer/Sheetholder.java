package platformer;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sheetholder {
    public static BufferedImage tiles;
    public static Texture tilestxtr;
    
    static {
        try {
            InputStream is = ResourceLoader.getResource("res/tiles.png").openStream();
            tiles = ImageIO.read(is);
            is = ResourceLoader.getResource("res/tiles.png").openStream();
            //tilestxtr = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/tiles.png")));
            tilestxtr = TextureLoader.getTexture("PNG", is);
            //new BufferedimageResourceLoader.getResource("res/tiles.png").openStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
