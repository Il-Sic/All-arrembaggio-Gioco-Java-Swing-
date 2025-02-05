package entità;

import main.Gioco;
import statigioco.Playing;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilità.Costanti.CostantiGiocatore.*;
import static utilità.Costanti.GRAVIOL;
import static utilità.Costanti.VEL_ANI;
import static utilità.MetodiUtili.*;

public class Giocatore extends Entità
{
    private BufferedImage[][] animazioni;                                       // non le carico perchè le utlizzo solo temporaneamente
    private boolean movimento = false, attacco = false;
    private boolean sinistra, destra, salto;
    private int [][] datiLvl;
    private float xDrawOffset = 21 * Gioco.SCALA;
    private float yDrawOffset = 4 * Gioco.SCALA;

    // SALTO e GRAVITÀ
//    private float velAria = 0f;
//    private float gravità = 0.04f * Gioco.SCALA;
    private float velSalto = -2.25f * Gioco.SCALA;                              // negativo perchè si sposta sulle y e quindi in altezza
    private float velCadutaDopoCollisione =  0.5f * Gioco.SCALA;                // quando ad esempio si colpisce il tetto
    private boolean inAria = false;

    // Barra di stato
    private BufferedImage barraStatoImg;

    private int larghezzaBarraStato = (int) (192 * Gioco.SCALA);
    private int altezzaBarraStato = (int) (58 * Gioco.SCALA);
    private int xBarraStato = (int) (10 * Gioco.SCALA);
    private int yBarraStato = (int) (10 * Gioco.SCALA);

    private int larghezzaBarraVita = (int) (150 * Gioco.SCALA);
    private int altezzaBarraVita = (int) (4 * Gioco.SCALA);
    private int xInizioBarraVita = (int) (34 * Gioco.SCALA);
    private int yInizioBarraVita = (int) (14 * Gioco.SCALA);

    private int larghezzaVita = larghezzaBarraVita;

    private int xFlip = 0;
    private int lFlip = 1;

    private boolean attaccoControllato;
    private Playing playing;

    private int casellaY = 0;

    public Giocatore (float x, float y, int larghezza, int altezza, Playing playing)
    {
        super (x, y, larghezza, altezza);
        this.playing = playing;
        this.stato = IDLE;
        this.vitaMax = 100;
        this.vitaCorrente = vitaMax;
        this.vitaCorrente = 35;
        this.velPg = 1.0f * Gioco.SCALA;
        caricaAnimazioni ();
        initHitBox (20, 27);
        initAttackBox ();
    }

    private void initAttackBox()
    {
        attackBox = new Rectangle2D.Float (x, y, (int) (20 * Gioco.SCALA), (int) (20 * Gioco.SCALA));
    }

    public void update ()
    {
        updateBarraVita ();

        if (vitaCorrente <= 0)
        {
            playing.setGameOver (true);
            return;
        }

        updateAttackBox ();

        updatePos ();

        if (movimento)
        {
            controllaPozioneToccata ();
            controllaSpuntoniToccati ();

            casellaY = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);
        }

        if (attacco)
        {
            controllaAttacco ();
        }

        updateTickAnimazioni ();
        setAnimazione ();
    }

    private void controllaSpuntoniToccati ()
    {
        playing.controllaSpuntoniToccati (this);
    }

    private void controllaPozioneToccata()
    {
        playing.controllaPozioneToccata (hitbox);
    }

    public void killa ()
    {
        vitaCorrente = 0;
    }

    private void controllaAttacco()
    {
        if (attaccoControllato || indiceAni != 1)
        {
            return;
        }

        attaccoControllato = true;

        playing.controllaColpoNemico (attackBox);
        playing.controllaHitOggetto (attackBox);
    }

    private void updateAttackBox ()
    {
        if (destra)
        {
            attackBox.x = hitbox.x + hitbox.width + (int) (Gioco.SCALA * 10);
        }
        else if (sinistra)
        {
            attackBox.x = hitbox.x - hitbox.width - (int) (Gioco.SCALA * 10);
        }

        attackBox.y = hitbox.y + (Gioco.SCALA * 10);
    }

    private void updateBarraVita ()
    {
        larghezzaVita = (int) ((vitaCorrente / (float) vitaMax) * larghezzaBarraVita);
    }

    public void cambiaVita (int valore)
    {
        vitaCorrente += valore;

        if (vitaCorrente <= 0)
        {
            vitaCorrente = 0;
        }
        else if (vitaCorrente >= vitaMax)
        {
            vitaCorrente = vitaMax;
        }
    }

    public void cambiaForza (int valore)
    {
        System.out.println ("Forza migliorata");
    }

    public void render (Graphics g, int lvlOffset)
    {
        g.drawImage (animazioni [stato][indiceAni], (int) (hitbox.x - xDrawOffset) - lvlOffset + xFlip,  (int) (hitbox.y - yDrawOffset) , larghezza * lFlip, altezza, null);              // qui modifico l' immagine e la sua dimensione
//        drawHitBox (g, lvlOffset);

//        drawAttackBox (g, lvlOffset);

        drawUI (g);
    }

    private void drawUI (Graphics g)
    {
        g.drawImage (barraStatoImg, xBarraStato, yBarraStato, larghezzaBarraStato, altezzaBarraStato,null);
        g.setColor (Color.red);
        g.fillRect (xInizioBarraVita + xBarraStato, yInizioBarraVita + yBarraStato, larghezzaVita, altezzaBarraVita);
    }


    //    public void setRectPos (int x, int y)
