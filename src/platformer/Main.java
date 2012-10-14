package platformer;

import org.lwjgl.opengl.Display;

public class Main {
    public World world;
    public Config config;
    public Utilities utilities;
    public Screen screen;
    
    public double fps = 0, fps2 = 0;
    public int chunkupdates;
    
    public Main ()
    {
        utilities = new Utilities();
        screen = new Screen();
        config = new Config(this);
    }
    public void init ()
    {
        screen.loadConfig(config);
        screen.createDisplay();
    }
    public void resetGame ()
    {
        world = new World(this);
    }
    public void run ()
    {
        long now, lastTime, lastTime2;
        double delta;
        lastTime  = System.currentTimeMillis();
        lastTime2 = System.currentTimeMillis();
        int frames = 0;
        
        resetGame();
        while(!screen.isCloseRequested()) {
            /*now = System.nanoTime();
            delta = (now - lastTime) / 1000000.0; //Delta in milliseconds
            lastTime = now;*/
            now = System.currentTimeMillis();
            delta = now - lastTime; //Delta in milliseconds
            lastTime = now;
            
            fps = utilities.averageFps(1000 / delta);
            tick(delta);
            render();
            frames++;
            if (System.currentTimeMillis() > (lastTime2 + 1000)) {
                fps2 = frames;
                chunkupdates = world.chunkupdates;
                world.chunkupdates = frames = 0;
                lastTime2 = System.currentTimeMillis();
            }
            
            if (config.maxfps > 0) {
                utilities.sync(config.maxfps);
            } else {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        destroy();
    }
    public void destroy ()
    {
        if (screen != null) screen.destroy();
    }
    public double render ()
    {
        long begin = System.nanoTime();
        screen.prepareRender();
        
        world.render();
        screen.finishRender();
        
        return ((System.nanoTime() - begin) / 1000000.0);
    }
    public void tick (double delta)
    {
        Display.setTitle("Chunks: " + world.chunkhandler.getChunkAmount()
                       + " Updates: " + chunkupdates
                       + " FPS: " + Math.round(fps * 10) / 10.0
                       + " FPS2: " + fps2
                       + " Entities: " + world.entities.size());
        world.tick(delta);
    }
    public static void main (String[] args)
    {
        Main main = new Main();
        main.init();
        main.run();
    }
}
