package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.reversi.controller.ModelStatusListener;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.HumanPlayer;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.model.player.ProviderPlayer;
import cs3500.reversi.provider.controller.ModelObserver;
import cs3500.reversi.provider.model.LinearCoord;


/**
 * Adapter for Provider model to work with original models.
 */
public class ToProvider implements ProviderAdapter {
  private ReversiModel model;

  /**
   * Constructs a ToProvider model.
   *
   * @param m ReversiModel to base it off of.
   */
  public ToProvider(ReversiModel m) {
    this.model = m;
  }

  /**
   * Constructs a ToProvider model.
   */
  public ToProvider() {
    this.model = new BasicReversi();
    this.model.resetAmtAdded();
    this.model.addPlayer(new ProviderPlayer(new HumanPlayer(this.model), this));
    this.model.addPlayer(new ProviderPlayer(new HumanPlayer(this.model), this));
    this.model.resetAmtAdded();
  }

  /**
   * Adds padding to the end of model so provider view displays properly.
   *
   * @param size the size of the board.
   * @param row  the row to add padding to.
   * @return update list of discs with padding.
   */
  public List<Disc> addFinalPadding(int size, List<Disc> row) {
    if (row.size() != size) {
      for (int i = 0; i < size - row.size(); i++) {
        row.add(new Disc(DiscType.BORDER));
      }
    }
    return row;
  }

  /**
   * Makes the provider board.
   *
   * @return the updated board
   */
  public List<List<Disc>> makeProviderBoard() {
    List<List<Disc>> pBoard = new ArrayList<>();
    List<Integer> paddingNums = new ArrayList<>();
    List<Integer> first = new ArrayList<>();
    List<Integer> last = new ArrayList<>();
    int halfBoard = this.model.getBoardSize() / 2;
    int timesTAdded = 1;
    int numT = (halfBoard / 2) + 1;
    int timesBAdded = 0;
    int numB = 1;
    for (int i = 0; i < halfBoard; i++) {
      first.add(numT);
      last.add(numB);
      if (timesTAdded > 0) {
        numT--;
        timesTAdded = 0;
      } else {
        timesTAdded++;
      }
      if (timesBAdded > 0) {
        numB++;
        timesBAdded = 0;
      } else {
        timesBAdded++;
      }
    }
    paddingNums.addAll(first);
    paddingNums.add(0);
    paddingNums.addAll(last);
    for (int i = 0; i < paddingNums.size(); i++) {
      List<Disc> row = new ArrayList<>();
      for (int j = 0; j < paddingNums.get(i); j++) {
        row.add(new Disc(DiscType.BORDER));
      }
      row.addAll(this.model.getBoard().get(i));
      pBoard.add(addFinalPadding(this.model.getBoardSize(), row));
    }
    return pBoard;
  }

  @Override
  public boolean isGameOver() {
    return this.model.isGameOver();
  }

  @Override
  public Player getPlayer1() {
    return this.model.getPlayer1();
  }

  @Override
  public Player getPlayer2() {
    return this.model.getPlayer2();
  }

  @Override
  public List<List<Disc>> getBoard() {
    return this.model.getBoard();
  }

  @Override
  public DiscType getCurrentTurn() {
    return this.model.getCurrentTurn();
  }

  @Override
  public Player findWinner() {
    return this.model.findWinner();
  }

  @Override
  public boolean validMove(LinearCoord coord) {
    List<List<Disc>> newBoard = this.makeProviderBoard();
    Disc d = newBoard.get(coord.row()).get(coord.column());
    return this.model.validMove(d);
  }

  @Override
  public cs3500.reversi.provider.player.Player getWinner() {
    if (this.model.findWinner().getPiece() == DiscType.BLACK) {
      return new ProviderPlayer(DiscType.BLACK);
    } else if (this.model.findWinner().getPiece() == DiscType.WHITE) {
      return new ProviderPlayer(DiscType.WHITE);
    }
    return null;
  }

  @Override
  public boolean gameOver() {
    return this.model.isGameOver();
  }

