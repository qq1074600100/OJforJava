package com.maple.oj.controllor;

import com.maple.oj.service.LoginService;
import com.maple.oj.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

//管理员才能访问
@Controller
public class ManagerControllor {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private LoginService loginService;

    @GetMapping("/managerIntercept")
    public String intercept() {
        return "managerIntercept";
    }

    @GetMapping("/questionForm")
    public String questionForm() {
        return "questionForm";
    }

    @GetMapping("/managerForm")
    public String managerForm() {
        return "managerForm";
    }

    //添加题目
    @PostMapping("/addQuestion")
    public ModelAndView addQuestion(@ModelAttribute("question") String question,
                                    @ModelAttribute("model") String model,
                                    @ModelAttribute("testCase") String testCase) {
        managerService.addQuestion(question, model, testCase);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    //按id删除题目
    @GetMapping("/delete/{id}")
    public ModelAndView deleteQuestionById(@PathVariable("id") int id) {
        managerService.deleteQuestionById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }

    //添加一个管理员用户
    @PostMapping("/addManager")
    public ModelAndView addManager(@ModelAttribute("username") String username,
                                   @ModelAttribute("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        int nameLength = username.length();

        //标记所有判定过程是否出错
        boolean totalSign = false;

        //标记用户名判定过程是否出错
        boolean sign = false;
        for (int i = 0; i < nameLength; i++) {
            char c = username.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9')))) {
                sign = true;
                break;
            }
        }
        if (nameLength > 20 || nameLength < 10 || sign) {
            model.put("userError", "用户名由10~20个字母或数字组成");
            totalSign = true;
        }
        if (loginService.userExists(username)) {
            model.put("userError", "用户名已存在");
            totalSign = true;
        }
        int passwordLength = password.length();
        if (passwordLength > 20 || passwordLength < 6) {
            model.put("passwordError", "密码由6~20个字符组成");
            totalSign = true;
        }

        if (totalSign) {
            modelAndView.setViewName("managerForm");
        } else {
            //添加管理员
            loginService.addUser(username, password, true);
            modelAndView.setViewName("success");
        }

        return modelAndView;
    }
}
