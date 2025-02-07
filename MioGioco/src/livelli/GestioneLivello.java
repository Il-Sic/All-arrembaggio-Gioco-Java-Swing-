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
    private BufferedImage [] spriteLivello, spriteAcqua;
    private ArrayList <Livello> livelli;
    private int indiceLvl = 0, tickAni, indiceAni;

    public GestioneLivello (Gioco gioco) throws URISyntaxException, IOException
    {
        this.gioco = gioco;
        importaSpritesEsterni ();
        creaAcqua ();
        livelli = new ArrayList<>();
        costruisciTuttiLivelli ();
    }

    private void creaAcqua()
    {
        spriteAcqua = new BufferedImage[5];
        BufferedImage img = CaricaSalva.GetAtltanteSprite(CaricaSalva.ACQUA_SUPERFICIE);
        for (int i = 0; i < 4; i++)
        {
            spriteAcqua[i] = img.getSubimage(i * 32, 0, 32, 32);
        }
        spriteAcqua[4] = CaricaSalva.GetAtltanteSprite(CaricaSalva.ACQUA_FONDALE);
    }

    public void caricaLivelloSuccessivo ()
    {
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
        for (int j = 0; j < Gioco.ALTEZZA_CASELLA; j++)
        {
            for (int i = 0; i < livelli.get(indiceLvl).getDatiLvl()[0].length; i++)
            {
                int index = livelli.get(indiceLvl).getIndiceSprite(i, j);
                int x = Gioco.DIMENSIONE_CASELLA * i - lvlOffset;
                int y = Gioco.DIMENSIONE_CASELLA * j;

                if (index == 48)
                {
                    g.drawImage(spriteAcqua[indiceAni], x, y, Gioco.DIMENSIONE_CASELLA, Gioco.DIMENSIONE_CASELLA, null);
                }
                else if (index == 49)
                {
                    g.drawImage(spriteAcqua[4], x, y, Gioco.DIMENSIONE_CASELLA, Gioco.DIMENSIONE_CASELLA, null);
                }
                else
                {
                    g.drawImage(spriteLivello[index], x, y, Gioco.DIMENSIONE_CASELLA, Gioco.DIMENSIONE_CASELLA, null);
                }
            }
        }
    }
    public void update ()
    {
        updateAnimazioneAcqua ();
    }

    private void updateAnimazioneAcqua()
    {
        tickAni++;
        if (tickAni >= 40)
        {
            tickAni = 0;
            indiceAni++;

            if (indiceAni >= 4)
            {
                indiceAni = 0;
            }
        }
    }

    public Livello getLivelloCorrente ()
    {
        return livelli.get (indiceLvl);
    }

    public int getNumeroLivelli ()
    {
        return livelli.size ();
    }

    public int getIndiceLivello ()
    {
        return indiceLvl;
    }

    public void setIndiceLivello (int indiceLivello)
    {
        this.indiceLvl = indiceLvl;
    }
}
