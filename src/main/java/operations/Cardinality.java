package operations;

import java.util.ArrayList;

public class Cardinality implements Operation {

    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<Integer> result = new ArrayList<>();

    public Cardinality(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Количество элементов";
        calculate();
    }

    public void calculate() {
        for (ArrayList<Double> doubles : list) {
            Integer size = doubles.size();
            result.add(size);
        }
    }

    @Override
    public ArrayList<Integer> getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
