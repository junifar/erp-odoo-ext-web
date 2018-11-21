package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.controller.web.xls.cme.XlsCme
import com.prasetia.erp.pojo.cme.CmeSummaryYearData
import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeCustData
import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL
import javax.servlet.http.HttpServletResponse

@Controller("CME Web Controller")
class CmeController{

    @RequestMapping("/project")
    fun indexCME(model: Model): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year")
        val cmeSummaryYearDataList: List<CmeSummaryYearData> = objectMapper.readValue(url)
        model.addAttribute("cmeSummaryYearDataList", cmeSummaryYearDataList)
        return "project/index"
    }

    @RequestMapping("/project/{tahun}")
    fun yearCME(model: Model, @PathVariable("tahun") tahun: String): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun")
        val cmeSummaryYearProjectTypeDataList: List<CmeSummaryYearProjectTypeData> = objectMapper.readValue(url)
        model.addAttribute("cmeSummaryYearProjectTypeDataList", cmeSummaryYearProjectTypeDataList)
        model.addAttribute("year_project", tahun)
        return "project/project_by_year"
    }

    @RequestMapping("/project/{tahun}/{type_id}")
    fun yearCustomerCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearProjectTypeCustDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        model.addAttribute("cmeSummaryYearProjectTypeCustDataList", cmeSummaryYearProjectTypeCustDataList)
        model.addAttribute("year_project", tahun)
        return "project/project_by_year_customer"
    }

    @RequestMapping("/project/{tahun}/{type_id}/{customer_id}")
    fun yearCustomerDetailCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int, @PathVariable("customer_id") customer_id: Long): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearTypeCustDetailDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        model.addAttribute("cmeSummaryYearTypeCustDetailDataList", cmeSummaryYearTypeCustDetailDataList.filter { it.customer_id == customer_id })
        return "project/project_by_year_customer_detail"
    }

    @RequestMapping("/project/download/{tahun}/{type_id}")
    fun downloadCME(model:Model, response:HttpServletResponse, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition", "attachment; filename=\"budget-cme-file-$tahun-$type_id.xls\"")

        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearProjectTypeCustDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        XlsCme(response, tahun, type_id, cmeSummaryYearProjectTypeCustDataList)
    }
}