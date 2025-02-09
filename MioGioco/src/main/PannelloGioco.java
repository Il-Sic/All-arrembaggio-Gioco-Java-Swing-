package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static main.Gioco.LARGHEZZA_GIOCO;
import static main.Gioco.ALTEZZA_GIOCO;

public class PannelloGioco extends JPanel
{
    private MouseInputs mouseInputs;
    private Gioco gioco;

    public PannelloGioco (Gioco gioco)
    {
        mouseInputs = new MouseInputs(this);
        this.gioco = gioco;

        setPanelSize ();
        addKeyListener (new KeyboardInputs (this));
        addMouseListener (mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize ()
    {
        Dimension size = new Dimension (LARGHEZZA_GIOCO, ALTEZZA_GIOCO);
        setPreferredSize (size);
        System.out.println ("size: " + LARGHEZZA_GIOCO + " : " + ALTEZZA_GIOCO);
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        gioco.render (g);
    }

    public Gioco getGioco ()
    {
        return gioco;
    }
}
