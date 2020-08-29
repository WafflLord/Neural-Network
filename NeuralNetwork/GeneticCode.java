import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneticCode {
    public static ArrayList objects;

    public static void createPopulation(Network[] networks, Class c) {
        objects = new ArrayList();
        for (int i = 0; i < networks.length; i++) {
            try {
                objects.add(c.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
            }
        }
    }

    public static int[] select(double[] fitnesses, double room) {
        double max = Double.MIN_VALUE;
        int lastMax = 0;
        ArrayList<Integer> choices = new ArrayList<>();
        choices.add(0);
        int[] parents = new int[2];
        for (int i = 0; i < fitnesses.length; i++) {
            if (fitnesses[i] > (max + room)) {
                max = fitnesses[i];
                lastMax = choices.get(0);
                choices.clear();
                choices.add(i);
            } else if (fitnesses[i] <= (max + room) && fitnesses[i] >= max) {
                choices.add(i);
            }
        }
        if(choices.size() > 1) {
            parents[0] = choices.get((int)(Math.random() * choices.size()));
            parents[1] = parents[0];
            while (parents[1] == parents[0])
                parents[1] = choices.get((int)(Math.random() * choices.size()));
        } else {
            parents[0] = choices.get(0);
            parents[1] = lastMax;
        }
        return parents;
    }


    public static Network createOffspring(Network[] networks, int[] parents, double mr, double mc) {
        double mutationRate = mr;
        double mutationChange = mc * 2;

        Network parentA = networks[parents[0]];
        Network parentB = networks[parents[1]];

        Network offspring = new Network(parentA.neurons);

        for (int i = 0; i < offspring.layers.size(); i++) {
            for (int j = 0; j < offspring.layers.get(i).neurons.size(); j++) {
                if (Math.random() > mutationRate) {
                    if (Math.random() > 0.5) {
                        offspring.layers.get(i).neurons.get(j).bias = parentA.layers.get(i).neurons.get(j).bias;
                    } else {
                        offspring.layers.get(i).neurons.get(j).bias = parentB.layers.get(i).neurons.get(j).bias;
                    }
                } else {
                    offspring.layers.get(i).neurons.get(j).bias += (Math.random() * mutationChange) - mc;
                }
                for (int k = 0; k < parentA.layers.get(i).neurons.get(j).weights.size(); k++) {
                    if (Math.random() > mutationRate) {
                        if (Math.random() > 0.5)
                            offspring.layers.get(i).neurons.get(j).weights.add(k, parentA.layers.get(i).neurons.get(j).weights.get(k));
                        else
                            offspring.layers.get(i).neurons.get(j).weights.add(k, parentB.layers.get(i).neurons.get(j).weights.get(k));
                    } else {
                        offspring.layers.get(i).neurons.get(j).weights.add(k, offspring.layers.get(i).neurons.get(j).weights.get(k) + (Math.random() * mutationChange) - mc);
                    }
                    offspring.layers.get(i).neurons.get(j).weights.remove(k + 1);
                }
            }
        }

        return offspring;
    }

    public static Object createOffspring(Network[] networks, int[] parents, Class c, double mr, double mc) {
        double mutationRate = mr;
        double mutationChange = mc * 2;

        Network parentA = networks[parents[0]];
        Network parentB = networks[parents[1]];

        Network offspring = new Network(parentA.neurons);

        for (int i = 0; i < offspring.layers.size(); i++) {
            for (int j = 0; j < offspring.layers.get(i).neurons.size(); j++) {
                if (Math.random() > mutationRate) {
                    if (Math.random() > 0.5) {
                        offspring.layers.get(i).neurons.get(j).bias = parentA.layers.get(i).neurons.get(j).bias;
                    } else {
                        offspring.layers.get(i).neurons.get(j).bias = parentB.layers.get(i).neurons.get(j).bias;
                    }
                } else {
                    offspring.layers.get(i).neurons.get(j).bias += (Math.random() * mutationChange) - mc;
                }
                for (int k = 0; k < parentA.layers.get(i).neurons.get(j).weights.size(); k++) {
                    if (Math.random() > mutationRate) {
                        if (Math.random() > 0.5)
                            offspring.layers.get(i).neurons.get(j).weights.add(k, parentA.layers.get(i).neurons.get(j).weights.get(k));
                        else
                            offspring.layers.get(i).neurons.get(j).weights.add(k, parentB.layers.get(i).neurons.get(j).weights.get(k));
                    } else {
                        offspring.layers.get(i).neurons.get(j).weights.add(k, offspring.layers.get(i).neurons.get(j).weights.get(k) + (Math.random() * mutationChange) - mc);
                    }
                    offspring.layers.get(i).neurons.get(j).weights.remove(k + 1);
                }
            }
        }

        try {
            return c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
        }

        return 0;
    }
}
