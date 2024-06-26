package cs3500.reversi;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to run a reversi game.
 */
public class ReversiRunner {
  /**
   * Main method to run a reversi game.
   */
  public static void main(String[] args) {
    try {
      ReversiMaker m = new ReversiMaker(new InputStreamReader(System.in), System.out);
      m.playGame();
      System.out.println(m.getModel().getListeners());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
