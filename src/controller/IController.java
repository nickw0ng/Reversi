package cs3500.reversi.controller;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.IView;

/**
 * Interface for the controller.
 */
public interface IController extends ModelStatusListener {

  /**
   * Retrieves the model.
   *
   * @return the model.
   */
  ReversiModel getModel();

  /**
   * Sets the canvas after a turn.
   */
  void setCanvas();

  /**
   * Sets a disc to clicked.
   *
   * @param d the disc clicked.
   */
  void click(Disc d);

  /**
   * Gets the game view.
   *
   * @return the game view.
   */
  IView getGameView();

}
