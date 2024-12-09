package entità;

import main.Gioco;

import static utilità.Costanti.CostantiNemico.*;

public class Granchio extends Nemico
{

    public Granchio (float x, float y)
    {
        super (x, y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO, GRANCHIO);
        initHitBox (x, y, (int) (22 * Gioco.SCALA), (int) (19  * Gioco.SCALA));
    }

    public void update (int [][] datiLvl, Giocatore giocatore)
    {
        updateMove(datiLvl, giocatore);
        updateTickAnimazione();
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
}
