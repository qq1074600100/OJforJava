package com.maple.oj.beans;

//保存问题的描述信息
public class QuestionInfo {
    private Integer id;
    private String question;
    private String model;

    public QuestionInfo() {
    }

    public QuestionInfo(Integer id, String question, String model) {
        this.id = id;
        this.question = question;
        this.model = model;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "QuestionInfo{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
