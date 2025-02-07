package entità;

import audio.LettoreAudio;
import main.Gioco;
import statigioco.Playing;
import utilità.CaricaSalva;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilità.Costanti.*;
import static utilità.Costanti.CostantiGiocatore.*;
import static utilità.Costanti.Direzioni.*;
import static utilità.MetodiUtili.*;

public class Giocatore extends Entità
{
    private BufferedImage[][] animazioni;
    private boolean movimento = false, attacco = false;
    private boolean sinistra, destra, salto;
    private int [][] datiLvl;
    private float xDrawOffset = 21 * Gioco.SCALA;
    private float yDrawOffset = 4 * Gioco.SCALA;

    private float velSalto = -2.25f * Gioco.SCALA;
    private float velCadutaDopoCollisione =  0.5f * Gioco.SCALA;

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

    private int larghezzaBarraForza = (int) (104 * Gioco.SCALA);
    private int altezzaBarraForza = (int) (2 * Gioco.SCALA);
    private int barraForzaXStart = (int) (44 * Gioco.SCALA);
    private int barraForzaYStart = (int) (34 * Gioco.SCALA);
    private int larghezzaForza = larghezzaBarraForza;
    private int valoreForzaMax = 200;
    private int valoreForza = valoreForzaMax;

    private int xFlip = 0;
    private int lFlip = 1;

    private boolean attaccoControllato;
    private Playing playing;

    private int casellaY = 0;

    private boolean forzaAttaccoAttiva;
    private int forzaAttaccoTick;
    private int forzaAumentaVel = 15;
    private int forzaAumentaTick;

    public Giocatore (float x, float y, int larghezza, int altezza, Playing playing)
    {
        super (x, y, larghezza, altezza);
        this.playing = playing;
        this.stato = IDLE;
        this.vitaMax = 100;
        this.vitaCorrente = vitaMax;
        this.velPg = 1.0f * Gioco.SCALA;

        caricaAnimazioni ();
        initHitBox (20, 27);
        initAttackBox ();
    }

    private void initAttackBox()
    {
        attackBox = new Rectangle2D.Float (x, y, (int) (20 * Gioco.SCALA), (int) (20 * Gioco.SCALA));

        resetAttackBox ();
    }

    public void update ()
    {
        updateBarraVita ();
        updateBarraForza ();

        if (vitaCorrente <= 0)
        {
            if (stato != MORTE)
            {
                stato = MORTE;
                tickAni = 0;
                indiceAni = 0;
                playing.setMorteGiocatore (true);
                playing.getGioco().getLettoreAudio().riproduciEffetto(LettoreAudio.MUORI);

                if (!IsEntitàSulPavimento(hitbox, datiLvl))
                {
                    inAria = true;
                    velAria = 0;
                }
            }
            else if (indiceAni == GetSpriteCont (MORTE) - 1 && tickAni >= VEL_ANI - 1)
            {
                playing.setGameOver(true);
                playing.getGioco().getLettoreAudio().fermaCanzone();
                playing.getGioco().getLettoreAudio().riproduciEffetto(LettoreAudio.GAMEOVER);
            }
            else
            {
                updateTickAnimazioni();

                if (inAria)
                {
                    if (PuòMuoversiQui(hitbox.x, hitbox.y + velAria, hitbox.width, hitbox.height, datiLvl))
                    {
                        hitbox.y += velAria;
                        velAria += GRAVIOL;
                    }
                    else
                    {
                        inAria = false;
                    }
                }
            }

            return;
        }

        updateAttackBox ();

        if (stato == COLPO)
        {
            if (indiceAni <= GetDannoNemico(stato) - 3)
            {
                contraccolpo(direzioneContraccolpo, datiLvl, 1.25f);
            }

            updateContraccolpoDrawOffset();
        }
        else
        {
            updatePos();
        }

        if (movimento)
        {
            controllaPozioneToccata ();
            controllaSpuntoniToccati ();
            controllaInAcqua();

            casellaY = (int) (hitbox.y / Gioco.DIMENSIONE_CASELLA);

            if (forzaAttaccoAttiva)
            {
                forzaAttaccoTick ++;

                if (forzaAttaccoTick >= 35)
                {
                    forzaAttaccoTick = 0;
                    forzaAttaccoAttiva = false;
                }
            }
        }

        if (attacco || forzaAttaccoAttiva)
        {
            controllaAttacco ();
        }

        updateTickAnimazioni ();
        setAnimazione ();
    }

    private void controllaInAcqua()
    {
        if (IsEntitàInAcqua (hitbox, playing.getGestioneLivello().getLivelloCorrente().getDatiLvl()))
        {
            vitaCorrente = 0;
        }
    }

    private void updateBarraForza()
    {
        larghezzaForza = (int) ((valoreForza / (float) valoreForzaMax) * larghezzaBarraForza);

        forzaAumentaTick ++;

        if (forzaAumentaTick >= forzaAumentaVel)
        {
            forzaAumentaTick = 0;
            cambiaForza (1);
        }
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

        if (forzaAttaccoAttiva)
        {
            attaccoControllato = false;
        }

        playing.controllaColpoNemico (attackBox);
        playing.controllaHitOggetto (attackBox);
        playing.getGioco().getLettoreAudio().riproduciSuonoAttacco();
    }

