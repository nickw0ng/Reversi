package cs3500.reversi.controller;

import java.io.IOException;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.HumanPlayer;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.IView;

/**
 * Mock Controller implementation.
 */
public class MockController implements IController, PlayerListener {
  private final Player player;
  private IView gameView;
  public boolean turn;
  private Appendable appendable;

  /**
   * Constructs a mock controller.
   * @param a the appendable
   * @param m the model
   * @param p the player
   * @param view the view
   */
  public MockController(Appendable a, ReversiModel m, Player p, IView view) {
    this.appendable = a;
    this.player = p;
    this.gameView = view;
    this.gameView.addPlayerListener(this);
    m.addListeners(this);
    if (this.player.getPiece() == DiscType.BLACK) {
      this.turn = true;
    } else {
      this.turn = false;
    }
    this.gameView.setTitle("ReversiMock - " + this.player);
    this.player.addController(this);
    if (this.player instanceof HumanPlayer) {
      setMouseListener();
      setKeyListener();
    }
  }

  @Override
  public ReversiModel getModel() {
    return null;
  }

  @Override
  public void setCanvas() {
    try {
      this.appendable.append("Canvas was set on " + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }

  }

  @Override
  public void click(Disc d) {
    try {
      this.appendable.append("Model was clicked " + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public String toString() {
    return this.player + " Controller";
  }

  @Override
  public void setMouseListener() {
    try {
      this.appendable.append("It was set because only human players have mouse liseteners - "
                      + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public IView getGameView() {
    try {
      this.appendable.append("Get game view was called -  "
                      + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return this.gameView;
  }

  @Override
  public void setKeyListener() {
    try {
      this.appendable.append("It was set because only human players have key liseteners - "
                      + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void turnChange(DiscType t) {
    try {
      this.appendable.append("Turn changed")
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void gameChange(ReversiModel m) {
    try {
      this.appendable.append("Game change called")
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }



  @Override
  public void play() {
    try {
      this.appendable.append("played " + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void flip(Disc d) {
    try {
      this.appendable.append("flipped " + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }
  }

  @Override
  public void pass() {
    try {
      this.appendable.append("passed " + this.toString())
              .append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException();
    }

  }
}
