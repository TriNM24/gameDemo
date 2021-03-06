package com.example.minigames.gameBasic.caroGame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.example.minigames.R;
import com.example.minigames.firebase.FirebaseHelper;
import com.example.minigames.gameBasic.caroGame.CaroGameActivity;
import com.example.minigames.ultis.Utils;

public class Board_ComVsHum extends View {
    private int m = 18, n = 18; // Khởi tạo số ô cờ
    public Boolean firstPlayerX = false;// X đánh
    private static int empty_cell = 0;
    private static int x_cell = 1;
    private static int o_cell = -1;
    int maxi = 0;
    int maxj = 0;

    private int grid_width = 25;
    private int grid_height = 25;
    private int grid_size = 40;
    public int playerTurn = 0;// Thằng đầu tiên đánh

    int mX, mY;// Tọa độ X,Y khi chạm tay
    // Cài đặt bắt sự kiện cho việc cài đặt bên Obj_Setting


    // Cấp phát động vùng nhớ cho amrng 2 chiều
    private int[][] arr = new int[m][n];

    private boolean isFirstInit = true;

    //variable to save point for game
    long mStartTime = 0;

    int mSizeLast = 0;

    public Board_ComVsHum(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board_ComVsHum(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics displayMetric = new DisplayMetrics();// Hàm này là hàm hiển thị số liệu của thiết bị
        String cell_char = new String();
        System.err.println("call to Chessboard onDraw MultiBoardChess");
        super.onDraw(canvas);

        // Lấy kích thước của màn hình
        System.err.println("get window size MultiBoardChess");
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetric);
        System.err.println("done get window size MultiBoardChess");
        int width = displayMetric.widthPixels;// Hiển thị số liệu độ rộng trên màn hình
        int height = displayMetric.heightPixels;// Hiển thị số liệu độ cao màn hình

        // Tính kích thước ô cờ
        // Kích thước thiết bị
        // Chia cho số ô cờ để tính được kích thước ô cờ
        // số ô cờ là m
        grid_size = height / n;

        System.out.println("Tính: " + "m= " + m + "; n= " + n);
        m = width / grid_size;
        mSizeLast = width % grid_size;
        n = height / grid_size;
        System.out.println("Giá trị sau vẽ : " + "m= " + m + "; n= " + n + "grid_sze" + grid_size);
        grid_width = m;
        grid_height = n;
        System.out.println("Giá trị sau vẽ : " + "grid_height= " + grid_height + "; grid_width= " + grid_width + ";grid_sze" + grid_size);

        if (isFirstInit) {
            System.err.println("onCreate Obj_MultiBoardChess");
            arr = new int[m][n];
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[0].length; j++) {
                    // 3 Xóa trắng các ô cờ
                    this.arr[i][j] = empty_cell;
                }
            }
            isFirstInit = false;
        }

        System.err.println("new paint MultiBoardChess");
        Paint paint = new Paint();
        paint.setStrokeWidth(2); // Độ đậm nhạt của đường kẻ và quân đánh
        for (int i = 0; i < grid_width; i++) {
            for (int j = 0; j < grid_height; j++) {
                if (arr[i][j] == empty_cell) {
                    paint.setColor(Color.WHITE);// set màu của ô cờ trống
                    cell_char = " ";
                } else if (arr[i][j] == x_cell) {
                    paint.setColor(Color.RED);// set màu của ô cờ x
                    cell_char = "X";
                } else if (arr[i][j] == o_cell) {
                    paint.setColor(Color.BLUE);// set màu của ô cờ O
                    cell_char = "O";
                }
                //Fill rectangle
                paint.setTextSize(grid_size / 2);
                paint.setStyle(Paint.Style.FILL);
                int xright = (i + 1) * grid_size;
                if ((i + 1) == grid_width) {
                    xright += mSizeLast;
                }
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, xright, (j + 1) * grid_size), paint);

                // Border rectangle
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, xright, (j + 1) * grid_size), paint);
                Rect bounds = new Rect();
                paint.getTextBounds("X", 0, 1, bounds);
                canvas.drawText(cell_char, 0, 1, i * grid_size + (grid_size - bounds.width()) / 2, j * grid_size + (bounds.height() + (grid_size - bounds.height()) / 2), paint);
            }
        }
    }

    private void clear() {
        arr = new int[m][n];
        System.err.println("onCreate boardchess");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                // 3 Xóa trắng các ô cờ
                this.arr[i][j] = empty_cell;
            }
        }
        invalidate();
    }

    private void showConfirmRetake() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Caro game");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Wrong move, please move again!");
        builder.setNegativeButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    public void showWinDialog(boolean humWin) {
        if (humWin) {

            long time = Utils.millisecondToSecond(System.currentTimeMillis() - mStartTime);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Caro game");
            builder.setIcon(R.drawable.congra);
            builder.setMessage(this.getContext().getString(R.string.alert_win, time));
            builder.setPositiveButton("OK", (dialog, which) -> {
                final AlertDialog.Builder newbuilder = new AlertDialog.Builder(getContext());
                newbuilder.setTitle("New game");
                newbuilder.setMessage("Do you want start new game?");
                newbuilder.setIcon(R.drawable.newgame);

                newbuilder.setPositiveButton("Yes", (dialog1, which1) -> clear());
                newbuilder.setNegativeButton("No", (dialog12, id) -> {
                    dialog12.cancel();
                    ((CaroGameActivity)getContext()).onSupportNavigateUp();
                });
                newbuilder.create().show();
                dialog.cancel();
            });
            builder.create().show();
            //update point for game 1
            FirebaseHelper.getInstance().getUserDao().updateGamePoint(1, time);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Caro game");
            builder.setIcon(R.drawable.congra);
            //builder.setIcon(R.drawable.images);
            builder.setMessage("The winner is Computer!");
            builder.setPositiveButton("OK", (dialog, which) -> {
                final AlertDialog.Builder newbuilder = new AlertDialog.Builder(getContext());
                newbuilder.setTitle("New game");
                newbuilder.setMessage("Do you want start new game?");
                newbuilder.setIcon(R.drawable.newgame);

                newbuilder.setPositiveButton("Yes", (dialog1, which1) -> clear());
                newbuilder.setNegativeButton("No", (dialog12, id) -> {
                    dialog12.cancel();
                    ((CaroGameActivity)getContext()).onSupportNavigateUp();
                });
                newbuilder.create().show();
                dialog.cancel();
            });
            builder.create().show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Gọi thằng đi trước từ Obj_Setting
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mStartTime == 0) {
                    mStartTime = System.currentTimeMillis();
                }

                // tọa độ X của điểm chạm
                mX = (int) event.getX();// Tọa độ khik2 đánh ở X
                // tọa độ Y của điểm chạm
                mY = (int) event.getY();// Tọa độ khi đánh ở Y
                System.out.println("Action down " + mX + "  " + mY + " " + (int) mX / grid_size + " " + (int) mY / grid_size);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////(playTurn ==0)///////////////////////////////
                // Lượt chơi của Human
                if (playerTurn == 0) {// Lượt đi của người đầu tiên
                    System.out.println("Playturn = 0" + " Người đánh");
                    if (this.arr[(int) mX / grid_size][(int) mY / grid_size] == empty_cell) {
                        // Lượt đi của thằng Human nếu đánh quân "X"
                        if (firstPlayerX == true) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = x_cell; // đổi trạng thái ô cờ
                        }
                        // Lượt đi của thằng Human nếu đánh quân "O"
                        if (firstPlayerX == false) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = o_cell; // đổi trạng thái ô cờ
                        }
//                        this.invalidate();// Hàm này để vẽ lại quân cờ đã chọn
                        //System.out.println("Vẽ lại bàn cờ khi thằng Human đánh");
                        // Chuyển lượt đi cho Computer
                        playerTurn = 1;
                        int hanghuman = mX / grid_size;
                        int cothuman = mY / grid_size;
                        int demhuman = 1;
                        int index = 1;
                        while (hanghuman + index < m && arr[hanghuman][cothuman] == arr[hanghuman + index][cothuman]) {
                            demhuman++;
                            index++;
                        }
                        index = 1; // reset chỉ số
                        while (hanghuman - index > 0 && arr[hanghuman][cothuman] == arr[hanghuman - index][cothuman]) {
                            demhuman++;
                            index++;
                        }
                        if (demhuman >= 5) {
                            showWinDialog(true);
                        }

                        demhuman = 1;
                        index = 1;
                        while (cothuman + index < n && arr[hanghuman][cothuman] == arr[hanghuman][cothuman + index]) {
                            demhuman++;
                            index++;
                        }
                        index = 1; // reset chỉ số
                        while (cothuman - index > 0 && arr[hanghuman][cothuman] == arr[hanghuman][cothuman - index]) {
                            demhuman++;
                            index++;
                        }
                        if (demhuman >= 5) {
                            showWinDialog(true);
                        }

                        demhuman = 1;
                        index = 1;
                        while (hanghuman + index < m && cothuman + index < n && arr[hanghuman][cothuman] == arr[hanghuman + index][cothuman + index]) {
                            demhuman++;
                            index++;
                        }
                        index = 1;
                        while (cothuman - index > 0 && hanghuman - index > 0 && arr[hanghuman][cothuman] == arr[hanghuman - index][cothuman - index]) {
                            demhuman++;
                            index++;
                        }
                        if (demhuman >= 5) {
                            showWinDialog(true);
                        }

                        demhuman = 1;
                        index = 1;
                        while (hanghuman + index < m && cothuman - index > 0 && arr[hanghuman][cothuman] == arr[hanghuman + index][cothuman - index]) {
                            demhuman++;
                            index++;
                        }
                        index = 1;
                        while (cothuman + index < n && hanghuman - index > 0 && arr[hanghuman][cothuman] == arr[hanghuman - index][cothuman + index]) {
                            demhuman++;
                            index++;
                        }
                        if (demhuman >= 5) {
                            showWinDialog(true);
                        }

                        this.invalidate();// Hàm này để vẽ lại quân cờ đã chọn

                        if (demhuman >= 5) {
                            showWinDialog(true);
                        }
                    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////playTurn==1/////////////////////////////////
                    // Lượt đi của Computer
                    // playTurn != 0 là thằng máy đánh
                    if (playerTurn == 1) {
                        System.out.println("Playturn = 1" + " Máy đánh");
                        // Khởi tạo mảng điểm chặn
                        int ArrayPointBlock[][] = new int[m][n];
                        //System.out.println("Khởi tạo điểm chặn");
                        // Duyệt mảng từ đầu đến cuối
                        for (int i = 0; i < arr.length; i++) {
                            for (int j = 0; j < arr[0].length; j++) {
                                int hang = (int) i;
                                int cot = (int) j;
                                int index = 1;
                                int dem_x_cell = 1;
                                int dem_o_cell = 1;

                                // Tọa độ computer x_cell đánh arr[hang][cot] có thể viết arr[(int) i][(int) j]

                                if (arr[hang][cot] == empty_cell) {// Kiểm tra ô cờ trống
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////// firstPlayerX == false////////////////
                                    // Máy chọn o_cell
                                    if (firstPlayerX == false) {
                                        // Máy đánh OOOOOOOOO_cell, người đánh XXXXXXXXXXX_cell(người đánh x_cell ở trên)

                                        dem_x_cell = 1;
                                        index = 1;
                                        // Kiểm tra hàng ngang
                                        while (hang + index < m && arr[hang + index][cot] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (hang - index > 0 && arr[hang - index][cot] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }

                                        switch (dem_x_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 10)
                                                    ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 8)
                                                    ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 6)
                                                    ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 4)
                                                    ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra cột dọc
                                        dem_x_cell = 1;
                                        index = 1;
                                        while (cot + index < n && arr[hang][cot + index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot - index > 0 && arr[hang][cot - index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 10)
                                                    ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 8)
                                                    ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 6)
                                                    ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 4)
                                                    ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra chéo trái
                                        dem_x_cell = 1;
                                        index = 1;
                                        while (hang + index < m && cot + index < n && arr[hang + index][cot + index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot - index > 0 && hang - index > 0 && arr[hang - index][cot - index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 10)
                                                    ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 8)
                                                    ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 6)
                                                    ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 4)
                                                    ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra chéo phải
                                        dem_x_cell = 1;
                                        index = 1;
                                        while (hang + index < m && cot - index > 0 && arr[hang + index][cot - index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot + index < n && hang - index > 0 && arr[hang - index][cot + index] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 10)
                                                    ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 8)
                                                    ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 6)
                                                    ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 4)
                                                    ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

