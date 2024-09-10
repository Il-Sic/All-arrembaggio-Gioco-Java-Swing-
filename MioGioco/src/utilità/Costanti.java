package utilità;

import main.Gioco;

public class Costanti
{
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
        public static final int SINSTRA = 0;
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
        public static final int TERRENO = 4;
        public static final int COLPO = 5;
        public static final int ATTACCO_1 = 6;
        public static final int ATTACCO_SALTO_1 = 7;
        public static final int ATTACCO_SALTO_2 = 8;

        public static int GetSpriteCont (int azione_giocatore)
        {
            return switch (azione_giocatore)
            {
                case CORSA -> 6;
                case IDLE -> 5;
                case COLPO -> 4;
                case SALTO, ATTACCO_1, ATTACCO_SALTO_1, ATTACCO_SALTO_2 -> 3;
                case TERRENO -> 2;
                default -> 1;                   // e anche CADUTA
            };
        }
    }
}
