import java.io.*;
import java.util.StringTokenizer;

public class A {
    private FastScanner in;
    private PrintWriter out;

    public A(FastScanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public static void main(String[] args) {
        try (
                FastScanner in = new FastScanner(System.in);
                PrintWriter out = new PrintWriter(System.out);
        ) {
            A solution = new A(in, out);
            solution.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
            }
            boolean checkReversed = true;
            for (int i = 1; i < n; i++) {
                checkReversed &= (array[i - 1] > array[i]);
            }
            out.println(checkReversed ? "NO" : "YES");
        }
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