//                                        // Thằng người đánh x_cell so sánh vs o_cell của thằng máy đánh
                                        // Kiểm tra hàng ngang
                                        dem_o_cell = 1;
                                        index = 1;
                                        while (hang + index < m && arr[hang + index][cot] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (hang - index > 0 && arr[hang - index][cot] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 9)
                                                    ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 7)
                                                    ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 1)
                                                    ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        // Kiểm tra cột dọc
                                        dem_o_cell = 1;
                                        index = 1;
                                        while (cot + index < n && arr[hang][cot + index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot - index > 0 && arr[hang][cot - index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 9)
                                                    ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 7)
                                                    ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 1)
                                                    ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        dem_o_cell = 1;
                                        index = 1;
                                        while (hang + index < m && cot + index < n && arr[hang + index][cot + index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot - index > 0 && hang - index > 0 && arr[hang - index][cot - index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 9)
                                                    ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 7)
                                                    ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 1)
                                                    ArrayPointBlock[i][j] = 1;
                                                break;
                                        }

                                        dem_o_cell = 1;
                                        index = 1;
                                        while (hang + index < m && cot - index > 0 && arr[hang + index][cot - index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (cot + index < n && hang - index > 0 && arr[hang - index][cot + index] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }

                                        // Mảng điểm được cho khi thằng máy chặn thằng người
                                        switch (dem_o_cell) {
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 9)
                                                    ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 7)
                                                    ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 1)
                                                    ArrayPointBlock[i][j] = 1;
                                                break;
                                        }
                                    }
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

                                    // Lượt đi của Computer nếu đánh quân "X", người chọn o_cell
                                    if (firstPlayerX == true) {
                                        dem_o_cell = 1;
                                        index = 1;
                                        // Kiểm tra hàng ngang
                                        while (hang + index < m && arr[hang + index][cot] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (hang - index > 0 && arr[hang - index][cot] == o_cell) {
                                            dem_o_cell++;
                                            index++;
                                        }
                                        switch (dem_o_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 10)
                                                    ArrayPointBlock[i][j] = 10;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 8)
                                                    ArrayPointBlock[i][j] = 8;
                                                break;
                                            case 3:
                                                if (ArrayPointBlock[i][j] < 6)
                                                    ArrayPointBlock[i][j] = 6;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 4)
                                                    ArrayPointBlock[i][j] = 4;
                                                break;
                                        }

                                        // Kiểm tra hàng ngang
                                        dem_x_cell = 1;
                                        index = 1;
                                        while (hang + index < m && arr[hang + index][cot] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        index = 1;
                                        while (hang - index > 0 && arr[hang - index][cot] == x_cell) {
                                            dem_x_cell++;
                                            index++;
                                        }
                                        switch (dem_x_cell) {
                                            case 5:
                                                if (ArrayPointBlock[i][j] < 9)
                                                    ArrayPointBlock[i][j] = 9;
                                                break;
                                            case 4:
                                                if (ArrayPointBlock[i][j] < 7)
                                                    ArrayPointBlock[i][j] = 7;
                                                break;
                                            case 2:
                                                if (ArrayPointBlock[i][j] < 1)
                                                    ArrayPointBlock[i][j] = 1;
                                                break;
                                        }
                                    }
