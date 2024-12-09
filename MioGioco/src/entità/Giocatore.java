package entità;

import main.Gioco;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilità.Costanti.CostantiGiocatore.*;
import static utilità.MetodiUtili.*;

public class Giocatore extends Entità
{
    private BufferedImage[][] animazioni;                                       // non le carico perchè le utlizzo solo temporaneamente
    private int tickAni, indiceAni, velAni = 25;                                // 30 perchè le animazioni sono a 120 fps / 4 immagini = 30 ma va troppo lento quindi ho incrementato a 50
    private int azioneGiocatore = IDLE;                                         // importato da utils
    private boolean movimento = false, attacco = false;
    private boolean sopra, sinistra, sotto, destra, salto;
    private float velGiocatore = 1.0f * Gioco.SCALA;
    private int [][] datiLvl;
    private float xDrawOffset = 21 * Gioco.SCALA;
    private float yDrawOffset = 4 * Gioco.SCALA;

    // SALTO e GRAVITÀ
    private float velAria = 0f;
    private float gravità = 0.04f * Gioco.SCALA;
    private float velSalto = -2.25f * Gioco.SCALA;                              // negativo perchè si sposta sulle y e quindi in altezza
    private float velCadutaDopoCollisione =  0.5f * Gioco.SCALA;                // quando ad esempio si colpisce il tetto
    private boolean inAria = false;

    public Giocatore (float x, float y, int larghezza, int altezza)
    {
        super (x, y, larghezza, altezza);
        caricaAnimazioni ();
        initHitBox (x, y, (int) (20 * Gioco.SCALA), (int) (27 * Gioco.SCALA));
    }

    public void update ()
    {
        updatePos ();
        updateTickAnimazioni ();
        setAnimazione ();
    }
    public void render (Graphics g, int lvlOffset)
    {
        g.drawImage (animazioni [azioneGiocatore][indiceAni],  (int) (hitbox.x - xDrawOffset) - lvlOffset,  (int) (hitbox.y - yDrawOffset) , larghezza, altezza, null);              // qui modifico l' immagine e la sua dimensione
//        drawHitBox (g, lvlOffset);
    }


//    public void setRectPos (int x, int y)
//    {
//        this.x = x;
//        this.y = y;
//    }
    private void updateTickAnimazioni ()
    {
        tickAni ++;
        if (tickAni >= velAni)                      // quando è uguale o supera cambia immagine simulando un animazione
        {
            tickAni = 0;
            indiceAni ++;

            if (indiceAni >= GetSpriteCont (azioneGiocatore))
            {
                indiceAni = 0;
                attacco = false;
            }
        }
    }

    private void setAnimazione()
    {
        int iniziaAni = azioneGiocatore;

        if (movimento)
        {
            azioneGiocatore = CORSA;
        }
        else
        {
            azioneGiocatore = IDLE;
        }

        if (inAria)
        {
            if (velAria < 0)
            {
                azioneGiocatore = SALTO;
            }
            else
            {
                azioneGiocatore = CADUTA;
            }
        }

        if (attacco)
        {
            azioneGiocatore = ATTACCO_1;
        }

        if (iniziaAni != azioneGiocatore)
        {
            resetTickAni ();
        }

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
            velX -= velGiocatore;
        }

        if (destra)
        {
            velX += velGiocatore;

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
                velAria += gravità;
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

        animazioni = new BufferedImage [9][6];              // y e x dell' immagine

        for (int i = 0; i < animazioni.length; i ++)
        {
            for (int j = 0; j < animazioni[i].length; j ++)
            {
                animazioni [i][j] = img.getSubimage (j * 64, i * 40, 64, 40);               // j è la x, i è la y dell' immagine
            }
        }
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
        sopra = false;
        sinistra = false;
        sotto = false;
        destra = false;
    }

    public void setAttacco (boolean attacco)
    {
        this.attacco = attacco;
    }

    public boolean isSopra ()
    {
        return sopra;
    }

    public void setSopra (boolean sopra)
    {
        this.sopra = sopra;
    }

    public boolean isSinistra ()
    {
        return sinistra;
    }

    public void setSinistra (boolean sinistra)
    {
        this.sinistra = sinistra;
    }

    public boolean isSotto ()
    {
        return sotto;
    }

    public void setSotto (boolean sotto)
    {
        this.sotto = sotto;
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
}
