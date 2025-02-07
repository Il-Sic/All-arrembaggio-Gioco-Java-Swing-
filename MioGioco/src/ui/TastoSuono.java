package ui;

import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilità.Costanti.UI.PauseButtons.*;

public class TastoSuono extends PauseButton
{
    private BufferedImage [][] soundImgs;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private int indiceRiga, indiceColonna;

    public TastoSuono(int x, int y, int larghezza, int altezza)
    {
        super (x, y, larghezza, altezza);

        caricaSoundImgs ();
    }

    private void caricaSoundImgs ()
    {
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.SOUND_BUTTONS);
        soundImgs = new BufferedImage [2][3];

        for (int i = 0; i < soundImgs.length; i ++)
        {
            for (int j = 0; j < soundImgs [i].length; j ++)
            {
                soundImgs [i][j] = temp.getSubimage (j * SOUND_SIZE_DEFAULT, i * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update ()
    {
        if (muted)
        {
            indiceRiga = 1;
        }
        else
        {
            indiceRiga = 0;
        }

        indiceColonna = 0;

        if (mouseOver)
        {
            indiceColonna = 1;
        }

        if (mousePressed)
        {
            indiceColonna = 2;
        }
    }

    public void draw (Graphics g)
    {
        g.drawImage (soundImgs [indiceRiga][indiceColonna], x, y, larghezza, altezza,null);
    }

    public boolean isMuted ()
    {
        return muted;
    }

    public void setMuted (boolean muted)
    {
        this.muted = muted;
    }

    public boolean isMousePressed ()
    {
        return mousePressed;
    }

    public void setMousePressed (boolean mousePressed)
    {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver ()
    {
        return mouseOver;
    }

    public void setMouseOver (boolean mouseOver)
    {
        this.mouseOver = mouseOver;
    }

    public void resetBools ()
    {
        mouseOver = false;
        mousePressed = false;
    }
}
