package org.purple.bloomy.core;

import org.purple.bloomy.core.bitset.BitSetService;
import org.purple.bloomy.core.exception.BloomyHashingException;
import org.purple.bloomy.core.hashing.BloomyHashingService;

import java.util.function.Function;

public class Bloomy<T> {

    private final int filterSize;
    private final BitSetService bitSetService;
    private final int numberOfHashes;
    private final Function<T, String> objectSerializer;
    private final BloomyHashingService primaryHashService;
    private final BloomyHashingService secondaryHashService;

    Bloomy(int filterSize, int numberOfHashes, Function<T, String> objectSerializer,
           BloomyHashingService primaryHashService,
           BloomyHashingService secondaryHashService, BitSetService bitSetService) {
        this.filterSize = filterSize;
        this.bitSetService = bitSetService;
        this.numberOfHashes = numberOfHashes;
        this.objectSerializer = objectSerializer;
        this.primaryHashService = primaryHashService;
        this.secondaryHashService = secondaryHashService;
    }

    public void register(T item) throws BloomyHashingException {
        String serializedValue = objectSerializer.apply(item);
        int primaryHash = getPrimaryHash(serializedValue);
        int secondaryHash = getSecondaryHash(serializedValue);

        calculateCombinedHashAndSetBits(primaryHash, secondaryHash);
    }

    public boolean possiblyContains(T item) throws BloomyHashingException {
        String serializedValue = objectSerializer.apply(item);
        int primaryHash = getPrimaryHash(serializedValue);
        int secondaryHash = getSecondaryHash(serializedValue);

        for (int run = 0; run < numberOfHashes; run++) {
            int runningHash = Math.abs((primaryHash + (run * secondaryHash)) % filterSize);
            if (!bitSetService.get(runningHash)) {
                return false;
            }
        }
        return true;
    }

    private void calculateCombinedHashAndSetBits(int primaryHash, int secondaryHash) {
        for (int run = 0; run < numberOfHashes; run++) {
            int runningHash = Math.abs((primaryHash + (run * secondaryHash)) % filterSize);
            bitSetService.set(runningHash);
        }
    }

    private int getPrimaryHash(String plainText) throws BloomyHashingException {
        return primaryHashService.getHash(plainText);
    }

    private int getSecondaryHash(String plainText) throws BloomyHashingException {
        return secondaryHashService.getHash(plainText);
    }

}
