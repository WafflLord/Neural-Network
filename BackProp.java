package NN;

import java.util.ArrayList;

public class BackProp
{
    public static void improve(Network network, double[] input, double[] answers, double step) {
        ArrayList<Double> changes = new ArrayList();
        double total = 0;
        for(int i = 1; i < network.neurons.length; i++) {
            for(int j = 0; j < network.neurons[i]; j++) {
                for(int k = 0; k < network.neurons[i - 1]; k++) {
                    double change = getError(network.calculate(input), answers);
                    network.weights[i][j][k] += step;
                    change -= getError(network.calculate(input), answers);
                    network.weights[i][j][k] -= step;
                    changes.add(change);
                    total += Math.abs(change);
                }
            }
            double change = getError(network.calculate(input), answers);
            network.bias[i] += step;
            change -= getError(network.calculate(input), answers);
            network.bias[i] -= step;
            changes.add(change);
            total += Math.abs(change);
        }
        int num = 0;
        for(int i = 1; i < network.neurons.length; i++) {
            for(int j = 0; j < network.neurons[i]; j++) {
                for(int k = 0; k < network.neurons[i - 1]; k++) {
                    network.weights[i][j][k] += (step * changes.get(num) / total);
                    num++;
                }
            }
            network.bias[i] += (step * changes.get(num) / total);
            num++;
        }
    }
    
    private static double getError(double[] output, double[] answers) {
        double error = 0;
        for(int i = 0; i < output.length; i++) {
            error += Math.pow(answers[i] - output[i], 2);
        }
        return Math.sqrt(error);
    }
}