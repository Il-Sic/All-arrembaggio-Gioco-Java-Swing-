package oggetti;

import main.Gioco;

public class Spuntone extends OggettoGioco
{

    public Spuntone (int x, int y, int tipoOggetto)
    {
        super (x, y, tipoOggetto);

        initHitbox (32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int) (Gioco.SCALA * 16);
        hitbox.y += yDrawOffset;
    }
}
