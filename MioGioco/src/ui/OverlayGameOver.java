package ui;

import main.Gioco;
import statigioco.Playing;
import statigioco.StatoGioco;

import java.awt.*;
import java.awt.event.KeyEvent;

public class OverlayGameOver
{
    private Playing playing;

    public OverlayGameOver (Playing playing)
    {
        this.playing = playing;
    }

    public void draw(Graphics g)
    {
        g.setColor (new Color (0, 0, 0, 200));
        g.fillRect (0, 0, Gioco.ALTEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);

        g.setColor (Color.white);
        g.drawString ("Game Over", Gioco.LARGHEZZA_GIOCO / 2, 150);
        g.drawString ("Premi 'esc' per tornare al 'Menu Iniziale!'", Gioco.LARGHEZZA_GIOCO / 2, 300);

    }

    public void keyPressed (KeyEvent e)
    {
        if (e.getKeyCode () == KeyEvent.VK_ESCAPE)
        {
            playing.resettaTutto ();
            StatoGioco.stato = StatoGioco.MENU;
        }

    }
}
