package livelli;

public class Livello
{
    private int [][] datiLvl;

    public Livello (int [][] datiLvl)
    {
        this.datiLvl = datiLvl;
    }

    public int getIndiceSprite (int x, int y)
    {
        return datiLvl [y][x];
    }

    public int [][] getDatiLvl ()
    {
        return datiLvl;
    }

}
