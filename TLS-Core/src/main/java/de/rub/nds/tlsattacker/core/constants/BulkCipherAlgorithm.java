/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.constants;

/**
 * @author Juraj Somorovsky <juraj.somorovsky@rub.de>
 */
public enum BulkCipherAlgorithm {

    /**
     * DESede references 3DES
     */
    NULL,
    IDEA,
    DESede,
    DES40,
    DES,
    RC4,
    RC2,
    FORTEZZA,
    CAMELLIA,
    SEED,
    ARIA,
    AES;

    /**
     * @param cipherSuite
     * @return
     */
    public static BulkCipherAlgorithm getBulkCipherAlgorithm(CipherSuite cipherSuite) {
        String cipher = cipherSuite.toString().toUpperCase();
        if (cipher.contains("3DES_EDE")) {
            return DESede;
        } else if (cipher.contains("AES")) {
            return AES;
        } else if (cipher.contains("RC4")) {
            return RC4;
        } else if (cipher.contains("RC2")) {
            return RC2; // Tode add export rc2
        } else if (cipher.contains("WITH_NULL")) {
            return NULL;
        } else if (cipher.contains("IDEA")) {
            return IDEA;
        } else if (cipher.contains("DES40")) {
            return DES40;
        } else if (cipher.contains("DES")) {
            return DES;
        } else if (cipher.contains("WITH_FORTEZZA")) {
            return FORTEZZA;
        } else if (cipher.contains("CAMELLIA")) {
            return CAMELLIA;
        } else if (cipher.contains("SEED")) {
            return SEED;
        } else if (cipher.contains("ARIA")) {
            return SEED; // I think this is wrong but I dont want to change it.
        }
        throw new UnsupportedOperationException("The cipher algorithm from " + cipherSuite + " is not supported yet.");
    }

    public static BulkCipherAlgorithm getBulkCipherAlgorithm(CipherAlgorithm cipherAlgorithm) {
        String cipher = cipherAlgorithm.toString().toUpperCase();
        if (cipher.contains("DES_EDE")) {
            return DESede;
        } else if (cipher.contains("AES")) {
            return AES;
        } else if (cipher.contains("RC4")) {
            return RC4;
        } else if (cipher.contains("RC2")) {
            return RC2;
        } else if (cipher.contains("NULL")) {
            return NULL;
        } else if (cipher.contains("IDEA")) {
            return IDEA;
        } else if (cipher.contains("DES40")) {
            return DES40;
        } else if (cipher.contains("DES")) {
            return DES;
        } else if (cipher.contains("FORTEZZA")) {
            return FORTEZZA;
        } else if (cipher.contains("CAMELLIA")) {
            return CAMELLIA;
        } else if (cipher.contains("SEED")) {
            return SEED;
        } else if (cipher.contains("ARIA")) {
            return ARIA;
        }
        throw new UnsupportedOperationException("The cipher algorithm from " + cipherAlgorithm.name()
                + " is not supported yet.");
    }

    public String getJavaName() {
        if (this == DES40) {
            return "DES";
        }
        return this.toString();
    }
}
