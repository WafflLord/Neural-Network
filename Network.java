import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Network {
    double fitness;
    int[] neurons;
    double[][][] weights;
    double[] bias;

    public Network(int[] n) {
        neurons = n;
        int largestNeuron = -Integer.MAX_VALUE;
        for (int ne : neurons) {
            if (ne > largestNeuron)
                largestNeuron = ne;
        }
        weights = new double[neurons.length][largestNeuron][largestNeuron];
        bias = new double[neurons.length];
        for (int i = 1; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i]; j++) {
                for (int k = 0; k < neurons[i - 1]; k++)
                    weights[i][j][k] = (Math.random() * 5) - 2.5;
            }
            bias[i] = (Math.random() * 5) - 2.5;
        }
    }

    public double[] calculate(double[] input) {
        return calculate(input, 1);
    }

    private double[] calculate(double[] input, int l) {
        if (l == neurons.length - 1)
            return layer(input, l);
        return calculate(layer(input, l), l + 1);
    }

    private double[] layer(double[] input, int layer) {
        double[] results = new double[neurons[layer]];
        for (int i = 0; i < neurons[layer]; i++) {
            results[i] = 0;
            for (int j = 0; j < input.length; j++)
                results[i] += input[j] * weights[layer][i][j];
            results[i] = sigmoid(results[i] + bias[layer]);
        }
        return results;
    }

    public void copy(Network network) {
        for (int i = 1; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i]; j++) {
                for (int k = 0; k < neurons[i - 1]; k++) {
                    weights[i][j][k] = network.weights[i][j][k];
                }
            }
            bias[i] = network.bias[i];
        }
    }

    public void save(String name) {
        try {
            File file = new File(name);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);

            for (int i = 1; i < neurons.length; i++) {
                for (int j = 0; j < neurons[i]; j++) {
                    for (int k = 0; k < neurons[i - 1]; k++) {
                        fw.write(weights[i][j][k] + " ");
                    }
                }
                fw.write(bias[i] + " ");
            }
            fw.close();
        } catch (Exception e) {

        }
    }

    public void load(String name) {
        try {
            File file = new File(name);
            Scanner in = new Scanner(file);

            for (int i = 1; i < neurons.length; i++) {
                for (int j = 0; j < neurons[i]; j++) {
                    for (int k = 0; k < neurons[i - 1]; k++) {
                        weights[i][j][k] = in.nextDouble();
                    }
                }
                bias[i] = in.nextDouble();
            }
            in.close();
        } catch (Exception e) {

        }
    }

    private double sigmoid(double value) {
        double result = Math.exp(-value);
        result = 1 + result;
        return 1 / result;
    }
}
