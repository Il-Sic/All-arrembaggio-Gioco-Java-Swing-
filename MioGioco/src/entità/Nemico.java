package entità;

import main.Gioco;
import statigioco.Playing;

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
    protected int attackBoxOffsetX;


    public Nemico (float x, float y, int larghezza, int altezza, int tipoNemico)
    {
        super (x, y, larghezza, altezza);
        this.tipoNemico = tipoNemico;

        vitaMax = GetVitaMax (tipoNemico);
        vitaCorrente = vitaMax;
        this.velPg = 0.30f * Gioco.SCALA;
    }

    protected void updateAttackBox ()
    {
        attackBox.x = hitbox.x -attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    protected void updateAttackBoxFlip()
    {
        if (dirCamminata == DESTRA)
        {
            attackBox.x = hitbox.x + hitbox.width;
        }
        else
        {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }

        attackBox.y = hitbox.y;
    }

    protected void initAttackBox(int w, int h, int attackBoxOffsetX)
    {
        attackBox = new Rectangle2D.Float (x, y, (int) (w * Gioco.SCALA), (int) (h * Gioco.SCALA));
        this.attackBoxOffsetX = (int) (Gioco.SCALA * attackBoxOffsetX);
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
                if (tipoNemico == GRANCHIO || tipoNemico == SQUALO)
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
                }
                else if (tipoNemico == STELLA)
                {
                    if (stato == ATTACCO)
                    {
                        indiceAni  = 3;
                    }
                    else
                    {
                        indiceAni = 0;

                        if (stato == COLPO)
                        {
                            stato = IDLE;
                        }
                        else if (stato == MORTE)
                        {
                            attivo = false;
                        }
                    }
                }
            }
        }
    }

    protected void controlloPrimoUpdate (int[][] datiLvl)
    {
        if (!IsEntitàSulPavimento(hitbox, datiLvl))
        {
            inAria = true;
        }

        primoUpdate = false;
    }

    protected void controlloInAria (int [][] datiLvl, Playing playing)
    {
        if (stato != COLPO && stato != MORTE)
        {
            updateInAria (datiLvl);
            playing.getGestoreOggetto().controllaSpuntoniToccati (playing.getGiocatore());

            if (IsEntitàInAcqua (hitbox, datiLvl))
            {
                ferisci (vitaMax);
            }
        }
    }

    protected void updateInAria(int[][] datiLvl)
    {
        if (PuòMuoversiQui(hitbox.x, hitbox.y + velAria, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.y += velAria;
            velAria += GRAVIOL;
        }
        else
        {
            inAria = false;
            hitbox.y = GetPosizioneEntitàVicinoAlMuroY(hitbox, velAria);
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

        if (PuòMuoversiQui(hitbox.x + velX, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            if (IsPavimento(hitbox, velX, datiLvl))
            {
                hitbox.x += velX;

                return;
            }
        }

        cambiaDirCamminata();
    }

    protected void giratiVersoGiocatore (Giocatore giocatore)
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

    public void nuovoStato(int statoNemico)
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

            if (dirCamminata == SINISTRA)
            {
                direzioneContraccolpo = DESTRA;
            }
            else
            {
                direzioneContraccolpo = SINISTRA;
                contraccolpoDirOffset = SU;
                pushDrawOffset = 0;
            }
        }
    }

    protected void controllaColpoGiocatore(Rectangle2D.Float attackBox, Giocatore giocatore)
    {
        if (attackBox.intersects (giocatore.hitbox))
        {
            giocatore.cambiaVita (-GetDannoNemico (tipoNemico));
        }
        else
        {
            if (tipoNemico == SQUALO)
            {
                return;
            }
        }

        attaccoControllato = true;
    }

    protected boolean puòVedereGiocatore (int [][] datiLvl, Giocatore giocatore)
    {
        int casellaGiocatoreY = (int) (giocatore.getHitbox().y / Gioco.DIMENSIONE_CASELLA);

        if (casellaGiocatoreY == casellaY)
        {
            if (isGiocatoreNelRange(giocatore))
            {
                return IsVistaChiara(datiLvl, hitbox, giocatore.hitbox, casellaY);
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

    protected boolean isGiocatoreNelRange (Giocatore giocatore)
    {
        int valoreAssoluto = (int) Math.abs (giocatore.hitbox.x - hitbox.x);

        return valoreAssoluto <= distanzaAttacco * 5;
    }

    protected boolean isGiocatoreNelRangePerAttacco (Giocatore giocatore)
    {
        int valoreAssoluto = (int) Math.abs (giocatore.hitbox.x - hitbox.x);

        switch (tipoNemico)
        {
            case GRANCHIO ->
            {
                return valoreAssoluto <= distanzaAttacco;
            }

            case SQUALO ->
            {
                return valoreAssoluto <= distanzaAttacco * 2;
            }

            default ->
            {
                return false;
            }
        }
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
        pushDrawOffset = 0;
    }

    public int xFlip()
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

    public int lFlip()
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

    public float getPushDrawOffset()
    {
        return pushDrawOffset;
    }
}
