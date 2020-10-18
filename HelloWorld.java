package NN;

public class HelloWorld {
    public static String target = "A robot may not injure a human being or, through inaction, allow a human being to come to harm.\nA robot must obey orders given it by human beings except where such orders would conflict with the First Law.\nA robot must protect its own existence as long as such protection does not conflict with the First or Second Law.";
    public static String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz \',.:;\"\\/?<>!@#$%^&*()_+-=1234567890{}[]\n";

    public static void main(String[] args) {
        int networksPerLetter = 1000;
        Network[][] letters = new Network[target.length()][networksPerLetter];
        for (int i = 0; i < target.length(); i++) {
            for (int j = 0; j < networksPerLetter; j++) {
                letters[i][j] = new Network(new int[]{1, 1});
            }
        }
        double[][] fitnesses = new double[target.length()][networksPerLetter];
        char[] word = new char[target.length()];
        for (int i = 0; i < word.length; i++)
            word[i] = ' ';
        String s = "";
        String line = "";
        for (char c : word)
            s += c;
        boolean[] keep = new boolean[target.length()];
        for (int i = 0; i < keep.length; i++)
            keep[i] = false;
        double[] input = new double[target.length()];
        for (int j = 0; j < input.length; j++) {
            input[j] = sigmoid(alphabet.indexOf(target.charAt(j)));
        }
        while (!s.equals(target)) {
            line = "";
            for (int i = 0; i < networksPerLetter; i++) {
                s = "";
                for (int j = 0; j < target.length(); j++) {
                    if (!s.equals(target)) {
                        if (!keep[j]) {
                            word[j] = alphabet.charAt((int) ((letters[j][i].calculate(new double[]{input[j]}))[0] * alphabet.length()));
                        }
                        s += word[j];
                        fitnesses[j][i] = calcFitness(target.charAt(j), word[j]);
                    }
                }
                if (Math.abs(s.compareTo(target)) < Math.abs(line.compareTo(target)))
                    line = s;
                System.out.println(s);
            }
            if (line.length() > 0) {
                for (int j = 0; j < target.length(); j++) {
                    if (target.charAt(j) == line.charAt(j)) {
                        //word[j] = line.charAt(j);
                        //keep[j] = true;
                    }
                }
            }
            Network[][] newGen = new Network[fitnesses.length][networksPerLetter];
            for (int i = 0; i < target.length(); i++) {
                newGen[i] = GeneticCode.createGen(letters[i], fitnesses[i], 0.001);
            }
            letters = newGen;
            System.out.println(line);
            System.out.println();
        }
    }

    public static double calcFitness(char t, char c) {
        double d = Math.abs(alphabet.indexOf(t) - alphabet.indexOf(c));
        return -d + alphabet.length();
    }

    private static double sigmoid(double input) {
        double result = Math.exp(-input);
        result += 1;
        return 1 / result;
    }
}