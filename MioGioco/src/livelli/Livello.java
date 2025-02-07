package livelli;

import entità.Granchio;
import entità.Squalo;
import entità.Stella;
import main.Gioco;
import oggetti.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilità.Costanti.CostantiNemico.*;
import static utilità.Costanti.CostantiOggetto.*;

public class Livello
{
    private BufferedImage img;
    private int[][] datiLvl;

    private ArrayList<Pozione> pozioni = new ArrayList<>();
    private ArrayList<ContenitoreGioco> contenitori = new ArrayList<>();
    private ArrayList<Spuntone> spuntoni = new ArrayList<>();
    private ArrayList<Cannone> cannoni = new ArrayList<>();

    private ArrayList<Granchio> granchi = new ArrayList<>();
    private ArrayList<Stella> stelle = new ArrayList<>();
    private ArrayList<Squalo> squali = new ArrayList<>();
    private ArrayList<AlberiBackground> alberi = new ArrayList<>();
    private ArrayList<Erba> erba = new ArrayList<>();

    private int larghezzaCaselleLvl;
    private int maxCaselleOffset;
    private int maxXLvlOffset;

    private Point spawnGiocatore;

    public Livello(BufferedImage img)
    {
        this.img = img;
        datiLvl = new int[img.getHeight()][img.getWidth()];
        caricaLivello();
        calcolaLvlOffset();
    }

    private void caricaLivello()
    {
        for (int y = 0; y < img.getHeight(); y++)
        {
            for (int x = 0; x < img.getWidth(); x++)
            {
                Color c = new Color(img.getRGB(x, y));

                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                caricaDatiLivello(red, x, y);
                caricaEntità(green, x, y);
                caricaOggetti(blue, x, y);
            }
        }
    }

    private void caricaDatiLivello(int valoreRosso, int x, int y)
    {
        if (valoreRosso >= 50)
        {
            datiLvl[y][x] = 0;
        }
        else
        {
            datiLvl[y][x] = valoreRosso;
        }
        switch (valoreRosso)
        {
            case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 ->
            {
                erba.add(new Erba((int) (x * Gioco.DIMENSIONE_CASELLA), (int) (y * Gioco.DIMENSIONE_CASELLA) - Gioco.DIMENSIONE_CASELLA, getRndGrassType(x)));
            }
        }
    }

    private int getRndGrassType(int xPos)
    {
        return xPos % 2;
    }

    private void caricaEntità (int valoreVerde, int x, int y)
    {
        switch (valoreVerde)
        {
            case GRANCHIO ->
            {
                granchi.add(new Granchio(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA));
            }
            case STELLA ->
            {
                stelle.add(new Stella(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA));
            }
            case SQUALO ->
            {
                squali.add(new Squalo(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA));
            }
            case 100 ->
            {
                spawnGiocatore = new Point(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA);
            }
        }
    }

    private void caricaOggetti(int valoreBlu, int x, int y)
    {
        switch (valoreBlu)
        {
            case POZIONE_ROSSA, POZIONE_BLU ->
            {
                pozioni.add(new Pozione(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA, valoreBlu));
            }
            case CASSA, BARILE ->
            {
                contenitori.add(new ContenitoreGioco(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA, valoreBlu));
            }
            case SPUNTONE ->
            {
                spuntoni.add(new Spuntone(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA, SPUNTONE));
            }
            case CANNONE_SINISTRA, CANNONE_DESTRA ->
            {
                cannoni.add(new Cannone(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA, valoreBlu));
            }
            case ALBERO_UNO, ALBERO_DUE, ALBERO_TRE ->
            {
                alberi.add(new AlberiBackground(x * Gioco.DIMENSIONE_CASELLA, y * Gioco.DIMENSIONE_CASELLA, valoreBlu));
            }
        }
    }

    private void calcolaLvlOffset()
    {
        larghezzaCaselleLvl = img.getWidth();
        maxCaselleOffset = larghezzaCaselleLvl - Gioco.LARGHEZZA_CASELLA;
        maxXLvlOffset = Gioco.DIMENSIONE_CASELLA * maxCaselleOffset;
    }

    public int getIndiceSprite(int x, int y)
    {
        return datiLvl[y][x];
    }

    public int[][] getDatiLvl()
    {
        return datiLvl;
    }

    public int getLvlOffset()
    {
        return maxXLvlOffset;
    }

    public Point getSpawnGiocatore()
    {
        return spawnGiocatore;
    }

    public ArrayList<Granchio> getGranchi()
    {
        return granchi;
    }

    public ArrayList<Squalo> getSquali()
    {
        return squali;
    }

    public ArrayList<Pozione> getPozioni()
    {
        return pozioni;
    }

    public ArrayList<ContenitoreGioco> getContenitori()
    {
        return contenitori;
    }

    public ArrayList<Spuntone> getSpuntoni()
    {
        return spuntoni;
    }

    public ArrayList<Cannone> getCannoni()
    {
        return cannoni;
    }

    public ArrayList<Stella> getStelle()
    {
        return stelle;
    }

    public ArrayList<AlberiBackground> getAlberi()
    {
        return alberi;
    }

    public ArrayList<Erba> getErba()
    {
        return erba;
    }
}
