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

//        val totalNilaiBudget = getTotalNilaiBudget(departmentDetailDataList)


        model.addAttribute("departmentDetailDataList",departmentDetailDataList)
//        model.addAttribute("totalNilaiBudget",totalNilaiBudget)
        model.addAttribute("department_id",department_id)
        model.addAttribute("periode",periode)
        return "department/detail_department"
    }

//    fun getTotalBudget(data:List<DepartmentBudgetYearData>):Long{
//        var total:Long = 0
//        data.forEach {
//            items->
//            items.department_budget?.forEach {
//                item_details ->
//                    item_details.budget_detail?.forEach {
//
//                    }
//            }
//        }
//    }

//    fun getTotalNilaiBudget(data: List<DepartmentBudgetYearData>)=longArrayOf(
//            getTotalBudget(data),
//    )

}
