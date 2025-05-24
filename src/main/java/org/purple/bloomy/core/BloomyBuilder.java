package org.purple.bloomy.core;

import org.purple.bloomy.core.exception.DuplicateHashFunctionException;
import org.purple.bloomy.core.hashing.BloomyHashingService;
import org.purple.bloomy.core.hashing.HashingAlgorithm;
import org.purple.bloomy.core.hashing.JavaHashcodeHashingService;
import org.purple.bloomy.core.hashing.Murmur3HashingService;
import org.purple.bloomy.core.hashing.factory.BloomyHashServiceFactory;

import java.util.Objects;
import java.util.function.Function;

public class BloomyBuilder<T> {

    private int filterSize = 10000;
    private int numberOfHashes = 5;
    private Function<T, String> objectSerializer = Object::toString;
    private HashingAlgorithm primaryHashAlgo;
    private HashingAlgorithm secondaryHashAlgo;
    private BloomyHashingService primaryHashService = new JavaHashcodeHashingService();
    private BloomyHashingService secondaryHashService = new Murmur3HashingService();

    public static <T> BloomyBuilder<T> getWithDefaults() {
        return new BloomyBuilder<>();
    }

    public BloomyBuilder<T> filterSize(int filterSize) {
        this.filterSize = filterSize;
        return this;
    }

    public BloomyBuilder<T> numberOfHashes(int numberOfHashes) {
        this.numberOfHashes = numberOfHashes;
        return this;
    }

    public BloomyBuilder<T> objectSerializer(Function<T, String> serializer) {
        this.objectSerializer = serializer;
        return this;
    }

    public BloomyBuilder<T> primaryHashService(HashingAlgorithm primaryHashAlgo) throws DuplicateHashFunctionException {
        if (Objects.nonNull(secondaryHashAlgo) && secondaryHashAlgo == primaryHashAlgo) {
            throw new DuplicateHashFunctionException("Primary hash algorithm cannot be the same as the secondary");
        }
        this.primaryHashService = BloomyHashServiceFactory.getHashingService(primaryHashAlgo);
        this.primaryHashAlgo = primaryHashAlgo;
        return this;
    }

    public BloomyBuilder<T> secondaryHashService(HashingAlgorithm secondaryHashAlgo) throws DuplicateHashFunctionException {
        if (Objects.nonNull(primaryHashAlgo) && primaryHashAlgo == secondaryHashAlgo) {
            throw new DuplicateHashFunctionException("Secondary hash algorithm cannot be the same as the primary");
        }
        this.secondaryHashService = BloomyHashServiceFactory.getHashingService(secondaryHashAlgo);
        this.secondaryHashAlgo = secondaryHashAlgo;
        return this;
    }

    public Bloomy<T> build() {
        return new Bloomy<>(filterSize, numberOfHashes, objectSerializer, primaryHashService, secondaryHashService);
    }
}
