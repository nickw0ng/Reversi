package cs3500.reversi.model.player;

import cs3500.reversi.controller.IController;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.strategy.Strategies;

/**
 * Interface that represents the model for a player in the game of Reversi.
 */
public interface Player {

  /**
   * Adds a controller to a player.
   *
   * @param c controller to add.
   */
  void addController(IController c);

  boolean isHuman();

  Strategies getStrategy();

  /**
   * Starts the players move.
   */
  void play();

  /**
   * Returns player's disc type.
   *
   * @return player's disc type.
   */
  DiscType getPiece();


}
