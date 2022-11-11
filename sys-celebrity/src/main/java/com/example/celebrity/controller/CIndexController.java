package com.example.celebrity.controller;

import com.example.common.entity.Good;
import com.example.common.entity.Recommend;
import com.example.common.entity.User;
import com.example.common.service.GoodService;
import com.example.common.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/celebrity")
public class CIndexController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private GoodService goodService;

    @GetMapping("/getRecommendGoods")
    @ResponseBody
    public List<Good> getRecommendGoods(HttpSession session){
        String account=((User) session.getAttribute("user")).getAccount();
        Recommend recommend=recommendService.getRecommend(account);
        int[] num=new int[6];
        num[0]=recommend.getRe1();
        num[1]=recommend.getRe2();
        num[2]=recommend.getRe3();
        num[3]=recommend.getRe4();
        num[4]=recommend.getRe5();
        num[5]=recommend.getRe6();

        List<Good> list=new ArrayList<>();

        for (int flag:num) {
            list.add(goodService.getGoodById(flag));
        }
        return list;
    }

}
