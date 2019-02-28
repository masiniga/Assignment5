import java.util.Scanner;

// FIX REMOVE AND PRINTBALANCE
public class BlockChainDriver {

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      throw new Exception("Invalid input!");
    }

    BlockChain bc = new BlockChain(new Integer(args[0]));
    boolean run = true;
    while (run) {
      System.out.println(bc.toString());
      System.out.print("Command? ");
      Scanner in = new Scanner(System.in);
      String com = in.next();
      if (com.equalsIgnoreCase("help")) {
        System.out.println("Valid commands:");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    report: reports the balances of Alice and Bob");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program");
      } else if (com.equalsIgnoreCase("mine")) {
        System.out.print("Amount transferred? ");
        int amt = in.nextInt();
        Block mb = bc.mine(amt);
        System.out.println("amount = " + mb.getAmount() + ", nonce = " + mb.getNonce());
      } else if (com.equalsIgnoreCase("append")) {
        System.out.print("Amount transferred? ");
        int amt = in.nextInt();
        System.out.print("Nonce? ");
        long n = in.nextLong();
        Block ab = new Block(bc.getSize(), amt, bc.last.value.getHash(), n);
        bc.append(ab);
      } else if (com.equalsIgnoreCase("remove")) {
        boolean rem = bc.removeLast();
        if (rem) {
          System.out.println("The block was successfully removed.");
        } else {
          System.out.println("This block cannot be removed.");
        }
      } else if (com.equalsIgnoreCase("check")) {
        if (bc.isValidBlockChain()) {
          System.out.println("Chain is valid!");
        } else {
          System.out.println("Chain is not valid!");
        }
      } else if (com.equalsIgnoreCase("report")) {
        bc.printBalance();
      } else if (com.equalsIgnoreCase("quit")) {
        run = false;
        break;
      } else {
        System.out.println("Sorry, that is an invalid command.");
        System.out.println("Enter 'help' to find valid commands.");
      }

    } // while


  }

}
