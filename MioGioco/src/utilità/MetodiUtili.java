package utilità;

import main.Gioco;

import java.awt.geom.Rectangle2D;

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
        return isSolido(hitbox.x + xVel, hitbox.y + hitbox.height + 1, datiLvl);
    }

    public static boolean isCaselleTutteCalpestabili(int startX, int endX, int y,int[][] datiLvl)
    {
        for (int i = 0; i < endX - startX; i ++)
        {
            if (isCasellaSolida (startX + i, y, datiLvl))
            {
                return false;
            }

            if (!isCasellaSolida (startX + i, y  + 1, datiLvl))
            {
                return false;
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
            return isCaselleTutteCalpestabili (secondaCasellaX, primaCasellaX, casellaY, datiLvl);
        }
        else
        {
            return isCaselleTutteCalpestabili (primaCasellaX, secondaCasellaX, casellaY, datiLvl);
        }
    }
}
