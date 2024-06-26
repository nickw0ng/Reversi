package cs3500.reversi.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.Hexagon;
import cs3500.reversi.view.IView;

/**
 * A Reversi Controller.
 */
public class ReversiController implements IController, PlayerListener {
  private ReversiModel model;
  private final Player player;
  private IView gameView;
  private final List<Hexagon> hexagons;
  public boolean turn;

  /**
   * Constructs a Reversi Controller.
   *
   * @param m    the model
   * @param p    the player
   * @param view the view
   */
  public ReversiController(ReversiModel m, Player p, IView view) {
    this.model = m;
    this.player = p;
    this.gameView = view;
    this.hexagons = view.getReversiPanel().getHexagons();
    this.gameView.addPlayerListener(this);
    this.model.addListeners(this);
    if (this.player.getPiece() == DiscType.BLACK) {
      this.turn = true;
    } else {
      this.turn = false;
    }
    this.gameView.setTitle("Reversi - " + this.player);
    this.player.addController(this);
    if (this.player.isHuman()) {
      setMouseListener();
      setKeyListener();
    }
  }

  @Override
  public void play() {
    if (this.turn) {
      this.player.play();
    }
    setCanvas();
  }

  @Override
  public ReversiModel getModel() {
    return this.model;
  }

  @Override
  public void setCanvas() {
    Objects.requireNonNull(model);
    Objects.requireNonNull(gameView);
    gameView.setCanvas();
  }

  @Override
  public void click(Disc d) {
    this.model.clickDisc(d);
  }

  @Override
  public void flip(Disc d) {
    this.model.flipDiscs(d);
  }

  @Override
  public void pass() {
    this.model.pass();
  }

  @Override
  public void setMouseListener() {
    gameView.setMouseListener(new ReversiMouse());
  }

  @Override
  public IView getGameView() {
    return this.gameView;
  }

  @Override
  public void setKeyListener() {
    gameView.setKeyListener(new ReversiKey());
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


  class ReversiMouse extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
      if (ReversiController.this.turn) {
        Disc d = new Disc(DiscType.BORDER);
        for (Hexagon h : ReversiController.this.hexagons) {
          if (h.contains(e.getX(), e.getY())) {
            d = model.getBoard().get(h.getRowNum()).get(h.getIndex());
            System.out.println("Clicked on hexagon at " + d.getPosn().toString());
            break;
          }
        }
        ReversiController.this.click(d);
        ReversiController.this.setCanvas();
      }
    }
  }

  class ReversiKey extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      if (ReversiController.this.turn && ReversiController.this.model.isThereClicked()) {
        char k = e.getKeyChar();
        if (k == 'p' || k == 'P') {
          ReversiController.this.gameView.notifyUserPass();
        } else if (k == 'm' || k == 'M') {
          try {
            ReversiController.this.gameView.notifyUserMove(model.findCLicked());
          } catch (IllegalStateException error) {
            ReversiController.this.gameView.showError("Invalid Move for " +
                    ReversiController.this.player);
          }
        }
        ReversiController.this.setCanvas();
      }
    }
  }
}
