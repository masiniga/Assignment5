import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * This class represents a single block in a block chain
 */
public class Block {
  private int num;// index of block
  private int amount;// amount transferred
  private Hash prevHash;// previous hash
  private long nonce;// nonce for finding hash
  private Hash hash;// current hash

  /**
   * Constructs a block and finds the nonce
   * 
   * @param num number of block
   * @param amount transferred
   * @param prevHash previous hash
   * @throws NoSuchAlgorithmException
   */
  public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    byte[] temp = ByteBuffer.allocate(8).putInt(num).putInt(amount).array();
    long i = 0;
    byte[] tempNonce = null;
    byte[] tempHash = null;
    Hash h = null;
    boolean run = true;
    while (run) {
      MessageDigest md = MessageDigest.getInstance("sha-256");
      md.update(temp);
      if (prevHash.getData() != null) {
        md.update(prevHash.getData());
      }
      tempNonce = ByteBuffer.allocate(8).putLong(i).array();
      md.update(tempNonce);
      tempHash = md.digest();
      h = new Hash(tempHash);
      if (h.isValid()) {
        run = false;
        break;
      }
      i++;
    }
    ByteBuffer b = ByteBuffer.wrap(tempNonce);
    this.nonce = b.getLong();
    this.hash = h;
  }

  /**
   * constructs a block based on a given nonce
   * 
   * @param num
   * @param amount
   * @param prevHash
   * @param nonce
   * @throws NoSuchAlgorithmException
   */
  public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
    this.num = num;
    this.amount = amount;
    this.prevHash = prevHash;
    this.nonce = nonce;
    this.hash = createHash(this.num, this.amount, this.prevHash, this.nonce);
  }

  /**
   * creates a hash based on num, amount, prevHash and nonce
   * 
   * @param num
   * @param amount
   * @param prevHash
   * @param nonce
   * @return a hash based on entered values
   * @throws NoSuchAlgorithmException
   */
  public Hash createHash(int num, int amount, Hash prevHash, long nonce)
      throws NoSuchAlgorithmException {
    byte[] temp = ByteBuffer.allocate(8).putInt(num).putInt(amount).array();
    MessageDigest md = MessageDigest.getInstance("sha-256");
    md.update(temp);
    if (prevHash.getData() != null) {
      md.update(prevHash.getData());
    }
    byte[] tempNonce = ByteBuffer.allocate(8).putLong(nonce).array();
    md.update(tempNonce);
    byte[] tempHash = md.digest();
    return new Hash(tempHash);
  }


  /**
   * @return the block number
   */
  public int getNum() {
    return this.num;
  }

  /**
   * @return amount transferred
   */
  public int getAmount() {
    return this.amount;
  }

  /**
   * @return the nonce
   */
  public long getNonce() {
    return this.nonce;
  }

  /**
   * @return the previous hash
   */
  public Hash getPrevHash() {
    return this.prevHash;
  }

  /**
   * @return current hash
   */
  public Hash getHash() {
    return this.hash;
  }

  /**
   * a string of the values stored in the block in the required format
   */
  public String toString() {
    return "Block " + this.num + " (Ammount: " + this.amount + ", Nonce: " + this.nonce
        + ", prevHash: " + this.prevHash + ", hash: " + this.hash + ")";
  }
}
