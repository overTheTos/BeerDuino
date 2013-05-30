/*
 *  Copyright 2005, 2006, 2007 Alessandro Chiari.
 *
 *  This file is part of Hobbybrew.
 *
 *  Hobbybrew is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Hobbybrew is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Hobbybrew; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package it.vupo.beerduino.recipe.data;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *  Classe contenente alcuni funzioni utilizzare per la lettura della ricetta (modifica da HobbyBrew)
 *
 * @author Alessandro Morniroli
 */
public abstract class Utils {

    public static String arr2String(byte[] arr, int start) {
        int offset = 0;
        while (arr[start + offset] != 0) {
            offset++;
        }
        return new String(arr, start, offset);
    }

    public static byte arr2Byte(byte[] arr, int start) {

        return arr[start];
    }

    public static float arr2float(byte[] arr, int start) {
        int i = 0;
        int len = 4;
        int cnt = 0;
        byte[] tmp = new byte[len];
        for (i = start; i < (start + len); i++) {
            tmp[cnt] = arr[i];
            cnt++;
        }
        int accum = 0;
        i = 0;
        for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
            accum |= ((long) (tmp[i] & 0xff)) << shiftBy;
            i++;
        }
        return Float.intBitsToFloat(accum);
    }

    public static double arr2Double(byte[] arr, int start) {
        float f = arr2float(arr, start);
        return new Double(f);
    }

    public static byte[] buffer(String filename) throws Exception {
        File file = new File(filename);
        InputStream is = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(is);
        long length = file.length();
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        dis.close();
        is.close();
        return bytes;
    }

    public static double F2C(double f) {
        return (f - 32) * 5 / 9;
    }
}
