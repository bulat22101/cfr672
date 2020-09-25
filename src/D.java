import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class D {
    private static long MODULO = 998244353;
    private FastScanner in;
    private PrintWriter out;
    private long[] facts;
    private long[] oppositeFacts;

    public D(FastScanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public static void main(String[] args) {
        try (
                FastScanner in = new FastScanner(System.in);
                PrintWriter out = new PrintWriter(System.out);
        ) {
            D solution = new D(in, out);
            solution.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        int n = in.nextInt();
        setUpFacts(n + 5);
        int k = in.nextInt();
        long res = 0;
        List<Integer> events = new ArrayList<>(2 * n);
        for (int i = 0; i < n; i++) {
            int l = in.nextInt();
            int r = in.nextInt();
            events.add(l);
            events.add(-r);
        }
        events.sort((o1, o2) -> {
            int ao1 = Math.abs(o1);
            int ao2 = Math.abs(o2);
            if (ao1 == ao2) {
                return -Integer.compare(o1, o2);
            } else {
                return Integer.compare(ao1, ao2);
            }
        });
        int turnedOn = 0;
        for (int event : events) {
            if (event > 0) {
                if (turnedOn >= k - 1) {
                    res += C(turnedOn, k - 1);
                    res %= MODULO;
                }
                turnedOn++;
            } else {
                turnedOn--;
            }
        }
        out.println(res);
    }

    private void setUpFacts(int size) {
        facts = new long[size];
        oppositeFacts = new long[size];
        facts[0] = 1;
        for (int i = 1; i < size; i++) {
            facts[i] = facts[i - 1] * i % MODULO;
        }
        for (int i = 0; i < size; i++) {
            oppositeFacts[i] = inverseElement(facts[i]);
        }
    }

    private long binPow(long base, long p) {
        if (p == 1) {
            return base;
        }

        if (p % 2 == 0) {
            long t = binPow(base, p / 2);
            return t * t % MODULO;
        } else {
            return binPow(base, p - 1) * base % MODULO;
        }
    }

    long inverseElement(long x) {
        return binPow(x, MODULO - 2);
    }

    private long C(int n, int k) {
        return facts[n] * oppositeFacts[k] % MODULO * oppositeFacts[n - k] % MODULO;
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
