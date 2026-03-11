package com.atguigu.movieAI;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.awt.*;
import java.net.URI;


@SpringBootTest
public class LLMTest {
    @Test
    public void testGPTDemo() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        String answer = model.chat("你好");
        System.out.println(answer);
    }

    @Autowired
    private OpenAiChatModel openAiChatModel;
    @Test
    public void testSpringBoot() {
        String answer = openAiChatModel.chat("我是谁？");
        System.out.println(answer);
    }

    @Autowired
    private OllamaChatModel ollamaChatModel;
    @Test
    public void testOllamaChat() {
        String answer = ollamaChatModel.chat("你是谁？");
        System.out.println(answer);
    }

    /**
     * 通义千问大模型
     */
    @Autowired
    private QwenChatModel qwenChatModel;
    @Test
    public void testDashScopeQwen() {
        //向模型提问
        String answer = qwenChatModel.chat("你好");
        //输出结果
        System.out.println(answer);
    }

    @Test
    public void testDashScopeWanx(){
        WanxImageModel wanxImageModel = WanxImageModel
                .builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(System.getenv("DASHSCOPE_API_KEY"))
                .build();
        Response<Image> response = wanxImageModel.generate("夜晚的森林天台场景，小黑是一只黑色小猫妖，圆圆的大眼睛，坐在屋顶边缘，微风吹动树叶和他的尾巴。远处是灯火通明的城市，高楼与霓虹灯形成温暖的光海。天空深蓝，繁星点点，月光洒在屋顶瓦片上。旁边坐着温柔的人类少年无限，白色衬衫与黑色长发随风飘动。整体画面是温馨安静的氛围，治愈系动画风格，细腻手绘质感，柔和光影，电影级构图，宫崎骏风格与国漫风格结合，8k，高细节，超清画质。");
        URI url = response.content().url();
        System.out.println(url);
    }
}
