package calculator;

import operations.*;

import java.util.ArrayList;

public class Calculator {
    operations.GeometricMean geometricMean;
    ArithmeticMean arithmeticMean;
    StandardDeviation standardDeviation;
    Scope scope;
    CovarianceCoefficients covarianceCoefficients;
    Cardinality cardinality;
    VarianceCoefficient varianceCoefficient;
    ConfidenceInterval confidenceInterval;
    Variance variance;
    Maximum maximum;
    Minimum minimum;
    ArrayList<ArrayList<?>> results = new ArrayList<>();
    ArrayList<Operation> operations = new ArrayList<>();

    public Calculator() {
    }

    public void calculate(ArrayList<ArrayList<Double>> data) {
        results.clear();
        operations.clear();

        operations.add(geometricMean = new GeometricMean(data));
        operations.add(arithmeticMean = new ArithmeticMean(data));
        operations.add(standardDeviation = new StandardDeviation(data));
        operations.add(scope = new Scope(data));
        operations.add(cardinality = new Cardinality(data));
        operations.add(varianceCoefficient = new VarianceCoefficient(data));
        operations.add(confidenceInterval = new ConfidenceInterval(data));
        operations.add(variance = new Variance(data));
        operations.add(maximum = new Maximum(data));
        operations.add(minimum = new Minimum(data));
        covarianceCoefficients = new CovarianceCoefficients(data);
    }

    public ArrayList<ArrayList<String>> fillResults() {
        ArrayList<ArrayList<String>> resultsWithNames = new ArrayList<>();
        for (Operation operation : operations) {
            ArrayList<String> row = new ArrayList<>();
            row.add(operation.getName());
            for (Object result : operation.getResult()) {
                row.add(result.toString());
            }
            resultsWithNames.add(row);
        }
        return resultsWithNames;
    }

    public ArrayList<ArrayList<Double>> getCovariation() {
        return covarianceCoefficients.getResult();
    }

    public String getCovariationName() {
        return covarianceCoefficients.getName();
    }
}
