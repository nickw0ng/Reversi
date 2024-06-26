package cs3500.reversi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.ModelStatusListener;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.Player;

/**
 * Mock implementation of {@link ReadonlyReversiModel}.
 */
public class ReversiMock implements ReadonlyReversiModel {
  Appendable appendable;
  List<List<Disc>> board;
  DiscType currentTurn;

  /**
   * Constructs a reversi mock.
   *
   * @param a the appendable
   * @param b the board
   * @param t the disc type of current player
   */
  public ReversiMock(Appendable a, List<List<Disc>> b, DiscType t) {
    this.appendable = a;
    this.board = b;
    this.currentTurn = t;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Player getPlayer1() {
    return null;
  }

  @Override
  public Player getPlayer2() {
    return null;
  }

  @Override
  public List<List<Disc>> getBoard() {
    return this.board;
  }

  @Override
  public DiscType getCurrentTurn() {
    return null;
  }

  @Override
  public Player findWinner() {
    try {
      this.appendable.append("getWinner Called")
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return null;
  }

  @Override
  public int getPlayer1Score() {
    return 0;
  }

  @Override
  public int getPlayer2Score() {
    return 0;
  }

  @Override
  public boolean validMove(Disc clicked) {
    try {
      this.appendable.append("Valid move checked on " + clicked.getPosn().toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return false;
  }

  @Override
  public int getBoardSize() {
    return this.board.size();
  }

  @Override
  public Disc findCLicked() {
    return null;
  }

  @Override
  public List<Disc> allMovesLeft(DiscType player) {
    try {
      this.appendable.append("Checking list of all moves left for player.");
    } catch (IOException e) {
      throw new RuntimeException();
    }
    List<Disc> ans = new ArrayList<>();
    List<Disc> add = new ArrayList<>();
    for (List<Disc> rows : this.board) {
      for (Disc d : rows) {
        if (d.sameType(DiscType.EMPTY)) {
          add = discsToFlipAll(d);
          if (!add.isEmpty()) {
            ans.add(d);
          }
        }
      }
    }
    return ans;
  }

  @Override
  public DiscType playerToAddType() {
    return DiscType.EMPTY;
  }

  @Override
  public List<ModelStatusListener> getListeners() {
    return null;
  }


  @Override
  public int getPlayerScore(DiscType player) {
    return 0;
  }

  @Override
  public List<Disc> discsToFlip(List<Disc> discs, Disc clicked) {
    List<Disc> ansList = new ArrayList<>();
    List<Disc> upList = new ArrayList<>();
    List<Disc> downList = new ArrayList<>();
    int iUp = discs.indexOf(clicked) + 1;
    int iDown = discs.indexOf(clicked) - 1;
    DiscType oType = ReversiUtils.opposite(this.currentTurn);
    boolean up = discs.indexOf(clicked) + 1 < discs.size();
    boolean down = discs.indexOf(clicked) - 1 > -1;
    boolean first = true;
    boolean ans = true;
    if (clicked.getType() == DiscType.BLACK || clicked.getType() == DiscType.WHITE) {
      return ansList;
    }
    while (up || down) {
      if (first) {
        if (!up) {
          ans = !(discs.get(iDown).sameType(oType));
        } else if (!down) {
          ans = !(discs.get(iUp).sameType(oType));
        } else {
          ans = !(discs.get(iDown).sameType(oType)) &&
                  !(discs.get(iUp).sameType(oType));
        }
        if (ans) {
          return new ArrayList<>();
        } else {
          if (iUp < discs.size() - 1) {
            upList.add(discs.get(iUp));
            iUp++;
          }
          if (iDown > 0) {
            downList.add(discs.get(iDown));
            iDown--;
          }
          first = false;
        }
      } else {
        if (up) {
          if (discs.get(iUp).sameType(oType)
                  && iUp < discs.size() - 1) {
            upList.add(discs.get(iUp));
            iUp++;
          } else if (discs.get(iUp).sameType(this.currentTurn)) {
            ansList.addAll(upList);
            up = false;
          } else {
            up = false;
            upList.clear();
          }
        }
        if (down) {
          if (discs.get(iDown).sameType(oType) && iDown > 0) {
            downList.add(discs.get(iDown));
            iDown--;
          } else if (discs.get(iDown).sameType(this.currentTurn)) {
            ansList.addAll(downList);
            down = false;
          } else {
            downList.clear();
            down = false;
          }
        }
      }
    }
    if (!ansList.isEmpty()) {
      ansList.add(clicked);
    }
    return ansList;
  }

  @Override
  public List<Disc> discsToFlipAll(Disc clicked) {
    List<Disc> rDiscs = this.board.get(clicked.getR());
    List<Disc> qDiscs = ReversiUtils.basedOnQOrZ(this.board, clicked, true);
    List<Disc> zDiscs = ReversiUtils.basedOnQOrZ(this.board, clicked, false);
    List<Disc> discsToFlip = discsToFlip(rDiscs, clicked);
    discsToFlip.addAll(discsToFlip(qDiscs, clicked));
    discsToFlip.addAll(discsToFlip(zDiscs, clicked));


    return discsToFlip;
  }


}
