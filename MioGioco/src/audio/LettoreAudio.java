package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class LettoreAudio
{
    public static int MENU_1 = 0;
    public static int LIVELLO_1 = 1;
    public static int LIVELLO_2 = 2;
    public static int MUORI = 0;
    public static int SALTO = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETATO = 3;
    public static int ATTACCO_UNO = 4;
    public static int ATTACCO_DUE = 5;
    public static int ATTACCO_TRE = 6;
    private Clip[] suoni, effetti;
    private int idCanzoneCorrente;
    private float volume = 0.5f;
    private boolean mutaCanzone, mutaEffetto;
    private Random rand = new Random();

    public LettoreAudio ()
    {
        caricaCanzoni();
        caricaEffetti();
        riproduciCanzone(MENU_1);
    }
    private void caricaCanzoni()
    {
        String[] nomi = { "menu", "livello1", "livello2" };

        suoni = new Clip [nomi.length];

        for (int i = 0; i < suoni.length; i++)
        {
            suoni[i] = getClip (nomi[i]);
        }
    }

    private void caricaEffetti()
    {
        String[] nomiEffetti = { "morte", "salto", "gameover", "lvlcompletato", "attacco1", "attacco2", "attacco3" };
        effetti = new Clip[nomiEffetti.length];

        for (int i = 0; i < effetti.length; i++)
        {
            effetti[i] = getClip(nomiEffetti[i]);
        }

        updateVolumeEffetti ();
    }

    private Clip getClip(String nome)
    {
        URL url = getClass().getResource("/audio/" + nome + ".wav");
        AudioInputStream audio;

        try
        {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void setVolume (float volume)
    {
        this.volume = volume;

        updateVolumeCanzone();
        updateVolumeEffetti();
    }

    public void fermaCanzone ()
    {
        if (suoni[idCanzoneCorrente].isActive())
        {
            suoni[idCanzoneCorrente].stop();
        }
    }

    public void setCanzoneLivello (int indiceLvl)
    {
        if (indiceLvl % 2 == 0)
        {
            riproduciCanzone(LIVELLO_1);
        }
        else
        {
            riproduciCanzone(LIVELLO_2);
        }
    }

    public void livelloCompletato ()
    {
        fermaCanzone ();
        riproduciEffetto (LVL_COMPLETATO);
    }

    public void riproduciSuonoAttacco ()
    {
        int start = 4;
        start += rand.nextInt(3);

        riproduciEffetto(start);
    }

    public void riproduciEffetto(int effetto)
    {
        if (effetti[effetto].getMicrosecondPosition () > 0)
        {
            effetti[effetto].setMicrosecondPosition(0);
        }

        effetti[effetto].start();
    }

    public void riproduciCanzone(int canzone)
    {
        fermaCanzone();
        idCanzoneCorrente = canzone;
        updateVolumeCanzone();
        suoni[idCanzoneCorrente].setMicrosecondPosition(0);
        suoni[idCanzoneCorrente].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toggleMutaCanzone ()
    {
        this.mutaCanzone = !mutaCanzone;

        for (Clip c : suoni)
        {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(mutaCanzone);
        }
    }

    public void toggleMutaEffetto()
    {
        this.mutaEffetto = !mutaEffetto;

        for (Clip c : effetti)
        {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(mutaEffetto);
        }

        if (!mutaEffetto)
        {
            riproduciEffetto(SALTO);
        }
    }

    private void updateVolumeCanzone()
    {
        FloatControl gainControl = (FloatControl) suoni[idCanzoneCorrente].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateVolumeEffetti()
    {
        for (Clip c : effetti)
        {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
