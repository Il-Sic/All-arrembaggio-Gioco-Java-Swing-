package entità;

import static utilità.Costanti.CostantiNemico.*;

public class Nemico extends Entità
{
    private int indiceAni, statoNemico, tipoNemico;
    private int tickAni, velAni = 25;


    public Nemico(float x, float y, int larghezza, int altezza, int tipoNemico)
    {
        super(x, y, larghezza, altezza);
        this.tipoNemico = tipoNemico;

        initHitBox(x, y, larghezza, altezza);
    }

    private void updateTickAnimazione ()
    {
        tickAni ++;

        if (tickAni >= velAni)
        {
            tickAni = 0;
            tickAni ++;

            if (indiceAni >= GetContSprite(tipoNemico, statoNemico))
            {
                indiceAni = 0;
            }
        }
    }

    public void update ()
    {
        updateTickAnimazione();
    }

    public int getIndiceAni ()
    {
        return indiceAni;
    }

    public int getStatoNemico ()
    {
        return statoNemico;
    }


}
