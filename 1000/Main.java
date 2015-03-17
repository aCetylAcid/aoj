import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
  public static void main (String[] args) throws Exception {
    BufferedReader br;
    br = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      Long[] nums = Utils.readNums(br, 2);

      if (nums != null) {
        System.out.println(nums[0] + nums[1]);
      } else {
        return ;
      }
    }
  }
}

class Utils
{
  /**
  *  Read nums from standard inputs.
  *  @param  br    buffered reader
  *  @param  limit number of input numbers
  *  @return numbers (Long[])
  *  @throws IllegalArgumentException if there is input exceptions
  */
  public static Long[] readNums (BufferedReader br, int limit) {
    Long[] nums = new Long[2];

    String line;
    try {
      if ((line = br.readLine()) == null) {
        return null;
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }

    if (line.split(" ").length != 2) {
      throw new IllegalArgumentException();
    }

    String[] words = line.split(" ");
    for (int i=0; i<words.length; i++) {
      try {
        nums[i] = Long.parseLong(words[i]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException();
      }
    }

    return nums;
  }
}
