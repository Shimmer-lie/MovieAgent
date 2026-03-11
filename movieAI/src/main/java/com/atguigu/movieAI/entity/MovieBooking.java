package com.atguigu.movieAI.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieBooking {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String phone;

    private String movieName;

    private String cinemaName;

    private String date;

    private String time;

    private String hall;

}