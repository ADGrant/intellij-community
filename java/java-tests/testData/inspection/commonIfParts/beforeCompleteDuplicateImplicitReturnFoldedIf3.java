// "Fix all 'Common parts of if statement branches can be extracted' problems in file" "false"

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class IfStatementWithIdenticalBranches {
  int work() {
    if (true) {
      System.out.println();
      if <caret>(false) {
        System.out.println();
        System.out.println();
        return;
      }
      System.out.println();
    }
    System.out.println();
  }
}