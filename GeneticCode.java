package NN;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class GeneticCode {

    public static Network[] createGen(Network[] networks, double[] fitnesses, double mutationRate) {
        Network[] parents = getParents(networks, fitnesses);
        Network[] newGen = new Network[networks.length];
        for (int i = 0; i < newGen.length; i++)
            newGen[i] = new Network(networks[0].neurons);

        for (int i = 1; i < networks[0].neurons.length; i++) {
            for (int j = 0; j < networks[0].neurons[i]; j++) {
                for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                    newGen[newGen.length - 2].weights[i][j][k] = parents[1].weights[i][j][k];
                    newGen[newGen.length - 1].weights[i][j][k] = parents[0].weights[i][j][k];
                }
            }
            newGen[newGen.length - 2].bias[i] = parents[1].bias[i];
            newGen[newGen.length - 1].bias[i] = parents[0].bias[i];
        }
        newGen[newGen.length - 2].fitness = parents[1].fitness;
        newGen[newGen.length - 1].fitness = parents[0].fitness;
        for (int net = 2; net < networks.length; net++) {
            if (Math.random() > 0.5) {
                for (int i = 1; i < networks[0].neurons.length; i++) {
                    for (int j = 0; j < networks[0].neurons[i]; j++) {
                        for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                            if (Math.random() > mutationRate)
                                newGen[net].weights[i][j][k] = parents[0].weights[i][j][k];
                            else
                                newGen[net].weights[i][j][k] = (Math.random() * 5) - 2.5;
                        }
                    }
                    if (Math.random() > mutationRate)
                        newGen[net].bias[i] = parents[0].bias[i];
                    else
                        newGen[net].bias[i] = (Math.random() * 5) - 2.5;
                }
            } else {
                for (int i = 1; i < networks[0].neurons.length; i++) {
                    for (int j = 0; j < networks[0].neurons[i]; j++) {
                        for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                            if (Math.random() > mutationRate)
                                newGen[net].weights[i][j][k] = parents[1].weights[i][j][k];
                            else
                                newGen[net].weights[i][j][k] = (Math.random() * 5) - 2.5;
                        }
                    }
                    if (Math.random() > mutationRate)
                        newGen[net].bias[i] = parents[1].bias[i];
                    else
                        newGen[net].bias[i] = (Math.random() * 5) - 2.5;
                }
            }
        }

        return newGen;
    }

    public static Network[] createGen(Network[] networks, double[] fitnesses, double mutationRate, int threads, int num) {
        Network[] parents = getParents(networks, fitnesses, threads, num);
        Network[] newGen = new Network[networks.length];
        for (int i = 0; i < newGen.length; i++)
            newGen[i] = new Network(networks[0].neurons);

        for (int i = 1; i < networks[0].neurons.length; i++) {
            for (int j = 0; j < networks[0].neurons[i]; j++) {
                for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                    newGen[newGen.length - 2].weights[i][j][k] = parents[1].weights[i][j][k];
                    newGen[newGen.length - 1].weights[i][j][k] = parents[0].weights[i][j][k];
                }
            }
            newGen[newGen.length - 2].bias[i] = parents[1].bias[i];
            newGen[newGen.length - 1].bias[i] = parents[0].bias[i];
        }
        newGen[newGen.length - 2].fitness = parents[1].fitness;
        newGen[newGen.length - 1].fitness = parents[0].fitness;
        for (int net = 2; net < networks.length; net++) {
            if (Math.random() > 0.5) {
                for (int i = 1; i < networks[0].neurons.length; i++) {
                    for (int j = 0; j < networks[0].neurons[i]; j++) {
                        for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                            if (Math.random() > mutationRate)
                                newGen[net].weights[i][j][k] = parents[0].weights[i][j][k];
                            else
                                newGen[net].weights[i][j][k] = (Math.random() * 5) - 2.5;
                        }
                    }
                    if (Math.random() > mutationRate)
                        newGen[net].bias[i] = parents[0].bias[i];
                    else
                        newGen[net].bias[i] = (Math.random() * 5) - 2.5;
                }
            } else {
                for (int i = 1; i < networks[0].neurons.length; i++) {
                    for (int j = 0; j < networks[0].neurons[i]; j++) {
                        for (int k = 0; k < networks[0].neurons[i - 1]; k++) {
                            if (Math.random() > mutationRate)
                                newGen[net].weights[i][j][k] = parents[1].weights[i][j][k];
                            else
                                newGen[net].weights[i][j][k] = (Math.random() * 5) - 2.5;
                        }
                    }
                    if (Math.random() > mutationRate)
                        newGen[net].bias[i] = parents[1].bias[i];
                    else
                        newGen[net].bias[i] = (Math.random() * 5) - 2.5;
                }
            }
        }

        return newGen;
    }

    private static Network[] getParents(Network[] networks, double[] fitnesses) {
        Network max = networks[0];
        Network max2 = networks[1];
        networks[0].fitness = fitnesses[0];
        networks[1].fitness = fitnesses[1];
        for (int i = 2; i < networks.length; i++) {
            networks[i].fitness = fitnesses[i];
            if (networks[i].fitness > max.fitness)
                max = networks[i];
            if (networks[i - 1].fitness > max2.fitness && max != networks[i - 1])
                max2 = networks[i - 1];
        }
        return new Network[]{max, max2};
    }

    private static Network[] getParents(Network[] networks, double[] fitnesses, int threads, int num) {
        Network max = networks[0];
        Network max2 = networks[1];
        networks[0].fitness = fitnesses[0];
        networks[1].fitness = fitnesses[1];
        for (int i = 2; i < networks.length; i++) {
            networks[i].fitness = fitnesses[i];
            if (networks[i].fitness > max.fitness)
                max = networks[i];
            if (networks[i - 1].fitness > max2.fitness && max != networks[i - 1])
                max2 = networks[i - 1];
        }
        try {
            File parents = new File("parents.txt");
            File done = new File("done.txt");
            File done2 = new File("done2.txt");
            File parent1 = new File("parentOne.txt");
            File parent2 = new File("parentTwo.txt");
            if (!parents.exists())
                parents.createNewFile();
            String line = "";
            int parentNum = 0;
            Scanner s;
            while (parentNum / 2 < num) {
                s = new Scanner(parents);
                parentNum = 0;
                line = "";
                while (s.hasNext()) {
                    line += s.next() + " ";
                    parentNum++;
                }
                s.close();
            }
            FileWriter fw = new FileWriter(parents);
            fw.write(line + max.fitness + " " + max2.fitness + " ");
            fw.close();
            if (num == threads - 1)
                done.createNewFile();
            else {
                while (!done.exists()) {
                }
            }
            if (num == 0) {
                s = new Scanner(parents);
                double x = 0;
                double lastX = -Double.MAX_VALUE;
                double mostFit = -Double.MAX_VALUE;
                double mostFit2 = -Double.MAX_VALUE;
                int bestNum = 0;
                int bestNum2 = 0;
                int i = -1;
                while (s.hasNext()) {
                    i++;
                    if ((x = s.nextDouble()) > mostFit) {
                        mostFit = x;
                        bestNum = i;
                    }
                    if (lastX > mostFit2 && lastX != mostFit) {
                        mostFit2 = lastX;
                        bestNum2 = i - 1;
                    }
                    lastX = x;
                }
                if (x > mostFit2 && x != mostFit) {
                    bestNum2 = i;
                }
                s.close();
                fw = new FileWriter(done);
                fw.write(bestNum + " " + bestNum2 + " ");
                fw.close();
                parents.delete();
                done2.createNewFile();
            } else {
                while (!done2.exists()) {
                }
            }
            s = new Scanner(done);
            int bestNum = s.nextInt();
            int bestNum2 = s.nextInt();
            s.close();
            if (bestNum == parentNum || bestNum2 == parentNum) {
                if (bestNum == parentNum) {
                    max.save("parentOne.txt");
                    while (!done.delete()) {
                    }
                } else {
                    max.save("parentTwo.txt");
                    done2.delete();
                }
            }
            if (bestNum == parentNum + 1 || bestNum2 == parentNum + 1) {
                if (bestNum == parentNum + 1) {
                    max2.save("parentOne.txt");
                    while (!done.delete()) {
                    }
                } else {
                    max2.save("parentTwo.txt");
                    done2.delete();
                }
            }
            while (done.exists() || done2.exists()) {
            }
            max.load("parentOne.txt");
            max2.load("parentTwo.txt");
            parent1.delete();
            parent2.delete();
        } catch (Exception e) {

        }
        return new Network[]{max, max2};
    }
}