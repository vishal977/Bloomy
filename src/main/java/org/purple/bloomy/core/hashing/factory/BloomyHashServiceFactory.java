package org.purple.bloomy.core.hashing.factory;

import org.purple.bloomy.core.hashing.*;

public class BloomyHashServiceFactory {

    public static BloomyHashingService getHashingService(HashingAlgorithm hashingAlgorithm) {
        return switch (hashingAlgorithm) {
            case MURMUR3 -> new Murmur3HashingService();
            case MD5 -> new Md5HashingService();
            default -> new JavaHashcodeHashingService();
        };
    }
}
