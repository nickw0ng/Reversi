package cs3500.reversi.controller;

import java.io.IOException;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.model.ProviderAdapter;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.player.ProviderPlayer;
import cs3500.reversi.provider.controller.InputObserver;
import cs3500.reversi.provider.model.LinearCoord;
import cs3500.reversi.provider.view.ReversiView;
import cs3500.reversi.strategy.Strategies;
import cs3500.reversi.view.IView;

/**
 * Controller for the provider model.
 */
public class ProviderController implements IController, InputObserver {
  private ProviderAdapter model;
  private final Player player;
  private ReversiView gameView;
  public boolean turn;

  /**
   * Constructs a Reversi Provider Controller.
   *
   * @param m    the model
   * @param p    the player
   * @param view the view
   */
  public ProviderController(ProviderAdapter m, Player p, ReversiView view) {
    this.model = m;
    this.player = p;
    this.gameView = view;
    this.gameView.addObserver(this);
    this.model.addListeners(this);
    if (this.player.getPiece() == DiscType.BLACK) {
      turn = true;
    } else {
      turn = false;
    }
    this.gameView.setVisible(true);
    this.player.addController(this);
  }

  @Override
  public void turnChange(DiscType t) {
    this.turn = t == this.player.getPiece();
  }

  @Override
  public void gameChange(ReversiModel m) {
    if (turn) {
      this.model.play();
    }
  }

  @Override
  public void play() {
    if (this.model.getCurrentTurn() == this.player.getPiece()) {
      try {
        if (!this.player.isHuman()) {
          this.moveHere(Strategies.getProviderStrat(this.player.getStrategy()).chooseMove(
                  this.model, new ProviderPlayer(this.player.getPiece(), this.player)));
        } else {
          this.player.play();
        }
      } catch (IllegalStateException e) {
        this.pass();
      }
      setCanvas();
    }
  }

  @Override
  public void moveHere(LinearCoord coord) {
    try {
      if (this.model.getCurrentTurn() == this.player.getPiece()) {
        this.model.placePiece(coord);
      }
    } catch (IllegalStateException e) {
      this.gameView.alertMessage(e.getMessage());
    }
  }

  @Override
  public void pass() {
    if (this.model.getCurrentTurn() == this.player.getPiece()) {
      this.model.pass();
    }
  }

  @Override
  public ReversiModel getModel() {
    return this.model;
  }

  @Override
  public void setCanvas() {
    try {
      this.gameView.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void click(Disc d) {
    //not relevant
  }

  @Override
  public IView getGameView() {
    return null;
  }
}
