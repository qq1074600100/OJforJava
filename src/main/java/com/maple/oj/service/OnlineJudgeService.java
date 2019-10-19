package com.maple.oj.service;

import com.maple.oj.beans.Answer;
import com.maple.oj.common.CodeRuntimeException;
import com.maple.oj.common.CompileErrorException;
import com.maple.oj.common.ResultReturnException;
import com.maple.oj.common.TimeoutException;
import com.maple.oj.dao.QuestionMapper;
import com.maple.oj.dynamic.DynamicLoader;
import com.maple.oj.utils.IOUtils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class OnlineJudgeService {
    @Autowired
    private QuestionMapper questionDao;

    private static final int RUN_TIME_LIMITED = 5;

    private void saveHistory(int correct, String msg, Integer qId, Integer uId) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Answer answer = new Answer(time, correct, msg, qId, uId);
        questionDao.insertAnswer(answer);
    }

    //判断程序是否正确，返回程序运行消耗时间(ms)
    public String judge(int id, String content, Integer uId) throws TimeoutException,
            CompileErrorException, CodeRuntimeException, ResultReturnException {

        //获取该问题的测试用例
        String path2TestCase = questionDao.getTestCasePathById(id);
        File testCaseFile = new File(path2TestCase);
        if (!testCaseFile.exists()) {
            String errMsg = "该测试用例不存在";
            saveHistory(0, errMsg, id, uId);
            throw new ResultReturnException(errMsg);
        }
        String source2TestCase = IOUtils.readFile2String(testCaseFile, "UTF-8");
        HashMap<String, String> sources = new HashMap<>();
        String testCaseFileName = testCaseFile.getName();
        sources.put(testCaseFileName, source2TestCase);
        sources.put("Solution.java", content);
        File testFile = new File(System.getProperty("user.dir"), "testCase\\Test.java");
        String source2Test = IOUtils.readFile2String(testFile, "UTF-8");
        sources.put(testFile.getName(), source2Test);

        // 编译结果收集器
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();
        Map<String, byte[]> bytecodes = DynamicLoader.compile(sources, compileCollector);
        //编译错误处理
        if (bytecodes == null) {
            // 获取编译错误信息
            List<Diagnostic<? extends JavaFileObject>> compileError = compileCollector.getDiagnostics();
            StringBuilder compileErrorRes = new StringBuilder();
            for (Diagnostic diagnostic : compileError) {
                compileErrorRes.append("Compilation error at ");
                compileErrorRes.append(diagnostic.getLineNumber());
                compileErrorRes.append(".");
                compileErrorRes.append(System.lineSeparator());
                compileErrorRes.append(diagnostic.getMessage(Locale.getDefault()));
            }
            String errMsg = compileErrorRes.toString();
            saveHistory(0, errMsg, id, uId);
            throw new CompileErrorException(errMsg);
        }

        //动态类加载器
        DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(bytecodes);
        //反射调用Test.test(String className)方法
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Callable<String> runTask = new Callable<String>() {
            @Override
            public String call() throws Exception {
                long begin = System.currentTimeMillis();
                Result result = null;
                try {
                    String testCaseClassName = testCaseFileName.substring(0, testCaseFileName.length() - 5);
                    Class testCaseClass = classLoader.loadClass(testCaseClassName);
                    result = JUnitCore.runClasses(testCaseClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();

                if (result.wasSuccessful()) {
                    return ("时间消耗: " + (end - begin) + "ms");
                } else {
                    StringBuilder msg = new StringBuilder();
                    for (Failure failure : result.getFailures()) {
                        msg.append(failure.getException().getMessage());
                    }
                    throw new ExecutionException(new CodeRuntimeException(msg.toString()));
                }
            }
        };
        Future<String> res = pool.submit(runTask);

        // 获取运行结果，处理非客户端代码错误
        String runResult;
        try {
            runResult = res.get(RUN_TIME_LIMITED, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            String errMsg = "Program interrupted.";
            saveHistory(0, errMsg, id, uId);
            throw new CodeRuntimeException(errMsg);
        } catch (ExecutionException e) {
            String errMsg = e.getCause().getMessage();
            saveHistory(0, errMsg, id, uId);
            throw new CodeRuntimeException(errMsg);
        } catch (java.util.concurrent.TimeoutException e) {
            String errMsg = "Time Limit Exceeded!";
            saveHistory(0, errMsg, id, uId);
            throw new TimeoutException();
        } finally {
            pool.shutdown();
        }
        saveHistory(1, runResult, id, uId);
        return runResult;
    }
}
