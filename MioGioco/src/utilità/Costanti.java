package utilità;

import main.Gioco;

import static utilità.Costanti.CostantiNemico.*;

public class Costanti
{
    public static final float GRAVIOL = 0.04f * Gioco.SCALA;
    public static final int VEL_ANI = 25;

    public static class CostantiNemico
    {
        public static final int GRANCHIO = 0;
        public static final int STELLA = 1;
        public static final int SQUALO = 2;

        public static final int IDLE = 0;
        public static final int CORSA = 1;
        public static final int ATTACCO = 2;
        public static final int COLPO = 3;
        public static final int MORTE = 4;

        public static final int LARGHEZZA_GRANCHIO_DEFAULT = 72;
        public static final int ALTEZZA_GRANCHIO_DEFAULT = 32;
        public static final int ALTEZZA_GRANCHIO = (int) (ALTEZZA_GRANCHIO_DEFAULT * Gioco.SCALA);
        public static final int LARGHEZZA_GRANCHIO = (int) (LARGHEZZA_GRANCHIO_DEFAULT * Gioco.SCALA);
        public static final int GRANCHIO_DRAWOFFSET_X = (int) (26 * Gioco.SCALA);
        public static final int GRANCHIO_DRAWOFFSET_Y = (int) (9 * Gioco.SCALA);

        public static final int LARGHEZZA_STELLA_DEFAULT = 34;
        public static final int ALTEZZA_STELLA_DEFAULT = 30;
        public static final int LARGHEZZA_STELLA = (int) (LARGHEZZA_STELLA_DEFAULT * Gioco.SCALA);
        public static final int ALTEZZA_STELLA = (int) (ALTEZZA_STELLA_DEFAULT * Gioco.SCALA);
        public static final int STELLA_DRAWOFFSET_X = (int) (9 * Gioco.SCALA);
        public static final int STELLA_DRAWOFFSET_Y = (int) (7 * Gioco.SCALA);

        public static final int LARGHEZZA_SQUALO_DEFAULT = 34;
        public static final int ALTEZZA_SQUALO_DEFAULT = 30;
        public static final int LARGHEZZA_SQUALO = (int) (LARGHEZZA_SQUALO_DEFAULT * Gioco.SCALA);
        public static final int ALTEZZA_SQUALO = (int) (ALTEZZA_SQUALO_DEFAULT * Gioco.SCALA);
        public static final int SQUALO_DRAWOFFSET_X = (int) (8 * Gioco.SCALA);
        public static final int SQUALO_DRAWOFFSET_Y = (int) (6 * Gioco.SCALA);


        public static int GetContSprite (int tipoNemico, int statoNemico)
        {
            switch (statoNemico) {

                case IDLE ->
                {
                    if (tipoNemico == GRANCHIO)
                    {
                        return 9;
                    }
                    else if (tipoNemico == STELLA || tipoNemico == SQUALO)
                    {
                        return 8;
                    }
                }
                case CORSA ->
                {
                    return 6;
                }
                case ATTACCO ->
                {
                    if (tipoNemico == SQUALO)
                    {
                        return 8;
                    }

                    return 7;
                }
                case COLPO ->
                {
                    return 4;
                }
                case MORTE ->
                {
                    return 5;
                }
            }

            return 0;
        }
    }

