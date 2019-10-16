package com.maple.oj.dao;

import com.maple.oj.beans.Answer;
import com.maple.oj.beans.QuestionInfoPath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    String getTestCasePathById(int id);

    List<QuestionInfoPath> getAllQuestionInfoPath();

    QuestionInfoPath getQuestionInfoPathById(int id);

    void insertAnswer(Answer answer);

    List<Answer> selectAnswerByUId(Integer uId);
}
