package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.UrmButtons.*;

public class OverlayPausa
{
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgL, bgA;
    private OpzioniAudio opzioniAudio;
    private UrmButton menuB, rigiocaB, riprendiB;
    private TastoVolume tastoVolume;


    public OverlayPausa (Playing playing)
    {
        this.playing = playing;
        caricaBackground ();
        opzioniAudio = playing.getGioco().getOpzioniAudio();
        creaUrmButtons ();
    }

    private void creaUrmButtons ()
    {
        int menuX = (int) (313 * Gioco.SCALA);
        int rigiocaX = (int) (387 * Gioco.SCALA);
        int ripendiX = (int) (462 * Gioco.SCALA);
        int bY = (int) (325 * Gioco.SCALA);

        menuB = new UrmButton (menuX, bY, URM_SIZE, URM_SIZE, 2);
        rigiocaB = new UrmButton (rigiocaX, bY, URM_SIZE, URM_SIZE, 1);
        riprendiB = new UrmButton (ripendiX, bY, URM_SIZE, URM_SIZE, 0);
    }

    private void caricaBackground ()
    {
        backgroundImg = CaricaSalva.GetAtltanteSprite (CaricaSalva.PAUSA_BACKGROUND);
        bgL = (int) (backgroundImg.getWidth () * Gioco.SCALA);
        bgA = (int) (backgroundImg.getHeight () * Gioco.SCALA);
        bgX = Gioco.LARGHEZZA_GIOCO / 2 - bgL / 2;
        bgY = (int) (25 * Gioco.SCALA);
    }

    public void update ()
    {

        menuB.update ();
        rigiocaB.update ();
        riprendiB.update ();

        opzioniAudio.update();
    }

    public void draw (Graphics g)
    {
        // Background
        g.drawImage (backgroundImg, bgX, bgY, bgL, bgA, null);

        // Urm buttons
        menuB.draw (g);
        rigiocaB.draw (g);
        riprendiB.draw (g);

        opzioniAudio.draw (g);
    }

    public void mouseDragged (MouseEvent e)
    {
        opzioniAudio.mouseDragged (e);
    }

    public void mouseClicked (MouseEvent e)
    {

    }

    public void mousePressed (MouseEvent e)
    {
        if (isDentro (e, menuB))
        {
            menuB.setMousePressed (true);
        }
        else if (isDentro (e, rigiocaB))
        {
            rigiocaB.setMousePressed (true);
        }
        else if (isDentro (e, riprendiB))
        {
            riprendiB.setMousePressed (true);
        }
        else if (isDentro (e, tastoVolume))
        {
            tastoVolume.setMousePressed (true);
        }
        else
        {
            opzioniAudio.mousePressed (e);
        }
    }

    public void mouseReleased (MouseEvent e)
    {
        if (isDentro (e, menuB))
        {
            if (menuB.isMousePressed ())
            {
                playing.resettaTutto();
                playing.setStato (StatoGioco.MENU);
                playing.riprendiGioco();
            }
        }
        else if (isDentro (e, rigiocaB))
        {
            if (rigiocaB.isMousePressed ())
            {
                playing.resettaTutto  ();
                playing.riprendiGioco ();
            }
        }
        else if (isDentro (e, riprendiB))
        {
            if (riprendiB.isMousePressed ())
            {
                playing.riprendiGioco ();
            }
        }
        else
        {
            opzioniAudio.mouseReleased(e);
        }

        menuB.resetBools ();
        rigiocaB.resetBools ();
        riprendiB.resetBools ();
    }

    public void mouseMoved(MouseEvent e)
    {
        menuB.setMouseOver (false);
        rigiocaB.setMouseOver (false);
        riprendiB.setMouseOver (false);
        tastoVolume.setMouseOver (false);

        if (isDentro (e, menuB))
        {
            menuB.setMouseOver (true);
        }
        else if (isDentro (e, rigiocaB))
        {
            rigiocaB.setMouseOver (true);
        }
        else if (isDentro (e, riprendiB))
        {
            riprendiB.setMouseOver (true);
        }
        else if (isDentro (e, tastoVolume))
        {
            tastoVolume.setMouseOver (true);
        }
        else
        {
            opzioniAudio.mouseMoved (e);
        }
    }

    private boolean isDentro(MouseEvent e, PauseButton b)
    {
        return b.getLimiti ().contains (e.getX (), e.getY ());
    }
}
