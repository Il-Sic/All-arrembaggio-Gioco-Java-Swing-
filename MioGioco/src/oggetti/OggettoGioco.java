package oggetti;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilità.Costanti.CostantiGiocatore.GetSpriteCont;
import static utilità.Costanti.CostantiOggetto.BARILE;
import static utilità.Costanti.CostantiOggetto.CASSA;
import static utilità.Costanti.VEL_ANI;

public class OggettoGioco
{

    protected int x, y, tipoOggetto;
    protected Rectangle2D.Float hitbox;
    protected boolean faiAnimazione, attivo = true;
    protected int tickAni, indiceAni;
    protected int xDrawOffset, yDrawOffset;

    public OggettoGioco(int x, int y, int objType)
    {
        this.x = x;
        this.y = y;
        this.tipoOggetto = objType;
    }

    protected void updateTickAnimazione ()
    {
        tickAni++;
        if (tickAni >= VEL_ANI)
        {
            tickAni = 0;
            indiceAni++;
            if (indiceAni >= GetSpriteCont (tipoOggetto))
            {
                indiceAni = 0;
                if (tipoOggetto == BARILE || tipoOggetto == CASSA)
                {
                    faiAnimazione = false;
                    attivo = false;
                }
            }
        }
    }

    public void resetta ()
    {
        indiceAni = 0;
        tickAni = 0;
        attivo = true;

        if (tipoOggetto == BARILE || tipoOggetto == CASSA)
            faiAnimazione = false;
        else
            faiAnimazione = true;
    }

    protected void initHitbox (int larghezza, int altezza)
    {
        hitbox = new Rectangle2D.Float(x, y, (int) (larghezza * Gioco.SCALA), (int) (altezza * Gioco.SCALA));
    }

    public void drawHitbox (Graphics g, int xLvlOffset)
    {
        g.setColor (Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public int getTipoOggetto ()
    {
        return tipoOggetto;
    }

    public Rectangle2D.Float getHitbox ()
    {
        return hitbox;
    }

    public boolean isAttivo ()
    {
        return attivo;
    }

    public void setAttivo (boolean attivo)
    {
        this.attivo = attivo;
    }

    public void setAnimazione (boolean faiAnimazione)
    {
        this.faiAnimazione = faiAnimazione;
    }

    public int getXDrawOffset ()
    {
        return xDrawOffset;
    }

    public int getYDrawOffset ()
    {
        return yDrawOffset;
    }

    public int getIndiceAni ()
    {
        return indiceAni;
    }
}
