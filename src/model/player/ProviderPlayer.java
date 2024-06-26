package cs3500.reversi.model.player;

import java.util.Objects;

import cs3500.reversi.controller.IController;
import cs3500.reversi.model.ProviderAdapter;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.provider.controller.InputObserver;
import cs3500.reversi.provider.model.ReadOnlyReversiModel;
import cs3500.reversi.strategy.Strategies;

/**
 * Provider Player Adapter.
 */
public class ProviderPlayer implements Player, cs3500.reversi.provider.player.Player {
  private final Player player;
  private final Strategies strategy;
  private final DiscType piece;
  private ProviderAdapter model;

  /**
   * Constructs a provider player.
   *
   * @param p given player
   * @param m given model
   */
  public ProviderPlayer(Player p, ProviderAdapter m) {
    this.player = p;
    this.model = m;
    this.piece = this.player.getPiece();
    this.strategy = null;
  }

  /**
   * Constructs a provider player.
   *
   * @param m given model
   * @param s given strategy.
   */
  public ProviderPlayer(ProviderAdapter m, Strategies s) {
    this.player = null;
    this.strategy = s;
    this.model = m;
    this.piece = this.model.playerToAddType();
  }

  /**
   * Constructs a provider player.
   *
   * @param t given disc type
   */
  public ProviderPlayer(DiscType t) {
    this.player = null;
    this.strategy = null;
    this.model = null;
    this.piece = t;
  }

  /**
   * Constructs a provider player.
   *
   * @param t given disc type
   * @param p given player
   */
  public ProviderPlayer(DiscType t, Player p) {
    this.player = p;
    this.strategy = null;
    this.model = null;
    this.piece = t;
  }

  @Override
  public void addController(IController c) {
    // Not applicable
  }

  @Override
  public boolean isHuman() {
    if (Objects.nonNull(player)) {
      return this.player instanceof HumanPlayer;
    } else {
      return false;
    }
  }

  @Override
  public Strategies getStrategy() {
    return this.strategy;
  }

  @Override
  public void play() {
    if (Objects.nonNull(player)) {
      this.player.play();
    }
  }

  @Override
  public DiscType getPiece() {
    return this.piece;
  }

  @Override
  public void startTurn(ReadOnlyReversiModel model) {
    this.player.play();
  }

  @Override
  public void addObserver(InputObserver observer) {
    // not applicable
  }

  @Override
  public String toString() {
    if (this.getPiece() == DiscType.BLACK) {
      return "X";
    } else if (this.getPiece() == DiscType.WHITE) {
      return "O";
    } else {
      return null;
    }
  }
}
