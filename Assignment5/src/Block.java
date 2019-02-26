import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
  private int num;
  private int amount;
  private Hash prevHash;
  private long nonce;
  private Hash hash;

  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    byte[] temp = ByteBuffer.allocate(8).putInt(num).putInt(amount).array();
    long i= 0;
    byte[] tempNonce = null;
    byte[] tempHash = null;
    Hash h = null;
    boolean run = true;
    while (run) {
      MessageDigest md = MessageDigest.getInstance("sha-256");
      md.update(temp);
      md.update(prevHash.getData());
      tempNonce = ByteBuffer.allocate(8).putLong(i).array();
      md.update(tempNonce);
      tempHash = md.digest();
      h = new Hash(tempHash);
      if(h.isValid()) {
        run = false;
        break;
      }
      i++;
    }
    ByteBuffer b = ByteBuffer.wrap(tempNonce);
    this.nonce = b.getLong();
    this.hash = h;
  }

  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    this.hash = createHash(this.num, this.amount, this.prevHash, this.nonce);
  }
  public Hash createHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    byte[] temp = ByteBuffer.allocate(8).putInt(num).putInt(amount).array();
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(temp);
    md.update(prevHash.getData());
    byte[] tempNonce = ByteBuffer.allocate(8).putLong(nonce).array();
    md.update(tempNonce);
    byte[] tempHash = md.digest();
    return new Hash(tempHash);
  }
  

  /**
   * 
   * @return the block number
   */
  public int getNum() {
    return this.num;
  }

  public int getAmount() {
    return this.amount;
  }

  public long getNonce() {
    return this.nonce;
  }

  public Hash getPrevHash() {
    return this.prevHash;
  }

  public Hash getHash() {
    return this.hash;
  }

  public String toString() {
    return "Block " + this.num + " (Ammount: " + this.amount + ", Nonce: " + this.nonce
        + ", prevHash: " + this.prevHash + ", hash: " + this.hash + ")";
  }
}
