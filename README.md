# 🌸 Bloomy

**Bloomy** is a lightweight and customizable Bloom Filter implementated in Java.

## 🚀 Features
- Probabilistic set membership testing (`possiblyContains`)
- Supports **generic types**. All you have to do is define a custom serialization function!
- Uses **double hashing** for performance and simplicity
- Plug-and-play hash function support
- Redis integration for distributed operation (coming soon)
## Usage example
```
public void bloomyDemo() throws DuplicateHashFunctionException, BloomyHashingException {
        Bloomy<String> bloomy = new BloomyBuilder<String>()
                .primaryHashService(HashingAlgorithm.JAVA_HASHCODE)
                .secondaryHashService(HashingAlgorithm.MURMUR3)
                .numberOfHashes(5)
                .filterSize(10000)
                .bitSetStore(BloomyBitSetStore.IN_MEMORY)
                .objectSerializer(s -> s)
                .build();
        bloomy.register("randomString101");
        System.out.println(bloomy.possiblyContains("randomString101")); // Likely to be True
        System.out.println(bloomy.possiblyContains("randomString")); // False
}
```
## 📦 Redis Integration (Planned)
We’re working on support for storing and syncing the BitSet to Redis for scalable, distributed setups.
## 📚 References
- [How Bloom filters work](https://en.wikipedia.org/wiki/Bloom_filter)
- [Guava Hashing](https://github.com/google/guava/wiki/HashingExplained)
