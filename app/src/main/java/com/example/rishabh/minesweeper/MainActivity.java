package com.example.rishabh.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout rootLayout;
    ArrayList<LinearLayout> rows;
    public int size_rrr=6;
    public int size_r=6;
    public int size_c=6;
    public int first_click=0;
    public int exclude_row=-1;
    public int exclude_coll=-1;
    public static final int not_set=0;
    public static final int button_set = 100;//for zero
    public static final int button_flag = 200;//for mine
    public int onclick_disable=0;
   // public static final int button_value = 0;//for mine
    TButton board[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootLayout=findViewById(R.id.rootLayout);
        setUpBoard();
    }
    public void setUpBoard(){
        rows=new ArrayList<>();
        board=new TButton[size_r][size_c];

        for (int i=0;i<size_r;i++){

            LinearLayout layout=new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            layout.setLayoutParams(layoutParams);
            rootLayout.addView(layout);
            rows.add(layout);
        }

        for (int i=0;i<size_r;i++){
            for (int j=0;j<size_c;j++){
                TButton button=new TButton(this,i,j);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                button.setLayoutParams(layoutParams);
                LinearLayout linearow=rows.get(i);
                linearow.addView(button);
                button.setOnClickListener(this);
                board[i][j]=button;
            }
        }

    }
    @Override
    public void onClick(View view) {
        TButton button= (TButton)view;
        int i=button.x;
        int j=button.y;
       if(onclick_disable!=1) {
           if (first_click == 0) {
               //  int index= setmines();

               //button.value=button_set;
               button.enabled = 0;
               // setmines(i,j,button);
               exclude_coll = j;
               exclude_row = i;
               first_click = 1;
               setmines(i, j);
               setnumber(button);
               button.enabled3 = 0;
               neighbour(button);
           } else if (button.value == button_flag) {
               onclick_disable = 1;
               revealmine(button);
               Toast.makeText(this, "you Lost The Game", Toast.LENGTH_SHORT).show();
           } else if (first_click == 1) {
               if (button.enabled3 != 0) {
                   button.enabled3 = 0;
                   neighbour(button);
               }
           }
       }

    }

    private void revealmine(TButton button) {
        for (int i=0;i<size_r;i++){
            for (int j=0;j<size_c;j++){

                if(board[i][j].value==button_flag) {
                    board[i][j].setBackgroundResource(R.drawable.layer);
                    board[i][j].enabled3=0;
                    board[i][j].enabled2=0;
                    board[i][j].enabled=0;
                }
            }
        }
    }

    private void setnumber(TButton button) {
        int[] x={-1,-1,-1,0,0,1,1,1};
        int[] y={-1,0,1,-1,1,-1,0,1};
        int x_c=button.x;
        int y_c=button.y;
        for(int i=0;i<8;i++){
            int yy=y_c+y[i];
            int xx=x_c+x[i];
            int val=0;
            int check=1;
            if(xx<size_r && xx>=0 && yy<size_c && yy>=0){
                Log.d("hey", "neighbour: "+xx+" "+yy);
                if(board[xx][yy].value==button_flag){

                    board[x_c][y_c].value++;
                    board[xx][yy].enabled=0;
                    int solid;
                    val++;
                //   board[x_c][y_c].setText(""+board[x_c][y_c].value);
                    check=0;

                }else if(board[xx][yy].value==0 && board[xx][yy].enabled==1){
                    board[xx][yy].enabled=0;
                    //   board[xx][yy].setText("0");
                    setnumber(board[xx][yy]);



                }

            }
        }
    }


    private void neighbour(TButton button) {
        int[] x={-1,-1,-1,0,0,1,1,1};
        int[] y={-1,0,1,-1,1,-1,0,1};
        int x_c=button.x;
        int y_c=button.y;board[x_c][y_c].setBackgroundResource(R.drawable.button);

        int check=1;
        if(board[x_c][y_c].value!=0){
            board[x_c][y_c].enabled2=0;
            board[x_c][y_c].setText(""+board[x_c][y_c].value);
            board[x_c][y_c].setBackgroundResource(R.drawable.back);
            return;
        }
        for(int i=0;i<8;i++){
            int yy=y_c+y[i];
            int xx=x_c+x[i];
            int val=0;
            check=1;
            if(xx<size_r && xx>=0 && yy<size_c && yy>=0){
                if(board[xx][yy].value==0 && board[xx][yy].enabled2==1){
                    board[xx][yy].enabled2=0;
                 //   board[xx][yy].setText(""+board[xx][yy].value);
                    board[xx][yy].setBackgroundResource(R.drawable.button);

                    //   board[xx][yy].setText("0");
                    neighbour(board[xx][yy]);
                }else if(board[xx][yy].enabled3==1 && board[xx][yy].value!=button_flag &&board[xx][yy].value!=0){
                    board[xx][yy].setBackgroundResource(R.drawable.back);

                    board[xx][yy].setText(""+board[xx][yy].value);
                        board[xx][yy].enabled3=0;
                }
                if(board[xx][yy].value==button_flag){
                    check=0;
                }


            }
        }
        if(check!=0){
            int i=0;
        //    board[x_c][y_c].setText(""+board[x_c][y_c].value);
        }


    }




    private void setmines(int exlude_r,int exclude_j) {
        int min=0;
        Random rand =new Random();
        int per= (int) ((size_c*size_r)/.6);
        for(int i=0;i<10;i++){
            int num_r=rand.nextInt(((size_r-1)-min)+1)+ min ;
            int num_c=rand.nextInt(((size_c-1)-min)+1)+min;
            if(num_r!=exlude_r && num_c!=exclude_j && board[num_r][num_c].enabled==1){
                board[num_r][num_c].value=button_flag;
                board[num_r][num_c].enabled=0;
         //      board[num_r][num_c].setText("-1");
            }else{
                i--;
            }
        }

        return ;

    }
}
