package entità;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Direzioni.DESTRA;

public class Granchio extends Nemico
{
    // Box attacco
    private Rectangle2D.Float attackBox;
    private int xAttackBoxOffset;

    public Granchio (float x, float y)
    {
        super (x, y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO, GRANCHIO);
        initHitBox (x, y, (int) (22 * Gioco.SCALA), (int) (19  * Gioco.SCALA));
        initAttackBox ();
    }

    private void initAttackBox()
    {
        attackBox = new Rectangle2D.Float (x, y, (int) (82 * Gioco.SCALA), (int) (19 * Gioco.SCALA));
        xAttackBoxOffset = (int) (30 * Gioco.SCALA);
    }

    public void update (int [][] datiLvl, Giocatore giocatore)
    {
        updateMove(datiLvl, giocatore);
        updateTickAnimazione();
        updateAttackBox ();
    }

    private void updateAttackBox()
    {
        attackBox.x = hitbox.x - xAttackBoxOffset;
        attackBox.y = hitbox.y;
    }

    public void updateMove (int [][] datiLvl, Giocatore giocatore)
    {
        if (primoUpdate)
        {
            checkPrimoUpdate (datiLvl);
        }

        if (inAria)
        {
            updateInAria (datiLvl);
        }
        else
        {
            switch (statoNemico)
            {
                case IDLE -> nuovoStato (CORSA);

                case CORSA ->
                {
                    if (puòVedereGiocatore(datiLvl, giocatore))
                    {
                        giraVersoGiocatore (giocatore);
                    }

                    if (isGiocatoreNelRaggioPerAttacco(giocatore))
                    {
                        nuovoStato(ATTACCO);
                    }

                    muovi (datiLvl);
                }
            }
        }
    }

    public void drawAttackBox (Graphics g, int xLvlOffset)
    {
        g.setColor (Color.red);
        g.drawRect ((int) (attackBox.x - xLvlOffset), (int) (attackBox.y), (int) (attackBox.width), (int) (attackBox.height));
    }

    public int xFlip ()
    {
        if (dirCamminata == DESTRA)
        {
            return larghezza;
        }
        else
        {
            return 0;
        }
    }

    public int lFlip ()
    {
        if (dirCamminata == DESTRA)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}
