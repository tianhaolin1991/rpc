package com.tianhaolin.rpc.comsumer.service;


import com.tianhaolin.rpc.provider.service.Calculator;
import com.tianhaolin.rpc.reuqest.CalculateRpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author: thl
 */
public class CalculatorRemoteImpl implements Calculator {
    public static final int PORT = 9090;
    private static final Logger log = LoggerFactory.getLogger(CalculatorRemoteImpl.class);

    public int add(int a, int b) {
        List<String> providers = lookupProviders("Calculator.add");
        try{
            Socket socket = new Socket(providers.get(0), 9090);
            CalculateRpcRequest calculateRpcRequest = generateRequest(a, b);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(generateRequest(a,b));

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object response = objectInputStream.readObject();
            log.info("response is {}",response);

            if(response instanceof Integer){
                return (Integer)response;
            }else{
                throw new InternalError("返回值类型错误");
            }
        }catch(Exception e){
            log.error("{}",e.getMessage());
            throw new InternalError(e.getMessage());
        }
    }

    private CalculateRpcRequest generateRequest(int a,int b) {
        CalculateRpcRequest calculateRpcRequest = new CalculateRpcRequest();
        calculateRpcRequest.setA(a);
        calculateRpcRequest.setB(b);
        calculateRpcRequest.setMethod("add");
        return calculateRpcRequest;
    }

    private List<String> lookupProviders(String name) {
        List<String> strings = new ArrayList();
        strings.add("127.0.0.1");
        return strings;
    }

}
