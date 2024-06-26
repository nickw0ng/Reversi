package cs3500.reversi.model.player;

import cs3500.reversi.controller.IController;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.strategy.Strategies;

/**
 * Represent a human player.
 * Implementation of {@link Player}.
 */
public class HumanPlayer implements Player {
  private ReversiModel model;
  private DiscType piece;

  /**
   * Constucts a human player.
   */
  public HumanPlayer(ReversiModel m) {
    this.model = m;
    this.piece = this.model.playerToAddType();
  }

  /**
   * Creates a player based on piece.
   *
   * @param p the piece
   */
  public HumanPlayer(DiscType p) {
    if (p != DiscType.BLACK && p != DiscType.WHITE) {
      throw new IllegalStateException("Invalid player type");
    }
    this.model = null;
    this.piece = p;
  }

  @Override
  public void addController(IController c) {
    // not needed for human player
  }

  @Override
  public boolean isHuman() {
    return true;
  }

  @Override
  public String toString() {
    if (this.piece == DiscType.BLACK) {
      return "Player 1";
    } else {
      return "Player 2";
    }
  }

  @Override
  public Strategies getStrategy() {
    return Strategies.HUMAN;
  }

  @Override
  public void play() {
    //not needed for human players
  }

  @Override
  public DiscType getPiece() {
    return this.piece;
  }


}
