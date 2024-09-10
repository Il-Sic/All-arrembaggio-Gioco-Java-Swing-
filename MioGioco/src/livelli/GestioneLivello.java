package livelli;

import main.Gioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GestioneLivello
{
    private Gioco gioco;
    private BufferedImage [] spriteLivello;
    private Livello livelloUno;

    public GestioneLivello (Gioco gioco)
    {
        this.gioco = gioco;
        //spriteLivello = CaricaSalva.GetAtltanteSprites (CaricaSalva.ALTLANTE_LIVELLO);
        importaSpritesEsterni ();
        livelloUno = new Livello (CaricaSalva.GetDatiLivello());
    }

    private void importaSpritesEsterni()
    {
        BufferedImage img = CaricaSalva.GetAtltanteSprite(CaricaSalva.ALTLANTE_LIVELLO);
        spriteLivello = new BufferedImage [48];

        for (int j = 0; j < 4; j ++)
        {
            for (int i = 0; i < 12; i ++)
            {
                int indice = j * 12 + i;
                spriteLivello [indice] = img.getSubimage (i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw (Graphics g, int lvlOffset)
    {
        for (int j = 0; j < Gioco.ALTEZZA_CASELLA; j ++)
        {
            for (int i = 0; i < livelloUno.getDatiLvl () [0].length; i ++)
            {
                int indice = livelloUno.getIndiceSprite (i, j);
                g.drawImage (spriteLivello [indice], Gioco.DIMENSIONE_CASELLA * i - lvlOffset, Gioco.DIMENSIONE_CASELLA * j, Gioco.DIMENSIONE_CASELLA, Gioco.DIMENSIONE_CASELLA, null);
            }
        }
    }
    public void update ()
    {
    }

    public Livello getLivelloCorrente ()
    {
        return livelloUno;
    }
}
