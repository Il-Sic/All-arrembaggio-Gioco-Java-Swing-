package oggetti;

import main.Gioco;

import static utilità.Costanti.CostantiOggetto.CASSA;

public class ContenitoreGioco extends OggettoGioco
{
    public ContenitoreGioco (int x, int y, int tipoOggetto)
    {
        super (x, y, tipoOggetto);

        createHitbox ();
    }

    private void createHitbox ()
    {
        if (tipoOggetto == CASSA)
        {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Gioco.SCALA);
            yDrawOffset = (int) (12 * Gioco.SCALA);

        }
        else
        {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Gioco.SCALA);
            yDrawOffset = (int) (5 * Gioco.SCALA);
        }

        hitbox.y += yDrawOffset + (int) (Gioco.SCALA * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update()
    {
        if (faiAnimazione)
        {
            updateTickAnimazione ();
        }
    }
}
