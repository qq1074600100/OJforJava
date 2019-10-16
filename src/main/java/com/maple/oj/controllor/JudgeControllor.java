package com.maple.oj.controllor;

import com.maple.oj.beans.QuestionInfo;
import com.maple.oj.beans.User;
import com.maple.oj.common.CodeRuntimeException;
import com.maple.oj.common.CompileErrorException;
import com.maple.oj.common.TimeoutException;
import com.maple.oj.service.LoginService;
import com.maple.oj.service.OnlineJudgeService;
import com.maple.oj.service.QuestionService;
import com.maple.oj.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class JudgeControllor {
    @Autowired
    OnlineJudgeService onlineJudgeService;
    @Autowired
    QuestionService questionService;
    @Autowired
    LoginService loginService;

    @GetMapping("/")
    public ModelAndView index() {
        //查询所有题目介绍，返回显示列表
        List<QuestionInfo> questionInfos = questionService.findAllQuestions();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("questionInfos", questionInfos);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/oj/{id}")
    public ModelAndView oj(@PathVariable("id") Integer id) {
        //根据id查找题目内容，并转向答题界面
        QuestionInfo questionInfo = questionService.findQuestionById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.getModel().put("questionInfo", questionInfo);
        modelAndView.setViewName("onlineJudge");
        return modelAndView;
    }

    @PostMapping("/judge")
    public ModelAndView judge(@ModelAttribute("id") int id, @ModelAttribute("content") String content,
                              HttpServletRequest request) {
        //调用service完成判题功能,
        //发生异常时，将异常信息存入model，以便前端显示
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        String resultMsg = null;
        try {
            //根据session获取当前用户ID
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("user");
            User user = loginService.getUserByName(username);
            Integer uId = user.getUid();
            resultMsg = onlineJudgeService.judge(id, content, uId);
            if (resultMsg != null) {
                model.put("answer", true);
                model.put("error", resultMsg);
            }
        } catch (TimeoutException
                | CompileErrorException
                | CodeRuntimeException e) {
            model.put("answer", false);
            model.put("error", IOUtils.string2Html(e.getMessage()));
        }
        model.put("id", id);
        modelAndView.setViewName("result");
        return modelAndView;
    }
}
