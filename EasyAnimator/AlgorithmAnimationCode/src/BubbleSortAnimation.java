import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Bubble sort algorithm obtained from Geeks for Geeks. This class simply utilizes that algorithm to
 * create an animation.
 */
public class BubbleSortAnimation {

  public static final int WIDTH = 10;
  public static final int HEIGHT = 10;
  public static final int RED = 0;
  public static final int GREEN = 255;
  public static final int BLUE = 0;
  public static final int X_OFFSET = 10;
  public static final int Y_OFFSET = 10;
  public static final int SIZE = 100;
  public static final int RANGE_OF_VALUES = 10;
  public static OutputStream OUTPUT_STREAM = null;

  static {
    try {
      OUTPUT_STREAM = new FileOutputStream("bubble.txt", true);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static final int TICK = 1;


  // An optimized version of Bubble Sort
  static void bubbleSort(int[] arr, int n) throws IOException {
    int i;
    int j;
    int temp;
    boolean swapped;
    for (i = 0; i < n - 1; i++) {
      swapped = false;
      for (j = 0; j < n - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          // variable for motions
          int initY1 = arr[j];
          int initY2 = arr[j + 1];
          // swap arr[j] and arr[j+1]
          temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
          swapped = true;
        }
      }

      // IF no two elements were
      // swapped by inner loop, then break
      if (!swapped) {
        break;
      }
    }
  }

  /**
   * Main driver code.
   *
   * @param args of something.
   * @throws IOException if output stream breaks.
   */
  public static void main(String[] args) throws IOException {
    List<Integer> nums = new ArrayList<>();

    // Create a for loop that generates 100 random ints
    for (int i = 0; i < SIZE; i++) {
      nums.add(new Random().nextInt(RANGE_OF_VALUES) * Y_OFFSET);
    }

    // declare canvas
    int maxNum = Collections.max(nums);
    OUTPUT_STREAM
        .write(
            String.format("canvas %d %d %d %d\n", 0, 0, nums.size() * X_OFFSET, HEIGHT * HEIGHT * 2)
                .getBytes());

    // declare shapes
    for (int i = 0; i < nums.size(); i++) {
      OUTPUT_STREAM.write(String.format("shape E%d ellipse\n", i + 1).getBytes());
    }

    // set init positions
    for (int i = 0; i < nums.size(); i++) {
      OUTPUT_STREAM.write(String
          .format("motion E%d %d %d %d %d %d %d %d %d  %d %d %d %d %d %d %d %d\n", i + 1, TICK,
              i * X_OFFSET,
              nums.get(i), WIDTH, HEIGHT, RED, GREEN, BLUE, TICK + 10, i * X_OFFSET, nums.get(i),
              WIDTH,
              HEIGHT, RED,
              GREEN, BLUE).getBytes());
    }

    int[] arr = nums.stream().mapToInt(Integer::intValue).toArray();
    int n = arr.length;
    bubbleSort(arr, n);

    for (int i = 0; i < n; i++) {
      OUTPUT_STREAM.write(String
          .format("motion E%d %d %d %d %d %d %d %d %d  %d %d %d %d %d %d %d %d\n", i + 1, TICK + 10,
              i * X_OFFSET,
              nums.get(i), WIDTH, HEIGHT, RED, GREEN, BLUE, TICK + 20, i * X_OFFSET, arr[i], WIDTH,
              HEIGHT, RED,
              GREEN, BLUE).getBytes());
      OUTPUT_STREAM.write(String
          .format("motion E%d %d %d %d %d %d %d %d %d  %d %d %d %d %d %d %d %d\n", i + 1, TICK + 20,
              i * X_OFFSET,
              arr[i], WIDTH, HEIGHT, RED, GREEN, BLUE, TICK + 30, i * X_OFFSET, arr[i], WIDTH,
              HEIGHT, RED,
              GREEN, BLUE).getBytes());
    }

  }

}
