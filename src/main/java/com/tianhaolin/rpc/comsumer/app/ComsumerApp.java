package com.tianhaolin.rpc.comsumer.app;

import com.tianhaolin.rpc.comsumer.service.CalculatorRemoteImpl;
import com.tianhaolin.rpc.provider.service.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: thl
 */
public class ComsumerApp {
    private static Logger log = LoggerFactory.getLogger(ComsumerApp.class);

    public static void main(String[] args) {
        Calculator calculator = new CalculatorRemoteImpl();
        int result = calculator.add(1, 2);
        log.info("result is {}", result);
    }
}
