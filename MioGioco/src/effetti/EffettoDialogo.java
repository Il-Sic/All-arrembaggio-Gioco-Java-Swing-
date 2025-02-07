package effetti;

import static utilità.Costanti.VEL_ANI;
import static utilità.Costanti.Dialogo.*;

public class EffettoDialogo
{
    private int x, y, tipo;
    private int indiceAni, tickAni;
    private boolean attivo = true;

    public EffettoDialogo (int x, int y, int tipo)
    {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
    }

    public void update()
    {
        tickAni++;

        if (tickAni >= VEL_ANI)
        {
            tickAni = 0;
            indiceAni++;

            if (indiceAni >= GetSpriteCont (tipo))
            {
                attivo = false;
                indiceAni = 0;
            }
        }
    }

    public void disattiva ()
    {
        attivo = false;
    }

    public void resetta (int x, int y)
    {
        this.x = x;
        this.y = y;
        attivo = true;
    }

    public int getIndiceAni()
    {
        return indiceAni;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getTipo()
    {
        return tipo;
    }

    public boolean isAttivo()
    {
        return attivo;
    }
}
