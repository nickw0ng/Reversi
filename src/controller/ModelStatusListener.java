package cs3500.reversi.controller;

import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.ReversiModel;

/**
 * Model status listener implementation.
 */
public interface ModelStatusListener {

  /**
   * Changes the turn.
   *
   * @param turn the turn to change to
   */
  void turnChange(DiscType turn);

  /**
   * Changes the game.
   *
   * @param m updated the model to change to.
   */
  void gameChange(ReversiModel m);

  /**
   * Plays the game.
   */
  void play();

}
