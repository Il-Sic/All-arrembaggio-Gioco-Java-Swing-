package ui;

import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.UrmButtons.*;

public class UrmButton extends PauseButton
{
    private BufferedImage [] imgs;
    private int indice, indiceRiga;
    private boolean mouseOver, mousePressed;

    public UrmButton (int x, int y, int larghezza, int altezza, int indiceRiga)                     // indice riga perchè ci sono 3 tipi di bottoni su diverse righe
    {
        super (x, y, larghezza, altezza);
        this.indiceRiga = indiceRiga;

        caricaImgs ();
    }

    private void caricaImgs ()
    {
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.URM_BUTTONS);
        imgs = new BufferedImage [3];

        for (int i = 0; i < imgs.length; i ++)
        {
            imgs [i] = temp.getSubimage (i * URM_SIZE_DEFAULT, indiceRiga * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
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
        g.drawImage (imgs [indice], x, y, URM_SIZE, URM_SIZE, null);
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
