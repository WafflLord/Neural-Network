import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Network
{
    ArrayList<Layer> layers;
    int[] neurons;
    int totalWeights;
    int totalBias;
    
    public Network(int[] n)
    {
        neurons = n;
        totalWeights = 0;
        totalBias = 0;
        for(int i = 1; i < neurons.length; i++)
        {
            totalWeights += (neurons[i] * neurons[i - 1]);
            totalBias += neurons[i];
        }
        layers = new ArrayList();
        for(int i = 1; i < n.length; i++)
            layers.add(new Layer(n[i], n[i - 1]));
    }
    
    public double[] reason(double[] input)
    {
        return reason(input, 0);
    }
    
    private double[] reason(double[] input, int i)
    {
        if(i == layers.size() - 1)
            return layers.get(i).getOutputs(input);
        return reason(layers.get(i).getOutputs(input), i += 1);
    }
    
    public int[] getWeightIndex(int index)
    {
        double counter = 0;
        for(int i = 0; i < layers.size(); i++)
        {
            for(int j = 0; j < layers.get(i).neurons.size(); j++)
            {
                for(int k = 0; k < layers.get(i).neurons.get(j).weights.size(); k++)
                {
                    if(counter == index)
                        return new int[]{i, j, k};
                    counter++;
                }
            }
        }
        return new int[]{};
    }
    
    public int[] getBiasIndex(int index)
    {
        double counter = 0;
        for(int i = 0; i < layers.size(); i++)
        {
            for(int j = 0; j < layers.get(i).neurons.size(); j++)
            {
                if(counter == index)
                    return new int[]{i, j};
                counter++;
            }
        }
        return new int[]{};
    }
    
    public void save(String name)
    {
        try
        {
            File file = new File(name);
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            for(Layer l : layers)
            {
                for(SigmoidNeuron sn : l.neurons)
                {
                    for(double w : sn.weights)
                        fw.write(w + " ");
                    fw.write(sn.bias + " ");
                }
            }
            fw.close();
        }
        catch(Exception e)
        {}
    }
    
    public void load(String name)
    {
        try
        {
            File file = new File(name);
            System.out.println(file.exists());
            Scanner fileKey = new Scanner(file);
            for(int i = 0; i < layers.size(); i++)
            {
                for(int j = 0; j < layers.get(i).neurons.size(); j++)
                {
                    for(int k = 0; k < layers.get(i).neurons.get(j).weights.size(); k++)
                    {
                        layers.get(i).neurons.get(j).weights.remove(k);
                        layers.get(i).neurons.get(j).weights.add(k, fileKey.nextDouble());
                    }
                    layers.get(i).neurons.get(j).bias = fileKey.nextDouble();
                }
            }
            fileKey.close();
        }
        catch(Exception e)
        {}
    }
}