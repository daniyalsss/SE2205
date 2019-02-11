import java.io.*;
import java.util.Scanner;

public class Assignment1 {

    private int matrix[][];
    
    public int[][] denseMatrixMult(int[][] A, int[][] B, int size)
    {
        int[][] matrix = new int[size][size];
        // initialization
        if (size == 1)
            matrix[0][0] = A[0][0] * B[0][0];
        else
        {
            int[][] A11 = new int[size/2][size/2];
            int[][] A12 = new int[size/2][size/2];
            int[][] A21 = new int[size/2][size/2];
            int[][] A22 = new int[size/2][size/2];
            int[][] B11 = new int[size/2][size/2];
            int[][] B12 = new int[size/2][size/2];
            int[][] B21 = new int[size/2][size/2];
            int[][] B22 = new int[size/2][size/2];

            // Split both matrices into 4
            split(A, A11, 0 , 0, size/2);
            split(A, A12, 0 , size/2, size/2);
            split(A, A21, size/2, 0, size/2);
            split(A, A22, size/2, size/2, size/2);

            split(B, B11, 0 , 0, size/2);
            split(B, B12, 0 , size/2, size/2);
            split(B, B21, size/2, 0, size/2);
            split(B, B22, size/2, size/2, size/2);

            int [][] M1 = denseMatrixMult(sum(A11, A22,0,0,0,0,size/2), sum(B11, B22,0,0,0,0,size/2), size/2);
            int [][] M2 = denseMatrixMult(sum(A21, A22,0,0,0,0,size/2), B11, size/2);
            int [][] M3 = denseMatrixMult(A11, sub(B12, B22,0,0,0,0,size/2), size/2);
            int [][] M4 = denseMatrixMult(A22, sub(B21, B11,0,0,0,0,size/2), size/2);
            int [][] M5 = denseMatrixMult(sum(A11, A12,0,0,0,0,size/2), B22, size/2);
            int [][] M6 = denseMatrixMult(sub(A21, A11,0,0,0,0,size/2), sum(B11, B12,0,0,0,0,size/2), size/2);
            int [][] M7 = denseMatrixMult(sub(A12, A22,0,0,0,0,size/2), sum(B21, B22,0,0,0,0,size/2), size/2);

            //C11 = M1 + M4 - M5 + M7
            int [][] C11 = sum(sub(sum(M1, M4,0,0,0,0,size/2), M5,0,0,0,0,size/2), M7,0,0,0,0,size/2);
            //C12 = M3 + M5
            int [][] C12 = sum(M3, M5,0,0,0,0,size/2);
            //C21 = M2 + M4
            int [][] C21 = sum(M2, M4,0,0,0,0,size/2);
            //C22 = M1 - M2 + M3 + M6
            int [][] C22 = sum(sub(sum(M1, M3,0,0,0,0,size/2), M2,0,0,0,0,size/2), M6,0,0,0,0,size/2);

            // Join matrices
            join(C11, matrix, 0 , 0, size/2);
            join(C12, matrix, 0 , size/2, size/2);
            join(C21, matrix, size/2, 0, size/2);
            join(C22, matrix, size/2, size/2, size/2);
        }
        return matrix;
    }
    // Split parent matrix into child matrices
    public void split(int[][] P, int[][] C, int x, int y, int size)
    {
        for(int i = 0, j = x; i < size; i++, j++) {
            for (int k = 0, l = y; k < size; k++, l++) {
                C[i][k] = P[j][l];
            }
        }
    }
    // Join child matrices into one big parent matrix
    public void join(int[][] C, int[][] P, int x, int y, int size)
    {
        for(int i = 0, j = x; i < size; i++, j++) {
            for (int k = 0, l = y; k < size; k++, l++) {
                P[j][l] = C[i][k];
            }
        }
    }

    public int[][] sum(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int size) {

        int[][] matrix = new int[size][size];
        // sums columns together then rows
        // double for loop for rows and columns
        for (int i = 0; i < size; i++) {

            int yA = y1, yB = y2;

            for (int j = 0; j < size; j++) {
                matrix[i][j] = A[x1][yA] + B[x2][yB];
                yA++;
                yB++;
            }
            x1++;
            x2++;
        }
        return matrix;
    }

    public int[][] sub(int[][] A, int[][] B, int x1, int y1, int x2, int y2, int size) {

        int[][] matrix = new int[size][size];
        // double for loop for rows and columns
        for (int i = 0; i < size; i++) {

            int yA = y1, yB = y2;

            for (int j = 0; j < size; j++) {
                matrix[i][j] = A[x1][yA] - B[x2][yB];
                yA++;
                yB++;
            }
            x1++;
            x2++;
        }
        return matrix;
    }


    public int[][] initMatrix(int size) {
        return matrix = new int[size][size];
    }

    public void printMatrix(int size, int[][] A) {

        String matrix = "";

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                matrix += A[i][j] + " ";
            }

            System.out.println(matrix);
            matrix = "";
        }
    }

    public int[][] readMatrix(String filename, int size) throws Exception {

        int matrix[][] = new int[size][size];
        int i = 0, j = 0;
        Scanner reader = new Scanner(new File(filename));

        while (reader.hasNext()) {
            //increment row
            if (j == size) {
                j = 0;
                i++;
            }
            matrix[i][j] = reader.nextInt();
            // increment column
            j++;
        }
        return matrix;
    }
}