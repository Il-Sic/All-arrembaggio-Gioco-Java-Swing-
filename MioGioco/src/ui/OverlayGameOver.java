package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.UrmButtons.URM_SIZE;

public class OverlayGameOver
{
    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgL, imgA;
    private UrmButton menu, gioca;

    public OverlayGameOver (Playing playing)
    {
        this.playing = playing;

        creaImg();
        creaButtons();
    }

    private void creaButtons()
    {
        int menuX = (int) (335 * Gioco.SCALA);
        int playX = (int) (440 * Gioco.SCALA);
        int y = (int) (195 * Gioco.SCALA);

        gioca = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void creaImg()
    {
        img = CaricaSalva.GetAtltanteSprite(CaricaSalva.SCHERMATA_MORTE);
        imgL = (int) (img.getWidth() * Gioco.SCALA);
        imgA = (int) (img.getHeight() * Gioco.SCALA);
        imgX = Gioco.LARGHEZZA_GIOCO / 2 - imgL / 2;
        imgY = (int) (100 * Gioco.SCALA);
    }

    public void draw (Graphics g)
    {
        g.setColor (new Color (0, 0, 0, 200));
        g.fillRect (0, 0, Gioco.ALTEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);

        g.drawImage(img, imgX, imgY, imgL, imgA, null);

        menu.draw (g);
        gioca.draw (g);
    }

    public void update ()
    {
        menu.update();
        gioca.update();
    }

    public void keyPressed (KeyEvent e)
    {

    }

    private boolean isDentro (UrmButton b, MouseEvent e)
    {
        return b.getLimiti().contains(e.getX(), e.getY());
    }

    public void mouseMoved (MouseEvent e)
    {
        gioca.setMouseOver (false);
        menu.setMouseOver (false);

        if (isDentro(menu, e))
        {
            menu.setMouseOver (true);
        }

        else if (isDentro(gioca, e))
        {
            gioca.setMouseOver (true);
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (isDentro (menu, e))
        {
            if (menu.isMousePressed())
            {
                playing.resettaTutto ();
                StatoGioco.stato = StatoGioco.MENU;
            }
        }
        else if (isDentro (gioca, e))
        {
            if (gioca.isMousePressed ())
            {
                playing.resettaTutto ();
                playing.getGioco().getLettoreAudio().setCanzoneLivello(playing.getGestioneLivello().getIndiceLivello());
            }
        }

        menu.resetBools();
        gioca.resetBools();
    }

    public void mousePressed (MouseEvent e)
    {
        if (isDentro (menu, e))
        {
            menu.setMousePressed(true);
        }

        else if (isDentro (gioca, e))
        {
            gioca.setMousePressed(true);
        }
    }
}
