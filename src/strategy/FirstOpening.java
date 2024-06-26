package cs3500.reversi.strategy;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.ReadonlyReversiModel;

/**
 * Strategy that finds the first available opening.
 */
public class FirstOpening implements MoveStrategy {

  @Override
  public Disc chooseDisc(ReadonlyReversiModel model, DiscType player) {
    if (!model.allMovesLeft(player).isEmpty()) {
      return model.allMovesLeft(player).get(0);
    } else {
      throw new IllegalStateException("No valid moves");
    }
  }
}