//                                    System.out.println("hang= " + hang + ", cot= " + cot + ", point " + ArrayPointBlock[hang][cot]
//                                            + ", dem_o_cell ="+ dem_o_cell+ ", dem_x_cell ="+ dem_x_cell);

                                    this.invalidate();
                                }

                            }
                        }
                        for (int i = 0; i < ArrayPointBlock.length; i++) {
                            for (int j = 0; j < ArrayPointBlock[0].length; j++) {
                                if (ArrayPointBlock[i][j] > ArrayPointBlock[maxi][maxj]) {
                                    maxi = i;
                                    maxj = j;
                                    ArrayPointBlock[i][j] = ArrayPointBlock[maxi][maxj];
                                }
                            }
                        }
                        int hangcom = maxi;
                        int cotcom = maxj;
                        int dem_o_cell = 1;
                        int dem_x_cell = 1;
                        int index = 1;
                        if (playerTurn == 0) {
                            if (firstPlayerX == true) {
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                            }
                            if (firstPlayerX == false) {
                                this.arr[(int) maxi][(int) maxj] = x_cell;
                            }
                        } else if (playerTurn == 1) {
                            if (firstPlayerX == false) {
                                dem_x_cell = 1;
                                index = 1;
                                // Kiểm tra hàng ngang
                                while (hangcom + index < m && arr[hangcom + index][cotcom] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                index = 1;
                                while (hangcom - index > 0 && arr[hangcom - index][cotcom] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = x_cell;

                                if (dem_x_cell >= 5) {
                                    showWinDialog(false);
                                }

                                // Kiểm tra cột dọc
                                dem_x_cell = 1;
                                index = 1;
                                while (cotcom + index < n && arr[hangcom][cotcom + index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom - index > 0 && arr[hangcom][cotcom - index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                if (dem_x_cell >= 5) {
                                    showWinDialog(false);
                                }


                                // Kiểm tra chéo trái
                                dem_x_cell = 1;
                                index = 1;
                                while (hangcom + index < m && cotcom + index < n && arr[hangcom + index][cotcom + index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom - index > 0 && hangcom - index > 0 && arr[hangcom - index][cotcom - index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = x_cell;
                                if (dem_x_cell >= 5) {
                                    showWinDialog(false);
                                }
                                // Kiểm tra chéo phải
                                dem_x_cell = 1;
                                index = 1;
                                while (hangcom + index < m && cotcom - index > 0 && arr[hangcom + index][cotcom - index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom + index < n && hangcom - index > 0 && arr[hangcom - index][cotcom + index] == x_cell) {
                                    dem_x_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = x_cell;

                                if (dem_x_cell >= 5) {
                                    showWinDialog(false);
                                }
                            }


                            if (firstPlayerX == true) {
                                dem_o_cell = 1;
                                index = 1;
                                // Kiểm tra hàng ngang
                                while (hangcom + index < m && arr[hangcom + index][cotcom] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                index = 1;
                                while (hangcom - index > 0 && arr[hangcom - index][cotcom] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >= 5) {
                                    showWinDialog(false);
                                }
                                // Kiểm tra cột dọc
                                dem_o_cell = 1;
                                index = 1;
                                while (cotcom + index < n && arr[hangcom][cotcom + index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom - index > 0 && arr[hangcom][cotcom - index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >= 5) {
                                    showWinDialog(false);
                                }
                                // Kiểm tra chéo trái
                                dem_o_cell = 1;
                                index = 1;
                                while (hangcom + index < m && cotcom + index < n && arr[hangcom + index][cotcom + index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom - index > 0 && hangcom - index > 0 && arr[hangcom - index][cotcom - index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = o_cell;
                                if (dem_o_cell >= 5) {
                                    showWinDialog(false);
                                }
                                // Kiểm tra chéo phải
                                dem_o_cell = 1;
                                index = 1;
                                while (hangcom + index < m && cotcom - index > 0 && arr[hangcom + index][cotcom - index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                index = 1;
                                while (cotcom + index < n && hangcom - index > 0 && arr[hangcom - index][cotcom + index] == o_cell) {
                                    dem_o_cell++;
                                    index++;
                                }
                                this.arr[(int) maxi][(int) maxj] = o_cell;

                                if (dem_o_cell >= 5) {
                                    showWinDialog(false);
                                }
                            }
                        }
                        this.invalidate();

                        // Chuyển nước đi cho Human đánh
                        playerTurn = 0;
                        System.out.println(" Chuyển cho thằng Human đánh");
                    } else {
                        showConfirmRetake();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("Action move");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("Action up");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("Action cancel");
                break;
            case MotionEvent.ACTION_OUTSIDE:
                System.out.println("Action outside");
                break;
        }
        return super.onTouchEvent(event);
    }
}
