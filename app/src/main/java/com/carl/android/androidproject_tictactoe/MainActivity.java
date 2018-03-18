package com.carl.android.androidproject_tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import static com.carl.android.androidproject_tictactoe.R.id.board;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = (BoardView) findViewById(R.id.board);
        board = new Board();
        boardView.setBoard(board);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_new_game){
            board.newGame();
            boardView.invalidate();
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameEnd(char c){
        String message = (c == 'T') ? "Game Over. Tie" : "Game Over. " + c + " win";

        new AlertDialog.Builder(this).setTitle("Tic-Tac-Toe").setMessage(message).
                setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        board.newGame();
                        boardView.invalidate();
                    }
                }).show();
    }
}
