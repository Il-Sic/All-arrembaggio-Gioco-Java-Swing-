package utilità;

import main.Gioco;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CaricaSalva
{
    public static final String ALTLANTE_GIOCATORE = "player_sprites.png";
    public static final String ALTLANTE_LIVELLO = "outside_sprites.png";
//  public static final String DATI_LIVELLO_1 = "level_one_data.png";
    public static final String DATI_LIVELLO_1 = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_SFONDO_FINESTRA = "sfondo_finestra_menu.png";
    public static final String PAUSA_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";

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

    public static int [][] GetDatiLivello ()
    {
        BufferedImage img = GetAtltanteSprite (DATI_LIVELLO_1);

        int [][] datiLvl = new int [img.getHeight ()][img.getWidth ()];

        for (int i = 0; i < img.getHeight(); i ++)
        {
            for (int j = 0; j < img.getWidth(); j ++)
            {
                Color color = new Color (img.getRGB (j, i));
                int valore = color.getRed();
                if (valore >= 48)
                {
                    valore = 0;
                }
                datiLvl [i][j] = valore;
            }
        }

        return datiLvl;
    }
}
