package ui;

import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.VolumeButtons.*;

public class TastoVolume extends PauseButton
{
    private BufferedImage [] imgs;
    private BufferedImage cursore;
    private int indice = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;
    private float valoreFloat = 0f;

    public TastoVolume(int x, int y, int larghezza, int altezza)
    {
        super (x + larghezza / 2, y, LARGHEZZA_VOLUME, altezza);
        limiti.x -= LARGHEZZA_VOLUME / 2;
        buttonX = x + larghezza / 2;
        this.x = x;
        this.larghezza = larghezza;
        minX = x + LARGHEZZA_VOLUME / 2;
        maxX = x + larghezza - LARGHEZZA_VOLUME / 2;
        caricaImgs ();
    }

    private void caricaImgs ()
    {
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.VOLUME_BUTTONS);
        imgs = new BufferedImage [3];

        for (int i = 0; i < imgs.length; i ++)
        {
            imgs [i] = temp.getSubimage (i * LARGHEZZA_VOLUME_DEFAULT, 0, LARGHEZZA_VOLUME_DEFAULT, ALTEZZA_VOLUME_DEFAULT);
        }

        cursore = temp.getSubimage (3 * LARGHEZZA_VOLUME_DEFAULT, 0, LARGHEZZA_CURSORE_DEFAULT, ALTEZZA_VOLUME_DEFAULT);
    }

    public void update ()
    {
        indice = 0;

        if (mouseOver)
        {
            indice = 1;
        }

        if (mousePressed)
        {
            indice = 2;
        }
    }

    public void draw (Graphics g)
    {
        g.drawImage (cursore, x, y, larghezza, altezza, null);
        g.drawImage (imgs [indice], buttonX - LARGHEZZA_VOLUME / 2, y, LARGHEZZA_VOLUME, altezza, null);
    }

    public void cambiaX (int x)
    {
        if (x < minX)
        {
            buttonX = minX;
        }
        else if (x > maxX)
        {
            buttonX = maxX;
        }
        else
        {
            buttonX = x;
        }

        updateValoreFloat();
        limiti.x = buttonX - LARGHEZZA_VOLUME / 2;
    }

    private void updateValoreFloat()
    {
        float range = maxX - minX;
        float valore = buttonX - minX;
        valoreFloat = valore / range;
    }

    public void resetBools ()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public boolean isMouseOver ()
    {
        return mouseOver;
    }

    public void setMouseOver (boolean mouseOver)
    {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed ()
    {
        return mousePressed;
    }

    public void setMousePressed (boolean mousePressed)
    {
        this.mousePressed = mousePressed;
    }

    public float getValoreFloat ()
    {
        return valoreFloat;
    }
}
