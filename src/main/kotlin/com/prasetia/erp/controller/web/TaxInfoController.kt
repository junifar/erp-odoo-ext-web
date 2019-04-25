package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.ACCOUNTING_URL
import com.prasetia.erp.constant.GlobalConstant.Companion.REDIRECT_LOGIN_URL
import com.prasetia.erp.controller.web.xls.taxinfo.XlsTaxInfo
import com.prasetia.erp.pojo.taxinfo.TaxInvoiceData
import com.prasetia.erp.pojo.taxinfo.TaxInvoicePeriodeData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.net.CacheResponse
import java.net.URL
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Controller("Tax Info Controller")
class TaxInfoController {

    @RequestMapping("/taxinfo")
    fun indexTaxInfo(model:Model, session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + ACCOUNTING_URL + "api/taxinfoperiode")
        val taxInfoSummaryDataList: List<TaxInvoicePeriodeData> = objectMapper.readValue(url)
        model.addAttribute("taxInfoSummaryDataList", taxInfoSummaryDataList.sortedByDescending { it.year })
        return "taxinfo/index"
    }

    @RequestMapping("taxinfo/{tahun}")
    fun taxInfoYear(model:Model, @PathVariable("tahun") tahun:String, session: HttpSession):String{
        if(session.getAttribute("id") == null){
            return REDIRECT_LOGIN_URL
        }
        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + ACCOUNTING_URL + "api/taxinfo/%s".format(tahun))
        val taxInfoDataList:List<TaxInvoiceData> = objectMapper.readValue(url)

        model.addAttribute("taxInfoDataList", taxInfoDataList.sortedBy { it.tanggal_pembayaran })
        model.addAttribute("periode", tahun)
        return "taxinfo/tax_invoice_by_year"
    }

    @RequestMapping("taxinfo/download/{tahun}")
    fun downloadTaxInvoice(model:Model, response: HttpServletResponse, @PathVariable("tahun") tahun:String){
        response.contentType = "application/vnd.ms-excel"
        response.setHeader("Content-Disposition", "attachment; filename=\"tax-invoice-file-$tahun.xls\"")
        val url = URL(GlobalConstant.BASE_URL + ACCOUNTING_URL + "api/taxinfo/%s".format(tahun))
        val objectMapper = ObjectMapper()
        val taxInfoDataList:List<TaxInvoiceData> = objectMapper.readValue(url)
        XlsTaxInfo(response, tahun, taxInfoDataList)
    }

}
