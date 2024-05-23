package operations;

import java.util.ArrayList;

public interface Operation {

    void calculate();

    ArrayList<?> getResult();

    String getName();
}
