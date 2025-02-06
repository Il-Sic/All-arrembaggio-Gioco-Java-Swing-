package utilità;

import entità.Granchio;
import main.Gioco;
import oggetti.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilità.Costanti.CostantiNemico.GRANCHIO;
import static utilità.Costanti.CostantiOggetto.*;

public class MetodiUtili
{
    public static boolean puòMuoversiQui(float x, float y, float larghezza, float altezza, int [][] datiLvl)
    {
        if (!isSolido(x, y, datiLvl))
        {
            if (!isSolido(x + larghezza, y + altezza, datiLvl))
            {
                if (!isSolido(x + larghezza, y, datiLvl))
                {
                    return !isSolido(x, y + altezza, datiLvl);
                }
            }
        }

        return false;
    }

    private static boolean isSolido(float x, float y, int [][] datiLvl)
    {
        int larghezzaMax = datiLvl [0].length * Gioco.DIMENSIONE_CASELLA;

        if (x < 0 || x >= larghezzaMax)
        {
            return true;
        }
        else if (y < 0 || y >= Gioco.ALTEZZA_GIOCO)
        {
            return true;
        }

        float indiceX = x / Gioco.DIMENSIONE_CASELLA;
        float indiceY = y / Gioco.DIMENSIONE_CASELLA;

        return isCasellaSolida ((int) indiceX, (int) indiceY, datiLvl);
    }

    private static boolean isCasellaSolida (int casellaX, int casellaY, int[][] datiLvl)
    {
        int valore = datiLvl [casellaY][casellaX];

        if (valore >= 48 || valore < 0 || valore != 11)
        {
            return  true;
        }

        return false;
    }

    public static float getPosizioneEntitàVicinoAlMuroX(Rectangle2D.Float hitbox, float velX)
    {
        int casellaCorr = (int) (hitbox.x / Gioco.DIMENSIONE_CASELLA);
        if (velX > 0)
        {
            // è a destra
            int posXCasella = casellaCorr * Gioco.DIMENSIONE_CASELLA;
            int xOffset = (int) (Gioco.DIMENSIONE_CASELLA - hitbox.width);
            return posXCasella + xOffset - 1;
        }
        else
        {
            // è a sinistra
            return casellaCorr * Gioco.DIMENSIONE_CASELLA;
        }
    }

