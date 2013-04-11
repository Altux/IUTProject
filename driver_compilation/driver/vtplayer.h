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

#ifndef _VTPLAYER_H
#define _VTPLAYER_H

/*
 * CMD_SET_PADS is the only ioctl() command used to control the mouse.
 * It simply sets the configuration of the tactile pads on the VTPlayer.
 * The configuration of the 32 pins is described in a 4-bytes buffer. See
 * the documentation for more information.
 */
#define VTP_CMD_SET_PADS 1

#endif /* _VTPLAYER_H */
