import java.util.Scanner;


public class BlockChainDriver {

  /**
   * main driver for the block chain command interface
   */
  public static void main(String[] args) throws Exception {
    //Check for valid arguments
    if (args.length != 1) {
      throw new Exception("Invalid input!");
    }

    //Create new BlockChain
    BlockChain bc = new BlockChain(new Integer(args[0]));
    
    //Loop through commands until 'quit' is called
    boolean run = true;
    while (run) {
      System.out.println(bc.toString()); //Prompt for a command
      System.out.print("Command? ");
      Scanner in = new Scanner(System.in); //Set up scanner
      String com = in.next();
      
      //Check against each valid command and execute appropriate steps
      if (com.equalsIgnoreCase("help")) { //return info about valid commands
        System.out.println("Valid commands:");
        System.out.println("    mine: discovers the nonce for a given transaction");
        System.out.println("    append: appends a new block onto the end of the chain");
        System.out.println("    remove: removes the last block from the end of the chain");
        System.out.println("    check: checks that the block chain is valid");
        System.out.println("    report: reports the balances of Alice and Bob");
        System.out.println("    help: prints this list of commands");
        System.out.println("    quit: quits the program");
      } else if (com.equalsIgnoreCase("mine")) {
        System.out.print("Amount transferred? "); //return a new valid block
        int amt = in.nextInt();
        Block mb = bc.mine(amt);
        System.out.println("amount = " + mb.getAmount() + ", nonce = " + mb.getNonce());
      } else if (com.equalsIgnoreCase("append")) { //add a new block to the chain once the nonce is known
        System.out.print("Amount transferred? ");
        int amt = in.nextInt();
        System.out.print("Nonce? ");
        long n = in.nextLong();
        Block ab = new Block(bc.getSize(), amt, bc.last.value.getHash(), n);
        bc.append(ab);
      } else if (com.equalsIgnoreCase("remove")) { //remove the most recently added block, if possible
        boolean rem = bc.removeLast();
        if (rem) {
          System.out.println("The block was successfully removed.");
        } else {
          System.out.println("This block cannot be removed.");
        }
      } else if (com.equalsIgnoreCase("check")) { //check if current chain is valid
        if (bc.isValidBlockChain()) {
          System.out.println("Chain is valid!");
        } else {
          System.out.println("Chain is not valid!");
        }
      } else if (com.equalsIgnoreCase("report")) { //report current balance
        bc.printBalance();
      } else if (com.equalsIgnoreCase("quit")) { //quit loop
        run = false;
        break;
      } else { //default for invalid commands
        System.out.println("Sorry, that is an invalid command.");
        System.out.println("Enter 'help' to find valid commands.");
      }

    } // while


  }

}
