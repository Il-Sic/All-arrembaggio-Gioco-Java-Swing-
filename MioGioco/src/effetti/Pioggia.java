package effetti;

import main.Gioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Pioggia
{
    private Point2D.Float[] gocce;
    private Random rand;
    private float velPioggia = 1.25f;
    private BufferedImage particellePioggia;

    public Pioggia()
    {
        rand = new Random();
        gocce = new Point2D.Float[1000];
        particellePioggia = CaricaSalva.GetAtltanteSprite(CaricaSalva.PARTICELLE_PIOGGIA);
        initGocce();
    }

    private void initGocce()
    {
        for (int i = 0; i < gocce.length; i++)
        {
            gocce[i] = getRndPos();
        }
    }

    private Point2D.Float getRndPos( )
    {
        return new Point2D.Float((int) getNuovaX(0), rand.nextInt(Gioco.ALTEZZA_GIOCO));
    }

    public void update (int xLvlOffset)
    {
        for (Point2D.Float p : gocce)
        {
            p.y += velPioggia;

            if (p.y >= Gioco.ALTEZZA_GIOCO)
            {
                p.y = -20;
                p.x = getNuovaX(xLvlOffset);
            }
        }
    }

    private float getNuovaX (int xLvlOffset)
    {
        float valore = (-Gioco.LARGHEZZA_GIOCO) + rand.nextInt((int) (Gioco.LARGHEZZA_GIOCO * 3f)) + xLvlOffset;

        return valore;
    }

    public void draw (Graphics g, int xLvlOffset)
    {
        for (Point2D.Float p : gocce)
        {
            g.drawImage(particellePioggia, (int) p.getX() - xLvlOffset, (int) p.getY(), 3, 12, null);
        }
    }
}
