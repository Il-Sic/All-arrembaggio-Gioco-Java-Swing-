package entità;

import statigioco.Playing;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.Dialogo.DOMANDA;
import static utilità.Costanti.Direzioni.DESTRA;
import static utilità.Costanti.Direzioni.SINISTRA;
import static utilità.Costanti.GetDannoNemico;
import static utilità.MetodiUtili.IsPavimento;
import static utilità.MetodiUtili.PuòMuoversiQui;

public class Stella extends Nemico
{
    private boolean preRotolata = true;
    private int tickDaUltimoDannoAlGiocatore;
    private int tickDopoRotolataInIdle;
    private int tickDurataRotolata, durataRotolata = 300;

    public Stella (float x, float y)
    {
        super(x, y, LARGHEZZA_STELLA, ALTEZZA_STELLA, STELLA);
        initHitBox (17, 21);
    }

    public void update(int[][] datiLvl, Playing playing)
    {
        updateBehavior(datiLvl, playing);
        updateTickAnimazione();
    }

    private void updateBehavior(int[][] datiLvl, Playing playing)
    {
        if (primoUpdate)
        {
            controlloPrimoUpdate(datiLvl);
        }

        if (inAria)
        {
            controlloInAria(datiLvl, playing);
        }
        else
        {
            switch (stato)
            {
                case IDLE ->
                {
                    preRotolata = true;

                    if (tickDopoRotolataInIdle >= 120)
                    {
                        if (IsPavimento (hitbox, datiLvl))
                        {
                            nuovoStato(CORSA);
                        }
                        else
                        {
                            inAria = true;
                        }

                        tickDopoRotolataInIdle = 0;
                        tickDaUltimoDannoAlGiocatore = 60;
                    }
                    else
                    {
                        tickDopoRotolataInIdle++;
                    }
                }
                case CORSA ->
                {
                    if (puòVedereGiocatore(datiLvl, playing.getGiocatore()))
                    {
                        nuovoStato(ATTACCO);
                        setDirCamminata(playing.getGiocatore());
                    }
                    muovi(datiLvl, playing);
                }

                case ATTACCO ->
                {
                    if (preRotolata)
                    {
                        if (indiceAni >= 3)
                        {
                            preRotolata = false;
                        }
                    }
                    else
                    {
                        muovi(datiLvl, playing);
                        controllaDannoAlGiocatore (playing.getGiocatore());
                        controllaRotolata(playing);
                    }

                }

                case COLPO ->
                {
                    if (indiceAni <= GetContSprite(tipoNemico, stato) - 2)
                    {
                        contraccolpo (direzioneContraccolpo, datiLvl, 2f);
                    }

                    updateContraccolpoDrawOffset ();
                    tickDopoRotolataInIdle = 120;
                }
            }
        }
    }

    private void controllaDannoAlGiocatore(Giocatore giocatore)
    {
        if (hitbox.intersects(giocatore.getHitbox()))
        {
            if (tickDaUltimoDannoAlGiocatore >= 60)
            {
                tickDaUltimoDannoAlGiocatore = 0;
                giocatore.cambiaVita(-GetDannoNemico (tipoNemico), this);
            }
            else
            {
                tickDaUltimoDannoAlGiocatore++;
            }
        }
    }

    private void setDirCamminata(Giocatore giocatore)
    {
        if (giocatore.getHitbox().x > hitbox.x)
        {
            dirCamminata = DESTRA;
        }
        else
        {
            dirCamminata = SINISTRA;
        }
    }

    protected void muovi (int[][] datiLvl, Playing playing)
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

        if (stato == ATTACCO)
        {
            xVel *= 2;
        }

        if (PuòMuoversiQui(hitbox.x + xVel, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            if (IsPavimento(hitbox, xVel, datiLvl))
            {
                hitbox.x += xVel;
                return;
            }
        }
        if (stato == ATTACCO)
        {
            rotolata (playing);
            tickDurataRotolata = 0;
        }

        cambiaDirCamminata();
    }

    private void controllaRotolata (Playing playing)
    {
        tickDurataRotolata++;

        if (tickDurataRotolata >= durataRotolata)
        {
            rotolata(playing);
            tickDurataRotolata = 0;
        }
    }

    private void rotolata (Playing playing)
    {
        nuovoStato(IDLE);
        playing.aggiungiDialogo((int) hitbox.x, (int) hitbox.y, DOMANDA);
    }

}
