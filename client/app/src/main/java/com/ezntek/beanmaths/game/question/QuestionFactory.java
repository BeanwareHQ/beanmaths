package com.ezntek.beanmaths.game.question;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezntek.beanmaths.game.question.Operations.Operation;
import com.ezntek.beanmaths.config.Config;

public class QuestionFactory {
    Random rng;
    Config config;
    final static int[] perfectSquares = { 1, 4, 9, 16, 25, 36, 49, 64, 81, 100 };
    final static int[] perfectCubes = { 1, 8, 27, 64, 125, 216, 343, 512, 729, 1000 };

    private List<List<Integer>> timesTable;

    public QuestionFactory(Config cfg) {
        this.rng = new Random();
        this.config = cfg;
    }

    public Question makeQuestion(Operation op) {
        Question result = null;

        switch (op) {
            // equivalent to case ADD or SUB.
            // intentional fall-through case
            case ADD:
            case SUB: {
                int maxValue = this.config.math.basic.max;
                if (!this.config.math.basic.imperfectDigits) {
                    // displacement from the next 10s place.
                    int displacement = randomNumRange(1, 9);

                    // hack to round to nearest 10
                    int base = Math.round(this.rng.nextInt(maxValue + 5) / 10) * 10;

                    // a "base" of 30 with a displacement of 4 would generate 26 + 4.
                    result = new Question(base - displacement, displacement, op);
                } else {
                    int rand1 = randomNumRange(1, maxValue / 2);
                    int rand2 = randomNumRange(1, maxValue / 2);
                    result = new Question(rand1, rand2, op);
                }
                break;
            }
            case MUL: {
                int maxValue = this.config.math.timesTable.max;

                // generate a random number from 2 to maxValue
                // avoids cases where one multiplies by 1
                int first = randomNumRange(2, maxValue);
                int second = randomNumRange(2, maxValue);

                result = new Question(first, second, op);
            }
            case DIV: {
                int maxValue = this.config.math.timesTable.max;

                if (this.timesTable == null) {
                    // constructs a base for the times tables, say
                    // 2, 3 ... 12
                    this.timesTable = new ArrayList<List<Integer>>();
                    for (Integer i = 2; i <= maxValue; i++) {
                        List<Integer> l = new ArrayList<Integer>();
                        for (Integer j = 1; j <= maxValue; j++) {
                            l.add(i * j);
                        }
                        this.timesTable.add(l);
                    }
                }

                int divisor = randomNumRange(2, maxValue);
                int dividend = this.timesTable.get(divisor).get(this.rng.nextInt(maxValue - 1));
                result = new Question(dividend, divisor, op);
            }
            case POW: {
                int first = randomNumRange(1, this.config.math.advanced.maxBase);
                int second = randomNumRange(2, this.config.math.advanced.maxPower); // avoid powers to 1 and 2
                result = new Question(first, second, op);
            }
            case ROOT: {
                int first;
                int second;

                if (this.rng.nextInt(10) % 2 == 0) {
                    int index = this.rng.nextInt(perfectSquares.length - 1);
                    first = perfectSquares[index];
                    second = 2;
                } else {
                    int index = this.rng.nextInt(perfectCubes.length - 1);
                    first = perfectCubes[index];
                    second = 3;
                }

                result = new Question(first, second, op);
            }
        }

        return result;
    }

    public Question makeQuestion() {
        Operation[] vals = Operation.values();
        Operation op = vals[this.rng.nextInt(vals.length - 1)];

        return makeQuestion(op);
    }

    private int randomNumRange(int start, int end) {
        return this.rng.nextInt(end + start) - start;
    }
}
