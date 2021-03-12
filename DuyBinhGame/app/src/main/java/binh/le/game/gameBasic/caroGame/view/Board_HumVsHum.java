package binh.le.game.gameBasic.caroGame.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import binh.le.game.R;

public class Board_HumVsHum extends View {
    private int m = 40, n = 40; // Khởi tạo số ô cờ

    public Boolean firstPlayerX = false;

    private static int empty_cell = 0;
    private static int x_cell = 1;
    private static int o_cell = -1;

    private int grid_width = 25;
    private int grid_height = 25;
    private int grid_size = 40;
    public int playerTurn = 0;

    int mX, mY;// Tọa độ X,Y khi chạm tay

    // Cài đặt bắt sự kiện cho việc cài đặt bên Obj_Setting

    // Cấp phát động vùng nhớ cho amrng 2 chiều
    private int[][] arr = new int[m][n];

    private boolean isFirstInit = true;

    private void clear() {
        arr = new int[m][n];
        System.err.println("onCreate boardchess");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                // 3 Xóa trắng các ô cờ
                this.arr[i][j] = empty_cell;
            }
        }
    }

    public Board_HumVsHum(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Board_HumVsHum(Context context) {
        super(context);
    }

    // Vẽ dùng hàm onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics displayMetric = new DisplayMetrics();
        String cell_char = new String();

        System.err.println("call to Chessboard onDraw");
        super.onDraw(canvas);
        // Lấy kích thước của màn hình
        System.err.println("get window size");


        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetric);
        System.err.println("done get window size");
        int width = displayMetric.widthPixels;// Độ rộng màn hình
        int height = displayMetric.heightPixels;// Độ cao màn hình

        // Màn hình máy to
        if (height / m < width / n) {
            grid_size = width / n;
        }
        // Màn hình máy nhỏ
        else if (width / n > height / m) {
            grid_size = height / m;

        }
        System.out.println("Tính: " + "m= " + m + "; n= " + n);
        n = width / (width / n);
        m = height / (grid_size);
        System.out.println("Giá trị sau vẽ : " + "m= " + m + "; n= " + n + "grid_sze" + grid_size);
        grid_width = m;
        grid_height = n;
        System.out.println("Giá trị sau vẽ : " + "grid_height= " + grid_height + "; grid_width= " + grid_width + ";grid_sze" + grid_size);

        if (isFirstInit) {
            clear();
            isFirstInit = false;
        }

        System.err.println("new paint");
        Paint paint = new Paint();
        paint.setStrokeWidth(2); // Độ đậm nhạt của đường kẻ và quân đánh
        for (int i = 0; i < grid_width; i++) {
            for (int j = 0; j < grid_height; j++) {
                System.err.println("draw one cell of the board " + i + " " + j + " " + grid_height + " " + grid_width);
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
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, (i + 1) * grid_size, (j + 1) * grid_size), paint);

                // Border rectangle
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK);
                canvas.drawRect(new Rect(i * grid_size, j * grid_size, (i + 1) * grid_size, (j + 1) * grid_size), paint);
                Rect bounds = new Rect();
                paint.getTextBounds("X", 0, 1, bounds);
                canvas.drawText(cell_char, 0, 1, i * grid_size + (grid_size - bounds.width()) / 2,
                        j * grid_size + (bounds.height() + (grid_size - bounds.height()) / 2), paint);
            }
        }
    }


    // Đi 1 nước
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // tọa độ X của điểm chạm
                mX = (int) event.getX();// Tọa độ khi đánh ở X
                // tọa độ Y của điểm chạm
                mY = (int) event.getY();// Tọa độ khi đánh ở Y
                System.out.println("Action down " + mX + "  " + mY + " " + (int) mX / grid_size + " " + (int) mY / grid_size);

                // mX/grid_size = vị trí ô cờ i; mY/grid_size = vị trí ô cờ j
                // nếu lượt chơi player 1

                // Kiểm tra ô trống thì cho đánh
                if (this.arr[(int) mX / grid_size][(int) mY / grid_size] == empty_cell) {
                    if (playerTurn == 0) {
                        if (firstPlayerX == true) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = x_cell; // đổi trạng thái ô cờ
                        }
                        if (firstPlayerX == false) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = o_cell; // đổi trạng thái ô cờ
                        }
                        // chuyển lượt chơi cho player 2
                        playerTurn = 1;
                    }
                    //nếu lượt chơi player 2
                    else if (playerTurn == 1) {
                        if (firstPlayerX == false) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = x_cell; // đổi trạng thái ô cờ
                        }
                        if (firstPlayerX == true) {
                            this.arr[(int) mX / grid_size][(int) mY / grid_size] = o_cell;
                        }
                        playerTurn = 0;
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cờ caro");
                    builder.setIcon(R.drawable.warning);
                    builder.setMessage("Đánh sai xin mời đánh lại");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    builder.create().show();
                }

                // Hàng và cột của điểm được chọn là hang,cot
                // Biến dem=0;
                //Nếu đủ 5 ô return
                //int a,b;

                // Vị trí điểm chạm cho hàng và cột
                int hang = (int) mX / grid_size;
                int cot = (int) mY / grid_size;
                int dem = 1;
                int index = 1;
                // Kiểm tra hàng ngang
                while (hang + index < m && arr[hang][cot] == arr[hang + index][cot]) {
                    dem++;
                    index++;
                }
                index = 1;
                while (hang - index > 0 && arr[hang][cot] == arr[hang - index][cot]) {
                    dem++;
                    index++;
                }
                if (dem >= 5) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cờ caro");
                    builder.setIcon(R.drawable.congra);
                    if (playerTurn == 0) {// Người đầu tiên đánh
                        if (firstPlayerX == true) {// Trường hợp người đầu tiên đánh "X"
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                    } else if (playerTurn == 1) {
                        if (firstPlayerX == true) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final AlertDialog.Builder newbuilder = new AlertDialog.Builder(getContext());
                            newbuilder.setTitle("New game");
                            newbuilder.setMessage("Bạn có muốn tạo game mới không ?");
                            newbuilder.setIcon(R.drawable.newgame);

                            newbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clear();
                                }
                            });
                            newbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            newbuilder.create().show();
                        }
                    });
                    builder.create().show();
                }


                // Kiểm tra hàng dọc
                dem = 1;
                index = 1;
                while (cot + index < n && arr[hang][cot] == arr[hang][cot + index]) {
                    dem++;
                    index++;
                }
                index = 1;
                while (cot - index > 0 && arr[hang][cot] == arr[hang][cot - index]) {
                    dem++;
                    index++;
                }
                if (dem >= 5) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cờ caro");
                    builder.setIcon(R.drawable.congra);
                    if (playerTurn == 0) {// Người đầu tiên đánh
                        if (firstPlayerX == true) {// Trường hợp người đầu tiên đánh "X"
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                    } else if (playerTurn == 1) {
                        if (firstPlayerX == true) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }

                // Kiểm tra đường chéo
                dem = 1;
                index = 1;
                while (hang + index < m && cot + index < n && arr[hang][cot] == arr[hang + index][cot + index]) {
                    dem++;
                    index++;
                }
                index = 1;
                while (cot - index > 0 && hang - index > 0 && arr[hang][cot] == arr[hang - index][cot - index]) {
                    dem++;
                    index++;
                }
                if (dem >= 5) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cờ caro");
                    builder.setIcon(R.drawable.congra);
                    if (playerTurn == 0) {// Người đầu tiên đánh
                        if (firstPlayerX == true) {// Trường hợp người đầu tiên đánh "X"
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");

                        }
                    } else if (playerTurn == 1) {
                        if (firstPlayerX == true) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
                }


                // Kiểm tra chéo phải
                dem = 1;
                index = 1;
                while (hang + index < m && cot - index > 0 && arr[hang][cot] == arr[hang + index][cot - index]) {
                    dem++;
                    index++;
                }
                index = 1;
                while (cot + index < n && hang - index > 0 && arr[hang][cot] == arr[hang - index][cot + index]) {
                    dem++;
                    index++;
                }
                if (dem >= 5) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cờ caro");
                    builder.setIcon(R.drawable.congra);
                    if (playerTurn == 0) {// Người đầu tiên đánh
                        if (firstPlayerX == true) {// Trường hợp người đầu tiên đánh "X"
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                    } else if (playerTurn == 1) {
                        if (firstPlayerX == true) {
                            builder.setMessage("!!!!!!    Chúc mừng X thắng    !!!!!!");
                        }
                        if (firstPlayerX == false) {
                            builder.setMessage("!!!!!!    Chúc mừng O thắng    !!!!!!");
                        }
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create().show();
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
        this.invalidate();// Hàm này để vẽ lại quân cờ đã chọn
        return super.onTouchEvent(event);
    }

// Source code by LamNguyen

}
