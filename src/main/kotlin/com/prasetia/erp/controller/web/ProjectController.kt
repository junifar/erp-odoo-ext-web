package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant.Companion.BASE_URL
import com.prasetia.erp.constant.GlobalConstant.Companion.DIREKSI_URL
import com.prasetia.erp.pojo.project.ProjectRecapAgingData
import com.prasetia.erp.pojo.project.ProjectRecapData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.net.URL

@Controller("Project Web Controller")
class ProjectController{

    @RequestMapping("/project/recap", method = [RequestMethod.POST])
    fun indexPost(model: Model, @RequestParam("site_type_filter[]") site_type_filter: List<String>?, @RequestParam("year_filter[]") year_filter: List<String>? ):String{

        val site_type_filter_string:String = site_type_filter.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",","-")
                .replace(" ","")

        val year_filter_String:String = year_filter.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(",","-")
                .replace(" ","")

        var siteTypeFilterValue = mutableListOf(0,0,0,0,0,0,0,0)
        var yearFilterValue = mutableListOf(0,0,0,0)

        site_type_filter?.forEach {
            when(it){
                "2"->siteTypeFilterValue[0] = 1
                "80"->siteTypeFilterValue[1] = 1
                "10"->siteTypeFilterValue[2] = 1
                "8"->siteTypeFilterValue[3] = 1
                "7"->siteTypeFilterValue[4] = 1
                "3"->siteTypeFilterValue[5] = 1
                "1"->siteTypeFilterValue[6] = 1
                "5"->siteTypeFilterValue[7] = 1
            }
        }

        year_filter?.forEach {
            when(it){
                "2019"->yearFilterValue[0] = 1
                "2018"->yearFilterValue[1] = 1
                "2017"->yearFilterValue[2] = 1
                "2016"->yearFilterValue[3] = 1
            }
        }


//        val condition = whereSiteTypeCondition(site_type_filter_string) + whereDateCondition(year_filter_String)
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + DIREKSI_URL + "api/project/recap/$site_type_filter_string/$year_filter_String")
        val url_aging = URL(BASE_URL + "api/project/recap_aging/$site_type_filter_string/$year_filter_String")

        val projectRecapDataList: List<ProjectRecapData> = objectMapper.readValue(url)
        val projectRecapAgingDataList: List<ProjectRecapAgingData> = objectMapper.readValue(url_aging)

//        print(site_type_filter_string)
//        print(year_filter_String)
//        model.addAttribute("site_type_filter", site_type_filter)
//        model.addAttribute("year_filter", year_filter)

        model.addAttribute("projectRecapDataList", projectRecapDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.po })

        model.addAttribute("projectRecapByValueDataList", projectRecapDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.nilai_po })

        model.addAttribute("projectRecapAgingDataList", projectRecapAgingDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
        )

        model.addAttribute("site_type_filter_value", siteTypeFilterValue)
        model.addAttribute("year_filter_value", yearFilterValue)

        return "project_summary/index"
    }

    @RequestMapping("/project/recap", method = [RequestMethod.GET])
    fun index(model:Model):String{
        val objectMapper = ObjectMapper()
        val url = URL(BASE_URL + DIREKSI_URL + "api/project/recap")
        val url_aging = URL(BASE_URL + DIREKSI_URL + "api/project/recap_aging")

        val projectRecapDataList: List<ProjectRecapData> = objectMapper.readValue(url)
        val projectRecapAgingDataList: List<ProjectRecapAgingData> = objectMapper.readValue(url_aging)

        val siteTypeFilterValue = intArrayOf(1,1,1,0,0,1,1,1)
        val yearFilterValue = intArrayOf(1,1,1,1)

        model.addAttribute("projectRecapDataList", projectRecapDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.po })

        model.addAttribute("projectRecapByValueDataList", projectRecapDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
                .sortedByDescending { it.nilai_po })

        model.addAttribute("projectRecapAgingDataList", projectRecapAgingDataList
//                .filter { it.site_type != "Maintenance Corrective" }
//                .filter { it.site_type != "Maintenance Preventive" }
        )

        model.addAttribute("site_type_filter_value", siteTypeFilterValue)
        model.addAttribute("year_filter_value", yearFilterValue)

        return "project_summary/index"
    }
}
