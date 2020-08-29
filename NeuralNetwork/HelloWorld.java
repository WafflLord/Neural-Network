public class HelloWorld {
    static String target = "Hello I want to jump into a pit of lava";
    static String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz \',.:;\"\\/?<>!@#$%^&*()_+-=1234567890{}[]\n";

    public static void main(String[] args) {
        int networksPerLetter = 100;
        Network[] letters = new Network[networksPerLetter * target.length()];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = new Network(new int[]{1, 1});
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
            int counter = 0;
            line = "";
            for (int i = 0; i < networksPerLetter; i++) {
                if (!s.equals(target)) {
                    s = "";
                    for (int j = 0; j < target.length(); j++) {
                        if (!keep[j]) {
                            word[j] = alphabet.charAt((int) ((letters[counter].reason(new double[]{input[j]}))[0] * alphabet.length()));
                        }
                        s += word[j];
                        fitnesses[j][i] = calcFitness(target.charAt(j), word[j]);
                        counter++;
                    }
                    if (Math.abs(s.compareTo(target)) < Math.abs(line.compareTo(target)))
                        line = s;
                    //System.out.println(s);
                }
            }
            if (line.length() > 0) {
                for (int j = 0; j < target.length(); j++) {
                    if (target.charAt(j) == line.charAt(j)) {
                        word[j] = line.charAt(j);
                        keep[j] = true;
                    }
                }
            }
            counter = 0;
            for (int i = 0; i < networksPerLetter; i++) {
                for (int j = 0; j < target.length(); j++) {
                    int[] parents = GeneticCode.select(fitnesses[j], 0);
                    parents[0] *= target.length();
                    parents[1] *= target.length();
                    parents[0] += j;
                    parents[1] += j;
                    //System.out.println(alphabet.charAt((int) ((letters[parents[0]].reason(new double[]{input[j]}))[0] * alphabet.length())));
                    letters[counter] = GeneticCode.createOffspring(letters, parents, 0.01, 0.05);
                    //System.out.println(alphabet.charAt((int) ((letters[counter].reason(new double[]{input[j]}))[0] * alphabet.length())));
                    counter++;
                }
            }
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