//    {
//        this.x = x;
//        this.y = y;
//    }
    private void updateTickAnimazioni ()
    {
        tickAni ++;

        if (tickAni >= VEL_ANI)                      // quando è uguale o supera cambia immagine simulando un animazione
        {
            tickAni = 0;
            indiceAni ++;

            if (indiceAni >= GetSpriteCont (stato))
            {
                indiceAni = 0;
                attacco = false;
                attaccoControllato = false;
            }
        }
    }

    private void setAnimazione()
    {
        int iniziaAni = stato;

        if (movimento)
        {
            stato = CORSA;
        }
        else
        {
            stato = IDLE;
        }

        if (inAria)
        {
            if (velAria < 0)
            {
                stato = SALTO;
            }
            else
            {
                stato = CADUTA;
            }
        }

        if (attacco)
        {
            stato = ATTACCO;

            if (iniziaAni != ATTACCO)
            {
                indiceAni = 1;
                tickAni = 0;
                return;
            }
        }

        if (iniziaAni != stato)
        {
            resetTickAni ();
        }

    }

    public void setSpawn (Point spawn)
    {
        this.x = spawn.x;
        this.y = spawn.y;

        hitbox.x = x;
        hitbox.y = y;
    }

    private void resetTickAni ()
    {
        tickAni = 0;
        indiceAni = 0;
    }

    private void updatePos()
    {
        movimento = false;

        if (salto)
        {
            salto ();
        }

        if (!inAria)
        {
            if ((!sinistra && !destra) || (sinistra && destra))
            {
                return;
            }
        }

        float velX = 0;

        if (sinistra)
        {
            velX -= velPg;

            xFlip = larghezza;
            lFlip = -1;
        }

        if (destra)
        {
            velX += velPg;

            xFlip = 0;
            lFlip = 1;
        }

        if (!inAria)
        {
            if (!isEntitàSulPavimento(hitbox, datiLvl))
            {
                inAria = true;
            }
        }

        if (inAria)
        {
            if (puòMuoversiQui(hitbox.x, hitbox.y + velAria, hitbox.width, hitbox.height, datiLvl))
            {
                hitbox.y += velAria;
                velAria += GRAVIOL;
                updatePosX(velX);
            }
            else
            {
                hitbox.y = getPosizioneEntitàVicinoAlMuroY(hitbox, velAria);

                if (velAria > 0)
                {
                    resetInAria ();
                }
                else
                {
                    velAria = velCadutaDopoCollisione;
                }

                updatePosX(velX);
            }
        }
        else
        {
            updatePosX(velX);
        }

        movimento = true;
    }

    private void salto()
    {
        if (inAria)
        {
            return;
        }

        inAria = true;
        velAria = velSalto;

    }

    private void resetInAria ()
    {
        inAria = false;
        velAria = 0f;
    }

    private void updatePosX(float velX)
    {
        if (puòMuoversiQui(hitbox.x + velX, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.x += velX;
        }
        else
        {
            hitbox.x = getPosizioneEntitàVicinoAlMuroX(hitbox, velX);
        }

    }

    private void caricaAnimazioni ()
    {
        BufferedImage img = CaricaSalva.GetAtltanteSprite(CaricaSalva.ALTLANTE_GIOCATORE);

        animazioni = new BufferedImage [7][8];              // y e x dell' immagine

        for (int j = 0; j < animazioni.length; j ++)
        {
            for (int i = 0; i < animazioni[j].length; i ++)
            {
                animazioni [j][i] = img.getSubimage (i * 64, j * 40, 64, 40);               // j è la y, i è la x dell' immagine
            }
        }

        barraStatoImg = CaricaSalva.GetAtltanteSprite (CaricaSalva.BARRA_STATO);
    }

    public void caricaDatiLvl (int [][] datiLvl)
    {
        this.datiLvl = datiLvl;

        if (!isEntitàSulPavimento(hitbox, datiLvl))
        {
            inAria = true;
        }
    }

    public void resetDirBooleans ()
    {
        sinistra = false;
        destra = false;
    }

    public int getCasellaY ()
    {
        return casellaY;
    }

    public void setAttacco (boolean attacco)
    {
        this.attacco = attacco;
    }

    public boolean isSinistra ()
    {
        return sinistra;
    }

    public void setSinistra (boolean sinistra)
    {
        this.sinistra = sinistra;
    }

    public boolean isDestra ()
    {
        return destra;
    }

    public void setDestra (boolean destra)
    {
        this.destra = destra;
    }


    public void setSalto (boolean salto)
    {
        this.salto = salto;
    }

    public void resettaTutto()
    {
        resetDirBooleans();
        inAria = false;
        attacco = false;
        movimento = false;
        stato = IDLE;
        vitaCorrente = vitaMax;
        hitbox.x = x;
        hitbox.y = y;

        if (!isEntitàSulPavimento(hitbox, datiLvl))
        {
            inAria = true;
        }
    }
}
