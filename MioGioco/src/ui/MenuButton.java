package ui;

import java.awt.*;
import java.awt.image.BufferedImage;

import statigioco.StatoGioco;
import utilità.CaricaSalva;
import static utilità.Costanti.UI.Buttons.*;


public class MenuButton
{
    private int xPos, yPos, indiceRiga, indice;
    private int xOffsetCentro = B_LARGHEZZA / 2;
    private StatoGioco stato;
    private BufferedImage [] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle limiti;


    public MenuButton (int xPos, int yPos, int indiceRiga, StatoGioco stato)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.indiceRiga = indiceRiga;
        this.stato = stato;

        caricaImmagini ();
        initLimiti ();
    }

    private void initLimiti()
    {
        limiti = new Rectangle (xPos - xOffsetCentro, yPos, B_LARGHEZZA, B_ALTEZZA);
    }

    private void caricaImmagini()
    {
        imgs = new BufferedImage[3];
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.MENU_BUTTONS);

        for (int i = 0; i < imgs.length; i++)
        {
            imgs [i] = temp.getSubimage (i * B_LARGHEZZA_DEFAULT, indiceRiga * B_ALTEZZA_DEFAULT, B_LARGHEZZA_DEFAULT, B_ALTEZZA_DEFAULT);
        }
    }

    public void draw (Graphics g)
    {
        g.drawImage (imgs [indice], xPos - xOffsetCentro, yPos, B_LARGHEZZA, B_ALTEZZA, null);
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

    public Rectangle getLimiti ()
    {
        return limiti;
    }

    public void applicaStatoGioco ()
    {
        StatoGioco.stato = stato;
    }

    public void resetBools ()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public StatoGioco getStato ()
    {
        return stato;
    }
}
