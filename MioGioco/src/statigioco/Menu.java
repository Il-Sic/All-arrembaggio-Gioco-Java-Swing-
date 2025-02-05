package statigioco;

import main.Gioco;
import ui.MenuButton;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends Stato implements StatoMetodi
{
    private MenuButton [] buttons = new MenuButton [3];
    private BufferedImage backgroundImg, sfondoFinestraMenu;
    private int menuX, menuY, menuLargezza, menuAltezza;

    public Menu (Gioco gioco)
    {
        super (gioco);
        caricaButtons ();
        caricaBackground ();
        sfondoFinestraMenu = CaricaSalva.GetAtltanteSprite (CaricaSalva.MENU_SFONDO_FINESTRA);
    }

    private void caricaBackground ()
    {
        backgroundImg = CaricaSalva.GetAtltanteSprite (CaricaSalva.MENU_BACKGROUND);
        menuLargezza = (int) (backgroundImg.getWidth () * Gioco.SCALA);
        menuAltezza = (int) (backgroundImg.getHeight () * Gioco.SCALA);
        menuX = Gioco.LARGHEZZA_GIOCO / 2 - menuLargezza / 2;
        menuY = (int) (45 * Gioco.SCALA);
    }

    private void caricaButtons ()
    {
        buttons [0] = new MenuButton (Gioco.LARGHEZZA_GIOCO / 2, (int) (150 * Gioco.SCALA), 0, StatoGioco.PLAYING);
        buttons [1] = new MenuButton (Gioco.LARGHEZZA_GIOCO / 2, (int) (220 * Gioco.SCALA), 1, StatoGioco.OPTIONS);
        buttons [2] = new MenuButton (Gioco.LARGHEZZA_GIOCO / 2, (int) (290 * Gioco.SCALA), 2, StatoGioco.QUIT);
    }

    @Override
    public void update ()
    {
        for (MenuButton mb : buttons)
        {
            mb.update ();
        }
    }

    @Override
    public void draw (Graphics g)
    {
        g.drawImage (sfondoFinestraMenu, 0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO, null);

        g.drawImage (backgroundImg, menuX, menuY, menuLargezza, menuAltezza, null);

        for (MenuButton mb : buttons)
        {
            mb.draw (g);
        }
//        g.setColor (Color.black);
//        g.drawString ("MENU", Gioco.LARGHEZZA_GIOCO / 2, 200);
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {

    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        for (MenuButton mb : buttons)
        {
            if (isIn (e, mb))
            {
                mb.setMousePressed (true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        for (MenuButton mb : buttons)
        {
            if (isIn (e, mb))
            {
                if (mb.isMousePressed ())
                {
                    mb.applicaStatoGioco ();
                }

                break;
            }
        }

        resetButtons ();
    }

    private void resetButtons()
    {
        for (MenuButton mb : buttons)
        {
            mb.resetBools ();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        for (MenuButton mb : buttons)
        {
            mb.setMouseOver (false);
        }

        for (MenuButton mb : buttons)
        {
            if (isIn (e, mb))
            {
                mb.setMouseOver (true);
                break;
            }
        }
    }

    @Override
    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode () == KeyEvent.VK_ENTER)
        {
            StatoGioco.stato = StatoGioco.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
