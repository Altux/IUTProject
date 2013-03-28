/*
 * Copyright (C) 2004-2007 Christophe Jacquet <jacquetc@free.fr>
 *
 * See <vtplayer.c> and the associated documentation for more information.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */


#ifndef _LIBVTPLAYER_H
#define _LIBVTPLAYER_H

#include <stdio.h>

#define NULL_BYTE       0xff
#define VTP_DATA_LENGTH 4

typedef unsigned char BYTE;
typedef unsigned short int u16;
typedef unsigned long int u32;

#ifdef __linux
#include "../driver/vtplayer.h"
#define VTPLAYER int

/**
 * Cette fonction permet d'ouvrir le fichier de la souris pour lever les picots.
 * @param device chemin vers le fichier
 * @return -1 en cas d'erreur.
 */
int vtplayer_open(const char *device);
#elif _WIN32
#include <errno.h>
#include <string.h>

#define VTP_ERROR_OPENING                       -1
#define VTP_ERROR_SETTING_CONFIGURATION         -2
#define VTP_ERROR_CLAIMING_INTERFACE            -3
#define VTP_ERROR_SETTING_ALTERNATE_INTERFACE   -4
#define VTP_NOT_FOUND                           -5

#define VTP_ERROR_USB_RELEASE                   -1
#define VTP_ERROR_USB_CLOSE                     -2

/**
 * Définition de la structure pour la lecture / écriture de la souris.
 */
#define VTPLAYER struct usb_dev_handle*

/**
 * Initialise le VTPlayer en scannant tous les ports USB
 * @return -1 en cas d'erreur.
 */
int vtplayer_open();

/**
 * 
 * 1er byte: x déplacement depuis le dernier événement.
 * 2eme byte: y déplacement depuis le dernier événement.
 * 3eme byte: inutilisé/inconnu
 * 4eme byte: bouton utilisé (1 à 4)
 * @param vtplayer_handle
 * @param data set x,y mouvements et boutons préssés
 * @return error si il y au moins une erreur, 0 sinon
 */
int vtplayer_get_status(char * data);
#endif

extern VTPLAYER vtplayer_handler;

/**
 * On n'utilise plus cette fonction. Voir set_pads_config
 * exect si vous ne voulez pas utiliser un sous-processus.
 * transforme cette configuration en une configuration compréhensible par la souris. <br/>
 *  <br/>
 * format: b32...b1 : <br/>
 * +----+----+----+----+     +----+----+----+----+ <br/>
 * |  1 |  2 |  3 |  4 |     | 17 | 18 | 19 | 20 | <br/>
 * +----+----+----+----+     +----+----+----+----+ <br/>
 * |  5 |  6 |  7 |  8 |     | 21 | 22 | 23 | 24 | <br/>
 * +----+----+----+----+     +----+----+----+----+ <br/>
 * |  9 | 10 | 11 | 12 |     | 25 | 26 | 27 | 28 | <br/>
 * +----+----+----+----+     +----+----+----+----+ <br/>
 * | 13 | 14 | 15 | 16 |     | 29 | 30 | 31 | 32 | <br/>
 * +----+----+----+----+     +----+----+----+----+ <br/>
 * @param configuration de pads voir VTP_DATA_LENGTH
 * @return error si il y au moins une erreur, 0 sinon
 */
int vtplayer_buffer_set(void *buffer);

/**
 * On n'utilise plus cette fonction. voir set_pads_config
 * 
 * @param vtplayer_handle
 * @param b1
 * @param b2
 * @param b3
 * @param b4
 * @return -1 en cas d'erreur.
 */
int vtplayer_set(BYTE b1, BYTE b2, BYTE b3, BYTE b4);

/**
 * On appelle cette fonction quand le VTPlayer n'est plus utilisé.
 */
int vtplayer_close();

#endif /* _LIBVTPLAYER_H */
