import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscPosn;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.ReadonlyReversiModel;
import cs3500.reversi.model.ReversiMock;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.strategy.AvoidNearCorner;
import cs3500.reversi.strategy.GetMostPieces;
import cs3500.reversi.strategy.GoForCorners;
import cs3500.reversi.strategy.MoveStrategy;

/**
 * Tests for the strategies.
 */
public class StrategyTests {
  ReversiModel model;
  ReversiModel small;
  StringBuilder log;
  ReadonlyReversiModel mock;

  @Before
  public void init() {
    model = new BasicReversi(7);
    small = new BasicReversi(5);
    log = new StringBuilder();
    mock = new ReversiMock(log, model.getBoard(), DiscType.BLACK);
  }

  // Checks that the go for corner method checks all corners
  @Test
  public void testGoForCornersChecks() {
    MoveStrategy strategy = new GoForCorners();

    strategy.chooseDisc(mock, DiscType.BLACK);

    Assert.assertTrue(log.toString().contains("Valid move checked on (0, 0, 3)\n" +
            "Valid move checked on (3, 0, 0)\n" +
            "Valid move checked on (-3, 3, 3)\n" +
            "Valid move checked on (3, 3, -3)\n" +
            "Valid move checked on (-3, 6, 0)\n" +
            "Valid move checked on (0, 6, -3)"));
  }

  // Checks that the go for corner strategy picks disc in corner
  @Test
  public void testGoForCornersPicks() {
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(-1, 5, -1)));
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(-1, 6, -2)));
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(-2, 6, -1)));
    MoveStrategy strategy = new GoForCorners();
    Assert.assertEquals(new Disc(DiscType.EMPTY, new DiscPosn(-3, 6, -0)),
            strategy.chooseDisc(model, DiscType.WHITE));
    Assert.assertNotEquals(new Disc(DiscType.EMPTY, new DiscPosn(-1, 4, -2)),
            strategy.chooseDisc(model, DiscType.WHITE));
  }

  // Checks that avoid corners strategy avoids the corners when there are only two move
  // options (one next to corner, one somewhere else)
  @Test
  public void testAvoidPicksNonCorner() {
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(1, 1, 1)));
    model.pass();
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(1, 4, -2)));
    MoveStrategy strategy = new AvoidNearCorner();
    Assert.assertEquals(new ArrayList<>(Arrays.asList(
                    new Disc(DiscType.EMPTY, new DiscPosn(2, 0, 1)),
                    new Disc(DiscType.EMPTY, new DiscPosn(-1, 5, -1)))),
            model.allMovesLeft(DiscType.WHITE));
    Assert.assertEquals(new Disc(DiscType.EMPTY, new DiscPosn(-1, 5, -1)),
            strategy.chooseDisc(model, DiscType.WHITE));
    Assert.assertNotEquals(new Disc(DiscType.EMPTY, new DiscPosn(2, 0, 1)),
            strategy.chooseDisc(model, DiscType.WHITE));
  }

  // Makes sure get most strategy goes through every possible move by checking it calls the function
  // that returns a list of all possible moves
  @Test
  public void testGetMost() {
    MoveStrategy strategy = new GetMostPieces();
    strategy.chooseDisc(mock, DiscType.BLACK);
    Assert.assertTrue(log.toString().contains("Checking list of all moves left for player."));
  }

  // Makes sure get most strategy picks move that moves the most discs
  @Test
  public void testGetMostPicksRightOne() {
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(-1, 5, -1)));
    MoveStrategy strategy = new GetMostPieces();
    Disc moreThan1 = new Disc(DiscType.EMPTY, new DiscPosn(-1, 6, -2));
    Disc only1 = new Disc(DiscType.EMPTY, new DiscPosn(1, 1, 1));
    Assert.assertTrue(model.discsToFlipAll(moreThan1).size()
            > model.discsToFlipAll(only1).size());
    Assert.assertEquals(moreThan1, strategy.chooseDisc(model, DiscType.WHITE));
    Assert.assertNotEquals(only1, strategy.chooseDisc(model, DiscType.WHITE));
  }

}
