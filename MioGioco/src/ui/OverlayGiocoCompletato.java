package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class OverlayGiocoCompletato
{
    private Playing playing;
    private BufferedImage img;
    private MenuButton esci;
    private int imgX, imgY, imgL, imgA;

    public OverlayGiocoCompletato (Playing playing)
    {
        this.playing = playing;
        createImg();
        createButtons();
    }

    private void createButtons()
    {
        esci = new MenuButton(Gioco.LARGHEZZA_GIOCO / 2, (int) (270 * Gioco.SCALA), 2, StatoGioco.MENU);
    }

    private void createImg()
    {
        img = CaricaSalva.GetAtltanteSprite(CaricaSalva.GIOCO_COMPLETATO);
        imgL = (int) (img.getWidth() * Gioco.SCALA);
        imgA = (int) (img.getHeight() * Gioco.SCALA);
        imgX = Gioco.LARGHEZZA_GIOCO / 2 - imgL / 2;
        imgY = (int) (100 * Gioco.SCALA);
    }

    public void draw (Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);

        g.drawImage(img, imgX, imgY, imgL, imgA, null);

        esci.draw(g);
    }

    public void update()
    {
        esci.update();
    }

    private boolean isIn(MenuButton b, MouseEvent e)
    {
        return b.getLimiti().contains(e.getX(), e.getY());
    }

    public void mouseMoved (MouseEvent e)
    {
        esci.setMouseOver(false);

        if (isIn(esci, e))
            esci.setMouseOver(true);
    }

    public void mouseReleased (MouseEvent e)
    {
        if (isIn(esci, e))
        {
            if (esci.isMousePressed())
            {
                playing.resettaTutto();
                playing.resettaGiocoCompletato();
                playing.setStato(StatoGioco.MENU);

            }
        }

        esci.resetBools();
    }

    public void mousePressed(MouseEvent e)
    {
        if (isIn (esci, e))
        {
            esci.setMousePressed(true);
        }
    }
}