  @Override
  public cs3500.reversi.provider.player.Player playerAt(LinearCoord coord) {
    try {
      List<List<Disc>> newBoard = this.makeProviderBoard();
      Disc d = newBoard.get(coord.row()).get(coord.column());
      if (d.sameType(DiscType.BLACK)) {
        return new ProviderPlayer(DiscType.BLACK);
      } else if (d.sameType(DiscType.WHITE)) {
        return new ProviderPlayer(DiscType.WHITE);
      } else if (d.sameType(DiscType.EMPTY)) {
        return null;
      } else {
        throw new IllegalArgumentException();
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public int getRightCol() {
    return this.model.getBoardSize() - 1;
  }

  @Override
  public int getLeftCol() {
    return 0;
  }

  @Override
  public int getTopRow() {
    return 0;
  }

  @Override
  public int getBottomRow() {
    return this.model.getBoardSize() - 1;
  }

  @Override
  public cs3500.reversi.provider.model.ReversiModel getModel() {
    return this;
  }

  @Override
  public int getPlayerScore(cs3500.reversi.provider.player.Player player) {
    if (player.toString().equals("X")) {
      return this.getPlayer1Score();
    } else if (player.toString().equals("O")) {
      return this.getPlayer2Score();
    }
    throw new IllegalStateException("Invalid Player");
  }

  @Override
  public cs3500.reversi.provider.player.Player nextToPlay() {
    return null;
  }

  @Override
  public int getPlayer1Score() {
    return this.model.getPlayer1Score();
  }

  @Override
  public int getPlayer2Score() {
    return this.model.getPlayer2Score();
  }

  @Override
  public boolean validMove(Disc clicked) {
    return this.model.validMove(clicked);
  }

  @Override
  public int getBoardSize() {
    return this.model.getBoardSize();
  }

  @Override
  public Disc findCLicked() {
    return this.model.findCLicked();
  }

  @Override
  public List<Disc> allMovesLeft(DiscType player) {
    return this.model.allMovesLeft(player);
  }

  @Override
  public int getPlayerScore(DiscType player) {
    return this.model.getPlayerScore(player);
  }

  @Override
  public List<Disc> discsToFlip(List<Disc> discs, Disc clicked) {
    return this.model.discsToFlip(discs, clicked);
  }

  @Override
  public List<Disc> discsToFlipAll(Disc clicked) {
    return this.model.discsToFlipAll(clicked);
  }

  @Override
  public DiscType playerToAddType() {
    return this.model.playerToAddType();
  }

  @Override
  public List<ModelStatusListener> getListeners() {
    return this.model.getListeners();
  }

  @Override
  public void addPlayer(Player p) {
    if (p instanceof ProviderPlayer) {
      this.model.addPlayer(p);
    } else {
      this.model.addPlayer(new ProviderPlayer(p, this));
    }
  }

  @Override
  public void addListeners(ModelStatusListener l) {
    this.model.addListeners(l);
  }

  @Override
  public void notifyTurnChange() {
    this.model.notifyTurnChange();
  }

  @Override
  public void flipDiscs(Disc clicked) {
    this.model.flipDiscs(clicked);
  }

  @Override
  public void play() {
    this.model.play();
  }

  @Override
  public void clickDisc(Disc d) {
    this.model.clickDisc(d);
  }

  @Override
  public void addObserver(ModelObserver obs) {
    //not applicable
  }

  @Override
  public void startGame() {
    //this.play();
  }

  @Override
  public void placePiece(LinearCoord coord) {
    List<List<Disc>> newBoard = this.makeProviderBoard();
    Disc d = newBoard.get(coord.row()).get(coord.column());
    flipDiscs(d);
  }

  @Override
  public void pass() {
    this.model.pass();
  }

  @Override
  public void updateScores() {
    this.model.updateScores();
  }

  @Override
  public void newBoard(List<List<Disc>> newBoard) {
    this.model.newBoard(newBoard);
  }

  @Override
  public boolean isThereClicked() {
    return this.model.isThereClicked();
  }

  @Override
  public void resetAmtAdded() {
    // not applicable
  }
}
