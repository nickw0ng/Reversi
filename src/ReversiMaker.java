package cs3500.reversi;

import java.io.IOException;
import java.util.Scanner;

import cs3500.reversi.controller.IController;
import cs3500.reversi.controller.ProviderController;
import cs3500.reversi.controller.ReversiController;
import cs3500.reversi.model.ProviderAdapter;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.ToProvider;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.provider.view.GUIReversiView;
import cs3500.reversi.provider.view.ReversiView;
import cs3500.reversi.strategy.Strategies;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ReversiGraphicsView;

/**
 * Makes a reversi game.
 */
public class ReversiMaker {

  private Readable readable;
  private Appendable appendable;
  private ProviderAdapter model;

  /**
   * Constructs a Reversi maker.
   *
   * @param r the readable input
   * @param a the appendable output
   */
  public ReversiMaker(Readable r, Appendable a) {
    if ((r == null) || (a == null)) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.readable = r;
    this.appendable = a;
    this.model = new ToProvider();
  }

  /**
   * The primary method for beginning and playing a game.
   *
   * @throws IOException If the Appendable object is unable to transmit output
   */
  public void playGame() throws IOException {
    Scanner sc = new Scanner(this.readable);
    this.appendable.append("There are 6 strategies for player 1:\n" +
            Strategies.asAList(false) + "\n" +
            "You may start the game by typing the corresponding number\n" +
            "Only valid numbers typed will be chosen (1 - 6), all others will be ignored \n" +
            "You make type your response here:");
    if (sc.hasNextLine()) {
      String line = sc.nextLine().toLowerCase();
      Scanner lineScanner = new Scanner(line);
      this.model.addPlayer(addInt(lineScanner, false));
    }

    IView view1 = new ReversiGraphicsView(model);
    IController controller1 = new ReversiController(model, this.model.getPlayer1(), view1);

    this.appendable.append("Would you like to use the original code or the provider?:\n" +
            "Type 1 for the original code or 2 for the provider code.");
    boolean ogView = false;
    if (sc.hasNextLine()) {
      String line = sc.nextLine().toLowerCase();
      Scanner lineScanner = new Scanner(line);
      ogView = addIntForView(lineScanner);
    }

    if (ogView) {
      this.appendable.append("There are 6 strategies for player 2:\n"
              + Strategies.asAList(false) + "\n" +
              "You may start the game by typing the corresponding number\n" +
              "Only valid numbers typed will be chosen (1 - 6), all others will be ignored \n" +
              "You make type your response here:");
      if (sc.hasNextLine()) {
        String line = sc.nextLine().toLowerCase();
        Scanner lineScanner = new Scanner(line);
        this.model.addPlayer(addInt(lineScanner, false));
      }
      IView view2 = new ReversiGraphicsView(model);
      IController controller2 = new ReversiController(model, this.model.getPlayer2(), view2);
    } else {
      this.appendable.append("There are 5 strategies for player 1:\n" + Strategies.asAList(true) +
              "\n" + "You may start the game by typing the corresponding number\n" +
              "Only valid numbers typed will be chosen (1 - 5), all others will be ignored \n" +
              "You make type your response here:");
      if (sc.hasNextLine()) {
        String line = sc.nextLine().toLowerCase();
        Scanner lineScanner = new Scanner(line);
        this.model.addPlayer(addInt(lineScanner, true));
      }
      ReversiView view2 = new GUIReversiView(model);
      IController controller2 = new ProviderController(model, this.model.getPlayer2(), view2);

    }
    this.model.play();
  }

  public ReversiModel getModel() {
    return this.model;
  }

  /**
   * Gets the next valid int in a scanner and adds the player to the model.
   *
   * @param sc       the command line command
   * @param provider if it is using provider strategies
   */
  public Player addInt(Scanner sc, boolean provider) {
    while (sc.hasNext()) {
      if (sc.hasNextInt()) {
        int x = sc.nextInt();
        if (((!provider && x <= 6) || (provider && x <= 5)) && x > 0) {
          return Strategies.createPlayer(Strategies.getStratByVal(x,
                  provider), this.model, provider);
        }
      }
    }
    return null;
  }

  /**
   * Gets the next valid int in a scanner and find the view the player wants to use.
   *
   * @param sc the command line command
   * @return true if it uses orignal, false if it uses the provider
   */
  public boolean addIntForView(Scanner sc) {
    while (sc.hasNext()) {
      if (sc.hasNextInt()) {
        int x = sc.nextInt();
        if (x == 1) {
          return true;
        } else if (x == 2) {
          return false;
        }
      }
    }
    throw new IllegalStateException("Invalid number");
  }

}
