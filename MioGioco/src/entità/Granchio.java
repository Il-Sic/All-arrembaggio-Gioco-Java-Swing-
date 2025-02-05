package entità;

import main.Gioco;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Direzioni.DESTRA;

public class Granchio extends Nemico
{
    private int xAttackBoxOffset;

    public Granchio (float x, float y)
    {
        super (x, y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO, GRANCHIO);
        initHitBox (22 , 19);
        initAttackBox ();
    }

    private void initAttackBox ()
    {
        attackBox = new Rectangle2D.Float (x, y, (int) (82 * Gioco.SCALA), (int) (19 * Gioco.SCALA));
        xAttackBoxOffset = (int) (30 * Gioco.SCALA);
    }

    public void update (int [][] datiLvl, Giocatore giocatore)
    {
        updateComportamento (datiLvl, giocatore);
        updateTickAnimazione();
        updateAttackBox ();
    }

    private void updateAttackBox ()
    {
        attackBox.x = hitbox.x - xAttackBoxOffset;
        attackBox.y = hitbox.y;
    }

    public void updateComportamento (int [][] datiLvl, Giocatore giocatore)
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
            switch (stato)
            {
                case IDLE -> nuovoStato (CORSA);

                case CORSA ->
                {
                    if (puòVedereGiocatore(datiLvl, giocatore))
                    {
                        giraVersoGiocatore(giocatore);

                        if (isGiocatoreNelRaggioPerAttacco(giocatore))
                        {
                            nuovoStato(ATTACCO);
                        }
                    }

                    muovi (datiLvl);
                }

                case ATTACCO ->
                {
                    if (indiceAni == 0)
                    {
                        attaccoControllato = false;
                    }

                    if (indiceAni == 3 && !attaccoControllato)
                    {
                        controllaColpoGiocatore (attackBox, giocatore);
                    }
                }
            }
        }
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
