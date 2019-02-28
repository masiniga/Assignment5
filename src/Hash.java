import java.util.Arrays;

/**
 * This class stores the hash as a byte array
 */
public class Hash {
  private byte[] data;// byte array of hash values

  /**
   * creates new hash
   * 
   * @param data byte array of hash values
   */
  public Hash(byte[] data) {
    this.data = data;
  }

  /**
   * @return data, the byte array of hash values
   */
  public byte[] getData() {
    return this.data;
  }

  /**
   * checks to see if the hash is valid by checking if the first three values are 0
   * 
   * @return if it is a valid hash
   */
  public boolean isValid() {
    return (data[0] == 0) && (data[1] == 0) && (data[2] == 0);
  }

  /**
   * creates a string of the hash in the required format
   */
  public String toString() {
    String h = "";
    if (this.data != null) {
      for (int i = 0; i < data.length; i++) {
        h += String.format("%x", Byte.toUnsignedInt(data[i]));
      }
    } else {
      h = "null";
    }
    return h;
  }

  /**
   * checks if two hashes are equal
   */
  public boolean equals(Object other) {
    return (other instanceof Hash) && (Arrays.equals(((Hash) other).getData(), this.getData()));
  }

}
