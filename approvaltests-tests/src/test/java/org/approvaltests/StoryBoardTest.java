package org.approvaltests;

import org.junit.jupiter.api.Test;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;
import org.lambda.utils.Grid;

public class StoryBoardTest
{
  @Test
  void gameOfLife()
  {
    GameOfLife gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    StoryBoard storyboard = new StoryBoard();
    storyboard.add(gameOfLife);
    storyboard.addFrames(3, gameOfLife::advance);
    Approvals.verify(storyboard);
    gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    Approvals.verify(StoryBoard.createSequence(gameOfLife, 3, gameOfLife::advance));
    gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    Approvals.verify(new StoryBoard().add(gameOfLife).addFrames(3, gameOfLife::advance));
  }
  private class GameOfLife
  {
    private Function2<Integer, Integer, Boolean> board;
    public GameOfLife(Function2<Integer, Integer, Boolean> board)
    {
      this.board = board;
    }
    public GameOfLife advance()
    {
      final Function2<Integer, Integer, Boolean> old = this.board;
      this.board = (x, y) -> {
        final int aliveNeighbours = Queryable
            .as(old.call(x - 1, y - 1), old.call(x - 0, y - 1), old.call(x + 1, y - 1), old.call(x - 1, y - 0),
                //            old.call(x - 0, y - 0),
                old.call(x + 1, y - 0), old.call(x - 1, y + 1), old.call(x - 0, y + 1), old.call(x + 1, y + 1))
            .where(a -> a).size();
        return aliveNeighbours == 3 || (aliveNeighbours == 2 && old.call(x, y));
      };
      return this;
    }
    @Override
    public String toString()
    {
      return Grid.print(5, 5, (x, y) -> board.call(x, y) ? "x " : ". ");
    }
  }
}
