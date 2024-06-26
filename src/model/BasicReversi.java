package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.reversi.controller.ModelStatusListener;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.HumanPlayer;
import cs3500.reversi.model.player.Player;

/**
 * Represents a basic reversi game.
 * An implementation of {@link ReversiModel}.
 */

public class BasicReversi implements ReversiModel {
  private List<List<Disc>> board;
  private List<Integer> scores;
  private DiscType currentTurn;
  private int passAmount = 0;
  private List<Player> players;
  private List<ModelStatusListener> listeners = new ArrayList<>();
  private int amtAdded = 0;


  /**
   * Constructs a basic reversi game based on board size.
   *
   * @param bs board size
   * @throws IllegalArgumentException if board size is less than 5 or even
   */
  public BasicReversi(int bs) {
    if (bs < 5) {
      throw new IllegalArgumentException("Board size but be greater than 4");
    } else if (bs % 2 == 0) {
      throw new IllegalArgumentException("Board size must be odd");
    }
    this.board = ReversiUtils.initBoard(bs);
    this.currentTurn = DiscType.BLACK;
    this.scores = new ArrayList<>(Arrays.asList(3, 3));
    this.players = new ArrayList<>(Arrays.asList(new HumanPlayer(DiscType.BLACK),
            new HumanPlayer(DiscType.WHITE)));
  }

  /**
   * Constructs a basic reversi game of size 7.
   */
  public BasicReversi() {
    this.board = ReversiUtils.initBoard(7);
    this.currentTurn = DiscType.BLACK;
    this.scores = new ArrayList<>(Arrays.asList(3, 3));
    this.players = new ArrayList<>(Arrays.asList(new HumanPlayer(DiscType.BLACK),
            new HumanPlayer(DiscType.WHITE)));
  }

  /**
   * Constructs a basic reversi game based on board size.
   *
   * @param board the given board
   * @throws IllegalArgumentException if board size is less than 5 or even
   */
  public BasicReversi(List<List<Disc>> board) {
    if (board.size() < 5) {
      throw new IllegalArgumentException("Board size but be greater than 4");
    } else if (board.size() % 2 == 0) {
      throw new IllegalArgumentException("Board size must be odd");
    }
    this.board = board;
    this.currentTurn = DiscType.BLACK;
    this.players = new ArrayList<>(Arrays.asList(new HumanPlayer(DiscType.BLACK),
            new HumanPlayer(DiscType.WHITE)));
    this.scores = new ArrayList<>(Arrays.asList(3, 3));
  }

  /**
   * Constructs a basic reversi game based on board size and starting turn.
   *
   * @param board the given board
   * @param turn  starting turn
   * @throws IllegalArgumentException if board size is less than 5 or even
   */
  public BasicReversi(List<List<Disc>> board, DiscType turn) {
    if (board.size() < 5) {
      throw new IllegalArgumentException("Board size but be greater than 4");
    } else if (board.size() % 2 == 0) {
      throw new IllegalArgumentException("Board size must be odd");
    }
    this.board = board;
    this.currentTurn = turn;
    this.players = new ArrayList<>(Arrays.asList(new HumanPlayer(DiscType.BLACK),
            new HumanPlayer(DiscType.WHITE)));
    this.scores = new ArrayList<>(Arrays.asList(3, 3));
  }

  @Override
  public void play() {
    for (ModelStatusListener listener : this.listeners) {
      listener.play();
    }
  }

  @Override
  public void addListeners(ModelStatusListener l) {
    this.listeners.add(l);
  }

  @Override
  public void notifyTurnChange() {
    for (ModelStatusListener listener : this.listeners) {
      listener.turnChange(this.currentTurn);
      listener.gameChange(this);
    }
  }

  @Override
  public void flipDiscs(Disc clicked) {
    if (validMove(clicked)) {
      List<Disc> discsToFlip = discsToFlipAll(clicked);
      for (int r = 0; r < this.board.size(); r++) {
        for (int i = 0; i < this.board.get(r).size(); i++) {
          for (int j = 0; j < discsToFlip.size(); j++) {
            if (this.board.get(r).get(i).equals(discsToFlip.get(j))) {
              this.board.get(r).set(i, this.board.get(r).get(i).updateType(this.currentTurn));
            }
          }
        }
      }
      this.currentTurn = this.currentTurn.opposite();
      this.passAmount = 0;
      updateScores();
      this.notifyTurnChange();
    } else {
      throw new IllegalStateException("Invalid move " + clicked.getPosn() + validMove(clicked));
    }
  }

  private void notifyPause() {
    for (ModelStatusListener listener : this.listeners) {
      //listener.pause();
    }
  }


  @Override
  public void pass() {
    if (this.passAmount > 1) {
      throw new IllegalStateException("Cannot pass more than twice in a row");
    }
    try {
      Disc d = this.findCLicked();
      int idx = this.board.get(d.getR()).indexOf(d);
      this.board.get(d.getR()).set(this.board.get(d.getR()).indexOf(d),
              d.updateType(DiscType.EMPTY));
    } catch (IllegalStateException e) {
      // do nothing
    }
    this.currentTurn = this.currentTurn.opposite();
    this.passAmount++;
    this.notifyTurnChange();
  }

