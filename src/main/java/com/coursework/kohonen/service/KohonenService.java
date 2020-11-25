package com.coursework.kohonen.service;

import com.coursework.kohonen.util.Node;
import com.coursework.kohonen.util.SomHH;
import org.springframework.stereotype.Service;

@Service
public class KohonenService {

    private int iteration = 0;

    public Node[] getNodes() {
        return SomHH.nodes;
    }

    public void setIteration(int iteration) {
        System.out.println(iteration);
    }

    public int getIteration() {
        return SomHH.currentIteration;
    }
}
