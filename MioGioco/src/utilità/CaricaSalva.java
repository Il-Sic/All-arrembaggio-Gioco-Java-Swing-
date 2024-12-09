package utilità;

import entità.Granchio;
import main.Gioco;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utilità.Costanti.CostantiNemico.GRANCHIO;

public class CaricaSalva
{
    public static final String ALTLANTE_GIOCATORE = "player_sprites.png";
    public static final String ALTLANTE_LIVELLO = "outside_sprites.png";
    //  public static final String DATI_LIVELLO_1 = "level_one_data.png";
    public static final String DATI_LIVELLO_1 = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_SFONDO_FINESTRA = "Designer-5.png";
    public static final String PAUSA_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String SPRITE_GRANCHIO = "granchio_sprite.png";
    public static final String BACKGROUND_IN_GIOCO = "background_in_gioco.png";
    public static final String PICCOLE_NUVOLE = "piccole_nuvole.png";
    public static final String GRANDI_NUVOLE = "grandi_nuvole.png";

    public static BufferedImage GetAtltanteSprite (String nomeFile)                        // restituisce un immagine memorizzata nel buffer
    {
        BufferedImage img = null;
        InputStream is = CaricaSalva.class.getResourceAsStream ("/" + nomeFile);

        try
        {
            img = ImageIO.read (is);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close ();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return img;
    }

    public static ArrayList <Granchio> GetGranchi ()
    {
        BufferedImage img = GetAtltanteSprite (DATI_LIVELLO_1);
        ArrayList <Granchio> lista = new ArrayList <> ();

        for (int j = 0; j < img.getHeight(); j ++)
        {
            for (int i = 0; i < img.getWidth(); i ++)
            {
                Color color = new Color (img.getRGB (i, j));
                int valore = color.getGreen();
                if (valore == GRANCHIO)
                {
                    lista.add (new Granchio (i * Gioco.DIMENSIONE_CASELLA, j * Gioco.DIMENSIONE_CASELLA));
                }
            }
        }

        return lista;
    }

    public static int [][] GetDatiLivello ()
    {
        BufferedImage img = GetAtltanteSprite (DATI_LIVELLO_1);

        int [][] datiLvl = new int [img.getHeight ()][img.getWidth ()];

        for (int j = 0; j < img.getHeight(); j ++)
        {
            for (int i = 0; i < img.getWidth(); i ++)
            {
                Color color = new Color (img.getRGB (i, j));
                int valore = color.getRed();
                if (valore >= 48)
                {
                    valore = 0;
                }
                datiLvl [j][i] = valore;
            }
        }

        return datiLvl;
    }
}
