package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.controller.web.xls.department.XlsDepartment
import com.prasetia.erp.pojo.department.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL
import javax.servlet.http.HttpServletResponse

@Controller("Budget Department Controller")
class DepartmentController{


    @RequestMapping("/budget_department")
    fun indexDepartment(model: Model):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/department_summary")
        val departmentSummaryDataList: List<DepartmentSummary> = objectMapper.readValue(url)
        model.addAttribute("departmentSummaryDataList", departmentSummaryDataList.sortedByDescending { it.periode })
        return "department/index"
    }

    @RequestMapping("/budget_department/{periode}")
    fun periodeDepartment(model: Model,@PathVariable("periode") periode:String):String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL+"api/department_year/$periode")
        val departmentPeriodeTypeDataList:List<DepartmentYearData> = objectMapper.readValue(url)
        val totalDepartmentName = getTotalDepartment(departmentPeriodeTypeDataList)
        val totalPercentDepartment = getTotalPercent(totalDepartmentName[1], totalDepartmentName[0])

        model.addAttribute("departmentPeriodeTypeDataList",departmentPeriodeTypeDataList.sortedByDescending { it.nilai_budget })
        model.addAttribute("departmentPeriodeTypeDataListGraph", departmentPeriodeTypeDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("totalDepartment",getTotalDepartment(departmentPeriodeTypeDataList))
        model.addAttribute("totalPercent",totalPercentDepartment)
        model.addAttribute("periode",periode)
        return "department/departmen_by_periode"
    }

    fun getTotalDepartmentBudget(data:List<DepartmentYearData>,type: String): Long {
        var total:Long =0
        data.forEach {
            when(type){
                "totalBudget" -> it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalDepartment(data:List<DepartmentYearData>) = longArrayOf(
            getTotalDepartmentBudget(data, "totalBudget"),
            getTotalDepartmentBudget(data, "totalRealisasi")
    )

    fun getTotalPercent(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )


    @RequestMapping("/budget_department/{periode}/{department_id}")
    fun detailDepartment(model: Model,@PathVariable("periode")periode: Int,
                         @PathVariable("department_id")department_id:Int):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/department_budget/%d/%d".format(periode,department_id))
        val departmentDetailDataList:List<DepartmentBudgetYearData> = objectMapper.readValue(url)
        val totalDepartment = getTotalDepartmentCustomer(departmentDetailDataList)
        val totalPercentRealisasiBudget = getDepartmentPrecent(totalDepartment[1], totalDepartment[0])


        model.addAttribute("departmentDetailDataList",departmentDetailDataList)
        model.addAttribute("total", getTotalDepartmentCustomer(departmentDetailDataList))
//        model.addAttribute("total_realisasi_detail",getTotalRealisasiData(departmentDetailDataList))
        model.addAttribute("totalPercentRealisasiBudget", totalPercentRealisasiBudget)
        model.addAttribute("department_id",department_id)
        model.addAttribute("periode",periode)
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

    fun getTotalRealisasiData(data:List<DepartmentBudgetYearData>) = (
            getTotalRealisasi(data, "total_realisasi_detail")
            )

    fun getDepartmentPrecent(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalDepartmentCustomer(data:List<DepartmentBudgetYearData>) = longArrayOf(
            getTotalBudget(data, "total_budget"),
            getTotalBudget(data, "total_realisasi")
    )

    @RequestMapping("/budget_department/{periode}/{department_id}/{line_id}")
    fun detailRealisasi(model: Model,@PathVariable("periode")periode: Int,
                        @PathVariable("department_id")department_id: Int,
                        @PathVariable("line_id")line_id:Long): String {
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/department_budget/%d/%d".format(periode,department_id))
        val departmentDetailDataList:List<DepartmentBudgetYearData> = objectMapper.readValue(url)
        val totalDepartment = getTotalDepartmentCustomer(departmentDetailDataList)
        val totalPercentRealisasiBudget = getDepartmentPrecent(totalDepartment[1], totalDepartment[0])

        model.addAttribute("departmentDetailDataList",departmentDetailDataListFilter(departmentDetailDataList,line_id))
        model.addAttribute("total_realisasi_detail",getTotalRealisasiData(departmentDetailDataList))
        model.addAttribute("total", getTotalDepartmentCustomer(departmentDetailDataList))
        model.addAttribute("totalPercentRealisasiBudget", totalPercentRealisasiBudget)
        return "department/detail_department"
    }

    fun departmentDetailDataListFilter(data:List<DepartmentBudgetYearData>, line_id: Long):List<DepartmentBudgetYearData>{
        var departmentDetailDataList:List<DepartmentBudgetYearData> = data
        var total:Long=0
        departmentDetailDataList.forEach {
            budgetYearData ->
            budgetYearData.department_budget?.forEach {
                department_budget->
                department_budget.budget_detail?.forEach {
                    budget_detail->
                    if (budget_detail.line_id != line_id ){
                        budget_detail.realisasi = null
                    }
                }
            }
        }
        return departmentDetailDataList
    }


    fun getTotalRealisasi(data: List<DepartmentBudgetYearData>,type: String):Long {
        var total:Long=0
        data.forEach {
            budget_data ->
            budget_data.department_budget?.forEach {
                budget_detail ->
                budget_detail.budget_detail?.forEach {
                    budget_realisasi ->
                    budget_realisasi.realisasi?.forEach {
                        when(type){
                            "total_realisasi_detail" -> it.budget_realisasi?.let { total = total.plus(it) }
                        }
                    }
                }
            }

            }
            return  total
        }

    @RequestMapping("/budget_department/xls/{periode}/{department_id}")
    fun downloadDepartment(model: Model,response:HttpServletResponse,
                           @PathVariable("periode")periode:String,
                           @PathVariable("department_id")department_id: String){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition","attachment;filename=\"budget-department-file-$periode.xls\"")

        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL+"api/department_budget/$periode/$department_id")
        val departmentDetailDataList:List<DepartmentBudgetYearData> = objectMapper.readValue(url)


        XlsDepartment(response, periode, department_id, departmentDetailDataList)
        }
    }
