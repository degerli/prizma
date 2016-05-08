package com.hrzafer.prizma.data;

public class Evaluation {
    int correct;
    int incorrect;
    double acc;

    public Evaluation(int correct, int incorrect) {
        this.correct = correct;
        this.incorrect = incorrect;
        int all = correct + incorrect;
        acc = (correct/(double)all) * 100;
    }

    @Override
    public String toString() {
        return "correct: " + correct + " incorrect: " + incorrect + " accuracy: " + acc;
    }
}
