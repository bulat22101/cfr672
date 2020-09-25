import java.io.*;
import java.util.StringTokenizer;

public class B {
    private FastScanner in;
    private PrintWriter out;

    public B(FastScanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public static void main(String[] args) {
        try (
                FastScanner in = new FastScanner(System.in);
                PrintWriter out = new PrintWriter(System.out);
        ) {
            B solution = new B(in, out);
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
            int[] count = new int[100];
            long res = 0;
            count[getLargestBitPos(array[n - 1])]++;
            for (int i = n - 2; i >= 0; i--) {
                int largestBit = getLargestBitPos(array[i]);
                res += count[largestBit];
                count[largestBit]++;
            }
            out.println(res);
        }
    }

    int getLargestBitPos(int x) {
        int res = 0;
        while (x > 0) {
            x >>= 1;
            res++;
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
