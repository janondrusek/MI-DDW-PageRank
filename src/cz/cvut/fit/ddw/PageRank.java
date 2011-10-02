package cz.cvut.fit.ddw;

import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.sparse.SparseVector;

public class PageRank {

    public static final double DAMPING = 0.85;
    public static final int ITERATIONS = 30;
    final SparseVector aVector;
    final Matrix hMatrix;

    public PageRank(Matrix hMatrix, SparseVector aVector) {
        this.hMatrix = hMatrix;
        this.aVector = aVector;
    }

    public SparseVector getPageRank(int iterations) {
        // left: (alpha * pi^(k)T *H) + right: [(alpha * pi(k)T * A + 1 - alpha) * pi(0)]
        long start = System.currentTimeMillis();
        SparseVector pi = getPiVector();
        final SparseVector piZero = pi.copy();

        for (int i = 0; i < iterations; i++) {
            SparseVector piZeroCopy = piZero.copy();
            SparseVector piLeft = pi.copy();

            // right
            piZeroCopy.scale(piLeft.scale(DAMPING).dot(aVector) + 1 - DAMPING);
            // left
            hMatrix.mult(DAMPING, pi, piLeft);

            piLeft.add(piZeroCopy);

            pi = piLeft;
        }

        System.out.println("pageRank in: " + (System.currentTimeMillis() - start));
        return pi;
    }

    private SparseVector getPiVector() {
        SparseVector pi = new SparseVector(hMatrix.numColumns());
        double num = new Double(1) / hMatrix.numColumns();

        for (int i = 0; i < hMatrix.numColumns(); i++) {
            pi.set(i, num);
        }

        return pi;
    }
}
