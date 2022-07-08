package intWithManyDivisors;

import java.util.Scanner;

public class IntegerWithLargestNumberOfDivisors {
    private final static int MAX = 100000;
    private static int maxDivisorCounter = 0;
    private static int intMaxDivisors;
    synchronized private static void report(int maxCounterFromThread, int intMaxFromThread) {
        if (maxCounterFromThread > maxDivisorCounter) {
            maxDivisorCounter = maxCounterFromThread;
            intMaxDivisors = intMaxFromThread;
        }
    }
    private static class CountDivisorsThread extends Thread {
        final int min, max;
        public CountDivisorsThread(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public void run() {
            int maxDivisors = 0;
            int whichInteger = 0;
            for (int i = min; i < max; i++) {
                int divisors = countDivisors(i);
                if (divisors > maxDivisors) {
                    maxDivisors = divisors;
                    whichInteger = i;
                }
            }
            report(maxDivisors, whichInteger);
        }
    }
    private static void countDivisorsWithThreads(int numberOfThreads) {
        System.out.println("\nCounting divisors using " + numberOfThreads + " threads...");
        long startTime = System.currentTimeMillis();
        CountDivisorsThread[] worker = new CountDivisorsThread[numberOfThreads];
        int integersPerThread = MAX / numberOfThreads;
        int start = 1;
        int end = integersPerThread;
        for (int i = 0; i < numberOfThreads; i++) {
            if (i == numberOfThreads - 1) {
                end = MAX;
            }
            worker[i] = new CountDivisorsThread(start, end);
            start = end + 1;
            end = start + integersPerThread - 1;
        }
        maxDivisorCounter = 0;
        for (int i = 0; i < numberOfThreads; i++)
            worker[i].start();
        for (int i = 0; i < numberOfThreads; i++) {
            while (worker[i].isAlive()) {
                try {
                    worker[i].join();
                } catch (InterruptedException ignored) {}
            }
        }
        System.out.println("\nThe largest number of divisors between 1 and " + MAX + " is " + maxDivisorCounter);
        System.out.println("The integer with multiple divisors: " + intMaxDivisors);
    }
    public static int countDivisors(int number) {
        int count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0)
                ++count;
        }
        return count;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int nrThreads = 0;
        while (nrThreads <= 1 || nrThreads > 10) {
            System.out.print("Do you wanna use from 1 up to maximum 10 threads? ");
            nrThreads = in.nextInt();
            if (nrThreads <= 1 || nrThreads > 10)
                System.out.println("If so, please enter a number from 1 to 10: ");
        }
        countDivisorsWithThreads(nrThreads);
    }
}





















//public class Adder { // how Multiple Threading work in Java
//// an input file with numbers inside & write the result out to another file;
//// use a couple of fields for the file names.
//    private String inFile, outFile;
//    public Adder(String inFile, String outFile) { /* assign filenames to member fields */ }
//// up, a Constructor -> that accepts those file names
//// down, a Method: doAdd() -> that add up the numbers inside of a file and write them out.
//    public void doAdd() { // we wanna total up the Values of a Local variable.
//        int total = 0;
//        String line = null;
//        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inFile))) {
//            String inline;
//            while ((line = reader.readLine()) != null)
//                total += Integer.parseInt(inline);// each line Converted to Integer and add it to the total.
//        }
//        try (BufferedReader writer - Files.newBufferWriter(Paths.get(outFile))) {
//
//        }
//// up, opened up an Output file, where I write the result out of that file.
//    }
//}
