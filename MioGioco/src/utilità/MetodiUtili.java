package utilità;

import main.Gioco;
import oggetti.*;

import java.awt.geom.Rectangle2D;

public class MetodiUtili
{
    public static boolean PuòMuoversiQui (float x, float y, float larghezza, float altezza, int [][] datiLvl)
    {
        if (!IsSolido(x, y, datiLvl))
        {
            if (!IsSolido(x + larghezza, y + altezza, datiLvl))
            {
                if (!IsSolido(x + larghezza, y, datiLvl))
                {
                    return !IsSolido(x, y + altezza, datiLvl);
                }
            }
        }

        return false;
    }

    private static boolean IsSolido(float x, float y, int [][] datiLvl)
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

        return IsCasellaSolida((int) indiceX, (int) indiceY, datiLvl);
    }

    private static boolean IsCasellaSolida(int casellaX, int casellaY, int[][] datiLvl)
    {
        int valore = datiLvl [casellaY][casellaX];

        switch (valore)
        {
            case 11, 48, 49 ->
            {
                return false;
            }

            default ->
            {
                return true;
            }
        }
    }

    public static float GetPosizioneEntitàVicinoAlMuroX(Rectangle2D.Float hitbox, float velX)
    {
        int casellaCorr = (int) (hitbox.x / Gioco.DIMENSIONE_CASELLA);
        if (velX > 0)
        {
            int posXCasella = casellaCorr * Gioco.DIMENSIONE_CASELLA;
            int xOffset = (int) (Gioco.DIMENSIONE_CASELLA - hitbox.width);
            return posXCasella + xOffset - 1;
        }
        else
        {
            return casellaCorr * Gioco.DIMENSIONE_CASELLA;
        }
    }

    public static float GetPosizioneEntitàVicinoAlMuroY(Rectangle2D.Float hitbox, float velAria)
    {
        int casellaCorr = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);
        if (velAria > 0)
        {
            int posYCasella = casellaCorr * Gioco.DIMENSIONE_CASELLA;
            int yOffset = (int) (Gioco.DIMENSIONE_CASELLA - hitbox.height);
            return posYCasella + yOffset - 1;
        }
        else
        {
            return casellaCorr * Gioco.DIMENSIONE_CASELLA;
        }
    }

    public static boolean IsEntitàSulPavimento(Rectangle2D.Float hitbox, int [][] datiLvl)
    {
        if (!IsSolido(hitbox.x, hitbox.y + hitbox.height + 1, datiLvl))
        {
            if (!IsSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean IsPavimento(Rectangle2D.Float hitbox, float xVel, int [][] datiLvl)
    {
        if (xVel > 0)
        {
            return IsSolido(hitbox.x + xVel + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl);
        }
        else
        {
            return IsSolido(hitbox.x + xVel, hitbox.y + hitbox.height + 1, datiLvl);
        }
    }

    public static boolean IsPavimento(Rectangle2D.Float hitbox, int [][] datiLvl)
    {
        if (!IsSolido(hitbox.x, hitbox.y + hitbox.height + 1, datiLvl))
        {
            if (!IsSolido(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, datiLvl))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean SonoCaselleTutteCalpestabili (int xInizio, int xFine, int y, int[][] datiLvl)
    {
        if (SonoTutteCasellePulite (xInizio, xFine, y, datiLvl))
        {
            for (int i = 0; i < xFine - xInizio; i++)
            {
                if (!IsCasellaSolida(xInizio + i, y + 1, datiLvl))
                {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean IsVistaChiara(int [][] datiLvl, Rectangle2D.Float primaHitbox, Rectangle2D.Float secondaHitbox, int casellaY)
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

    public static boolean IsEntitàInAcqua (Rectangle2D.Float hitbox, int[][] datiLvl)
    {
        if (GetValoreCasella(hitbox.x, hitbox.y + hitbox.height, datiLvl) != 48)
        {
            if (GetValoreCasella(hitbox.x + hitbox.width, hitbox.y + hitbox.height, datiLvl) != 48)
            {
                return false;
            }
        }
        return true;
    }

    private static int GetValoreCasella(float x, float y, int[][] datiLvl)
    {
        int xCord = (int) (x / Gioco.DIMENSIONE_CASELLA);
        int yCord = (int) (y / Gioco.DIMENSIONE_CASELLA);
        return datiLvl[yCord][xCord];
    }

    public static boolean SonoTutteCasellePulite (int xStart, int xEnd, int y, int[][] datiLvl)
    {
        for (int i = 0; i < xEnd - xStart; i++)
        {
            if (IsCasellaSolida(xStart + i, y, datiLvl))
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
        return IsSolido(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, datiLvl);
    }
}
