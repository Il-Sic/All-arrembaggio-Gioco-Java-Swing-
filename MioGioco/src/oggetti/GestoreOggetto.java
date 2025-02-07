package oggetti;

import entità.Giocatore;
import livelli.Livello;
import main.Gioco;
import statigioco.Playing;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilità.Costanti.CostantiOggetto.*;
import static utilità.Costanti.Proiettili.ALTEZZA_PALLA_CANNONE;
import static utilità.Costanti.Proiettili.LARGHEZZA_PALLA_CANNONE;
import static utilità.MetodiUtili.IsProiettileColpisceLivello;
import static utilità.MetodiUtili.PuòCannoneVedereGiocatore;

public class GestoreOggetto
{
    private Playing playing;
    private BufferedImage [][] imgsPozione, imgsContenitore, imgsAlberi;
    private BufferedImage imgSpuntone, imgPallaCannone;
    private BufferedImage [] imgsCannone, imgsErba;

    private ArrayList <Pozione> pozioni;
    private ArrayList <ContenitoreGioco> contenitori;
    private ArrayList <Proiettile> proiettili = new ArrayList<>();

    private Livello livelloCorrente;

    public GestoreOggetto (Playing playing)
    {
        this.playing = playing;
        livelloCorrente = playing.getGestioneLivello().getLivelloCorrente();

        caricaImgs ();
    }

