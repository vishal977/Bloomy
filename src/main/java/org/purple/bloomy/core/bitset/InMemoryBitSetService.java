package org.purple.bloomy.core.bitset;

import java.util.BitSet;

public class InMemoryBitSetService implements BitSetService {

    private final BitSet bitSet;

    public InMemoryBitSetService(int size) {
        bitSet = new BitSet(size);
    }

    @Override
    public boolean get(int index) {
        return bitSet.get(index);
    }

    @Override
    public void set(int index) {
        bitSet.set(index);
    }
}
