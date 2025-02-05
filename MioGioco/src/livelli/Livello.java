package livelli;

import entità.Granchio;
import main.Gioco;
import oggetti.Cannone;
import oggetti.ContenitoreGioco;
import oggetti.Pozione;
import oggetti.Spuntone;
import utilità.MetodiUtili;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilità.MetodiUtili.GetDatiLivello;
import static utilità.MetodiUtili.GetGranchi;
import static utilità.MetodiUtili.GetSpawnGiocatore;

public class Livello
{
    private BufferedImage img;
    private int [][] datiLvl;
    private ArrayList <Granchio> granchi;

    private ArrayList <Pozione> pozioni;
    private ArrayList <ContenitoreGioco> contenitori;
    private ArrayList <Spuntone> spuntoni;
    private ArrayList <Cannone> cannoni;


    private int larghezzaCaselleLvl;
    private int maxCaselleOffset ;
    private int maxXLvlOffset;

    private Point spawnGiocatore;

    public Livello (BufferedImage img)
    {
        this.img = img;

        creaDatiLivello ();
        creaNemici ();
        creaPozioni ();
        creaContenitori ();
        creaSpuntoni ();
        creaCannoni ();
        calcolaLvlOffset ();
        calcolaSpawnGiocatore ();
    }

    private void creaCannoni()
    {
        cannoni = MetodiUtili.GetCannoni (img);
    }

    private void creaSpuntoni()
    {
        spuntoni = MetodiUtili.GetSpuntoni (img);
    }

    private void creaContenitori()
    {
        contenitori = MetodiUtili.GetContenitori (img);
    }

    private void creaPozioni()
    {
        pozioni = MetodiUtili.GetPozioni (img);
    }

    private void creaDatiLivello()
    {
        datiLvl = GetDatiLivello (img);
    }

    private void creaNemici()
    {
        granchi = GetGranchi (img);
    }

    private void calcolaLvlOffset()
    {
        larghezzaCaselleLvl = img.getWidth ();

        maxCaselleOffset = larghezzaCaselleLvl - Gioco.LARGHEZZA_CASELLA;

        maxXLvlOffset = Gioco.DIMENSIONE_CASELLA * maxCaselleOffset;
    }

    public void calcolaSpawnGiocatore ()
    {
        spawnGiocatore = GetSpawnGiocatore (img);
    }

    public int getIndiceSprite (int x, int y)
    {
        return datiLvl [y][x];
    }

    public int [][] getDatiLvl ()
    {
        return datiLvl;
    }

    public int getLvlOffset ()
    {
        return maxXLvlOffset;
    }

    public ArrayList <Granchio> getGranchi ()
    {
        return granchi;
    }

    public Point getSpawnGiocatore ()
    {
        return spawnGiocatore;
    }

    public ArrayList <Pozione> getPozioni ()
    {
        return pozioni;
    }

    public ArrayList <ContenitoreGioco> getContenitori ()
    {
        return contenitori;
    }

    public ArrayList <Spuntone> getSpuntoni ()
    {
        return spuntoni;
    }

    public ArrayList <Cannone> getCannoni ()
    {
        return cannoni;
    }
}
