package org.purple.bloomy.core.hashing;

import org.purple.bloomy.core.exception.BloomyHashingException;

public interface BloomyHashingService {
    int getHash(String plainText) throws BloomyHashingException;
}
