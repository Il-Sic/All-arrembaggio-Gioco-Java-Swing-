package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.UrmButtons.URM_SIZE;

public class OverlayLivelloCompletato
{
    private Playing playing;
    private UrmButton menu, successivo;
    BufferedImage img;
    private int bgX, bgY, bgL, bgA;

    public OverlayLivelloCompletato (Playing playing)
    {
        this.playing = playing;
        initImg ();
        initButtons ();
    }

    private void initImg()
    {
        img = CaricaSalva.GetAtltanteSprite (CaricaSalva.LIVELLO_COMPLETATO);
        bgL = (int) (img.getWidth() * Gioco.SCALA);
        bgA = (int) (img.getHeight() * Gioco.SCALA);
        bgX = Gioco.LARGHEZZA_GIOCO / 2 - bgL /2;
        bgY = (int) (75 * Gioco.SCALA);
    }

    private void initButtons()
    {
        int menuX = (int) (330 * Gioco.SCALA);
        int successivoX = (int) (445 * Gioco.SCALA);
        int y = (int) (195 * Gioco.SCALA);

        successivo = new UrmButton    (successivoX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton (menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    public void draw (Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);

        g.drawImage (img, bgX, bgY, bgL, bgA, null);

        successivo.draw (g);
        menu.draw (g);
    }

    public void update ()
    {
        successivo.update();
        menu.update();
    }

    public boolean isDentro (UrmButton b, MouseEvent e)
    {
        return b.getLimiti ().contains (e.getX (), e.getY ());
    }

    public void mouseMoved (MouseEvent e)
    {
        successivo.setMouseOver (false);
        menu.setMouseOver (false);

        if (isDentro (successivo, e))
        {
            successivo.setMouseOver (true);
        }
        else if (isDentro (menu, e))
        {
            menu.setMouseOver (true);
        }
    }

    public void mouseReleased (MouseEvent e)
    {
        if (isDentro (menu, e))
        {
            if (menu.isMousePressed())
            {
                playing.resettaTutto ();
                playing.setStato (StatoGioco.MENU);
            }
        }
        else if (isDentro (successivo, e))
        {
            if (successivo.isMousePressed())
            {
                playing.caricaLivelloSuccessivo();
                playing.getGioco().getLettoreAudio().setCanzoneLivello (playing.getGestoreLivello().getIndiceLivello());
            }
        }

        successivo.resetBools();
        menu.resetBools();
    }

    public void mousePressed (MouseEvent e)
    {
        if (isDentro (successivo, e))
        {
            successivo.setMousePressed (true);
        }
        else if (isDentro (menu, e))
        {
            menu.setMousePressed (true);
        }
    }
}
