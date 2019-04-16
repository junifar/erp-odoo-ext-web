package com.prasetia.erp.controller.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prasetia.erp.constant.GlobalConstant
import com.prasetia.erp.constant.GlobalConstant.Companion.MAIN_ROOT
import com.prasetia.erp.pojo.user.UserAuthData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.net.URL
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@Controller("Auth Controller")
class AuthController{

    @RequestMapping("/login", method = [RequestMethod.GET])
    fun index(session: HttpSession):String{
        if(!session.isNew){
            println(session.getAttribute("id"))
        }
        return "auth/login"
    }

    @RequestMapping("/logout")
    fun sessionDestroy(session: HttpSession):String{
        session.invalidate()
        return "redirect:$MAIN_ROOT/login"
    }

    @RequestMapping("/login", method = [RequestMethod.POST])
    fun indexPost(model: Model, @RequestParam("username") username:String,
                  @RequestParam("password") password:String,
                  request: HttpServletRequest): String {
//        println(username)
//        println(password)
        val usernameReq = if(username.isEmpty()) "-" else username
        val passwordReq = if(password.isBlank()) "-" else password

        val objectMapper = ObjectMapper()
        val url = URL(GlobalConstant.BASE_URL + GlobalConstant.GENERAL_URL + "/api/signin/$usernameReq/$passwordReq")

        val userAuthData: List<UserAuthData> = objectMapper.readValue(url)

        if (userAuthData.isNotEmpty()){
            request.session.setAttribute("id", userAuthData[0].id)
            request.session.setAttribute("login", userAuthData[0].login)
        }

        return "redirect:$MAIN_ROOT"

//        return "auth/login"
    }
}
