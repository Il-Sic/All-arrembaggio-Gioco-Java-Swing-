package entità;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entità
{
    protected float x, y;
    protected int larghezza, altezza;
    protected Rectangle2D.Float hitbox;

    public Entità (float x, float y ,int larghezza, int altezza)
    {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    protected void drawHitBox (Graphics g, int xLvlOffset)
    {
        // debug hitbox
        g.setColor (Color.PINK);
        g.drawRect ((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }
    protected void initHitBox(float x, float y, int larghezza, int altezza)
    {
        hitbox = new Rectangle2D.Float (x, y, larghezza, altezza);
    }

//    public void updateHitBox ()
//    {
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }

    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }

}
