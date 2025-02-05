package livelli;

import main.Gioco;
import statigioco.StatoGioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GestioneLivello
{
    private Gioco gioco;
    private BufferedImage [] spriteLivello;
    private ArrayList <Livello> livelli;
    private int indiceLvl = 0;

    public GestioneLivello (Gioco gioco) throws URISyntaxException, IOException
    {
        this.gioco = gioco;
        importaSpritesEsterni ();
        livelli = new ArrayList<>();
        costruisciTuttiLivelli ();
    }

    public void caricaLivelloSuccessivo ()
    {
        indiceLvl ++;

        if (indiceLvl >= livelli.size())
        {
            indiceLvl = 0;
            System.out.println("Hai finito il gioco! Complimenti!");
            StatoGioco.stato = StatoGioco.MENU;
        }

        Livello nuovoLivello = livelli.get (indiceLvl);
        gioco.getPlaying ().getGestioneNemico ().caricaNemici (nuovoLivello);
        gioco.getPlaying ().getGiocatore ().caricaDatiLvl (nuovoLivello.getDatiLvl ());
        gioco.getPlaying ().setMaxLvlOffset (nuovoLivello.getLvlOffset ());
        gioco.getPlaying ().getGestoreOggetto ().caricaOggetti (nuovoLivello);
    }

    private void costruisciTuttiLivelli() throws URISyntaxException, IOException
    {
        BufferedImage [] tuttiLivelli = CaricaSalva.GetTuttiLivelli();

        for (BufferedImage img : tuttiLivelli)
        {
            livelli.add (new Livello (img));
        }
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
            for (int i = 0; i < livelli.get (indiceLvl).getDatiLvl () [0].length; i ++)
            {
                int indice = livelli.get (indiceLvl).getIndiceSprite (i, j);
                g.drawImage (spriteLivello [indice], Gioco.DIMENSIONE_CASELLA * i - lvlOffset, Gioco.DIMENSIONE_CASELLA * j, Gioco.DIMENSIONE_CASELLA, Gioco.DIMENSIONE_CASELLA, null);
            }
        }
    }
    public void update ()
    {
    }

    public Livello getLivelloCorrente ()
    {
        return livelli.get (indiceLvl);
    }

    public int getNumeroLivelli ()
    {
        return livelli.size ();
    }
}
