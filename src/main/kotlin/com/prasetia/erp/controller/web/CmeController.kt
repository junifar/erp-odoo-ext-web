package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.BUDGET_PROJECT_URL
import com.prasetia.erp.constant.GlobalConstant.Companion.REDIRECT_LOGIN_URL
import com.prasetia.erp.controller.web.xls.cme.XlsCme
import com.prasetia.erp.pojo.cme.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller("CME Web Controller")
class CmeController{

    @RequestMapping("/project")
    fun indexCME(model: Model, session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + BUDGET_PROJECT_URL + "api/project_summary_year")
        val cmeSummaryYearDataList: List<CmeSummaryYearData> = objectMapper.readValue(url)
        model.addAttribute("cmeSummaryYearDataList", cmeSummaryYearDataList)
        model.addAttribute("cmeSummaryYearDataList1", cmeSummaryYearDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearDataList2", cmeSummaryYearDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("total",getTotal(cmeSummaryYearDataList))
        return "project/index"
    }

    fun getTotalCme(data: List<CmeSummaryYearData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInv" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalLabaRugi" -> it.profit_loss.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotal(data:List<CmeSummaryYearData>) = doubleArrayOf(
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
    fun yearCME(model: Model, @PathVariable("tahun") tahun: String, session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
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

    @RequestMapping("/project_customer/{tahun}")
    fun yearCMECustomer(model: Model, @PathVariable("tahun") tahun: String, session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + BUDGET_PROJECT_URL + "api/project_summary_year_customer/$tahun")
        val cmeSummaryYearCustomerDataList: List<CmeSummaryYearCustomerData> = objectMapper.readValue(url)
        val totalCmeName = getTotalCmeCustomerYear(cmeSummaryYearCustomerDataList)
        val totalPercentPo = getPrecent(totalCmeName[4], totalCmeName[3])
        val totalCmeBudget = getTotalCmeYearCustomerBudget(cmeSummaryYearCustomerDataList)
        val totalPercentBudget = getPrecentBudget(totalCmeBudget[3], totalCmeBudget[2])
        val totalCmeLabaRugi = getTotalCmeCustomerLabaRugi(cmeSummaryYearCustomerDataList)
        val totalPercentLabaRugi = getPrecentLabaRugi(totalCmeLabaRugi[2], totalCmeLabaRugi[3])
        val totalPercentCmePo = getPrecentLabaRugi(totalCmeLabaRugi[2], totalCmeLabaRugi[4])

        model.addAttribute("cmeSummaryYearProjectTypeDataList", cmeSummaryYearCustomerDataList.sortedByDescending { it.nilai_po })
        model.addAttribute("cmeSummaryYearProjectTypeDataList1", cmeSummaryYearCustomerDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearProjectTypeDataList2", cmeSummaryYearCustomerDataList.sortedByDescending { it.realisasi_budget }.take(5))
        model.addAttribute("total",getTotalCmeCustomerYear(cmeSummaryYearCustomerDataList))
        model.addAttribute("totalPercent",totalPercentPo)
        model.addAttribute("year_project", tahun)
        model.addAttribute("totalBudget",getTotalCmeYearCustomerBudget(cmeSummaryYearCustomerDataList))
        model.addAttribute("totalPercentBudget",totalPercentBudget)
        model.addAttribute("totalLabaRugi",getTotalCmeCustomerLabaRugi(cmeSummaryYearCustomerDataList))
        model.addAttribute("totalPercentLabaRugiRealisasi",totalPercentLabaRugi)
        model.addAttribute("totalPercentLabaRugiPo",totalPercentCmePo)
        return "project/project_by_customer"
    }

    fun getTotalCmeYear(data: List<CmeSummaryYearProjectTypeData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInv" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalBelumTertagih" -> it.remaining_invoice.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeCustomerYear(data: List<CmeSummaryYearCustomerData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInv" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalBelumTertagih" -> it.remaining_invoice.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeCustomerYear(data:List<CmeSummaryYearCustomerData>) = doubleArrayOf(
            getTotalCmeCustomerYear(data,"totalSite"),
            getTotalCmeCustomerYear(data,"totalSiteCancel"),
            getTotalCmeCustomerYear(data,"totalEstimasiPo"),
            getTotalCmeCustomerYear(data,"totalPo"),
            getTotalCmeCustomerYear(data,"totalInv"),
            getTotalCmeCustomerYear(data,"totalBelumTertagih")
    )

    fun getTotalCmeYear(data:List<CmeSummaryYearProjectTypeData>) = doubleArrayOf(
            getTotalCmeYear(data,"totalSite"),
            getTotalCmeYear(data,"totalSiteCancel"),
            getTotalCmeYear(data,"totalEstimasiPo"),
            getTotalCmeYear(data,"totalPo"),
            getTotalCmeYear(data,"totalInv"),
            getTotalCmeYear(data,"totalBelumTertagih")
    )

    fun getPrecent(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    fun getTotalCmeBudget(data: List<CmeSummaryYearProjectTypeData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasiBudget" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeCustomerBudget(data: List<CmeSummaryYearCustomerData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasiBudget" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeYearBudget(data:List<CmeSummaryYearProjectTypeData>) = doubleArrayOf(
            getTotalCmeBudget(data,"totalSite"),
            getTotalCmeBudget(data,"totalSiteCancel"),
            getTotalCmeBudget(data,"totalBudget"),
            getTotalCmeBudget(data,"totalRealisasiBudget"),
            getTotalCmeBudget(data,"totalPo")
    )

    fun getTotalCmeYearCustomerBudget(data:List<CmeSummaryYearCustomerData>) = doubleArrayOf(
            getTotalCmeCustomerBudget(data,"totalSite"),
            getTotalCmeCustomerBudget(data,"totalSiteCancel"),
            getTotalCmeCustomerBudget(data,"totalBudget"),
            getTotalCmeCustomerBudget(data,"totalRealisasiBudget"),
            getTotalCmeCustomerBudget(data,"totalPo")
    )

    fun getPrecentBudget(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    fun getTotalCmeLabaRugi(data: List<CmeSummaryYearProjectTypeData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalLabaRugi" -> it.profit_loss.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeLabaRugi(data:List<CmeSummaryYearProjectTypeData>) = doubleArrayOf(
            getTotalCmeLabaRugi(data,"totalSite"),
            getTotalCmeLabaRugi(data,"totalSiteCancel"),
            getTotalCmeLabaRugi(data,"totalLabaRugi"),
            getTotalCmeLabaRugi(data,"totalRealisasi"),
            getTotalCmeLabaRugi(data,"totalPo")
    )

    fun getTotalCmeCustomerLabaRugi(data: List<CmeSummaryYearCustomerData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalSiteCancel" -> it.site_cancel.let { total = total.plus(it) }
                "totalLabaRugi" -> it.profit_loss.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCmeCustomerLabaRugi(data:List<CmeSummaryYearCustomerData>) = doubleArrayOf(
            getTotalCmeCustomerLabaRugi(data,"totalSite"),
            getTotalCmeCustomerLabaRugi(data,"totalSiteCancel"),
            getTotalCmeCustomerLabaRugi(data,"totalLabaRugi"),
            getTotalCmeCustomerLabaRugi(data,"totalRealisasi"),
            getTotalCmeCustomerLabaRugi(data,"totalPo")
    )

    fun getPrecentLabaRugi(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/project/{tahun}/{type_id}")
    fun yearCustomerCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int,
                        session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + BUDGET_PROJECT_URL + "api/project_summary_year/$tahun/$type_id")
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
        model.addAttribute("project_type", getProjectType(cmeSummaryYearProjectTypeCustDataList))
        return "project/project_by_year_customer"
    }

    @RequestMapping("/project_customer/{tahun}/{customer_id}")
    fun yearSiteTypeCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("customer_id") customer_id: String,
                        session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + "api/project_summary_year_customer/$tahun/$customer_id")
        val cmeSummaryYearProjectTypeCustDataList: List<CmeSummaryYearCustomerProjectTypeData> = objectMapper.readValue(url)
        val totalPoInv = getTotalPoInvCustomer(cmeSummaryYearProjectTypeCustDataList)
        val totalPercentPoInv = getPrecentPoInv(totalPoInv[3],totalPoInv[2])
        val totalBudgetRealisasi = getTotalBudgetCustomerRealisasi(cmeSummaryYearProjectTypeCustDataList)
        val totalPercent = getPrecentBudgetRealisasi(totalBudgetRealisasi[2],totalBudgetRealisasi[1])
        val totalLabaRugi = getTotalCustomerPoInvCustomer(cmeSummaryYearProjectTypeCustDataList)
        val totalPercentProfitRealisasi = getPrecentCustomerPoInv(totalLabaRugi[1], totalLabaRugi[2])
        val totalPercentProfitPo = getPrecentCustomerPoInv(totalLabaRugi[1], totalLabaRugi[3])

        model.addAttribute("cmeSummaryYearProjectTypeCustDataList", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_po })
        model.addAttribute("cmeSummaryYearProjectTypeCustGraph", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_po }.take(5))
        model.addAttribute("cmeSummaryYearProjectTypeCustGraph2", cmeSummaryYearProjectTypeCustDataList.sortedByDescending { it.nilai_budget }.take(5))
        model.addAttribute("total",getTotalCustomerPoInvCustomer(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentLabRugRealisasi",totalPercentProfitRealisasi)
        model.addAttribute("percentLabRugPo",totalPercentProfitPo)
        model.addAttribute("totalPoInv",getTotalPoInvCustomer(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentPoInv",totalPercentPoInv)
        model.addAttribute("totalBudgetRealisasi",getTotalBudgetCustomerRealisasi(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("percentBudgetRealisasi",totalPercent)
        model.addAttribute("year_project", tahun)
        model.addAttribute("project_type", getProjectTypeCustomer(cmeSummaryYearProjectTypeCustDataList))
        model.addAttribute("customer", getCustomer(cmeSummaryYearProjectTypeCustDataList))
        return "project/project_by_site_type"
    }

    fun getProjectType(data:List<CmeSummaryYearProjectTypeCustData>):String{
        return if(data.isNotEmpty()) data[0].project_type else ""
    }

    fun getProjectTypeCustomer(data:List<CmeSummaryYearCustomerProjectTypeData>):String{
        return if(data.isNotEmpty()) data[0].project_type else ""
    }

    fun getCustomer(data:List<CmeSummaryYearCustomerProjectTypeData>): String? {
        return if(data.isNotEmpty()) data[0].customer else ""
    }

    fun getCustomerName(data:List<CmeSummaryYearProjectTypeCustData>): String? {
        return if(data.isNotEmpty()) data[0].customer else ""
    }

    fun getTotalPoInv(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInv" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalBelumTertagih" -> it.remaining_invoice.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalPoInv(data:List<CmeSummaryYearProjectTypeCustData>) = doubleArrayOf(
            getTotalPoInv(data,"totalSite"),
            getTotalPoInv(data,"totalEstimasiPo"),
            getTotalPoInv(data,"totalPo"),
            getTotalPoInv(data,"totalInv"),
            getTotalPoInv(data,"totalBelumTertagih")
    )


    fun getTotalPoInvCustomer(data: List<CmeSummaryYearCustomerProjectTypeData>, type: String):Double{
        var total =0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInv" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalBelumTertagih" -> it.remaining_invoice.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalPoInvCustomer(data:List<CmeSummaryYearCustomerProjectTypeData>) = doubleArrayOf(
            getTotalPoInvCustomer(data,"totalSite"),
            getTotalPoInvCustomer(data,"totalEstimasiPo"),
            getTotalPoInvCustomer(data,"totalPo"),
            getTotalPoInvCustomer(data,"totalInv"),
            getTotalPoInvCustomer(data,"totalBelumTertagih")
    )

    fun getPrecentPoInv(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    fun getTotalBudgetRealisasi(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Double{
        var total = 0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalBudgetRealisasi(data:List<CmeSummaryYearProjectTypeCustData>) = doubleArrayOf(
            getTotalBudgetRealisasi(data,"totalSite"),
            getTotalBudgetRealisasi(data,"totalBudget"),
            getTotalBudgetRealisasi(data,"totalRealisasi")
    )

    fun getTotalBudgetCustomerRealisasi(data: List<CmeSummaryYearCustomerProjectTypeData>, type: String):Double{
        var total = 0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalBudgetCustomerRealisasi(data:List<CmeSummaryYearCustomerProjectTypeData>) = doubleArrayOf(
            getTotalBudgetCustomerRealisasi(data,"totalSite"),
            getTotalBudgetCustomerRealisasi(data,"totalBudget"),
            getTotalBudgetCustomerRealisasi(data,"totalRealisasi")
    )

    fun getPrecentBudgetRealisasi(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    fun getTotalCustomerPo(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Double{
        var total = 0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalLabaRugi" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCustomerPoInv(data:List<CmeSummaryYearProjectTypeCustData>) = doubleArrayOf(
            getTotalCustomerPo(data,"totalSite"),
            getTotalCustomerPo(data,"totalLabaRugi"),
            getTotalCustomerPo(data,"totalRealisasi"),
            getTotalCustomerPo(data,"totalPo")
    )

    fun getTotalCustomerPoCustomer(data: List<CmeSummaryYearCustomerProjectTypeData>, type: String):Double{
        var total = 0.0
        data.forEach { it ->
            when(type){
                "totalSite" -> it.jumlah_site.let { total = total.plus(it) }
                "totalLabaRugi" -> it.nilai_budget.let { total = total.plus(it) }
                "totalRealisasi" -> it.realisasi_budget.let { total = total.plus(it) }
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
            }
        }
        return total
    }

    fun getTotalCustomerPoInvCustomer(data:List<CmeSummaryYearCustomerProjectTypeData>) = doubleArrayOf(
            getTotalCustomerPoCustomer(data,"totalSite"),
            getTotalCustomerPoCustomer(data,"totalLabaRugi"),
            getTotalCustomerPoCustomer(data,"totalRealisasi"),
            getTotalCustomerPoCustomer(data,"totalPo")
    )

    fun getPrecentCustomerPoInv(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/project/{tahun}/{type_id}/{customer_id}")
    fun yearCustomerDetailCME(model: Model, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int, @PathVariable("customer_id") customer_id: Long, session: HttpSession): String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + BUDGET_PROJECT_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearTypeCustDetailDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        val cmeSummaryYearTypeCustDetailDataListFilter = cmeSummaryYearTypeCustDetailDataList.filter { it.customer_id == customer_id }
        var cmeYearTypeCustProject: List<CmeYearProjectTypeCustProjectDetailData>? = mutableListOf()
        cmeSummaryYearTypeCustDetailDataListFilter.forEach{ it ->
            cmeYearTypeCustProject = it.project_list
            it.project_list?.forEach {
                it.percent_po = if (it.estimate_po > 0)((it.estimate_po - it.nilai_budget) * 100f) / it.estimate_po else 0.0
                it.percent_labarugi = if (it.estimate_po > 0)((it.nilai_invoice - it.realisasi_budget) * 100f) / it.realisasi_budget else 0.0
            }
        }

        val totalPoBudget = getTotalDetailPoBudget(cmeSummaryYearTypeCustDetailDataListFilter)
        val totalPercentPoBudget = getPrecentDetailPoBudget(totalPoBudget[1],totalPoBudget[0])
        val totalGrossMargin = getGrossMargin(totalPoBudget[0],totalPoBudget[1])
        val totalInvLabRug = getTotalDetailInvLabRug(cmeSummaryYearTypeCustDetailDataListFilter)
        val totalPercentInvLabRug  = getPrecentInvLabRug(totalInvLabRug[1],totalInvLabRug[0])
        val totalGrossMarginInvLabRug  = getGrossMarginInvLabRug(totalInvLabRug[0],totalInvLabRug[1])

//        model.addAttribute("cmeSummaryYearTypeCustDetailDataList", cmeSummaryYearTypeCustDetailDataList.filter { it.customer_id == customer_id })
        model.addAttribute("cmeSummaryYearTypeCustDetailDataList", cmeSummaryYearTypeCustDetailDataListFilter.sortedByDescending { it.nilai_po })
        model.addAttribute("cmeYearTypeCustProjectGraph1", cmeYearTypeCustProject?.sortedByDescending { it.nilai_po }?.take(5))
        model.addAttribute("totalPoBudget",getTotalDetailPoBudget(cmeSummaryYearTypeCustDetailDataListFilter))
        model.addAttribute("percentPoBudget",totalPercentPoBudget)
        model.addAttribute("grossMargin",totalGrossMargin)
        model.addAttribute("totalInvLabRug",getTotalDetailInvLabRug(cmeSummaryYearTypeCustDetailDataListFilter))
        model.addAttribute("percentInvLabRug",totalPercentInvLabRug)
        model.addAttribute("grossMarginInvLabRug",totalGrossMarginInvLabRug)
        model.addAttribute("tahun", tahun)
        model.addAttribute("project_type", getProjectType(cmeSummaryYearTypeCustDetailDataListFilter))
        model.addAttribute("customer_name", getCustomerName(cmeSummaryYearTypeCustDetailDataListFilter))
        return "project/project_by_year_customer_detail"
    }

    fun getTotalDetaiPoBudget(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Double{
        var total = 0.0
        data.forEach {
            d1->d1.project_list?.forEach { it ->
            when(type){
                    "totalEstimasiPo" -> it.estimate_po.let { total = total.plus(it) }
                    "totalBudget" -> it.nilai_budget.let { total = total.plus(it) }
                }
        }
        }
        return total
    }

    fun getTotalDetailPoBudget(data:List<CmeSummaryYearProjectTypeCustData>) = doubleArrayOf(
            getTotalDetaiPoBudget(data,"totalEstimasiPo"),
            getTotalDetaiPoBudget(data,"totalBudget")
    )

    fun getGrossMargin(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data - data1 else(0.0)
    )

    fun getPrecentDetailPoBudget(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    fun getTotalDetaiInvLabRug(data: List<CmeSummaryYearProjectTypeCustData>, type: String):Double{
        var total =0.0
        data.forEach {
            d1->d1.project_list?.forEach { it ->
            when(type){
                "totalPo" -> it.nilai_po.let { total = total.plus(it) }
                "totalInvoice" -> it.nilai_invoice.let { total = total.plus(it) }
                "totalRealisasiBudget" -> it.realisasi_budget.let { total = total.plus(it) }
            }
        }
        }
        return total
    }

    fun getTotalDetailInvLabRug(data:List<CmeSummaryYearProjectTypeCustData>) = doubleArrayOf(
            getTotalDetaiInvLabRug(data,"totalPo"),
            getTotalDetaiInvLabRug(data,"totalInvoice"),
            getTotalDetaiInvLabRug(data,"totalRealisasiBudget")
    )

    fun getGrossMarginInvLabRug(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data - data1 else(0.0)
    )

    fun getPrecentInvLabRug(data:Double, data1: Double) = doubleArrayOf(
            if (data1 > 0.0) data * 100 / data1 else (0.0)
    )

    @RequestMapping("/project/download/{tahun}/{type_id}")
    fun downloadCME(model:Model, response:HttpServletResponse, @PathVariable("tahun") tahun: String, @PathVariable("type_id") type_id: Int){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition", "attachment; filename=\"budget-cme-file-$tahun-$type_id.xls\"")

        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + BUDGET_PROJECT_URL + "api/project_summary_year/$tahun/$type_id")
        val cmeSummaryYearProjectTypeCustDataList: List<CmeSummaryYearProjectTypeCustData> = objectMapper.readValue(url)
        XlsCme(response, tahun, type_id, cmeSummaryYearProjectTypeCustDataList)
    }
}
