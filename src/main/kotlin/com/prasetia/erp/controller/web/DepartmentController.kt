package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.pojo.department.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
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

    @RequestMapping("/budget_department/{periode}")
    fun periodeDepartment(model: Model,@PathVariable("periode") periode:String):String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL+"api/department_year/$periode")
        val departmentPeriodeTypeDataList:List<DepartmentYearData> = objectMapper.readValue(url)
        model.addAttribute("departmentPeriodeTypeDataList",departmentPeriodeTypeDataList)
        model.addAttribute("departmentPeriodeTypeDataListGraph", departmentPeriodeTypeDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("periode",periode)
        return "department/departmen_by_periode"
    }
    @RequestMapping("/budget_department/{periode}/{department_id}")
    fun detailDepartment(model: Model,@PathVariable("periode")periode: Int,
                         @PathVariable("department_id")department_id:Int):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/department_budget/%d/%d".format(periode,department_id))
        val departmentDetailDataList:List<DepartmentBudgetYearData> = objectMapper.readValue(url)
        val totalDepartment = getTotalDepartmentCustomer(departmentDetailDataList)
        val totalPercentRealisasiBudget = getDepartmentPrecent(totalDepartment[1], totalDepartment[0])

//        var Temp = 1281

        model.addAttribute("departmentDetailDataList",departmentDetailDataList)
        model.addAttribute("total", getTotalDepartmentCustomer(departmentDetailDataList))
        model.addAttribute("totalPercentRealisasiBudget", totalPercentRealisasiBudget)
        model.addAttribute("department_id",department_id)
        model.addAttribute("periode",periode)
//        model.addAttribute("Temp",Temp)
        return "department/detail_department"
    }


    fun getTotalBudget(data:List<DepartmentBudgetYearData>, type:String): Long{
        var total: Long = 0
        data.forEach {
            when (type){
                "total_budget" -> it.nilai_budget?.let { total = total.plus(it) }
                "total_realisasi" -> it.realisasi_budget?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getDepartmentPrecent(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalDepartmentCustomer(data:List<DepartmentBudgetYearData>) = longArrayOf(
            getTotalBudget(data, "total_budget"),
            getTotalBudget(data, "total_realisasi")
    )



}
