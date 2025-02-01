package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import utilità.CaricaSalva;
import utilità.Costanti;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.PauseButtons.*;
import static utilità.Costanti.UI.UrmButtons.*;
import static utilità.Costanti.UI.VolumeButtons.*;

public class OverlayPausa
{
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgL, bgA;                         // x, y, larghezza e altezza background
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, rigiocaB, riprendiB;
    private VolumeButton volumeButton;


    public OverlayPausa (Playing playing)
    {
        this.playing = playing;
        caricaBackground ();
        creaSoundButtons ();
        creaUrmButtons ();
        creaVolumeButton ();
    }

    private void creaVolumeButton ()
    {
        int vX = (int) (309 * Gioco.SCALA);
        int vY = (int) (278 * Gioco.SCALA);
        volumeButton = new VolumeButton (vX, vY, CURSORE_LARGHEZZA, VOLUME_ALTEZZA);
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

    private void creaSoundButtons ()
    {
        int soundX = (int) (450 * Gioco.SCALA);
        int musicY = (int) (140 * Gioco.SCALA);
        int sfxY = (int) (186 * Gioco.SCALA);
        musicButton = new SoundButton (soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton (soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
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
        musicButton.update ();
        sfxButton.update ();

        menuB.update ();
        rigiocaB.update ();
        riprendiB.update ();

        volumeButton.update ();
    }

    public void draw (Graphics g)
    {
        // Background
        g.drawImage (backgroundImg, bgX, bgY, bgL, bgA, null);

        // Sound buttons
        musicButton.draw (g);
        sfxButton.draw (g);

        // Urm buttons
        menuB.draw (g);
        rigiocaB.draw (g);
        riprendiB.draw (g);

        // Cursore volume
        volumeButton.draw (g);
    }

    public void mouseDragged (MouseEvent e)
    {
        if (volumeButton.isMousePressed ())
        {
            volumeButton.cambiaX (e.getX ());
        }
    }

    public void mouseClicked (MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        if (isIn (e, musicButton))
        {
            musicButton.setMousePressed (true);
        }
        else if (isIn (e, sfxButton))
        {
            sfxButton.setMousePressed (true);
        }
        else if (isIn (e, menuB))
        {
            menuB.setMousePressed (true);
        }
        else if (isIn (e, rigiocaB))
        {
            rigiocaB.setMousePressed (true);
        }
        else if (isIn (e, riprendiB))
        {
            riprendiB.setMousePressed (true);
        }
        else if (isIn (e, volumeButton))
        {
            volumeButton.setMousePressed (true);
        }
    }

    public void mouseReleased (MouseEvent e)
    {
        if (isIn (e, musicButton))
        {
           if (musicButton.isMousePressed ())
           {
               musicButton.setMuted (!musicButton.isMuted ());
           }
        }
        else if (isIn (e, sfxButton))
        {
            if (sfxButton.isMousePressed ())
            {
                sfxButton.setMuted (!sfxButton.isMuted ());
            }
        }
        else if (isIn (e, menuB))
        {
            if (menuB.isMousePressed ())
            {
                StatoGioco.stato = StatoGioco.MENU;
                playing.riprendiGioco ();
            }
        }
        else if (isIn (e, rigiocaB))
        {
            if (rigiocaB.isMousePressed ())
            {
                playing.resettaTutto  ();
                playing.riprendiGioco ();
            }
        }
        else if (isIn (e, riprendiB))
        {
            if (riprendiB.isMousePressed ())
            {
                playing.riprendiGioco ();
            }
        }

        musicButton.resetBools ();
        sfxButton.resetBools ();
        menuB.resetBools ();
        rigiocaB.resetBools ();
        riprendiB.resetBools ();
        volumeButton.resetBools ();
    }

    public void mouseMoved(MouseEvent e)
    {
        musicButton.setMouseOver (false);
        sfxButton.setMouseOver (false);
        menuB.setMouseOver (false);
        rigiocaB.setMouseOver (false);
        riprendiB.setMouseOver (false);
        volumeButton.setMouseOver (false);

        if (isIn (e, musicButton))
        {
            musicButton.setMouseOver (true);
        }
        else if (isIn (e, sfxButton))
        {
            sfxButton.setMouseOver (true);
        }
        else if (isIn (e, menuB))
        {
            menuB.setMouseOver (true);
        }
        else if (isIn (e, rigiocaB))
        {
            rigiocaB.setMouseOver (true);
        }
        else if (isIn (e, riprendiB))
        {
            riprendiB.setMouseOver (true);
        }
        else if (isIn (e, volumeButton))
        {
            volumeButton.setMouseOver (true);
        }
    }

    private boolean isIn (MouseEvent e, PauseButton b)
    {
        return b.getLimiti ().contains (e.getX (), e.getY ());

    }

}
