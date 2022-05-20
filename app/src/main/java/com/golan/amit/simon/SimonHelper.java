package com.golan.amit.simon;

import java.util.Arrays;

public class SimonHelper {

    /**
     * Constants
     */
    public static final int COARDS = 5;
    public static final int ROUNDS = 15;
//    public static final int ROUNDS = 2;

    private int[] simon_nums;

    private int out_index;
    private int running_index;

    public SimonHelper() {
        simon_nums = new int[ROUNDS];
        reset_indexes();
    }

    public int getCoardByIndex(int index) {
        return this.simon_nums[index];
    }

    public void reset_indexes() {
        this.out_index = 0;
        this.running_index = 0;
    }

    public void fill() {
        for(int i = 0; i < ROUNDS; i++) {
            simon_nums[i] = (int)(Math.random() * COARDS);
        }
    }

    public String h_representation() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i <= getOut_index(); i++) {
            sb.append(simon_nums[i]+1);
            if(i < getOut_index())
                sb.append(" ");
        }
        return sb.toString();
    }

    public String full_h_representation() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < ROUNDS; i++) {
            sb.append(simon_nums[i]+1);
            if(i < ROUNDS)
                sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "SimonHelper{" +
                "simon_nums=" + Arrays.toString(simon_nums) +
                '}';
    }

    public int getOut_index() {
        return out_index;
    }

    public void setOut_index(int out_index) {
        this.out_index = out_index;
    }

    public void increaseOut_index() {
        this.out_index++;
    }

    public int getRunning_index() {
        return running_index;
    }

    public void setRunning_index(int running_index) {
        this.running_index = running_index;
    }

    public void increaseRunning_index() {
        this.running_index++;
    }

}
