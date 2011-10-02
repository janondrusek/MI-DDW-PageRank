package cz.cvut.fit.ddw;

import java.io.BufferedReader;
import java.io.IOException;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.sparse.FlexCompColMatrix;
import no.uib.cipr.matrix.sparse.SparseVector;

public class MatrixReader {

    FlexCompColMatrix hMatrix;
    BufferedReader br;
    SparseVector aVector;

    public MatrixReader(BufferedReader br) throws IOException {
        this.br = br;
        loadHMatrix();
    }

    private void loadHMatrix() throws IOException {
        int size = readSize();
        aVector = new SparseVector(size);
        hMatrix = new FlexCompColMatrix(size, size);
        fillHMatrix();
    }

    private int readSize() throws IOException {
        return Integer.parseInt(br.readLine());
    }

    private void fillHMatrix() throws IOException {
        int row = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] patterns = line.split(" ");

            if (isNotEmptyLine(patterns)) {
                fillHMatrixRow(row, patterns);
            } else {
                addEmptyRow(row);
            }
            row++;
        }
    }

    private void fillHMatrixRow(int row, String[] patterns) {
        RowContainer rc = new RowContainer(patterns);
        for (Integer key : rc.getLinks().keySet()) {
            hMatrix.add(key, row, (double) rc.getLinks().get(key) / rc.getRowSum());
        }
    }

    private boolean isNotEmptyLine(String[] patterns) {
        return patterns.length > 0 && patterns[0].length() >= 3;
    }

    private void addEmptyRow(int row) {
        aVector.set(row, 1);
    }

    public Matrix getHMatrix() {
        return hMatrix;
    }

    public SparseVector getAVector() {
        return aVector;
    }
}
