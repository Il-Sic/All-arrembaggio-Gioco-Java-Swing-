package statigioco;

import audio.LettoreAudio;
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

    public boolean isDentro(MouseEvent e, MenuButton mb)
    {
        return mb.getLimiti ().contains (e.getX (), e.getY ());
    }

    public void setStato (StatoGioco stato)
    {
        switch (stato)
        {
            case MENU -> gioco.getLettoreAudio().riproduciCanzone(LettoreAudio.MENU_1);
            case PLAYING -> gioco.getLettoreAudio().setCanzoneLivello(gioco.getPlaying().getGestoreLivello().getIndiceLivello());
        }
        StatoGioco.stato = stato;
    }
}
