package UltraChallenge;

/*考え方:
クイーンを1とし、ほかのクイーンを置けない位置を2、置ける位置を0として二次元配列を考える。
１行目から順にクイーンを0の位置に配置し、そのときに次の行に置けるクイーンがなくなってしまうと前の行に戻り、
次の0にクイーンを配置し、また次の行を確認。クイーンが置けるまで次の行にすすめない。
もし、ｎ行目に置いたクイーンのすべてで解が求まらなかった場合は、一行戻り次の0にクイーンを移動する。
つまり、解が求まるまで行を必要な時だけ行ったり戻ったりし、すべてのパターンを調べずに済む

*/
public class Problem1_3 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int queenNumber = 1000;//クイーンの数
        int[][] queen = new int[queenNumber][queenNumber];//クイーンの配置を二次元配列とする
        int queenColumn[] = new int[queenNumber];//各列のクイーンの配置を記憶
        //はじめにqueen配列にすべて0を記憶

        for(int row = 0; row < queenNumber; row++) {
            for(int column = 0; column < queenNumber; column++) {
                queen[row][column] = 0;
            }
            queenColumn[row] = 0;
        }
        //queenColumn[0] = queenNumber/2 - 1;
        for(int n = 0; n < queenNumber/2;n++) {
            queen[n][2*n+1] = 1;
            putQueen(queen,n,queenNumber);
        }
        //  for(int n = queenNumber/2;n < queenNumber - 2;n++){
        //      queen[n][(n-queenNumber/2)*2] = 1;
        //      putQueen(queen,n,queenNumber);
            
        // }
        //display(queen,queenNumber);
        int totalRow = queenNumber/2;//どの行まで進んだか数えるため
            while(totalRow < queenNumber - 1){
  
                //進んだ配列以降を初期化
                for(int row = totalRow; row < queenNumber;row++){
                    for(int column = 0; column < queenNumber; column++) {
                        queen[row][column] = 0;
                    }
                }
                //初期化した後にすでに置かれているクイーンの配置を考慮
                for(int row = 0; row < totalRow; row++) {
                    putQueen(queen,row,queenNumber);
                }


                if(queen[totalRow][queenColumn[totalRow]] == 0) {//今の列が０なら１（クイーン）を配置
                    queen[totalRow][queenColumn[totalRow]] = 1;
                    putQueen(queen,totalRow,queenNumber);
                    totalRow++;
                    //display(queen,queenNumber);
                }else {//０でないなら次の列へ進む
                    while(queen[totalRow][queenColumn[totalRow]] != 0) {
                        queenColumn[totalRow]+=2;
                        if(queenColumn[totalRow] >= queenNumber) break;
                    }
                }
                int zeroCount = 0;
                for(int column = 0; column < queenNumber; column++){//クイーンを配置したときの次の行の０の数をカウント
                    if(queen[totalRow][column] == 0) zeroCount++;
                }
                if(zeroCount == 0){//次の行の０の数が０の場合はクイーンを配置できないため、前の行で配置したクイーンはよくなかったので次の０に配置
                    totalRow--;
                    while(queen[totalRow][queenColumn[totalRow]] != 0) {
                        queenColumn[totalRow]+=2;
                        if(queenColumn[totalRow] >= queenNumber) break;
                    }
                }
                if(queenColumn[totalRow] >= queenNumber) {//前の行ですべての列を調べても解が求まらなかったら、さらに一行戻りクイーンを移動
                    while(queenColumn[totalRow] >= queenNumber) {
                        queenColumn[totalRow] = 0;
                        totalRow--;
                        queenColumn[totalRow]++;
                    }
                }
                //display(queen,queenNumber);
            }
        for(int column = 0; column < queenNumber; column++) {
            if(queen[queenNumber - 1][column] == 0) queen[queenNumber - 1][column] = 1;
            putQueen(queen,queenNumber - 1,queenNumber);
        }
        long endTime = System.currentTimeMillis();

        //display(queen,queenNumber); //駒を表示
        //queenPlace(queen,queenNumber); //駒が左から何番目にいるか表示
        System.out.println("処理時間は " + (endTime - startTime) + " ms");
    }


    public static void putQueen(int[][] queen, int queenRow, int queenNumber) {//配置したクイーンによって置けなくなる場所を登録
        for(int column = 0; column < queenNumber; column++) {
            if(queen[queenRow][column] == 1) {
                for(int row = queenRow + 1; row < queenNumber; row++) {
                    queen[row][column] = 2;
                }
                for(int column2 = 0; column2 < queenNumber;column2++) {
                    if(queen[queenRow][column2] != 1) {
                        queen[queenRow][column2] = 2;
                    }
                }
                int rowRight = queenRow + 1;
                for(int columnRight = column + 1; columnRight < queenNumber; columnRight++) {
                    if(rowRight < queenNumber) queen[rowRight++][columnRight] = 2;
                }
                int rowLeft = queenRow + 1;
                for(int columnLeft = column - 1; columnLeft >= 0 ; columnLeft--) {
                    if(rowLeft < queenNumber) queen[rowLeft++][columnLeft] = 2;
                }
                break;
            }
        }
    }

    public static void display(int[][] queenPlace, int queenNumber){//クイーンの配置を出力するため
        for(int row = 0;row < queenNumber ;row++){
            for(int column = 0; column < queenNumber ; column++) {
                if(queenPlace[row][column] == 1) {
                    System.out.print("凸");
                }else if(queenPlace[row][column] == 2) {
                    System.out.print("ー");
                }else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void queenPlace(int[][] queen, int queenNumber) {
        for(int row = 0;row < queenNumber ;row++){
            int column = 0;
            while(queen[row][column] == 2){
                column++;
            }
            System.out.println(column + 1);
        }
        System.out.println();
    }
}
