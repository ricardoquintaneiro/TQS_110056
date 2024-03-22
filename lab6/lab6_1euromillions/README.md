#### e) Project analysis

The project initially passed the quality gate, even though it has a "Security Hotspot" with review priority minimum because of a pseudorandom number generator that might not be safe. It also has 23 open issues dealing with maintainability.

Details:

    Security - 0
    Reliability - 0
    Maintainability - 23
    Coverage - 75%
    Duplication - 0.0%
    Security Hotspots - 1


#### f) Fixing issues

| Issue | Problem description | How to solve |
| ----- | ------------------- | ------------ |
| Security hotspot | Weak Cryptography - Make sure that using this pseudorandom number generator is safe here | Use a cryptographically strong random number generator (RNG) like "java.security.SecureRandom" in place of this PRNG  


Using the SecureRandom class instead of just Random fixed the Security Hotspot:

Details of new analysis:

    Security - 0
    Reliability - 0
    Maintainability - 22
    Coverage - 74.5%
    Duplication - 0.0%
    Security Hotspots - 0