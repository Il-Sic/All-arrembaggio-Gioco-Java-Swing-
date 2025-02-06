package ui;

import main.Gioco;

import java.awt.*;
import java.awt.event.MouseEvent;

import static utilità.Costanti.UI.PauseButtons.SOUND_SIZE;
import static utilità.Costanti.UI.VolumeButtons.ALTEZZA_VOLUME;
import static utilità.Costanti.UI.VolumeButtons.LARGHEZZA_CURSORE;

public class OpzioniAudio
{
    private TastoVolume tastoVolume;
    private TastoSuono tastoMusica, tastoSfx;
    public OpzioniAudio ()
    {
        creaTastoSuono();
        creaTastoVolume();
    }
    private void creaTastoVolume()
    {
        int vX = (int) (309 * Gioco.SCALA);
        int vY = (int) (278 * Gioco.SCALA);
        tastoVolume = new TastoVolume (vX, vY, LARGHEZZA_CURSORE, ALTEZZA_VOLUME);
    }
    private void creaTastoSuono()
    {
        int suonoX = (int) (450 * Gioco.SCALA);
        int musicaY = (int) (140 * Gioco.SCALA);
        int sfxY = (int) (186 * Gioco.SCALA);
        tastoMusica = new TastoSuono (suonoX, musicaY, SOUND_SIZE, SOUND_SIZE);
        tastoSfx = new TastoSuono (suonoX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }
    public void update()
    {
        tastoMusica.update();
        tastoSfx.update();
        tastoVolume.update();
    }
    public void draw (Graphics g)
    {
        tastoMusica.draw(g);
        tastoSfx.draw(g);
        tastoVolume.draw(g);
    }
    public void mouseDragged (MouseEvent e)
    {
        if (tastoVolume.isMousePressed())
        {
            tastoVolume.cambiaX (e.getX());
        }
    }
    public void mousePressed (MouseEvent e)
    {
        if (isDentro (e, tastoMusica))
        {
            tastoMusica.setMousePressed (true);
        }
        else if (isDentro (e, tastoSfx))
        {
            tastoSfx.setMousePressed (true);
        }
        else if (isDentro (e, tastoVolume))
        {
            tastoVolume.setMousePressed (true);
        }
    }
    public void mouseReleased (MouseEvent e)
    {
        if (isDentro(e, tastoMusica))
        {
            if (tastoMusica.isMousePressed())
            {
                tastoMusica.setMuted (!tastoMusica.isMuted());
            }
        }
        else if (isDentro (e, tastoSfx))
        {
            if (tastoSfx.isMousePressed())
            {
                tastoSfx.setMuted (!tastoSfx.isMuted());
            }
        }

        tastoMusica.resetBools();
        tastoSfx.resetBools();
        tastoVolume.resetBools();
    }
    public void mouseMoved(MouseEvent e)
    {
        tastoMusica.setMouseOver(false);
        tastoSfx.setMouseOver(false);
        tastoVolume.setMouseOver(false);
        if (isDentro(e, tastoMusica))
        {
            tastoMusica.setMouseOver(true);
        }
        else if (isDentro(e, tastoSfx))
        {
            tastoSfx.setMouseOver(true);
        }
        else if (isDentro(e, tastoVolume))
        {
            tastoVolume.setMouseOver(true);
        }
    }
    private boolean isDentro(MouseEvent e, PauseButton b)
    {
        return b.getLimiti().contains(e.getX(), e.getY());
    }
}
