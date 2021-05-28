/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.service;

import javax.ws.rs.*;
import  javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.apache.log4j.Logger;
/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 0.1-SNAPSHOT
 */
@Path("/service")
public class HelloService {
    final static Logger logger = Logger.getLogger(HelloService.class);

    @GET
    @Path("/")
    public String get(@HeaderParam("X-JWT-Assertion") String jwttoken) {
        logger.info("GET Method Invoked");
        logger.info(jwttoken);

        String jwtp = jwttoken.trim().split("\\.")[0].trim();
        byte[] encodedHelloBytesp = DatatypeConverter.parseBase64Binary(jwtp);
        String decodedp = new String(encodedHelloBytesp, StandardCharsets.UTF_8) ;
        logger.info("Header "+decodedp);


        String jwtheader = jwttoken.trim().split("\\.")[1].trim();
        byte[] encodedHelloBytes = DatatypeConverter.parseBase64Binary(jwtheader);
        String decodedHeader = new String(encodedHelloBytes, StandardCharsets.UTF_8) ;
        logger.info("Payload "+ decodedHeader);


        Object obj=JSONValue.parse(decodedHeader);
        JSONObject jsonObj = (JSONObject) obj;

        long exp = (long) jsonObj.get("exp");

        Date expDate = new Date(exp*1000);

        logger.info("Exp, "+ expDate);

        Date currentDate = new Date();

        logger.info("Current Time, "+ currentDate);

        logger.info("Compare times Exp < currentTime, "+ (currentDate.compareTo(expDate) > 0));

        return "Hello from WSO2 MSF4J App";
    }

    @POST
    @Path("/")
    public void post() {
        // TODO: Implementation for HTTP POST request
        System.out.println("POST invoked");
    }

    @PUT
    @Path("/")
    public void put() {
        // TODO: Implementation for HTTP PUT request
        System.out.println("PUT invoked");
    }

    @DELETE
    @Path("/")
    public void delete() {
        // TODO: Implementation for HTTP DELETE request
        System.out.println("DELETE invoked");
    }
}
