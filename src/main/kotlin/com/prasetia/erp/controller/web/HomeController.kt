package com.prasetia.erp.controller.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController{
    @RequestMapping("")
    fun home(model:Model): String{
//        return "home"
        return "prasetia_home"
    }

    @RequestMapping("/sample")
    fun sample(model:Model): String{
        return "sample/index"
    }
}
