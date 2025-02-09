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
    public static final String ATLANTE_TRAPPOLA = "trap_atlas.png";
    public static final String ATLANTE_CANNONE = "cannon_atlas.png";
    public static final String PALLA_CANNONE = "ball.png";
    public static final String SCHERMATA_MORTE = "death_screen.png";
    public static final String OPZIONI_MENU = "options_background.png";
    public static final String ATLANTE_STELLA = "pinkstar_atlas.png";
    public static final String ATLANTE_DOMANDA = "question_atlas.png";
    public static final String ATLANTE_ESCLAMAZIONE = "exclamation_atlas.png";
    public static final String ATLANTE_SQUALO = "shark_atlas.png";
    public static final String ATLANTE_ERBA = "grass_atlas.png";
    public static final String ATLANTE_ALBERO_UNO = "tree_one_atlas.png";
    public static final String ATLANTE_ALBERO_DUE = "tree_two_atlas.png";
    public static final String GIOCO_COMPLETATO = "game_completed.png";
    public static final String PARTICELLE_PIOGGIA = "rain_particle.png";
    public static final String ACQUA_SUPERFICIE = "water_atlas_animation.png";
    public static final String ACQUA_FONDALE = "water.png";
    public static final String BARCA = "ship.png";


    public static BufferedImage GetAtltanteSprite (String nomeFile)
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
