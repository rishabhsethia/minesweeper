package com.example.rishabh.minesweeper;

import android.content.Context;
import android.widget.Button;

import static com.example.rishabh.minesweeper.MainActivity.button_flag;
import static com.example.rishabh.minesweeper.MainActivity.button_set;
import static com.example.rishabh.minesweeper.MainActivity.not_set;

public class TButton extends android.support.v7.widget.AppCompatButton {
   public int value;
   public int x;
    public int y;
    public int enabled;
    public int enabled2;
    public int enabled3;

    public TButton(Context context,int i,int j) {
        super(context);
        value=not_set;
        x=i;
        y=j;
        enabled=1;
        enabled2=1;enabled3=1;
    }
    public void setButton(){
        this.value=button_set;
    }

    public int getVal(){
        return this.value;
    }

}
