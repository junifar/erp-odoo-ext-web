package com.prasetia.erp.controller.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller("Revenue Web Controller")
class RevenueController{
    @RequestMapping("/revenue")
    fun index(model: Model):String{
        return "revenue/index"
    }

    @RequestMapping("/revenue/{tahun}")
    fun revenueByYear(model: Model, @PathVariable("tahun") tahun:String):String{
        return "revenue/revenue_by_year"
    }

    @RequestMapping("/revenue/project_type/{tahun}/{project_type}")
    fun revenueByYearType(model: Model,
                          @PathVariable("tahun") tahun: String,
                          @PathVariable("project_type") project_type:String):String{
        return "revenue/revenue_by_year_project_type"
    }

    @RequestMapping("/revenue/customer/{tahun}/{customer_id}")
    fun revenueByYearCustomer(model: Model,
                              @PathVariable("tahun") tahun: String,
                              @PathVariable("customer_id") customer_id:String):String{
        return "revenue/revenue_by_year_customer"
    }
}
