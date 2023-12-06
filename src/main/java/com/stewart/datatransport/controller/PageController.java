package com.stewart.datatransport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 *
 * @author stewart
 * @date 2023/12/5
 */
@Controller
public class PageController {

    /**
     * page controller
     *
     * @return index.html
     */
    @RequestMapping("/page/{route}")
    public String page(@PathVariable String route){
        return "page/" + route;
    }
}
