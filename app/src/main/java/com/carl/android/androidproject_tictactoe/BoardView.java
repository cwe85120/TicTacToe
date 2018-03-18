package com.carl.android.androidproject_tictactoe;

import android.content.Context;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class BoardView extends View {

    private static final int LINE_THICKNESS = 5;
    private static final int BOARD_MARGIN = 20;
    private static final int STROKE_WIDTH = 15;
    private int width, height, boardWidth, boardHeight;
    private Paint gridPaint, oPaint, xPaint;
    private Board board;
    private MainActivity activity;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.rgb(138,43,226));
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.MAGENTA);
    }

    public void setMainActivity(MainActivity a){
        activity = a;
    }

    public void setBoard(Board b){
        board = b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);

        boardHeight = (height - LINE_THICKNESS) / 3;
        boardWidth = (width - LINE_THICKNESS) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas){
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!board.isGameOver() && event.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int) (event.getX() / boardWidth);
            int y = (int) (event.getY() / boardHeight);
            char win = board.play(x, y);
            invalidate();

            if(win != ' '){
                activity.gameEnd(win);
            }
            else{
                win = board.computer();
                invalidate();

                if(win != ' '){
                    activity.gameEnd(win);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public void drawBoard(Canvas canvas){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                draws(canvas, board.getBoard(i, j), i, j);
            }
        }
    }

    public  void drawGrid(Canvas canvas){
        for(int i = 0; i < 2; i++){

            //vertical lines
            float left = boardWidth * (i + 1);
            float right = left + LINE_THICKNESS;
            float top = 0;
            float bottom = height;
            canvas.drawRect(left, top, right, bottom, gridPaint);

            //horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = boardHeight * (i + 1);
            float bottom2 = top2 + LINE_THICKNESS;
            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void draws(Canvas canvas, char c, int x, int y){
        if(c == 'O'){
            float cx = (boardWidth * x) + boardWidth / 2;
            float cy = (boardHeight * y) + boardHeight / 2;
            canvas.drawCircle(cx, cy, Math.min(boardWidth, boardHeight) / 2 - BOARD_MARGIN * 2, oPaint);
        }
        else if(c == 'X'){
            float startX = (boardWidth * x) + BOARD_MARGIN;
            float startY = (boardHeight * y) + BOARD_MARGIN;
            float endX = startX + boardWidth - BOARD_MARGIN * 2;
            float endY = startY + boardHeight - BOARD_MARGIN;

            float startX2 = (boardWidth * (x + 1)) - BOARD_MARGIN;
            float startY2 = (boardHeight * y) + BOARD_MARGIN;
            float endX2 = startX2 - boardWidth + BOARD_MARGIN * 2;
            float endY2 = startY2 + boardHeight - BOARD_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);
            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }
}
