package com.atguigu.movieAI.service;

import com.atguigu.movieAI.entity.MovieBooking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieBookingServiceTest {

    @Autowired
    private MovieBookingService movieBookingService;

    @Test
    void testGetOne() {

        MovieBooking movieBooking = new MovieBooking();
        movieBooking.setUsername("张三");
        movieBooking.setPhone("13800000000");
        movieBooking.setMovieName("星际穿越");
        movieBooking.setCinemaName("八千代影城");
        movieBooking.setDate("2026-03-20");
        movieBooking.setTime("19:30");

        MovieBooking bookingDB = movieBookingService.getOne(movieBooking);

        System.out.println(bookingDB);
    }

    @Test
    void testSave() {

        MovieBooking movieBooking = new MovieBooking();

        movieBooking.setUsername("张三");
        movieBooking.setPhone("13800000000");
        movieBooking.setMovieName("星际穿越");
        movieBooking.setCinemaName("八千代影城");
        movieBooking.setDate("2026-03-20");
        movieBooking.setTime("19:30");
        movieBooking.setHall("3号厅");

        movieBookingService.save(movieBooking);
    }

    @Test
    void testRemoveById() {

        movieBookingService.removeById(3L);

    }

}