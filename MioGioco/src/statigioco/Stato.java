package statigioco;

import main.Gioco;
import ui.MenuButton;

import java.awt.event.MouseEvent;

public class Stato
{
    protected Gioco gioco;

    public Stato (Gioco gioco)
    {
        this.gioco = gioco;
    }

    public Gioco getGioco ()
    {
        return gioco;
    }

    public boolean isIn (MouseEvent e, MenuButton mb)
    {
        return mb.getLimiti ().contains (e.getX (), e.getY ());
    }
}
