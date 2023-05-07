import mpi.MPI;

public class CollectivePassingMultiplyMatrices {

    private static final int ROOT_PROCESS = 0;

    public static void multiply(String[] args, Matrix matrix1, Matrix matrix2) {
        int count_rows = matrix1.getCountRows();
        int count_columns = matrix1.getCountColumns();

        MPI.Init(args);

        int countProcesses = MPI.COMM_WORLD.Size();
        int processID = MPI.COMM_WORLD.Rank();

        int rowsForOneProcess = count_rows / countProcesses;
        int extraRows = count_rows % countProcesses;

        int[] countsElementsInProcesses = new int[countProcesses];
        for(int i = 0; i < countsElementsInProcesses.length; i++){
            countsElementsInProcesses[i] = rowsForOneProcess * count_columns;
            if(i == countProcesses - 1){
                countsElementsInProcesses[i] += extraRows * count_columns;
            }
        }

        int[] displs = new int[countProcesses];
        displs[0] = 0;
        for(int i = 1; i < displs.length; i++){
            displs[i] = countsElementsInProcesses[i-1] + displs[i-1];
        }

        int[] arrayMatrix1 = matrix1.convertToArray();
        int[] arrayMatrix2 = matrix2.convertToArray();
        int[] arrayResult = new int[count_rows * count_columns];

        int countElementsCurrProcess = countsElementsInProcesses[processID];
        int[] arraySubMatrix1 = new int[countElementsCurrProcess * count_columns];

        MPI.COMM_WORLD.Scatterv(arrayMatrix1, 0, countsElementsInProcesses, displs, MPI.INT,
                arraySubMatrix1, 0, countElementsCurrProcess, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(arrayMatrix2,0, arrayMatrix1.length, MPI.INT,0);


        Matrix subMatrix1 = new Matrix(arraySubMatrix1, countElementsCurrProcess / count_columns, count_columns);
        Matrix matrx2 = new Matrix(arrayMatrix2, count_rows, count_columns);
        Matrix partResultMatrix = subMatrix1.multiply(matrx2);

        int[] arrayPartResultMatrix = partResultMatrix.convertToArray();
        //MPI.COMM_WORLD.Allgatherv(arrayPartResultMatrix,0, arrayPartResultMatrix.length, MPI.INT,
        //        arrayResult,0, countsElementsInProcesses, displs, MPI.INT);
        //MPI.COMM_WORLD.Gatherv(arrayPartResultMatrix,0, arrayPartResultMatrix.length, MPI.INT,
        //        arrayResult,0, countsElementsInProcesses, displs, MPI.INT, ROOT_PROCESS);



        if(processID == ROOT_PROCESS){
            Matrix resultMatrix = new Matrix(arrayResult, count_rows, count_columns);
            //resultMatrix.print();
            //Matrix res = matrix1.multiply(matrix2);
            //System.out.println(res.Equal(resultMatrix));
        }

        MPI.Finalize();
    }
}
