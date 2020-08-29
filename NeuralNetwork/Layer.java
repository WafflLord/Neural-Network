import java.util.ArrayList;
public class Layer
{
    ArrayList<SigmoidNeuron> neurons;
    
    public Layer(int numOfNeurons, int prevNumOfNeurons)
    {
        neurons = new ArrayList();
        for(int i = 0; i < numOfNeurons; i++)
        {
            neurons.add(new SigmoidNeuron(prevNumOfNeurons));
        }
    }
    
    public double[] getOutputs(double[] input)
    {
        double[] result = new double[neurons.size()];
        for(int i = 0; i < result.length; i++)
        {
            result[i] = neurons.get(i).output(input);
        }
        return result;
    }
}