import ru.ifmo.rain.golikov.mockapi.ApiClient;
import ru.ifmo.rain.golikov.mockapi.ApiManager;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    var client = new ApiClient("104f8af6104f8af61012eee30210177ef91104f104f8af648e710b8a4b63d445e681ded");
    var manager = new ApiManager(client);
    var count = manager.countPosts("icpc", 24);
    System.out.println(Arrays.toString(count));
  }
}