    public void controllaSpuntoniToccati (Giocatore giocatore)
    {
        for (Spuntone s : livelloCorrente.getSpuntoni())
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
                    {
                        type = 1;
                    }

                    pozioni.add(new Pozione ((int) (contGio.getHitbox().x + contGio.getHitbox().width / 2), (int) (contGio.getHitbox().y - contGio.getHitbox().height / 2), type));
                    return;
                }
            }
        }
    }

    public void caricaOggetti (Livello nuovoLivello)
    {
        livelloCorrente = nuovoLivello;
        pozioni = new ArrayList <> (nuovoLivello.getPozioni ());
        contenitori = new ArrayList <> (nuovoLivello.getContenitori ());
        proiettili.clear();
    }

    private void caricaImgs ()
    {
        BufferedImage potionSprite = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_POZIONE);
        imgsPozione = new BufferedImage [2][7];

        for (int j = 0; j < imgsPozione.length; j++)
        {
            for (int i = 0; i < imgsPozione[j].length; i++)
            {
                imgsPozione[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_CONTENITORE);
        imgsContenitore = new BufferedImage [2][8];

        for (int j = 0; j < imgsContenitore.length; j++)
        {
            for (int i = 0; i < imgsContenitore [j].length; i++)
            {
                imgsContenitore [j][i] = containerSprite.getSubimage (40 * i, 30 * j, 40, 30);
            }
        }

        imgSpuntone = CaricaSalva.GetAtltanteSprite (CaricaSalva.ATLANTE_TRAPPOLA);

        imgsCannone = new BufferedImage [7];
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.ATLANTE_CANNONE);

        for (int i = 0; i < imgsCannone.length; i ++)
        {
            imgsCannone [i] = temp.getSubimage (40 * i, 0, 40, 26);
        }

        imgPallaCannone = CaricaSalva.GetAtltanteSprite (CaricaSalva.PALLA_CANNONE);

        imgsAlberi = new BufferedImage[2][4];
        BufferedImage imgAlberoUno = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_ALBERO_UNO);
        for (int i = 0; i < 4; i++)
        {
            imgsAlberi[0][i] = imgAlberoUno.getSubimage(i * 39, 0, 39, 92);
        }

        BufferedImage imgAlberoDue = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_ALBERO_DUE);
        for (int i = 0; i < 4; i++)
        {
            imgsAlberi[1][i] = imgAlberoDue.getSubimage(i * 62, 0, 62, 54);
        }

        BufferedImage grassTemp = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_ERBA);
        imgsErba = new BufferedImage[2];
        for (int i = 0; i < imgsErba.length; i++)
        {
            imgsErba[i] = grassTemp.getSubimage(32 * i, 0, 32, 32);
        }
    }

    public void update (int [][] datiLvl, Giocatore giocatore)
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

        updateCannoni (datiLvl, giocatore);
        updateProiettili (datiLvl, giocatore);
    }

    private void updateAlberiBackground ()
    {
        for (AlberoBackground aB : livelloCorrente.getAlberi ())
        {
            aB.update();
        }
    }

    private void updateProiettili (int[][] datiLvl, Giocatore giocatore)
    {
        for (Proiettile proiettile : proiettili)
        {
            if (proiettile.isAttivo())
            {
                proiettile.updatePos();

                if (proiettile.getHitbox().intersects(giocatore.getHitbox()))
                {
                    giocatore.cambiaVita(-25);
                    proiettile.setAttivo(false);
                } else if (IsProiettileColpisceLivello (proiettile, datiLvl))
                {
                    proiettile.setAttivo(false);
                }
            }
        }
    }

    private boolean isGiocatoreNelRange (Cannone cannone, Giocatore giocatore)
    {
        int valoreAssoluto = (int) Math.abs (giocatore.getHitbox().x - cannone.getHitbox().x);

        return valoreAssoluto <= Gioco.DIMENSIONE_CASELLA * 5;
    }

    private boolean isGiocatoreDifronteCannone (Cannone cannone, Giocatore giocatore)
    {
        if (cannone.getTipoOggetto () == CANNONE_SINISTRA)
        {
            if (cannone.getHitbox().x > giocatore.getHitbox().x)
            {
                return true;
            }
        }
        else if (cannone.getHitbox().x < giocatore.getHitbox().x)
        {
            return true;
        }

        return false;
    }

    private void updateCannoni (int[][] datiLvl, Giocatore giocatore)
    {
        for (Cannone cannone : livelloCorrente.getCannoni())
        {
            if (!cannone.faiAnimazione)
            {
                if (cannone.getCasellaY() == giocatore.getCasellaY())
                {
                    if (isGiocatoreNelRange (cannone, giocatore))
                    {
                        if (isGiocatoreDifronteCannone (cannone, giocatore))
                        {
                            if (PuòCannoneVedereGiocatore(datiLvl, giocatore.getHitbox(), cannone.getHitbox(), cannone.getCasellaY()))
                            {
                                cannone.setAnimazione(true);
                            }
                        }
                    }
                }
            }

            cannone.update();

            if (cannone.getIndiceAni() == 4 && cannone.getTickAni() == 0)
            {
                sparoCannone (cannone);
            }
        }
    }
    private void sparoCannone (Cannone cannone)
    {
        int dir = 1;

        if (cannone.getTipoOggetto() == CANNONE_SINISTRA)
        {
            dir = -1;
        }

        proiettili.add (new Proiettile ((int) cannone.getHitbox().x, (int) cannone.getHitbox().y, dir));
    }

    public void draw (Graphics g, int xLvlOffset)
    {
        drawPozioni (g, xLvlOffset);
        drawContenitori (g, xLvlOffset);
        drawTrappole (g, xLvlOffset);
        drawCannoni (g, xLvlOffset);
        drawProiettili (g, xLvlOffset);
        drawErba (g, xLvlOffset);
    }

    private void drawErba(Graphics g, int xLvlOffset)
    {
        for (Erba erba : livelloCorrente.getErba())
        {
            g.drawImage(imgsErba[erba.getTipo()], erba.getX() - xLvlOffset, erba.getY(), (int) (32 * Gioco.SCALA), (int) (32 * Gioco.SCALA), null);
        }
    }

    public void drawAlberiBackground (Graphics g, int xLvlOffset)
    {
        for (AlberoBackground aB : livelloCorrente.getAlberi ())
        {

            int tipo = aB.getTipo();
            if (tipo == 9)
            {
                tipo = 8;
            }
            g.drawImage(imgsAlberi[tipo - 7][aB.getIndiceAni()], aB.getX() - xLvlOffset + GetAlberoOffsetX(aB.getTipo()), (int) (aB.getY() + GetAlberoOffsetY(aB.getTipo())), GetLarghezzaAlbero(aB.getTipo()), GetAltezzaAlbero(aB.getTipo()), null);
        }
    }

    private void drawProiettili(Graphics g, int xLvlOffset)
    {
        for (Proiettile proiettile : proiettili)
        {
            if (proiettile.isAttivo())
            {
                g.drawImage (imgPallaCannone, (int) (proiettile.getHitbox().x - xLvlOffset), (int) (proiettile.getHitbox().y), LARGHEZZA_PALLA_CANNONE, ALTEZZA_PALLA_CANNONE, null);
            }
        }
    }

    private void drawCannoni (Graphics g, int xLvlOffset)
    {
        for (Cannone c : livelloCorrente.getCannoni())
        {
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int larghezza = LARGHEZZA_CANNONE;

            if (c.getTipoOggetto() == CANNONE_DESTRA)
            {
                x += larghezza;
                larghezza *= -1;
            }

            g.drawImage(imgsCannone[c.getIndiceAni()], x, (int) (c.getHitbox().y), larghezza, ALTEZZA_CANNONE, null);
        }
    }

    private void drawTrappole (Graphics g, int xLvlOffset)
    {
        for (Spuntone s : livelloCorrente.getSpuntoni())
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
