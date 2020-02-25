package com.marwaeltayeb.souq.model;

public class Help {

    private String question;
    private String answer;

    public Help(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
