package statigioco;

import main.Gioco;
import ui.OpzioniAudio;
import ui.PauseButton;
import ui.UrmButton;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.UrmButtons.URM_SIZE;

public class OpzioniGioco extends Stato implements StatoMetodi
{
    private OpzioniAudio opzioniAudio;
    private BufferedImage imgBackgound, imgBackgroundOpzioni;
    private int bgX, bgY, bgL, bgA;
    private UrmButton menuB;

    public OpzioniGioco (Gioco gioco)
    {
        super(gioco);
        caricaImgs();
        caricaTasto();
        opzioniAudio = gioco.getOpzioniAudio();
    }

    private void caricaTasto()
    {
        int menuX = (int) (387 * Gioco.SCALA);
        int menuY = (int) (325 * Gioco.SCALA);

        menuB = new UrmButton (menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void caricaImgs ()
    {
        imgBackgound = CaricaSalva.GetAtltanteSprite (CaricaSalva.MENU_SFONDO_FINESTRA);
        imgBackgroundOpzioni = CaricaSalva.GetAtltanteSprite (CaricaSalva.OPZIONI_MENU);

        bgL = (int) (imgBackgroundOpzioni.getWidth() * Gioco.SCALA);
        bgA = (int) (imgBackgroundOpzioni.getHeight() * Gioco.SCALA);
        bgX = Gioco.LARGHEZZA_GIOCO / 2 - bgL / 2;
        bgY = (int) (33 * Gioco.SCALA);
    }

    @Override
    public void update()
    {
        menuB.update();
        opzioniAudio.update();
    }

    @Override
    public void draw (Graphics g)
    {
        g.drawImage (imgBackgound, 0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO, null);
        g.drawImage (imgBackgroundOpzioni, bgX, bgY, bgL, bgA, null);
        menuB.draw (g);
        opzioniAudio.draw (g);
    }

    public void mouseDragged (MouseEvent e)
    {
        opzioniAudio.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (isDentro (e, menuB))
        {
            menuB.setMousePressed(true);
        }
        else
        {
            opzioniAudio.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (isDentro (e, menuB))
        {
            if (menuB.isMousePressed())
            {
                StatoGioco.stato = StatoGioco.MENU;
            }
        }
        else
        {
            opzioniAudio.mouseReleased(e);
        }

        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        menuB.setMouseOver(false);
        if (this.isDentro (e, menuB))
        {
            menuB.setMouseOver(true);
        }
        else
        {
            opzioniAudio.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            StatoGioco.stato = StatoGioco.MENU;
        }
    }

    @Override
    public void keyReleased (KeyEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    private boolean isDentro (MouseEvent e, PauseButton b)
    {
        return b.getLimiti().contains(e.getX(), e.getY());
    }
}
