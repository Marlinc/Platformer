package platformer;

public class Config {
    public int maxfps;
    public int width;
    public int height;
    
    public boolean vsync;
    public boolean resizable;
    public boolean fullscreen;
    
    public Main main;
    public String title = "Default title set in config.title";
    
    public Config (Main main)
    {
        this.main = main;
        
        maxfps = getMonitorRefreshRate();
        width  = 640;
        height = 480;
        
        resizable  = false;
        vsync      = true;
        fullscreen = false;
    }
    public int getMonitorRefreshRate()
    {
        return main.screen.getMonitorRefreshRate();
    }
}
