package com.maple.oj.beans;

public class QuestionInfoPath {
    private Integer id;
    private String questionPath;
    private String modelPath;

    public QuestionInfoPath() {
    }

    public QuestionInfoPath(Integer id, String questionPath, String modelPath) {
        this.id = id;
        this.questionPath = questionPath;
        this.modelPath = modelPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionPath() {
        return questionPath;
    }

    public void setQuestionPath(String questionPath) {
        this.questionPath = questionPath;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
