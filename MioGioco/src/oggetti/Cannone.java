package oggetti;

import main.Gioco;

public class Cannone extends OggettoGioco
{
    private int casellaY;

    public Cannone (int x, int y, int objType)
    {
        super(x, y, objType);

        casellaY = y / Gioco.DIMENSIONE_CASELLA;

        initHitbox(40, 26);

        hitbox.x -= (int) (4 * Gioco.SCALA);
        hitbox.y += (int) (6 * Gioco.SCALA);
    }
    public void update ()
    {
        if (faiAnimazione)
        {
            updateTickAnimazione ();
        }
    }
    public int getCasellaY ()
    {
        return casellaY;
    }
}
