package oggetti;

import main.Gioco;

public class Pozione extends OggettoGioco
{
    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;

    public Pozione (int x, int y, int tipoOggetto)
    {
        super(x, y, tipoOggetto);
        faiAnimazione = true;

        initHitbox(7, 14);

        xDrawOffset = (int) (3 * Gioco.SCALA);
        yDrawOffset = (int) (2 * Gioco.SCALA);

        maxHoverOffset = (int) (10 * Gioco.SCALA);
    }

    public void update ()
    {
        updateTickAnimazione ();
        updateHover ();
    }

    private void updateHover ()
    {
        hoverOffset += (0.075f * Gioco.SCALA * hoverDir);

        if (hoverOffset >= maxHoverOffset)
        {
            hoverDir = -1;
        }
        else if (hoverOffset < 0)
        {
            hoverDir = 1;
        }

        hitbox.y = y + hoverOffset;
    }
}
