package statigioco;

import effetti.EffettoDialogo;
import effetti.Pioggia;
import entità.Entità;
import entità.GestoreNemico;
import entità.Giocatore;
import livelli.GestoreLivello;
import main.Gioco;
import oggetti.GestoreOggetto;
import ui.OverlayGameOver;
import ui.OverlayGiocoCompletato;
import ui.OverlayLivelloCompletato;
import ui.OverlayPausa;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

import static utilità.Costanti.Ambiente.*;
import static utilità.Costanti.Dialogo.*;

public class Playing extends Stato implements StatoMetodi
{
    private Giocatore giocatore;
    private GestoreNemico gestoreNemico;
    private GestoreLivello gestoreLivello;
    private GestoreOggetto gestoreOggetto;
    private OverlayPausa overlayPausa;
    private OverlayGameOver overlayGameOver;
    private OverlayLivelloCompletato overlayLivelloCompletato;
    private OverlayGiocoCompletato overlayGiocoCompletato;
    private Pioggia pioggia;
    private boolean inPausa = false;

    private int xLvlOffset;
    private int bordoSin = (int) (0.25 * Gioco.LARGHEZZA_GIOCO);
    private int bordoDes = (int) (0.75 * Gioco.LARGHEZZA_GIOCO);
    private int maxXLvlOffset;

    private BufferedImage immagineBackgound, grandeNuvola, piccolaNuvola;
    private BufferedImage [] imgsDomanda, imgsEsclamazione, imgsBarca;
    private ArrayList <EffettoDialogo> effettiDialogo = new ArrayList<>();

    private int [] posizionePiccolaNuvola;
    private Random rand = new Random();

    private boolean gameOver = false;
    private boolean lvlCompletato = false;
    private boolean morteGiocatore = false;
    private boolean giocoCompletato = false;
    private boolean drawPioggia = false;

    private boolean drawBarca = true;
    private int aniBarca, tickBarca, dirBarca = 1;
    private float deltaAltezzaBarca, cambiaAltezzaBarca = 0.05f * Gioco.SCALA;

    public Playing (Gioco gioco) throws URISyntaxException, IOException
    {
        super (gioco);
        initClassi ();

        immagineBackgound = CaricaSalva.GetAtltanteSprite(CaricaSalva.BACKGROUND_IN_GIOCO);
        grandeNuvola = CaricaSalva.GetAtltanteSprite(CaricaSalva.GRANDI_NUVOLE);
        piccolaNuvola = CaricaSalva.GetAtltanteSprite(CaricaSalva.PICCOLE_NUVOLE);
        posizionePiccolaNuvola = new int [8];

        for (int i = 0; i < posizionePiccolaNuvola.length; i++)
        {
            posizionePiccolaNuvola [i] = (int) (90 * Gioco.SCALA) + rand.nextInt ((int) (100 * Gioco.SCALA));
        }

        imgsBarca = new BufferedImage [4];
        BufferedImage temp = CaricaSalva.GetAtltanteSprite (CaricaSalva.BARCA);

        for (int i = 0; i < imgsBarca.length; i++)
        {
            imgsBarca [i] = temp.getSubimage(i * 78, 0, 78, 72);
        }

        caricaDialogo ();
        calcolaLvlOffset ();
        caricaInizioLivello ();
        setDrawPioggiaBoolean ();
    }

    private void caricaDialogo()
    {
        caricaImgsDialogo ();

        for (int i = 0; i < 10; i++)
        {
            effettiDialogo.add(new EffettoDialogo(0, 0, ESCLAMAZIONE));
        }
        for (int i = 0; i < 10; i++)
        {
            effettiDialogo.add(new EffettoDialogo(0, 0, DOMANDA));
        }

        for (EffettoDialogo effettoDialogo : effettiDialogo)
        {
            effettoDialogo.disattiva();
        }
    }

    private void caricaImgsDialogo()
    {
        BufferedImage temp = CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_DOMANDA);
        imgsDomanda = new BufferedImage[5];

        for (int i = 0; i < imgsDomanda.length; i++)
        {
            imgsDomanda[i] = temp.getSubimage(i * 14, 0, 14, 12);
        }

        temp = (CaricaSalva.GetAtltanteSprite(CaricaSalva.ATLANTE_ESCLAMAZIONE));
        imgsEsclamazione = new BufferedImage[5];

