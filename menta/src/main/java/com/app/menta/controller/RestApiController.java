package com.app.menta.controller;

import com.app.menta.dto.DbRequestDto;
import com.app.menta.dto.DbResponseDto;
import com.app.menta.service.DbTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * RestApiController
 *
 */
@RestController
@RequestMapping("api")
@Slf4j
public class RestApiController {

    @Autowired
    DbTestService dbTestService;


    /**
     * DB接続試験用
     *
     * @param request 要求電文
     */
    @RequestMapping(value = "/dbtest")
    @PostMapping
    public DbResponseDto dbtest(@RequestBody DbRequestDto request) {

        return dbTestService.excute(request);
    }


}

