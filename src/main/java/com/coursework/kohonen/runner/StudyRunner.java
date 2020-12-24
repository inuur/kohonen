package com.coursework.kohonen.runner;

import com.coursework.kohonen.util.kohonen.KohonenMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StudyRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        KohonenMap kohonenMap = new KohonenMap();
        kohonenMap.studyMultithreading(1000, true);
//        kohonenMap.handleInput();
        kohonenMap.countColors();
    }
}
