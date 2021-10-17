import ru.ifmo.rain.golikov.mockapi.api.ApiClient;
import ru.ifmo.rain.golikov.mockapi.api.ApiManager;

import java.util.*;

public class Main {
  public static void main(String[] args) {
    var client = ApiClient.fromEnv();
    var manager = new ApiManager(client);
    var count = manager.countPosts("#food", 24);
    System.out.println(Arrays.toString(count));
  }
}
