package com.ezntek.beanmaths.game.question;

public class Operations {
    public enum Operation {
        ADD,
        SUB,
        MUL,
        DIV,
        POW,
        ROOT,
    };

    public static String toString(Operation op) {
        switch (op) {
            case ADD:
                return "+";
            case SUB:
                return "-";
            case MUL:
                return "×";
            case DIV:
                return "/";
            case POW:
                return "^";
            case ROOT:
                return "√";
            default:
                return "💩THIS IS A BUG PLEASE REPORT💩"; // poo
        }
    }
}
