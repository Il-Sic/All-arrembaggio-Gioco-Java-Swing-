package entità;

import main.Gioco;

import java.awt.geom.Rectangle2D;

import static utilità.Costanti.*;
import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Direzioni.*;
import static utilità.MetodiUtili.*;

public class Nemico extends Entità
{
    protected int tipoNemico;
    protected boolean primoUpdate = true;
    protected int dirCamminata = SINISTRA;
    protected int casellaY;
    protected float distanzaAttacco = Gioco.DIMENSIONE_CASELLA;
    protected boolean attivo = true;
    protected boolean attaccoControllato;


    public Nemico (float x, float y, int larghezza, int altezza, int tipoNemico)
    {
        super (x, y, larghezza, altezza);
        this.tipoNemico = tipoNemico;

        vitaMax = GetVitaMax (tipoNemico);
        vitaCorrente = vitaMax;
        this.velPg = 0.5f * Gioco.SCALA;
    }

    protected void updateTickAnimazione()
    {
        tickAni++;

        if (tickAni >= VEL_ANI)
        {
            tickAni = 0;
            indiceAni ++;

            if (indiceAni >= GetContSprite(tipoNemico, stato))
            {
                indiceAni = 0;

                switch (stato)
                {
                    case ATTACCO, COLPO ->
                    {
                        stato = IDLE;
                    }
                    case MORTE ->
                    {
                        attivo = false;
                    }
                }

//                if (statoNemico == ATTACCO)
//                {
//                    statoNemico = IDLE;
//                }
//                else if (statoNemico == COLPO)
//                {
//                    statoNemico = IDLE;
//                }
//                else if (statoNemico == MORTE)
//                {
//                    attivo = false;
//                }
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
        if (puòMuoversiQui(hitbox.x, hitbox.y + velAria, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.y += velAria;
            velAria += GRAVIOL;
        }
        else
        {
            inAria = false;
            hitbox.y = getPosizioneEntitàVicinoAlMuroY(hitbox, velAria);
            casellaY = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);
        }
    }

    protected void muovi (int [][] datiLvl)
    {
        float velX = 0;

        if (dirCamminata == SINISTRA)
        {
            velX = - velPg;
        }
        else
        {
            velX = velPg;
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

    protected void cambiaDirCamminata()
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
        this.stato = statoNemico;

        tickAni = 0;
        indiceAni = 0;
    }

    public void ferisci (int valore)
    {
        vitaCorrente -= valore;

        if (vitaCorrente <= 0)
        {
            nuovoStato (MORTE);
        }
        else
        {
            nuovoStato (COLPO);
        }
    }

    protected void controllaColpoGiocatore(Rectangle2D.Float attackBox, Giocatore giocatore)
    {
        if (attackBox.intersects (giocatore.hitbox))
        {
            giocatore.cambiaVita (-GetDannoNemico (tipoNemico));
        }

        attaccoControllato = true;
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

    public int getStatoNemico()
    {
        return stato;
    }

    public boolean isAttivo ()
    {
        return attivo;
    }

    public void resettaNemico()
    {
        hitbox.x = x;
        hitbox.y = y;
        primoUpdate = true;
        vitaCorrente = vitaMax;
        nuovoStato (IDLE);
        attivo = true;
        velAria = 0;
    }
}
