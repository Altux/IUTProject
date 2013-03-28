#include <windows.h>
#include "../lib/libvtplayer.h"

#define VTP_NO_CLIC          0
#define VTP_CLIC_DROIT_HAUT  1
#define VTP_CLIC_DROIT_BAS   2
#define VTP_CLIC_GAUCHE_BAS  4
#define VTP_CLIC_GAUCHE_HAUT 8

#define STATUS_LENGHT 4
//#define LIBUSB_ERROR_TIMEOUT 116

#define SLEEP_TIME 1000

int main(int argc, char** argv) {
    /*float v = 1.f;

    if (argc == 2) {
        sscanf(argv[1], "%f", &v);
        dprintf("vitesse augmente de : %f", v);
    }
#ifdef DEBUG
    else {
        dprintf("vitesse normal\n");
    }
#endif
     */

    // initialisation
    char last[STATUS_LENGHT] = {0};
    char new[STATUS_LENGHT] = {0};

    while (1) {
        while (vtplayer_open() < 0) {
            Sleep(SLEEP_TIME);
        }

        int ret = 0;

        // tant que la souris n'a pas été débrancher
        while (EIO != -ret /*LIBUSB_ERROR_TIMEOUT == -ret || ret >= 0 (-116 corespond a une erreur de timeout vérifier par rapport a la sortie de mise en veille)*/) {
            // si l'on a pu obtenir le statu de la souris
            if ((ret = vtplayer_get_status(new)) >= 0) {
                // si différent on bouge la souris
                if (last[0] != new[0] || last[1] != new[1]) {
#ifdef DEBUG
                    printf("coordonnee (%d, %d)\n", new[0], new[1]);
#endif
                    mouse_event(MOUSEEVENTF_MOVE, (long) new[0] /** v*/, (long) new[1] /** v*/, 0, 0);
                    // on recopie la nouvelle position du curseur dans l'ancienne.
                    last[0] = new[0];
                    last[1] = new[1];
                }

                if (last[3] != new[3]) {
                    // choix en fonction du bit de clic
                    switch ((int) new[3]) {

                            // Si il ni a pas de bouton presser
                        case VTP_NO_CLIC:
                        {
                            DWORD dwEventFlags;
                            switch ((int) last[3]) {
                                case VTP_CLIC_DROIT_BAS:
                                case VTP_CLIC_DROIT_HAUT:
#ifdef DEBUG
                                    printf("clic droit relacher\n");
#endif
                                    dwEventFlags = MOUSEEVENTF_RIGHTUP;
                                    break;

                                case VTP_CLIC_GAUCHE_BAS:
#ifdef DEBUG
                                    printf("clic gauche bas relacher (clic molette)\n");
#endif
                                    dwEventFlags = MOUSEEVENTF_MIDDLEUP;
                                    break;

                                case VTP_CLIC_GAUCHE_HAUT:
#ifdef DEBUG
                                    printf("clic gauche haut relacher\n");
#endif
                                    dwEventFlags = MOUSEEVENTF_LEFTUP;
                                    break;
                            }
                            // mise a jour des information
                            mouse_event(dwEventFlags, 0, 0, 0, 0);
                        }
                            break;

                            // Si c'est l'un des boutons droit
                        case VTP_CLIC_DROIT_BAS:
                        case VTP_CLIC_DROIT_HAUT:
#ifdef DEBUG
                            printf("clic droit presse\n");
#endif
                            mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0);
                            break;

                        case VTP_CLIC_GAUCHE_BAS:
#ifdef DEBUG
                            printf("clic gauche bas presse (clic molette)\n");
#endif
                            mouse_event(MOUSEEVENTF_MIDDLEDOWN, 0, 0, 0, 0);
                            break;

                        case VTP_CLIC_GAUCHE_HAUT:
#ifdef DEBU
                            printf("clic gauche presse\n");
#endif
                            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
                            break;
                    }

                    // copie du bit de clic
                    last[3] = new[3];
                }
            }
        }

        // libération des ressource
        vtplayer_close(); // release_vtplayer();
        vtplayer_handler = NULL;
    }
    return (EXIT_SUCCESS);
}

