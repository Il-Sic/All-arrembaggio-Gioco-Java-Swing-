package entità;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilità.Costanti.Direzioni.*;
import static utilità.MetodiUtili.PuòMuoversiQui;

public abstract class Entità
{
    protected float x, y;
    protected int larghezza, altezza;
    protected Rectangle2D.Float hitbox;
    protected int tickAni, indiceAni;
    protected int stato;
    protected float velAria;
    protected boolean inAria = false;
    protected int vitaMax;
    protected int vitaCorrente;
    protected Rectangle2D.Float attackBox;
    protected float velPg;

    protected int direzioneContraccolpo;
    protected float pushDrawOffset;
    protected int contraccolpoDirOffset = SU;

    public Entità (float x, float y ,int larghezza, int altezza)
    {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    protected void updateContraccolpoDrawOffset ()
    {
        float vel = 0.95f;
        float limite = -30f;

        if (contraccolpoDirOffset == SU)
        {
            pushDrawOffset -= vel;

            if (pushDrawOffset <= limite)
            {
                contraccolpoDirOffset = SOTTO;
            }
        }
        else
        {
            pushDrawOffset += vel;

            if (pushDrawOffset >= 0)
            {
                pushDrawOffset = 0;
            }
        }
    }

    public void contraccolpo (int direzioneContraccolpo, int [][] datiLvl, float velMulti)
    {
        float xVel = 0;

        if (direzioneContraccolpo == SINISTRA)
        {
            xVel = -velPg;
        }
        else
        {
            xVel = velPg;
        }

        if (PuòMuoversiQui(hitbox.x + xVel * velMulti, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.x += xVel * velMulti;
        }
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

    public int getStato ()
    {
        return stato;
    }

    public void nuovoStato (int stato)
    {
        this.stato = stato;
        tickAni = 0;
        indiceAni = 0;
    }
}
