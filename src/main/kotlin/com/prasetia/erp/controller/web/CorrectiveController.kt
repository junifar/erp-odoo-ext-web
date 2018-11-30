package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.controller.web.xls.corrective.XlsCorrective
import com.prasetia.erp.pojo.corrective.*
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
        val totalCorrectiveName = getTotal(correctiveSummaryDataList)
        val totalPercentPenagihan = TotalPercent(totalCorrectiveName[1], totalCorrectiveName[0])
        val totalPercentBudget = TotalPercent(totalCorrectiveName[3], totalCorrectiveName[2])
        val totalPercentLabaRugi= TotalPercent(totalCorrectiveName[3], totalCorrectiveName[1])

        model.addAttribute("correctiveSummaryDataList", correctiveSummaryDataList.sortedByDescending{it.year_project})
        model.addAttribute("correctiveSummaryDataListGraph", correctiveSummaryDataList.sortedByDescending{it.year_project}.take(5))
        model.addAttribute("totalCorrective",getTotal(correctiveSummaryDataList))
        model.addAttribute("totalPercentPenagihan",totalPercentPenagihan)
        model.addAttribute("totalPercentBudget",totalPercentBudget)
        model.addAttribute("totalPercentLabaRugi",totalPercentLabaRugi)
        return "corrective/summary_corrective"
    }

    fun getTotalCorrective(data: List<CorrectiveCustomerSummaryData>, type: String):Double{
        var total =0.0
        data.forEach {
            when(type){
                "totalPO" ->it.nilai_po?.let { total = total.plus(it) }
                "totalPenagihan" ->it.nilai_inv?.let { total = total.plus(it) }
                "totalBudget" ->it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasi" ->it.realisasi_budget?.let { total = total.plus(it) }
                "totalLabaRugi" ->it.profit?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotal(data:List<CorrectiveCustomerSummaryData>) = doubleArrayOf(
            getTotalCorrective(data,"totalPO"),
            getTotalCorrective(data,"totalPenagihan"),
            getTotalCorrective(data,"totalBudget"),
            getTotalCorrective(data,"totalRealisasi"),
            getTotalCorrective(data,"totalLabaRugi")
    )

    fun TotalPercent(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/corrective1")
    fun indexCorrective1(model: Model): String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_summary")
        val correctiveSummaryDataList: List<CorrectiveCustomerSummaryData> = objectMapper.readValue(url)
        model.addAttribute("correctiveSummaryDataList", correctiveSummaryDataList)
        return "corrective/index2"
    }

    fun getTotalCorrectiveCustomer(data:List<CorrectiveYearData>, type:String): Double{
        var total= 0.0
        data.forEach {
            when (type){
                "total_site" -> it.jumlah_site?.let { total = total.plus(it) }
                "total_po" -> it.nilai_po?.let { total = total.plus(it) }
                "total_inv" -> it.nilai_inv?.let { total = total.plus(it) }
                "total_budget" -> it.nilai_budget?.let { total = total.plus(it) }
                "total_realisasi" -> it.realisasi_budget?.let { total = total.plus(it) }
                "total_laba_rugi" -> it.profit?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCorrectiveCustomer(data: List<CorrectiveYearData>) = doubleArrayOf(
            getTotalCorrectiveCustomer(data, "total_site"),
            getTotalCorrectiveCustomer(data, "total_po"),
            getTotalCorrectiveCustomer(data, "total_inv"),
            getTotalCorrectiveCustomer(data, "total_budget"),
            getTotalCorrectiveCustomer(data, "total_realisasi"),
            getTotalCorrectiveCustomer(data, "total_laba_rugi")
    )

    fun getDivisionPrecent(data:Double, data1: Double) = doubleArrayOf(
        if (data1 > 0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/corrective/{tahun}")
    fun indexCorrectiveByYear(model:Model, @PathVariable("tahun") tahun:String): String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_year/$tahun")
        val correctiveDataList: List<CorrectiveYearData> = objectMapper.readValue(url)
        val totalCorrectiveCustomer = getTotalCorrectiveCustomer(correctiveDataList)
        val totalPercentInvPO = getDivisionPrecent(totalCorrectiveCustomer[2], totalCorrectiveCustomer[1])
        val totalPercentRealisasiBudget = getDivisionPrecent(totalCorrectiveCustomer[4], totalCorrectiveCustomer[3])
        val totalPercentLabaRugiPO = getDivisionPrecent(totalCorrectiveCustomer[5], totalCorrectiveCustomer[1])
        model.addAttribute("total", getTotalCorrectiveCustomer(correctiveDataList))
        model.addAttribute("tahun", tahun)
        model.addAttribute("totalPercentInvPO", totalPercentInvPO)
        model.addAttribute("totalPercentRealisasiBudget", totalPercentRealisasiBudget)
        model.addAttribute("totalPercentLabaRugiPO", totalPercentLabaRugiPO)
        model.addAttribute("correctiveDataList", correctiveDataList.sortedByDescending { it.nilai_po })
        model.addAttribute("correctiveDataListGraph", correctiveDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("correctiveDataListGraph2", correctiveDataList.sortedByDescending { it.nilai_budget }.take(5))
        return "corrective/index"
    }

    @RequestMapping("/corrective/{customer_id}/{tahun}")
    fun indexCorrectiveByYearCustomer(model:Model, @PathVariable("tahun") tahun:String, @PathVariable("customer_id") customer_id:Int): String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_detail/$customer_id/$tahun")
        val correctiveDataCustomerList: List<CorrectiveDetailYearCustomerData> = objectMapper.readValue(url)

        var correctiveDataSOList: List<CorrectiveSOData>? = mutableListOf()
        correctiveDataCustomerList.forEach{
            correctiveDataSOList = it.sales_order
        }
        var correctiveDataBudgetList: List<CorrectiveBudgetData>? = mutableListOf()
        correctiveDataCustomerList.forEach{
            correctiveDataBudgetList = it.budget
        }
        val totalDetailCorrective = getTotalCorrectiveBudget(correctiveDataCustomerList)
        val totalPercentDetailCorrective = getTotalPercent1(totalDetailCorrective[1], totalDetailCorrective[0])
        val totalDetailCorrectiveBudget = getTotalCorrectivedetail1(correctiveDataCustomerList)
        val totalPercentBudget = getTotalPercent2(totalDetailCorrectiveBudget[1],totalDetailCorrectiveBudget[0])

        model.addAttribute("correctiveDataList", correctiveDataCustomerList)
        model.addAttribute("correctiveDataSOList", correctiveDataSOList?.sortedByDescending { it.nilai_po })
        model.addAttribute("correctiveDataBudgetList", correctiveDataBudgetList?.sortedByDescending { it.nilai_budget })
        model.addAttribute("correctiveDataListGraph1", correctiveDataSOList?.sortedByDescending { it.nilai_po }?.take(5))
        model.addAttribute("correctiveDataListGraph2", correctiveDataBudgetList?.sortedByDescending { it.nilai_budget }?.take(5))
        model.addAttribute("total",getTotalCorrectiveBudget(correctiveDataCustomerList))
        model.addAttribute("percent",totalPercentDetailCorrective)
        model.addAttribute("totalBudget",getTotalCorrectivedetail1(correctiveDataCustomerList))
        model.addAttribute("percentBudget",totalPercentBudget)
        return "corrective/detailcustomeryear"
    }

    fun getTotalCorrectiveBudget(data:List<CorrectiveDetailYearCustomerData>,type: String): Double {
        var total =0.0
        data.forEach{
            when(type){
                "totalPo" -> it.nilai_po?.let { total = total.plus(it) }
                "totalInv" -> it.nilai_inv?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCorrectiveBudget(data:List<CorrectiveDetailYearCustomerData>) = doubleArrayOf(
            getTotalCorrectiveBudget(data, "totalPo"),
            getTotalCorrectiveBudget(data, "totalInv")
    )

    fun getTotalPercent1(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0) data * 100 / data1 else (0.0)
    )

    fun getTotalCorrectiveDetail(data:List<CorrectiveDetailYearCustomerData>,type: String): Double {
        var total =0.0
        data.forEach{
            d1->d1.budget?.forEach {
            when(type){
                "totalBudget" -> it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget?.let { total = total.plus(it) }
            }
        }
        }
        return total
    }

    fun getTotalCorrectivedetail1(data:List<CorrectiveDetailYearCustomerData>) = doubleArrayOf(
            getTotalCorrectiveDetail(data, "totalBudget"),
            getTotalCorrectiveDetail(data, "totalRealisasi")
    )

    fun getTotalPercent2(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/corrective/xls/{tahun}")
    fun downloadCorrective(model: Model, response:HttpServletResponse, @PathVariable("tahun") tahun:String){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition", "attachment; filename=\"budget-corrective-file-$tahun.xls\"")
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + "api/corrective_year/$tahun")
        val correctiveYearDataList: List<CorrectiveYearData> = objectMapper.readValue(url)
        XlsCorrective(response, tahun, correctiveYearDataList)
    }
}