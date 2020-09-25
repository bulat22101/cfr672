import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class E {
    private FastScanner in;
    private PrintWriter out;

    public E(FastScanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public static void main(String[] args) {
        try (
                FastScanner in = new FastScanner(System.in);
                PrintWriter out = new PrintWriter(System.out);
        ) {
            E solution = new E(in, out);
            solution.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        List<Integer> segments = getSegments(array);
        List<Integer> prefSums = getPrefSums(segments);
        int sum = prefSums.get(prefSums.size() - 1);
        int segmentsN = segments.size();
        int maxActions = n * (n - 1) / 2;
        int[][][] dp = new int[segmentsN + 3][sum + 3][maxActions + 3];
        for (int[][] matrix :
                dp) {
            for (int[] row : matrix) {
                Arrays.fill(row, Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        for (int i = 0; i < segmentsN; i++) {
            for (int j = 0; j <= sum; j++) {
                for (int k = 0; k <= maxActions; k++) {
                    if (dp[i][j][k] == Integer.MAX_VALUE) {
                        continue;
                    }
                    for (int l = j; l <= sum; l++) {
                        int h = l - j;
                        int newK = k + Math.abs(prefSums.get(i) - l);
                        dp[i + 1][l][newK] = Math.min(dp[i + 1][l][newK], dp[i][j][k] + h * h);
                    }
                }
            }
        }
        int previous = Integer.MAX_VALUE;
        for (int k = 0; k <= maxActions; k++) {
            int current = Math.min(previous, dp[segmentsN][sum][k]);
            out.println((sum * sum - current) / 2);
            previous = current;
        }
    }

    private List<Integer> getSegments(int[] array) {
        List<Integer> res = new ArrayList<>();
        int count = 0;
        for (int value : array) {
            if (value == 0) {
                count++;
            } else {
                res.add(count);
                count = 0;
            }
        }
        res.add(count);
        return res;
    }

    private List<Integer> getPrefSums(List<Integer> list) {
        List<Integer> res = new ArrayList<>(list.size());
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
            res.add(sum);
        }
        return res;
    }

    public static class FastScanner implements Closeable {
        BufferedReader bufferedReader;
        StringTokenizer stringTokenizer;

        public FastScanner(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public FastScanner(File file) throws FileNotFoundException {
            this(new FileInputStream(file));
        }

        public String next() throws IOException {
            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    throw new EOFException("End of input stream is reached.");
                }
                stringTokenizer = new StringTokenizer(line);
            }
            return stringTokenizer.nextToken();
        }

        public String nextLine() throws IOException {
            stringTokenizer = null;
            return bufferedReader.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        @Override
        public void close() throws IOException {
            bufferedReader.close();
        }
    }
}
