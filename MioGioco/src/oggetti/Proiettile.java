package oggetti;

import main.Gioco;

import java.awt.geom.Rectangle2D;

import static utilità.Costanti.Proiettili.*;

public class Proiettile
{
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean attivo = true;

    public Proiettile (int x, int y, int dir)
    {
        int xOffset = (int) (-3 * Gioco.SCALA);
        int yOffset = (int) (5 * Gioco.SCALA);

        if (dir == 1)
        {
            xOffset = (int) (29 * Gioco.SCALA);
        }

        hitbox = new Rectangle2D.Float (x + xOffset, y + yOffset, LARGHEZZA_PALLA_CANNONE, ALTEZZA_PALLA_CANNONE);
        this.dir = dir;
    }
    public void updatePos ()
    {
        hitbox.x += dir * VEL_PALLA_CANNONE;
    }
    public void setPos (int x, int y)
    {
        hitbox.x = x;
        hitbox.y = y;
    }
    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }
    public void setAttivo (boolean attivo)
    {
        this.attivo = attivo;
    }
    public boolean isAttivo ()
    {
        return attivo;
    }
}
