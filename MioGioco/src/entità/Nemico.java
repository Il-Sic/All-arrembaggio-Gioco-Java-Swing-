package entità;

import main.Gioco;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Direzioni.*;
import static utilità.MetodiUtili.*;

public class Nemico extends Entità
{
    protected int indiceAni, statoNemico, tipoNemico;
    protected int tickAni, velAni = 25;
    protected boolean primoUpdate = true;
    protected boolean inAria;
    protected float gravità = 0.04f * Gioco.SCALA;
    protected float velCaduta;
    protected float velCamminata = 0.3f * Gioco.SCALA;
    protected int dirCamminata = SINISTRA;
    protected int casellaY;
    protected float distanzaAttacco = Gioco.DIMENSIONE_CASELLA;


    public Nemico(float x, float y, int larghezza, int altezza, int tipoNemico)
    {
        super(x, y, larghezza, altezza);
        this.tipoNemico = tipoNemico;

        initHitBox(x, y, larghezza, altezza);
    }

    protected void updateTickAnimazione()
    {
        tickAni++;

        if (tickAni >= velAni)
        {
            tickAni = 0;
            indiceAni ++;

            if (indiceAni >= getContSprite (tipoNemico, statoNemico))
            {
                indiceAni = 0;

                if (statoNemico == ATTACCO)
                {
                    statoNemico = IDLE;
                }
            }
        }
    }

    protected void checkPrimoUpdate(int[][] datiLvl)
    {
        if (!isEntitàSulPavimento(hitbox, datiLvl))
        {
            inAria = true;
        }

        primoUpdate = false;
    }

    protected void updateInAria(int[][] datiLvl)
    {
        if (puòMuoversiQui(hitbox.x, hitbox.y + velCaduta, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.y += velCaduta;
            velCaduta += gravità;
        }
        else
        {
            inAria = false;
            hitbox.y = getPosizioneEntitàVicinoAlMuroY(hitbox, velCaduta);
            casellaY = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);
        }
    }

    protected void muovi (int [][] datiLvl)
    {
        float velX = 0;

        if (dirCamminata == SINISTRA)
        {
            velX = - velCamminata;
        }
        else
        {
            velX = velCamminata;
        }

        if (puòMuoversiQui(hitbox.x + velX, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            if (isPavimento(hitbox, velX, datiLvl))
            {
                hitbox.x += velX;

                return;                                             // importante per non "looparlo" nella stessa posizione
            }
        }

        cambiaDirCamminata();
    }

    private void cambiaDirCamminata()
    {
        if (dirCamminata == SINISTRA)
        {
            dirCamminata = DESTRA;
        }
        else
        {
            dirCamminata = SINISTRA;
        }
    }

    protected void nuovoStato (int statoNemico)
    {
        this.statoNemico = statoNemico;

        tickAni = 0;
        indiceAni = 0;
    }

    protected boolean puòVedereGiocatore (int [][] datiLvl, Giocatore giocatore)
    {
        int casellaGiocatoreY = (int) (giocatore.getHitbox().y / Gioco.DIMENSIONE_CASELLA);

        if (casellaGiocatoreY == casellaY)
        {
            if (isGiocatoreNelRaggio(giocatore))
            {
                return isVistaChiara (datiLvl, hitbox, giocatore.hitbox, casellaY);
            }
        }

        return false;
    }

    protected void giraVersoGiocatore (Giocatore giocatore)
    {
        if (giocatore.hitbox.x > hitbox.x)
        {
            dirCamminata = DESTRA;
        }
        else
        {
            dirCamminata = SINISTRA;
        }
    }

    protected boolean isGiocatoreNelRaggio (Giocatore giocatore)
    {
        int valoreAssoluto = (int) Math.abs (giocatore.hitbox.x - hitbox.x);

        return valoreAssoluto <= distanzaAttacco * 5;
    }

    protected boolean isGiocatoreNelRaggioPerAttacco(Giocatore giocatore)
    {
        int valoreAssoluto = (int) Math.abs (giocatore.hitbox.x - hitbox.x);

        return valoreAssoluto <= distanzaAttacco;
    }

    public int getIndiceAni()
    {
        return indiceAni;
    }

    public int getStatoNemico()
    {
        return statoNemico;
    }
}
