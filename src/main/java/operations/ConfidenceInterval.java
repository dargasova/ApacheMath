package operations;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.List;

public class ConfidenceInterval implements Operation {

    String name;
    ArrayList<ArrayList<Double>> list;
    ArrayList<String> result = new ArrayList<>();

    public ConfidenceInterval(ArrayList<ArrayList<Double>> list) {
        this.list = list;
        this.name = "Доверительный интервал";
        calculate();
    }

    public void calculate() {

        result = new ArrayList<>();
        List<Double> mean = new ArrayList<>();

        for (List<Double> column : list) {
            double mn = StatUtils.mean(column.stream().mapToDouble(Double::doubleValue).toArray());
            mean.add(mn);
        }

        List<Double> sd = new ArrayList<>();

        for (List<Double> column : list) {
            double ssdd = StatUtils.variance((column.stream().mapToDouble(Double::doubleValue).toArray()));
            sd.add(Math.sqrt(ssdd));
        }

        List<Integer> size = new ArrayList<>();

        for (List<Double> column : list) {
            Integer quantity = column.size();
            size.add(quantity);
        }

        double confidenceLevel = 0.95;

        for (int ind = 0; ind < list.size(); ind++) {
            NormalDistribution normalDistribution = new NormalDistribution();
            double quant = normalDistribution.inverseCumulativeProbability(1 - (1 - confidenceLevel) / 2);
            double marginOfError = quant * (sd.get(ind) / size.get(ind));
            double lowerBound = mean.get(ind) - marginOfError;
            double upperBound = mean.get(ind) + marginOfError;
            String interval = "[" + lowerBound + "; " + upperBound + "]";
            result.add(interval);
        }
    }

    @Override
    public ArrayList<String> getResult() {
        return result;
    }

    public String getName() {
        return name;
    }
}
