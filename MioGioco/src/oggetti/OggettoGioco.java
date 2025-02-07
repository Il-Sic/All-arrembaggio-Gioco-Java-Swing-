package oggetti;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilità.Costanti.CostantiOggetto.*;
import static utilità.Costanti.VEL_ANI;

public class OggettoGioco
{

    protected int x, y, tipoOggetto;
    protected Rectangle2D.Float hitbox;
    protected boolean faiAnimazione, attivo = true;
    protected int tickAni, indiceAni;
    protected int xDrawOffset, yDrawOffset;

    public OggettoGioco(int x, int y, int tipoOggetto)
    {
        this.x = x;
        this.y = y;
        this.tipoOggetto = tipoOggetto;
    }

    protected void updateTickAnimazione ()
    {
        tickAni ++;

        if (tickAni >= VEL_ANI)
        {
            tickAni = 0;
            indiceAni ++;

            if (indiceAni >= GetSpriteCont (tipoOggetto))
            {
                indiceAni = 0;

                if (tipoOggetto == BARILE || tipoOggetto == CASSA)
                {
                    faiAnimazione = false;
                    attivo = false;
                }
                else if (tipoOggetto == CANNONE_SINISTRA || tipoOggetto == CANNONE_DESTRA)
                {
                    faiAnimazione = false;
                }
            }
        }
    }


    public void resetta ()
    {
        indiceAni = 0;
        tickAni = 0;
        attivo = true;

        if (tipoOggetto == BARILE || tipoOggetto == CASSA || tipoOggetto == CANNONE_SINISTRA || tipoOggetto == CANNONE_DESTRA)
        {
            faiAnimazione = false;
        }
        else
        {
            faiAnimazione = true;
        }
    }

    protected void initHitbox (int larghezza, int altezza)
    {
        hitbox = new Rectangle2D.Float(x, y, (int) (larghezza * Gioco.SCALA), (int) (altezza * Gioco.SCALA));
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

    public int getTickAni ()
    {
        return tickAni;
    }
}
