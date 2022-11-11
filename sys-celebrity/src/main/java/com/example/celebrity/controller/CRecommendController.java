package com.example.celebrity.controller;


import com.example.common.entity.Recommend;
import com.example.common.entity.UserBehavior;
import com.example.common.service.GoodService;
import com.example.common.service.RecommendService;
import com.example.common.service.UserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("celebrity")
public class CRecommendController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private UserBehaviorService userBehaviorService;

    @Autowired
    private GoodService goodService;

    @GetMapping("getRecommend")
    @ResponseBody
    public String getRecommend(HttpSession session) {
        String account = ((User) session.getAttribute("user")).getAccount();

        //根据account获取用户行为
        UserBehavior userBehavior = userBehaviorService.all(account);
        //根据一些方法获取其喜好，
        Recommend recommend = new Recommend();
        recommend.setAccount(account);
        String[] hobby = new String[6];
        hobby[0] = userBehavior.getLike1();
        hobby[1] = userBehavior.getLike2();
        hobby[2] = userBehavior.getBrowse1();
        hobby[3] = userBehavior.getBrowse2();
        hobby[4] = userBehavior.getBrowse3();
        hobby[5] = userBehavior.getBrowse4();

        //根据喜好从数据库获取对应商品,并将其放入到recommend表中
        recommend.setRe1(getGoodIdByType(hobby[0]));
        recommend.setRe2(getGoodIdByType(hobby[1]));
        recommend.setRe3(getGoodIdByType(hobby[2]));
        recommend.setRe4(getGoodIdByType(hobby[3]));
        recommend.setRe5(getGoodIdByType(hobby[4]));
        recommend.setRe6(getGoodIdByType(hobby[5]));

        //将生成的数据放到数据库里面去
        int flag=recommendService.saveRecommend(recommend);
        if (flag!=0){
            return "200";
        }
        return "400";
    }


    public int getGoodIdByType(String hobby) {
        List<Integer> list = goodService.getGoodId(hobby);
        Random random = new Random();
        int flag = random.nextInt(list.size());
        return list.get(flag);
    }
}