  @Override
  public boolean validMove(Disc clicked) {
    return !discsToFlipAll(clicked).isEmpty();
  }

  @Override
  public void clickDisc(Disc d) {
    if (d.getType() != DiscType.BORDER) {
      for (int i = 0; i < this.board.get(d.getR()).size(); i++) {
        if (this.board.get(d.getR()).get(i).equals(d)) {
          if (d.getType() == DiscType.EMPTY) {
            this.board.get(d.getR()).set(i,
                    board.get(d.getR()).get(i).updateType(DiscType.CLICKED));
          } else if (d.getType() == DiscType.CLICKED) {
            this.board.get(d.getR()).set(i,
                    board.get(d.getR()).get(i).updateType(DiscType.EMPTY));
          }
        }
      }
    }
    for (int r = 0; r < this.board.size(); r++) {
      for (int i = 0; i < this.board.get(r).size(); i++) {
        if (this.board.get(r).get(i).getType() == DiscType.CLICKED
                && !this.board.get(r).get(i).equals(d)) {
          this.board.get(r).set(i, board.get(r).get(i).updateType(DiscType.EMPTY));
        }
      }
    }
  }

  @Override
  public void addPlayer(Player p) {
    if (this.amtAdded == 0) {
      this.players.set(0, p);
    } else if (this.amtAdded == 1) {
      this.players.set(1, p);
    } else {
      throw new IllegalStateException("Cannot add more than 2 players");
    }
    amtAdded++;
  }

  @Override
  public DiscType playerToAddType() {
    if (this.amtAdded == 0) {
      return DiscType.BLACK;
    } else if (this.amtAdded == 1) {
      return DiscType.WHITE;
    } else {
      throw new IllegalStateException("Cannot add more than 2 players");
    }
  }

  @Override
  public void resetAmtAdded() {
    this.amtAdded = 0;
  }

  @Override
  public List<ModelStatusListener> getListeners() {
    return this.listeners;
  }


  @Override
  public List<Disc> allMovesLeft(DiscType player) {
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
  public boolean isGameOver() {
    return this.passAmount > 1;
  }

  @Override
  public Player getPlayer1() {
    return this.players.get(0);
  }

  @Override
  public Player getPlayer2() {
    return this.players.get(1);
  }

  @Override
  public void updateScores() {
    this.scores.set(0, ReversiUtils.updateScores(DiscType.BLACK, this.board));
    this.scores.set(1, ReversiUtils.updateScores(DiscType.WHITE, this.board));
  }


  @Override
  public void newBoard(List<List<Disc>> newBoard) {
    this.board.clear();
    this.board.addAll(newBoard);
  }


  @Override
  public List<List<Disc>> getBoard() {
    return this.board;
  }

  @Override
  public DiscType getCurrentTurn() {
    return this.currentTurn;
  }

  @Override
  public Player findWinner() {
    if (this.scores.get(0) > this.scores.get(1)) {
      return this.players.get(0);
    } else if (this.scores.get(0) < this.scores.get(1)) {
      return this.players.get(1);
    } else {
      throw new IllegalStateException("Tie Game.");
    }
  }

  @Override
  public int getPlayer1Score() {
    return this.scores.get(0);
  }

  @Override
  public int getPlayer2Score() {
    return this.scores.get(1);
  }

  @Override
  public int getPlayerScore(DiscType player) {
    if (player == DiscType.BLACK) {
      return this.scores.get(0);
    } else if (player == DiscType.WHITE) {
      return this.scores.get(1);
    } else {
      throw new IllegalStateException("Cannot find score of a non player");
    }
  }


  @Override
  public int getBoardSize() {
    return this.board.size();
  }

  @Override
  public Disc findCLicked() {
    for (int r = 0; r < this.board.size(); r++) {
      for (int i = 0; i < this.board.get(r).size(); i++) {
        if (this.board.get(r).get(i).getType() == DiscType.CLICKED) {
          return this.board.get(r).get(i);
        }
      }
    }
    throw new IllegalStateException("There are no clicked discs");
  }

  @Override
  public boolean isThereClicked() {
    try {
      findCLicked();
    } catch (IllegalStateException e) {
      return false;
    }
    return true;
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
          ans = !(discs.get(iDown).sameType(oType)) && !(discs.get(iUp).sameType(oType));
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
          if (discs.get(iUp).sameType(oType) && iUp < discs.size() - 1) {
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
    if (clicked.getType() != DiscType.BORDER) {
      List<Disc> rDiscs = this.board.get(clicked.getR());
      List<Disc> qDiscs = ReversiUtils.basedOnQOrZ(this.board, clicked, true);
      List<Disc> zDiscs = ReversiUtils.basedOnQOrZ(this.board, clicked, false);
      List<Disc> discsToFlip = discsToFlip(rDiscs, clicked);
      discsToFlip.addAll(discsToFlip(qDiscs, clicked));
      discsToFlip.addAll(discsToFlip(zDiscs, clicked));
      return discsToFlip;
    } else {
      return new ArrayList<>();
    }
  }
}
