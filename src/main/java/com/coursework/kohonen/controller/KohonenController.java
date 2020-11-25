package com.coursework.kohonen.controller;

import com.coursework.kohonen.service.KohonenService;
import com.coursework.kohonen.util.Node;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/map")
public class KohonenController {

    final KohonenService kohonenService;

    public KohonenController(KohonenService kohonenService) {
        this.kohonenService = kohonenService;
    }

    @GetMapping
    public Node[] map() {
        return kohonenService.getNodes();
    }

    @GetMapping("/iteration")
    public int iteration() {
        return kohonenService.getIteration();
    }
}
