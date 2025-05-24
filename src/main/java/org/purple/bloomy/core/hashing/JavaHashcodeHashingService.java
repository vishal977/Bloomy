package org.purple.bloomy.core.hashing;

public class JavaHashcodeHashingService implements BloomyHashingService {

    @Override
    public int getHash(String plainText) {
        return plainText.hashCode();
    }
}
