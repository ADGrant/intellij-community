// "Fix all 'Common parts of if statement branches can be extracted' problems in file" "true"

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class IfStatementWithIdenticalBranches {
  int getX() {
    return 42;
  }

  int work() {
      int x = getX();
      return x;
  }
}