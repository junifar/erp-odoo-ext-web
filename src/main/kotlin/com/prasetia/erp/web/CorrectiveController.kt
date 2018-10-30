package com.prasetia.erp.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.controller.web.xls.corrective.XlsCorrective
import com.prasetia.erp.pojo.corrective.CorrectiveCustomerSummaryData
import com.prasetia.erp.pojo.corrective.CorrectiveYearData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL
import javax.servlet.http.HttpServletResponse

@Controller("Corrective Web Controller")
class CorrectiveController{
    @RequestMapping("/corrective")
    fun indexCorrective(model: Model): String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_summary")
        val correctiveSummaryDataList: List<CorrectiveCustomerSummaryData> = objectMapper.readValue(url)
        model.addAttribute("correctiveSummaryDataList", correctiveSummaryDataList)
        return "corrective/index"
    }

    @RequestMapping("/corrective1")
    fun indexCorrective1(model: Model): String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_summary")
        val correctiveSummaryDataList: List<CorrectiveCustomerSummaryData> = objectMapper.readValue(url)
        model.addAttribute("correctiveSummaryDataList", correctiveSummaryDataList)
        return "corrective/index2"
    }


    @RequestMapping("/corrective/xls/{tahun}")
    fun downloadCorrective(model: Model, response:HttpServletResponse, @PathVariable("tahun") tahun:String){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition", "attachment; filename=\"budget-preventive-file-$tahun.xls\"")
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_year/$tahun")
        val correctiveYearDataList: List<CorrectiveYearData> = objectMapper.readValue(url)
        XlsCorrective(response, tahun, correctiveYearDataList)
    }
}