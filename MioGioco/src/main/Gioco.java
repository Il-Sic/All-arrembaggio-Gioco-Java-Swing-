package main;

import audio.LettoreAudio;
import statigioco.Menu;
import statigioco.OpzioniGioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import ui.OpzioniAudio;
import utilità.CaricaSalva;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Gioco implements Runnable
{
    private FinestraGioco finestra;
    private PannelloGioco pannello;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private OpzioniGioco opzioniGioco;
    private OpzioniAudio opzioniAudio;
    private LettoreAudio lettoreAudio;

    public static final int DIMENSIONE_PREDEFINITA_CASELLA = 32;
    public static final float SCALA = 2f;
    public static final int LARGHEZZA_CASELLA = 26;
    public static final int ALTEZZA_CASELLA = 14;
    public static final int DIMENSIONE_CASELLA = (int) (DIMENSIONE_PREDEFINITA_CASELLA * SCALA);
    public static final int LARGHEZZA_GIOCO = DIMENSIONE_CASELLA * LARGHEZZA_CASELLA;
    public static final int ALTEZZA_GIOCO = DIMENSIONE_CASELLA * ALTEZZA_CASELLA;

    public Gioco () throws URISyntaxException, IOException
    {
        initClassi();

        pannello = new PannelloGioco (this);
        finestra = new FinestraGioco (pannello);
        pannello.setFocusable (true);
        pannello.requestFocusInWindow ();

        startGameLoop();
    }

    private void initClassi () throws URISyntaxException, IOException
    {
        opzioniAudio = new OpzioniAudio(this);
        lettoreAudio = new LettoreAudio();
        menu = new Menu (this);
        playing = new Playing (this);
        opzioniGioco = new OpzioniGioco (this);
    }

    private void startGameLoop ()
    {
        gameThread = new Thread (this);
        gameThread.start();
    }

    public void update ()
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                playing.update ();
            }

            case MENU ->
            {
                menu.update ();
            }

            case OPTIONS ->
            {
                opzioniAudio.update();
            }

            case QUIT ->
            {
                System.exit (0);
            }

            default ->
            {
                System.exit (0);
            }
        }

    }

    public void render (Graphics g)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                playing.draw (g);
            }

            case OPTIONS ->
            {
                opzioniGioco.draw (g);
            }

            case MENU ->
            {
                menu.draw (g);
            }

            default ->
            {

            }
        }
    }

    @Override
    public void run()
    {
        double tempoPerFrame = 1000000000.0 / FPS_SET;
        double tempoPerUpdate = 1000000000.0 / UPS_SET;

        long tempoPrecedente = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long ultimoCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true)
        {
            long tempoCorrente = System.nanoTime();

            deltaU += (tempoCorrente - tempoPrecedente) / tempoPerUpdate;
            deltaF += (tempoCorrente - tempoPrecedente) / tempoPerFrame;
            tempoPrecedente = tempoCorrente;


            if (deltaU >= 1)
            {
                update ();
                updates ++;
                deltaU --;
            }

            if (deltaF >= 1)
            {
                pannello.repaint();
                frames ++;
                deltaF --;

            }

            if (System.currentTimeMillis() - ultimoCheck >= 1000)
            {
                ultimoCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost ()
    {
        if (StatoGioco.stato == StatoGioco.PLAYING)
        {
            playing.getGiocatore ().resetDirBooleans ();
        }
    }

    public Menu getMenu ()
    {
        return menu;
    }

    public Playing getPlaying ()
    {
        return playing;
    }

    public OpzioniAudio getOpzioniAudio ()
    {
        return opzioniAudio;
    }

    public OpzioniGioco getOpzioniGioco ()
    {
        return opzioniGioco;
    }

    public LettoreAudio getLettoreAudio ()
    {
        return lettoreAudio;
    }
}
