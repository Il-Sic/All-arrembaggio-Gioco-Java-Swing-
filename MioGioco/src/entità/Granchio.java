package entità;

import static utilità.Costanti.CostantiNemico.*;

public class Granchio extends Nemico
{

    public Granchio (float x, float y)
    {
        super(x, y, LARGHEZZA_GRANCHIO, ALTEZZA_GRANCHIO, GRANCHIO);
    }
}
