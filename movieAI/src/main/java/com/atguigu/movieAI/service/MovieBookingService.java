package com.atguigu.movieAI.service;

import com.atguigu.movieAI.entity.MovieBooking;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MovieBookingService extends IService<MovieBooking> {

    MovieBooking getOne(MovieBooking movieBooking);

}