package ca.mcgill.purposeful.configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/** Class contains the RSA public and private keys for JWT */
public class JWTKey {

  private RSAPrivateKey privateKey;
  private RSAPublicKey publicKey;

  /**
   * Constructor for JWTKey
   * @param privateKey the private key
   * @param publicKey the public key
   */
  public JWTKey(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  /**
   * Get the private key
   * @return the private key
   */
  public RSAPrivateKey getPrivateKey() {
    return privateKey;
  }

    /**
     * Set the private key
     * @param privateKey the private key
     */
  public void setPrivateKey(RSAPrivateKey privateKey) {
    this.privateKey = privateKey;
  }

    /**
     * Get the public key
     * @return the public key
     */
  public RSAPublicKey getPublicKey() {
    return publicKey;
  }

    /**
     * Set the public key
     * @param publicKey the public key
     */
  public void setPublicKey(RSAPublicKey publicKey) {
    this.publicKey = publicKey;
  }
}