    public static float getPosizioneEntitàVicinoAlMuroY(Rectangle2D.Float hitbox, float velAria)
    {
        int casellaCorr = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);
        if (velAria > 0)
        {
            // è in caduta - toccando il terreno
            int posYCasella = casellaCorr * Gioco.DIMENSIONE_CASELLA;
            int yOffset = (int) (Gioco.DIMENSIONE_CASELLA - hitbox.height);
            return posYCasella + yOffset - 1;
        }
        else
        {
            // sta saltando
            return casellaCorr * Gioco.DIMENSIONE_CASELLA;
        }
    }

    public static boolean isEntitàSulPavimento(Rectangle2D.Float hitbox, int [][] datiLvl)
    {
        // Controlla il pixel vicino alla parte inferiore sinistra e destra
        if (!isSolido(hitbox.x, hitbox.y + hitbox.height + 1, datiLvl))
        {
            if (!isSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isPavimento(Rectangle2D.Float hitbox, float xVel, int [][] datiLvl)
    {
        if (xVel > 0)
        {
            return isSolido(hitbox.x + xVel + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl);
        }
        else
        {
            return isSolido(hitbox.x + xVel, hitbox.y + hitbox.height + 1, datiLvl);
        }
    }

    public static boolean SonoCaselleTutteCalpestabili (int xInizio, int xFine, int y, int[][] datiLvl)
    {
        if (SonoTutteCasellePulite (xInizio, xFine, y, datiLvl))
        {
            for (int i = 0; i < xFine - xInizio; i++)
            {
                if (!isCasellaSolida (xInizio + i, y + 1, datiLvl))
                {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isVistaChiara (int [][] datiLvl, Rectangle2D.Float primaHitbox, Rectangle2D.Float secondaHitbox, int casellaY)
    {
        int primaCasellaX = (int) (primaHitbox.x / Gioco.DIMENSIONE_CASELLA);
        int secondaCasellaX = (int) (secondaHitbox.x / Gioco.DIMENSIONE_CASELLA);

        if (primaCasellaX > secondaCasellaX)
        {
            return SonoCaselleTutteCalpestabili(secondaCasellaX, primaCasellaX, casellaY, datiLvl);
        }
        else
        {
            return SonoCaselleTutteCalpestabili(primaCasellaX, secondaCasellaX, casellaY, datiLvl);
        }
    }

    public static boolean SonoTutteCasellePulite (int xStart, int xEnd, int y, int[][] lvlData)
    {
        for (int i = 0; i < xEnd - xStart; i++)
        {
            if (isCasellaSolida (xStart + i, y, lvlData))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean PuòCannoneVedereGiocatore (int[][] datiLvl, Rectangle2D.Float primaHitbox, Rectangle2D.Float secondaHitbox, int casellaY)
    {
        int primaXTile = (int) (primaHitbox.x / Gioco.DIMENSIONE_CASELLA);
        int secondaXTile = (int) (secondaHitbox.x / Gioco.DIMENSIONE_CASELLA);

        if (primaXTile > secondaXTile)
        {
            return MetodiUtili.SonoTutteCasellePulite (secondaXTile, primaXTile, casellaY, datiLvl);
        }
        else
        {
            return SonoTutteCasellePulite (primaXTile, secondaXTile, casellaY, datiLvl);
        }
    }

    public static boolean IsProiettileColpisceLivello (Proiettile p, int[][] datiLvl)
    {
        return isSolido (p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, datiLvl);
    }


    public static int [][] GetDatiLivello (BufferedImage img)
    {
        int [][] datiLvl = new int [img.getHeight ()][img.getWidth ()];

        for (int j = 0; j < img.getHeight(); j ++)
        {
            for (int i = 0; i < img.getWidth(); i ++)
            {
                Color color = new Color (img.getRGB (i, j));
                int valore = color.getRed();
                if (valore >= 48)
                {
                    valore = 0;
                }
                datiLvl [j][i] = valore;
            }
        }

        return datiLvl;
    }

    public static ArrayList<Granchio> GetGranchi (BufferedImage img)
    {
        ArrayList <Granchio> lista = new ArrayList <> ();

        for (int j = 0; j < img.getHeight(); j ++)
        {
            for (int i = 0; i < img.getWidth(); i ++)
            {
                Color color = new Color (img.getRGB (i, j));
                int valore = color.getGreen();
                if (valore == GRANCHIO)
                {
                    lista.add (new Granchio (i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA));
                }
            }
        }

        return lista;
    }

    public static Point GetSpawnGiocatore (BufferedImage img)
    {
        for (int j = 0; j < img.getHeight (); j ++)
        {
            for (int i = 0; i < img.getWidth (); i ++)
            {
                Color color = new Color (img.getRGB (i, j));
                int valore = color.getGreen();

                if (valore == 100)
                {
                    return new Point (i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA);
                }
            }
        }

        return new Point (1 * Gioco.DIMENSIONE_CASELLA, 1 * Gioco.DIMENSIONE_CASELLA);
    }

    public static ArrayList <Pozione> GetPozioni (BufferedImage img)
    {
        ArrayList <Pozione> pozioni = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int valore = color.getBlue();

                if (valore == POZIONE_ROSSA || valore == POZIONE_BLU)
                {
                    pozioni.add(new Pozione (i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA, valore));
                }
            }
        }

        return pozioni;
    }
    public static ArrayList <ContenitoreGioco> GetContenitori (BufferedImage img)
    {
        ArrayList <ContenitoreGioco> contenitori = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int valore = color.getBlue();

                if (valore == CASSA || valore == BARILE)
                {
                    contenitori.add(new ContenitoreGioco(i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA, valore));
                }
            }
        }
        return contenitori;
    }

    public static ArrayList <Spuntone> GetSpuntoni (BufferedImage img)
    {

        ArrayList <Spuntone> spuntoni = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int valore = color.getBlue();

                if (valore == SPUNTONE)
                {
                    spuntoni.add (new Spuntone(i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA, SPUNTONE));
                }
            }
        }

        return spuntoni;
    }

    public static ArrayList <Cannone> GetCannoni (BufferedImage img)
    {
        ArrayList <Cannone> cannoni = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int valore = color.getBlue();

                if (valore == CANNONE_SINISTRA || valore == CANNONE_DESTRA)
                {
                    cannoni.add(new Cannone(i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA, valore));
                }
            }
        }

        return cannoni;
    }
}
