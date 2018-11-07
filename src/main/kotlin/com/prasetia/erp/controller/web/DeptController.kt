package com.prasetia.erp.controller.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller("Budget Department Controller")
class DeptController{

    @RequestMapping("/budget_department")
    fun indexDepartment(model: Model):String{
        return "department/index"
    }
}