    private void updateAttackBox ()
    {
        if (sinistra && destra)
        {
            if (lFlip == 1)
            {
                setAttackBoxLatoDestro();
            }
            else
            {
                setAttackBoxLatoSinistro();
            }
        }

        else if (destra || forzaAttaccoAttiva && lFlip == 1)
        {
            setAttackBoxLatoDestro();
        }
        else if (sinistra || forzaAttaccoAttiva && lFlip == -1)
        {
            setAttackBoxLatoSinistro();
        }

        attackBox.y = hitbox.y + (Gioco.SCALA * 10);
    }

    private void updateBarraVita ()
    {
        larghezzaVita = (int) ((vitaCorrente / (float) vitaMax) * larghezzaBarraVita);
    }

    public void cambiaVita (int valore)
    {
        if (valore < 0)
        {
            if (stato == COLPO)
            {
                return;
            }
            else
            {
                nuovoStato(COLPO);
            }
        }

        vitaCorrente += valore;
        vitaCorrente = Math.max (Math.min (vitaCorrente, vitaMax), 0);
    }

    public void cambiaVita (int valore, Nemico nemico)
    {
        if (stato == COLPO)
        {
            return;
        }

        cambiaVita(valore);

        contraccolpoDirOffset = SU;
        pushDrawOffset = 0;

        if (nemico.getHitbox().x < hitbox.x)
        {
            direzioneContraccolpo = DESTRA;
        }
        else
        {
            direzioneContraccolpo = SINISTRA;
        }
    }

    public void cambiaForza (int valore)
    {
        valoreForza += valore;

        if (valoreForza >= valoreForzaMax)
        {
            valoreForza = valoreForzaMax;
        }

        else if (valoreForza <= 0)
        {
            valoreForza = 0;
        }
    }

    public void forzaAttacco ()
    {
        if (forzaAttaccoAttiva)
        {
            return;
        }

        if (valoreForza >= 60)
        {
            forzaAttaccoAttiva = true;
            cambiaForza (-60);
        }
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

        g.setColor(Color.yellow);
        g.fillRect (barraForzaXStart + xBarraStato, barraForzaYStart + yBarraStato, larghezzaForza, altezzaBarraForza);
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

                if (stato == COLPO)
                {
                    nuovoStato(IDLE);
                    velAria = 0f;

                    if (!IsPavimento(hitbox, 0, datiLvl))
                    {
                        inAria = true;
                    }
                }
            }
        }
    }

    private void setAnimazione()
    {
        int iniziaAni = stato;

        if (stato == COLPO)
        {
            return;
        }

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

        if (forzaAttaccoAttiva)
        {
            stato = ATTACCO;
            indiceAni = 1;
            tickAni = 0;

            return;
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
            if (!forzaAttaccoAttiva)
            {
                if ((!sinistra && !destra) || (sinistra && destra))
                {
                    return;
                }
            }
        }

        float velX = 0;

        if (sinistra && !destra)
        {
            velX -= velPg;

            xFlip = larghezza;
            lFlip = -1;
        }

        if (!sinistra && destra)
        {
            velX += velPg;

            xFlip = 0;
            lFlip = 1;
        }

        if (forzaAttaccoAttiva)
        {
            if (!sinistra && !destra || (sinistra && destra))
            {
                if (lFlip == -1)
                {
                    velX = -velPg;
                }
                else
                {
                    velX = velPg;
                }
            }

            velX *= 3;
        }

        if (!inAria)
        {
            if (!IsEntitàSulPavimento(hitbox, datiLvl))
            {
                inAria = true;
            }
        }

        if (inAria && !forzaAttaccoAttiva)
        {
            if (PuòMuoversiQui(hitbox.x, hitbox.y + velAria, hitbox.width, hitbox.height, datiLvl))
            {
                hitbox.y += velAria;
                velAria += GRAVIOL;
                updatePosX (velX);
            }
            else
            {
                hitbox.y = GetPosizioneEntitàVicinoAlMuroY(hitbox, velAria);

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

        playing.getGioco().getLettoreAudio().riproduciEffetto(LettoreAudio.SALTO);
        inAria = true;
        velAria = velSalto;
    }

    private void resetInAria ()
    {
        inAria = false;
        velAria = 0f;
    }

    private void updatePosX (float velX)
    {
        if (PuòMuoversiQui(hitbox.x + velX, hitbox.y, hitbox.width, hitbox.height, datiLvl))
        {
            hitbox.x += velX;
        }
        else
        {
            hitbox.x = GetPosizioneEntitàVicinoAlMuroX(hitbox, velX);

            if (forzaAttaccoAttiva)
            {
                forzaAttaccoAttiva = false;
                forzaAttaccoTick = 0;
            }
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

        if (!IsEntitàSulPavimento(hitbox, datiLvl))
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

    private void setAttackBoxLatoDestro()
    {
        attackBox.x = hitbox.x + hitbox.width - (int) (Gioco.SCALA * 5);
    }

    private void setAttackBoxLatoSinistro()
    {
        attackBox.x = hitbox.x - hitbox.width - (int) (Gioco.SCALA * 10);
    }


    public void resettaTutto()
    {
        resetDirBooleans();
        inAria = false;
        attacco = false;
        movimento = false;
        velAria = 0f;
        stato = IDLE;
        vitaCorrente = vitaMax;

        hitbox.x = x;
        hitbox.y = y;
        resetAttackBox ();

        if (!IsEntitàSulPavimento(hitbox, datiLvl))
        {
            inAria = true;
        }
    }

    private void resetAttackBox()
    {
        if (lFlip == 1)
        {
            attackBox.x = hitbox.x + hitbox.width + (int) (Gioco.SCALA * 10);
        }
        else
        {
            attackBox.x = hitbox.x + hitbox.width - (int) (Gioco.SCALA * 10);
        }
    }
}
