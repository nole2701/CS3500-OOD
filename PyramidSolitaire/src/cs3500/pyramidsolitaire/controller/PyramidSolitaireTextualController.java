package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Class representing a controller for PyramidSolitaire. It is controlled using a scanner as a
 * Readable. It outputs an Appendable which is modified using the commands the controller supports.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  protected final Readable in;
  protected final Appendable out;
  protected boolean quit;

  /**
   * Constructor for a controller.
   * @param rd Readable to capture user input.
   * @param ap Output.
   * @throws IllegalArgumentException if the readable or appendable are null.
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null) {
      throw new IllegalArgumentException("Readable is null");
    } else if (ap == null) {
      throw new IllegalArgumentException("Appendable is null");
    }
    this.in = Objects.requireNonNull(rd);
    this.out = Objects.requireNonNull(ap);
    this.quit = false;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Model is null");
    }
    Scanner scanner = new Scanner(this.in);
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("No deck provided");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Failed to start game . " + e.getMessage());
    }
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.out);
    this.draw(model, view, false);


    while (scanner.hasNext()) {
      String input = scanner.next();

      if (input.equalsIgnoreCase("q") || quit) {
        this.draw(model, view, true);
        break;
      }

      switch (input) {
        case "rm1":
          int rm1row = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rm1card = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          try {
            model.remove(rm1row, rm1card);
            this.draw(model, view, false);
          } catch (IllegalArgumentException e) {
            append(out, "Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "rm2":
          int rm2row1 = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rm2card1 = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rm2row2 = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rm2card2 = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          try {
            model.remove(rm2row1, rm2card1, rm2row2, rm2card2);
            this.draw(model, view, false);
          } catch (IllegalArgumentException e) {
            append(out, "Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "rmwd":
          int rmwdDraw = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rmwdRow = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          int rmwdCard = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          try {
            model.removeUsingDraw(rmwdDraw, rmwdRow, rmwdCard);
            this.draw(model, view, false);
          } catch (IllegalArgumentException e) {
            append(out, "Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        case "dd":
          int ddIndex = getNextInt(scanner, out) - 1;
          if (quit) {
            break;
          }
          try {
            model.discardDraw(ddIndex);
            this.draw(model, view, false);
          } catch (IllegalArgumentException e) {
            append(out, "Invalid move. Play again. " + e.getMessage() + "\n");
          }
          break;
        default:
          append(out, "Invalid command. Fix invalid input \""
              + input + "\" and continue.\n");
      }
      if (quit) {
        this.draw(model, view, true);
        break;
      }
      if (model.isGameOver()) {
        break;
      }
    }
  }


  private void draw(PyramidSolitaireModel<?> model, PyramidSolitaireView view,
      boolean quit) throws IllegalStateException {
    try {
      if (quit) {
        out.append("Game quit!\nState of game when quit:\n");
      }
      view.render();
      if (!model.isGameOver()) {
        out.append("\nScore: " + model.getScore() + "\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
  }

  private void append(Appendable out, String value) throws IllegalStateException {
    try {
      out.append(value);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write to appendable");
    }
  }

  private int getNextInt(Scanner scanner, Appendable out) {
    while (scanner.hasNext()) {
      try {
        return scanner.nextInt();
      } catch (InputMismatchException e) {
        String invalidInput = scanner.next();
        if (invalidInput.equalsIgnoreCase("q")) {
          this.quit = true;
          return -999999;
        } else {
          append(out, "Input cannot be read. Fix invalid input \""
              + invalidInput + "\" and continue.\n");
        }
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Nothing to read.");
      }
    }
    return -999999;
  }






}









