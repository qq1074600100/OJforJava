package com.maple.oj.service;

import com.maple.oj.beans.QuestionInfoPath;
import com.maple.oj.dao.ManagerMapper;
import com.maple.oj.dao.QuestionMapper;
import com.maple.oj.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerDao;
    @Autowired
    private QuestionMapper questionDao;

    //将问题相关的内容写入响应文件，然后更新数据库，确保外键约束
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Throwable.class)
    public boolean addQuestion(String question, String model, String testCase) {
        List<Integer> ids = managerDao.selectAllId();
        Integer maxId = IOUtils.getMax(ids);
        Integer id = maxId + 1;
        File questionFile = new File(
                "D:\\computerScience\\Web\\springBootDemo\\oj\\questions\\question" + id + ".txt");
        File modelFile = new File(
                "D:\\computerScience\\Web\\springBootDemo\\oj\\models\\model" + id + ".java");
        File testCaseFile = new File(
                "D:\\computerScience\\Web\\springBootDemo\\oj\\testCase\\TestCase" + id + ".java");
        managerDao.insertQuestionInfo(
                new QuestionInfoPath(id, questionFile.getAbsolutePath(), modelFile.getAbsolutePath()));
        managerDao.insertTestCase(id, testCaseFile.getAbsolutePath());
        IOUtils.write2File(question, questionFile);
        IOUtils.write2File(model, modelFile);
        IOUtils.write2File(testCase, testCaseFile);
        return true;
    }

    public boolean deleteQuestionById(int id) {
        QuestionInfoPath questionInfoPath = questionDao.getQuestionInfoPathById(id);
        File questionFile = new File(questionInfoPath.getQuestionPath());
        File modelFile = new File(questionInfoPath.getModelPath());
        String testCasePath = questionDao.getTestCasePathById(id);
        File testCaseFile = new File(testCasePath);
        if (questionFile.exists()) {
            questionFile.delete();
        }
        if (modelFile.exists()) {
            modelFile.delete();
        }
        if (testCaseFile.exists()) {
            testCaseFile.delete();
        }
        managerDao.deleteQuestionInfoById(id);
        return true;
    }
}
