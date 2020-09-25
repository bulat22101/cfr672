import java.io.*;
import java.util.StringTokenizer;

public class C2 {
    private FastScanner in;
    private PrintWriter out;

    public C2(FastScanner in, PrintWriter out) {
        this.in = in;
        this.out = out;
    }

    public static void main(String[] args) {
        try (
                FastScanner in = new FastScanner(System.in);
                PrintWriter out = new PrintWriter(System.out);
        ) {
            C2 solution = new C2(in, out);
            solution.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void solve() throws IOException {
        int t = in.nextInt();
        while (t-- > 0) {
            int n = in.nextInt();
            int q = in.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
            }
            Tree tree = new Tree(array);
            out.println(tree.getResult());
            while (q-- > 0) {
                int l = in.nextInt() - 1;
                int r = in.nextInt() - 1;
                if (l != r) {
                    tree.change(l, array[r]);
                    tree.change(r, array[l]);
                    array[r] ^= array[l];
                    array[l] ^= array[r];
                    array[r] ^= array[l];
                }
                out.println(tree.getResult());
            }
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

    private class Tree {
        private final static long INF = -1_000_000_000_000L;
        Node root;

        Tree(int[] array) {
            this.root = build(0, array.length, array);
        }

        public long getResult() {
            return Math.max(root.pp, root.pn);
        }

        public void change(int i, int val) {
            change(root, i, val);
        }

        private Node build(int l, int r, int[] array) {
            Node node = new Node(l, r);
            if (node.isLeaf()) {
                setUpLeaf(node, array[l]);
            } else {
                int mid = (l + r) / 2;
                node.leftSon = build(l, mid, array);
                node.rightSon = build(mid, r, array);
                fixNode(node);
            }
            return node;
        }

        private void change(Node node, int i, long val) {
            if (i < node.lB || i >= node.rB) {
                return;
            }
            if (node.isLeaf()) {
                setUpLeaf(node, val);
            } else {
                change(node.leftSon, i, val);
                change(node.rightSon, i, val);
                fixNode(node);
            }
        }

        private void setUpLeaf(Node node, long val) {
            node.pp = val;
            node.nn = -val;
            node.np = INF;
            node.pn = INF;
        }

        private void fixNode(Node node) {
            if (!node.isLeaf()) {
                Node leftSon = node.leftSon;
                Node rightSon = node.rightSon;
                node.nn = Math.max(
                        Math.max(leftSon.nn, rightSon.nn),
                        Math.max(leftSon.nn + rightSon.pn, leftSon.np + rightSon.nn)
                );
                node.np = Math.max(
                        Math.max(leftSon.np, rightSon.np),
                        Math.max(leftSon.nn + rightSon.pp, leftSon.np + rightSon.np)
                );
                node.pn = Math.max(
                        Math.max(leftSon.pn, rightSon.pn),
                        Math.max(leftSon.pn + rightSon.pn, leftSon.pp + rightSon.nn)
                );
                node.pp = Math.max(
                        Math.max(rightSon.pp, leftSon.pp),
                        Math.max(leftSon.pp + rightSon.np, leftSon.pn + rightSon.pp)
                );
            }
        }

        private class Node {
            long np;
            long pp;
            long nn;
            long pn;
            int lB;
            int rB;
            Node leftSon;
            Node rightSon;

            public Node(int lB, int rB) {
                this.lB = lB;
                this.rB = rB;
            }

            public int getSize() {
                return rB - lB;
            }

            public boolean isLeaf() {
                return getSize() == 1;
            }
        }
    }
}
