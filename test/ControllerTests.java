import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.reversi.controller.IController;
import cs3500.reversi.controller.MockController;
import cs3500.reversi.model.player.AIPlayer;
import cs3500.reversi.model.BasicReversi;
import cs3500.reversi.model.discs.Disc;
import cs3500.reversi.model.discs.DiscPosn;
import cs3500.reversi.model.discs.DiscType;
import cs3500.reversi.model.player.HumanPlayer;
import cs3500.reversi.model.player.Player;
import cs3500.reversi.strategy.Strategies;
import cs3500.reversi.view.IView;
import cs3500.reversi.view.ReversiGraphicsView;

/**
 * Tests for the controller.
 */
public class ControllerTests {
  BasicReversi model;
  StringBuilder log;
  IController c1;
  IController c2;
  Player p1;
  Player p2;
  IView view1;
  IView view2;

  @Before
  public void init() {
    model = new BasicReversi();
    log = new StringBuilder();
    p1 = new AIPlayer(model, Strategies.MOSTDISCS);
    p2 = new HumanPlayer(model);
    view1 = new ReversiGraphicsView(model);
    view2 = new ReversiGraphicsView(model);
    c1 = new MockController(log, model, p1, view1);
    c2 = new MockController(log, model, p2, view2);
  }

  // checks the view listeners are properly added.
  @Test
  public void checkViewListeners() {
    Assert.assertEquals(new ArrayList<>(Arrays.asList(c1)), view1.getListeners());
    Assert.assertEquals(new ArrayList<>(Arrays.asList(c2)), view2.getListeners());
  }

  // checks the model listeners are properly added.
  @Test
  public void checkModelListeners() {
    Assert.assertEquals(new ArrayList<>(Arrays.asList(c1, c2)), model.getListeners());
  }

  @Test
  public void testModelPlaysBothControllers() {
    model.play();
    Assert.assertTrue(log.toString().contains("played Player 1 Controller\n" +
            "played Player 2 Controller"));
  }

  @Test
  public void testModelSetsListenersForHumansOnly() {
    model.play();
    Assert.assertTrue(log.toString().contains("It was set because only human " +
            "players have mouse liseteners " +
            "- Player 2 Controller\n" + "It was set because only human " +
            "players have key liseteners " +
            "- Player 2 Controller"));
    Assert.assertTrue(p2 instanceof HumanPlayer);
  }

  // Tests that game change and turn change is called twice (once for each controller) 
  // when piece is flipped
  @Test
  public void testTurnChangeAndGameChange() {
    model.flipDiscs(new Disc(DiscType.EMPTY, new DiscPosn(-1, 5, -1)));
    Assert.assertTrue(log.toString().contains("Turn changed\n" +
            "Game change called\n" +
            "Turn changed\n" +
            "Game change called"));
  }

  // Tests that game change and turn change is called twice (once for each controller) 
  // when player passes
  @Test
  public void testTurnChangeAndGameChangePass() {
    model.pass();
    Assert.assertTrue(log.toString().contains("Turn changed\n" +
            "Game change called\n" +
            "Turn changed\n" +
            "Game change called"));
  }

}
