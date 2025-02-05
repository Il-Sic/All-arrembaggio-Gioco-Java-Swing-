package statigioco;

import entità.GestioneNemico;
import entità.Giocatore;
import livelli.GestioneLivello;
import main.Gioco;
import oggetti.GestoreOggetto;
import ui.OverlayGameOver;
import ui.OverlayLivelloCompletato;
import ui.OverlayPausa;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import static utilità.Costanti.Ambiente.*;

public class Playing extends Stato implements StatoMetodi
{
    private Giocatore giocatore;
    private GestioneNemico gestioneNemico;
    private GestioneLivello gestioneLivello;
    private GestoreOggetto gestoreOggetto;
    private OverlayPausa overlayPausa;
    private OverlayGameOver overlayGameOver;
    private OverlayLivelloCompletato overlayLivelloCompletato;
    private boolean inPausa = false;

    private int xLvlOffset;
    private int bordoSin = (int) (0.2 * Gioco.LARGHEZZA_GIOCO);
    private int bordoDes = (int) (0.8 * Gioco.LARGHEZZA_GIOCO);
//    private int larghezzaCaselleLvl  = CaricaSalva.GetDatiLivello () [0].length;
//    private int maxCaselleOffset = larghezzaCaselleLvl - Gioco.LARGHEZZA_CASELLA;
//    private int maxXLvlOffset = maxCaselleOffset * Gioco.DIMENSIONE_CASELLA;
    private int maxXLvlOffset;

    private BufferedImage immagineBackgound, grandeNuvola, piccolaNuvola;
    private int [] posizionePiccolaNuvola;
    private Random rand = new Random();

    private boolean gameOver = false;
    private boolean lvlCompletato = false;

    public Playing (Gioco gioco) throws URISyntaxException, IOException
    {
        super (gioco);
        initClassi ();

        immagineBackgound = CaricaSalva.GetAtltanteSprite(CaricaSalva.BACKGROUND_IN_GIOCO);
        grandeNuvola = CaricaSalva.GetAtltanteSprite(CaricaSalva.GRANDI_NUVOLE);
        piccolaNuvola = CaricaSalva.GetAtltanteSprite(CaricaSalva.PICCOLE_NUVOLE);

        posizionePiccolaNuvola = new int [8];
        for (int i = 0; i < posizionePiccolaNuvola.length; i++)
        {
            posizionePiccolaNuvola [i] = (int) (90 * Gioco.SCALA) + rand.nextInt ((int) (100 * Gioco.SCALA));
        }

        calcolaLvlOffset ();

        caricaInizioLivello ();
    }

    public void caricaLivelloSuccessivo ()
    {
        resettaTutto ();
        gestioneLivello.caricaLivelloSuccessivo ();
        giocatore.setSpawn (gestioneLivello.getLivelloCorrente ().getSpawnGiocatore ());
    }

    private void caricaInizioLivello()
    {
        gestioneNemico.caricaNemici (gestioneLivello.getLivelloCorrente ());
        gestoreOggetto.caricaOggetti (gestioneLivello.getLivelloCorrente ());
    }

    private void calcolaLvlOffset()
    {
        maxXLvlOffset = gestioneLivello.getLivelloCorrente ().getLvlOffset ();
    }

    private void initClassi () throws URISyntaxException, IOException
    {
        gestioneLivello = new GestioneLivello (gioco);
        gestioneNemico = new GestioneNemico (this);

        giocatore = new Giocatore (200, 200, (int) (64 * Gioco.SCALA), (int) (40 * Gioco.SCALA), this);
        giocatore.caricaDatiLvl (gestioneLivello.getLivelloCorrente ().getDatiLvl ());
        giocatore.setSpawn (gestioneLivello.getLivelloCorrente ().getSpawnGiocatore ());

        overlayPausa = new OverlayPausa (this);
        overlayGameOver = new OverlayGameOver (this);
        overlayLivelloCompletato = new OverlayLivelloCompletato (this);

        gestoreOggetto = new GestoreOggetto (this);
    }

    public void windowFocusLost ()
    {
        giocatore.resetDirBooleans ();              // se ad esempio clicco su una pagina esterna al gioco si ferma il personaggio invece di continuare a muoversi in loop             // se ad esempio clicco su una pagina esterna al gioco si ferma il personaggio invece di continuare a muoversi in loop
    }

    public Giocatore getGiocatore ()
    {
        return giocatore;
    }

    public void riprendiGioco ()
    {
        inPausa = false;
    }

    @Override
    public void update ()
    {
        if (inPausa)
        {
            overlayPausa.update();
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.update();
        }
        else if (!gameOver)
        {
            gestioneLivello.update ();
            gestoreOggetto.update (gestioneLivello.getLivelloCorrente ().getDatiLvl(), giocatore);
            giocatore.update ();
            gestioneNemico.update (gestioneLivello.getLivelloCorrente().getDatiLvl(), giocatore);
            controllaVicinoBordo ();
        }
    }

