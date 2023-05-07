import mpi.*;

import java.util.Arrays;

public class Main {
    private static final int countRows = 2500;
    private static final int countColumn = 2500;

    private static final int ROOT_PROCESS = 0;
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(countRows, countColumn, 0, 10);
        Matrix matrix2 = new Matrix(countRows, countColumn, 0, 10);

        var startTime = System.currentTimeMillis();

        MPI.Init(args);

        int countProcesses = MPI.COMM_WORLD.Size();
        int processID = MPI.COMM_WORLD.Rank();

        int rowsForOneProcess = countRows / countProcesses;
        int extraRows = countRows % countProcesses;

        int[] countsElementsInProcesses = new int[countProcesses];
        for(int i = 0; i < countsElementsInProcesses.length; i++){
            countsElementsInProcesses[i] = rowsForOneProcess * countColumn;
            if(i == countProcesses - 1){
                countsElementsInProcesses[i] += extraRows * countColumn;
            }
        }

        int[] displs = new int[countProcesses];
        displs[0] = 0;
        for(int i = 1; i < displs.length; i++){
            displs[i] = countsElementsInProcesses[i-1] + displs[i-1];
        }

        int[] arrayMatrix1 = matrix1.convertToArray();
        int[] arrayMatrix2 = matrix2.convertToArray();
        int[] arrayResult = new int[countRows * countColumn];

        int countElementsCurrProcess = countsElementsInProcesses[processID];
        int[] arraySubMatrix1 = new int[countElementsCurrProcess];

        MPI.COMM_WORLD.Scatterv(arrayMatrix1, 0, countsElementsInProcesses, displs, MPI.INT,
                arraySubMatrix1, 0, countElementsCurrProcess, MPI.INT, ROOT_PROCESS);
        MPI.COMM_WORLD.Bcast(arrayMatrix2,0, arrayMatrix1.length, MPI.INT,ROOT_PROCESS);


        Matrix subMatrix1 = new Matrix(arraySubMatrix1, countElementsCurrProcess / countColumn, countColumn);
        Matrix matrx2 = new Matrix(arrayMatrix2, countRows, countColumn);
        Matrix partResultMatrix = subMatrix1.multiply(matrx2);

        int[] arrayPartResultMatrix = partResultMatrix.convertToArray();
        MPI.COMM_WORLD.Allgatherv(arrayPartResultMatrix,0, arrayPartResultMatrix.length, MPI.INT,
                arrayResult,0, countsElementsInProcesses, displs, MPI.INT);
        //MPI.COMM_WORLD.Gatherv(arrayPartResultMatrix,0, arrayPartResultMatrix.length, MPI.INT,
        //        arrayResult,0, countsElementsInProcesses, displs, MPI.INT, ROOT_PROCESS);

        if(processID == ROOT_PROCESS){
            Matrix resultMatrix = new Matrix(arrayResult, countRows, countColumn);
            var endTime = System.currentTimeMillis();
            System.out.println("time " + (endTime-startTime));

            //Matrix res = matrix1.multiply(matrix2);
            //System.out.println(res.Equal(resultMatrix));
        }

        MPI.Finalize();
    }
}