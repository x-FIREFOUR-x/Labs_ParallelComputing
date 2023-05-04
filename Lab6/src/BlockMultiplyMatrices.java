import mpi.*;

import static java.lang.System.exit;

public class BlockMultiplyMatrices {
    private static final int countRows = 1000;
    private static final int countColumn = 1000;

    private static final int TAG_MASTER = 1;
    private static final int TAG_WORKER = 2;

    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(countRows, countColumn, 0, 10);
        Matrix matrix2 = new Matrix(countRows, countColumn, 0, 10);
        Matrix resultMatrix = new Matrix(countRows, countColumn);

        var startTime = System.currentTimeMillis();

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

                MPI.COMM_WORLD.Send(new int[]{indexStartRow},0, 1, MPI.INT, i, TAG_MASTER);
                MPI.COMM_WORLD.Send(new int[]{indexEndRow},0, 1, MPI.INT, i, TAG_MASTER);
                MPI.COMM_WORLD.Send(subMatrix1Buff,0, subMatrix1Buff.length , MPI.BYTE, i, TAG_MASTER);
                MPI.COMM_WORLD.Send(matrix2Buff,0, matrix2Buff.length, MPI.BYTE, i, TAG_MASTER);
            }


            for (int i = 1; i <= countWorkers; i++) {
                int[] indexStartRow = new int[1];
                int[] indexEndRow = new int[1];
                MPI.COMM_WORLD.Recv(indexStartRow,0,1, MPI.INT, i, TAG_WORKER);
                MPI.COMM_WORLD.Recv(indexEndRow,0,1, MPI.INT, i, TAG_WORKER);

                int countElemsResultBuffer = (indexEndRow[0]-indexStartRow[0] + 1) * countColumn * Integer.BYTES;
                byte[] resultMatrixBuff = new byte[countElemsResultBuffer];
                MPI.COMM_WORLD.Recv(resultMatrixBuff,0,
                        countElemsResultBuffer , MPI.BYTE, i, TAG_WORKER);
                Matrix subMatrix = new Matrix(resultMatrixBuff, indexEndRow[0] - indexStartRow[0] + 1, countColumn);

                resultMatrix.putSubMatrix(subMatrix, indexStartRow[0], indexEndRow[0], countColumn);
            }
            //resultMatrix.print();
            var endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
        else {
            int[] indexStartRow = new int[1];
            int[] indexEndRow = new int[1];
            MPI.COMM_WORLD.Recv(indexStartRow,0,1, MPI.INT, 0, TAG_MASTER);
            MPI.COMM_WORLD.Recv(indexEndRow,0,1, MPI.INT, 0, TAG_MASTER);

            int sizeSubMatrix1Buff = (indexEndRow[0] - indexStartRow[0] + 1) * countColumn * Integer.BYTES;
            int sizeMatrix2Buff = countRows * countColumn * Integer.BYTES;
            byte[] subMatrix1Buff = new byte[sizeSubMatrix1Buff];
            byte[] matrix2Buff = new byte[sizeMatrix2Buff];
            MPI.COMM_WORLD.Recv(subMatrix1Buff,0, sizeSubMatrix1Buff, MPI.BYTE,0, TAG_MASTER);
            MPI.COMM_WORLD.Recv(matrix2Buff,0,sizeMatrix2Buff, MPI.BYTE,0, TAG_MASTER);

            Matrix subMatrix1 = new Matrix(subMatrix1Buff, indexEndRow[0] - indexStartRow[0] + 1, countColumn);
            Matrix Matrx2 = new Matrix(matrix2Buff, countRows, countColumn);
            Matrix resultMatrx = subMatrix1.multiply(Matrx2);

            byte[] resultMatrixBuff = resultMatrx.convertToByteArray();

            MPI.COMM_WORLD.Send(indexStartRow,0, 1, MPI.INT, 0, TAG_WORKER);
            MPI.COMM_WORLD.Send(indexEndRow,0, 1, MPI.INT, 0, TAG_WORKER);
            MPI.COMM_WORLD.Send(resultMatrixBuff,0, resultMatrixBuff.length, MPI.BYTE, 0, TAG_WORKER);
        }

        MPI.Finalize();
    }
}