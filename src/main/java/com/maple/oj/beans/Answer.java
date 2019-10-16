package com.maple.oj.beans;

import java.sql.Timestamp;

public class Answer {
    private Timestamp commitTime;
    private int correct;
    private String questionPath;
    private String msg;
    private Integer qId;
    private Integer uId;
    private String question;

    public Answer() {
    }

    public Answer(Timestamp commitTime, int correct, String questionPath, String msg) {
        this.commitTime = commitTime;
        this.correct = correct;
        this.questionPath = questionPath;
        this.msg = msg;
    }

    public Answer(Timestamp commitTime, int correct, String msg, Integer qId, Integer uId) {
        this.commitTime = commitTime;
        this.correct = correct;
        this.questionPath = questionPath;
        this.msg = msg;
        this.qId = qId;
        this.uId = uId;
    }

    public Timestamp getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Timestamp commitTime) {
        this.commitTime = commitTime;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getQuestionPath() {
        return questionPath;
    }

    public void setQuestionPath(String questionPath) {
        this.questionPath = questionPath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getqId() {
        return qId;
    }

    public void setqId(Integer qId) {
        this.qId = qId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
