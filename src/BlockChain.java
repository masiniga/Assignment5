import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * This class constructs the linked list which contains the blockchain
 *
 */
public class BlockChain {
  Node<Block> first; //First node in list
  Node<Block> last; //last node in list

  /**
   * Constructor for BlockChain
   * @param initial - initial amount of money person a possesses
   * @throws NoSuchAlgorithmException
   */
  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first = new Node(new Block(0, initial, new Hash(null)), null);
    this.last = this.first;
  }

  /**
   * Mines a new candidate block
   * @param amount - amount of money transfered 
   * @return new valid block
   * @throws NoSuchAlgorithmException
   */
  public Block mine(int amount) throws NoSuchAlgorithmException {
    return new Block(this.last.value.getNum() + 1, amount, this.last.value.getHash());
  }

  /**
   * @return current size of list
   */
  public int getSize() {
    return last.value.getNum() + 1;
  }

  /**
   * Adds a new block to the chain
   * @param blk - a valid block
   * @throws NoSuchAlgorithmException
   */
  public void append(Block blk) throws NoSuchAlgorithmException {
    Hash h = blk.createHash(blk.getNum(), blk.getAmount(), last.value.getHash(), blk.getNonce());
    if (!(blk.getHash().equals(h)) || !(h.isValid())) { //validates new block
      throw new IllegalArgumentException();
    }
    Node<Block> newBlock = new Node<Block>(blk, null);
    if (this.last.value.getNum() == 0) { //adds block to list
      this.last = newBlock;
      this.first.next = last;
    } else {
      this.last.next = newBlock;
      this.last = newBlock;
    }
  }

  /**
   * Removes the most recently added block
   * @return boolean false if there are no blocks remaining
   */
  public boolean removeLast() {
    if (this.last.value.getNum() == 0) { //checks if there are any blocks
      return false;
    } else {
      Node<Block> temp = first;
      while (temp.next.next != null) { //moves to end of list
        temp = temp.next;
      }
      this.last = temp; //removes block
      this.last.next = null; 
      return true;
    }
  }

  /**
   * @return hash of most recent block
   */
  public Hash getHash() {
    return last.value.getHash();
  }

  /**
   * validates block chain
   * @return boolean true if block chain is valid
   * @throws NoSuchAlgorithmException
   */
  public boolean isValidBlockChain() throws NoSuchAlgorithmException {
    Node<Block> temp = first;
    int a = 0;
    int b = first.value.getAmount();
    while (temp != null) { //moves through list
      int amt = temp.value.getAmount();
      a += amt;
      b -= amt;
      if (!temp.value.getHash().equals(temp.value.createHash(temp.value.getNum(), //checks hash value against previous hash
          temp.value.getAmount(), temp.value.getPrevHash(), temp.value.getNonce()))) {
        return false;
      } else if (!temp.value.getHash().isValid()) { //checks hash validity
        return false;
      }
      if (a < 0 || b < 0) { //checks account values
        return false;
      }
      temp = temp.next;
    }
    return true;
  }

  /**
   * prints the current balance of person a and person b
   */
  public void printBalance() {
    int a = 0;
    int b = first.value.getAmount();
    Node<Block> temp = first;
    while (temp != null) { //iterates through list collecting transaction amounts
      int amt = temp.value.getAmount();
      a += amt;
      b -= amt;
      temp = temp.next;
    }
    System.out.println("Alice: " + a + ", Bob: " + b); //prints amounts 
  }

  /**
   * @return string describing the block chain
   */
  public String toString() { 
    String s = "";
    Node temp = first;
    for (int i = 0; i < getSize(); i++) {
      s += ("\n" + temp.value.toString());
      temp = temp.next;
    }
    return s;
  }
}


/**
 * Node code was taken from code given in class
 */
class Node<T> {
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The value stored in the ndoe.
   */
  T value;

  /**
   * The next node.
   */
  Node<T> next;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new node that contains val and that links to next.
   */
  public Node(T value, Node<T> next) {
    this.value = value;
    this.next = next;
  } // Node(T, Node<T>)
} // class Node<T>


