package inputs;

import main.PannelloGioco;
import statigioco.StatoGioco;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener
{
    private PannelloGioco pannello;
    public KeyboardInputs (PannelloGioco pannello)
    {
        this.pannello = pannello;
    }
    @Override
    public void keyTyped (KeyEvent e)
    {

    }

    @Override
    public void keyPressed (KeyEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().keyPressed (e);
            }
            case OPTIONS ->
            {
                pannello.getGioco ().getOpzioniGioco ().keyPressed (e);
            }
            case MENU ->
            {
                pannello.getGioco ().getMenu ().keyPressed (e);
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().keyReleased (e);
            }
            case MENU ->
            {
                pannello.getGioco ().getMenu ().keyReleased (e);
            }
        }
    }
}
