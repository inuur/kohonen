package com.coursework.kohonen.runner;

import com.coursework.kohonen.util.SomHH;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StudyRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
            SomHH knet = new SomHH();
            knet.readCSV();
            knet.main1();
    }
}
