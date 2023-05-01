import mpi.MPI;

import static java.lang.System.exit;

public class NonBlockMultiplyMatrices {
    private static final int countRows = 1000;
    private static final int countColumn = 1000;

    private static final int TAG_MASTER = 1;
    private static final int TAG_WORKER = 2;

    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(countRows, countColumn, 0, 10);
        Matrix matrix2 = new Matrix(countRows, countColumn, 0, 10);
        Matrix resultMatrix = new Matrix(countRows, countColumn);
        double startTime, endTime;

        MPI.Init(args);

        int countTasks = MPI.COMM_WORLD.Size();
        int taskID = MPI.COMM_WORLD.Rank();
        //System.out.println("Tasks: "+ countTasks);

        int masterID = 0;
        int countWorkers = countTasks - 1;

        if(countTasks < 2){
            MPI.COMM_WORLD.Abort(1);
            exit(1);
        }

        if(taskID == masterID){
            startTime = MPI.Wtime();
            int rowsForOneWorker = countRows / countWorkers;
            int extraRows = countRows % countWorkers;

            for (int i = 1; i <= countWorkers; i++) {
                int indexStartRow = (i-1) * rowsForOneWorker;
                int indexEndRow = indexStartRow + rowsForOneWorker - 1;
                if(i == countWorkers){
                    indexEndRow += extraRows;
                }

                Matrix subMatrix1 = matrix1.getSubMatrix(indexStartRow, indexEndRow, countColumn);
                byte[] subMatrix1Buff = subMatrix1.convertToByteArray();
                byte[] matrix2Buff = matrix2.convertToByteArray();

                MPI.COMM_WORLD.Isend(new int[]{indexStartRow}, 0, 1, MPI.INT, i, TAG_MASTER);
                MPI.COMM_WORLD.Isend(new int[]{indexEndRow}, 0, 1, MPI.INT, i, TAG_MASTER);
                MPI.COMM_WORLD.Isend(subMatrix1Buff, 0, subMatrix1Buff.length , MPI.BYTE, i, TAG_MASTER);
                MPI.COMM_WORLD.Isend(matrix2Buff, 0, matrix2Buff.length, MPI.BYTE, i, TAG_MASTER);
            }


            for (int i = 1; i <= countWorkers; i++) {
                int[] indexStartRow = new int[1];
                int[] indexEndRow = new int[1];

                var recIndexStart =  MPI.COMM_WORLD.Irecv(indexStartRow,0,1, MPI.INT, i, TAG_WORKER);
                var recIndexEnd =MPI.COMM_WORLD.Irecv(indexEndRow,0,1, MPI.INT, i, TAG_WORKER);
                recIndexStart.Wait();
                recIndexEnd.Wait();

                int countElemsResultBuffer = (indexEndRow[0] - indexStartRow[0] + 1) * countColumn * Integer.BYTES;
                byte[] resultMatrixBuff = new byte[countElemsResultBuffer];

                var recRes = MPI.COMM_WORLD.Irecv(resultMatrixBuff,0, countElemsResultBuffer , MPI.BYTE, i, TAG_WORKER);
                recRes.Wait();

                Matrix subMatrix = new Matrix(resultMatrixBuff, indexEndRow[0] - indexStartRow[0] + 1, countColumn);
                resultMatrix.putSubMatrix(subMatrix, indexStartRow[0], indexEndRow[0], countColumn);
            }
            endTime = MPI.Wtime();
            //resultMatrix.print();
            System.out.println(endTime-startTime + " seconds");
        }
        else {
            int[] indexStartRow = new int[1];
            int[] indexEndRow = new int[1];
            var recIndexStart = MPI.COMM_WORLD.Irecv(indexStartRow,0,1, MPI.INT, 0, TAG_MASTER);
            var recIndexEnd = MPI.COMM_WORLD.Irecv(indexEndRow,0,1, MPI.INT, 0, TAG_MASTER);
            recIndexStart.Wait();
            recIndexEnd.Wait();

            int sizeSubMatrix1Buff = (indexEndRow[0] - indexStartRow[0] + 1) * countColumn * Integer.BYTES;
            int sizeMatrix2Buff = countRows * countColumn * Integer.BYTES;
            byte[] subMatrix1Buff = new byte[sizeSubMatrix1Buff];
            byte[] matrix2Buff = new byte[sizeMatrix2Buff];
            var recSubMatrix1 = MPI.COMM_WORLD.Irecv(subMatrix1Buff,0, sizeSubMatrix1Buff, MPI.BYTE,0, TAG_MASTER);
            var recMatrix2 = MPI.COMM_WORLD.Irecv(matrix2Buff,0,sizeMatrix2Buff, MPI.BYTE,0, TAG_MASTER);
            recSubMatrix1.Wait();
            recMatrix2.Wait();

            Matrix subMatrix1 = new Matrix(subMatrix1Buff, indexEndRow[0] - indexStartRow[0] + 1, countColumn);
            Matrix Matrx2 = new Matrix(matrix2Buff, countRows, countColumn);
            Matrix resultMatrx = subMatrix1.multiply(Matrx2);

            byte[] resultMatrixBuff = resultMatrx.convertToByteArray();

            MPI.COMM_WORLD.Isend(indexStartRow,0, 1, MPI.INT, 0, TAG_WORKER);
            MPI.COMM_WORLD.Isend(indexEndRow,0, 1, MPI.INT, 0, TAG_WORKER);
            MPI.COMM_WORLD.Isend(resultMatrixBuff,0, resultMatrixBuff.length, MPI.BYTE, 0, TAG_WORKER);
        }

        MPI.Finalize();
    }
}
