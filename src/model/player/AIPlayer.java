package cs3500.reversi.model.player;

import cs3500.reversi.controller.IController;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.strategy.Strategies;

/**
 * Represents an AI Player in a reversi game.
 */
public class AIPlayer implements Player {
  private final DiscType piece;
  private final Strategies strategy;
  private ReversiModel model;
  private IController controller;

  /**
   * Constructs an AI player.
   *
   * @param m the game model
   * @param strategy their strategy
   */
  public AIPlayer(ReversiModel m, Strategies strategy) {
    this.model = m;
    this.piece = this.model.playerToAddType();
    this.strategy = strategy;
  }

  @Override
  public void addController(IController c) {
    this.controller = c;
  }

  @Override
  public boolean isHuman() {
    return false;
  }

  @Override
  public Strategies getStrategy() {
    return this.strategy;
  }

  @Override
  public void play() {
    this.controller.getGameView().briefPause();
    if (this.getPiece() == this.model.getCurrentTurn()) {
      try {
        this.controller.getGameView().notifyUserMove(
                Strategies.getStrat(this.strategy).chooseDisc(this.model, this.piece));
      } catch (IllegalStateException e) {
        this.controller.getGameView().notifyUserPass();
      }
    }
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
  public DiscType getPiece() {
    return this.piece;
  }



}
