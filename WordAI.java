package NN;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class WordAI {
    public static void main(String[] args) {
        String ques = "    ";
        Network network = new Network(new int[]{1, 200, 200, 1});
        ArrayList<String> Q = new ArrayList();
        ArrayList<String> A = new ArrayList();
        while (!ques.toLowerCase().substring(0, 4).equals("exit")) {
            System.out.println("Say something");
            Scanner key = new Scanner(System.in);
            ques = key.nextLine();
            Scanner s = new Scanner(ques);
            int num = -1;
            while (s.hasNext()) {
                s.next();
                num++;
            }
            for (int i = num; i < 9; i++)
                ques += " _";
            double[] input = new double[1];
            if(!Q.contains(ques))
                Q.add(ques);
            System.out.println("How should the AI respond?");
            String ans = key.nextLine();
            s = new Scanner(ans);
            num = -1;
            while (s.hasNext()) {
                s.next();
                num++;
            }
            for (int i = num; i < 9; i++)
                ans += " _";
            String out = "";
            String lastOut = "";
            double[] answer = new double[1];
            if(!A.contains(ans))
                A.add(ans);
            int tries = 10000;
            while(tries > Q.size()) {
                tries = 0;
                for(int i = 0; i < Q.size(); i++) {
                    input[0] = ((double) i / Q.size()) + (0.5 / Q.size());
                    answer[0] = ((double) i / A.size()) + (0.5 / A.size());
                    out = "";
                    ans = A.get(i);
                    while (!out.equals(ans)) {
                        double[] output = network.calculate(input);
                        out = A.get((int) (output[0] * A.size()));
                        if (!lastOut.equals(out))
                            System.out.println(out);
                        BackProp.improve(network, input, answer, 0.01);
                        lastOut = out;
                        tries++;
                    }
                }
            }
        }
        System.out.println("Say something");
        ques = "    ";
        while (!ques.toLowerCase().substring(0, 4).equals("exit")) {
            Scanner key = new Scanner(System.in);
            ques = key.nextLine();
            Scanner s = new Scanner(ques);
            int num = -1;
            while (s.hasNext()) {
                s.next();
                num++;
            }
            for (int i = num; i < 9; i++)
                ques += " _";
            double[] input = new double[1];
            input[0] = ((double) Q.indexOf(ques) / Q.size()) + (0.5 / Q.size());
            double[] output = network.calculate(input);
            String out = A.get((int) (output[0] * A.size()));
            System.out.println(out);
        }
    }
}