    private void controllaVicinoBordo ()
    {
        int giocatoreX = (int) giocatore.getHitbox().x;
        int diff = giocatoreX - xLvlOffset;

        if (diff > bordoDes)
        {
            xLvlOffset += diff - bordoDes;
        }
        else if (diff < bordoSin)
        {
            xLvlOffset += diff - bordoSin;
        }

        if (xLvlOffset > maxXLvlOffset)
        {
            xLvlOffset = maxXLvlOffset;
        }
        else if (xLvlOffset < 0)
        {
            xLvlOffset = 0;
        }
    }

    private void drawNuvole (Graphics g)
    {
        for (int i = 0; i < 3; i ++)
        {
            g.drawImage (grandeNuvola, i * LARGHEZZA_GRANDE_NUVOLA - (int) (xLvlOffset * 0.3), (int) (204 * Gioco.SCALA), LARGHEZZA_GRANDE_NUVOLA, ALTEZZA_GRANDE_NUVOLA, null);
        }

        for (int i = 0; i < posizionePiccolaNuvola.length; i ++)
        {
            g.drawImage (piccolaNuvola, LARGHEZZA_PICCOLA_NUVOLA * 4 * i - (int) (xLvlOffset * 0.7), posizionePiccolaNuvola [i],  LARGHEZZA_PICCOLA_NUVOLA, ALTEZZA_PICCOLA_NUVOLA, null);
        }
    }

    @Override
    public void draw (Graphics g)
    {
        g.drawImage(immagineBackgound, 0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO, null);

        drawNuvole(g);

        gestioneLivello.draw(g, xLvlOffset);
        giocatore.render(g, xLvlOffset);
        gestioneNemico.draw(g, xLvlOffset);
        gestoreOggetto.draw(g, xLvlOffset);

        if (inPausa)
        {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);
            overlayPausa.draw(g);
        }
        else if (gameOver)
        {
            overlayGameOver.draw(g);
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.draw (g);
        }
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
        if (!gameOver)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                giocatore.setAttacco(true);
            }
        }
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        if (!gameOver)
        {
            if (inPausa)
            {
                overlayPausa.mousePressed (e);
            }
            else if (lvlCompletato)
            {
                overlayLivelloCompletato.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
        if (!gameOver)
        {
            if (inPausa)
            {
                overlayPausa.mouseReleased (e);
            }
            else if (lvlCompletato)
            {
                overlayLivelloCompletato.mouseReleased (e);
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent e)
    {
        if (!gameOver)
        {
            if (inPausa)
            {
                overlayPausa.mouseMoved (e);
            }
            else if (lvlCompletato)
            {
                overlayLivelloCompletato.mouseMoved (e);
            }
        }
    }

    public void mouseDragged (MouseEvent e)
    {
        if (inPausa)
        {
            overlayPausa.mouseDragged (e);
        }
    }

    @Override
    public void keyPressed (KeyEvent e)
    {
        if (gameOver)
        {
            overlayGameOver.keyPressed (e);
        }
        else
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_A ->
                {
                    giocatore.setSinistra (true);
                }

                case KeyEvent.VK_D ->
                {
                    giocatore.setDestra (true);
                }

                case KeyEvent.VK_SPACE ->
                {
                    giocatore.setSalto (true);
                }

                case KeyEvent.VK_ESCAPE ->
                {
                    inPausa = !inPausa;                             // piccola scappatoia per fare il contrario di quello che è in quel momento
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (!gameOver)
        {
            switch (e.getKeyCode())
            {
                //            case KeyEvent.VK_W ->
                //            {
                //                giocatore.setSopra (false);
                //            }

                case KeyEvent.VK_A ->
                {
                    giocatore.setSinistra (false);
                }

                //            case KeyEvent.VK_S ->
                //            {
                //                giocatore.setSotto (false);
                //            }

                case KeyEvent.VK_D ->
                {
                    giocatore.setDestra (false);
                }


                case KeyEvent.VK_SPACE ->
                {
                    giocatore.setSalto (false);
                }
            }
        }
    }

    public void resettaTutto()
    {
        gameOver = false;
        inPausa = false;
        lvlCompletato = false;
        giocatore.resettaTutto ();
        gestioneNemico.resettaTuttoNemici ();
        gestoreOggetto.resettaTuttiOggetti();
    }

    public void controllaColpoNemico (Rectangle2D.Float attackBox)
    {
        gestioneNemico.controllaColpoNemico (attackBox);
    }

    public void controllaHitOggetto (Rectangle2D.Float attackbox)
    {
        gestoreOggetto.controllaHitOggetto (attackbox);
    }

    public void controllaPozioneToccata (Rectangle2D.Float hitbox)
    {
        gestoreOggetto.controllaOggettoToccato (hitbox);
    }

    public void controllaSpuntoniToccati (Giocatore giocatore)
    {
        gestoreOggetto.controllaSpuntoniToccati (giocatore);
    }

    public void setGameOver (boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public GestioneNemico getGestioneNemico ()
    {
        return gestioneNemico;
    }

    public GestoreOggetto getGestoreOggetto ()
    {
        return gestoreOggetto;
    }

    public GestioneLivello getGestioneLivello ()
    {
        return gestioneLivello;
    }

    public void setMaxLvlOffset (int lvlOffset)
    {
        this.maxXLvlOffset = lvlOffset;
    }

    public void setLivelloCompletato (boolean livelloCompletato)
    {
        this.lvlCompletato = livelloCompletato;
    }
}
