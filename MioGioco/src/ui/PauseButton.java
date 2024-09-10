package ui;

import java.awt.*;

public class PauseButton
{
    protected int x, y, larghezza, altezza;
    protected Rectangle limiti;

    public PauseButton (int x, int y, int larghezza, int altezza)
    {
        this.x = x;
        this.y = y;
        this.larghezza = larghezza;
        this.altezza = altezza;
        creaLimiti ();
    }

    private void creaLimiti ()
    {
        limiti = new Rectangle (x, y, larghezza, altezza);
    }

    public int getX ()
    {
        return x;
    }

    public void setX (int x)
    {
        this.x = x;
    }

    public int getY ()
    {
        return y;
    }

    public void setY (int y)
    {
        this.y = y;
    }

    public int getLarghezza ()
    {
        return larghezza;
    }

    public void setLarghezza (int larghezza)
    {
        this.larghezza = larghezza;
    }

    public int getAltezza ()
    {
        return altezza;
    }

    public void setAltezza (int altezza)
    {
        this.altezza = altezza;
    }

    public Rectangle getLimiti ()
    {
        return limiti;
    }

    public void setLimiti (Rectangle limiti)
    {
        this.limiti = limiti;
    }
}
