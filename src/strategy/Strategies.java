package cs3500.reversi.strategy;


import cs3500.reversi.model.ProviderAdapter;
import cs3500.reversi.model.player.AIPlayer;
import cs3500.reversi.model.player.HumanPlayer;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.model.player.ProviderPlayer;
import cs3500.reversi.provider.strategy.AvoidNextToCornersStrategy;
import cs3500.reversi.provider.strategy.CaptureCornersStrategy;
import cs3500.reversi.provider.strategy.CaptureMaxStrategy;
import cs3500.reversi.provider.strategy.CompleteReversiStrategyFromFallible;
import cs3500.reversi.provider.strategy.InfallibleReversiStrategy;
import cs3500.reversi.provider.strategy.MiniMaxStrategy;

/**
 * Represents the types of game modes.
 */
public enum Strategies {
  HUMAN, GETFIRSTAVAILABLE, MOSTDISCS, CORNERS, AVOIDCORNERS, MAXMIN;

  /**
   * Gets the strategy based on the given value.
   *
   * @param num the given number
   * @return the strategy based on the given value.
   */
  public static Strategies getStratByVal(int num, boolean provider) {
    if (!provider) {
      if (num == 1) {
        return HUMAN;
      } else if (num == 2) {
        return GETFIRSTAVAILABLE;
      } else if (num == 3) {
        return MOSTDISCS;
      } else if (num == 4) {
        return CORNERS;
      } else if (num == 5) {
        return AVOIDCORNERS;
      } else if (num == 6) {
        return MAXMIN;
      } else {
        throw new IllegalArgumentException("Invalid Value");
      }
    } else {
      return getProviderStratByVal(num);
    }
  }

  /**
   * Gets the strategy based on the given value for the provider strategies.
   *
   * @param num the given number
   * @return the strategy based on the given value.
   */
  public static Strategies getProviderStratByVal(int num) {
    if (num == 1) {
      return HUMAN;
    } else if (num == 2) {
      return MOSTDISCS;
    } else if (num == 3) {
      return CORNERS;
    } else if (num == 4) {
      return AVOIDCORNERS;
    } else if (num == 5) {
      return MAXMIN;
    } else {
      throw new IllegalArgumentException("Invalid Value");
    }
  }

  @Override
  public String toString() {
    if (this == HUMAN) {
      return "controlled by human player";
    } else if (this == GETFIRSTAVAILABLE) {
      return "goes for the first available space";
    } else if (this == MOSTDISCS) {
      return "goes for the move that gets the most discs";
    } else if (this == CORNERS) {
      return "goes for the upper leftmost valid corner";
    } else if (this == AVOIDCORNERS) {
      return "avoids going next to corners";
    } else {
      return "goes for the move that makes it hardest for the other player";
    }
  }

  /**
   * Displays the strategies in a string in a numbered list.
   *
   * @return string of all the strategies
   */
  public static String asAList(boolean provider) {
    String ans = "";
    if (!provider) {
      for (int i = 1; i < 6; i++) {
        ans += i + ". " + getStratByVal(i, provider) + ", ";
        if (i == 3) {
          ans += "\n";
        }
      }
      ans += 6 + ". " + getStratByVal(6, provider) + ", ";
    } else {
      for (int i = 1; i < 5; i++) {
        ans += i + ". " + getProviderStratByVal(i) + ", ";
        if (i == 3) {
          ans += "\n";
        }
      }
      ans += 5 + ". " + getProviderStratByVal(5) + ", ";
    }
    return ans;
  }

  /**
   * Creates a game based on given mode.
   *
   * @param strategy the strategy the user selects
   * @return the player
   */
  public static Player createPlayer(Strategies strategy, ProviderAdapter model, boolean provider) {
    if (!provider) {
      switch (strategy) {
        case HUMAN:
          return new HumanPlayer(model);
        case GETFIRSTAVAILABLE:
          return new AIPlayer(model, strategy);
        case MOSTDISCS:
          return new AIPlayer(model, strategy);
        case CORNERS:
          return new AIPlayer(model, strategy);
        case AVOIDCORNERS:
          return new AIPlayer(model, strategy);
        case MAXMIN:
          return new AIPlayer(model, strategy);
        default:
          throw new IllegalArgumentException("Unsupported strategy type");
      }
    } else {
      switch (strategy) {
        case HUMAN:
          return new HumanPlayer(model);
        case MOSTDISCS:
          return new ProviderPlayer(model, strategy);
        case CORNERS:
          return new ProviderPlayer(model, strategy);
        case AVOIDCORNERS:
          return new ProviderPlayer(model, strategy);
        case MAXMIN:
          return new ProviderPlayer(model, strategy);
        default:
          throw new IllegalArgumentException("Unsupported strategy type");
      }
    }
  }

  /**
   * Returns the MoveStrategy corresponding to the Strategies.
   *
   * @param s the given Strategies
   * @return the MoveStrategy corresponding to the Strategies
   */
  public static MoveStrategy getStrat(Strategies s) {
    switch (s) {
      case HUMAN:
        return null;
      case GETFIRSTAVAILABLE:
        return new FirstOpening();
      case MOSTDISCS:
        return new GetMostPieces();
      case CORNERS:
        return new GoForCorners();
      case AVOIDCORNERS:
        return new AvoidNearCorner();
      case MAXMIN:
        return new BestStrategy();
      default:
        throw new IllegalArgumentException("Unsupported strategy type");
    }
  }

  /**
   * Returns the InfallibleReversiStrategy corresponding to the Strategies.
   *
   * @param s the given Strategies
   * @return the InfallibleReversiStrategy corresponding to the Strategies
   */
  public static InfallibleReversiStrategy getProviderStrat(Strategies s) {
    switch (s) {
      case HUMAN:
        return null;
      case MOSTDISCS:
        return new CompleteReversiStrategyFromFallible(new CaptureMaxStrategy());
      case CORNERS:
        return new CompleteReversiStrategyFromFallible(new CaptureCornersStrategy());
      case AVOIDCORNERS:
        return new CompleteReversiStrategyFromFallible(new AvoidNextToCornersStrategy());
      case MAXMIN:
        return new CompleteReversiStrategyFromFallible(new MiniMaxStrategy());
      default:
        throw new IllegalArgumentException("Unsupported strategy type");
    }
  }
}
