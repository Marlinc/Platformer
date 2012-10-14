package platformer;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public int selectedSlot;
    public List<Tile> slots;
    
    public Inventory ()
    {
        selectedSlot = 0;
        slots = new ArrayList<Tile>();
        slots.add(Tile.dirt);
        slots.add(Tile.grass);
        slots.add(Tile.stone);
        slots.add(Tile.leave);
        slots.add(Tile.log);
        slots.add(Tile.stone);
        slots.add(Tile.tnt);
        slots.add(Tile.wood);
        slots.add(Tile.sand);
        slots.add(Tile.gravel);
        slots.add(Tile.water);
    }
    public void scrollUp ()
    {
        selectedSlot++;
        if (selectedSlot >= slots.size()) selectedSlot = 0;
    }
    public Tile holdingTile ()
    {
        return slots.get(selectedSlot);
    }
    public void scrollDown ()
    {
        selectedSlot--;
        if (selectedSlot < 0) selectedSlot = slots.size() - 1;
    }
}