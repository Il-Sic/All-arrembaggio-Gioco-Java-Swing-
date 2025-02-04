package entità;

import livelli.Livello;
import statigioco.Playing;
import utilità.CaricaSalva;

import static utilità.Costanti.CostantiNemico.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GestioneNemico
{
    private Playing playing;
    private BufferedImage [][] granchioArray;
    private ArrayList <Granchio> granchi = new ArrayList<>();

    public GestioneNemico (Playing playing)
    {
        this.playing = playing;

        caricaImmaginiNemico ();
    }

    public void caricaNemici (Livello livello)
    {
        granchi = livello.getGranchi ();
    }

    public void update (int [][] datiLvl, Giocatore giocatore)
    {
        boolean isAttivoQualcosa = false;

        for (Granchio granchio : granchi)
        {
            if (granchio.isAttivo ())
            {
                granchio.update(datiLvl, giocatore);
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

    }

    private void drawGranchi(Graphics g, int xLvlOffset)
    {
        for (Granchio granchio : granchi)
        {
            if (granchio.isAttivo())
            {
                g.drawImage(granchioArray [granchio.getStatoNemico()][granchio.getIndiceAni()], (int) (granchio.getHitbox().x) - xLvlOffset - GRANCHIO_DRAWOFFSET_X + granchio.xFlip(), (int) (granchio.getHitbox().y) - GRANCHIO_DRAWOFFSET_Y, LARGHEZZA_GRANCHIO * granchio.lFlip(), ALTEZZA_GRANCHIO,null);

//                granchio.drawHitBox(g, xLvlOffset);

//                granchio.drawAttackBox (g, xLvlOffset);
            }
        }
    }

    public void controllaColpoNemico (Rectangle2D.Float attackBox)
    {
        for (Granchio granchio : granchi)
        {
            if (granchio.isAttivo())
            {
                if (attackBox.intersects (granchio.getHitbox()))
                {
                    granchio.ferisci (10);

                    return;
                }
            }
        }
    }

    private void caricaImmaginiNemico()
    {
        granchioArray = new BufferedImage [5][9];
        BufferedImage temp = CaricaSalva.GetAtltanteSprite(CaricaSalva.SPRITE_GRANCHIO);

        for (int j = 0; j < granchioArray.length; j++)
        {
            for (int i = 0; i < granchioArray [j].length; i++)
            {
                granchioArray [j][i]= temp.getSubimage(i * LARGHEZZA_GRANCHIO_DEFAULT , j * ALTEZZA_GRANCHIO_DEFAULT, LARGHEZZA_GRANCHIO_DEFAULT, ALTEZZA_GRANCHIO_DEFAULT);
            }
        }
    }

    public void resettaTuttoNemici()
    {
        for (Granchio granchio : granchi)
        {
            granchio.resettaNemico ();
        }
    }
}
