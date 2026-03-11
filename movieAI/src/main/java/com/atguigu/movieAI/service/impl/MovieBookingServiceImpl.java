package com.atguigu.movieAI.service.impl;

import com.atguigu.movieAI.entity.MovieBooking;
import com.atguigu.movieAI.mapper.MovieBookingMapper;
import com.atguigu.movieAI.service.MovieBookingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MovieBookingServiceImpl
        extends ServiceImpl<MovieBookingMapper, MovieBooking>
        implements MovieBookingService {

    /**
     * 查询电影预约是否存在
     * @param movieBooking
     * @return
     */
    @Override
    public MovieBooking getOne(MovieBooking movieBooking) {

        LambdaQueryWrapper<MovieBooking> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(MovieBooking::getUsername, movieBooking.getUsername());
        queryWrapper.eq(MovieBooking::getPhone, movieBooking.getPhone());
        queryWrapper.eq(MovieBooking::getMovieName, movieBooking.getMovieName());
        queryWrapper.eq(MovieBooking::getCinemaName, movieBooking.getCinemaName());
        queryWrapper.eq(MovieBooking::getDate, movieBooking.getDate());
        queryWrapper.eq(MovieBooking::getTime, movieBooking.getTime());

        MovieBooking bookingDB = baseMapper.selectOne(queryWrapper);

        return bookingDB;
    }
}