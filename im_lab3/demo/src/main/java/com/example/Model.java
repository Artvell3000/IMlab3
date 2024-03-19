package com.example;

import java.util.ArrayList;

public class Model {
    ArrayList<Boolean[]> g = new ArrayList<>();
    int lenRow = 14;
    final Boolean [][] currentPattern = new Boolean[][]{
        {true,true,true}, //1
        {true,true,false}, //2
        {true,false,true}, //3
        {true,false,false}, //4
        {false,true,true}, //5
        {false,true,false}, //6
        {false,false,true}, //7
        {false,false,false}, //8
    };

    Boolean[] rule = new Boolean[8]; 

    private Boolean[] getFirstVector(){
        Boolean[] v = new Boolean[14];
        for(int i=0;i<14;i++){
            v[i] = false;
        }
        v[6] = true;
        v[7] = true;
        v[11] = true;
        return v;
    }

    Model(int n){
        g.add(getFirstVector());
        setRule(n);
    }

    public int getNumberOfState(){
        return g.size()-1;
    }

    private Boolean[] getPattern(int i, Boolean[] v){
        Boolean[] p = new Boolean[3];
        p[1] = v[i];

        if(i == 0){
            p[0] = v[lenRow-1];
            p[2] = v[i+1];
            return p;
        }

        if(i == 13){
            p[0] = v[i-1];
            p[2] = v[0];
            return p;
        }

        p[0] = v[i-1];
        p[2] = v[i+1];
        return p;
    }

    private int getNumberOfPattern(Boolean[] p){
        
        for(int i=0;i<7;i++){
            if(
                currentPattern[i][0] == p[0] &&
                currentPattern[i][1] == p[1] &&
                currentPattern[i][2] == p[2] 
            ) return i;
        }
        return 7;
    }

    public Boolean[] getNextState(){
        Boolean[] newV = new Boolean[14];
        Boolean[] oldV = g.get(getNumberOfState());

        for(int i=0;i<lenRow;i++){
            var p = getPattern(i, oldV);
            var n = getNumberOfPattern(p);
            newV[i] = rule[n];
        }

        g.add(newV);
        return newV;
    }

    public void setRule(int n){
        Boolean[] r = new Boolean[8];
        int i = 7;
        while(n > 0){
            r[i] = (n%2 == 1);
            n /= 2;
            i--;
        }
        while(i>=0){
            r[i] = false;
            i--;
        }

        rule = r;
        for(int j=0;j<8;j++){
            if(r[j]) System.out.print(1);
            else System.out.print(0);
        }
    }

    public Boolean[] getNowState(){
        return g.get(g.size()-1);
    }
}
