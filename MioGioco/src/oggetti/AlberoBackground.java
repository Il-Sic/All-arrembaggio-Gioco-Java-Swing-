package oggetti;

import java.util.Random;

public class AlberoBackground
{
    private int x, y, tipo, indiceAni, tickAni;

    public AlberoBackground(int x, int y, int tipo)
    {
        this.x = x;
        this.y = y;
        this.tipo = tipo;

        Random r = new Random();
        indiceAni = r.nextInt(4);
    }

    public void update()
    {
        tickAni++;

        if (tickAni >= 35)
        {
            tickAni = 0;
            indiceAni++;

            if (indiceAni >= 4)
            {
                indiceAni = 0;
            }
        }
    }

    public int getIndiceAni ()
    {
        return indiceAni;
    }

    public void setIndiceAni (int indiceAni)
    {
        this.indiceAni = indiceAni;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getTipo()
    {
        return tipo;
    }

    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }
}
