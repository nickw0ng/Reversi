import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscPosn;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.view.ReversiTextualView;
import cs3500.reversi.view.TextualView;

/**
 * Tests for {@link ReversiModel}.
 */
public class BasicReversiTest {
  ReversiModel tester;
  ReversiModel size5;
  ReversiTextualView testerView;


  /**
   * initializes examples used in tests.
   */
  @Before
  public void init() {
    tester = new BasicReversi(7);
    size5 = new BasicReversi(5);
    testerView = new TextualView(tester);
  }

  // Tests that move based on R axis are correct
  @Test
  public void testValidRMove() {
    Disc validMove = new Disc(DiscType.EMPTY, new DiscPosn(1, 4, -2));
    Disc invalidMove = new Disc(DiscType.EMPTY, new DiscPosn(-3, 4, 2));
    Assert.assertTrue(tester.validMove(validMove));
    Assert.assertFalse(tester.validMove(invalidMove));
  }

  // Tests that move based on Q axis are correct
  @Test
  public void testValidQMove() {
    Disc validMove = new Disc(DiscType.EMPTY, new DiscPosn(1, 1, 1));
    Disc invalidMove = new Disc(DiscType.EMPTY, new DiscPosn(2, 0, 1));
    System.out.println(testerView);
    Assert.assertTrue(tester.validMove(validMove));
    Assert.assertFalse(tester.validMove(invalidMove));
  }

  // Tests that move based on Z axis are correct
  @Test
  public void testValidZMove() {
    Disc validMove = new Disc(DiscType.EMPTY, new DiscPosn(-2, 4, 1));
    Disc invalidMove = new Disc(DiscType.EMPTY, new DiscPosn(-3, 5, 1));
    Assert.assertTrue(tester.validMove(validMove));
    Assert.assertFalse(tester.validMove(invalidMove));
  }

  // Tests flip discs flips the discs and also updates the turn
  @Test
  public void testFlipDiscs() {
    Disc validMove = new Disc(DiscType.EMPTY, new DiscPosn(-2, 4, 1));
    Assert.assertEquals(DiscType.EMPTY, tester.getBoard().get(4).get(1).getType());
    Assert.assertEquals(DiscType.WHITE, tester.getBoard().get(3).get(2).getType());
    Assert.assertEquals(DiscType.BLACK, tester.getCurrentTurn());
    tester.flipDiscs(validMove);
    Assert.assertEquals(DiscType.WHITE, tester.getCurrentTurn());
    Assert.assertEquals(DiscType.BLACK, tester.getBoard().get(4).get(1).getType());
    Assert.assertEquals(DiscType.BLACK, tester.getBoard().get(3).get(2).getType());
  }

  // Tests flip discs throws an IllegalStateException when a move is invalid
  @Test(expected = IllegalStateException.class)
  public void testInvalidFlip() {
    Disc invalidMove = new Disc(DiscType.EMPTY, new DiscPosn(2, 0, 1));
    tester.flipDiscs(invalidMove);
  }

  // Tests that only the valid discs are flipped and not every disc next to the move
  @Test
  public void testOnlyValidFlip() {
    Assert.assertEquals(DiscType.WHITE, tester.getBoard().get(4).get(3).getType());
    Disc m1 = new Disc(DiscType.EMPTY, new DiscPosn(-2, 4, 1));
    tester.flipDiscs(m1);
    Assert.assertEquals(DiscType.WHITE, tester.getBoard().get(4).get(3).getType());
  }

  // Tests that the view displays the correct board when disc are flipped and
  // also alternates players
  @Test
  public void testViewDiscFlip() {
    Assert.assertEquals("   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            "_ _ O _ X _ _\n" +
            " _ _ X O _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n", testerView.toString());
    Disc m1 = new Disc(DiscType.EMPTY, new DiscPosn(-2, 4, 1));
    tester.flipDiscs(m1);
    Assert.assertEquals("   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            "_ _ X _ X _ _\n" +
            " _ X X O _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n", testerView.toString());

    Disc m2 = new Disc(DiscType.EMPTY, new DiscPosn(1, 4, -2));
    tester.flipDiscs(m2);
    Assert.assertEquals("   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            "_ _ X _ O _ _\n" +
            " _ X X O O _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n", testerView.toString());

  }

  // Tests the textual view of the initial board
  @Test
  public void testInitView() {
    Assert.assertEquals("   _ _ _ _\n" +
            "  _ _ _ _ _\n" +
            " _ _ X O _ _\n" +
            "_ _ O _ X _ _\n" +
            " _ _ X O _ _\n" +
            "  _ _ _ _ _\n" +
            "   _ _ _ _\n", testerView.toString());
  }

  // Test game over
  @Test
  public void testGameOver() {
    Assert.assertFalse(tester.isGameOver());
    tester.pass();
    tester.pass();
    Assert.assertTrue(tester.isGameOver());
  }

  // Test that you cannot pass more than twice in a row
  @Test(expected = IllegalStateException.class)
  public void testTooManyPasses() {
    tester.pass();
    tester.pass();
    tester.pass();
  }

  // Tests that the score updates after a move is made
  @Test
  public void testScoreUpdating() {
    Assert.assertEquals(3, tester.getPlayer1Score());
    Assert.assertEquals(3, tester.getPlayer2Score());
    Disc m1 = new Disc(DiscType.EMPTY, new DiscPosn(-2, 4, 1));
    tester.flipDiscs(m1);
    Assert.assertEquals(5, tester.getPlayer1Score());
    Assert.assertEquals(2, tester.getPlayer2Score());
    Disc m2 = new Disc(DiscType.EMPTY, new DiscPosn(1, 4, -2));
    tester.flipDiscs(m2);
    Assert.assertEquals(4, tester.getPlayer1Score());
    Assert.assertEquals(4, tester.getPlayer2Score());
  }

}
