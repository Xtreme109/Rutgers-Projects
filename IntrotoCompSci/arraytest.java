public class arraytest {


    public static int[] flatten(int[][] a) {
        if (a.length == 0) return null;
        int rows = a.length;
        int columns = a[0].length;


        System.out.println(rows);
        System.out.println(columns);

        int[] tmp = new int[rows*columns];

        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++) {
                tmp[j+columns*i] = a[i][j];
                System.out.println(j+columns*i);
            }
        }

        return tmp;
    }

    public static void main(String[] args) {
        int[][] trust = {
                { 1,2,3,4,5},
                {1,2,3,4,5}
        };

        
        flatten(trust);

    }
}
