package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.controller.web.xls.cme.XlsCme
import com.prasetia.erp.pojo.cme.CmeSummaryYearData
import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeCustData
import com.prasetia.erp.pojo.cme.CmeSummaryYearProjectTypeData
import com.prasetia.erp.pojo.cme.CmeYearProjectTypeCustProjectDetailData
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
        val totalCmeData = getTotal(cmeSummaryYearDataList)
        model.addAttribute("cmeSummaryYearDataList", cmeSummaryYearDataList)
        model.addAttribute("cmeSummaryYearDataList1", cmeSummaryYearDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearDataList2", cmeSummaryYearDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("total",getTotal(cmeSummaryYearDataList))
        return "project/index"
    }

    fun getTotalCme(data: List<CmeSummaryYearData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalSiteCancel" ->it.site_cancel?.let { total = total.plus(it) }
                "totalEstimasiPo" ->it.estimate_po?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
                "totalInv" ->it.nilai_invoice?.let { total = total.plus(it) }
                "totalBudget" ->it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasi" ->it.realisasi_budget?.let { total = total.plus(it) }
                "totalLabaRugi" ->it.profit_loss?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotal(data:List<CmeSummaryYearData>) = longArrayOf(
            getTotalCme(data,"totalSite"),
            getTotalCme(data,"totalSiteCancel"),
            getTotalCme(data,"totalEstimasiPo"),
            getTotalCme(data,"totalPo"),
            getTotalCme(data,"totalInv"),
            getTotalCme(data,"totalBudget"),
            getTotalCme(data,"totalRealisasi"),
            getTotalCme(data,"totalLabaRugi")
    )

    @RequestMapping("/project/{tahun}")
    fun yearCME(model: Model, @PathVariable("tahun") tahun: String): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun")
        val cmeSummaryYearProjectTypeDataList: List<CmeSummaryYearProjectTypeData> = objectMapper.readValue(url)
        val totalCmeName = getTotalCmeYear(cmeSummaryYearProjectTypeDataList)
        val totalPercentPo = getPrecent(totalCmeName[4], totalCmeName[3])
        val totalCmeBudget = getTotalCmeYearBudget(cmeSummaryYearProjectTypeDataList)
        val totalPercentBudget = getPrecentBudget(totalCmeBudget[3], totalCmeBudget[2])
        val totalCmeLabaRugi = getTotalCmeLabaRugi(cmeSummaryYearProjectTypeDataList)
        val totalPercentLabaRugi = getPrecentLabaRugi(totalCmeLabaRugi[2], totalCmeLabaRugi[3])
        val totalPercentCmePo = getPrecentLabaRugi(totalCmeLabaRugi[2], totalCmeLabaRugi[4])

        model.addAttribute("cmeSummaryYearProjectTypeDataList", cmeSummaryYearProjectTypeDataList)
        model.addAttribute("cmeSummaryYearProjectTypeDataList1", cmeSummaryYearProjectTypeDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearProjectTypeDataList2", cmeSummaryYearProjectTypeDataList.sortedByDescending { it.realisasi_budget }.take(5))
        model.addAttribute("total",getTotalCmeYear(cmeSummaryYearProjectTypeDataList))
        model.addAttribute("totalPercent",totalPercentPo)
        model.addAttribute("year_project", tahun)
        model.addAttribute("totalBudget",getTotalCmeYearBudget(cmeSummaryYearProjectTypeDataList))
        model.addAttribute("totalPercentBudget",totalPercentBudget)
        model.addAttribute("totalLabaRugi",getTotalCmeLabaRugi(cmeSummaryYearProjectTypeDataList))
        model.addAttribute("totalPercentLabaRugiRealisasi",totalPercentLabaRugi)
        model.addAttribute("totalPercentLabaRugiPo",totalPercentCmePo)
        return "project/project_by_year"
    }

    fun getTotalCmeYear(data: List<CmeSummaryYearProjectTypeData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalSiteCancel" ->it.site_cancel?.let { total = total.plus(it) }
                "totalEstimasiPo" ->it.estimate_po?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
                "totalInv" ->it.nilai_invoice?.let { total = total.plus(it) }
                "totalBelumTertagih" ->it.remaining_invoice?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeYear(data:List<CmeSummaryYearProjectTypeData>) = longArrayOf(
            getTotalCmeYear(data,"totalSite"),
            getTotalCmeYear(data,"totalSiteCancel"),
            getTotalCmeYear(data,"totalEstimasiPo"),
            getTotalCmeYear(data,"totalPo"),
            getTotalCmeYear(data,"totalInv"),
            getTotalCmeYear(data,"totalBelumTertagih")
    )

    fun getPrecent(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalCmeBudget(data: List<CmeSummaryYearProjectTypeData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalSiteCancel" ->it.site_cancel?.let { total = total.plus(it) }
                "totalBudget" ->it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasiBudget" ->it.realisasi_budget?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeYearBudget(data:List<CmeSummaryYearProjectTypeData>) = longArrayOf(
            getTotalCmeBudget(data,"totalSite"),
            getTotalCmeBudget(data,"totalSiteCancel"),
            getTotalCmeBudget(data,"totalBudget"),
            getTotalCmeBudget(data,"totalRealisasiBudget"),
            getTotalCmeBudget(data,"totalPo")
    )

    fun getPrecentBudget(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalCmeLabaRugi(data: List<CmeSummaryYearProjectTypeData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalSiteCancel" ->it.site_cancel?.let { total = total.plus(it) }
                "totalLabaRugi" ->it.profit_loss?.let { total = total.plus(it) }
                "totalRealisasi" ->it.realisasi_budget?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeLabaRugi(data:List<CmeSummaryYearProjectTypeData>) = longArrayOf(
            getTotalCmeLabaRugi(data,"totalSite"),
            getTotalCmeLabaRugi(data,"totalSiteCancel"),
            getTotalCmeLabaRugi(data,"totalLabaRugi"),
            getTotalCmeLabaRugi(data,"totalRealisasi"),
            getTotalCmeLabaRugi(data,"totalPo")
    )

    fun getPrecentLabaRugi(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    @RequestMapping("/project/{tahun}/{type_id}")
    fun yearCustomerCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearProjectTypeCustDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        val totalPoInv = getTotalPoInv(cmeSummaryYearProjectTypeCustDataList)
        val totalPercentPoInv = getPrecentPoInv(totalPoInv[3],totalPoInv[2])
        val totalBudgetRealisasi = getTotalBudgetRealisasi(cmeSummaryYearProjectTypeCustDataList)
        val totalPercent = getPrecentBudgetRealisasi(totalBudgetRealisasi[2],totalBudgetRealisasi[1])
        val totalLabaRugi = getTotalCustomerPoInv(cmeSummaryYearProjectTypeCustDataList)
        val totalPercentProfitRealisasi = getPrecentCustomerPoInv(totalLabaRugi[1], totalLabaRugi[2])
        val totalPercentProfitPo = getPrecentCustomerPoInv(totalLabaRugi[1], totalLabaRugi[3])

        model.addAttribute("cmeSummaryYearProjectTypeCustDataList", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_po })
        model.addAttribute("cmeSummaryYearProjectTypeCustGraph", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearProjectTypeCustGraph2", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("total",getTotalCustomerPoInv(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentLabRugRealisasi",totalPercentProfitRealisasi)
        model.addAttribute("percentLabRugPo",totalPercentProfitPo)
        model.addAttribute("totalPoInv",getTotalPoInv(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentPoInv",totalPercentPoInv)
        model.addAttribute("totalBudgetRealisasi",getTotalBudgetRealisasi(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentBudgetRealisasi",totalPercent)
        model.addAttribute("year_project", tahun)
        return "project/project_by_year_customer"
    }

    fun getTotalPoInv(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalEstimasiPo" ->it.estimate_po?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
                "totalInv" ->it.nilai_invoice?.let { total = total.plus(it) }
                "totalBelumTertagih" ->it.remaining_invoice?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalPoInv(data:List<CmeSummaryYearProjectTypeCustData>) = longArrayOf(
            getTotalPoInv(data,"totalSite"),
            getTotalPoInv(data,"totalEstimasiPo"),
            getTotalPoInv(data,"totalPo"),
            getTotalPoInv(data,"totalInv"),
            getTotalPoInv(data,"totalBelumTertagih")
    )

    fun getPrecentPoInv(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalBudgetRealisasi(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalBudget" ->it.nilai_budget?.let { total = total.plus(it) }
                "totalRealisasi" ->it.realisasi_budget?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalBudgetRealisasi(data:List<CmeSummaryYearProjectTypeCustData>) = longArrayOf(
            getTotalBudgetRealisasi(data,"totalSite"),
            getTotalBudgetRealisasi(data,"totalBudget"),
            getTotalBudgetRealisasi(data,"totalRealisasi")
    )

    fun getPrecentBudgetRealisasi(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalCustomerPo(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Long{
        var total:Long =0
        data.forEach {
            when(type){
                "totalSite" ->it.jumlah_site?.let { total = total.plus(it) }
                "totalLabaRugi" ->it.profit_loss?.let { total = total.plus(it) }
                "totalRealisasi" ->it.realisasi_budget?.let { total = total.plus(it) }
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCustomerPoInv(data:List<CmeSummaryYearProjectTypeCustData>) = longArrayOf(
            getTotalCustomerPo(data,"totalSite"),
            getTotalCustomerPo(data,"totalLabaRugi"),
            getTotalCustomerPo(data,"totalRealisasi"),
            getTotalCustomerPo(data,"totalPo")
    )

    fun getPrecentCustomerPoInv(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    @RequestMapping("/project/{tahun}/{type_id}/{customer_id}")
    fun yearCustomerDetailCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int, @PathVariable("customer_id") customer_id: Long): String{
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearTypeCustDetailDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        var cmeYearTypeCustProject: List<CmeYearProjectTypeCustProjectDetailData>? = mutableListOf()
        cmeSummaryYearTypeCustDetailDataList.filter { it.customer_id == customer_id }.forEach{
            cmeYearTypeCustProject = it.project_list
            it.project_list?.forEach {
                it.percent_po = if (it.estimate_po > 0)((it.estimate_po - it.nilai_budget) * 100f) / it.estimate_po else 0f
                it.percent_labarugi = if (it.estimate_po > 0)((it.nilai_invoice - it.realisasi_budget) * 100f) / it.realisasi_budget else 0f
            }
        }

        val totalPoBudget = getTotalDetailPoBudget(cmeSummaryYearTypeCustDetailDataList)
        val totalPercentPoBudget = getPrecentDetailPoBudget(totalPoBudget[1],totalPoBudget[0])
        val totalGrossMargin = getGrossMargin(totalPoBudget[0],totalPoBudget[1])
        val totalInvLabRug = getTotalDetailInvLabRug(cmeSummaryYearTypeCustDetailDataList)
        val totalPercentInvLabRug  = getPrecentInvLabRug(totalInvLabRug[1],totalInvLabRug[0])
        val totalGrossMarginInvLabRug  = getGrossMarginInvLabRug(totalInvLabRug[0],totalInvLabRug[1])

        model.addAttribute("cmeSummaryYearTypeCustDetailDataList", cmeSummaryYearTypeCustDetailDataList.filter { it.customer_id == customer_id })
        model.addAttribute("cmeYearTypeCustProjectGraph1", cmeYearTypeCustProject?.take(5))
        model.addAttribute("totalPoBudget",getTotalDetailPoBudget(cmeSummaryYearTypeCustDetailDataList))
        model.addAttribute("percentPoBudget",totalPercentPoBudget)
        model.addAttribute("grossMargin",totalGrossMargin)
        model.addAttribute("totalInvLabRug",getTotalDetailInvLabRug(cmeSummaryYearTypeCustDetailDataList))
        model.addAttribute("percentInvLabRug",totalPercentInvLabRug)
        model.addAttribute("grossMarginInvLabRug",totalGrossMarginInvLabRug)
        model.addAttribute("tahun", tahun)
        return "project/project_by_year_customer_detail"
    }

    fun getTotalDetaiPoBudget(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Long{
        var total:Long =0
        data.forEach {
            d1->d1.project_list?.forEach {
                when(type){
                    "totalEstimasiPo" ->it.estimate_po?.let { total = total.plus(it) }
                    "totalBudget" ->it.nilai_budget?.let { total = total.plus(it) }
                }
        }
        }
        return total
    }

    fun getTotalDetailPoBudget(data:List<CmeSummaryYearProjectTypeCustData>) = longArrayOf(
            getTotalDetaiPoBudget(data,"totalEstimasiPo"),
            getTotalDetaiPoBudget(data,"totalBudget")
    )

    fun getGrossMargin(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() - data1 else(0).toFloat()
    )

    fun getPrecentDetailPoBudget(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

    fun getTotalDetaiInvLabRug(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Long{
        var total:Long =0
        data.forEach {
            d1->d1.project_list?.forEach {
            when(type){
                "totalPo" ->it.nilai_po?.let { total = total.plus(it) }
                "totalInvoice" ->it.nilai_invoice?.let { total = total.plus(it) }
                "totalRealisasiBudget" ->it.realisasi_budget?.let { total = total.plus(it) }
            }
        }
        }
        return total
    }

    fun getTotalDetailInvLabRug(data:List<CmeSummaryYearProjectTypeCustData>) = longArrayOf(
            getTotalDetaiInvLabRug(data,"totalPo"),
            getTotalDetaiInvLabRug(data,"totalInvoice"),
            getTotalDetaiInvLabRug(data,"totalRealisasiBudget")
    )

    fun getGrossMarginInvLabRug(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() - data1 else(0).toFloat()
    )

    fun getPrecentInvLabRug(data:Long, data1: Long) = floatArrayOf(
            if (data1 > 0) data.toFloat() * 100 / data1 else (0).toFloat()
    )

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