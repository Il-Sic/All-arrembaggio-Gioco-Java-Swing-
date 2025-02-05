package utilità;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class CaricaSalva
{
    public static final String ALTLANTE_GIOCATORE = "player_sprites.png";
    public static final String ALTLANTE_LIVELLO = "outside_sprites.png";
//    public static final String DATI_LIVELLO_1 = "level_one_data.png";
//    public static final String DATI_LIVELLO_1 = "level_one_data_long.png";
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
    public static final String BARRA_STATO = "health_power_bar.png";
    public static final String LIVELLO_COMPLETATO = "completed_sprite.png";
    public static final String ATLANTE_POZIONE = "potions_sprites.png";
    public static final String ATLANTE_CONTENITORE = "objects_sprites.png";

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

    public static BufferedImage [] GetTuttiLivelli () throws URISyntaxException, IOException
    {
        URL url = CaricaSalva.class.getResource ("/Livellini");
        File file = null;

        assert url != null;
        file = new File (url.toURI ());

        File [] files = file.listFiles();

        assert files != null;
        File [] filesOrdinati = new File [files.length];

        for (int i = 0; i < filesOrdinati.length; i++)
        {
            for (File value : files)
            {
                if (value.getName().equals((i + 1) + ".png"))
                {
                    filesOrdinati[i] = value;
                }
            }
        }

        BufferedImage[] imgs = new BufferedImage [filesOrdinati.length];

        for (int i = 0; i < imgs.length; i ++)
        {
            imgs [i] = ImageIO.read (filesOrdinati [i]);
        }

        return imgs;
    }
}
