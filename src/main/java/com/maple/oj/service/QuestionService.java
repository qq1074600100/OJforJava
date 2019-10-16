package com.maple.oj.service;

import com.maple.oj.beans.Answer;
import com.maple.oj.beans.QuestionInfo;
import com.maple.oj.beans.QuestionInfoPath;
import com.maple.oj.dao.QuestionMapper;
import com.maple.oj.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionDao;

    public List<QuestionInfo> findAllQuestions() {
        List<QuestionInfoPath> questionInfoPaths = questionDao.getAllQuestionInfoPath();
        List<QuestionInfo> questionInfos = new ArrayList<>();
        for (QuestionInfoPath questionInfoPath : questionInfoPaths) {
            QuestionInfo info = new QuestionInfo();
            info.setId(questionInfoPath.getId());
            info.setQuestion(IOUtils.readFile2String(new File(questionInfoPath.getQuestionPath()), "UTF-8"));
            info.setModel(IOUtils.readFile2String(new File(questionInfoPath.getModelPath()), "UTF-8"));
            questionInfos.add(info);
        }
        return questionInfos;
    }

    public QuestionInfo findQuestionById(int id) {
        QuestionInfoPath questionInfoPath = questionDao.getQuestionInfoPathById(id);
        QuestionInfo info = new QuestionInfo();
        info.setId(questionInfoPath.getId());
        info.setQuestion(IOUtils.readFile2String(new File(questionInfoPath.getQuestionPath()), "UTF-8"));
        info.setModel(IOUtils.readFile2String(new File(questionInfoPath.getModelPath()), "UTF-8"));
        return info;
    }

    public List<Answer> findAllAnswerByUId(Integer id) {
        List<Answer> answers = questionDao.selectAnswerByUId(id);
        //遍历，依据文件路径读出文件内容
        for (Answer answer : answers) {
            File questionFile = new File(answer.getQuestionPath());
            answer.setQuestion(IOUtils.readFile2String(questionFile, "UTF-8"));
            answer.setMsg(IOUtils.string2Html(answer.getMsg()));
        }
        return answers;
    }
}
