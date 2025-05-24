package org.purple.bloomy.core.hashing;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Murmur3HashingService implements BloomyHashingService {

    private static final HashFunction MURMUR3 = Hashing.murmur3_32_fixed();

    @Override
    public int getHash(String plainText) {
        return MURMUR3.hashString(plainText, StandardCharsets.UTF_8).asInt();
    }
}
