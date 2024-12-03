package entità;

import statigioco.Playing;
import utilità.CaricaSalva;

import static utilità.Costanti.CostantiNemico.*;

import java.awt.*;
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

        aggiungiNemici ();
    }

    private void aggiungiNemici()
    {
        granchi = CaricaSalva.GetGranchi();
    }

    public void update ()
    {
        for (Granchio granchio : granchi)
        {
            granchio.update();
        }
    }

    public void draw(Graphics g, int xLvlOffset)
    {
        drawGranchi (g, xLvlOffset);
    }

    private void drawGranchi(Graphics g, int xLvlOffset)
    {
        for (Granchio granchio : granchi)
        {
            g.drawImage(granchioArray [granchio.getStatoNemico()][granchio.getIndiceAni()], (int) granchio.getHitBox().x - xLvlOffset, (int) granchio.getHitBox().y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO,null);
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
}
