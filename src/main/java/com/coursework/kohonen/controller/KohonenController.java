package com.coursework.kohonen.controller;

import com.coursework.kohonen.util.kohonen.KohonenMap;
import com.coursework.kohonen.util.kohonen.MapNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/map")
public class KohonenController {


    @GetMapping
    public List<MapNode> map() {
        return KohonenMap.mapNodes;
    }

    @GetMapping("/iteration")
    public int iteration() {
        return KohonenMap.currentIteration;
    }
}
