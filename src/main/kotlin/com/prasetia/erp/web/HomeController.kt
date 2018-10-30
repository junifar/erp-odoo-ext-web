package com.prasetia.erp.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController{
    @RequestMapping("")
    fun home(model:Model): String{
        return "home"
    }

    @RequestMapping("/sample")
    fun sample(model:Model): String{
        return "sample/index"
    }
}