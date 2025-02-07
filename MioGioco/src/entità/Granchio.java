package entità;

import statigioco.Playing;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.MetodiUtili.IsPavimento;

public class Granchio extends Nemico
{
    public Granchio (float x, float y)
    {
        super (x, y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO, GRANCHIO);
        initHitBox (22 , 19);
        initAttackBox (82, 19, 30);
    }



    public void update (int [][] datiLvl, Playing playing)
    {
        updateComportamento (datiLvl, playing);
        updateTickAnimazione();
        updateAttackBox ();
    }

    public void updateComportamento (int [][] datiLvl, Playing playing)
    {
        if (primoUpdate)
        {
            controlloPrimoUpdate(datiLvl);
        }

        if (inAria)
        {
            updateInAria (datiLvl);
        }
        else
        {
            switch (stato)
            {
                case IDLE ->
                {
                    if (IsPavimento(hitbox, datiLvl))
                    {
                        nuovoStato (CORSA);
                    }
                    else
                    {
                        inAria = true;
                    }
                }

                case CORSA ->
                {
                    if (puòVedereGiocatore(datiLvl, playing.getGiocatore()))
                    {
                        giraVersoGiocatore(playing.getGiocatore());

                        if (isGiocatoreNelRangePerAttacco(playing.getGiocatore()))
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
                        controllaColpoGiocatore (attackBox, playing.getGiocatore());
                    }
                }

                case COLPO ->
                {
                    if (indiceAni <= GetContSprite(tipoNemico, stato) - 2)
                    {
                        contraccolpo (direzioneContraccolpo, datiLvl, 2f);
                    }

                    updateContraccolpoDrawOffset ();
                }
            }
        }
    }
}
