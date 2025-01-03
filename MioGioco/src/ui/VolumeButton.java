package ui;

import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.VolumeButtons.*;

public class VolumeButton extends PauseButton
{
    private BufferedImage [] imgs;
    private BufferedImage cursore;
    private int indice = 0;
    private boolean mouseOver, mousePressed;
    private int buttonX, minX, maxX;

    public VolumeButton (int x, int y, int larghezza, int altezza)
    {
        super (x + larghezza / 2, y, VOLUME_LARGEZZA, altezza);
        limiti.x -= VOLUME_LARGEZZA / 2;
        buttonX = x + larghezza / 2;
        this.x = x;                                                                         //
        this.larghezza = larghezza;                                                         //      entrambi servono per il cursore effettivo altrimenti sarebbero cambiate
        minX = x + VOLUME_LARGEZZA / 2;
        maxX = x + larghezza - VOLUME_LARGEZZA / 2;
        caricaImgs ();
    }

    private void caricaImgs ()
    {
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.VOLUME_BUTTONS);
        imgs = new BufferedImage [3];

        for (int i = 0; i < imgs.length; i ++)
        {
            imgs [i] = temp.getSubimage (i * VOLUME_LARGHEZZA_DEFAULT, 0, VOLUME_LARGHEZZA_DEFAULT, VOLUME_ALTEZZA_DEFAULT);
        }

        cursore = temp.getSubimage (3 * VOLUME_LARGHEZZA_DEFAULT, 0, CURSORE_LARGHEZZA_DEFAULT, VOLUME_ALTEZZA_DEFAULT);
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
        g.drawImage (imgs [indice], buttonX - VOLUME_LARGEZZA / 2, y, VOLUME_LARGEZZA, altezza, null);
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

        limiti.x = buttonX;
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
}
