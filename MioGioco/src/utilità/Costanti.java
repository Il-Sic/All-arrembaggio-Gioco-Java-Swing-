package utilità;

import main.Gioco;

import static utilità.Costanti.CostantiNemico.GRANCHIO;

public class Costanti
{
    public static final float GRAVIOL = 0.04f * Gioco.SCALA;
    public static final int VEL_ANI = 25;

    public static class CostantiNemico
    {
        public static final int GRANCHIO = 0;

        public static final int IDLE = 0;
        public static final int CORSA = 1;
        public static final int ATTACCO = 2;
        public static final int COLPO = 3;
        public static final int MORTE = 4;

        public static final int ALTEZZA_GRANCHIO_DEFAULT = 32;
        public static final int LARGHEZZA_GRANCHIO_DEFAULT = 72;

        public static final int ALTEZZA_GRANCHIO = (int) (ALTEZZA_GRANCHIO_DEFAULT * Gioco.SCALA);
        public static final int LARGHEZZA_GRANCHIO = (int) (LARGHEZZA_GRANCHIO_DEFAULT * Gioco.SCALA);

        public static final int GRANCHIO_DRAWOFFSET_X = (int) (26 * Gioco.SCALA);
        public static final int GRANCHIO_DRAWOFFSET_Y = (int) (9 * Gioco.SCALA);

        public static int GetContSprite (int tipoNemico, int statoNemico)
        {
            return switch (tipoNemico)
            {
                case GRANCHIO -> switch (statoNemico)
                {
                    case IDLE -> 9;

                    case CORSA -> 6;

                    case ATTACCO -> 7;

                    case COLPO -> 4;

                    case MORTE -> 5;

                    default -> 0;
                };

                //                case STELLA -> switch (statoNemico)
                //                {
                //
                //                }
                default -> throw new IllegalStateException("Tipo nemico sconosciuto: " + tipoNemico);
            };
        }
    }

    public static int GetVitaMax (int tipoNemico)
    {
        switch (tipoNemico)
        {
            case GRANCHIO ->
            {
                return 10;
            }

            default ->
            {
                return 1;
            }
        }
    }

    public static int GetDannoNemico (int tipoNemico)
    {
        switch (tipoNemico)
        {
            case GRANCHIO ->
            {
                return 15;
            }

            default ->
            {
                return 0;
            }
        }
    }

    public static class Ambiente
    {
        public static final int LARGHEZZA_GRANDE_NUVOLA_DEFAULT = 448;
        public static final int ALTEZZA_GRANDE_NUVOLA_DEFAULT = 101;
        public static final int LARGHEZZA_PICCOLA_NUVOLA_DEFAULT = 74;
        public static final int ALTEZZA_PICCOLA_NUVOLA_DEFAULT = 24;

        public static final int LARGHEZZA_GRANDE_NUVOLA = (int) (LARGHEZZA_GRANDE_NUVOLA_DEFAULT * Gioco.SCALA);
        public static final int ALTEZZA_GRANDE_NUVOLA = (int) (ALTEZZA_GRANDE_NUVOLA_DEFAULT * Gioco.SCALA);
        public static final int LARGHEZZA_PICCOLA_NUVOLA = (int) (LARGHEZZA_PICCOLA_NUVOLA_DEFAULT * Gioco.SCALA);
        public static final int ALTEZZA_PICCOLA_NUVOLA = (int) (ALTEZZA_PICCOLA_NUVOLA_DEFAULT * Gioco.SCALA);
    }

    public static  class UI
    {
        public static class Buttons
        {
            public static final int B_LARGHEZZA_DEFAULT = 140;
            public static final int B_ALTEZZA_DEFAULT = 56;
            public static final int B_LARGHEZZA = (int) (B_LARGHEZZA_DEFAULT * Gioco.SCALA);
            public static final int B_ALTEZZA = (int) (B_ALTEZZA_DEFAULT * Gioco.SCALA);
        }

        public static class PauseButtons
        {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Gioco.SCALA);
        }

        public static class UrmButtons
        {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Gioco.SCALA);
        }

        public static class VolumeButtons
        {
            public static final int VOLUME_LARGHEZZA_DEFAULT = 28;
            public static final int VOLUME_ALTEZZA_DEFAULT = 44;
            public static final int CURSORE_LARGHEZZA_DEFAULT = 215;
            //public static final int CURSORE_ALTEZZA_DEFAULT = 45;

            public static final int VOLUME_LARGEZZA = (int) (VOLUME_LARGHEZZA_DEFAULT * Gioco.SCALA);
            public static final int VOLUME_ALTEZZA = (int) (VOLUME_ALTEZZA_DEFAULT * Gioco.SCALA);
            public static final int CURSORE_LARGHEZZA = (int) (CURSORE_LARGHEZZA_DEFAULT * Gioco.SCALA);
            //public static final int CURSORE_ALTEZZA  = (int) (CURSORE_ALTEZZA_DEFAULT * Gioco.SCALA);
        }
    }

    public static class Direzioni
    {
        public static final int SINISTRA = 0;
        public static final int SU = 1;
        public static final int DESTRA = 2;
        public static final int SOTTO = 3;

    }

    public static class CostantiGiocatore
    {
        public static final int IDLE = 0;
        public static final int CORSA = 1;
        public static final int SALTO = 2;
        public static final int CADUTA = 3;
        public static final int ATTACCO = 4;
        public static final int COLPO = 5;
        public static final int MORTE = 6;

        public static int GetSpriteCont (int azioneGiocatore)
        {
            return switch (azioneGiocatore)
            {
                case MORTE -> 8;
                case CORSA -> 6;
                case IDLE -> 5;
                case COLPO -> 4;
                case SALTO, ATTACCO -> 3;
                case CADUTA -> 1;
                default -> 1;                   // e anche CADUTA
            };
        }
    }

    public static class CostantiOggetto
    {
        public static final int POZIONE_ROSSA = 0;
        public static final int POZIONE_BLU = 1;
        public static final int BARILE = 2;
        public static final int CASSA = 3;
        public static final int VALORE_POZIONE_ROSSA = 15;
        public static final int VALORE_POZIONE_BLU = 10;
        public static final int LARGHEZZA_CONTENITORE_DEFAULT = 40;
        public static final int ALTEZZA_CONTENITORE_DEFAULT = 30;
        public static final int LARGHEZZA_CONTENITORE = (int) (Gioco.SCALA * LARGHEZZA_CONTENITORE_DEFAULT);
        public static final int ALTEZZA_CONTENITORE = (int) (Gioco.SCALA * ALTEZZA_CONTENITORE_DEFAULT);
        public static final int LARGHEZZA_POZIONE_DEFAULT = 12;
        public static final int ALTEZZA_POZIONE_DEFAULT = 16;
        public static final int LARGHEZZA_POZIONE = (int) (Gioco.SCALA * LARGHEZZA_POZIONE_DEFAULT);
        public static final int ALTEZZA_POZIONE = (int) (Gioco.SCALA * ALTEZZA_POZIONE_DEFAULT);

        public static int GetSpriteCont (int tipoOggetto)
        {
            return switch (tipoOggetto)
            {
                case POZIONE_ROSSA, POZIONE_BLU -> 7;
                case BARILE, CASSA -> 8;
                default -> 1;
            };
        }
    }
}
