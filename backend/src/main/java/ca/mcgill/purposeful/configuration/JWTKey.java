package ca.mcgill.purposeful.configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Class containts the RSA public and private keys for JWT
 */
public class JWTKey {

  private RSAPrivateKey privateKey;
  private RSAPublicKey publicKey;

  public JWTKey(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
    this.privateKey = privateKey;
    this.publicKey = publicKey;
  }

  public RSAPrivateKey getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(RSAPrivateKey privateKey) {
    this.privateKey = privateKey;
  }

  public RSAPublicKey getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(RSAPublicKey publicKey) {
    this.publicKey = publicKey;
  }
}