package entità;

import livelli.Livello;
import statigioco.Playing;
import utilità.CaricaSalva;

import static utilità.Costanti.CostantiNemico.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GestoreNemico
{
    private Playing playing;
    private BufferedImage [][] granchioArray, stellaArray, squaloArray;
    private Livello livelloCorrente;

    public GestoreNemico(Playing playing)
    {
        this.playing = playing;

        caricaImmaginiNemico ();
    }

    public void caricaNemici (Livello livello)
    {
        this.livelloCorrente = livello;
    }

    public void update (int [][] datiLvl)
    {
        boolean isAttivoQualcosa = false;

        for (Granchio granchio : livelloCorrente.getGranchi())
        {
            if (granchio.isAttivo ())
            {
                granchio.update(datiLvl, playing);
                isAttivoQualcosa = true;
            } 
        }

        for (Stella stella : livelloCorrente.getStelle())
        {
            if (stella.isAttivo ())
            {
                stella.update(datiLvl, playing);
                isAttivoQualcosa = true;
            }
        }

        for (Squalo squalo : livelloCorrente.getSquali())
        {
            if (squalo.isAttivo ())
            {
                squalo.update(datiLvl, playing);
                isAttivoQualcosa = true;
            }
        }

        if (!isAttivoQualcosa)
        {
            playing.setLivelloCompletato (true);
        }
    }

    public void draw (Graphics g, int xLvlOffset)
    {
        drawGranchi (g, xLvlOffset);
        drawStelle (g, xLvlOffset);
        drawSquali (g, xLvlOffset);
    }

    private void drawSquali(Graphics g, int xLvlOffset)
    {
        for (Squalo squalo : livelloCorrente.getSquali())
        {
            if (squalo.isAttivo())
            {
                g.drawImage(squaloArray[squalo.getStato()][squalo.getIndiceAni()], (int) squalo.getHitbox().x - xLvlOffset - SQUALO_DRAWOFFSET_X + squalo.xFlip(), (int) squalo.getHitbox().y - SQUALO_DRAWOFFSET_Y + (int) squalo.getPushDrawOffset(), LARGHEZZA_SQUALO * squalo.lFlip(), ALTEZZA_SQUALO, null);
            }
        }
    }

    private void drawStelle(Graphics g, int xLvlOffset)
    {
        for (Stella stella : livelloCorrente.getStelle())
        {
            if (stella.isAttivo())
            {
                g.drawImage(stellaArray[stella.getStato()][stella.getIndiceAni()], (int) stella.getHitbox().x - xLvlOffset - STELLA_DRAWOFFSET_X + stella.xFlip(), (int) stella.getHitbox().y - STELLA_DRAWOFFSET_Y + (int) stella.getPushDrawOffset(), LARGHEZZA_STELLA * stella.lFlip(), ALTEZZA_STELLA, null);
            }
        }
    }

    private void drawGranchi(Graphics g, int xLvlOffset)
    {
        for (Granchio granchio : livelloCorrente.getGranchi())
        {
            if (granchio.isAttivo())
            {
                g.drawImage(granchioArray [granchio.getStatoNemico()][granchio.getIndiceAni()], (int) (granchio.getHitbox().x) - xLvlOffset - GRANCHIO_DRAWOFFSET_X + granchio.xFlip(), (int) (granchio.getHitbox().y) - GRANCHIO_DRAWOFFSET_Y, LARGHEZZA_GRANCHIO * granchio.lFlip(), ALTEZZA_GRANCHIO,null);
            }
        }
    }

    public void controllaColpoNemico (Rectangle2D.Float attackBox)
    {
        for (Granchio granchio : livelloCorrente.getGranchi())
        {
            if (granchio.isAttivo())
            {
                if (granchio.getVitaCorrente() > 0)
                {
                    if (attackBox.intersects (granchio.getHitbox()))
                    {
                        granchio.ferisci (10);

                        return;
                    }
                }
            }
        }

        for (Stella stella : livelloCorrente.getStelle())
        {
            if (stella.isAttivo())
            {
                if (stella.getStato() == ATTACCO && stella.getIndiceAni() >= 3)
                {
                    return;
                }
                else
                {
                    if (stella.getStato() != MORTE && stella.getStato() != COLPO)
                    {
                        if (attackBox.intersects(stella.getHitbox()))
                        {
                            stella.ferisci(30);
                            return;
                        }
                    }
                }
            }
        }

        for (Squalo squalo : livelloCorrente.getSquali())
        {
            if (squalo.isAttivo())
            {
                if (squalo.getStato() != MORTE && squalo.getStato() != COLPO)
                {
                    if (attackBox.intersects(squalo.getHitbox()))
                    {
                        squalo.ferisci(20);
                        return;
                    }
                }
            }
        }
    }

    private void caricaImmaginiNemico()
    {
        granchioArray = getImgArr(CaricaSalva.GetAtltanteSprite(CaricaSalva.SPRITE_GRANCHIO), 9, 5, LARGHEZZA_GRANCHIO_DEFAULT, ALTEZZA_GRANCHIO_DEFAULT);
        stellaArray = getImgArr(CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_STELLA), 8, 5, LARGHEZZA_STELLA_DEFAULT, ALTEZZA_STELLA_DEFAULT);
        squaloArray = getImgArr(CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_SQUALO), 8, 5, LARGHEZZA_SQUALO_DEFAULT, ALTEZZA_SQUALO_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH)
    {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];

        for (int j = 0; j < tempArr.length; j++)
        {
            for (int i = 0; i < tempArr[j].length; i++)
            {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        }

        return tempArr;
    }

    public void resettaTuttiNemici ()
    {
        for (Granchio granchio : livelloCorrente.getGranchi())
        {
            granchio.resettaNemico();
        }
        for (Stella stella : livelloCorrente.getStelle())
        {
            stella.resettaNemico();
        }
        for (Squalo squalo : livelloCorrente.getSquali())
        {
            squalo.resettaNemico();
        }
    }
}
