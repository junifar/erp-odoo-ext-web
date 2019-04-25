package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.constant.GlobalConstant.Companion.DIREKSI_URL
import com.prasetia.erp.constant.GlobalConstant.Companion.REDIRECT_LOGIN_URL
import com.prasetia.erp.pojo.revenue.RevenueYearData
import com.prasetia.erp.pojo.revenue.RevenueYearDetailData
import com.prasetia.erp.pojo.revenue.RevenueYearDetailSiteTypeData
import com.prasetia.erp.pojo.revenue.RevenueYearHeaderData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.URL
import javax.servlet.http.HttpSession

@Controller("Revenue Web Controller")
class RevenueController{
    @RequestMapping("/revenue")
    fun index(model: Model, session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + DIREKSI_URL + "api/revenue")

        val revenueYearDataList: List<RevenueYearData> = objectMapper.readValue(url)

        model.addAttribute("revenueYearDataList", revenueYearDataList)
        model.addAttribute("summaryrevenueYearData", getTotalRevenueYearDataList(revenueYearDataList))
        return "revenue/index"
    }

    fun getTotalRevenueYearDataList(data:List<RevenueYearData>):RevenueYearData = RevenueYearData(
            0,
            null,
            data.sumByDouble { it.nilai_po?:0.0 },
            data.sumByDouble { it.invoiced?:0.0 },
            data.sumByDouble { it.paid?:0.0 },
            data.sumByDouble { it.total?:0.0 },
            data.sumByDouble { it.target?:0.0 }
    )

    @RequestMapping("/revenue/{tahun}")
    fun revenueByYear(model: Model, @PathVariable("tahun") tahun:String, session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + DIREKSI_URL + "api/revenue/%s".format(tahun))

        val revenueYearHeaderData: List<RevenueYearHeaderData> = objectMapper.readValue(url)

        var revenueYearDetailData: List<RevenueYearDetailData>? = mutableListOf()
        var revenueYearDetailSiteTypeData: List<RevenueYearDetailSiteTypeData>? = mutableListOf()

        revenueYearHeaderData.forEach {
            revenueYearDetailData = it.revenue_year_detail
            revenueYearDetailSiteTypeData = it.revenue_year_detail_site_type
        }

        model.addAttribute("revenueYearHeaderData", revenueYearHeaderData)
        model.addAttribute("revenueYearDetailDataChart", revenueYearDetailData?.take(5))
        model.addAttribute("revenueYearDetailSiteTypeDataChart", revenueYearDetailSiteTypeData?.take(5))
        model.addAttribute("summaryRevenueYearHeaderData", getTotalRevenueByYearByCustomer(revenueYearDetailData!!))
        model.addAttribute("summaryRevenueYearSiteTypeHeaderData", getTotalRevenueByYearBySiteType(revenueYearDetailSiteTypeData!!))
        return "revenue/revenue_by_year"
    }

    fun getTotalRevenueByYearBySiteType(data:List<RevenueYearDetailSiteTypeData>): RevenueYearDetailSiteTypeData = RevenueYearDetailSiteTypeData(
            0, 0, "",
            data.sumBy { it.jumlah_site }, 0,
            data.sumByDouble { it.nilai_po?:0.0 },
            data.sumByDouble { it.invoiced?:0.0 },
            data.sumByDouble { it.paid?:0.0 },
            data.sumByDouble { it.total?:0.0 },
            data.sumByDouble { it.target?:0.0 })

    fun getTotalRevenueByYearByCustomer(data:List<RevenueYearDetailData>): RevenueYearDetailData = RevenueYearDetailData(
            0, 0, "",
            data.sumBy { it.jumlah_site }, 0,
            data.sumByDouble { it.nilai_po?:0.0 },
            data.sumByDouble { it.invoiced?:0.0 },
            data.sumByDouble { it.paid?:0.0 },
            data.sumByDouble { it.total?:0.0 },
            data.sumByDouble { it.target?:0.0 })

    @RequestMapping("/revenue/project_type/{tahun}/{project_type}")
    fun revenueByYearType(model: Model,
                          @PathVariable("tahun") tahun: String,
                          @PathVariable("project_type") project_type:String,
                          session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        return "revenue/revenue_by_year_project_type"
    }

    @RequestMapping("/revenue/customer/{tahun}/{customer_id}")
    fun revenueByYearCustomer(model: Model,
                              @PathVariable("tahun") tahun: String,
                              @PathVariable("customer_id") customer_id:String,
                              session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        return "revenue/revenue_by_year_customer"
    }
}
