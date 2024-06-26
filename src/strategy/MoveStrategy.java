package cs3500.reversi.strategy;

import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.discs.Disc;

/**
 * Interface for strategies in a Reversi game.
 */
public interface MoveStrategy {

  /**
   * A Strategy interface for choosing where to play next for the given player.
   */
  Disc chooseDisc(ReadonlyReversiModel model, DiscType player);


}
