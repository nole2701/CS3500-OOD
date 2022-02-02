package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;

/**
 * A textual view for rendering a PyramidSolitaireModel.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable appendable;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.appendable = new StringBuilder();
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  @Override
  public void render() throws IOException {
    appendable.append(this.toString());
  }

  @Override
  public String toString() {
    String result = "";
    if (model.getNumRows() == -1) {
      result = "";
    } else if (model.getScore() == 0) {
      result = "You win!";
    } else if (model.isGameOver()) {
      result = "Game over. Score: " + model.getScore();
    } else {
      for (int row = 0; row < model.getNumRows(); row++) {

        boolean rowEmpty = true;
        for (int card = 0; card < model.getRowWidth(row); card++) {
          if (model.getCardAt(row, card) != null) {
            rowEmpty = false;
            break;
          }
        }

        if (!rowEmpty) {

          int initialSpaces = 2 * (model.getNumRows() - (row + 1));
          for (int s = 0; s < initialSpaces; s++) {
            result += " ";
          }

          for (int card = 0; card < model.getRowWidth(row); card++) {

            if (model.getCardAt(row, card) == null) {
              if (card < model.getRowWidth(row) - 1) {
                result += ".  " + " ";
              } else {
                result += ".";
              }


            } else if (card < model.getRowWidth(row) - 1) {
              if (model.getCardAt(row, card).toString().length() == 2) {
                result += model.getCardAt(row, card).toString() + "  ";
              } else {
                result += model.getCardAt(row, card).toString() + " ";
              }


            } else {
              if (model.getCardAt(row, card).toString().length() == 2) {
                result += model.getCardAt(row, card).toString();
              } else {
                result += model.getCardAt(row, card).toString();
              }
            }


          }

        }
        result += "\n";

      }
      boolean allNull = false;
      for (Object c : model.getDrawCards()) {
        if (c == null) {
          allNull = true;
          break;
        }
      }
      if (model.getNumDraw() == 0 || allNull) {
        result += "Draw:";
      } else {
        result += "Draw: ";
      }
      for (int i = 0; i < model.getDrawCards().size(); i++) {
        if (i < model.getDrawCards().size() - 1) {
          if (model.getDrawCards().get(i).toString().length() == 2) {
            if (i == 0) {
              result += model.getDrawCards().get(i).toString() + ",";
            } else {
              result += " " + model.getDrawCards().get(i).toString() + ",";
            }
          } else {
            result += " " + model.getDrawCards().get(i).toString() + ",";
          }
        } else {
          if (model.getDrawCards().get(i).toString().length() == 2) {
            if (i == 0) {
              result += model.getDrawCards().get(i).toString();
            } else {
              result += " " + model.getDrawCards().get(i).toString();
            }
          } else {
            result += " " + model.getDrawCards().get(i).toString();
          }
        }
      }
    }
    return result;
  }
}
