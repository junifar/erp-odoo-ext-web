package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.pojo.department.DepartmentSummary
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL

@Controller("Budget Department Controller")
class DepartmentController{

    @RequestMapping("/budget_department")
    fun indexDepartment(model: Model):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/department_summary")
        val departmentSummaryDataList: List<DepartmentSummary> = objectMapper.readValue(url)
        model.addAttribute("departmentSummaryDataList", departmentSummaryDataList)
        return "department/index"
    }
}