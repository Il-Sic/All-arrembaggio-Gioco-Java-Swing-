package statigioco;

import entità.Giocatore;
import livelli.GestioneLivello;
import main.Gioco;
import ui.OverlayPausa;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends Stato implements StatoMetodi
{
    private Giocatore giocatore;
    private GestioneLivello gestioneLivello;
    private OverlayPausa overlayPausa;
    private boolean inPausa = false;

    private int xLvlOffset;
    private int bordoSin = (int) (0.2 * Gioco.LARGHEZZA_GIOCO);
    private int bordoDes = (int) (0.8 * Gioco.LARGHEZZA_GIOCO);
    private int larghezzaCaselleLvl  = CaricaSalva.GetDatiLivello () [0].length;
    private int maxCaselleOffset = larghezzaCaselleLvl - Gioco.LARGHEZZA_CASELLA;
    private int maxXLvlOffset = maxCaselleOffset * Gioco.DIMENSIONE_CASELLA;

    public Playing (Gioco gioco)
    {
        super (gioco);
        initClassi ();
    }

    private void initClassi ()
    {
        gestioneLivello = new GestioneLivello (gioco);
        giocatore = new Giocatore (200, 200, (int) (64 * Gioco.SCALA), (int) (40 * Gioco.SCALA));
        giocatore.caricaDatiLvl (gestioneLivello.getLivelloCorrente().getDatiLvl());
        overlayPausa = new OverlayPausa (this);
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
        if (!inPausa)
        {
            gestioneLivello.update ();
            giocatore.update ();
            controllaVicinoBordo ();
        }
        else
        {
            overlayPausa.update ();
        }
    }

    private void controllaVicinoBordo ()
    {
        int giocatoreX = (int) giocatore.getHitBox ().x;
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

    @Override
    public void draw (Graphics g)
    {
        gestioneLivello.draw (g, xLvlOffset);
        giocatore.render (g, xLvlOffset);

        if (inPausa)
        {
            g.setColor (new Color (0, 0, 0, 100));
            g.fillRect (0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);
            overlayPausa.draw (g);
        }
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            giocatore.setAttacco(true);
        }
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        if (inPausa)
        {
            overlayPausa.mousePressed (e);
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
        if (inPausa)
        {
            overlayPausa.mouseReleased (e);
        }
    }

    @Override
    public void mouseMoved (MouseEvent e)
    {
        if (inPausa)
        {
            overlayPausa.mouseMoved (e);
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
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_W ->
            {
                giocatore.setSopra (true);
            }

            case KeyEvent.VK_A ->
            {
                giocatore.setSinistra (true);
            }

            case KeyEvent.VK_S ->
            {
                giocatore.setSotto (true);
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

    @Override
    public void keyReleased(KeyEvent e)
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
