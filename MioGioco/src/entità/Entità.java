package entità;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entità
{
    protected float x, y;
    protected int larghezza, altezza;
    protected Rectangle2D.Float hitbox;
    protected int tickAni, indiceAni;                                // 30 perchè le animazioni sono a 120 fps / 4 immagini = 30 ma va troppo lento quindi ho incrementato a 50
    protected int stato;
    protected float velAria;
    protected boolean inAria = false;
    protected int vitaMax;
    protected int vitaCorrente;
    // Box attacco
    protected Rectangle2D.Float attackBox;

    protected float velPg = 1.0f * Gioco.SCALA;

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

    protected void initHitBox(int larghezza, int altezza)
    {
        hitbox = new Rectangle2D.Float (x, y, (int) larghezza * Gioco.SCALA, (int) altezza * Gioco.SCALA);
    }

    protected void drawAttackBox (Graphics g, int xLvlOffset)
    {
        g.setColor (Color.red);
        g.drawRect ((int) (attackBox.x - xLvlOffset), (int) (attackBox.y), (int) (attackBox.width), (int) (attackBox.height));
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

    public int getIndiceAni()
    {
        return indiceAni;
    }

    public int getVitaCorrente ()
    {
        return vitaCorrente;
    }
}
