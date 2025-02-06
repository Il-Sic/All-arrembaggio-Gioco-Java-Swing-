package inputs;

import entità.Giocatore;
import main.PannelloGioco;
import statigioco.StatoGioco;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener
{
    private PannelloGioco pannello;

    public MouseInputs (PannelloGioco pannello)
    {
        this.pannello = pannello;
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().mouseClicked (e);
            }
        }
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().mousePressed (e);
            }
            case OPTIONS ->
            {
                pannello.getGioco ().getOpzioniGioco ().mousePressed (e);
            }
            case MENU ->
            {
                pannello.getGioco ().getMenu ().mousePressed (e);
            }
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().mouseReleased (e);
            }
            case OPTIONS ->
            {
                pannello.getGioco ().getOpzioniGioco ().mouseReleased (e);
            }
            case MENU ->
            {
                pannello.getGioco ().getMenu ().mouseReleased (e);
            }
        }
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {

    }

    @Override
    public void mouseExited (MouseEvent e)
    {

    }

    @Override
    public void mouseDragged (MouseEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().mouseDragged (e);
            }
            case OPTIONS ->
            {
                pannello.getGioco().getOpzioniGioco ().mouseDragged (e);
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent e)
    {
        switch (StatoGioco.stato)
        {
            case PLAYING ->
            {
                pannello.getGioco ().getPlaying ().mouseMoved (e);
            }
            case OPTIONS ->
            {
                pannello.getGioco ().getOpzioniGioco ().mouseMoved (e);
            }
            case MENU ->
            {
                pannello.getGioco ().getMenu ().mouseMoved (e);
            }
        }
        //pannello.getGioco().getGiocatore().setRectPos (e.getX (), e.getY());
    }
}
