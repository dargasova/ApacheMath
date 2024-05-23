package operations;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;

public class StandardDeviation implements Operation {

    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<Double> result = new ArrayList<>();

    public StandardDeviation(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Стандартное отклонение";
        calculate();
    }

    public void calculate() {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (ArrayList<Double> doubles : list) {
            doubles.forEach(stats::addValue);
            result.add(stats.getStandardDeviation());
        }
    }

    @Override
    public ArrayList<Double> getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
