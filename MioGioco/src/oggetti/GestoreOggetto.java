package oggetti;

import entità.Giocatore;
import livelli.Livello;
import statigioco.Playing;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilità.Costanti.CostantiOggetto.*;

public class GestoreOggetto
{

    private Playing playing;
    private BufferedImage [][] imgsPozione, imgsContenitore;
    private BufferedImage imgSpuntone;

    private ArrayList <Pozione> pozioni;
    private ArrayList <ContenitoreGioco> contenitori;
    private ArrayList <Spuntone> spuntoni;

    public GestoreOggetto (Playing playing)
    {
        this.playing = playing;
        caricaImgs ();
    }

    public void controllaSpuntoniToccati (Giocatore giocatore)
    {
        for (Spuntone s : spuntoni)
        {
            if (s.getHitbox().intersects (giocatore.getHitbox()))
            {
                giocatore.killa ();
            }
        }
    }

    public void controllaOggettoToccato (Rectangle2D.Float hitbox)
    {
        for (Pozione p : pozioni)
        {
            if (p.isAttivo ())
            {
                if (hitbox.intersects (p.getHitbox ()))
                {
                    p.setAttivo (false);
                    applicaEffettoAlGiocatore (p);
                }
            }
        }
    }

    public void applicaEffettoAlGiocatore (Pozione p)
    {
        if (p.getTipoOggetto () == POZIONE_ROSSA)
        {
            playing.getGiocatore ().cambiaVita (VALORE_POZIONE_ROSSA);
        }
        else
        {
            playing.getGiocatore ().cambiaForza (VALORE_POZIONE_BLU);
        }
    }

    public void controllaHitOggetto (Rectangle2D.Float attackbox)
    {
        for (ContenitoreGioco contGio : contenitori)
        {
            if (contGio.isAttivo() && !contGio.faiAnimazione)
            {
                if (contGio.getHitbox().intersects(attackbox))
                {
                    contGio.setAnimazione (true);
                    int type = 0;
                    if (contGio.getTipoOggetto () == BARILE)
                        type = 1;
                    pozioni.add(new Pozione ((int) (contGio.getHitbox().x + contGio.getHitbox().width / 2), (int) (contGio.getHitbox().y - contGio.getHitbox().height / 2), type));
                    return;
                }
            }
        }
    }

    public void caricaOggetti (Livello nuovoLivello)
    {
        pozioni = new ArrayList <> (nuovoLivello.getPozioni ());
        contenitori = new ArrayList <> (nuovoLivello.getContenitori ());
        spuntoni = nuovoLivello.getSpuntoni();
    }

    private void caricaImgs ()
    {
        BufferedImage potionSprite = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_POZIONE);
        imgsPozione = new BufferedImage[2][7];

        for (int j = 0; j < imgsPozione.length; j++)
        {
            for (int i = 0; i < imgsPozione[j].length; i++)
            {
                imgsPozione[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_CONTENITORE);
        imgsContenitore = new BufferedImage[2][8];

        for (int j = 0; j < imgsContenitore.length; j++)
        {
            for (int i = 0; i < imgsContenitore [j].length; i++)
            {
                imgsContenitore[j][i] = containerSprite.getSubimage (40 * i, 30 * j, 40, 30);
            }
        }

        imgSpuntone = CaricaSalva.GetAtltanteSprite (CaricaSalva.ATLANTE_TRAPPOLA);
    }

    public void update ()
    {
        for (Pozione p : pozioni)
        {
            if (p.isAttivo())
            {
                p.update();
            }
        }

        for (ContenitoreGioco contGio : contenitori)
        {
            if (contGio.isAttivo ())
            {
                contGio.update();
            }
        }
    }

    public void draw (Graphics g, int xLvlOffset)
    {
        drawPozioni (g, xLvlOffset);
        drawContenitori (g, xLvlOffset);
        drawTrappole (g, xLvlOffset);
    }

    private void drawTrappole(Graphics g, int xLvlOffset)
    {
        for (Spuntone s : spuntoni)
        {
            g.drawImage (imgSpuntone, (int) (s.getHitbox ().x - xLvlOffset), (int) (s.getHitbox().y - s.getYDrawOffset ()), LARGHEZZA_SPUNTONE, ALTEZZA_SPUNTONE, null);
        }
    }

    private void drawContenitori (Graphics g, int xLvlOffset)
    {
        for (ContenitoreGioco contGio : contenitori)
        {
            if (contGio.isAttivo ())
            {
                int type = 0;

                if (contGio.getTipoOggetto () == BARILE)
                {
                    type = 1;
                }

                g.drawImage(imgsContenitore[type][contGio.getIndiceAni()], (int) (contGio.getHitbox().x - contGio.getXDrawOffset() - xLvlOffset), (int) (contGio.getHitbox().y - contGio.getYDrawOffset()), LARGHEZZA_CONTENITORE, ALTEZZA_CONTENITORE, null);
            }
        }
    }

    private void drawPozioni (Graphics g, int xLvlOffset)
    {
        for (Pozione p : pozioni)
        {
            if (p.isAttivo())
            {
                int type = 0;

                if (p.getTipoOggetto() == POZIONE_ROSSA)
                {
                    type = 1;
                }

                g.drawImage(imgsPozione[type][p.getIndiceAni()], (int) (p.getHitbox().x - p.getXDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getYDrawOffset()), LARGHEZZA_POZIONE, ALTEZZA_POZIONE, null);
            }
        }
    }

    public void resettaTuttiOggetti ()
    {
        caricaOggetti (playing.getGestioneLivello().getLivelloCorrente());

        for (Pozione p : pozioni)
        {
            p.resetta();
        }

        for (ContenitoreGioco contGio : contenitori)
        {
            contGio.resetta();
        }
    }
}
