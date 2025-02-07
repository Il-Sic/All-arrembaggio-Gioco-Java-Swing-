package entità;

import statigioco.Playing;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Dialogo.ESCLAMAZIONE;
import static utilità.Costanti.Direzioni.SINISTRA;
import static utilità.MetodiUtili.IsPavimento;
import static utilità.MetodiUtili.PuòMuoversiQui;

public class Squalo extends Nemico
{
    public Squalo (float x, float y)
    {
        super(x, y, LARGHEZZA_SQUALO, ALTEZZA_SQUALO, SQUALO);
        initHitBox(18, 22);
        initAttackBox(20, 20, 20);
    }

    public void update(int[][] datiLvl, Playing playing)
    {
        updateComportamento(datiLvl, playing);
        updateTickAnimazione();
        updateAttackBoxFlip();
    }

    private void updateComportamento(int[][] datiLvl, Playing playing)
    {
        if (primoUpdate)
        {
            controlloPrimoUpdate(datiLvl);
        }

        if (inAria)
        {
            controlloInAria (datiLvl, playing);
        }
        else
        {
            switch (stato)
            {
                case IDLE ->
                {
                    if (IsPavimento(hitbox, datiLvl))
                    {
                        nuovoStato(CORSA);
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
                        giratiVersoGiocatore(playing.getGiocatore());
                        
                        if (isGiocatoreNelRangePerAttacco(playing.getGiocatore()))
                        {
                            nuovoStato(ATTACCO);
                        }
                    }

                    muovi(datiLvl);
                }
                
                case ATTACCO ->
                {
                    if (indiceAni == 0)
                    {
                        attaccoControllato = false;
                    }
                    else if (indiceAni == 3)
                    {
                        if (!attaccoControllato)
                        {
                            controllaColpoGiocatore (attackBox, playing.getGiocatore());
                        }
                        
                        mossaAttacco(datiLvl, playing);
                    }
                }
                    
                case COLPO ->
                {
                    if (indiceAni <= GetContSprite (tipoNemico, stato) - 2)
                    {
                        contraccolpo (direzioneContraccolpo, datiLvl, 2f);
                    }

                    updateContraccolpoDrawOffset();
                }
            }
        }
    }

    protected void mossaAttacco(int[][] datiLvl, Playing playing)
    {
        float xVel = 0;

        if (dirCamminata == SINISTRA)
        {
            xVel = -velPg;
        }
        else
        {
            xVel = velPg;
        }

        if (PuòMuoversiQui(hitbox.x + xVel * 4, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            if (IsPavimento(hitbox, xVel * 4, datiLvl))
            {
                hitbox.x += xVel * 4;
                return;
            }
        }

        nuovoStato(IDLE);
        playing.aggiungiDialogo((int) hitbox.x, (int) hitbox.y, ESCLAMAZIONE);
    }
}
