package com.prasetia.erp.controller.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller("Project Web Controller")
class ProjectController{

    @RequestMapping("/project/recap")
    fun index(model:Model):String{
        return "project_summary/index"
    }
}
