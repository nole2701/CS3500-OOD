import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;

/**
 * A mock class extending BasicPyramidSolitaire used for testing if the controller
 * gives the correct inputs to the model.
 */
public class BasicPyramidSolitaireMock extends BasicPyramidSolitaire {
  public StringBuilder out;

  public BasicPyramidSolitaireMock() {
    this.out = new StringBuilder();
  }


  @Override
  public void remove(int row1, int card1, int row2, int card2) {
    this.out.append(row1 +  " " + card1 + " " + row2 + " " + card2);
  }

  @Override
  public void remove(int row, int card) {
    this.out.append(row +  " " + card);
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) {
    this.out.append(drawIndex + " " + row +  " " + card);
  }

  @Override
  public void discardDraw(int drawIndex) {
    this.out.append(drawIndex);
  }



}
