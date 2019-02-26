import java.util.Arrays;

public class Hash {
  private byte[] data;
  public Hash(byte[] data) {
    this.data = data;
  }
  public byte[] getData() {
    return this.data;
  }
  public boolean isValid() {
    return (data[0] == 0) && (data[1] == 0) && (data[2]==0);
  }

  public String toString() {
    String h = "";
    for (int i = 0; i < data.length; i++) {
      h += String.format("%x", Byte.toUnsignedInt(data[i]));
    }
    return h;
  }
  public boolean equals(Object other) {
    return (other instanceof Hash) && 
        (Arrays.equals(((Hash) other).getData(), this.getData()));
  }
  
}
