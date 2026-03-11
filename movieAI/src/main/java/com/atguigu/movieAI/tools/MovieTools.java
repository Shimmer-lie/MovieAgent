package com.atguigu.movieAI.tools;

import com.atguigu.movieAI.entity.MovieBooking;
import com.atguigu.movieAI.service.MovieBookingService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieTools {

    @Autowired
    private MovieBookingService movieBookingService;

    @Tool(
            name = "电影订票",
            value = "根据用户提供的信息进行电影订票。先调用queryMovieSession查询是否有场次，如果有则让用户确认信息，确认后再进行订票。"
    )
    public String bookMovie(MovieBooking movieBooking){

        // 查询数据库是否已有相同预约
        MovieBooking bookingDB = movieBookingService.getOne(movieBooking);

        if(bookingDB == null){

            movieBooking.setId(null); // 防止大模型幻觉设置ID

            if(movieBookingService.save(movieBooking)){
                return "订票成功，并返回电影票详情";
            }else{
                return "订票失败";
            }
        }

        return "您在相同时间已经预订过该电影";
    }


    @Tool(
            name = "取消电影订票",
            value = "根据用户信息查询电影订票是否存在，如果存在则取消订票，否则返回取消失败"
    )
    public String cancelMovieBooking(MovieBooking movieBooking){

        MovieBooking bookingDB = movieBookingService.getOne(movieBooking);

        if(bookingDB != null){

            // 删除记录
            if(movieBookingService.removeById(bookingDB.getId())){
                return "取消订票成功";
            }else{
                return "取消订票失败";
            }

        }

        return "没有找到您的电影订票记录，请确认信息";
    }


    @Tool(
            name = "查询电影场次",
            value = "根据电影名称、日期和时间查询是否有可订票的电影场次"
    )
    public boolean queryMovieSession(

            @P(value = "电影名称") String movieName,

            @P(value = "日期") String date,

            @P(value = "时间，例如：18:00、20:30") String time,

            @P(value = "影院名称", required = false) String cinemaName
    ){

        System.out.println("查询电影场次");

        System.out.println("电影名称：" + movieName);
        System.out.println("日期：" + date);
        System.out.println("时间：" + time);
        System.out.println("影院：" + cinemaName);

        // TODO 模拟影院排片系统

        /*
        真实逻辑应该是：

        1 查询电影院排片表
        2 判断是否存在该电影场次
        3 判断影厅是否满座
        4 返回是否可订票
        */

        return true;
    }

}