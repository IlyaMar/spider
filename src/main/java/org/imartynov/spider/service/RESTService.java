/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.imartynov.spider.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.imartynov.spider.domain.Account;
import org.imartynov.spider.ejb.AccountManager;

@Stateless
@Path("/")
public class RESTService {
    @EJB
    AccountManager accountManager;

    private JsonObjectBuilder buildT(String name) {
        return Json.createObjectBuilder()
                .add("name", name)
                .add("quantity", 3);
    }
    
    @GET
    @Path("/targets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> getTargets() {
        System.out.println("get targets");
        /*return Json.createArrayBuilder()
                .add(buildT("aaa"))
                .add(buildT("bbb"))
                .build();*/
        return accountManager.getAll();
    	
    }

    @POST
    @Path("/targets")
    @Consumes("application/json")
    public void addTarget(Account a) {
        System.out.println("add target");
        accountManager.add(a);
    }

    @GET
    @Path("/start")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> start() {
        System.out.println("start");
        accountManager.schedule("ivan");
        return accountManager.getAll();
    	
    }

}