        for (int i = 0; i < imgsEsclamazione.length; i++)
        {
            imgsEsclamazione[i] = temp.getSubimage(i * 14, 0, 14, 12);
        }
    }

    public void caricaLivelloSuccessivo ()
    {
        gestoreLivello.setIndiceLivello (gestoreLivello.getIndiceLivello() + 1);
        gestoreLivello.caricaLivelloSuccessivo ();
        giocatore.setSpawn (gestoreLivello.getLivelloCorrente ().getSpawnGiocatore ());
        resettaTutto ();
        drawBarca = false;
    }

    private void caricaInizioLivello()
    {
        gestoreNemico.caricaNemici (gestoreLivello.getLivelloCorrente ());
        gestoreOggetto.caricaOggetti (gestoreLivello.getLivelloCorrente ());
    }

    private void calcolaLvlOffset()
    {
        maxXLvlOffset = gestoreLivello.getLivelloCorrente ().getLvlOffset ();
    }

    private void initClassi () throws URISyntaxException, IOException
    {
        gestoreLivello = new GestoreLivello(gioco);
        gestoreNemico = new GestoreNemico(this);
        gestoreOggetto = new GestoreOggetto (this);

        giocatore = new Giocatore (200, 200, (int) (64 * Gioco.SCALA), (int) (40 * Gioco.SCALA), this);
        giocatore.caricaDatiLvl (gestoreLivello.getLivelloCorrente ().getDatiLvl ());
        giocatore.setSpawn (gestoreLivello.getLivelloCorrente ().getSpawnGiocatore ());

        overlayPausa = new OverlayPausa (this);
        overlayGameOver = new OverlayGameOver (this);
        overlayLivelloCompletato = new OverlayLivelloCompletato (this);
        overlayGiocoCompletato = new OverlayGiocoCompletato (this);

        pioggia = new Pioggia();
    }

    public Giocatore getGiocatore ()
    {
        return giocatore;
    }

    public void riprendiGioco ()
    {
        inPausa = false;
    }

    @Override
    public void update ()
    {
        if (inPausa)
        {
            overlayPausa.update();
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.update();
        }
        else if (gameOver)
        {
            overlayGameOver.update();
        }
        else if (morteGiocatore)
        {
            giocatore.update();
        }
        else if (giocoCompletato)
        {
            overlayGiocoCompletato.update ();
        }
        else
        {
            updateDialogo ();

            if (drawPioggia)
            {
                pioggia.update(xLvlOffset);
            }

            gestoreLivello.update();
            gestoreOggetto.update(gestoreLivello.getLivelloCorrente().getDatiLvl(), giocatore);
            giocatore.update();
            gestoreNemico.update(gestoreLivello.getLivelloCorrente().getDatiLvl());
            controllaVicinoBordo();

            if (drawBarca)
            {
                updateAniBarca ();
            }
        }
    }

    private void updateAniBarca()
    {
        tickBarca ++;

        if (tickBarca >= 35)
        {
            tickBarca = 0;
            aniBarca ++;

            if (aniBarca >= 4)
            {
                aniBarca = 0;
            }
        }

        deltaAltezzaBarca += cambiaAltezzaBarca * dirBarca;
        deltaAltezzaBarca = Math.max(Math.min(10 * Gioco.SCALA, deltaAltezzaBarca), 0);

        if (deltaAltezzaBarca == 0)
        {
            dirBarca = 1;
        }
        else if (deltaAltezzaBarca == 10 * Gioco.SCALA)
        {
            dirBarca = -1;
        }
    }

    private void updateDialogo()
    {
        for (EffettoDialogo effettoDialogo : effettiDialogo)
        {
            if (effettoDialogo.isAttivo())
            {
                effettoDialogo.update();
            }
        }
    }

    private void drawDialogo (Graphics g, int xLvlOffset)
    {
        for (EffettoDialogo effettoDialogo : effettiDialogo)
        {
            if (effettoDialogo.isAttivo())
            {
                if (effettoDialogo.getTipo() == DOMANDA)
                {
                    g.drawImage(imgsDomanda[effettoDialogo.getIndiceAni()], effettoDialogo.getX() - xLvlOffset, effettoDialogo.getY(), LARGHEZZA_DIALOGO, ALTEZZA_DIALOGO, null);
                }
                else
                {
                    g.drawImage(imgsEsclamazione[effettoDialogo.getIndiceAni()], effettoDialogo.getX() - xLvlOffset, effettoDialogo.getY(), LARGHEZZA_DIALOGO, ALTEZZA_DIALOGO, null);
                }
            }
        }
    }

    public void aggiungiDialogo(int x, int y, int tipo)
    {
        effettiDialogo.add(new EffettoDialogo(x, y - (int) (Gioco.SCALA * 15), tipo));

        for (EffettoDialogo effettoDialogo : effettiDialogo)
        {
            if (!effettoDialogo.isAttivo())
            {
                if (effettoDialogo.getTipo() == tipo)
                {
                    effettoDialogo.resetta(x, -(int) (Gioco.SCALA * 15));
                    return;
                }
            }
        }
    }

    private void controllaVicinoBordo ()
    {
        int giocatoreX = (int) giocatore.getHitbox().x;
        int diff = giocatoreX - xLvlOffset;

        if (diff > bordoDes)
        {
            xLvlOffset += diff - bordoDes;
        }
        else if (diff < bordoSin)
        {
            xLvlOffset += diff - bordoSin;
        }

        if (xLvlOffset > maxXLvlOffset)
        {
            xLvlOffset = maxXLvlOffset;
        }
        else if (xLvlOffset < 0)
        {
            xLvlOffset = 0;
        }
    }

    private void drawNuvole (Graphics g)
    {
        for (int i = 0; i < 3; i ++)
        {
            g.drawImage (grandeNuvola, i * LARGHEZZA_GRANDE_NUVOLA - (int) (xLvlOffset * 0.3), (int) (204 * Gioco.SCALA), LARGHEZZA_GRANDE_NUVOLA, ALTEZZA_GRANDE_NUVOLA, null);
        }

        for (int i = 0; i < posizionePiccolaNuvola.length; i ++)
        {
            g.drawImage (piccolaNuvola, LARGHEZZA_PICCOLA_NUVOLA * 4 * i - (int) (xLvlOffset * 0.7), posizionePiccolaNuvola [i],  LARGHEZZA_PICCOLA_NUVOLA, ALTEZZA_PICCOLA_NUVOLA, null);
        }
    }

    @Override
    public void draw (Graphics g)
    {
        g.drawImage(immagineBackgound, 0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO, null);

        drawNuvole(g);

        if (drawPioggia)
        {
            pioggia.draw(g, xLvlOffset);
        }

        if (drawBarca)
        {
            g.drawImage (imgsBarca[aniBarca], (int) (100 * Gioco.SCALA) - xLvlOffset, (int) ((288 * Gioco.SCALA) + deltaAltezzaBarca), (int) (78 * Gioco.SCALA), (int) (72 * Gioco.SCALA), null);

        }

        gestoreLivello.draw(g, xLvlOffset);
        giocatore.render(g, xLvlOffset);
        gestoreNemico.draw(g, xLvlOffset);
        gestoreOggetto.draw(g, xLvlOffset);
        gestoreOggetto.drawAlberiBackground (g, xLvlOffset);
        drawDialogo (g, xLvlOffset);

        if (inPausa)
        {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Gioco.LARGHEZZA_GIOCO, Gioco.ALTEZZA_GIOCO);
            overlayPausa.draw(g);
        }
        else if (gameOver)
        {
            overlayGameOver.draw(g);
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.draw (g);
        }
        else if (giocoCompletato)
        {
            overlayGiocoCompletato.draw (g);
        }
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
        if (!gameOver)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                giocatore.setAttacco(true);
            }
            else if (e.getButton() == MouseEvent.BUTTON3)
            {
                giocatore.forzaAttacco();
            }
        }
    }

    @Override
    public void mousePressed (MouseEvent e)
    {
        if (gameOver)
        {
            overlayGameOver.mousePressed(e);
        }
        else if (inPausa)
        {
            overlayPausa.mousePressed(e);
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.mousePressed(e);
        }
        else if (giocoCompletato)
        {
            overlayGiocoCompletato.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
        if (gameOver)
        {
            overlayGameOver.mouseReleased(e);
        }
        else if (inPausa)
        {
            overlayPausa.mouseReleased(e);
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.mouseReleased(e);
        }
        else if (giocoCompletato)
        {
            overlayGiocoCompletato.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved (MouseEvent e)
    {
        if (gameOver)
        {
            overlayGameOver.mouseMoved(e);
        }
        else if (inPausa)
        {
            overlayPausa.mouseMoved(e);
        }
        else if (lvlCompletato)
        {
            overlayLivelloCompletato.mouseMoved(e);
        }
        else if (giocoCompletato)
        {
            overlayGiocoCompletato.mouseMoved(e);
        }
    }

    public void mouseDragged (MouseEvent e)
    {
        if (!gameOver && !lvlCompletato && !giocoCompletato)
        {
            if (inPausa)
            {
                overlayPausa.mouseDragged(e);
            }
        }
    }

    @Override
    public void keyPressed (KeyEvent e)
    {
        if (!gameOver && !lvlCompletato && !giocoCompletato)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT->
                {
                    giocatore.setSinistra (true);
                }

                case KeyEvent.VK_D, KeyEvent.VK_RIGHT->
                {
                    giocatore.setDestra (true);
                }

                case KeyEvent.VK_SPACE, KeyEvent.VK_UP ->
                {
                    giocatore.setSalto (true);
                }

                case KeyEvent.VK_Z ->
                {
                    giocatore.setAttacco (true);
                }

                case KeyEvent.VK_X ->
                {
                    giocatore.forzaAttacco();
                }

                case KeyEvent.VK_ESCAPE ->
                {
                    inPausa = !inPausa;
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (!gameOver && !lvlCompletato && !giocoCompletato)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_A, KeyEvent.VK_LEFT ->
                {
                    giocatore.setSinistra (false);
                }

                case KeyEvent.VK_D, KeyEvent.VK_RIGHT ->
                {
                    giocatore.setDestra (false);
                }

                case KeyEvent.VK_SPACE, KeyEvent.VK_UP ->
                {
                    giocatore.setSalto (false);
                }
            }
        }
    }

    public void resettaTutto()
    {
        gameOver = false;
        inPausa = false;
        lvlCompletato = false;
        morteGiocatore = false;

        setDrawPioggiaBoolean ();

        giocatore.resettaTutto ();
        gestoreNemico.resettaTuttiNemici();
        gestoreOggetto.resettaTuttiOggetti();
        effettiDialogo.clear();
    }

    private void setDrawPioggiaBoolean()
    {
        if (rand.nextFloat() >= 0.8f)
        {
            drawPioggia = true;
        }
    }

    public void controllaColpoNemico (Rectangle2D.Float attackBox)
    {
        gestoreNemico.controllaColpoNemico (attackBox);
    }

    public void controllaHitOggetto (Rectangle2D.Float attackbox)
    {
        gestoreOggetto.controllaHitOggetto (attackbox);
    }

    public void controllaPozioneToccata (Rectangle2D.Float hitbox)
    {
        gestoreOggetto.controllaOggettoToccato (hitbox);
    }

    public boolean controllaSpuntoniToccatiEntità (Entità entità)
    {
        return gestoreOggetto.controllaSpuntoniToccati (entità);
    }

    public void resettaGiocoCompletato ()
    {
        giocoCompletato = false;
    }

    public void setGiocoCompletato ()
    {
        giocoCompletato = true;
    }

    public void setGameOver (boolean gameOver)
    {
        this.gameOver = gameOver;
    }

    public GestoreNemico getGestoreNemico()
    {
        return gestoreNemico;
    }

    public GestoreOggetto getGestoreOggetto ()
    {
        return gestoreOggetto;
    }

    public GestoreLivello getGestoreLivello()
    {
        return gestoreLivello;
    }

    public void setMaxLvlOffset (int lvlOffset)
    {
        this.maxXLvlOffset = lvlOffset;
    }

    public void setLivelloCompletato (boolean livelloCompletato)
    {
        gioco.getLettoreAudio().livelloCompletato();
        if (gestoreLivello.getIndiceLivello() + 1 >= gestoreLivello.getNumeroLivelli())
        {
            giocoCompletato = true;
            gestoreLivello.setIndiceLivello (0);
            gestoreLivello.caricaLivelloSuccessivo();
            resettaTutto();
            return;
        }
        this.lvlCompletato = livelloCompletato;
    }

    public void setMorteGiocatore (boolean morteGiocatore)
    {
        this.morteGiocatore = morteGiocatore;
    }
}
