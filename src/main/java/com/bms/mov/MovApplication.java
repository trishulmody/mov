package com.bms.mov;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MovApplication implements CommandLineRunner
{

    private static Logger LOG = LoggerFactory.getLogger(MovApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(MovApplication.class, args);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(()->{
            System.out.println("Executing  now " + LocalDateTime.now());
            try
            {
                Bms.book();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.MINUTES);
    }

    @Override
    public void run(String... args)
    {
        LOG.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i)
        {
            LOG.info("args[{}]: {}", i, args[i]);
        }
    }
    //comment
}
