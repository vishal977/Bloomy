package org.purple.bloomy.core.bitset;

import java.util.Objects;

public class BitSetFactory {

    public static BitSetService getService(BloomyBitSetStore bitSet, int size) {
        if (Objects.requireNonNull(bitSet) == BloomyBitSetStore.IN_MEMORY) {
            return new InMemoryBitSetService(size);
        } else {
            throw new IllegalStateException("Unsupported bitset service");
        }
    }
}
