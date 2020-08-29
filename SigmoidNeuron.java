import java.util.ArrayList;
public class SigmoidNeuron
{
    double bias;
    ArrayList<Double> weights;
    
    public SigmoidNeuron(int prevNumOfNeurons)
    {
        bias = (Math.random() * 5) - 2.5;
        weights = new ArrayList();
        for(int i = 0; i < prevNumOfNeurons; i++)
            weights.add((Math.random() * 5) - 2.5);
    }
    
    public double output(double[] inputs)
    {
        double result = 0;
        for(int i = 0; i < inputs.length; i++)
            result += (inputs[i] * weights.get(i));
        return sigmoid(result + bias);
    }
    
    private double sigmoid(double input)
    {
        double result = Math.exp(-input);
        result += 1;
        return 1 / result;
    }
}