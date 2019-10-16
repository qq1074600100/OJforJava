package com.maple.oj.service;

import com.maple.oj.beans.Answer;
import com.maple.oj.common.CodeRuntimeException;
import com.maple.oj.common.CompileErrorException;
import com.maple.oj.common.TimeoutException;
import com.maple.oj.dao.QuestionMapper;
import com.maple.oj.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
public class OnlineJudgeService {
    @Autowired
    private QuestionMapper questionDao;

    private static final int S_OF_TIMEOUT = 5;


    //判断程序是否正确，返回程序运行消耗时间(ms)
    public String judge(int id, String content, Integer uId) throws TimeoutException,
            CompileErrorException, CodeRuntimeException {
        //获取该问题的测试用例
        String path2TestCase = questionDao.getTestCasePathById(id);
        File testCaseFile = new File(path2TestCase);
        if (!testCaseFile.exists()) {
            System.out.println("该测试用例不存在");
            return null;
        }

        //随机一个文件夹名，防止并行访问时，文件脏读
        int num = (int) (Math.random() * 100);
        //添加包语句，防止无法加载主类
        content = "package tempClass.a" + num + ";\n" + content;
        String testCaseHeader = "package tempClass.a" + num + ";\n";
        //创建文件夹，并得到文件所在位置
        String currentDir = System.getProperty("user.dir");
        File tempFolder = new File(currentDir, "tempClass\\a" + num);
        tempFolder.mkdir();
        File solutionJavaFile = new File(tempFolder, "Solution.java");
        File solutionClassFile = new File(tempFolder, "Solution.class");
        String testFileName = testCaseFile.getName();
        String testClassName = testFileName.substring(0, testFileName.length() - 5);
        File testJavaFile = new File(tempFolder, testClassName + ".java");
        File testClassFile = new File(tempFolder, testClassName + ".class");
        IOUtils.write2File(content, solutionJavaFile);
        IOUtils.testCase2PackageJava(testCaseFile, testJavaFile, testCaseHeader);

        try {
            //编译.java文件，若文件不存在，说明出现编译错误，抛异常返回错误信息，交由上层处理
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("javac -encoding utf-8 " + solutionJavaFile.getPath());
            process.waitFor();
            if (!solutionClassFile.exists()) {
                String errMsg = IOUtils.readCmd2String(process.getErrorStream(), "GBK");
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Answer answer = new Answer(time, 0, errMsg, id, uId);
                questionDao.insertAnswer(answer);
                throw new CompileErrorException(errMsg);
            }

            process = runtime.exec("javac -encoding utf-8 " + testJavaFile.getPath());
            process.waitFor();
            if (!testClassFile.exists()) {
                String errMsg = IOUtils.readCmd2String(process.getErrorStream(), "GBK");
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Answer answer = new Answer(time, 0, errMsg, id, uId);
                questionDao.insertAnswer(answer);
                throw new CompileErrorException(errMsg);
            }

            //计时，并判断是否发生超时，若超时，抛异常，交由上层处理
            long begin = System.currentTimeMillis();
            String relativePath2Class = "tempClass.a" + num + "." + testClassName;
            process = runtime.exec("java " + relativePath2Class);
            process.waitFor(OnlineJudgeService.S_OF_TIMEOUT, TimeUnit.SECONDS);
            long end = System.currentTimeMillis();
            if (end - begin >= OnlineJudgeService.S_OF_TIMEOUT) {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Answer answer = new Answer(time, 0, "超时", id, uId);
                questionDao.insertAnswer(answer);
                throw new TimeoutException();
            }

            //判断运行是否正确，若发生错误，抛异常，交由上层处理
            String runErrMsg = IOUtils.readCmd2String(process.getErrorStream(), "GBK");
            if (runErrMsg.length() > 0) {
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Answer answer = new Answer(time, 0, runErrMsg, id, uId);
                questionDao.insertAnswer(answer);
                throw new CodeRuntimeException(runErrMsg);
            }

            //否则程序正确，返回运行时间
            String rstMsg = IOUtils.readCmd2String(process.getInputStream(), "GBK");
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Answer answer = new Answer(time, 1, "时间消耗:" + rstMsg + "ms", id, uId);
            questionDao.insertAnswer(answer);
            return "时间消耗:" + rstMsg + "ms";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            //无论结果正确与否，删除java文件及文件夹，防止服务端出现重复文件名，同时也防止占用过多的存储空间
            if (tempFolder.exists()) {
                IOUtils.deleteFolder(tempFolder);
            }
        }
        return null;
    }
}
