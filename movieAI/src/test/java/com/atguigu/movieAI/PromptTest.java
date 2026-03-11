package com.atguigu.movieAI;

import com.atguigu.movieAI.assistant.MemoryChatAssistant;
import com.atguigu.movieAI.assistant.SeparateChatAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {

    @Autowired
    private SeparateChatAssistant separateChatAssistant;
    @Test
    public void testSystemMessage() {
        String answer = separateChatAssistant.chat(5,"今天几号");
        System.out.println(answer);
    }

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;
    @Test
    public void testUserMessage() {
        String answer = memoryChatAssistant.chat("我是奶龙");
        System.out.println(answer);

        String answer2 = memoryChatAssistant.chat("我18了");
        System.out.println(answer2);

        String answer3 = memoryChatAssistant.chat("你知道我是谁吗,多大了");
        System.out.println(answer3);
    }

    @Test
    public void testV() {
        String answer1 = separateChatAssistant.chat2(6, "我是奶龙");
        System.out.println(answer1);
        String answer2 = separateChatAssistant.chat2(6, "我是谁");
        System.out.println(answer2);
    }

    @Test
    public void testUserInfo() {
        //从数据库中获取用户信息
        String username = "奶龙";
        int age = 18;

        String answer1 = separateChatAssistant.chat3(7, "我是谁，我多大了", username, age);
        System.out.println(answer1);
    }
}
