package cz.cvut.fit.ddw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.sparse.SparseVector;

public class Main {

    public static void main(String[] args) {
        int iterations = 30;
        long start = System.currentTimeMillis();
        BufferedReader br = null;
        MatrixReader mr = null;

        try {
            br = new BufferedReader(new FileReader("/home/vega/Documents/School/CVUT/FIT/2.druhy_semester/DDW/test3.txt"));
            mr = new MatrixReader(br);
            System.out.println("matrix read in: " + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            System.exit(1);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        PageRank pr = new PageRank(mr.getHMatrix(), mr.getAVector());
        print(pr.getPageRank(iterations));

        System.out.println("total time: " + (System.currentTimeMillis() - start));
    }

    public static void print(Matrix matrix) {

        for (int i = 0; i < matrix.numRows(); i++) {
            for (int j = 0; j < matrix.numColumns(); j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.print("\n");
        }
    }

    public static void print(SparseVector sv) {
        BufferedWriter bo = null;
        try {
            bo = new BufferedWriter(new FileWriter("/tmp/matrix.out"));
            for (int i = 0; i < sv.size(); i++) {
                System.out.print(sv.get(i) + " ");
                bo.write(sv.get(i) + " ");
            }
            bo.write("\n sum: " + sum(sv) + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bo.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static double sum(SparseVector sv) {
        double sum = 0;
        for (double col : sv.getData()) {
            sum += col;
        }
        return sum;
    }
}
