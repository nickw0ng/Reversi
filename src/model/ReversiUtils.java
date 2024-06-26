package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscPosn;
import cs3500.reversi.model.discs.DiscType;

/**
 * Utils for {@link ReversiModel}.
 */
public class ReversiUtils {

  /**
   * Makes the initial board in a game.
   *
   * @param size board size.
   * @return the initial board.
   */
  public static List<List<Disc>> initBoard(int size) {
    List<List<Disc>> ans = new ArrayList<>();
    int q = 0;
    boolean nextHalf = false;
    int split = (size - 1) / 2;
    int z = split + 1;
    int zStart = split + 1;
    int changeSplit = split;
    for (int r = 0; r < size; r++) {
      ans.add(new ArrayList<>());
      z = zStart;
      for (int i = q; i <= changeSplit; i++) {
        z--;
        if ((r == split - 1 && i == 0) || (r == split && i == 1)
                || (r == split + 1 && i == -1)) {
          ans.get(r).add(new Disc(DiscType.BLACK, new DiscPosn(i, r, z)));
        } else if ((r == split - 1 && i == 1) || (r == split && i == -1)
                || (r == split + 1 && i == 0)) {
          ans.get(r).add(new Disc(DiscType.WHITE, new DiscPosn(i, r, z)));
        } else {
          ans.get(r).add(new Disc(DiscType.EMPTY, new DiscPosn(i, r, z)));
        }
      }
      if (ans.get(r).size() == size) {
        nextHalf = true;
      }
      if (nextHalf) {
        changeSplit--;
        zStart--;
      } else {
        q--;
      }
    }
    return ans;
  }


  /**
   * Returns the flipping of the color of the disc.
   *
   * @param type the color the disc represnts.
   * @return te opposite type of the original disc
   */
  public static DiscType opposite(DiscType type) {
    if (type.equals(DiscType.BLACK)) {
      return DiscType.WHITE;
    } else if (type.equals(DiscType.WHITE)) {
      return DiscType.BLACK;
    } else {
      throw new IllegalArgumentException("Can't reverse type of empty cell");
    }
  }




  /**
   * Makes a list of all the discs with the same given q or z values.
   *
   * @param board   the board it is based on
   * @param clicked the given disc to base list off of
   * @param q       true if it is based on q coordinate, false if based on z
   * @return list of all the discs with the same given q or z values
   */
  public static List<Disc> basedOnQOrZ(List<List<Disc>> board, Disc clicked, boolean q) {
    List<Disc> ans = new ArrayList<>();
    for (List<Disc> rows : board) {
      for (Disc d : rows) {
        if (q) {
          if (d.getQ() == clicked.getQ()) {
            ans.add(d);
          }
        } else {
          if (d.getZ() == clicked.getZ()) {
            ans.add(d);
          }
        }
      }
    }
    return ans;
  }

  /**
   * Return a players score.
   *
   * @param type  the players color
   * @param board the board
   * @return the number of discs of a given color
   */
  public static int updateScores(DiscType type, List<List<Disc>> board) {
    int ans = 0;
    for (List<Disc> rows : board) {
      for (Disc d : rows) {
        if (d.getType() == type) {
          ans++;
        }
      }
    }
    return ans;
  }
}