    public static int GetVitaMax (int tipoNemico)
    {
        switch (tipoNemico)
        {
            case GRANCHIO ->
            {
                return 50;
            }

            case STELLA, SQUALO ->
            {
                return 25;
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

            case STELLA ->
            {
                return 20;
            }

            case SQUALO ->
            {
                return 25;
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
            public static final int LARGHEZZA_VOLUME_DEFAULT = 28;
            public static final int ALTEZZA_VOLUME_DEFAULT = 44;
            public static final int LARGHEZZA_CURSORE_DEFAULT = 215;
            //public static final int CURSORE_ALTEZZA_DEFAULT = 45;

            public static final int LARGHEZZA_VOLUME = (int) (LARGHEZZA_VOLUME_DEFAULT * Gioco.SCALA);
            public static final int ALTEZZA_VOLUME = (int) (ALTEZZA_VOLUME_DEFAULT * Gioco.SCALA);
            public static final int LARGHEZZA_CURSORE = (int) (LARGHEZZA_CURSORE_DEFAULT * Gioco.SCALA);
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
                default -> 1;
            };
        }
    }

    public static class CostantiOggetto
    {
        public static final int POZIONE_ROSSA = 0;
        public static final int POZIONE_BLU = 1;
        public static final int BARILE = 2;
        public static final int CASSA = 3;
        public static final int SPUNTONE = 4;
        public static final int CANNONE_SINISTRA = 5;
        public static final int CANNONE_DESTRA = 6;
        public static final int ALBERO_UNO = 7;
        public static final int ALBERO_DUE = 8;
        public static final int ALBERO_TRE = 9;


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

        public static final int LARGHEZZA_SPUNTONE_DEFAULT = 32;
        public static final int ALTEZZA_SPUNTONE_DEFAULT = 32;
        public static final int LARGHEZZA_SPUNTONE = (int) (Gioco.SCALA * LARGHEZZA_SPUNTONE_DEFAULT);
        public static final int ALTEZZA_SPUNTONE = (int) (Gioco.SCALA * ALTEZZA_SPUNTONE_DEFAULT);

        public static final int LARGHEZZA_CANNONE_DEFAULT = 40;
        public static final int ALTEZZA_CANNONE_DEFAULT = 26;
        public static final int LARGHEZZA_CANNONE = (int) (Gioco.SCALA * LARGHEZZA_CANNONE_DEFAULT);
        public static final int ALTEZZA_CANNONE = (int) (Gioco.SCALA * ALTEZZA_CANNONE_DEFAULT);

        public static int GetSpriteCont (int tipoOggetto)
        {
            return switch (tipoOggetto)
            {
                case POZIONE_ROSSA, POZIONE_BLU, CANNONE_SINISTRA, CANNONE_DESTRA -> 7;

                case BARILE, CASSA -> 8;

                default -> 1;
            };
        }

        public static int GetAlberoOffsetX(int tipoAlbero)
        {
            switch (tipoAlbero)
            {
                case ALBERO_UNO ->
                {
                    return (Gioco.DIMENSIONE_CASELLA / 2) - (GetLarghezzaAlbero (tipoAlbero) / 2);
                }
                case ALBERO_DUE ->
                {
                    return (int) (Gioco.DIMENSIONE_CASELLA / 2.5f);
                }
                case ALBERO_TRE ->
                {
                    return (int) (Gioco.DIMENSIONE_CASELLA / 1.65f);
                }
            }

            return 0;
        }

        public static int GetAlberoOffsetY(int tipoAlbero)
        {
            switch (tipoAlbero)
            {
                case ALBERO_UNO ->
                {
                    return -GetAltezzaAlbero(tipoAlbero) + Gioco.DIMENSIONE_CASELLA * 2;
                }
                case ALBERO_DUE , ALBERO_TRE ->
                {
                    return -GetAltezzaAlbero(tipoAlbero) + (int) (Gioco.DIMENSIONE_CASELLA / 1.25f);
                }
            }
            return 0;

        }

        public static int GetLarghezzaAlbero (int tipoAlbero)
        {
            switch (tipoAlbero)
            {
                case ALBERO_UNO ->
                {
                    return (int) (39 * Gioco.SCALA);
                }
                case ALBERO_DUE ->
                {
                    return (int) (62 * Gioco.SCALA);
                }
                case ALBERO_TRE ->
                {
                    return -(int) (62 * Gioco.SCALA);
                }
            }

            return 0;
        }

        public static int GetAltezzaAlbero(int tipoAlbero)
        {
            switch (tipoAlbero)
            {
                case ALBERO_UNO ->
                {
                    return (int) (int) (92 * Gioco.SCALA);
                }
                case ALBERO_DUE, ALBERO_TRE ->
                {
                    return (int) (54 * Gioco.SCALA);
                }
            }

            return 0;
        }
    }

    public static class Proiettili
    {
        public static final int LARGHEZZA_PALLA_CANNONE_DEFAULT = 15;
        public static final int ALTEZZA_PALLA_CANNONE_DEFAULT = 15;

        public static final int LARGHEZZA_PALLA_CANNONE = (int)(Gioco.SCALA * LARGHEZZA_PALLA_CANNONE_DEFAULT);
        public static final int ALTEZZA_PALLA_CANNONE = (int)(Gioco.SCALA * ALTEZZA_PALLA_CANNONE_DEFAULT);
        public static final float VEL_PALLA_CANNONE = 0.75f * Gioco.SCALA;
    }

    public static class Dialogo
    {
        public static final int DOMANDA = 0;
        public static final int ESCLAMAZIONE = 1;

        public static final int LARGHEZZA_DIALOGO = (int) (14 * Gioco.SCALA);
        public static final int ALTEZZA_DIALOGO = (int) (12 * Gioco.SCALA);

        public static int GetSpriteCont (int tipo)
        {
            switch (tipo)
            {
                case DOMANDA, ESCLAMAZIONE ->
                {
                    return 5;
                }
            }

            return 0;
        }
    }
}
