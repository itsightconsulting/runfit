package com.itsight.controller;

import com.itsight.domain.dto.UbPeruLimDTO;
import com.itsight.service.UbPeruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/p/ubigeo")
public class PaisController {

    UbPeruService ubPeruService;

    @Autowired
    public PaisController(UbPeruService ubPeruService){
        this.ubPeruService = ubPeruService;
    }

    @GetMapping("/get/peru-lim")
    public @ResponseBody
    UbPeruLimDTO getUbigeoPeruLim(){
        return ubPeruService.findPeLimUbigeo();
    }

    @GetMapping("/get/peru-prov-by-dep")
    public @ResponseBody
    UbPeruLimDTO getUbigeoPeruProvByDep(@RequestParam("depId") String depId){
        return ubPeruService.findPeProvByDep(depId);
    }

    @GetMapping("/get/peru-dis-by-dep-and-prov")
    public @ResponseBody
    UbPeruLimDTO getUbigeoPeruDistByDepAndProv(@RequestParam("depId") String depId, @RequestParam("provId") String provId){
        return ubPeruService.findPeDistByDepAndProv(depId, provId);
    }
}
