/* Copyright (C) 2017  Intel Corporation
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 only, as published by the Free Software Foundation.
 * This file has been designated as subject to the "Classpath"
 * exception as provided in the LICENSE file that accompanied
 * this code.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License version 2 for more details (a copy
 * is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this program; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */


package lib.util.persistent;

import lib.util.persistent.types.ArrayType;
import lib.util.persistent.types.ReferenceArrayType;
import lib.util.persistent.types.Types;

import java.util.Arrays;

public final class PersistentByteArray extends AbstractPersistentMutableArray {
    private static final ArrayType<PersistentByteArray> TYPE = new ReferenceArrayType<>(PersistentByteArray.class, Types.BYTE);

    public PersistentByteArray(int size) {
        super(TYPE, size);
    }

    public PersistentByteArray(byte[] array) {
        this(array.length);
        for (int i = 0; i < array.length; i++) setByteElement(i, array[i]);
    }

    private PersistentByteArray(ObjectPointer<PersistentByteArray> pointer) {
        super(pointer);
    }

    public byte get(int index) {
        return getByteElement(index);
    }

    public void set(int index, byte value) {
        setByteElement(index, value);
    }

    public byte[] toArray() {
        byte[] ans = new byte[length()];
        PersistentArrays.toByteArray(this, ans, ans.length);
        return ans;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersistentByteArray)) {
            return false;
        }
        PersistentByteArray o = (PersistentByteArray) obj;
        if (length() != o.length()) {
            return false;
        }
        for (int i = 0; i < length(); i++) {
            if (get(i) != o.get(i)) {
                return false;
            }
        }
        return true;
    }
}