package utilità;

import main.Gioco;

import java.awt.geom.Rectangle2D;

public class MetodiUtili
{
    public static boolean PuòMuoversiQui (float x, float y, float larghezza, float altezza, int [][] datiLvl)
    {
        if (!IsSolido (x, y, datiLvl))
        {
            if (!IsSolido (x + larghezza, y + altezza, datiLvl))
            {
                if (!IsSolido (x + larghezza, y, datiLvl))
                {
                    if (!IsSolido (x, y + altezza, datiLvl))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean IsSolido (float x, float y, int [][] datiLvl)
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

        int valore = datiLvl [(int) indiceY][(int) indiceX];

        if (valore >= 48 || valore < 0 || valore != 11)
        {
            return  true;
        }

        return false;
    }

    public static float GetXPosEntitàVicinoAlMuro (Rectangle2D.Float hitbox, float velX)
    {
        int casellaCorr = (int) hitbox.x / Gioco.DIMENSIONE_CASELLA;
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

    public static float GetYPosEntitàVicinoAlMuro (Rectangle2D.Float hitbox, float velAria)
    {
        int casellaCorr = (int) hitbox.y / Gioco.DIMENSIONE_CASELLA;
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

    public static boolean EntitàSulPavimento (Rectangle2D.Float hitbox, int [][] datiLvl)
    {
        // Controlla il pixel vicino alla parte inferiore sinistra e destra
        if (!IsSolido (hitbox.x, hitbox.y + hitbox.height + 1, datiLvl))
        {
            if (!IsSolido (hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl))
            {
                return false;
            }
        }
        return true;
    }
}
