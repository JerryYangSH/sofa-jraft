package com.alipay.sofa.jraft.rhea.storage;

import com.alipay.sofa.jraft.util.FastByteOperations;
import lib.util.persistent.ComparableWith;
import lib.util.persistent.ObjectPointer;
import lib.util.persistent.PersistentImmutableByteArray;
import lib.util.persistent.PersistentImmutableObject;
import lib.util.persistent.types.ObjectField;
import lib.util.persistent.types.ObjectType;

public final class PMemDecoratedKey extends PersistentImmutableObject implements Comparable<PMemDecoratedKey>,
        MDecoratedKey,
        ComparableWith<PMemDecoratedKey> {
    private static final ObjectField<PersistentImmutableByteArray> PARTITION_KEY_BUFFER = new ObjectField<>();

    private static final ObjectType<PMemDecoratedKey> TYPE = ObjectType.withFields(PMemDecoratedKey.class,
            PARTITION_KEY_BUFFER);

    // constructor
    public PMemDecoratedKey(byte[] partitionKeyBuffer) {
        super(TYPE, (PMemDecoratedKey self) -> {
            self.initObjectField(PARTITION_KEY_BUFFER, new PersistentImmutableByteArray(partitionKeyBuffer));
        });
    }

    // reconstructor
    @SuppressWarnings("unused")
    private PMemDecoratedKey(ObjectPointer<? extends PMemDecoratedKey> pointer) {
        super(pointer);
    }

    @Override
    public byte[] getKey() {
        return getObjectField(PARTITION_KEY_BUFFER).toArray();
    }

    public byte[] toArray() {
        return getKey();
    }

    @Override
    public int compareTo(PMemDecoratedKey pos) {
        if (this == pos) {
            return 0;
        }

        byte[] b1 = getKey();
        byte[] b2 = pos.getKey();
        //int cmp = getToken().compareTo(pos.getToken());
        //return cmp == 0 ? FastByteOperations.compareUnsigned(b1, 0, b1.length, b2, 0, b2.length) : cmp;
        return FastByteOperations.compareUnsigned(b1, 0, b1.length, b2, 0, b2.length);

    }

    @Override
    public int compareWith(PMemDecoratedKey pos) {
        byte[] b1 = getKey();
        byte[] b2 = pos.getKey();
        return FastByteOperations.compareUnsigned(b1, 0, b1.length, b2, 0, b2.length);
    }

    @Override
    public int hashCode() {
        byte[] key = getKey();
        int h = 1;
        int p = 0;
        for (int i = key.length - 1; i >= p; i--) {
            h = 31 * h + (int) key[i];
        }
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        // sanity checks
        if (obj == null) {
            return false;
        }

        if (obj instanceof PMemDecoratedKey) {
            if (this == obj) {
                return true;
            }
            return compareTo((PMemDecoratedKey) obj) == 0;
        }

        return false;
    }
}