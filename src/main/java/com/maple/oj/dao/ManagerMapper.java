package com.maple.oj.dao;

import com.maple.oj.beans.QuestionInfoPath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {
    List<Integer> selectAllId();

    boolean deleteQuestionInfoById(Integer id);

    boolean insertQuestionInfo(QuestionInfoPath questionInfoPath);

    boolean insertTestCase(Integer testId, String path);
}
