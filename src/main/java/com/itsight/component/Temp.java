package com.itsight.component;

import com.itsight.domain.UbPeru;
import com.itsight.service.PaisService;
import com.itsight.service.UbPeruService;
import org.springframework.beans.factory.annotation.Autowired;

public class Temp {

    @Autowired
    private UbPeruService ubPeruService;

    public void addingUbPeru() {
        if (ubPeruService.findOne(1) == null) ubPeruService.save(new UbPeru("010000", "Amazonas", "", ""));
        if (ubPeruService.findOne(2) == null) ubPeruService.save(new UbPeru("010100", "Amazonas", "Chachapoyas", ""));
        if (ubPeruService.findOne(3) == null)
            ubPeruService.save(new UbPeru("010101", "Amazonas", "Chachapoyas", "Chachapoyas"));
        if (ubPeruService.findOne(4) == null)
            ubPeruService.save(new UbPeru("010102", "Amazonas", "Chachapoyas", "Asunción"));
        if (ubPeruService.findOne(5) == null)
            ubPeruService.save(new UbPeru("010103", "Amazonas", "Chachapoyas", "Balsas"));
        if (ubPeruService.findOne(6) == null)
            ubPeruService.save(new UbPeru("010104", "Amazonas", "Chachapoyas", "Cheto"));
        if (ubPeruService.findOne(7) == null)
            ubPeruService.save(new UbPeru("010105", "Amazonas", "Chachapoyas", "Chiliquin"));
        if (ubPeruService.findOne(8) == null)
            ubPeruService.save(new UbPeru("010106", "Amazonas", "Chachapoyas", "Chuquibamba"));
        if (ubPeruService.findOne(9) == null)
            ubPeruService.save(new UbPeru("010107", "Amazonas", "Chachapoyas", "Granada"));
        if (ubPeruService.findOne(10) == null)
            ubPeruService.save(new UbPeru("010108", "Amazonas", "Chachapoyas", "Huancas"));
        if (ubPeruService.findOne(11) == null)
            ubPeruService.save(new UbPeru("010109", "Amazonas", "Chachapoyas", "La Jalca"));
        if (ubPeruService.findOne(12) == null)
            ubPeruService.save(new UbPeru("010110", "Amazonas", "Chachapoyas", "Leimebamba"));
        if (ubPeruService.findOne(13) == null)
            ubPeruService.save(new UbPeru("010111", "Amazonas", "Chachapoyas", "Levanto"));
        if (ubPeruService.findOne(14) == null)
            ubPeruService.save(new UbPeru("010112", "Amazonas", "Chachapoyas", "Magdalena"));
        if (ubPeruService.findOne(15) == null)
            ubPeruService.save(new UbPeru("010113", "Amazonas", "Chachapoyas", "Mariscal Castilla"));
        if (ubPeruService.findOne(16) == null)
            ubPeruService.save(new UbPeru("010114", "Amazonas", "Chachapoyas", "Molinopampa"));
        if (ubPeruService.findOne(17) == null)
            ubPeruService.save(new UbPeru("010115", "Amazonas", "Chachapoyas", "Montevideo"));
        if (ubPeruService.findOne(18) == null)
            ubPeruService.save(new UbPeru("010116", "Amazonas", "Chachapoyas", "Olleros"));
        if (ubPeruService.findOne(19) == null)
            ubPeruService.save(new UbPeru("010117", "Amazonas", "Chachapoyas", "Quinjalca"));
        if (ubPeruService.findOne(20) == null)
            ubPeruService.save(new UbPeru("010118", "Amazonas", "Chachapoyas", "San Francisco de Daguas"));
        if (ubPeruService.findOne(21) == null)
            ubPeruService.save(new UbPeru("010119", "Amazonas", "Chachapoyas", "San Isidro de Maino"));
        if (ubPeruService.findOne(22) == null)
            ubPeruService.save(new UbPeru("010120", "Amazonas", "Chachapoyas", "Soloco"));
        if (ubPeruService.findOne(23) == null)
            ubPeruService.save(new UbPeru("010121", "Amazonas", "Chachapoyas", "Sonche"));
        if (ubPeruService.findOne(24) == null) ubPeruService.save(new UbPeru("010200", "Amazonas", "Bagua", ""));
        if (ubPeruService.findOne(25) == null) ubPeruService.save(new UbPeru("010201", "Amazonas", "Bagua", "Bagua"));
        if (ubPeruService.findOne(26) == null)
            ubPeruService.save(new UbPeru("010202", "Amazonas", "Bagua", "Aramango"));
        if (ubPeruService.findOne(27) == null)
            ubPeruService.save(new UbPeru("010203", "Amazonas", "Bagua", "Copallin"));
        if (ubPeruService.findOne(28) == null)
            ubPeruService.save(new UbPeru("010204", "Amazonas", "Bagua", "El Parco"));
        if (ubPeruService.findOne(29) == null) ubPeruService.save(new UbPeru("010205", "Amazonas", "Bagua", "Imaza"));
        if (ubPeruService.findOne(30) == null) ubPeruService.save(new UbPeru("010206", "Amazonas", "Bagua", "La Peca"));
        if (ubPeruService.findOne(31) == null) ubPeruService.save(new UbPeru("010300", "Amazonas", "Bongará", ""));
        if (ubPeruService.findOne(32) == null)
            ubPeruService.save(new UbPeru("010301", "Amazonas", "Bongará", "Jumbilla"));
        if (ubPeruService.findOne(33) == null)
            ubPeruService.save(new UbPeru("010302", "Amazonas", "Bongará", "Chisquilla"));
        if (ubPeruService.findOne(34) == null)
            ubPeruService.save(new UbPeru("010303", "Amazonas", "Bongará", "Churuja"));
        if (ubPeruService.findOne(35) == null)
            ubPeruService.save(new UbPeru("010304", "Amazonas", "Bongará", "Corosha"));
        if (ubPeruService.findOne(36) == null)
            ubPeruService.save(new UbPeru("010305", "Amazonas", "Bongará", "Cuispes"));
        if (ubPeruService.findOne(37) == null)
            ubPeruService.save(new UbPeru("010306", "Amazonas", "Bongará", "Florida"));
        if (ubPeruService.findOne(38) == null) ubPeruService.save(new UbPeru("010307", "Amazonas", "Bongará", "Jazan"));
        if (ubPeruService.findOne(39) == null) ubPeruService.save(new UbPeru("010308", "Amazonas", "Bongará", "Recta"));
        if (ubPeruService.findOne(40) == null)
            ubPeruService.save(new UbPeru("010309", "Amazonas", "Bongará", "San Carlos"));
        if (ubPeruService.findOne(41) == null)
            ubPeruService.save(new UbPeru("010310", "Amazonas", "Bongará", "Shipasbamba"));
        if (ubPeruService.findOne(42) == null)
            ubPeruService.save(new UbPeru("010311", "Amazonas", "Bongará", "Valera"));
        if (ubPeruService.findOne(43) == null)
            ubPeruService.save(new UbPeru("010312", "Amazonas", "Bongará", "Yambrasbamba"));
        if (ubPeruService.findOne(44) == null) ubPeruService.save(new UbPeru("010400", "Amazonas", "Condorcanqui", ""));
        if (ubPeruService.findOne(45) == null)
            ubPeruService.save(new UbPeru("010401", "Amazonas", "Condorcanqui", "Nieva"));
        if (ubPeruService.findOne(46) == null)
            ubPeruService.save(new UbPeru("010402", "Amazonas", "Condorcanqui", "El Cenepa"));
        if (ubPeruService.findOne(47) == null)
            ubPeruService.save(new UbPeru("010403", "Amazonas", "Condorcanqui", "Río Santiago"));
        if (ubPeruService.findOne(48) == null) ubPeruService.save(new UbPeru("010500", "Amazonas", "Luya", ""));
        if (ubPeruService.findOne(49) == null) ubPeruService.save(new UbPeru("010501", "Amazonas", "Luya", "Lamud"));
        if (ubPeruService.findOne(50) == null)
            ubPeruService.save(new UbPeru("010502", "Amazonas", "Luya", "Camporredondo"));
        if (ubPeruService.findOne(51) == null)
            ubPeruService.save(new UbPeru("010503", "Amazonas", "Luya", "Cocabamba"));
        if (ubPeruService.findOne(52) == null) ubPeruService.save(new UbPeru("010504", "Amazonas", "Luya", "Colcamar"));
        if (ubPeruService.findOne(53) == null) ubPeruService.save(new UbPeru("010505", "Amazonas", "Luya", "Conila"));
        if (ubPeruService.findOne(54) == null)
            ubPeruService.save(new UbPeru("010506", "Amazonas", "Luya", "Inguilpata"));
        if (ubPeruService.findOne(55) == null) ubPeruService.save(new UbPeru("010507", "Amazonas", "Luya", "Longuita"));
        if (ubPeruService.findOne(56) == null)
            ubPeruService.save(new UbPeru("010508", "Amazonas", "Luya", "Lonya Chico"));
        if (ubPeruService.findOne(57) == null) ubPeruService.save(new UbPeru("010509", "Amazonas", "Luya", "Luya"));
        if (ubPeruService.findOne(58) == null)
            ubPeruService.save(new UbPeru("010510", "Amazonas", "Luya", "Luya Viejo"));
        if (ubPeruService.findOne(59) == null) ubPeruService.save(new UbPeru("010511", "Amazonas", "Luya", "María"));
        if (ubPeruService.findOne(60) == null) ubPeruService.save(new UbPeru("010512", "Amazonas", "Luya", "Ocalli"));
        if (ubPeruService.findOne(61) == null) ubPeruService.save(new UbPeru("010513", "Amazonas", "Luya", "Ocumal"));
        if (ubPeruService.findOne(62) == null) ubPeruService.save(new UbPeru("010514", "Amazonas", "Luya", "Pisuquia"));
        if (ubPeruService.findOne(63) == null)
            ubPeruService.save(new UbPeru("010515", "Amazonas", "Luya", "Providencia"));
        if (ubPeruService.findOne(64) == null)
            ubPeruService.save(new UbPeru("010516", "Amazonas", "Luya", "San Cristóbal"));
        if (ubPeruService.findOne(65) == null)
            ubPeruService.save(new UbPeru("010517", "Amazonas", "Luya", "San Francisco de Yeso"));
        if (ubPeruService.findOne(66) == null)
            ubPeruService.save(new UbPeru("010518", "Amazonas", "Luya", "San Jerónimo"));
        if (ubPeruService.findOne(67) == null)
            ubPeruService.save(new UbPeru("010519", "Amazonas", "Luya", "San Juan de Lopecancha"));
        if (ubPeruService.findOne(68) == null)
            ubPeruService.save(new UbPeru("010520", "Amazonas", "Luya", "Santa Catalina"));
        if (ubPeruService.findOne(69) == null)
            ubPeruService.save(new UbPeru("010521", "Amazonas", "Luya", "Santo Tomas"));
        if (ubPeruService.findOne(70) == null) ubPeruService.save(new UbPeru("010522", "Amazonas", "Luya", "Tingo"));
        if (ubPeruService.findOne(71) == null) ubPeruService.save(new UbPeru("010523", "Amazonas", "Luya", "Trita"));
        if (ubPeruService.findOne(72) == null)
            ubPeruService.save(new UbPeru("010600", "Amazonas", "Rodríguez de Mendoza", ""));
        if (ubPeruService.findOne(73) == null)
            ubPeruService.save(new UbPeru("010601", "Amazonas", "Rodríguez de Mendoza", "San Nicolás"));
        if (ubPeruService.findOne(74) == null)
            ubPeruService.save(new UbPeru("010602", "Amazonas", "Rodríguez de Mendoza", "Chirimoto"));
        if (ubPeruService.findOne(75) == null)
            ubPeruService.save(new UbPeru("010603", "Amazonas", "Rodríguez de Mendoza", "Cochamal"));
        if (ubPeruService.findOne(76) == null)
            ubPeruService.save(new UbPeru("010604", "Amazonas", "Rodríguez de Mendoza", "Huambo"));
        if (ubPeruService.findOne(77) == null)
            ubPeruService.save(new UbPeru("010605", "Amazonas", "Rodríguez de Mendoza", "Limabamba"));
        if (ubPeruService.findOne(78) == null)
            ubPeruService.save(new UbPeru("010606", "Amazonas", "Rodríguez de Mendoza", "Longar"));
        if (ubPeruService.findOne(79) == null)
            ubPeruService.save(new UbPeru("010607", "Amazonas", "Rodríguez de Mendoza", "Mariscal Benavides"));
        if (ubPeruService.findOne(80) == null)
            ubPeruService.save(new UbPeru("010608", "Amazonas", "Rodríguez de Mendoza", "Milpuc"));
        if (ubPeruService.findOne(81) == null)
            ubPeruService.save(new UbPeru("010609", "Amazonas", "Rodríguez de Mendoza", "Omia"));
        if (ubPeruService.findOne(82) == null)
            ubPeruService.save(new UbPeru("010610", "Amazonas", "Rodríguez de Mendoza", "Santa Rosa"));
        if (ubPeruService.findOne(83) == null)
            ubPeruService.save(new UbPeru("010611", "Amazonas", "Rodríguez de Mendoza", "Totora"));
        if (ubPeruService.findOne(84) == null)
            ubPeruService.save(new UbPeru("010612", "Amazonas", "Rodríguez de Mendoza", "Vista Alegre"));
        if (ubPeruService.findOne(85) == null) ubPeruService.save(new UbPeru("010700", "Amazonas", "Utcubamba", ""));
        if (ubPeruService.findOne(86) == null)
            ubPeruService.save(new UbPeru("010701", "Amazonas", "Utcubamba", "Bagua Grande"));
        if (ubPeruService.findOne(87) == null)
            ubPeruService.save(new UbPeru("010702", "Amazonas", "Utcubamba", "Cajaruro"));
        if (ubPeruService.findOne(88) == null)
            ubPeruService.save(new UbPeru("010703", "Amazonas", "Utcubamba", "Cumba"));
        if (ubPeruService.findOne(89) == null)
            ubPeruService.save(new UbPeru("010704", "Amazonas", "Utcubamba", "El Milagro"));
        if (ubPeruService.findOne(90) == null)
            ubPeruService.save(new UbPeru("010705", "Amazonas", "Utcubamba", "Jamalca"));
        if (ubPeruService.findOne(91) == null)
            ubPeruService.save(new UbPeru("010706", "Amazonas", "Utcubamba", "Lonya Grande"));
        if (ubPeruService.findOne(92) == null)
            ubPeruService.save(new UbPeru("010707", "Amazonas", "Utcubamba", "Yamon"));
        if (ubPeruService.findOne(93) == null) ubPeruService.save(new UbPeru("020000", "Áncash", "", ""));
        if (ubPeruService.findOne(94) == null) ubPeruService.save(new UbPeru("020100", "Áncash", "Huaraz", ""));
        if (ubPeruService.findOne(95) == null) ubPeruService.save(new UbPeru("020101", "Áncash", "Huaraz", "Huaraz"));
        if (ubPeruService.findOne(96) == null)
            ubPeruService.save(new UbPeru("020102", "Áncash", "Huaraz", "Cochabamba"));
        if (ubPeruService.findOne(97) == null)
            ubPeruService.save(new UbPeru("020103", "Áncash", "Huaraz", "Colcabamba"));
        if (ubPeruService.findOne(98) == null) ubPeruService.save(new UbPeru("020104", "Áncash", "Huaraz", "Huanchay"));
        if (ubPeruService.findOne(99) == null)
            ubPeruService.save(new UbPeru("020105", "Áncash", "Huaraz", "Independencia"));
        if (ubPeruService.findOne(100) == null) ubPeruService.save(new UbPeru("020106", "Áncash", "Huaraz", "Jangas"));
        if (ubPeruService.findOne(101) == null)
            ubPeruService.save(new UbPeru("020107", "Áncash", "Huaraz", "La Libertad"));
        if (ubPeruService.findOne(102) == null) ubPeruService.save(new UbPeru("020108", "Áncash", "Huaraz", "Olleros"));
        if (ubPeruService.findOne(103) == null)
            ubPeruService.save(new UbPeru("020109", "Áncash", "Huaraz", "Pampas Grande"));
        if (ubPeruService.findOne(104) == null)
            ubPeruService.save(new UbPeru("020110", "Áncash", "Huaraz", "Pariacoto"));
        if (ubPeruService.findOne(105) == null) ubPeruService.save(new UbPeru("020111", "Áncash", "Huaraz", "Pira"));
        if (ubPeruService.findOne(106) == null) ubPeruService.save(new UbPeru("020112", "Áncash", "Huaraz", "Tarica"));
        if (ubPeruService.findOne(107) == null) ubPeruService.save(new UbPeru("020200", "Áncash", "Aija", ""));
        if (ubPeruService.findOne(108) == null) ubPeruService.save(new UbPeru("020201", "Áncash", "Aija", "Aija"));
        if (ubPeruService.findOne(109) == null) ubPeruService.save(new UbPeru("020202", "Áncash", "Aija", "Coris"));
        if (ubPeruService.findOne(110) == null) ubPeruService.save(new UbPeru("020203", "Áncash", "Aija", "Huacllan"));
        if (ubPeruService.findOne(111) == null) ubPeruService.save(new UbPeru("020204", "Áncash", "Aija", "La Merced"));
        if (ubPeruService.findOne(112) == null) ubPeruService.save(new UbPeru("020205", "Áncash", "Aija", "Succha"));
        if (ubPeruService.findOne(113) == null)
            ubPeruService.save(new UbPeru("020300", "Áncash", "Antonio Raymondi", ""));
        if (ubPeruService.findOne(114) == null)
            ubPeruService.save(new UbPeru("020301", "Áncash", "Antonio Raymondi", "Llamellin"));
        if (ubPeruService.findOne(115) == null)
            ubPeruService.save(new UbPeru("020302", "Áncash", "Antonio Raymondi", "Aczo"));
        if (ubPeruService.findOne(116) == null)
            ubPeruService.save(new UbPeru("020303", "Áncash", "Antonio Raymondi", "Chaccho"));
        if (ubPeruService.findOne(117) == null)
            ubPeruService.save(new UbPeru("020304", "Áncash", "Antonio Raymondi", "Chingas"));
        if (ubPeruService.findOne(118) == null)
            ubPeruService.save(new UbPeru("020305", "Áncash", "Antonio Raymondi", "Mirgas"));
        if (ubPeruService.findOne(119) == null)
            ubPeruService.save(new UbPeru("020306", "Áncash", "Antonio Raymondi", "San Juan de Rontoy"));
        if (ubPeruService.findOne(120) == null) ubPeruService.save(new UbPeru("020400", "Áncash", "Asunción", ""));
        if (ubPeruService.findOne(121) == null)
            ubPeruService.save(new UbPeru("020401", "Áncash", "Asunción", "Chacas"));
        if (ubPeruService.findOne(122) == null)
            ubPeruService.save(new UbPeru("020402", "Áncash", "Asunción", "Acochaca"));
        if (ubPeruService.findOne(123) == null) ubPeruService.save(new UbPeru("020500", "Áncash", "Bolognesi", ""));
        if (ubPeruService.findOne(124) == null)
            ubPeruService.save(new UbPeru("020501", "Áncash", "Bolognesi", "Chiquian"));
        if (ubPeruService.findOne(125) == null)
            ubPeruService.save(new UbPeru("020502", "Áncash", "Bolognesi", "Abelardo Pardo Lezameta"));
        if (ubPeruService.findOne(126) == null)
            ubPeruService.save(new UbPeru("020503", "Áncash", "Bolognesi", "Antonio Raymondi"));
        if (ubPeruService.findOne(127) == null)
            ubPeruService.save(new UbPeru("020504", "Áncash", "Bolognesi", "Aquia"));
        if (ubPeruService.findOne(128) == null)
            ubPeruService.save(new UbPeru("020505", "Áncash", "Bolognesi", "Cajacay"));
        if (ubPeruService.findOne(129) == null)
            ubPeruService.save(new UbPeru("020506", "Áncash", "Bolognesi", "Canis"));
        if (ubPeruService.findOne(130) == null)
            ubPeruService.save(new UbPeru("020507", "Áncash", "Bolognesi", "Colquioc"));
        if (ubPeruService.findOne(131) == null)
            ubPeruService.save(new UbPeru("020508", "Áncash", "Bolognesi", "Huallanca"));
        if (ubPeruService.findOne(132) == null)
            ubPeruService.save(new UbPeru("020509", "Áncash", "Bolognesi", "Huasta"));
        if (ubPeruService.findOne(133) == null)
            ubPeruService.save(new UbPeru("020510", "Áncash", "Bolognesi", "Huayllacayan"));
        if (ubPeruService.findOne(134) == null)
            ubPeruService.save(new UbPeru("020511", "Áncash", "Bolognesi", "La Primavera"));
        if (ubPeruService.findOne(135) == null)
            ubPeruService.save(new UbPeru("020512", "Áncash", "Bolognesi", "Mangas"));
        if (ubPeruService.findOne(136) == null)
            ubPeruService.save(new UbPeru("020513", "Áncash", "Bolognesi", "Pacllon"));
        if (ubPeruService.findOne(137) == null)
            ubPeruService.save(new UbPeru("020514", "Áncash", "Bolognesi", "San Miguel de Corpanqui"));
        if (ubPeruService.findOne(138) == null)
            ubPeruService.save(new UbPeru("020515", "Áncash", "Bolognesi", "Ticllos"));
        if (ubPeruService.findOne(139) == null) ubPeruService.save(new UbPeru("020600", "Áncash", "Carhuaz", ""));
        if (ubPeruService.findOne(140) == null)
            ubPeruService.save(new UbPeru("020601", "Áncash", "Carhuaz", "Carhuaz"));
        if (ubPeruService.findOne(141) == null)
            ubPeruService.save(new UbPeru("020602", "Áncash", "Carhuaz", "Acopampa"));
        if (ubPeruService.findOne(142) == null)
            ubPeruService.save(new UbPeru("020603", "Áncash", "Carhuaz", "Amashca"));
        if (ubPeruService.findOne(143) == null) ubPeruService.save(new UbPeru("020604", "Áncash", "Carhuaz", "Anta"));
        if (ubPeruService.findOne(144) == null)
            ubPeruService.save(new UbPeru("020605", "Áncash", "Carhuaz", "Ataquero"));
        if (ubPeruService.findOne(145) == null)
            ubPeruService.save(new UbPeru("020606", "Áncash", "Carhuaz", "Marcara"));
        if (ubPeruService.findOne(146) == null)
            ubPeruService.save(new UbPeru("020607", "Áncash", "Carhuaz", "Pariahuanca"));
        if (ubPeruService.findOne(147) == null)
            ubPeruService.save(new UbPeru("020608", "Áncash", "Carhuaz", "San Miguel de Aco"));
        if (ubPeruService.findOne(148) == null) ubPeruService.save(new UbPeru("020609", "Áncash", "Carhuaz", "Shilla"));
        if (ubPeruService.findOne(149) == null) ubPeruService.save(new UbPeru("020610", "Áncash", "Carhuaz", "Tinco"));
        if (ubPeruService.findOne(150) == null) ubPeruService.save(new UbPeru("020611", "Áncash", "Carhuaz", "Yungar"));
        if (ubPeruService.findOne(151) == null)
            ubPeruService.save(new UbPeru("020700", "Áncash", "Carlos Fermín Fitzcarrald", ""));
        if (ubPeruService.findOne(152) == null)
            ubPeruService.save(new UbPeru("020701", "Áncash", "Carlos Fermín Fitzcarrald", "San Luis"));
        if (ubPeruService.findOne(153) == null)
            ubPeruService.save(new UbPeru("020702", "Áncash", "Carlos Fermín Fitzcarrald", "San Nicolás"));
        if (ubPeruService.findOne(154) == null)
            ubPeruService.save(new UbPeru("020703", "Áncash", "Carlos Fermín Fitzcarrald", "Yauya"));
        if (ubPeruService.findOne(155) == null) ubPeruService.save(new UbPeru("020800", "Áncash", "Casma", ""));
        if (ubPeruService.findOne(156) == null) ubPeruService.save(new UbPeru("020801", "Áncash", "Casma", "Casma"));
        if (ubPeruService.findOne(157) == null)
            ubPeruService.save(new UbPeru("020802", "Áncash", "Casma", "Buena Vista Alta"));
        if (ubPeruService.findOne(158) == null)
            ubPeruService.save(new UbPeru("020803", "Áncash", "Casma", "Comandante Noel"));
        if (ubPeruService.findOne(159) == null) ubPeruService.save(new UbPeru("020804", "Áncash", "Casma", "Yautan"));
        if (ubPeruService.findOne(160) == null) ubPeruService.save(new UbPeru("020900", "Áncash", "Corongo", ""));
        if (ubPeruService.findOne(161) == null)
            ubPeruService.save(new UbPeru("020901", "Áncash", "Corongo", "Corongo"));
        if (ubPeruService.findOne(162) == null) ubPeruService.save(new UbPeru("020902", "Áncash", "Corongo", "Aco"));
        if (ubPeruService.findOne(163) == null) ubPeruService.save(new UbPeru("020903", "Áncash", "Corongo", "Bambas"));
        if (ubPeruService.findOne(164) == null) ubPeruService.save(new UbPeru("020904", "Áncash", "Corongo", "Cusca"));
        if (ubPeruService.findOne(165) == null)
            ubPeruService.save(new UbPeru("020905", "Áncash", "Corongo", "La Pampa"));
        if (ubPeruService.findOne(166) == null) ubPeruService.save(new UbPeru("020906", "Áncash", "Corongo", "Yanac"));
        if (ubPeruService.findOne(167) == null) ubPeruService.save(new UbPeru("020907", "Áncash", "Corongo", "Yupan"));
        if (ubPeruService.findOne(168) == null) ubPeruService.save(new UbPeru("021000", "Áncash", "Huari", ""));
        if (ubPeruService.findOne(169) == null) ubPeruService.save(new UbPeru("021001", "Áncash", "Huari", "Huari"));
        if (ubPeruService.findOne(170) == null) ubPeruService.save(new UbPeru("021002", "Áncash", "Huari", "Anra"));
        if (ubPeruService.findOne(171) == null) ubPeruService.save(new UbPeru("021003", "Áncash", "Huari", "Cajay"));
        if (ubPeruService.findOne(172) == null)
            ubPeruService.save(new UbPeru("021004", "Áncash", "Huari", "Chavin de Huantar"));
        if (ubPeruService.findOne(173) == null) ubPeruService.save(new UbPeru("021005", "Áncash", "Huari", "Huacachi"));
        if (ubPeruService.findOne(174) == null) ubPeruService.save(new UbPeru("021006", "Áncash", "Huari", "Huacchis"));
        if (ubPeruService.findOne(175) == null) ubPeruService.save(new UbPeru("021007", "Áncash", "Huari", "Huachis"));
        if (ubPeruService.findOne(176) == null) ubPeruService.save(new UbPeru("021008", "Áncash", "Huari", "Huantar"));
        if (ubPeruService.findOne(177) == null) ubPeruService.save(new UbPeru("021009", "Áncash", "Huari", "Masin"));
        if (ubPeruService.findOne(178) == null) ubPeruService.save(new UbPeru("021010", "Áncash", "Huari", "Paucas"));
        if (ubPeruService.findOne(179) == null) ubPeruService.save(new UbPeru("021011", "Áncash", "Huari", "Ponto"));
        if (ubPeruService.findOne(180) == null)
            ubPeruService.save(new UbPeru("021012", "Áncash", "Huari", "Rahuapampa"));
        if (ubPeruService.findOne(181) == null) ubPeruService.save(new UbPeru("021013", "Áncash", "Huari", "Rapayan"));
        if (ubPeruService.findOne(182) == null)
            ubPeruService.save(new UbPeru("021014", "Áncash", "Huari", "San Marcos"));
        if (ubPeruService.findOne(183) == null)
            ubPeruService.save(new UbPeru("021015", "Áncash", "Huari", "San Pedro de Chana"));
        if (ubPeruService.findOne(184) == null) ubPeruService.save(new UbPeru("021016", "Áncash", "Huari", "Uco"));
        if (ubPeruService.findOne(185) == null) ubPeruService.save(new UbPeru("021100", "Áncash", "Huarmey", ""));
        if (ubPeruService.findOne(186) == null)
            ubPeruService.save(new UbPeru("021101", "Áncash", "Huarmey", "Huarmey"));
        if (ubPeruService.findOne(187) == null)
            ubPeruService.save(new UbPeru("021102", "Áncash", "Huarmey", "Cochapeti"));
        if (ubPeruService.findOne(188) == null)
            ubPeruService.save(new UbPeru("021103", "Áncash", "Huarmey", "Culebras"));
        if (ubPeruService.findOne(189) == null) ubPeruService.save(new UbPeru("021104", "Áncash", "Huarmey", "Huayan"));
        if (ubPeruService.findOne(190) == null) ubPeruService.save(new UbPeru("021105", "Áncash", "Huarmey", "Malvas"));
        if (ubPeruService.findOne(191) == null) ubPeruService.save(new UbPeru("021200", "Áncash", "Huaylas", ""));
        if (ubPeruService.findOne(192) == null) ubPeruService.save(new UbPeru("021201", "Áncash", "Huaylas", "Caraz"));
        if (ubPeruService.findOne(193) == null)
            ubPeruService.save(new UbPeru("021202", "Áncash", "Huaylas", "Huallanca"));
        if (ubPeruService.findOne(194) == null) ubPeruService.save(new UbPeru("021203", "Áncash", "Huaylas", "Huata"));
        if (ubPeruService.findOne(195) == null)
            ubPeruService.save(new UbPeru("021204", "Áncash", "Huaylas", "Huaylas"));
        if (ubPeruService.findOne(196) == null) ubPeruService.save(new UbPeru("021205", "Áncash", "Huaylas", "Mato"));
        if (ubPeruService.findOne(197) == null)
            ubPeruService.save(new UbPeru("021206", "Áncash", "Huaylas", "Pamparomas"));
        if (ubPeruService.findOne(198) == null)
            ubPeruService.save(new UbPeru("021207", "Áncash", "Huaylas", "Pueblo Libre"));
        if (ubPeruService.findOne(199) == null)
            ubPeruService.save(new UbPeru("021208", "Áncash", "Huaylas", "Santa Cruz"));
        if (ubPeruService.findOne(200) == null)
            ubPeruService.save(new UbPeru("021209", "Áncash", "Huaylas", "Santo Toribio"));
        if (ubPeruService.findOne(201) == null)
            ubPeruService.save(new UbPeru("021210", "Áncash", "Huaylas", "Yuracmarca"));
        if (ubPeruService.findOne(202) == null)
            ubPeruService.save(new UbPeru("021300", "Áncash", "Mariscal Luzuriaga", ""));
        if (ubPeruService.findOne(203) == null)
            ubPeruService.save(new UbPeru("021301", "Áncash", "Mariscal Luzuriaga", "Piscobamba"));
        if (ubPeruService.findOne(204) == null)
            ubPeruService.save(new UbPeru("021302", "Áncash", "Mariscal Luzuriaga", "Casca"));
        if (ubPeruService.findOne(205) == null)
            ubPeruService.save(new UbPeru("021303", "Áncash", "Mariscal Luzuriaga", "Eleazar Guzmán Barron"));
        if (ubPeruService.findOne(206) == null)
            ubPeruService.save(new UbPeru("021304", "Áncash", "Mariscal Luzuriaga", "Fidel Olivas Escudero"));
        if (ubPeruService.findOne(207) == null)
            ubPeruService.save(new UbPeru("021305", "Áncash", "Mariscal Luzuriaga", "Llama"));
        if (ubPeruService.findOne(208) == null)
            ubPeruService.save(new UbPeru("021306", "Áncash", "Mariscal Luzuriaga", "Llumpa"));
        if (ubPeruService.findOne(209) == null)
            ubPeruService.save(new UbPeru("021307", "Áncash", "Mariscal Luzuriaga", "Lucma"));
        if (ubPeruService.findOne(210) == null)
            ubPeruService.save(new UbPeru("021308", "Áncash", "Mariscal Luzuriaga", "Musga"));
        if (ubPeruService.findOne(211) == null) ubPeruService.save(new UbPeru("021400", "Áncash", "Ocros", ""));
        if (ubPeruService.findOne(212) == null) ubPeruService.save(new UbPeru("021401", "Áncash", "Ocros", "Ocros"));
        if (ubPeruService.findOne(213) == null) ubPeruService.save(new UbPeru("021402", "Áncash", "Ocros", "Acas"));
        if (ubPeruService.findOne(214) == null)
            ubPeruService.save(new UbPeru("021403", "Áncash", "Ocros", "Cajamarquilla"));
        if (ubPeruService.findOne(215) == null)
            ubPeruService.save(new UbPeru("021404", "Áncash", "Ocros", "Carhuapampa"));
        if (ubPeruService.findOne(216) == null) ubPeruService.save(new UbPeru("021405", "Áncash", "Ocros", "Cochas"));
        if (ubPeruService.findOne(217) == null) ubPeruService.save(new UbPeru("021406", "Áncash", "Ocros", "Congas"));
        if (ubPeruService.findOne(218) == null) ubPeruService.save(new UbPeru("021407", "Áncash", "Ocros", "Llipa"));
        if (ubPeruService.findOne(219) == null)
            ubPeruService.save(new UbPeru("021408", "Áncash", "Ocros", "San Cristóbal de Rajan"));
        if (ubPeruService.findOne(220) == null)
            ubPeruService.save(new UbPeru("021409", "Áncash", "Ocros", "San Pedro"));
        if (ubPeruService.findOne(221) == null)
            ubPeruService.save(new UbPeru("021410", "Áncash", "Ocros", "Santiago de Chilcas"));
        if (ubPeruService.findOne(222) == null) ubPeruService.save(new UbPeru("021500", "Áncash", "Pallasca", ""));
        if (ubPeruService.findOne(223) == null)
            ubPeruService.save(new UbPeru("021501", "Áncash", "Pallasca", "Cabana"));
        if (ubPeruService.findOne(224) == null)
            ubPeruService.save(new UbPeru("021502", "Áncash", "Pallasca", "Bolognesi"));
        if (ubPeruService.findOne(225) == null)
            ubPeruService.save(new UbPeru("021503", "Áncash", "Pallasca", "Conchucos"));
        if (ubPeruService.findOne(226) == null)
            ubPeruService.save(new UbPeru("021504", "Áncash", "Pallasca", "Huacaschuque"));
        if (ubPeruService.findOne(227) == null)
            ubPeruService.save(new UbPeru("021505", "Áncash", "Pallasca", "Huandoval"));
        if (ubPeruService.findOne(228) == null)
            ubPeruService.save(new UbPeru("021506", "Áncash", "Pallasca", "Lacabamba"));
        if (ubPeruService.findOne(229) == null) ubPeruService.save(new UbPeru("021507", "Áncash", "Pallasca", "Llapo"));
        if (ubPeruService.findOne(230) == null)
            ubPeruService.save(new UbPeru("021508", "Áncash", "Pallasca", "Pallasca"));
        if (ubPeruService.findOne(231) == null)
            ubPeruService.save(new UbPeru("021509", "Áncash", "Pallasca", "Pampas"));
        if (ubPeruService.findOne(232) == null)
            ubPeruService.save(new UbPeru("021510", "Áncash", "Pallasca", "Santa Rosa"));
        if (ubPeruService.findOne(233) == null) ubPeruService.save(new UbPeru("021511", "Áncash", "Pallasca", "Tauca"));
        if (ubPeruService.findOne(234) == null) ubPeruService.save(new UbPeru("021600", "Áncash", "Pomabamba", ""));
        if (ubPeruService.findOne(235) == null)
            ubPeruService.save(new UbPeru("021601", "Áncash", "Pomabamba", "Pomabamba"));
        if (ubPeruService.findOne(236) == null)
            ubPeruService.save(new UbPeru("021602", "Áncash", "Pomabamba", "Huayllan"));
        if (ubPeruService.findOne(237) == null)
            ubPeruService.save(new UbPeru("021603", "Áncash", "Pomabamba", "Parobamba"));
        if (ubPeruService.findOne(238) == null)
            ubPeruService.save(new UbPeru("021604", "Áncash", "Pomabamba", "Quinuabamba"));
        if (ubPeruService.findOne(239) == null) ubPeruService.save(new UbPeru("021700", "Áncash", "Recuay", ""));
        if (ubPeruService.findOne(240) == null) ubPeruService.save(new UbPeru("021701", "Áncash", "Recuay", "Recuay"));
        if (ubPeruService.findOne(241) == null) ubPeruService.save(new UbPeru("021702", "Áncash", "Recuay", "Catac"));
        if (ubPeruService.findOne(242) == null)
            ubPeruService.save(new UbPeru("021703", "Áncash", "Recuay", "Cotaparaco"));
        if (ubPeruService.findOne(243) == null)
            ubPeruService.save(new UbPeru("021704", "Áncash", "Recuay", "Huayllapampa"));
        if (ubPeruService.findOne(244) == null)
            ubPeruService.save(new UbPeru("021705", "Áncash", "Recuay", "Llacllin"));
        if (ubPeruService.findOne(245) == null) ubPeruService.save(new UbPeru("021706", "Áncash", "Recuay", "Marca"));
        if (ubPeruService.findOne(246) == null)
            ubPeruService.save(new UbPeru("021707", "Áncash", "Recuay", "Pampas Chico"));
        if (ubPeruService.findOne(247) == null) ubPeruService.save(new UbPeru("021708", "Áncash", "Recuay", "Pararin"));
        if (ubPeruService.findOne(248) == null)
            ubPeruService.save(new UbPeru("021709", "Áncash", "Recuay", "Tapacocha"));
        if (ubPeruService.findOne(249) == null)
            ubPeruService.save(new UbPeru("021710", "Áncash", "Recuay", "Ticapampa"));
        if (ubPeruService.findOne(250) == null) ubPeruService.save(new UbPeru("021800", "Áncash", "Santa", ""));
        if (ubPeruService.findOne(251) == null) ubPeruService.save(new UbPeru("021801", "Áncash", "Santa", "Chimbote"));
        if (ubPeruService.findOne(252) == null)
            ubPeruService.save(new UbPeru("021802", "Áncash", "Santa", "Cáceres del Perú"));
        if (ubPeruService.findOne(253) == null) ubPeruService.save(new UbPeru("021803", "Áncash", "Santa", "Coishco"));
        if (ubPeruService.findOne(254) == null) ubPeruService.save(new UbPeru("021804", "Áncash", "Santa", "Macate"));
        if (ubPeruService.findOne(255) == null) ubPeruService.save(new UbPeru("021805", "Áncash", "Santa", "Moro"));
        if (ubPeruService.findOne(256) == null) ubPeruService.save(new UbPeru("021806", "Áncash", "Santa", "Nepeña"));
        if (ubPeruService.findOne(257) == null) ubPeruService.save(new UbPeru("021807", "Áncash", "Santa", "Samanco"));
        if (ubPeruService.findOne(258) == null) ubPeruService.save(new UbPeru("021808", "Áncash", "Santa", "Santa"));
        if (ubPeruService.findOne(259) == null)
            ubPeruService.save(new UbPeru("021809", "Áncash", "Santa", "Nuevo Chimbote"));
        if (ubPeruService.findOne(260) == null) ubPeruService.save(new UbPeru("021900", "Áncash", "Sihuas", ""));
        if (ubPeruService.findOne(261) == null) ubPeruService.save(new UbPeru("021901", "Áncash", "Sihuas", "Sihuas"));
        if (ubPeruService.findOne(262) == null)
            ubPeruService.save(new UbPeru("021902", "Áncash", "Sihuas", "Acobamba"));
        if (ubPeruService.findOne(263) == null)
            ubPeruService.save(new UbPeru("021903", "Áncash", "Sihuas", "Alfonso Ugarte"));
        if (ubPeruService.findOne(264) == null)
            ubPeruService.save(new UbPeru("021904", "Áncash", "Sihuas", "Cashapampa"));
        if (ubPeruService.findOne(265) == null)
            ubPeruService.save(new UbPeru("021905", "Áncash", "Sihuas", "Chingalpo"));
        if (ubPeruService.findOne(266) == null)
            ubPeruService.save(new UbPeru("021906", "Áncash", "Sihuas", "Huayllabamba"));
        if (ubPeruService.findOne(267) == null) ubPeruService.save(new UbPeru("021907", "Áncash", "Sihuas", "Quiches"));
        if (ubPeruService.findOne(268) == null) ubPeruService.save(new UbPeru("021908", "Áncash", "Sihuas", "Ragash"));
        if (ubPeruService.findOne(269) == null)
            ubPeruService.save(new UbPeru("021909", "Áncash", "Sihuas", "San Juan"));
        if (ubPeruService.findOne(270) == null)
            ubPeruService.save(new UbPeru("021910", "Áncash", "Sihuas", "Sicsibamba"));
        if (ubPeruService.findOne(271) == null) ubPeruService.save(new UbPeru("022000", "Áncash", "Yungay", ""));
        if (ubPeruService.findOne(272) == null) ubPeruService.save(new UbPeru("022001", "Áncash", "Yungay", "Yungay"));
        if (ubPeruService.findOne(273) == null)
            ubPeruService.save(new UbPeru("022002", "Áncash", "Yungay", "Cascapara"));
        if (ubPeruService.findOne(274) == null) ubPeruService.save(new UbPeru("022003", "Áncash", "Yungay", "Mancos"));
        if (ubPeruService.findOne(275) == null)
            ubPeruService.save(new UbPeru("022004", "Áncash", "Yungay", "Matacoto"));
        if (ubPeruService.findOne(276) == null) ubPeruService.save(new UbPeru("022005", "Áncash", "Yungay", "Quillo"));
        if (ubPeruService.findOne(277) == null)
            ubPeruService.save(new UbPeru("022006", "Áncash", "Yungay", "Ranrahirca"));
        if (ubPeruService.findOne(278) == null) ubPeruService.save(new UbPeru("022007", "Áncash", "Yungay", "Shupluy"));
        if (ubPeruService.findOne(279) == null) ubPeruService.save(new UbPeru("022008", "Áncash", "Yungay", "Yanama"));
        if (ubPeruService.findOne(280) == null) ubPeruService.save(new UbPeru("030000", "Apurímac", "", ""));
        if (ubPeruService.findOne(281) == null) ubPeruService.save(new UbPeru("030100", "Apurímac", "Abancay", ""));
        if (ubPeruService.findOne(282) == null)
            ubPeruService.save(new UbPeru("030101", "Apurímac", "Abancay", "Abancay"));
        if (ubPeruService.findOne(283) == null)
            ubPeruService.save(new UbPeru("030102", "Apurímac", "Abancay", "Chacoche"));
        if (ubPeruService.findOne(284) == null)
            ubPeruService.save(new UbPeru("030103", "Apurímac", "Abancay", "Circa"));
        if (ubPeruService.findOne(285) == null)
            ubPeruService.save(new UbPeru("030104", "Apurímac", "Abancay", "Curahuasi"));
        if (ubPeruService.findOne(286) == null)
            ubPeruService.save(new UbPeru("030105", "Apurímac", "Abancay", "Huanipaca"));
        if (ubPeruService.findOne(287) == null)
            ubPeruService.save(new UbPeru("030106", "Apurímac", "Abancay", "Lambrama"));
        if (ubPeruService.findOne(288) == null)
            ubPeruService.save(new UbPeru("030107", "Apurímac", "Abancay", "Pichirhua"));
        if (ubPeruService.findOne(289) == null)
            ubPeruService.save(new UbPeru("030108", "Apurímac", "Abancay", "San Pedro de Cachora"));
        if (ubPeruService.findOne(290) == null)
            ubPeruService.save(new UbPeru("030109", "Apurímac", "Abancay", "Tamburco"));
        if (ubPeruService.findOne(291) == null) ubPeruService.save(new UbPeru("030200", "Apurímac", "Andahuaylas", ""));
        if (ubPeruService.findOne(292) == null)
            ubPeruService.save(new UbPeru("030201", "Apurímac", "Andahuaylas", "Andahuaylas"));
        if (ubPeruService.findOne(293) == null)
            ubPeruService.save(new UbPeru("030202", "Apurímac", "Andahuaylas", "Andarapa"));
        if (ubPeruService.findOne(294) == null)
            ubPeruService.save(new UbPeru("030203", "Apurímac", "Andahuaylas", "Chiara"));
        if (ubPeruService.findOne(295) == null)
            ubPeruService.save(new UbPeru("030204", "Apurímac", "Andahuaylas", "Huancarama"));
        if (ubPeruService.findOne(296) == null)
            ubPeruService.save(new UbPeru("030205", "Apurímac", "Andahuaylas", "Huancaray"));
        if (ubPeruService.findOne(297) == null)
            ubPeruService.save(new UbPeru("030206", "Apurímac", "Andahuaylas", "Huayana"));
        if (ubPeruService.findOne(298) == null)
            ubPeruService.save(new UbPeru("030207", "Apurímac", "Andahuaylas", "Kishuara"));
        if (ubPeruService.findOne(299) == null)
            ubPeruService.save(new UbPeru("030208", "Apurímac", "Andahuaylas", "Pacobamba"));
        if (ubPeruService.findOne(300) == null)
            ubPeruService.save(new UbPeru("030209", "Apurímac", "Andahuaylas", "Pacucha"));
        if (ubPeruService.findOne(301) == null)
            ubPeruService.save(new UbPeru("030210", "Apurímac", "Andahuaylas", "Pampachiri"));
        if (ubPeruService.findOne(302) == null)
            ubPeruService.save(new UbPeru("030211", "Apurímac", "Andahuaylas", "Pomacocha"));
        if (ubPeruService.findOne(303) == null)
            ubPeruService.save(new UbPeru("030212", "Apurímac", "Andahuaylas", "San Antonio de Cachi"));
        if (ubPeruService.findOne(304) == null)
            ubPeruService.save(new UbPeru("030213", "Apurímac", "Andahuaylas", "San Jerónimo"));
        if (ubPeruService.findOne(305) == null)
            ubPeruService.save(new UbPeru("030214", "Apurímac", "Andahuaylas", "San Miguel de Chaccrampa"));
        if (ubPeruService.findOne(306) == null)
            ubPeruService.save(new UbPeru("030215", "Apurímac", "Andahuaylas", "Santa María de Chicmo"));
        if (ubPeruService.findOne(307) == null)
            ubPeruService.save(new UbPeru("030216", "Apurímac", "Andahuaylas", "Talavera"));
        if (ubPeruService.findOne(308) == null)
            ubPeruService.save(new UbPeru("030217", "Apurímac", "Andahuaylas", "Tumay Huaraca"));
        if (ubPeruService.findOne(309) == null)
            ubPeruService.save(new UbPeru("030218", "Apurímac", "Andahuaylas", "Turpo"));
        if (ubPeruService.findOne(310) == null)
            ubPeruService.save(new UbPeru("030219", "Apurímac", "Andahuaylas", "Kaquiabamba"));
        if (ubPeruService.findOne(311) == null)
            ubPeruService.save(new UbPeru("030220", "Apurímac", "Andahuaylas", "José María Arguedas"));
        if (ubPeruService.findOne(312) == null) ubPeruService.save(new UbPeru("030300", "Apurímac", "Antabamba", ""));
        if (ubPeruService.findOne(313) == null)
            ubPeruService.save(new UbPeru("030301", "Apurímac", "Antabamba", "Antabamba"));
        if (ubPeruService.findOne(314) == null)
            ubPeruService.save(new UbPeru("030302", "Apurímac", "Antabamba", "El Oro"));
        if (ubPeruService.findOne(315) == null)
            ubPeruService.save(new UbPeru("030303", "Apurímac", "Antabamba", "Huaquirca"));
        if (ubPeruService.findOne(316) == null)
            ubPeruService.save(new UbPeru("030304", "Apurímac", "Antabamba", "Juan Espinoza Medrano"));
        if (ubPeruService.findOne(317) == null)
            ubPeruService.save(new UbPeru("030305", "Apurímac", "Antabamba", "Oropesa"));
        if (ubPeruService.findOne(318) == null)
            ubPeruService.save(new UbPeru("030306", "Apurímac", "Antabamba", "Pachaconas"));
        if (ubPeruService.findOne(319) == null)
            ubPeruService.save(new UbPeru("030307", "Apurímac", "Antabamba", "Sabaino"));
        if (ubPeruService.findOne(320) == null) ubPeruService.save(new UbPeru("030400", "Apurímac", "Aymaraes", ""));
        if (ubPeruService.findOne(321) == null)
            ubPeruService.save(new UbPeru("030401", "Apurímac", "Aymaraes", "Chalhuanca"));
        if (ubPeruService.findOne(322) == null)
            ubPeruService.save(new UbPeru("030402", "Apurímac", "Aymaraes", "Capaya"));
        if (ubPeruService.findOne(323) == null)
            ubPeruService.save(new UbPeru("030403", "Apurímac", "Aymaraes", "Caraybamba"));
        if (ubPeruService.findOne(324) == null)
            ubPeruService.save(new UbPeru("030404", "Apurímac", "Aymaraes", "Chapimarca"));
        if (ubPeruService.findOne(325) == null)
            ubPeruService.save(new UbPeru("030405", "Apurímac", "Aymaraes", "Colcabamba"));
        if (ubPeruService.findOne(326) == null)
            ubPeruService.save(new UbPeru("030406", "Apurímac", "Aymaraes", "Cotaruse"));
        if (ubPeruService.findOne(327) == null)
            ubPeruService.save(new UbPeru("030407", "Apurímac", "Aymaraes", "Ihuayllo"));
        if (ubPeruService.findOne(328) == null)
            ubPeruService.save(new UbPeru("030408", "Apurímac", "Aymaraes", "Justo Apu Sahuaraura"));
        if (ubPeruService.findOne(329) == null)
            ubPeruService.save(new UbPeru("030409", "Apurímac", "Aymaraes", "Lucre"));
        if (ubPeruService.findOne(330) == null)
            ubPeruService.save(new UbPeru("030410", "Apurímac", "Aymaraes", "Pocohuanca"));
        if (ubPeruService.findOne(331) == null)
            ubPeruService.save(new UbPeru("030411", "Apurímac", "Aymaraes", "San Juan de Chacña"));
        if (ubPeruService.findOne(332) == null)
            ubPeruService.save(new UbPeru("030412", "Apurímac", "Aymaraes", "Sañayca"));
        if (ubPeruService.findOne(333) == null)
            ubPeruService.save(new UbPeru("030413", "Apurímac", "Aymaraes", "Soraya"));
        if (ubPeruService.findOne(334) == null)
            ubPeruService.save(new UbPeru("030414", "Apurímac", "Aymaraes", "Tapairihua"));
        if (ubPeruService.findOne(335) == null)
            ubPeruService.save(new UbPeru("030415", "Apurímac", "Aymaraes", "Tintay"));
        if (ubPeruService.findOne(336) == null)
            ubPeruService.save(new UbPeru("030416", "Apurímac", "Aymaraes", "Toraya"));
        if (ubPeruService.findOne(337) == null)
            ubPeruService.save(new UbPeru("030417", "Apurímac", "Aymaraes", "Yanaca"));
        if (ubPeruService.findOne(338) == null) ubPeruService.save(new UbPeru("030500", "Apurímac", "Cotabambas", ""));
        if (ubPeruService.findOne(339) == null)
            ubPeruService.save(new UbPeru("030501", "Apurímac", "Cotabambas", "Tambobamba"));
        if (ubPeruService.findOne(340) == null)
            ubPeruService.save(new UbPeru("030502", "Apurímac", "Cotabambas", "Cotabambas"));
        if (ubPeruService.findOne(341) == null)
            ubPeruService.save(new UbPeru("030503", "Apurímac", "Cotabambas", "Coyllurqui"));
        if (ubPeruService.findOne(342) == null)
            ubPeruService.save(new UbPeru("030504", "Apurímac", "Cotabambas", "Haquira"));
        if (ubPeruService.findOne(343) == null)
            ubPeruService.save(new UbPeru("030505", "Apurímac", "Cotabambas", "Mara"));
        if (ubPeruService.findOne(344) == null)
            ubPeruService.save(new UbPeru("030506", "Apurímac", "Cotabambas", "Challhuahuacho"));
        if (ubPeruService.findOne(345) == null) ubPeruService.save(new UbPeru("030600", "Apurímac", "Chincheros", ""));
        if (ubPeruService.findOne(346) == null)
            ubPeruService.save(new UbPeru("030601", "Apurímac", "Chincheros", "Chincheros"));
        if (ubPeruService.findOne(347) == null)
            ubPeruService.save(new UbPeru("030602", "Apurímac", "Chincheros", "Anco_Huallo"));
        if (ubPeruService.findOne(348) == null)
            ubPeruService.save(new UbPeru("030603", "Apurímac", "Chincheros", "Cocharcas"));
        if (ubPeruService.findOne(349) == null)
            ubPeruService.save(new UbPeru("030604", "Apurímac", "Chincheros", "Huaccana"));
        if (ubPeruService.findOne(350) == null)
            ubPeruService.save(new UbPeru("030605", "Apurímac", "Chincheros", "Ocobamba"));
        if (ubPeruService.findOne(351) == null)
            ubPeruService.save(new UbPeru("030606", "Apurímac", "Chincheros", "Ongoy"));
        if (ubPeruService.findOne(352) == null)
            ubPeruService.save(new UbPeru("030607", "Apurímac", "Chincheros", "Uranmarca"));
        if (ubPeruService.findOne(353) == null)
            ubPeruService.save(new UbPeru("030608", "Apurímac", "Chincheros", "Ranracancha"));
        if (ubPeruService.findOne(354) == null)
            ubPeruService.save(new UbPeru("030609", "Apurímac", "Chincheros", "Rocchacc"));
        if (ubPeruService.findOne(355) == null)
            ubPeruService.save(new UbPeru("030610", "Apurímac", "Chincheros", "El Porvenir"));
        if (ubPeruService.findOne(356) == null) ubPeruService.save(new UbPeru("030700", "Apurímac", "Grau", ""));
        if (ubPeruService.findOne(357) == null)
            ubPeruService.save(new UbPeru("030701", "Apurímac", "Grau", "Chuquibambilla"));
        if (ubPeruService.findOne(358) == null)
            ubPeruService.save(new UbPeru("030702", "Apurímac", "Grau", "Curpahuasi"));
        if (ubPeruService.findOne(359) == null) ubPeruService.save(new UbPeru("030703", "Apurímac", "Grau", "Gamarra"));
        if (ubPeruService.findOne(360) == null)
            ubPeruService.save(new UbPeru("030704", "Apurímac", "Grau", "Huayllati"));
        if (ubPeruService.findOne(361) == null) ubPeruService.save(new UbPeru("030705", "Apurímac", "Grau", "Mamara"));
        if (ubPeruService.findOne(362) == null)
            ubPeruService.save(new UbPeru("030706", "Apurímac", "Grau", "Micaela Bastidas"));
        if (ubPeruService.findOne(363) == null)
            ubPeruService.save(new UbPeru("030707", "Apurímac", "Grau", "Pataypampa"));
        if (ubPeruService.findOne(364) == null)
            ubPeruService.save(new UbPeru("030708", "Apurímac", "Grau", "Progreso"));
        if (ubPeruService.findOne(365) == null)
            ubPeruService.save(new UbPeru("030709", "Apurímac", "Grau", "San Antonio"));
        if (ubPeruService.findOne(366) == null)
            ubPeruService.save(new UbPeru("030710", "Apurímac", "Grau", "Santa Rosa"));
        if (ubPeruService.findOne(367) == null) ubPeruService.save(new UbPeru("030711", "Apurímac", "Grau", "Turpay"));
        if (ubPeruService.findOne(368) == null)
            ubPeruService.save(new UbPeru("030712", "Apurímac", "Grau", "Vilcabamba"));
        if (ubPeruService.findOne(369) == null) ubPeruService.save(new UbPeru("030713", "Apurímac", "Grau", "Virundo"));
        if (ubPeruService.findOne(370) == null) ubPeruService.save(new UbPeru("030714", "Apurímac", "Grau", "Curasco"));
        if (ubPeruService.findOne(371) == null) ubPeruService.save(new UbPeru("040000", "Arequipa", "", ""));
        if (ubPeruService.findOne(372) == null) ubPeruService.save(new UbPeru("040100", "Arequipa", "Arequipa", ""));
        if (ubPeruService.findOne(373) == null)
            ubPeruService.save(new UbPeru("040101", "Arequipa", "Arequipa", "Arequipa"));
        if (ubPeruService.findOne(374) == null)
            ubPeruService.save(new UbPeru("040102", "Arequipa", "Arequipa", "Alto Selva Alegre"));
        if (ubPeruService.findOne(375) == null)
            ubPeruService.save(new UbPeru("040103", "Arequipa", "Arequipa", "Cayma"));
        if (ubPeruService.findOne(376) == null)
            ubPeruService.save(new UbPeru("040104", "Arequipa", "Arequipa", "Cerro Colorado"));
        if (ubPeruService.findOne(377) == null)
            ubPeruService.save(new UbPeru("040105", "Arequipa", "Arequipa", "Characato"));
        if (ubPeruService.findOne(378) == null)
            ubPeruService.save(new UbPeru("040106", "Arequipa", "Arequipa", "Chiguata"));
        if (ubPeruService.findOne(379) == null)
            ubPeruService.save(new UbPeru("040107", "Arequipa", "Arequipa", "Jacobo Hunter"));
        if (ubPeruService.findOne(380) == null)
            ubPeruService.save(new UbPeru("040108", "Arequipa", "Arequipa", "La Joya"));
        if (ubPeruService.findOne(381) == null)
            ubPeruService.save(new UbPeru("040109", "Arequipa", "Arequipa", "Mariano Melgar"));
        if (ubPeruService.findOne(382) == null)
            ubPeruService.save(new UbPeru("040110", "Arequipa", "Arequipa", "Miraflores"));
        if (ubPeruService.findOne(383) == null)
            ubPeruService.save(new UbPeru("040111", "Arequipa", "Arequipa", "Mollebaya"));
        if (ubPeruService.findOne(384) == null)
            ubPeruService.save(new UbPeru("040112", "Arequipa", "Arequipa", "Paucarpata"));
        if (ubPeruService.findOne(385) == null)
            ubPeruService.save(new UbPeru("040113", "Arequipa", "Arequipa", "Pocsi"));
        if (ubPeruService.findOne(386) == null)
            ubPeruService.save(new UbPeru("040114", "Arequipa", "Arequipa", "Polobaya"));
        if (ubPeruService.findOne(387) == null)
            ubPeruService.save(new UbPeru("040115", "Arequipa", "Arequipa", "Quequeña"));
        if (ubPeruService.findOne(388) == null)
            ubPeruService.save(new UbPeru("040116", "Arequipa", "Arequipa", "Sabandia"));
        if (ubPeruService.findOne(389) == null)
            ubPeruService.save(new UbPeru("040117", "Arequipa", "Arequipa", "Sachaca"));
        if (ubPeruService.findOne(390) == null)
            ubPeruService.save(new UbPeru("040118", "Arequipa", "Arequipa", "San Juan de Siguas"));
        if (ubPeruService.findOne(391) == null)
            ubPeruService.save(new UbPeru("040119", "Arequipa", "Arequipa", "San Juan de Tarucani"));
        if (ubPeruService.findOne(392) == null)
            ubPeruService.save(new UbPeru("040120", "Arequipa", "Arequipa", "Santa Isabel de Siguas"));
        if (ubPeruService.findOne(393) == null)
            ubPeruService.save(new UbPeru("040121", "Arequipa", "Arequipa", "Santa Rita de Siguas"));
        if (ubPeruService.findOne(394) == null)
            ubPeruService.save(new UbPeru("040122", "Arequipa", "Arequipa", "Socabaya"));
        if (ubPeruService.findOne(395) == null)
            ubPeruService.save(new UbPeru("040123", "Arequipa", "Arequipa", "Tiabaya"));
        if (ubPeruService.findOne(396) == null)
            ubPeruService.save(new UbPeru("040124", "Arequipa", "Arequipa", "Uchumayo"));
        if (ubPeruService.findOne(397) == null)
            ubPeruService.save(new UbPeru("040125", "Arequipa", "Arequipa", "Vitor"));
        if (ubPeruService.findOne(398) == null)
            ubPeruService.save(new UbPeru("040126", "Arequipa", "Arequipa", "Yanahuara"));
        if (ubPeruService.findOne(399) == null)
            ubPeruService.save(new UbPeru("040127", "Arequipa", "Arequipa", "Yarabamba"));
        if (ubPeruService.findOne(400) == null)
            ubPeruService.save(new UbPeru("040128", "Arequipa", "Arequipa", "Yura"));
        if (ubPeruService.findOne(401) == null)
            ubPeruService.save(new UbPeru("040129", "Arequipa", "Arequipa", "José Luis Bustamante Y Rivero"));
        if (ubPeruService.findOne(402) == null) ubPeruService.save(new UbPeru("040200", "Arequipa", "Camaná", ""));
        if (ubPeruService.findOne(403) == null)
            ubPeruService.save(new UbPeru("040201", "Arequipa", "Camaná", "Camaná"));
        if (ubPeruService.findOne(404) == null)
            ubPeruService.save(new UbPeru("040202", "Arequipa", "Camaná", "José María Quimper"));
        if (ubPeruService.findOne(405) == null)
            ubPeruService.save(new UbPeru("040203", "Arequipa", "Camaná", "Mariano Nicolás Valcárcel"));
        if (ubPeruService.findOne(406) == null)
            ubPeruService.save(new UbPeru("040204", "Arequipa", "Camaná", "Mariscal Cáceres"));
        if (ubPeruService.findOne(407) == null)
            ubPeruService.save(new UbPeru("040205", "Arequipa", "Camaná", "Nicolás de Pierola"));
        if (ubPeruService.findOne(408) == null) ubPeruService.save(new UbPeru("040206", "Arequipa", "Camaná", "Ocoña"));
        if (ubPeruService.findOne(409) == null)
            ubPeruService.save(new UbPeru("040207", "Arequipa", "Camaná", "Quilca"));
        if (ubPeruService.findOne(410) == null)
            ubPeruService.save(new UbPeru("040208", "Arequipa", "Camaná", "Samuel Pastor"));
        if (ubPeruService.findOne(411) == null) ubPeruService.save(new UbPeru("040300", "Arequipa", "Caravelí", ""));
        if (ubPeruService.findOne(412) == null)
            ubPeruService.save(new UbPeru("040301", "Arequipa", "Caravelí", "Caravelí"));
        if (ubPeruService.findOne(413) == null)
            ubPeruService.save(new UbPeru("040302", "Arequipa", "Caravelí", "Acarí"));
        if (ubPeruService.findOne(414) == null)
            ubPeruService.save(new UbPeru("040303", "Arequipa", "Caravelí", "Atico"));
        if (ubPeruService.findOne(415) == null)
            ubPeruService.save(new UbPeru("040304", "Arequipa", "Caravelí", "Atiquipa"));
        if (ubPeruService.findOne(416) == null)
            ubPeruService.save(new UbPeru("040305", "Arequipa", "Caravelí", "Bella Unión"));
        if (ubPeruService.findOne(417) == null)
            ubPeruService.save(new UbPeru("040306", "Arequipa", "Caravelí", "Cahuacho"));
        if (ubPeruService.findOne(418) == null)
            ubPeruService.save(new UbPeru("040307", "Arequipa", "Caravelí", "Chala"));
        if (ubPeruService.findOne(419) == null)
            ubPeruService.save(new UbPeru("040308", "Arequipa", "Caravelí", "Chaparra"));
        if (ubPeruService.findOne(420) == null)
            ubPeruService.save(new UbPeru("040309", "Arequipa", "Caravelí", "Huanuhuanu"));
        if (ubPeruService.findOne(421) == null)
            ubPeruService.save(new UbPeru("040310", "Arequipa", "Caravelí", "Jaqui"));
        if (ubPeruService.findOne(422) == null)
            ubPeruService.save(new UbPeru("040311", "Arequipa", "Caravelí", "Lomas"));
        if (ubPeruService.findOne(423) == null)
            ubPeruService.save(new UbPeru("040312", "Arequipa", "Caravelí", "Quicacha"));
        if (ubPeruService.findOne(424) == null)
            ubPeruService.save(new UbPeru("040313", "Arequipa", "Caravelí", "Yauca"));
        if (ubPeruService.findOne(425) == null) ubPeruService.save(new UbPeru("040400", "Arequipa", "Castilla", ""));
        if (ubPeruService.findOne(426) == null)
            ubPeruService.save(new UbPeru("040401", "Arequipa", "Castilla", "Aplao"));
        if (ubPeruService.findOne(427) == null)
            ubPeruService.save(new UbPeru("040402", "Arequipa", "Castilla", "Andagua"));
        if (ubPeruService.findOne(428) == null) ubPeruService.save(new UbPeru("040403", "Arequipa", "Castilla", "Ayo"));
        if (ubPeruService.findOne(429) == null)
            ubPeruService.save(new UbPeru("040404", "Arequipa", "Castilla", "Chachas"));
        if (ubPeruService.findOne(430) == null)
            ubPeruService.save(new UbPeru("040405", "Arequipa", "Castilla", "Chilcaymarca"));
        if (ubPeruService.findOne(431) == null)
            ubPeruService.save(new UbPeru("040406", "Arequipa", "Castilla", "Choco"));
        if (ubPeruService.findOne(432) == null)
            ubPeruService.save(new UbPeru("040407", "Arequipa", "Castilla", "Huancarqui"));
        if (ubPeruService.findOne(433) == null)
            ubPeruService.save(new UbPeru("040408", "Arequipa", "Castilla", "Machaguay"));
        if (ubPeruService.findOne(434) == null)
            ubPeruService.save(new UbPeru("040409", "Arequipa", "Castilla", "Orcopampa"));
        if (ubPeruService.findOne(435) == null)
            ubPeruService.save(new UbPeru("040410", "Arequipa", "Castilla", "Pampacolca"));
        if (ubPeruService.findOne(436) == null)
            ubPeruService.save(new UbPeru("040411", "Arequipa", "Castilla", "Tipan"));
        if (ubPeruService.findOne(437) == null)
            ubPeruService.save(new UbPeru("040412", "Arequipa", "Castilla", "Uñon"));
        if (ubPeruService.findOne(438) == null)
            ubPeruService.save(new UbPeru("040413", "Arequipa", "Castilla", "Uraca"));
        if (ubPeruService.findOne(439) == null)
            ubPeruService.save(new UbPeru("040414", "Arequipa", "Castilla", "Viraco"));
        if (ubPeruService.findOne(440) == null) ubPeruService.save(new UbPeru("040500", "Arequipa", "Caylloma", ""));
        if (ubPeruService.findOne(441) == null)
            ubPeruService.save(new UbPeru("040501", "Arequipa", "Caylloma", "Chivay"));
        if (ubPeruService.findOne(442) == null)
            ubPeruService.save(new UbPeru("040502", "Arequipa", "Caylloma", "Achoma"));
        if (ubPeruService.findOne(443) == null)
            ubPeruService.save(new UbPeru("040503", "Arequipa", "Caylloma", "Cabanaconde"));
        if (ubPeruService.findOne(444) == null)
            ubPeruService.save(new UbPeru("040504", "Arequipa", "Caylloma", "Callalli"));
        if (ubPeruService.findOne(445) == null)
            ubPeruService.save(new UbPeru("040505", "Arequipa", "Caylloma", "Caylloma"));
        if (ubPeruService.findOne(446) == null)
            ubPeruService.save(new UbPeru("040506", "Arequipa", "Caylloma", "Coporaque"));
        if (ubPeruService.findOne(447) == null)
            ubPeruService.save(new UbPeru("040507", "Arequipa", "Caylloma", "Huambo"));
        if (ubPeruService.findOne(448) == null)
            ubPeruService.save(new UbPeru("040508", "Arequipa", "Caylloma", "Huanca"));
        if (ubPeruService.findOne(449) == null)
            ubPeruService.save(new UbPeru("040509", "Arequipa", "Caylloma", "Ichupampa"));
        if (ubPeruService.findOne(450) == null)
            ubPeruService.save(new UbPeru("040510", "Arequipa", "Caylloma", "Lari"));
        if (ubPeruService.findOne(451) == null)
            ubPeruService.save(new UbPeru("040511", "Arequipa", "Caylloma", "Lluta"));
        if (ubPeruService.findOne(452) == null)
            ubPeruService.save(new UbPeru("040512", "Arequipa", "Caylloma", "Maca"));
        if (ubPeruService.findOne(453) == null)
            ubPeruService.save(new UbPeru("040513", "Arequipa", "Caylloma", "Madrigal"));
        if (ubPeruService.findOne(454) == null)
            ubPeruService.save(new UbPeru("040514", "Arequipa", "Caylloma", "San Antonio de Chuca"));
        if (ubPeruService.findOne(455) == null)
            ubPeruService.save(new UbPeru("040515", "Arequipa", "Caylloma", "Sibayo"));
        if (ubPeruService.findOne(456) == null)
            ubPeruService.save(new UbPeru("040516", "Arequipa", "Caylloma", "Tapay"));
        if (ubPeruService.findOne(457) == null)
            ubPeruService.save(new UbPeru("040517", "Arequipa", "Caylloma", "Tisco"));
        if (ubPeruService.findOne(458) == null)
            ubPeruService.save(new UbPeru("040518", "Arequipa", "Caylloma", "Tuti"));
        if (ubPeruService.findOne(459) == null)
            ubPeruService.save(new UbPeru("040519", "Arequipa", "Caylloma", "Yanque"));
        if (ubPeruService.findOne(460) == null)
            ubPeruService.save(new UbPeru("040520", "Arequipa", "Caylloma", "Majes"));
        if (ubPeruService.findOne(461) == null) ubPeruService.save(new UbPeru("040600", "Arequipa", "Condesuyos", ""));
        if (ubPeruService.findOne(462) == null)
            ubPeruService.save(new UbPeru("040601", "Arequipa", "Condesuyos", "Chuquibamba"));
        if (ubPeruService.findOne(463) == null)
            ubPeruService.save(new UbPeru("040602", "Arequipa", "Condesuyos", "Andaray"));
        if (ubPeruService.findOne(464) == null)
            ubPeruService.save(new UbPeru("040603", "Arequipa", "Condesuyos", "Cayarani"));
        if (ubPeruService.findOne(465) == null)
            ubPeruService.save(new UbPeru("040604", "Arequipa", "Condesuyos", "Chichas"));
        if (ubPeruService.findOne(466) == null)
            ubPeruService.save(new UbPeru("040605", "Arequipa", "Condesuyos", "Iray"));
        if (ubPeruService.findOne(467) == null)
            ubPeruService.save(new UbPeru("040606", "Arequipa", "Condesuyos", "Río Grande"));
        if (ubPeruService.findOne(468) == null)
            ubPeruService.save(new UbPeru("040607", "Arequipa", "Condesuyos", "Salamanca"));
        if (ubPeruService.findOne(469) == null)
            ubPeruService.save(new UbPeru("040608", "Arequipa", "Condesuyos", "Yanaquihua"));
        if (ubPeruService.findOne(470) == null) ubPeruService.save(new UbPeru("040700", "Arequipa", "Islay", ""));
        if (ubPeruService.findOne(471) == null)
            ubPeruService.save(new UbPeru("040701", "Arequipa", "Islay", "Mollendo"));
        if (ubPeruService.findOne(472) == null)
            ubPeruService.save(new UbPeru("040702", "Arequipa", "Islay", "Cocachacra"));
        if (ubPeruService.findOne(473) == null)
            ubPeruService.save(new UbPeru("040703", "Arequipa", "Islay", "Dean Valdivia"));
        if (ubPeruService.findOne(474) == null) ubPeruService.save(new UbPeru("040704", "Arequipa", "Islay", "Islay"));
        if (ubPeruService.findOne(475) == null) ubPeruService.save(new UbPeru("040705", "Arequipa", "Islay", "Mejia"));
        if (ubPeruService.findOne(476) == null)
            ubPeruService.save(new UbPeru("040706", "Arequipa", "Islay", "Punta de Bombón"));
        if (ubPeruService.findOne(477) == null) ubPeruService.save(new UbPeru("040800", "Arequipa", "La Uniòn", ""));
        if (ubPeruService.findOne(478) == null)
            ubPeruService.save(new UbPeru("040801", "Arequipa", "La Uniòn", "Cotahuasi"));
        if (ubPeruService.findOne(479) == null)
            ubPeruService.save(new UbPeru("040802", "Arequipa", "La Uniòn", "Alca"));
        if (ubPeruService.findOne(480) == null)
            ubPeruService.save(new UbPeru("040803", "Arequipa", "La Uniòn", "Charcana"));
        if (ubPeruService.findOne(481) == null)
            ubPeruService.save(new UbPeru("040804", "Arequipa", "La Uniòn", "Huaynacotas"));
        if (ubPeruService.findOne(482) == null)
            ubPeruService.save(new UbPeru("040805", "Arequipa", "La Uniòn", "Pampamarca"));
        if (ubPeruService.findOne(483) == null)
            ubPeruService.save(new UbPeru("040806", "Arequipa", "La Uniòn", "Puyca"));
        if (ubPeruService.findOne(484) == null)
            ubPeruService.save(new UbPeru("040807", "Arequipa", "La Uniòn", "Quechualla"));
        if (ubPeruService.findOne(485) == null)
            ubPeruService.save(new UbPeru("040808", "Arequipa", "La Uniòn", "Sayla"));
        if (ubPeruService.findOne(486) == null)
            ubPeruService.save(new UbPeru("040809", "Arequipa", "La Uniòn", "Tauria"));
        if (ubPeruService.findOne(487) == null)
            ubPeruService.save(new UbPeru("040810", "Arequipa", "La Uniòn", "Tomepampa"));
        if (ubPeruService.findOne(488) == null)
            ubPeruService.save(new UbPeru("040811", "Arequipa", "La Uniòn", "Toro"));
        if (ubPeruService.findOne(489) == null) ubPeruService.save(new UbPeru("050000", "Ayacucho", "", ""));
        if (ubPeruService.findOne(490) == null) ubPeruService.save(new UbPeru("050100", "Ayacucho", "Huamanga", ""));
        if (ubPeruService.findOne(491) == null)
            ubPeruService.save(new UbPeru("050101", "Ayacucho", "Huamanga", "Ayacucho"));
        if (ubPeruService.findOne(492) == null)
            ubPeruService.save(new UbPeru("050102", "Ayacucho", "Huamanga", "Acocro"));
        if (ubPeruService.findOne(493) == null)
            ubPeruService.save(new UbPeru("050103", "Ayacucho", "Huamanga", "Acos Vinchos"));
        if (ubPeruService.findOne(494) == null)
            ubPeruService.save(new UbPeru("050104", "Ayacucho", "Huamanga", "Carmen Alto"));
        if (ubPeruService.findOne(495) == null)
            ubPeruService.save(new UbPeru("050105", "Ayacucho", "Huamanga", "Chiara"));
        if (ubPeruService.findOne(496) == null)
            ubPeruService.save(new UbPeru("050106", "Ayacucho", "Huamanga", "Ocros"));
        if (ubPeruService.findOne(497) == null)
            ubPeruService.save(new UbPeru("050107", "Ayacucho", "Huamanga", "Pacaycasa"));
        if (ubPeruService.findOne(498) == null)
            ubPeruService.save(new UbPeru("050108", "Ayacucho", "Huamanga", "Quinua"));
        if (ubPeruService.findOne(499) == null)
            ubPeruService.save(new UbPeru("050109", "Ayacucho", "Huamanga", "San José de Ticllas"));
        if (ubPeruService.findOne(500) == null)
            ubPeruService.save(new UbPeru("050110", "Ayacucho", "Huamanga", "San Juan Bautista"));
        if (ubPeruService.findOne(501) == null)
            ubPeruService.save(new UbPeru("050111", "Ayacucho", "Huamanga", "Santiago de Pischa"));
        if (ubPeruService.findOne(502) == null)
            ubPeruService.save(new UbPeru("050112", "Ayacucho", "Huamanga", "Socos"));
        if (ubPeruService.findOne(503) == null)
            ubPeruService.save(new UbPeru("050113", "Ayacucho", "Huamanga", "Tambillo"));
        if (ubPeruService.findOne(504) == null)
            ubPeruService.save(new UbPeru("050114", "Ayacucho", "Huamanga", "Vinchos"));
        if (ubPeruService.findOne(505) == null)
            ubPeruService.save(new UbPeru("050115", "Ayacucho", "Huamanga", "Jesús Nazareno"));
        if (ubPeruService.findOne(506) == null)
            ubPeruService.save(new UbPeru("050116", "Ayacucho", "Huamanga", "Andrés Avelino Cáceres Dorregaray"));
        if (ubPeruService.findOne(507) == null) ubPeruService.save(new UbPeru("050200", "Ayacucho", "Cangallo", ""));
        if (ubPeruService.findOne(508) == null)
            ubPeruService.save(new UbPeru("050201", "Ayacucho", "Cangallo", "Cangallo"));
        if (ubPeruService.findOne(509) == null)
            ubPeruService.save(new UbPeru("050202", "Ayacucho", "Cangallo", "Chuschi"));
        if (ubPeruService.findOne(510) == null)
            ubPeruService.save(new UbPeru("050203", "Ayacucho", "Cangallo", "Los Morochucos"));
        if (ubPeruService.findOne(511) == null)
            ubPeruService.save(new UbPeru("050204", "Ayacucho", "Cangallo", "María Parado de Bellido"));
        if (ubPeruService.findOne(512) == null)
            ubPeruService.save(new UbPeru("050205", "Ayacucho", "Cangallo", "Paras"));
        if (ubPeruService.findOne(513) == null)
            ubPeruService.save(new UbPeru("050206", "Ayacucho", "Cangallo", "Totos"));
        if (ubPeruService.findOne(514) == null)
            ubPeruService.save(new UbPeru("050300", "Ayacucho", "Huanca Sancos", ""));
        if (ubPeruService.findOne(515) == null)
            ubPeruService.save(new UbPeru("050301", "Ayacucho", "Huanca Sancos", "Sancos"));
        if (ubPeruService.findOne(516) == null)
            ubPeruService.save(new UbPeru("050302", "Ayacucho", "Huanca Sancos", "Carapo"));
        if (ubPeruService.findOne(517) == null)
            ubPeruService.save(new UbPeru("050303", "Ayacucho", "Huanca Sancos", "Sacsamarca"));
        if (ubPeruService.findOne(518) == null)
            ubPeruService.save(new UbPeru("050304", "Ayacucho", "Huanca Sancos", "Santiago de Lucanamarca"));
        if (ubPeruService.findOne(519) == null) ubPeruService.save(new UbPeru("050400", "Ayacucho", "Huanta", ""));
        if (ubPeruService.findOne(520) == null)
            ubPeruService.save(new UbPeru("050401", "Ayacucho", "Huanta", "Huanta"));
        if (ubPeruService.findOne(521) == null)
            ubPeruService.save(new UbPeru("050402", "Ayacucho", "Huanta", "Ayahuanco"));
        if (ubPeruService.findOne(522) == null)
            ubPeruService.save(new UbPeru("050403", "Ayacucho", "Huanta", "Huamanguilla"));
        if (ubPeruService.findOne(523) == null)
            ubPeruService.save(new UbPeru("050404", "Ayacucho", "Huanta", "Iguain"));
        if (ubPeruService.findOne(524) == null)
            ubPeruService.save(new UbPeru("050405", "Ayacucho", "Huanta", "Luricocha"));
        if (ubPeruService.findOne(525) == null)
            ubPeruService.save(new UbPeru("050406", "Ayacucho", "Huanta", "Santillana"));
        if (ubPeruService.findOne(526) == null) ubPeruService.save(new UbPeru("050407", "Ayacucho", "Huanta", "Sivia"));
        if (ubPeruService.findOne(527) == null)
            ubPeruService.save(new UbPeru("050408", "Ayacucho", "Huanta", "Llochegua"));
        if (ubPeruService.findOne(528) == null)
            ubPeruService.save(new UbPeru("050409", "Ayacucho", "Huanta", "Canayre"));
        if (ubPeruService.findOne(529) == null)
            ubPeruService.save(new UbPeru("050410", "Ayacucho", "Huanta", "Uchuraccay"));
        if (ubPeruService.findOne(530) == null)
            ubPeruService.save(new UbPeru("050411", "Ayacucho", "Huanta", "Pucacolpa"));
        if (ubPeruService.findOne(531) == null) ubPeruService.save(new UbPeru("050412", "Ayacucho", "Huanta", "Chaca"));
        if (ubPeruService.findOne(532) == null) ubPeruService.save(new UbPeru("050500", "Ayacucho", "La Mar", ""));
        if (ubPeruService.findOne(533) == null)
            ubPeruService.save(new UbPeru("050501", "Ayacucho", "La Mar", "San Miguel"));
        if (ubPeruService.findOne(534) == null) ubPeruService.save(new UbPeru("050502", "Ayacucho", "La Mar", "Anco"));
        if (ubPeruService.findOne(535) == null) ubPeruService.save(new UbPeru("050503", "Ayacucho", "La Mar", "Ayna"));
        if (ubPeruService.findOne(536) == null)
            ubPeruService.save(new UbPeru("050504", "Ayacucho", "La Mar", "Chilcas"));
        if (ubPeruService.findOne(537) == null)
            ubPeruService.save(new UbPeru("050505", "Ayacucho", "La Mar", "Chungui"));
        if (ubPeruService.findOne(538) == null)
            ubPeruService.save(new UbPeru("050506", "Ayacucho", "La Mar", "Luis Carranza"));
        if (ubPeruService.findOne(539) == null)
            ubPeruService.save(new UbPeru("050507", "Ayacucho", "La Mar", "Santa Rosa"));
        if (ubPeruService.findOne(540) == null) ubPeruService.save(new UbPeru("050508", "Ayacucho", "La Mar", "Tambo"));
        if (ubPeruService.findOne(541) == null)
            ubPeruService.save(new UbPeru("050509", "Ayacucho", "La Mar", "Samugari"));
        if (ubPeruService.findOne(542) == null)
            ubPeruService.save(new UbPeru("050510", "Ayacucho", "La Mar", "Anchihuay"));
        if (ubPeruService.findOne(543) == null) ubPeruService.save(new UbPeru("050600", "Ayacucho", "Lucanas", ""));
        if (ubPeruService.findOne(544) == null)
            ubPeruService.save(new UbPeru("050601", "Ayacucho", "Lucanas", "Puquio"));
        if (ubPeruService.findOne(545) == null)
            ubPeruService.save(new UbPeru("050602", "Ayacucho", "Lucanas", "Aucara"));
        if (ubPeruService.findOne(546) == null)
            ubPeruService.save(new UbPeru("050603", "Ayacucho", "Lucanas", "Cabana"));
        if (ubPeruService.findOne(547) == null)
            ubPeruService.save(new UbPeru("050604", "Ayacucho", "Lucanas", "Carmen Salcedo"));
        if (ubPeruService.findOne(548) == null)
            ubPeruService.save(new UbPeru("050605", "Ayacucho", "Lucanas", "Chaviña"));
        if (ubPeruService.findOne(549) == null)
            ubPeruService.save(new UbPeru("050606", "Ayacucho", "Lucanas", "Chipao"));
        if (ubPeruService.findOne(550) == null)
            ubPeruService.save(new UbPeru("050607", "Ayacucho", "Lucanas", "Huac-Huas"));
        if (ubPeruService.findOne(551) == null)
            ubPeruService.save(new UbPeru("050608", "Ayacucho", "Lucanas", "Laramate"));
        if (ubPeruService.findOne(552) == null)
            ubPeruService.save(new UbPeru("050609", "Ayacucho", "Lucanas", "Leoncio Prado"));
        if (ubPeruService.findOne(553) == null)
            ubPeruService.save(new UbPeru("050610", "Ayacucho", "Lucanas", "Llauta"));
        if (ubPeruService.findOne(554) == null)
            ubPeruService.save(new UbPeru("050611", "Ayacucho", "Lucanas", "Lucanas"));
        if (ubPeruService.findOne(555) == null)
            ubPeruService.save(new UbPeru("050612", "Ayacucho", "Lucanas", "Ocaña"));
        if (ubPeruService.findOne(556) == null)
            ubPeruService.save(new UbPeru("050613", "Ayacucho", "Lucanas", "Otoca"));
        if (ubPeruService.findOne(557) == null)
            ubPeruService.save(new UbPeru("050614", "Ayacucho", "Lucanas", "Saisa"));
        if (ubPeruService.findOne(558) == null)
            ubPeruService.save(new UbPeru("050615", "Ayacucho", "Lucanas", "San Cristóbal"));
        if (ubPeruService.findOne(559) == null)
            ubPeruService.save(new UbPeru("050616", "Ayacucho", "Lucanas", "San Juan"));
        if (ubPeruService.findOne(560) == null)
            ubPeruService.save(new UbPeru("050617", "Ayacucho", "Lucanas", "San Pedro"));
        if (ubPeruService.findOne(561) == null)
            ubPeruService.save(new UbPeru("050618", "Ayacucho", "Lucanas", "San Pedro de Palco"));
        if (ubPeruService.findOne(562) == null)
            ubPeruService.save(new UbPeru("050619", "Ayacucho", "Lucanas", "Sancos"));
        if (ubPeruService.findOne(563) == null)
            ubPeruService.save(new UbPeru("050620", "Ayacucho", "Lucanas", "Santa Ana de Huaycahuacho"));
        if (ubPeruService.findOne(564) == null)
            ubPeruService.save(new UbPeru("050621", "Ayacucho", "Lucanas", "Santa Lucia"));
        if (ubPeruService.findOne(565) == null)
            ubPeruService.save(new UbPeru("050700", "Ayacucho", "Parinacochas", ""));
        if (ubPeruService.findOne(566) == null)
            ubPeruService.save(new UbPeru("050701", "Ayacucho", "Parinacochas", "Coracora"));
        if (ubPeruService.findOne(567) == null)
            ubPeruService.save(new UbPeru("050702", "Ayacucho", "Parinacochas", "Chumpi"));
        if (ubPeruService.findOne(568) == null)
            ubPeruService.save(new UbPeru("050703", "Ayacucho", "Parinacochas", "Coronel Castañeda"));
        if (ubPeruService.findOne(569) == null)
            ubPeruService.save(new UbPeru("050704", "Ayacucho", "Parinacochas", "Pacapausa"));
        if (ubPeruService.findOne(570) == null)
            ubPeruService.save(new UbPeru("050705", "Ayacucho", "Parinacochas", "Pullo"));
        if (ubPeruService.findOne(571) == null)
            ubPeruService.save(new UbPeru("050706", "Ayacucho", "Parinacochas", "Puyusca"));
        if (ubPeruService.findOne(572) == null)
            ubPeruService.save(new UbPeru("050707", "Ayacucho", "Parinacochas", "San Francisco de Ravacayco"));
        if (ubPeruService.findOne(573) == null)
            ubPeruService.save(new UbPeru("050708", "Ayacucho", "Parinacochas", "Upahuacho"));
        if (ubPeruService.findOne(574) == null)
            ubPeruService.save(new UbPeru("050800", "Ayacucho", "Pàucar del Sara Sara", ""));
        if (ubPeruService.findOne(575) == null)
            ubPeruService.save(new UbPeru("050801", "Ayacucho", "Pàucar del Sara Sara", "Pausa"));
        if (ubPeruService.findOne(576) == null)
            ubPeruService.save(new UbPeru("050802", "Ayacucho", "Pàucar del Sara Sara", "Colta"));
        if (ubPeruService.findOne(577) == null)
            ubPeruService.save(new UbPeru("050803", "Ayacucho", "Pàucar del Sara Sara", "Corculla"));
        if (ubPeruService.findOne(578) == null)
            ubPeruService.save(new UbPeru("050804", "Ayacucho", "Pàucar del Sara Sara", "Lampa"));
        if (ubPeruService.findOne(579) == null)
            ubPeruService.save(new UbPeru("050805", "Ayacucho", "Pàucar del Sara Sara", "Marcabamba"));
        if (ubPeruService.findOne(580) == null)
            ubPeruService.save(new UbPeru("050806", "Ayacucho", "Pàucar del Sara Sara", "Oyolo"));
        if (ubPeruService.findOne(581) == null)
            ubPeruService.save(new UbPeru("050807", "Ayacucho", "Pàucar del Sara Sara", "Pararca"));
        if (ubPeruService.findOne(582) == null)
            ubPeruService.save(new UbPeru("050808", "Ayacucho", "Pàucar del Sara Sara", "San Javier de Alpabamba"));
        if (ubPeruService.findOne(583) == null)
            ubPeruService.save(new UbPeru("050809", "Ayacucho", "Pàucar del Sara Sara", "San José de Ushua"));
        if (ubPeruService.findOne(584) == null)
            ubPeruService.save(new UbPeru("050810", "Ayacucho", "Pàucar del Sara Sara", "Sara Sara"));
        if (ubPeruService.findOne(585) == null) ubPeruService.save(new UbPeru("050900", "Ayacucho", "Sucre", ""));
        if (ubPeruService.findOne(586) == null)
            ubPeruService.save(new UbPeru("050901", "Ayacucho", "Sucre", "Querobamba"));
        if (ubPeruService.findOne(587) == null) ubPeruService.save(new UbPeru("050902", "Ayacucho", "Sucre", "Belén"));
        if (ubPeruService.findOne(588) == null)
            ubPeruService.save(new UbPeru("050903", "Ayacucho", "Sucre", "Chalcos"));
        if (ubPeruService.findOne(589) == null)
            ubPeruService.save(new UbPeru("050904", "Ayacucho", "Sucre", "Chilcayoc"));
        if (ubPeruService.findOne(590) == null)
            ubPeruService.save(new UbPeru("050905", "Ayacucho", "Sucre", "Huacaña"));
        if (ubPeruService.findOne(591) == null)
            ubPeruService.save(new UbPeru("050906", "Ayacucho", "Sucre", "Morcolla"));
        if (ubPeruService.findOne(592) == null) ubPeruService.save(new UbPeru("050907", "Ayacucho", "Sucre", "Paico"));
        if (ubPeruService.findOne(593) == null)
            ubPeruService.save(new UbPeru("050908", "Ayacucho", "Sucre", "San Pedro de Larcay"));
        if (ubPeruService.findOne(594) == null)
            ubPeruService.save(new UbPeru("050909", "Ayacucho", "Sucre", "San Salvador de Quije"));
        if (ubPeruService.findOne(595) == null)
            ubPeruService.save(new UbPeru("050910", "Ayacucho", "Sucre", "Santiago de Paucaray"));
        if (ubPeruService.findOne(596) == null) ubPeruService.save(new UbPeru("050911", "Ayacucho", "Sucre", "Soras"));
        if (ubPeruService.findOne(597) == null)
            ubPeruService.save(new UbPeru("051000", "Ayacucho", "Víctor Fajardo", ""));
        if (ubPeruService.findOne(598) == null)
            ubPeruService.save(new UbPeru("051001", "Ayacucho", "Víctor Fajardo", "Huancapi"));
        if (ubPeruService.findOne(599) == null)
            ubPeruService.save(new UbPeru("051002", "Ayacucho", "Víctor Fajardo", "Alcamenca"));
        if (ubPeruService.findOne(600) == null)
            ubPeruService.save(new UbPeru("051003", "Ayacucho", "Víctor Fajardo", "Apongo"));
        if (ubPeruService.findOne(601) == null)
            ubPeruService.save(new UbPeru("051004", "Ayacucho", "Víctor Fajardo", "Asquipata"));
        if (ubPeruService.findOne(602) == null)
            ubPeruService.save(new UbPeru("051005", "Ayacucho", "Víctor Fajardo", "Canaria"));
        if (ubPeruService.findOne(603) == null)
            ubPeruService.save(new UbPeru("051006", "Ayacucho", "Víctor Fajardo", "Cayara"));
        if (ubPeruService.findOne(604) == null)
            ubPeruService.save(new UbPeru("051007", "Ayacucho", "Víctor Fajardo", "Colca"));
        if (ubPeruService.findOne(605) == null)
            ubPeruService.save(new UbPeru("051008", "Ayacucho", "Víctor Fajardo", "Huamanquiquia"));
        if (ubPeruService.findOne(606) == null)
            ubPeruService.save(new UbPeru("051009", "Ayacucho", "Víctor Fajardo", "Huancaraylla"));
        if (ubPeruService.findOne(607) == null)
            ubPeruService.save(new UbPeru("051010", "Ayacucho", "Víctor Fajardo", "Huaya"));
        if (ubPeruService.findOne(608) == null)
            ubPeruService.save(new UbPeru("051011", "Ayacucho", "Víctor Fajardo", "Sarhua"));
        if (ubPeruService.findOne(609) == null)
            ubPeruService.save(new UbPeru("051012", "Ayacucho", "Víctor Fajardo", "Vilcanchos"));
        if (ubPeruService.findOne(610) == null)
            ubPeruService.save(new UbPeru("051100", "Ayacucho", "Vilcas Huamán", ""));
        if (ubPeruService.findOne(611) == null)
            ubPeruService.save(new UbPeru("051101", "Ayacucho", "Vilcas Huamán", "Vilcas Huaman"));
        if (ubPeruService.findOne(612) == null)
            ubPeruService.save(new UbPeru("051102", "Ayacucho", "Vilcas Huamán", "Accomarca"));
        if (ubPeruService.findOne(613) == null)
            ubPeruService.save(new UbPeru("051103", "Ayacucho", "Vilcas Huamán", "Carhuanca"));
        if (ubPeruService.findOne(614) == null)
            ubPeruService.save(new UbPeru("051104", "Ayacucho", "Vilcas Huamán", "Concepción"));
        if (ubPeruService.findOne(615) == null)
            ubPeruService.save(new UbPeru("051105", "Ayacucho", "Vilcas Huamán", "Huambalpa"));
        if (ubPeruService.findOne(616) == null)
            ubPeruService.save(new UbPeru("051106", "Ayacucho", "Vilcas Huamán", "Independencia"));
        if (ubPeruService.findOne(617) == null)
            ubPeruService.save(new UbPeru("051107", "Ayacucho", "Vilcas Huamán", "Saurama"));
        if (ubPeruService.findOne(618) == null)
            ubPeruService.save(new UbPeru("051108", "Ayacucho", "Vilcas Huamán", "Vischongo"));
        if (ubPeruService.findOne(619) == null) ubPeruService.save(new UbPeru("060000", "Cajamarca", "", ""));
        if (ubPeruService.findOne(620) == null) ubPeruService.save(new UbPeru("060100", "Cajamarca", "Cajamarca", ""));
        if (ubPeruService.findOne(621) == null)
            ubPeruService.save(new UbPeru("060101", "Cajamarca", "Cajamarca", "Cajamarca"));
        if (ubPeruService.findOne(622) == null)
            ubPeruService.save(new UbPeru("060102", "Cajamarca", "Cajamarca", "Asunción"));
        if (ubPeruService.findOne(623) == null)
            ubPeruService.save(new UbPeru("060103", "Cajamarca", "Cajamarca", "Chetilla"));
        if (ubPeruService.findOne(624) == null)
            ubPeruService.save(new UbPeru("060104", "Cajamarca", "Cajamarca", "Cospan"));
        if (ubPeruService.findOne(625) == null)
            ubPeruService.save(new UbPeru("060105", "Cajamarca", "Cajamarca", "Encañada"));
        if (ubPeruService.findOne(626) == null)
            ubPeruService.save(new UbPeru("060106", "Cajamarca", "Cajamarca", "Jesús"));
        if (ubPeruService.findOne(627) == null)
            ubPeruService.save(new UbPeru("060107", "Cajamarca", "Cajamarca", "Llacanora"));
        if (ubPeruService.findOne(628) == null)
            ubPeruService.save(new UbPeru("060108", "Cajamarca", "Cajamarca", "Los Baños del Inca"));
        if (ubPeruService.findOne(629) == null)
            ubPeruService.save(new UbPeru("060109", "Cajamarca", "Cajamarca", "Magdalena"));
        if (ubPeruService.findOne(630) == null)
            ubPeruService.save(new UbPeru("060110", "Cajamarca", "Cajamarca", "Matara"));
        if (ubPeruService.findOne(631) == null)
            ubPeruService.save(new UbPeru("060111", "Cajamarca", "Cajamarca", "Namora"));
        if (ubPeruService.findOne(632) == null)
            ubPeruService.save(new UbPeru("060112", "Cajamarca", "Cajamarca", "San Juan"));
        if (ubPeruService.findOne(633) == null) ubPeruService.save(new UbPeru("060200", "Cajamarca", "Cajabamba", ""));
        if (ubPeruService.findOne(634) == null)
            ubPeruService.save(new UbPeru("060201", "Cajamarca", "Cajabamba", "Cajabamba"));
        if (ubPeruService.findOne(635) == null)
            ubPeruService.save(new UbPeru("060202", "Cajamarca", "Cajabamba", "Cachachi"));
        if (ubPeruService.findOne(636) == null)
            ubPeruService.save(new UbPeru("060203", "Cajamarca", "Cajabamba", "Condebamba"));
        if (ubPeruService.findOne(637) == null)
            ubPeruService.save(new UbPeru("060204", "Cajamarca", "Cajabamba", "Sitacocha"));
        if (ubPeruService.findOne(638) == null) ubPeruService.save(new UbPeru("060300", "Cajamarca", "Celendín", ""));
        if (ubPeruService.findOne(639) == null)
            ubPeruService.save(new UbPeru("060301", "Cajamarca", "Celendín", "Celendín"));
        if (ubPeruService.findOne(640) == null)
            ubPeruService.save(new UbPeru("060302", "Cajamarca", "Celendín", "Chumuch"));
        if (ubPeruService.findOne(641) == null)
            ubPeruService.save(new UbPeru("060303", "Cajamarca", "Celendín", "Cortegana"));
        if (ubPeruService.findOne(642) == null)
            ubPeruService.save(new UbPeru("060304", "Cajamarca", "Celendín", "Huasmin"));
        if (ubPeruService.findOne(643) == null)
            ubPeruService.save(new UbPeru("060305", "Cajamarca", "Celendín", "Jorge Chávez"));
        if (ubPeruService.findOne(644) == null)
            ubPeruService.save(new UbPeru("060306", "Cajamarca", "Celendín", "José Gálvez"));
        if (ubPeruService.findOne(645) == null)
            ubPeruService.save(new UbPeru("060307", "Cajamarca", "Celendín", "Miguel Iglesias"));
        if (ubPeruService.findOne(646) == null)
            ubPeruService.save(new UbPeru("060308", "Cajamarca", "Celendín", "Oxamarca"));
        if (ubPeruService.findOne(647) == null)
            ubPeruService.save(new UbPeru("060309", "Cajamarca", "Celendín", "Sorochuco"));
        if (ubPeruService.findOne(648) == null)
            ubPeruService.save(new UbPeru("060310", "Cajamarca", "Celendín", "Sucre"));
        if (ubPeruService.findOne(649) == null)
            ubPeruService.save(new UbPeru("060311", "Cajamarca", "Celendín", "Utco"));
        if (ubPeruService.findOne(650) == null)
            ubPeruService.save(new UbPeru("060312", "Cajamarca", "Celendín", "La Libertad de Pallan"));
        if (ubPeruService.findOne(651) == null) ubPeruService.save(new UbPeru("060400", "Cajamarca", "Chota", ""));
        if (ubPeruService.findOne(652) == null) ubPeruService.save(new UbPeru("060401", "Cajamarca", "Chota", "Chota"));
        if (ubPeruService.findOne(653) == null)
            ubPeruService.save(new UbPeru("060402", "Cajamarca", "Chota", "Anguia"));
        if (ubPeruService.findOne(654) == null)
            ubPeruService.save(new UbPeru("060403", "Cajamarca", "Chota", "Chadin"));
        if (ubPeruService.findOne(655) == null)
            ubPeruService.save(new UbPeru("060404", "Cajamarca", "Chota", "Chiguirip"));
        if (ubPeruService.findOne(656) == null)
            ubPeruService.save(new UbPeru("060405", "Cajamarca", "Chota", "Chimban"));
        if (ubPeruService.findOne(657) == null)
            ubPeruService.save(new UbPeru("060406", "Cajamarca", "Chota", "Choropampa"));
        if (ubPeruService.findOne(658) == null)
            ubPeruService.save(new UbPeru("060407", "Cajamarca", "Chota", "Cochabamba"));
        if (ubPeruService.findOne(659) == null)
            ubPeruService.save(new UbPeru("060408", "Cajamarca", "Chota", "Conchan"));
        if (ubPeruService.findOne(660) == null)
            ubPeruService.save(new UbPeru("060409", "Cajamarca", "Chota", "Huambos"));
        if (ubPeruService.findOne(661) == null) ubPeruService.save(new UbPeru("060410", "Cajamarca", "Chota", "Lajas"));
        if (ubPeruService.findOne(662) == null) ubPeruService.save(new UbPeru("060411", "Cajamarca", "Chota", "Llama"));
        if (ubPeruService.findOne(663) == null)
            ubPeruService.save(new UbPeru("060412", "Cajamarca", "Chota", "Miracosta"));
        if (ubPeruService.findOne(664) == null)
            ubPeruService.save(new UbPeru("060413", "Cajamarca", "Chota", "Paccha"));
        if (ubPeruService.findOne(665) == null) ubPeruService.save(new UbPeru("060414", "Cajamarca", "Chota", "Pion"));
        if (ubPeruService.findOne(666) == null)
            ubPeruService.save(new UbPeru("060415", "Cajamarca", "Chota", "Querocoto"));
        if (ubPeruService.findOne(667) == null)
            ubPeruService.save(new UbPeru("060416", "Cajamarca", "Chota", "San Juan de Licupis"));
        if (ubPeruService.findOne(668) == null)
            ubPeruService.save(new UbPeru("060417", "Cajamarca", "Chota", "Tacabamba"));
        if (ubPeruService.findOne(669) == null)
            ubPeruService.save(new UbPeru("060418", "Cajamarca", "Chota", "Tocmoche"));
        if (ubPeruService.findOne(670) == null)
            ubPeruService.save(new UbPeru("060419", "Cajamarca", "Chota", "Chalamarca"));
        if (ubPeruService.findOne(671) == null) ubPeruService.save(new UbPeru("060500", "Cajamarca", "Contumazá", ""));
        if (ubPeruService.findOne(672) == null)
            ubPeruService.save(new UbPeru("060501", "Cajamarca", "Contumazá", "Contumaza"));
        if (ubPeruService.findOne(673) == null)
            ubPeruService.save(new UbPeru("060502", "Cajamarca", "Contumazá", "Chilete"));
        if (ubPeruService.findOne(674) == null)
            ubPeruService.save(new UbPeru("060503", "Cajamarca", "Contumazá", "Cupisnique"));
        if (ubPeruService.findOne(675) == null)
            ubPeruService.save(new UbPeru("060504", "Cajamarca", "Contumazá", "Guzmango"));
        if (ubPeruService.findOne(676) == null)
            ubPeruService.save(new UbPeru("060505", "Cajamarca", "Contumazá", "San Benito"));
        if (ubPeruService.findOne(677) == null)
            ubPeruService.save(new UbPeru("060506", "Cajamarca", "Contumazá", "Santa Cruz de Toledo"));
        if (ubPeruService.findOne(678) == null)
            ubPeruService.save(new UbPeru("060507", "Cajamarca", "Contumazá", "Tantarica"));
        if (ubPeruService.findOne(679) == null)
            ubPeruService.save(new UbPeru("060508", "Cajamarca", "Contumazá", "Yonan"));
        if (ubPeruService.findOne(680) == null) ubPeruService.save(new UbPeru("060600", "Cajamarca", "Cutervo", ""));
        if (ubPeruService.findOne(681) == null)
            ubPeruService.save(new UbPeru("060601", "Cajamarca", "Cutervo", "Cutervo"));
        if (ubPeruService.findOne(682) == null)
            ubPeruService.save(new UbPeru("060602", "Cajamarca", "Cutervo", "Callayuc"));
        if (ubPeruService.findOne(683) == null)
            ubPeruService.save(new UbPeru("060603", "Cajamarca", "Cutervo", "Choros"));
        if (ubPeruService.findOne(684) == null)
            ubPeruService.save(new UbPeru("060604", "Cajamarca", "Cutervo", "Cujillo"));
        if (ubPeruService.findOne(685) == null)
            ubPeruService.save(new UbPeru("060605", "Cajamarca", "Cutervo", "La Ramada"));
        if (ubPeruService.findOne(686) == null)
            ubPeruService.save(new UbPeru("060606", "Cajamarca", "Cutervo", "Pimpingos"));
        if (ubPeruService.findOne(687) == null)
            ubPeruService.save(new UbPeru("060607", "Cajamarca", "Cutervo", "Querocotillo"));
        if (ubPeruService.findOne(688) == null)
            ubPeruService.save(new UbPeru("060608", "Cajamarca", "Cutervo", "San Andrés de Cutervo"));
        if (ubPeruService.findOne(689) == null)
            ubPeruService.save(new UbPeru("060609", "Cajamarca", "Cutervo", "San Juan de Cutervo"));
        if (ubPeruService.findOne(690) == null)
            ubPeruService.save(new UbPeru("060610", "Cajamarca", "Cutervo", "San Luis de Lucma"));
        if (ubPeruService.findOne(691) == null)
            ubPeruService.save(new UbPeru("060611", "Cajamarca", "Cutervo", "Santa Cruz"));
        if (ubPeruService.findOne(692) == null)
            ubPeruService.save(new UbPeru("060612", "Cajamarca", "Cutervo", "Santo Domingo de la Capilla"));
        if (ubPeruService.findOne(693) == null)
            ubPeruService.save(new UbPeru("060613", "Cajamarca", "Cutervo", "Santo Tomas"));
        if (ubPeruService.findOne(694) == null)
            ubPeruService.save(new UbPeru("060614", "Cajamarca", "Cutervo", "Socota"));
        if (ubPeruService.findOne(695) == null)
            ubPeruService.save(new UbPeru("060615", "Cajamarca", "Cutervo", "Toribio Casanova"));
        if (ubPeruService.findOne(696) == null) ubPeruService.save(new UbPeru("060700", "Cajamarca", "Hualgayoc", ""));
        if (ubPeruService.findOne(697) == null)
            ubPeruService.save(new UbPeru("060701", "Cajamarca", "Hualgayoc", "Bambamarca"));
        if (ubPeruService.findOne(698) == null)
            ubPeruService.save(new UbPeru("060702", "Cajamarca", "Hualgayoc", "Chugur"));
        if (ubPeruService.findOne(699) == null)
            ubPeruService.save(new UbPeru("060703", "Cajamarca", "Hualgayoc", "Hualgayoc"));
        if (ubPeruService.findOne(700) == null) ubPeruService.save(new UbPeru("060800", "Cajamarca", "Jaén", ""));
        if (ubPeruService.findOne(701) == null) ubPeruService.save(new UbPeru("060801", "Cajamarca", "Jaén", "Jaén"));
        if (ubPeruService.findOne(702) == null)
            ubPeruService.save(new UbPeru("060802", "Cajamarca", "Jaén", "Bellavista"));
        if (ubPeruService.findOne(703) == null)
            ubPeruService.save(new UbPeru("060803", "Cajamarca", "Jaén", "Chontali"));
        if (ubPeruService.findOne(704) == null)
            ubPeruService.save(new UbPeru("060804", "Cajamarca", "Jaén", "Colasay"));
        if (ubPeruService.findOne(705) == null) ubPeruService.save(new UbPeru("060805", "Cajamarca", "Jaén", "Huabal"));
        if (ubPeruService.findOne(706) == null)
            ubPeruService.save(new UbPeru("060806", "Cajamarca", "Jaén", "Las Pirias"));
        if (ubPeruService.findOne(707) == null)
            ubPeruService.save(new UbPeru("060807", "Cajamarca", "Jaén", "Pomahuaca"));
        if (ubPeruService.findOne(708) == null) ubPeruService.save(new UbPeru("060808", "Cajamarca", "Jaén", "Pucara"));
        if (ubPeruService.findOne(709) == null)
            ubPeruService.save(new UbPeru("060809", "Cajamarca", "Jaén", "Sallique"));
        if (ubPeruService.findOne(710) == null)
            ubPeruService.save(new UbPeru("060810", "Cajamarca", "Jaén", "San Felipe"));
        if (ubPeruService.findOne(711) == null)
            ubPeruService.save(new UbPeru("060811", "Cajamarca", "Jaén", "San José del Alto"));
        if (ubPeruService.findOne(712) == null)
            ubPeruService.save(new UbPeru("060812", "Cajamarca", "Jaén", "Santa Rosa"));
        if (ubPeruService.findOne(713) == null)
            ubPeruService.save(new UbPeru("060900", "Cajamarca", "San Ignacio", ""));
        if (ubPeruService.findOne(714) == null)
            ubPeruService.save(new UbPeru("060901", "Cajamarca", "San Ignacio", "San Ignacio"));
        if (ubPeruService.findOne(715) == null)
            ubPeruService.save(new UbPeru("060902", "Cajamarca", "San Ignacio", "Chirinos"));
        if (ubPeruService.findOne(716) == null)
            ubPeruService.save(new UbPeru("060903", "Cajamarca", "San Ignacio", "Huarango"));
        if (ubPeruService.findOne(717) == null)
            ubPeruService.save(new UbPeru("060904", "Cajamarca", "San Ignacio", "La Coipa"));
        if (ubPeruService.findOne(718) == null)
            ubPeruService.save(new UbPeru("060905", "Cajamarca", "San Ignacio", "Namballe"));
        if (ubPeruService.findOne(719) == null)
            ubPeruService.save(new UbPeru("060906", "Cajamarca", "San Ignacio", "San José de Lourdes"));
        if (ubPeruService.findOne(720) == null)
            ubPeruService.save(new UbPeru("060907", "Cajamarca", "San Ignacio", "Tabaconas"));
        if (ubPeruService.findOne(721) == null) ubPeruService.save(new UbPeru("061000", "Cajamarca", "San Marcos", ""));
        if (ubPeruService.findOne(722) == null)
            ubPeruService.save(new UbPeru("061001", "Cajamarca", "San Marcos", "Pedro Gálvez"));
        if (ubPeruService.findOne(723) == null)
            ubPeruService.save(new UbPeru("061002", "Cajamarca", "San Marcos", "Chancay"));
        if (ubPeruService.findOne(724) == null)
            ubPeruService.save(new UbPeru("061003", "Cajamarca", "San Marcos", "Eduardo Villanueva"));
        if (ubPeruService.findOne(725) == null)
            ubPeruService.save(new UbPeru("061004", "Cajamarca", "San Marcos", "Gregorio Pita"));
        if (ubPeruService.findOne(726) == null)
            ubPeruService.save(new UbPeru("061005", "Cajamarca", "San Marcos", "Ichocan"));
        if (ubPeruService.findOne(727) == null)
            ubPeruService.save(new UbPeru("061006", "Cajamarca", "San Marcos", "José Manuel Quiroz"));
        if (ubPeruService.findOne(728) == null)
            ubPeruService.save(new UbPeru("061007", "Cajamarca", "San Marcos", "José Sabogal"));
        if (ubPeruService.findOne(729) == null) ubPeruService.save(new UbPeru("061100", "Cajamarca", "San Miguel", ""));
        if (ubPeruService.findOne(730) == null)
            ubPeruService.save(new UbPeru("061101", "Cajamarca", "San Miguel", "San Miguel"));
        if (ubPeruService.findOne(731) == null)
            ubPeruService.save(new UbPeru("061102", "Cajamarca", "San Miguel", "Bolívar"));
        if (ubPeruService.findOne(732) == null)
            ubPeruService.save(new UbPeru("061103", "Cajamarca", "San Miguel", "Calquis"));
        if (ubPeruService.findOne(733) == null)
            ubPeruService.save(new UbPeru("061104", "Cajamarca", "San Miguel", "Catilluc"));
        if (ubPeruService.findOne(734) == null)
            ubPeruService.save(new UbPeru("061105", "Cajamarca", "San Miguel", "El Prado"));
        if (ubPeruService.findOne(735) == null)
            ubPeruService.save(new UbPeru("061106", "Cajamarca", "San Miguel", "La Florida"));
        if (ubPeruService.findOne(736) == null)
            ubPeruService.save(new UbPeru("061107", "Cajamarca", "San Miguel", "Llapa"));
        if (ubPeruService.findOne(737) == null)
            ubPeruService.save(new UbPeru("061108", "Cajamarca", "San Miguel", "Nanchoc"));
        if (ubPeruService.findOne(738) == null)
            ubPeruService.save(new UbPeru("061109", "Cajamarca", "San Miguel", "Niepos"));
        if (ubPeruService.findOne(739) == null)
            ubPeruService.save(new UbPeru("061110", "Cajamarca", "San Miguel", "San Gregorio"));
        if (ubPeruService.findOne(740) == null)
            ubPeruService.save(new UbPeru("061111", "Cajamarca", "San Miguel", "San Silvestre de Cochan"));
        if (ubPeruService.findOne(741) == null)
            ubPeruService.save(new UbPeru("061112", "Cajamarca", "San Miguel", "Tongod"));
        if (ubPeruService.findOne(742) == null)
            ubPeruService.save(new UbPeru("061113", "Cajamarca", "San Miguel", "Unión Agua Blanca"));
        if (ubPeruService.findOne(743) == null) ubPeruService.save(new UbPeru("061200", "Cajamarca", "San Pablo", ""));
        if (ubPeruService.findOne(744) == null)
            ubPeruService.save(new UbPeru("061201", "Cajamarca", "San Pablo", "San Pablo"));
        if (ubPeruService.findOne(745) == null)
            ubPeruService.save(new UbPeru("061202", "Cajamarca", "San Pablo", "San Bernardino"));
        if (ubPeruService.findOne(746) == null)
            ubPeruService.save(new UbPeru("061203", "Cajamarca", "San Pablo", "San Luis"));
        if (ubPeruService.findOne(747) == null)
            ubPeruService.save(new UbPeru("061204", "Cajamarca", "San Pablo", "Tumbaden"));
        if (ubPeruService.findOne(748) == null) ubPeruService.save(new UbPeru("061300", "Cajamarca", "Santa Cruz", ""));
        if (ubPeruService.findOne(749) == null)
            ubPeruService.save(new UbPeru("061301", "Cajamarca", "Santa Cruz", "Santa Cruz"));
        if (ubPeruService.findOne(750) == null)
            ubPeruService.save(new UbPeru("061302", "Cajamarca", "Santa Cruz", "Andabamba"));
        if (ubPeruService.findOne(751) == null)
            ubPeruService.save(new UbPeru("061303", "Cajamarca", "Santa Cruz", "Catache"));
        if (ubPeruService.findOne(752) == null)
            ubPeruService.save(new UbPeru("061304", "Cajamarca", "Santa Cruz", "Chancaybaños"));
        if (ubPeruService.findOne(753) == null)
            ubPeruService.save(new UbPeru("061305", "Cajamarca", "Santa Cruz", "La Esperanza"));
        if (ubPeruService.findOne(754) == null)
            ubPeruService.save(new UbPeru("061306", "Cajamarca", "Santa Cruz", "Ninabamba"));
        if (ubPeruService.findOne(755) == null)
            ubPeruService.save(new UbPeru("061307", "Cajamarca", "Santa Cruz", "Pulan"));
        if (ubPeruService.findOne(756) == null)
            ubPeruService.save(new UbPeru("061308", "Cajamarca", "Santa Cruz", "Saucepampa"));
        if (ubPeruService.findOne(757) == null)
            ubPeruService.save(new UbPeru("061309", "Cajamarca", "Santa Cruz", "Sexi"));
        if (ubPeruService.findOne(758) == null)
            ubPeruService.save(new UbPeru("061310", "Cajamarca", "Santa Cruz", "Uticyacu"));
        if (ubPeruService.findOne(759) == null)
            ubPeruService.save(new UbPeru("061311", "Cajamarca", "Santa Cruz", "Yauyucan"));
        if (ubPeruService.findOne(760) == null) ubPeruService.save(new UbPeru("070000", "Callao", "", ""));
        if (ubPeruService.findOne(761) == null)
            ubPeruService.save(new UbPeru("070100", "Callao", "Prov. Const. del Callao", ""));
        if (ubPeruService.findOne(762) == null)
            ubPeruService.save(new UbPeru("070101", "Callao", "Prov. Const. del Callao", "Callao"));
        if (ubPeruService.findOne(763) == null)
            ubPeruService.save(new UbPeru("070102", "Callao", "Prov. Const. del Callao", "Bellavista"));
        if (ubPeruService.findOne(764) == null)
            ubPeruService.save(new UbPeru("070103", "Callao", "Prov. Const. del Callao", "Carmen de la Legua Reynoso"));
        if (ubPeruService.findOne(765) == null)
            ubPeruService.save(new UbPeru("070104", "Callao", "Prov. Const. del Callao", "La Perla"));
        if (ubPeruService.findOne(766) == null)
            ubPeruService.save(new UbPeru("070105", "Callao", "Prov. Const. del Callao", "La Punta"));
        if (ubPeruService.findOne(767) == null)
            ubPeruService.save(new UbPeru("070106", "Callao", "Prov. Const. del Callao", "Ventanilla"));
        if (ubPeruService.findOne(768) == null)
            ubPeruService.save(new UbPeru("070107", "Callao", "Prov. Const. del Callao", "Mi Perú"));
        if (ubPeruService.findOne(769) == null) ubPeruService.save(new UbPeru("080000", "Cusco", "", ""));
        if (ubPeruService.findOne(770) == null) ubPeruService.save(new UbPeru("080100", "Cusco", "Cusco", ""));
        if (ubPeruService.findOne(771) == null) ubPeruService.save(new UbPeru("080101", "Cusco", "Cusco", "Cusco"));
        if (ubPeruService.findOne(772) == null) ubPeruService.save(new UbPeru("080102", "Cusco", "Cusco", "Ccorca"));
        if (ubPeruService.findOne(773) == null) ubPeruService.save(new UbPeru("080103", "Cusco", "Cusco", "Poroy"));
        if (ubPeruService.findOne(774) == null)
            ubPeruService.save(new UbPeru("080104", "Cusco", "Cusco", "San Jerónimo"));
        if (ubPeruService.findOne(775) == null)
            ubPeruService.save(new UbPeru("080105", "Cusco", "Cusco", "San Sebastian"));
        if (ubPeruService.findOne(776) == null) ubPeruService.save(new UbPeru("080106", "Cusco", "Cusco", "Santiago"));
        if (ubPeruService.findOne(777) == null) ubPeruService.save(new UbPeru("080107", "Cusco", "Cusco", "Saylla"));
        if (ubPeruService.findOne(778) == null) ubPeruService.save(new UbPeru("080108", "Cusco", "Cusco", "Wanchaq"));
        if (ubPeruService.findOne(779) == null) ubPeruService.save(new UbPeru("080200", "Cusco", "Acomayo", ""));
        if (ubPeruService.findOne(780) == null) ubPeruService.save(new UbPeru("080201", "Cusco", "Acomayo", "Acomayo"));
        if (ubPeruService.findOne(781) == null) ubPeruService.save(new UbPeru("080202", "Cusco", "Acomayo", "Acopia"));
        if (ubPeruService.findOne(782) == null) ubPeruService.save(new UbPeru("080203", "Cusco", "Acomayo", "Acos"));
        if (ubPeruService.findOne(783) == null)
            ubPeruService.save(new UbPeru("080204", "Cusco", "Acomayo", "Mosoc Llacta"));
        if (ubPeruService.findOne(784) == null)
            ubPeruService.save(new UbPeru("080205", "Cusco", "Acomayo", "Pomacanchi"));
        if (ubPeruService.findOne(785) == null)
            ubPeruService.save(new UbPeru("080206", "Cusco", "Acomayo", "Rondocan"));
        if (ubPeruService.findOne(786) == null)
            ubPeruService.save(new UbPeru("080207", "Cusco", "Acomayo", "Sangarara"));
        if (ubPeruService.findOne(787) == null) ubPeruService.save(new UbPeru("080300", "Cusco", "Anta", ""));
        if (ubPeruService.findOne(788) == null) ubPeruService.save(new UbPeru("080301", "Cusco", "Anta", "Anta"));
        if (ubPeruService.findOne(789) == null) ubPeruService.save(new UbPeru("080302", "Cusco", "Anta", "Ancahuasi"));
        if (ubPeruService.findOne(790) == null) ubPeruService.save(new UbPeru("080303", "Cusco", "Anta", "Cachimayo"));
        if (ubPeruService.findOne(791) == null)
            ubPeruService.save(new UbPeru("080304", "Cusco", "Anta", "Chinchaypujio"));
        if (ubPeruService.findOne(792) == null) ubPeruService.save(new UbPeru("080305", "Cusco", "Anta", "Huarocondo"));
        if (ubPeruService.findOne(793) == null) ubPeruService.save(new UbPeru("080306", "Cusco", "Anta", "Limatambo"));
        if (ubPeruService.findOne(794) == null) ubPeruService.save(new UbPeru("080307", "Cusco", "Anta", "Mollepata"));
        if (ubPeruService.findOne(795) == null) ubPeruService.save(new UbPeru("080308", "Cusco", "Anta", "Pucyura"));
        if (ubPeruService.findOne(796) == null) ubPeruService.save(new UbPeru("080309", "Cusco", "Anta", "Zurite"));
        if (ubPeruService.findOne(797) == null) ubPeruService.save(new UbPeru("080400", "Cusco", "Calca", ""));
        if (ubPeruService.findOne(798) == null) ubPeruService.save(new UbPeru("080401", "Cusco", "Calca", "Calca"));
        if (ubPeruService.findOne(799) == null) ubPeruService.save(new UbPeru("080402", "Cusco", "Calca", "Coya"));
        if (ubPeruService.findOne(800) == null) ubPeruService.save(new UbPeru("080403", "Cusco", "Calca", "Lamay"));
        if (ubPeruService.findOne(801) == null) ubPeruService.save(new UbPeru("080404", "Cusco", "Calca", "Lares"));
        if (ubPeruService.findOne(802) == null) ubPeruService.save(new UbPeru("080405", "Cusco", "Calca", "Pisac"));
        if (ubPeruService.findOne(803) == null)
            ubPeruService.save(new UbPeru("080406", "Cusco", "Calca", "San Salvador"));
        if (ubPeruService.findOne(804) == null) ubPeruService.save(new UbPeru("080407", "Cusco", "Calca", "Taray"));
        if (ubPeruService.findOne(805) == null) ubPeruService.save(new UbPeru("080408", "Cusco", "Calca", "Yanatile"));
        if (ubPeruService.findOne(806) == null) ubPeruService.save(new UbPeru("080500", "Cusco", "Canas", ""));
        if (ubPeruService.findOne(807) == null) ubPeruService.save(new UbPeru("080501", "Cusco", "Canas", "Yanaoca"));
        if (ubPeruService.findOne(808) == null) ubPeruService.save(new UbPeru("080502", "Cusco", "Canas", "Checca"));
        if (ubPeruService.findOne(809) == null)
            ubPeruService.save(new UbPeru("080503", "Cusco", "Canas", "Kunturkanki"));
        if (ubPeruService.findOne(810) == null) ubPeruService.save(new UbPeru("080504", "Cusco", "Canas", "Langui"));
        if (ubPeruService.findOne(811) == null) ubPeruService.save(new UbPeru("080505", "Cusco", "Canas", "Layo"));
        if (ubPeruService.findOne(812) == null)
            ubPeruService.save(new UbPeru("080506", "Cusco", "Canas", "Pampamarca"));
        if (ubPeruService.findOne(813) == null) ubPeruService.save(new UbPeru("080507", "Cusco", "Canas", "Quehue"));
        if (ubPeruService.findOne(814) == null)
            ubPeruService.save(new UbPeru("080508", "Cusco", "Canas", "Tupac Amaru"));
        if (ubPeruService.findOne(815) == null) ubPeruService.save(new UbPeru("080600", "Cusco", "Canchis", ""));
        if (ubPeruService.findOne(816) == null) ubPeruService.save(new UbPeru("080601", "Cusco", "Canchis", "Sicuani"));
        if (ubPeruService.findOne(817) == null)
            ubPeruService.save(new UbPeru("080602", "Cusco", "Canchis", "Checacupe"));
        if (ubPeruService.findOne(818) == null)
            ubPeruService.save(new UbPeru("080603", "Cusco", "Canchis", "Combapata"));
        if (ubPeruService.findOne(819) == null)
            ubPeruService.save(new UbPeru("080604", "Cusco", "Canchis", "Marangani"));
        if (ubPeruService.findOne(820) == null)
            ubPeruService.save(new UbPeru("080605", "Cusco", "Canchis", "Pitumarca"));
        if (ubPeruService.findOne(821) == null)
            ubPeruService.save(new UbPeru("080606", "Cusco", "Canchis", "San Pablo"));
        if (ubPeruService.findOne(822) == null)
            ubPeruService.save(new UbPeru("080607", "Cusco", "Canchis", "San Pedro"));
        if (ubPeruService.findOne(823) == null) ubPeruService.save(new UbPeru("080608", "Cusco", "Canchis", "Tinta"));
        if (ubPeruService.findOne(824) == null) ubPeruService.save(new UbPeru("080700", "Cusco", "Chumbivilcas", ""));
        if (ubPeruService.findOne(825) == null)
            ubPeruService.save(new UbPeru("080701", "Cusco", "Chumbivilcas", "Santo Tomas"));
        if (ubPeruService.findOne(826) == null)
            ubPeruService.save(new UbPeru("080702", "Cusco", "Chumbivilcas", "Capacmarca"));
        if (ubPeruService.findOne(827) == null)
            ubPeruService.save(new UbPeru("080703", "Cusco", "Chumbivilcas", "Chamaca"));
        if (ubPeruService.findOne(828) == null)
            ubPeruService.save(new UbPeru("080704", "Cusco", "Chumbivilcas", "Colquemarca"));
        if (ubPeruService.findOne(829) == null)
            ubPeruService.save(new UbPeru("080705", "Cusco", "Chumbivilcas", "Livitaca"));
        if (ubPeruService.findOne(830) == null)
            ubPeruService.save(new UbPeru("080706", "Cusco", "Chumbivilcas", "Llusco"));
        if (ubPeruService.findOne(831) == null)
            ubPeruService.save(new UbPeru("080707", "Cusco", "Chumbivilcas", "Quiñota"));
        if (ubPeruService.findOne(832) == null)
            ubPeruService.save(new UbPeru("080708", "Cusco", "Chumbivilcas", "Velille"));
        if (ubPeruService.findOne(833) == null) ubPeruService.save(new UbPeru("080800", "Cusco", "Espinar", ""));
        if (ubPeruService.findOne(834) == null) ubPeruService.save(new UbPeru("080801", "Cusco", "Espinar", "Espinar"));
        if (ubPeruService.findOne(835) == null)
            ubPeruService.save(new UbPeru("080802", "Cusco", "Espinar", "Condoroma"));
        if (ubPeruService.findOne(836) == null)
            ubPeruService.save(new UbPeru("080803", "Cusco", "Espinar", "Coporaque"));
        if (ubPeruService.findOne(837) == null) ubPeruService.save(new UbPeru("080804", "Cusco", "Espinar", "Ocoruro"));
        if (ubPeruService.findOne(838) == null)
            ubPeruService.save(new UbPeru("080805", "Cusco", "Espinar", "Pallpata"));
        if (ubPeruService.findOne(839) == null)
            ubPeruService.save(new UbPeru("080806", "Cusco", "Espinar", "Pichigua"));
        if (ubPeruService.findOne(840) == null)
            ubPeruService.save(new UbPeru("080807", "Cusco", "Espinar", "Suyckutambo"));
        if (ubPeruService.findOne(841) == null)
            ubPeruService.save(new UbPeru("080808", "Cusco", "Espinar", "Alto Pichigua"));
        if (ubPeruService.findOne(842) == null) ubPeruService.save(new UbPeru("080900", "Cusco", "La Convención", ""));
        if (ubPeruService.findOne(843) == null)
            ubPeruService.save(new UbPeru("080901", "Cusco", "La Convención", "Santa Ana"));
        if (ubPeruService.findOne(844) == null)
            ubPeruService.save(new UbPeru("080902", "Cusco", "La Convención", "Echarate"));
        if (ubPeruService.findOne(845) == null)
            ubPeruService.save(new UbPeru("080903", "Cusco", "La Convención", "Huayopata"));
        if (ubPeruService.findOne(846) == null)
            ubPeruService.save(new UbPeru("080904", "Cusco", "La Convención", "Maranura"));
        if (ubPeruService.findOne(847) == null)
            ubPeruService.save(new UbPeru("080905", "Cusco", "La Convención", "Ocobamba"));
        if (ubPeruService.findOne(848) == null)
            ubPeruService.save(new UbPeru("080906", "Cusco", "La Convención", "Quellouno"));
        if (ubPeruService.findOne(849) == null)
            ubPeruService.save(new UbPeru("080907", "Cusco", "La Convención", "Kimbiri"));
        if (ubPeruService.findOne(850) == null)
            ubPeruService.save(new UbPeru("080908", "Cusco", "La Convención", "Santa Teresa"));
        if (ubPeruService.findOne(851) == null)
            ubPeruService.save(new UbPeru("080909", "Cusco", "La Convención", "Vilcabamba"));
        if (ubPeruService.findOne(852) == null)
            ubPeruService.save(new UbPeru("080910", "Cusco", "La Convención", "Pichari"));
        if (ubPeruService.findOne(853) == null)
            ubPeruService.save(new UbPeru("080911", "Cusco", "La Convención", "Inkawasi"));
        if (ubPeruService.findOne(854) == null)
            ubPeruService.save(new UbPeru("080912", "Cusco", "La Convención", "Villa Virgen"));
        if (ubPeruService.findOne(855) == null)
            ubPeruService.save(new UbPeru("080913", "Cusco", "La Convención", "Villa Kintiarina"));
        if (ubPeruService.findOne(856) == null) ubPeruService.save(new UbPeru("081000", "Cusco", "Paruro", ""));
        if (ubPeruService.findOne(857) == null) ubPeruService.save(new UbPeru("081001", "Cusco", "Paruro", "Paruro"));
        if (ubPeruService.findOne(858) == null) ubPeruService.save(new UbPeru("081002", "Cusco", "Paruro", "Accha"));
        if (ubPeruService.findOne(859) == null) ubPeruService.save(new UbPeru("081003", "Cusco", "Paruro", "Ccapi"));
        if (ubPeruService.findOne(860) == null) ubPeruService.save(new UbPeru("081004", "Cusco", "Paruro", "Colcha"));
        if (ubPeruService.findOne(861) == null)
            ubPeruService.save(new UbPeru("081005", "Cusco", "Paruro", "Huanoquite"));
        if (ubPeruService.findOne(862) == null) ubPeruService.save(new UbPeru("081006", "Cusco", "Paruro", "Omacha"));
        if (ubPeruService.findOne(863) == null)
            ubPeruService.save(new UbPeru("081007", "Cusco", "Paruro", "Paccaritambo"));
        if (ubPeruService.findOne(864) == null)
            ubPeruService.save(new UbPeru("081008", "Cusco", "Paruro", "Pillpinto"));
        if (ubPeruService.findOne(865) == null)
            ubPeruService.save(new UbPeru("081009", "Cusco", "Paruro", "Yaurisque"));
        if (ubPeruService.findOne(866) == null) ubPeruService.save(new UbPeru("081100", "Cusco", "Paucartambo", ""));
        if (ubPeruService.findOne(867) == null)
            ubPeruService.save(new UbPeru("081101", "Cusco", "Paucartambo", "Paucartambo"));
        if (ubPeruService.findOne(868) == null)
            ubPeruService.save(new UbPeru("081102", "Cusco", "Paucartambo", "Caicay"));
        if (ubPeruService.findOne(869) == null)
            ubPeruService.save(new UbPeru("081103", "Cusco", "Paucartambo", "Challabamba"));
        if (ubPeruService.findOne(870) == null)
            ubPeruService.save(new UbPeru("081104", "Cusco", "Paucartambo", "Colquepata"));
        if (ubPeruService.findOne(871) == null)
            ubPeruService.save(new UbPeru("081105", "Cusco", "Paucartambo", "Huancarani"));
        if (ubPeruService.findOne(872) == null)
            ubPeruService.save(new UbPeru("081106", "Cusco", "Paucartambo", "Kosñipata"));
        if (ubPeruService.findOne(873) == null) ubPeruService.save(new UbPeru("081200", "Cusco", "Quispicanchi", ""));
        if (ubPeruService.findOne(874) == null)
            ubPeruService.save(new UbPeru("081201", "Cusco", "Quispicanchi", "Urcos"));
        if (ubPeruService.findOne(875) == null)
            ubPeruService.save(new UbPeru("081202", "Cusco", "Quispicanchi", "Andahuaylillas"));
        if (ubPeruService.findOne(876) == null)
            ubPeruService.save(new UbPeru("081203", "Cusco", "Quispicanchi", "Camanti"));
        if (ubPeruService.findOne(877) == null)
            ubPeruService.save(new UbPeru("081204", "Cusco", "Quispicanchi", "Ccarhuayo"));
        if (ubPeruService.findOne(878) == null)
            ubPeruService.save(new UbPeru("081205", "Cusco", "Quispicanchi", "Ccatca"));
        if (ubPeruService.findOne(879) == null)
            ubPeruService.save(new UbPeru("081206", "Cusco", "Quispicanchi", "Cusipata"));
        if (ubPeruService.findOne(880) == null)
            ubPeruService.save(new UbPeru("081207", "Cusco", "Quispicanchi", "Huaro"));
        if (ubPeruService.findOne(881) == null)
            ubPeruService.save(new UbPeru("081208", "Cusco", "Quispicanchi", "Lucre"));
        if (ubPeruService.findOne(882) == null)
            ubPeruService.save(new UbPeru("081209", "Cusco", "Quispicanchi", "Marcapata"));
        if (ubPeruService.findOne(883) == null)
            ubPeruService.save(new UbPeru("081210", "Cusco", "Quispicanchi", "Ocongate"));
        if (ubPeruService.findOne(884) == null)
            ubPeruService.save(new UbPeru("081211", "Cusco", "Quispicanchi", "Oropesa"));
        if (ubPeruService.findOne(885) == null)
            ubPeruService.save(new UbPeru("081212", "Cusco", "Quispicanchi", "Quiquijana"));
        if (ubPeruService.findOne(886) == null) ubPeruService.save(new UbPeru("081300", "Cusco", "Urubamba", ""));
        if (ubPeruService.findOne(887) == null)
            ubPeruService.save(new UbPeru("081301", "Cusco", "Urubamba", "Urubamba"));
        if (ubPeruService.findOne(888) == null)
            ubPeruService.save(new UbPeru("081302", "Cusco", "Urubamba", "Chinchero"));
        if (ubPeruService.findOne(889) == null)
            ubPeruService.save(new UbPeru("081303", "Cusco", "Urubamba", "Huayllabamba"));
        if (ubPeruService.findOne(890) == null)
            ubPeruService.save(new UbPeru("081304", "Cusco", "Urubamba", "Machupicchu"));
        if (ubPeruService.findOne(891) == null) ubPeruService.save(new UbPeru("081305", "Cusco", "Urubamba", "Maras"));
        if (ubPeruService.findOne(892) == null)
            ubPeruService.save(new UbPeru("081306", "Cusco", "Urubamba", "Ollantaytambo"));
        if (ubPeruService.findOne(893) == null) ubPeruService.save(new UbPeru("081307", "Cusco", "Urubamba", "Yucay"));
        if (ubPeruService.findOne(894) == null) ubPeruService.save(new UbPeru("090000", "Huancavelica", "", ""));
        if (ubPeruService.findOne(895) == null)
            ubPeruService.save(new UbPeru("090100", "Huancavelica", "Huancavelica", ""));
        if (ubPeruService.findOne(896) == null)
            ubPeruService.save(new UbPeru("090101", "Huancavelica", "Huancavelica", "Huancavelica"));
        if (ubPeruService.findOne(897) == null)
            ubPeruService.save(new UbPeru("090102", "Huancavelica", "Huancavelica", "Acobambilla"));
        if (ubPeruService.findOne(898) == null)
            ubPeruService.save(new UbPeru("090103", "Huancavelica", "Huancavelica", "Acoria"));
        if (ubPeruService.findOne(899) == null)
            ubPeruService.save(new UbPeru("090104", "Huancavelica", "Huancavelica", "Conayca"));
        if (ubPeruService.findOne(900) == null)
            ubPeruService.save(new UbPeru("090105", "Huancavelica", "Huancavelica", "Cuenca"));
        if (ubPeruService.findOne(901) == null)
            ubPeruService.save(new UbPeru("090106", "Huancavelica", "Huancavelica", "Huachocolpa"));
        if (ubPeruService.findOne(902) == null)
            ubPeruService.save(new UbPeru("090107", "Huancavelica", "Huancavelica", "Huayllahuara"));
        if (ubPeruService.findOne(903) == null)
            ubPeruService.save(new UbPeru("090108", "Huancavelica", "Huancavelica", "Izcuchaca"));
        if (ubPeruService.findOne(904) == null)
            ubPeruService.save(new UbPeru("090109", "Huancavelica", "Huancavelica", "Laria"));
        if (ubPeruService.findOne(905) == null)
            ubPeruService.save(new UbPeru("090110", "Huancavelica", "Huancavelica", "Manta"));
        if (ubPeruService.findOne(906) == null)
            ubPeruService.save(new UbPeru("090111", "Huancavelica", "Huancavelica", "Mariscal Cáceres"));
        if (ubPeruService.findOne(907) == null)
            ubPeruService.save(new UbPeru("090112", "Huancavelica", "Huancavelica", "Moya"));
        if (ubPeruService.findOne(908) == null)
            ubPeruService.save(new UbPeru("090113", "Huancavelica", "Huancavelica", "Nuevo Occoro"));
        if (ubPeruService.findOne(909) == null)
            ubPeruService.save(new UbPeru("090114", "Huancavelica", "Huancavelica", "Palca"));
        if (ubPeruService.findOne(910) == null)
            ubPeruService.save(new UbPeru("090115", "Huancavelica", "Huancavelica", "Pilchaca"));
        if (ubPeruService.findOne(911) == null)
            ubPeruService.save(new UbPeru("090116", "Huancavelica", "Huancavelica", "Vilca"));
        if (ubPeruService.findOne(912) == null)
            ubPeruService.save(new UbPeru("090117", "Huancavelica", "Huancavelica", "Yauli"));
        if (ubPeruService.findOne(913) == null)
            ubPeruService.save(new UbPeru("090118", "Huancavelica", "Huancavelica", "Ascensión"));
        if (ubPeruService.findOne(914) == null)
            ubPeruService.save(new UbPeru("090119", "Huancavelica", "Huancavelica", "Huando"));
        if (ubPeruService.findOne(915) == null)
            ubPeruService.save(new UbPeru("090200", "Huancavelica", "Acobamba", ""));
        if (ubPeruService.findOne(916) == null)
            ubPeruService.save(new UbPeru("090201", "Huancavelica", "Acobamba", "Acobamba"));
        if (ubPeruService.findOne(917) == null)
            ubPeruService.save(new UbPeru("090202", "Huancavelica", "Acobamba", "Andabamba"));
        if (ubPeruService.findOne(918) == null)
            ubPeruService.save(new UbPeru("090203", "Huancavelica", "Acobamba", "Anta"));
        if (ubPeruService.findOne(919) == null)
            ubPeruService.save(new UbPeru("090204", "Huancavelica", "Acobamba", "Caja"));
        if (ubPeruService.findOne(920) == null)
            ubPeruService.save(new UbPeru("090205", "Huancavelica", "Acobamba", "Marcas"));
        if (ubPeruService.findOne(921) == null)
            ubPeruService.save(new UbPeru("090206", "Huancavelica", "Acobamba", "Paucara"));
        if (ubPeruService.findOne(922) == null)
            ubPeruService.save(new UbPeru("090207", "Huancavelica", "Acobamba", "Pomacocha"));
        if (ubPeruService.findOne(923) == null)
            ubPeruService.save(new UbPeru("090208", "Huancavelica", "Acobamba", "Rosario"));
        if (ubPeruService.findOne(924) == null)
            ubPeruService.save(new UbPeru("090300", "Huancavelica", "Angaraes", ""));
        if (ubPeruService.findOne(925) == null)
            ubPeruService.save(new UbPeru("090301", "Huancavelica", "Angaraes", "Lircay"));
        if (ubPeruService.findOne(926) == null)
            ubPeruService.save(new UbPeru("090302", "Huancavelica", "Angaraes", "Anchonga"));
        if (ubPeruService.findOne(927) == null)
            ubPeruService.save(new UbPeru("090303", "Huancavelica", "Angaraes", "Callanmarca"));
        if (ubPeruService.findOne(928) == null)
            ubPeruService.save(new UbPeru("090304", "Huancavelica", "Angaraes", "Ccochaccasa"));
        if (ubPeruService.findOne(929) == null)
            ubPeruService.save(new UbPeru("090305", "Huancavelica", "Angaraes", "Chincho"));
        if (ubPeruService.findOne(930) == null)
            ubPeruService.save(new UbPeru("090306", "Huancavelica", "Angaraes", "Congalla"));
        if (ubPeruService.findOne(931) == null)
            ubPeruService.save(new UbPeru("090307", "Huancavelica", "Angaraes", "Huanca-Huanca"));
        if (ubPeruService.findOne(932) == null)
            ubPeruService.save(new UbPeru("090308", "Huancavelica", "Angaraes", "Huayllay Grande"));
        if (ubPeruService.findOne(933) == null)
            ubPeruService.save(new UbPeru("090309", "Huancavelica", "Angaraes", "Julcamarca"));
        if (ubPeruService.findOne(934) == null)
            ubPeruService.save(new UbPeru("090310", "Huancavelica", "Angaraes", "San Antonio de Antaparco"));
        if (ubPeruService.findOne(935) == null)
            ubPeruService.save(new UbPeru("090311", "Huancavelica", "Angaraes", "Santo Tomas de Pata"));
        if (ubPeruService.findOne(936) == null)
            ubPeruService.save(new UbPeru("090312", "Huancavelica", "Angaraes", "Secclla"));
        if (ubPeruService.findOne(937) == null)
            ubPeruService.save(new UbPeru("090400", "Huancavelica", "Castrovirreyna", ""));
        if (ubPeruService.findOne(938) == null)
            ubPeruService.save(new UbPeru("090401", "Huancavelica", "Castrovirreyna", "Castrovirreyna"));
        if (ubPeruService.findOne(939) == null)
            ubPeruService.save(new UbPeru("090402", "Huancavelica", "Castrovirreyna", "Arma"));
        if (ubPeruService.findOne(940) == null)
            ubPeruService.save(new UbPeru("090403", "Huancavelica", "Castrovirreyna", "Aurahua"));
        if (ubPeruService.findOne(941) == null)
            ubPeruService.save(new UbPeru("090404", "Huancavelica", "Castrovirreyna", "Capillas"));
        if (ubPeruService.findOne(942) == null)
            ubPeruService.save(new UbPeru("090405", "Huancavelica", "Castrovirreyna", "Chupamarca"));
        if (ubPeruService.findOne(943) == null)
            ubPeruService.save(new UbPeru("090406", "Huancavelica", "Castrovirreyna", "Cocas"));
        if (ubPeruService.findOne(944) == null)
            ubPeruService.save(new UbPeru("090407", "Huancavelica", "Castrovirreyna", "Huachos"));
        if (ubPeruService.findOne(945) == null)
            ubPeruService.save(new UbPeru("090408", "Huancavelica", "Castrovirreyna", "Huamatambo"));
        if (ubPeruService.findOne(946) == null)
            ubPeruService.save(new UbPeru("090409", "Huancavelica", "Castrovirreyna", "Mollepampa"));
        if (ubPeruService.findOne(947) == null)
            ubPeruService.save(new UbPeru("090410", "Huancavelica", "Castrovirreyna", "San Juan"));
        if (ubPeruService.findOne(948) == null)
            ubPeruService.save(new UbPeru("090411", "Huancavelica", "Castrovirreyna", "Santa Ana"));
        if (ubPeruService.findOne(949) == null)
            ubPeruService.save(new UbPeru("090412", "Huancavelica", "Castrovirreyna", "Tantara"));
        if (ubPeruService.findOne(950) == null)
            ubPeruService.save(new UbPeru("090413", "Huancavelica", "Castrovirreyna", "Ticrapo"));
        if (ubPeruService.findOne(951) == null)
            ubPeruService.save(new UbPeru("090500", "Huancavelica", "Churcampa", ""));
        if (ubPeruService.findOne(952) == null)
            ubPeruService.save(new UbPeru("090501", "Huancavelica", "Churcampa", "Churcampa"));
        if (ubPeruService.findOne(953) == null)
            ubPeruService.save(new UbPeru("090502", "Huancavelica", "Churcampa", "Anco"));
        if (ubPeruService.findOne(954) == null)
            ubPeruService.save(new UbPeru("090503", "Huancavelica", "Churcampa", "Chinchihuasi"));
        if (ubPeruService.findOne(955) == null)
            ubPeruService.save(new UbPeru("090504", "Huancavelica", "Churcampa", "El Carmen"));
        if (ubPeruService.findOne(956) == null)
            ubPeruService.save(new UbPeru("090505", "Huancavelica", "Churcampa", "La Merced"));
        if (ubPeruService.findOne(957) == null)
            ubPeruService.save(new UbPeru("090506", "Huancavelica", "Churcampa", "Locroja"));
        if (ubPeruService.findOne(958) == null)
            ubPeruService.save(new UbPeru("090507", "Huancavelica", "Churcampa", "Paucarbamba"));
        if (ubPeruService.findOne(959) == null)
            ubPeruService.save(new UbPeru("090508", "Huancavelica", "Churcampa", "San Miguel de Mayocc"));
        if (ubPeruService.findOne(960) == null)
            ubPeruService.save(new UbPeru("090509", "Huancavelica", "Churcampa", "San Pedro de Coris"));
        if (ubPeruService.findOne(961) == null)
            ubPeruService.save(new UbPeru("090510", "Huancavelica", "Churcampa", "Pachamarca"));
        if (ubPeruService.findOne(962) == null)
            ubPeruService.save(new UbPeru("090511", "Huancavelica", "Churcampa", "Cosme"));
        if (ubPeruService.findOne(963) == null)
            ubPeruService.save(new UbPeru("090600", "Huancavelica", "Huaytará", ""));
        if (ubPeruService.findOne(964) == null)
            ubPeruService.save(new UbPeru("090601", "Huancavelica", "Huaytará", "Huaytara"));
        if (ubPeruService.findOne(965) == null)
            ubPeruService.save(new UbPeru("090602", "Huancavelica", "Huaytará", "Ayavi"));
        if (ubPeruService.findOne(966) == null)
            ubPeruService.save(new UbPeru("090603", "Huancavelica", "Huaytará", "Córdova"));
        if (ubPeruService.findOne(967) == null)
            ubPeruService.save(new UbPeru("090604", "Huancavelica", "Huaytará", "Huayacundo Arma"));
        if (ubPeruService.findOne(968) == null)
            ubPeruService.save(new UbPeru("090605", "Huancavelica", "Huaytará", "Laramarca"));
        if (ubPeruService.findOne(969) == null)
            ubPeruService.save(new UbPeru("090606", "Huancavelica", "Huaytará", "Ocoyo"));
        if (ubPeruService.findOne(970) == null)
            ubPeruService.save(new UbPeru("090607", "Huancavelica", "Huaytará", "Pilpichaca"));
        if (ubPeruService.findOne(971) == null)
            ubPeruService.save(new UbPeru("090608", "Huancavelica", "Huaytará", "Querco"));
        if (ubPeruService.findOne(972) == null)
            ubPeruService.save(new UbPeru("090609", "Huancavelica", "Huaytará", "Quito-Arma"));
        if (ubPeruService.findOne(973) == null)
            ubPeruService.save(new UbPeru("090610", "Huancavelica", "Huaytará", "San Antonio de Cusicancha"));
        if (ubPeruService.findOne(974) == null)
            ubPeruService.save(new UbPeru("090611", "Huancavelica", "Huaytará", "San Francisco de Sangayaico"));
        if (ubPeruService.findOne(975) == null)
            ubPeruService.save(new UbPeru("090612", "Huancavelica", "Huaytará", "San Isidro"));
        if (ubPeruService.findOne(976) == null)
            ubPeruService.save(new UbPeru("090613", "Huancavelica", "Huaytará", "Santiago de Chocorvos"));
        if (ubPeruService.findOne(977) == null)
            ubPeruService.save(new UbPeru("090614", "Huancavelica", "Huaytará", "Santiago de Quirahuara"));
        if (ubPeruService.findOne(978) == null)
            ubPeruService.save(new UbPeru("090615", "Huancavelica", "Huaytará", "Santo Domingo de Capillas"));
        if (ubPeruService.findOne(979) == null)
            ubPeruService.save(new UbPeru("090616", "Huancavelica", "Huaytará", "Tambo"));
        if (ubPeruService.findOne(980) == null)
            ubPeruService.save(new UbPeru("090700", "Huancavelica", "Tayacaja", ""));
        if (ubPeruService.findOne(981) == null)
            ubPeruService.save(new UbPeru("090701", "Huancavelica", "Tayacaja", "Pampas"));
        if (ubPeruService.findOne(982) == null)
            ubPeruService.save(new UbPeru("090702", "Huancavelica", "Tayacaja", "Acostambo"));
        if (ubPeruService.findOne(983) == null)
            ubPeruService.save(new UbPeru("090703", "Huancavelica", "Tayacaja", "Acraquia"));
        if (ubPeruService.findOne(984) == null)
            ubPeruService.save(new UbPeru("090704", "Huancavelica", "Tayacaja", "Ahuaycha"));
        if (ubPeruService.findOne(985) == null)
            ubPeruService.save(new UbPeru("090705", "Huancavelica", "Tayacaja", "Colcabamba"));
        if (ubPeruService.findOne(986) == null)
            ubPeruService.save(new UbPeru("090706", "Huancavelica", "Tayacaja", "Daniel Hernández"));
        if (ubPeruService.findOne(987) == null)
            ubPeruService.save(new UbPeru("090707", "Huancavelica", "Tayacaja", "Huachocolpa"));
        if (ubPeruService.findOne(988) == null)
            ubPeruService.save(new UbPeru("090709", "Huancavelica", "Tayacaja", "Huaribamba"));
        if (ubPeruService.findOne(989) == null)
            ubPeruService.save(new UbPeru("090710", "Huancavelica", "Tayacaja", "Ñahuimpuquio"));
        if (ubPeruService.findOne(990) == null)
            ubPeruService.save(new UbPeru("090711", "Huancavelica", "Tayacaja", "Pazos"));
        if (ubPeruService.findOne(991) == null)
            ubPeruService.save(new UbPeru("090713", "Huancavelica", "Tayacaja", "Quishuar"));
        if (ubPeruService.findOne(992) == null)
            ubPeruService.save(new UbPeru("090714", "Huancavelica", "Tayacaja", "Salcabamba"));
        if (ubPeruService.findOne(993) == null)
            ubPeruService.save(new UbPeru("090715", "Huancavelica", "Tayacaja", "Salcahuasi"));
        if (ubPeruService.findOne(994) == null)
            ubPeruService.save(new UbPeru("090716", "Huancavelica", "Tayacaja", "San Marcos de Rocchac"));
        if (ubPeruService.findOne(995) == null)
            ubPeruService.save(new UbPeru("090717", "Huancavelica", "Tayacaja", "Surcubamba"));
        if (ubPeruService.findOne(996) == null)
            ubPeruService.save(new UbPeru("090718", "Huancavelica", "Tayacaja", "Tintay Puncu"));
        if (ubPeruService.findOne(997) == null)
            ubPeruService.save(new UbPeru("090719", "Huancavelica", "Tayacaja", "Quichuas"));
        if (ubPeruService.findOne(998) == null)
            ubPeruService.save(new UbPeru("090720", "Huancavelica", "Tayacaja", "Andaymarca"));
        if (ubPeruService.findOne(999) == null)
            ubPeruService.save(new UbPeru("090721", "Huancavelica", "Tayacaja", "Roble"));
        if (ubPeruService.findOne(1000) == null)
            ubPeruService.save(new UbPeru("090722", "Huancavelica", "Tayacaja", "Pichos"));
        if (ubPeruService.findOne(1001) == null) ubPeruService.save(new UbPeru("100000", "Huánuco", "", ""));
        if (ubPeruService.findOne(1002) == null) ubPeruService.save(new UbPeru("100100", "Huánuco", "Huánuco", ""));
        if (ubPeruService.findOne(1003) == null)
            ubPeruService.save(new UbPeru("100101", "Huánuco", "Huánuco", "Huanuco"));
        if (ubPeruService.findOne(1004) == null)
            ubPeruService.save(new UbPeru("100102", "Huánuco", "Huánuco", "Amarilis"));
        if (ubPeruService.findOne(1005) == null)
            ubPeruService.save(new UbPeru("100103", "Huánuco", "Huánuco", "Chinchao"));
        if (ubPeruService.findOne(1006) == null)
            ubPeruService.save(new UbPeru("100104", "Huánuco", "Huánuco", "Churubamba"));
        if (ubPeruService.findOne(1007) == null)
            ubPeruService.save(new UbPeru("100105", "Huánuco", "Huánuco", "Margos"));
        if (ubPeruService.findOne(1008) == null)
            ubPeruService.save(new UbPeru("100106", "Huánuco", "Huánuco", "Quisqui (Kichki)"));
        if (ubPeruService.findOne(1009) == null)
            ubPeruService.save(new UbPeru("100107", "Huánuco", "Huánuco", "San Francisco de Cayran"));
        if (ubPeruService.findOne(1010) == null)
            ubPeruService.save(new UbPeru("100108", "Huánuco", "Huánuco", "San Pedro de Chaulan"));
        if (ubPeruService.findOne(1011) == null)
            ubPeruService.save(new UbPeru("100109", "Huánuco", "Huánuco", "Santa María del Valle"));
        if (ubPeruService.findOne(1012) == null)
            ubPeruService.save(new UbPeru("100110", "Huánuco", "Huánuco", "Yarumayo"));
        if (ubPeruService.findOne(1013) == null)
            ubPeruService.save(new UbPeru("100111", "Huánuco", "Huánuco", "Pillco Marca"));
        if (ubPeruService.findOne(1014) == null)
            ubPeruService.save(new UbPeru("100112", "Huánuco", "Huánuco", "Yacus"));
        if (ubPeruService.findOne(1015) == null)
            ubPeruService.save(new UbPeru("100113", "Huánuco", "Huánuco", "San Pablo de Pillao"));
        if (ubPeruService.findOne(1016) == null) ubPeruService.save(new UbPeru("100200", "Huánuco", "Ambo", ""));
        if (ubPeruService.findOne(1017) == null) ubPeruService.save(new UbPeru("100201", "Huánuco", "Ambo", "Ambo"));
        if (ubPeruService.findOne(1018) == null) ubPeruService.save(new UbPeru("100202", "Huánuco", "Ambo", "Cayna"));
        if (ubPeruService.findOne(1019) == null) ubPeruService.save(new UbPeru("100203", "Huánuco", "Ambo", "Colpas"));
        if (ubPeruService.findOne(1020) == null)
            ubPeruService.save(new UbPeru("100204", "Huánuco", "Ambo", "Conchamarca"));
        if (ubPeruService.findOne(1021) == null) ubPeruService.save(new UbPeru("100205", "Huánuco", "Ambo", "Huacar"));
        if (ubPeruService.findOne(1022) == null)
            ubPeruService.save(new UbPeru("100206", "Huánuco", "Ambo", "San Francisco"));
        if (ubPeruService.findOne(1023) == null)
            ubPeruService.save(new UbPeru("100207", "Huánuco", "Ambo", "San Rafael"));
        if (ubPeruService.findOne(1024) == null)
            ubPeruService.save(new UbPeru("100208", "Huánuco", "Ambo", "Tomay Kichwa"));
        if (ubPeruService.findOne(1025) == null) ubPeruService.save(new UbPeru("100300", "Huánuco", "Dos de Mayo", ""));
        if (ubPeruService.findOne(1026) == null)
            ubPeruService.save(new UbPeru("100301", "Huánuco", "Dos de Mayo", "La Unión"));
        if (ubPeruService.findOne(1027) == null)
            ubPeruService.save(new UbPeru("100307", "Huánuco", "Dos de Mayo", "Chuquis"));
        if (ubPeruService.findOne(1028) == null)
            ubPeruService.save(new UbPeru("100311", "Huánuco", "Dos de Mayo", "Marías"));
        if (ubPeruService.findOne(1029) == null)
            ubPeruService.save(new UbPeru("100313", "Huánuco", "Dos de Mayo", "Pachas"));
        if (ubPeruService.findOne(1030) == null)
            ubPeruService.save(new UbPeru("100316", "Huánuco", "Dos de Mayo", "Quivilla"));
        if (ubPeruService.findOne(1031) == null)
            ubPeruService.save(new UbPeru("100317", "Huánuco", "Dos de Mayo", "Ripan"));
        if (ubPeruService.findOne(1032) == null)
            ubPeruService.save(new UbPeru("100321", "Huánuco", "Dos de Mayo", "Shunqui"));
        if (ubPeruService.findOne(1033) == null)
            ubPeruService.save(new UbPeru("100322", "Huánuco", "Dos de Mayo", "Sillapata"));
        if (ubPeruService.findOne(1034) == null)
            ubPeruService.save(new UbPeru("100323", "Huánuco", "Dos de Mayo", "Yanas"));
        if (ubPeruService.findOne(1035) == null) ubPeruService.save(new UbPeru("100400", "Huánuco", "Huacaybamba", ""));
        if (ubPeruService.findOne(1036) == null)
            ubPeruService.save(new UbPeru("100401", "Huánuco", "Huacaybamba", "Huacaybamba"));
        if (ubPeruService.findOne(1037) == null)
            ubPeruService.save(new UbPeru("100402", "Huánuco", "Huacaybamba", "Canchabamba"));
        if (ubPeruService.findOne(1038) == null)
            ubPeruService.save(new UbPeru("100403", "Huánuco", "Huacaybamba", "Cochabamba"));
        if (ubPeruService.findOne(1039) == null)
            ubPeruService.save(new UbPeru("100404", "Huánuco", "Huacaybamba", "Pinra"));
        if (ubPeruService.findOne(1040) == null) ubPeruService.save(new UbPeru("100500", "Huánuco", "Huamalíes", ""));
        if (ubPeruService.findOne(1041) == null)
            ubPeruService.save(new UbPeru("100501", "Huánuco", "Huamalíes", "Llata"));
        if (ubPeruService.findOne(1042) == null)
            ubPeruService.save(new UbPeru("100502", "Huánuco", "Huamalíes", "Arancay"));
        if (ubPeruService.findOne(1043) == null)
            ubPeruService.save(new UbPeru("100503", "Huánuco", "Huamalíes", "Chavín de Pariarca"));
        if (ubPeruService.findOne(1044) == null)
            ubPeruService.save(new UbPeru("100504", "Huánuco", "Huamalíes", "Jacas Grande"));
        if (ubPeruService.findOne(1045) == null)
            ubPeruService.save(new UbPeru("100505", "Huánuco", "Huamalíes", "Jircan"));
        if (ubPeruService.findOne(1046) == null)
            ubPeruService.save(new UbPeru("100506", "Huánuco", "Huamalíes", "Miraflores"));
        if (ubPeruService.findOne(1047) == null)
            ubPeruService.save(new UbPeru("100507", "Huánuco", "Huamalíes", "Monzón"));
        if (ubPeruService.findOne(1048) == null)
            ubPeruService.save(new UbPeru("100508", "Huánuco", "Huamalíes", "Punchao"));
        if (ubPeruService.findOne(1049) == null)
            ubPeruService.save(new UbPeru("100509", "Huánuco", "Huamalíes", "Puños"));
        if (ubPeruService.findOne(1050) == null)
            ubPeruService.save(new UbPeru("100510", "Huánuco", "Huamalíes", "Singa"));
        if (ubPeruService.findOne(1051) == null)
            ubPeruService.save(new UbPeru("100511", "Huánuco", "Huamalíes", "Tantamayo"));
        if (ubPeruService.findOne(1052) == null)
            ubPeruService.save(new UbPeru("100600", "Huánuco", "Leoncio Prado", ""));
        if (ubPeruService.findOne(1053) == null)
            ubPeruService.save(new UbPeru("100601", "Huánuco", "Leoncio Prado", "Rupa-Rupa"));
        if (ubPeruService.findOne(1054) == null)
            ubPeruService.save(new UbPeru("100602", "Huánuco", "Leoncio Prado", "Daniel Alomía Robles"));
        if (ubPeruService.findOne(1055) == null)
            ubPeruService.save(new UbPeru("100603", "Huánuco", "Leoncio Prado", "Hermílio Valdizan"));
        if (ubPeruService.findOne(1056) == null)
            ubPeruService.save(new UbPeru("100604", "Huánuco", "Leoncio Prado", "José Crespo y Castillo"));
        if (ubPeruService.findOne(1057) == null)
            ubPeruService.save(new UbPeru("100605", "Huánuco", "Leoncio Prado", "Luyando"));
        if (ubPeruService.findOne(1058) == null)
            ubPeruService.save(new UbPeru("100606", "Huánuco", "Leoncio Prado", "Mariano Damaso Beraun"));
        if (ubPeruService.findOne(1059) == null)
            ubPeruService.save(new UbPeru("100607", "Huánuco", "Leoncio Prado", "Pucayacu"));
        if (ubPeruService.findOne(1060) == null)
            ubPeruService.save(new UbPeru("100608", "Huánuco", "Leoncio Prado", "Castillo Grande"));
        if (ubPeruService.findOne(1061) == null) ubPeruService.save(new UbPeru("100700", "Huánuco", "Marañón", ""));
        if (ubPeruService.findOne(1062) == null)
            ubPeruService.save(new UbPeru("100701", "Huánuco", "Marañón", "Huacrachuco"));
        if (ubPeruService.findOne(1063) == null)
            ubPeruService.save(new UbPeru("100702", "Huánuco", "Marañón", "Cholon"));
        if (ubPeruService.findOne(1064) == null)
            ubPeruService.save(new UbPeru("100703", "Huánuco", "Marañón", "San Buenaventura"));
        if (ubPeruService.findOne(1065) == null)
            ubPeruService.save(new UbPeru("100704", "Huánuco", "Marañón", "La Morada"));
        if (ubPeruService.findOne(1066) == null)
            ubPeruService.save(new UbPeru("100705", "Huánuco", "Marañón", "Santa Rosa de Alto Yanajanca"));
        if (ubPeruService.findOne(1067) == null) ubPeruService.save(new UbPeru("100800", "Huánuco", "Pachitea", ""));
        if (ubPeruService.findOne(1068) == null)
            ubPeruService.save(new UbPeru("100801", "Huánuco", "Pachitea", "Panao"));
        if (ubPeruService.findOne(1069) == null)
            ubPeruService.save(new UbPeru("100802", "Huánuco", "Pachitea", "Chaglla"));
        if (ubPeruService.findOne(1070) == null)
            ubPeruService.save(new UbPeru("100803", "Huánuco", "Pachitea", "Molino"));
        if (ubPeruService.findOne(1071) == null)
            ubPeruService.save(new UbPeru("100804", "Huánuco", "Pachitea", "Umari"));
        if (ubPeruService.findOne(1072) == null) ubPeruService.save(new UbPeru("100900", "Huánuco", "Puerto Inca", ""));
        if (ubPeruService.findOne(1073) == null)
            ubPeruService.save(new UbPeru("100901", "Huánuco", "Puerto Inca", "Puerto Inca"));
        if (ubPeruService.findOne(1074) == null)
            ubPeruService.save(new UbPeru("100902", "Huánuco", "Puerto Inca", "Codo del Pozuzo"));
        if (ubPeruService.findOne(1075) == null)
            ubPeruService.save(new UbPeru("100903", "Huánuco", "Puerto Inca", "Honoria"));
        if (ubPeruService.findOne(1076) == null)
            ubPeruService.save(new UbPeru("100904", "Huánuco", "Puerto Inca", "Tournavista"));
        if (ubPeruService.findOne(1077) == null)
            ubPeruService.save(new UbPeru("100905", "Huánuco", "Puerto Inca", "Yuyapichis"));
        if (ubPeruService.findOne(1078) == null) ubPeruService.save(new UbPeru("101000", "Huánuco", "Lauricocha", ""));
        if (ubPeruService.findOne(1079) == null)
            ubPeruService.save(new UbPeru("101001", "Huánuco", "Lauricocha", "Jesús"));
        if (ubPeruService.findOne(1080) == null)
            ubPeruService.save(new UbPeru("101002", "Huánuco", "Lauricocha", "Baños"));
        if (ubPeruService.findOne(1081) == null)
            ubPeruService.save(new UbPeru("101003", "Huánuco", "Lauricocha", "Jivia"));
        if (ubPeruService.findOne(1082) == null)
            ubPeruService.save(new UbPeru("101004", "Huánuco", "Lauricocha", "Queropalca"));
        if (ubPeruService.findOne(1083) == null)
            ubPeruService.save(new UbPeru("101005", "Huánuco", "Lauricocha", "Rondos"));
        if (ubPeruService.findOne(1084) == null)
            ubPeruService.save(new UbPeru("101006", "Huánuco", "Lauricocha", "San Francisco de Asís"));
        if (ubPeruService.findOne(1085) == null)
            ubPeruService.save(new UbPeru("101007", "Huánuco", "Lauricocha", "San Miguel de Cauri"));
        if (ubPeruService.findOne(1086) == null) ubPeruService.save(new UbPeru("101100", "Huánuco", "Yarowilca", ""));
        if (ubPeruService.findOne(1087) == null)
            ubPeruService.save(new UbPeru("101101", "Huánuco", "Yarowilca", "Chavinillo"));
        if (ubPeruService.findOne(1088) == null)
            ubPeruService.save(new UbPeru("101102", "Huánuco", "Yarowilca", "Cahuac"));
        if (ubPeruService.findOne(1089) == null)
            ubPeruService.save(new UbPeru("101103", "Huánuco", "Yarowilca", "Chacabamba"));
        if (ubPeruService.findOne(1090) == null)
            ubPeruService.save(new UbPeru("101104", "Huánuco", "Yarowilca", "Aparicio Pomares"));
        if (ubPeruService.findOne(1091) == null)
            ubPeruService.save(new UbPeru("101105", "Huánuco", "Yarowilca", "Jacas Chico"));
        if (ubPeruService.findOne(1092) == null)
            ubPeruService.save(new UbPeru("101106", "Huánuco", "Yarowilca", "Obas"));
        if (ubPeruService.findOne(1093) == null)
            ubPeruService.save(new UbPeru("101107", "Huánuco", "Yarowilca", "Pampamarca"));
        if (ubPeruService.findOne(1094) == null)
            ubPeruService.save(new UbPeru("101108", "Huánuco", "Yarowilca", "Choras"));
        if (ubPeruService.findOne(1095) == null) ubPeruService.save(new UbPeru("110000", "Ica", "", ""));
        if (ubPeruService.findOne(1096) == null) ubPeruService.save(new UbPeru("110100", "Ica", "Ica", ""));
        if (ubPeruService.findOne(1097) == null) ubPeruService.save(new UbPeru("110101", "Ica", "Ica", "Ica"));
        if (ubPeruService.findOne(1098) == null) ubPeruService.save(new UbPeru("110102", "Ica", "Ica", "La Tinguiña"));
        if (ubPeruService.findOne(1099) == null) ubPeruService.save(new UbPeru("110103", "Ica", "Ica", "Los Aquijes"));
        if (ubPeruService.findOne(1100) == null) ubPeruService.save(new UbPeru("110104", "Ica", "Ica", "Ocucaje"));
        if (ubPeruService.findOne(1101) == null) ubPeruService.save(new UbPeru("110105", "Ica", "Ica", "Pachacutec"));
        if (ubPeruService.findOne(1102) == null) ubPeruService.save(new UbPeru("110106", "Ica", "Ica", "Parcona"));
        if (ubPeruService.findOne(1103) == null) ubPeruService.save(new UbPeru("110107", "Ica", "Ica", "Pueblo Nuevo"));
        if (ubPeruService.findOne(1104) == null) ubPeruService.save(new UbPeru("110108", "Ica", "Ica", "Salas"));
        if (ubPeruService.findOne(1105) == null)
            ubPeruService.save(new UbPeru("110109", "Ica", "Ica", "San José de Los Molinos"));
        if (ubPeruService.findOne(1106) == null)
            ubPeruService.save(new UbPeru("110110", "Ica", "Ica", "San Juan Bautista"));
        if (ubPeruService.findOne(1107) == null) ubPeruService.save(new UbPeru("110111", "Ica", "Ica", "Santiago"));
        if (ubPeruService.findOne(1108) == null) ubPeruService.save(new UbPeru("110112", "Ica", "Ica", "Subtanjalla"));
        if (ubPeruService.findOne(1109) == null) ubPeruService.save(new UbPeru("110113", "Ica", "Ica", "Tate"));
        if (ubPeruService.findOne(1110) == null)
            ubPeruService.save(new UbPeru("110114", "Ica", "Ica", "Yauca del Rosario"));
        if (ubPeruService.findOne(1111) == null) ubPeruService.save(new UbPeru("110200", "Ica", "Chincha", ""));
        if (ubPeruService.findOne(1112) == null)
            ubPeruService.save(new UbPeru("110201", "Ica", "Chincha", "Chincha Alta"));
        if (ubPeruService.findOne(1113) == null)
            ubPeruService.save(new UbPeru("110202", "Ica", "Chincha", "Alto Laran"));
        if (ubPeruService.findOne(1114) == null) ubPeruService.save(new UbPeru("110203", "Ica", "Chincha", "Chavin"));
        if (ubPeruService.findOne(1115) == null)
            ubPeruService.save(new UbPeru("110204", "Ica", "Chincha", "Chincha Baja"));
        if (ubPeruService.findOne(1116) == null)
            ubPeruService.save(new UbPeru("110205", "Ica", "Chincha", "El Carmen"));
        if (ubPeruService.findOne(1117) == null)
            ubPeruService.save(new UbPeru("110206", "Ica", "Chincha", "Grocio Prado"));
        if (ubPeruService.findOne(1118) == null)
            ubPeruService.save(new UbPeru("110207", "Ica", "Chincha", "Pueblo Nuevo"));
        if (ubPeruService.findOne(1119) == null)
            ubPeruService.save(new UbPeru("110208", "Ica", "Chincha", "San Juan de Yanac"));
        if (ubPeruService.findOne(1120) == null)
            ubPeruService.save(new UbPeru("110209", "Ica", "Chincha", "San Pedro de Huacarpana"));
        if (ubPeruService.findOne(1121) == null) ubPeruService.save(new UbPeru("110210", "Ica", "Chincha", "Sunampe"));
        if (ubPeruService.findOne(1122) == null)
            ubPeruService.save(new UbPeru("110211", "Ica", "Chincha", "Tambo de Mora"));
        if (ubPeruService.findOne(1123) == null) ubPeruService.save(new UbPeru("110300", "Ica", "Nasca", ""));
        if (ubPeruService.findOne(1124) == null) ubPeruService.save(new UbPeru("110301", "Ica", "Nasca", "Nasca"));
        if (ubPeruService.findOne(1125) == null) ubPeruService.save(new UbPeru("110302", "Ica", "Nasca", "Changuillo"));
        if (ubPeruService.findOne(1126) == null) ubPeruService.save(new UbPeru("110303", "Ica", "Nasca", "El Ingenio"));
        if (ubPeruService.findOne(1127) == null) ubPeruService.save(new UbPeru("110304", "Ica", "Nasca", "Marcona"));
        if (ubPeruService.findOne(1128) == null)
            ubPeruService.save(new UbPeru("110305", "Ica", "Nasca", "Vista Alegre"));
        if (ubPeruService.findOne(1129) == null) ubPeruService.save(new UbPeru("110400", "Ica", "Palpa", ""));
        if (ubPeruService.findOne(1130) == null) ubPeruService.save(new UbPeru("110401", "Ica", "Palpa", "Palpa"));
        if (ubPeruService.findOne(1131) == null) ubPeruService.save(new UbPeru("110402", "Ica", "Palpa", "Llipata"));
        if (ubPeruService.findOne(1132) == null) ubPeruService.save(new UbPeru("110403", "Ica", "Palpa", "Río Grande"));
        if (ubPeruService.findOne(1133) == null) ubPeruService.save(new UbPeru("110404", "Ica", "Palpa", "Santa Cruz"));
        if (ubPeruService.findOne(1134) == null) ubPeruService.save(new UbPeru("110405", "Ica", "Palpa", "Tibillo"));
        if (ubPeruService.findOne(1135) == null) ubPeruService.save(new UbPeru("110500", "Ica", "Pisco", ""));
        if (ubPeruService.findOne(1136) == null) ubPeruService.save(new UbPeru("110501", "Ica", "Pisco", "Pisco"));
        if (ubPeruService.findOne(1137) == null) ubPeruService.save(new UbPeru("110502", "Ica", "Pisco", "Huancano"));
        if (ubPeruService.findOne(1138) == null) ubPeruService.save(new UbPeru("110503", "Ica", "Pisco", "Humay"));
        if (ubPeruService.findOne(1139) == null)
            ubPeruService.save(new UbPeru("110504", "Ica", "Pisco", "Independencia"));
        if (ubPeruService.findOne(1140) == null) ubPeruService.save(new UbPeru("110505", "Ica", "Pisco", "Paracas"));
        if (ubPeruService.findOne(1141) == null) ubPeruService.save(new UbPeru("110506", "Ica", "Pisco", "San Andrés"));
        if (ubPeruService.findOne(1142) == null)
            ubPeruService.save(new UbPeru("110507", "Ica", "Pisco", "San Clemente"));
        if (ubPeruService.findOne(1143) == null)
            ubPeruService.save(new UbPeru("110508", "Ica", "Pisco", "Tupac Amaru Inca"));
        if (ubPeruService.findOne(1144) == null) ubPeruService.save(new UbPeru("120000", "Junín", "", ""));
        if (ubPeruService.findOne(1145) == null) ubPeruService.save(new UbPeru("120100", "Junín", "Huancayo", ""));
        if (ubPeruService.findOne(1146) == null)
            ubPeruService.save(new UbPeru("120101", "Junín", "Huancayo", "Huancayo"));
        if (ubPeruService.findOne(1147) == null)
            ubPeruService.save(new UbPeru("120104", "Junín", "Huancayo", "Carhuacallanga"));
        if (ubPeruService.findOne(1148) == null)
            ubPeruService.save(new UbPeru("120105", "Junín", "Huancayo", "Chacapampa"));
        if (ubPeruService.findOne(1149) == null)
            ubPeruService.save(new UbPeru("120106", "Junín", "Huancayo", "Chicche"));
        if (ubPeruService.findOne(1150) == null)
            ubPeruService.save(new UbPeru("120107", "Junín", "Huancayo", "Chilca"));
        if (ubPeruService.findOne(1151) == null)
            ubPeruService.save(new UbPeru("120108", "Junín", "Huancayo", "Chongos Alto"));
        if (ubPeruService.findOne(1152) == null)
            ubPeruService.save(new UbPeru("120111", "Junín", "Huancayo", "Chupuro"));
        if (ubPeruService.findOne(1153) == null) ubPeruService.save(new UbPeru("120112", "Junín", "Huancayo", "Colca"));
        if (ubPeruService.findOne(1154) == null)
            ubPeruService.save(new UbPeru("120113", "Junín", "Huancayo", "Cullhuas"));
        if (ubPeruService.findOne(1155) == null)
            ubPeruService.save(new UbPeru("120114", "Junín", "Huancayo", "El Tambo"));
        if (ubPeruService.findOne(1156) == null)
            ubPeruService.save(new UbPeru("120116", "Junín", "Huancayo", "Huacrapuquio"));
        if (ubPeruService.findOne(1157) == null)
            ubPeruService.save(new UbPeru("120117", "Junín", "Huancayo", "Hualhuas"));
        if (ubPeruService.findOne(1158) == null)
            ubPeruService.save(new UbPeru("120119", "Junín", "Huancayo", "Huancan"));
        if (ubPeruService.findOne(1159) == null)
            ubPeruService.save(new UbPeru("120120", "Junín", "Huancayo", "Huasicancha"));
        if (ubPeruService.findOne(1160) == null)
            ubPeruService.save(new UbPeru("120121", "Junín", "Huancayo", "Huayucachi"));
        if (ubPeruService.findOne(1161) == null)
            ubPeruService.save(new UbPeru("120122", "Junín", "Huancayo", "Ingenio"));
        if (ubPeruService.findOne(1162) == null)
            ubPeruService.save(new UbPeru("120124", "Junín", "Huancayo", "Pariahuanca"));
        if (ubPeruService.findOne(1163) == null)
            ubPeruService.save(new UbPeru("120125", "Junín", "Huancayo", "Pilcomayo"));
        if (ubPeruService.findOne(1164) == null)
            ubPeruService.save(new UbPeru("120126", "Junín", "Huancayo", "Pucara"));
        if (ubPeruService.findOne(1165) == null)
            ubPeruService.save(new UbPeru("120127", "Junín", "Huancayo", "Quichuay"));
        if (ubPeruService.findOne(1166) == null)
            ubPeruService.save(new UbPeru("120128", "Junín", "Huancayo", "Quilcas"));
        if (ubPeruService.findOne(1167) == null)
            ubPeruService.save(new UbPeru("120129", "Junín", "Huancayo", "San Agustín"));
        if (ubPeruService.findOne(1168) == null)
            ubPeruService.save(new UbPeru("120130", "Junín", "Huancayo", "San Jerónimo de Tunan"));
        if (ubPeruService.findOne(1169) == null) ubPeruService.save(new UbPeru("120132", "Junín", "Huancayo", "Saño"));
        if (ubPeruService.findOne(1170) == null)
            ubPeruService.save(new UbPeru("120133", "Junín", "Huancayo", "Sapallanga"));
        if (ubPeruService.findOne(1171) == null)
            ubPeruService.save(new UbPeru("120134", "Junín", "Huancayo", "Sicaya"));
        if (ubPeruService.findOne(1172) == null)
            ubPeruService.save(new UbPeru("120135", "Junín", "Huancayo", "Santo Domingo de Acobamba"));
        if (ubPeruService.findOne(1173) == null)
            ubPeruService.save(new UbPeru("120136", "Junín", "Huancayo", "Viques"));
        if (ubPeruService.findOne(1174) == null) ubPeruService.save(new UbPeru("120200", "Junín", "Concepción", ""));
        if (ubPeruService.findOne(1175) == null)
            ubPeruService.save(new UbPeru("120201", "Junín", "Concepción", "Concepción"));
        if (ubPeruService.findOne(1176) == null) ubPeruService.save(new UbPeru("120202", "Junín", "Concepción", "Aco"));
        if (ubPeruService.findOne(1177) == null)
            ubPeruService.save(new UbPeru("120203", "Junín", "Concepción", "Andamarca"));
        if (ubPeruService.findOne(1178) == null)
            ubPeruService.save(new UbPeru("120204", "Junín", "Concepción", "Chambara"));
        if (ubPeruService.findOne(1179) == null)
            ubPeruService.save(new UbPeru("120205", "Junín", "Concepción", "Cochas"));
        if (ubPeruService.findOne(1180) == null)
            ubPeruService.save(new UbPeru("120206", "Junín", "Concepción", "Comas"));
        if (ubPeruService.findOne(1181) == null)
            ubPeruService.save(new UbPeru("120207", "Junín", "Concepción", "Heroínas Toledo"));
        if (ubPeruService.findOne(1182) == null)
            ubPeruService.save(new UbPeru("120208", "Junín", "Concepción", "Manzanares"));
        if (ubPeruService.findOne(1183) == null)
            ubPeruService.save(new UbPeru("120209", "Junín", "Concepción", "Mariscal Castilla"));
        if (ubPeruService.findOne(1184) == null)
            ubPeruService.save(new UbPeru("120210", "Junín", "Concepción", "Matahuasi"));
        if (ubPeruService.findOne(1185) == null)
            ubPeruService.save(new UbPeru("120211", "Junín", "Concepción", "Mito"));
        if (ubPeruService.findOne(1186) == null)
            ubPeruService.save(new UbPeru("120212", "Junín", "Concepción", "Nueve de Julio"));
        if (ubPeruService.findOne(1187) == null)
            ubPeruService.save(new UbPeru("120213", "Junín", "Concepción", "Orcotuna"));
        if (ubPeruService.findOne(1188) == null)
            ubPeruService.save(new UbPeru("120214", "Junín", "Concepción", "San José de Quero"));
        if (ubPeruService.findOne(1189) == null)
            ubPeruService.save(new UbPeru("120215", "Junín", "Concepción", "Santa Rosa de Ocopa"));
        if (ubPeruService.findOne(1190) == null) ubPeruService.save(new UbPeru("120300", "Junín", "Chanchamayo", ""));
        if (ubPeruService.findOne(1191) == null)
            ubPeruService.save(new UbPeru("120301", "Junín", "Chanchamayo", "Chanchamayo"));
        if (ubPeruService.findOne(1192) == null)
            ubPeruService.save(new UbPeru("120302", "Junín", "Chanchamayo", "Perene"));
        if (ubPeruService.findOne(1193) == null)
            ubPeruService.save(new UbPeru("120303", "Junín", "Chanchamayo", "Pichanaqui"));
        if (ubPeruService.findOne(1194) == null)
            ubPeruService.save(new UbPeru("120304", "Junín", "Chanchamayo", "San Luis de Shuaro"));
        if (ubPeruService.findOne(1195) == null)
            ubPeruService.save(new UbPeru("120305", "Junín", "Chanchamayo", "San Ramón"));
        if (ubPeruService.findOne(1196) == null)
            ubPeruService.save(new UbPeru("120306", "Junín", "Chanchamayo", "Vitoc"));
        if (ubPeruService.findOne(1197) == null) ubPeruService.save(new UbPeru("120400", "Junín", "Jauja", ""));
        if (ubPeruService.findOne(1198) == null) ubPeruService.save(new UbPeru("120401", "Junín", "Jauja", "Jauja"));
        if (ubPeruService.findOne(1199) == null) ubPeruService.save(new UbPeru("120402", "Junín", "Jauja", "Acolla"));
        if (ubPeruService.findOne(1200) == null) ubPeruService.save(new UbPeru("120403", "Junín", "Jauja", "Apata"));
        if (ubPeruService.findOne(1201) == null) ubPeruService.save(new UbPeru("120404", "Junín", "Jauja", "Ataura"));
        if (ubPeruService.findOne(1202) == null)
            ubPeruService.save(new UbPeru("120405", "Junín", "Jauja", "Canchayllo"));
        if (ubPeruService.findOne(1203) == null) ubPeruService.save(new UbPeru("120406", "Junín", "Jauja", "Curicaca"));
        if (ubPeruService.findOne(1204) == null)
            ubPeruService.save(new UbPeru("120407", "Junín", "Jauja", "El Mantaro"));
        if (ubPeruService.findOne(1205) == null) ubPeruService.save(new UbPeru("120408", "Junín", "Jauja", "Huamali"));
        if (ubPeruService.findOne(1206) == null)
            ubPeruService.save(new UbPeru("120409", "Junín", "Jauja", "Huaripampa"));
        if (ubPeruService.findOne(1207) == null) ubPeruService.save(new UbPeru("120410", "Junín", "Jauja", "Huertas"));
        if (ubPeruService.findOne(1208) == null)
            ubPeruService.save(new UbPeru("120411", "Junín", "Jauja", "Janjaillo"));
        if (ubPeruService.findOne(1209) == null) ubPeruService.save(new UbPeru("120412", "Junín", "Jauja", "Julcán"));
        if (ubPeruService.findOne(1210) == null)
            ubPeruService.save(new UbPeru("120413", "Junín", "Jauja", "Leonor Ordóñez"));
        if (ubPeruService.findOne(1211) == null)
            ubPeruService.save(new UbPeru("120414", "Junín", "Jauja", "Llocllapampa"));
        if (ubPeruService.findOne(1212) == null) ubPeruService.save(new UbPeru("120415", "Junín", "Jauja", "Marco"));
        if (ubPeruService.findOne(1213) == null) ubPeruService.save(new UbPeru("120416", "Junín", "Jauja", "Masma"));
        if (ubPeruService.findOne(1214) == null)
            ubPeruService.save(new UbPeru("120417", "Junín", "Jauja", "Masma Chicche"));
        if (ubPeruService.findOne(1215) == null) ubPeruService.save(new UbPeru("120418", "Junín", "Jauja", "Molinos"));
        if (ubPeruService.findOne(1216) == null)
            ubPeruService.save(new UbPeru("120419", "Junín", "Jauja", "Monobamba"));
        if (ubPeruService.findOne(1217) == null) ubPeruService.save(new UbPeru("120420", "Junín", "Jauja", "Muqui"));
        if (ubPeruService.findOne(1218) == null)
            ubPeruService.save(new UbPeru("120421", "Junín", "Jauja", "Muquiyauyo"));
        if (ubPeruService.findOne(1219) == null) ubPeruService.save(new UbPeru("120422", "Junín", "Jauja", "Paca"));
        if (ubPeruService.findOne(1220) == null) ubPeruService.save(new UbPeru("120423", "Junín", "Jauja", "Paccha"));
        if (ubPeruService.findOne(1221) == null) ubPeruService.save(new UbPeru("120424", "Junín", "Jauja", "Pancan"));
        if (ubPeruService.findOne(1222) == null) ubPeruService.save(new UbPeru("120425", "Junín", "Jauja", "Parco"));
        if (ubPeruService.findOne(1223) == null)
            ubPeruService.save(new UbPeru("120426", "Junín", "Jauja", "Pomacancha"));
        if (ubPeruService.findOne(1224) == null) ubPeruService.save(new UbPeru("120427", "Junín", "Jauja", "Ricran"));
        if (ubPeruService.findOne(1225) == null)
            ubPeruService.save(new UbPeru("120428", "Junín", "Jauja", "San Lorenzo"));
        if (ubPeruService.findOne(1226) == null)
            ubPeruService.save(new UbPeru("120429", "Junín", "Jauja", "San Pedro de Chunan"));
        if (ubPeruService.findOne(1227) == null) ubPeruService.save(new UbPeru("120430", "Junín", "Jauja", "Sausa"));
        if (ubPeruService.findOne(1228) == null) ubPeruService.save(new UbPeru("120431", "Junín", "Jauja", "Sincos"));
        if (ubPeruService.findOne(1229) == null)
            ubPeruService.save(new UbPeru("120432", "Junín", "Jauja", "Tunan Marca"));
        if (ubPeruService.findOne(1230) == null) ubPeruService.save(new UbPeru("120433", "Junín", "Jauja", "Yauli"));
        if (ubPeruService.findOne(1231) == null) ubPeruService.save(new UbPeru("120434", "Junín", "Jauja", "Yauyos"));
        if (ubPeruService.findOne(1232) == null) ubPeruService.save(new UbPeru("120500", "Junín", "Junín", ""));
        if (ubPeruService.findOne(1233) == null) ubPeruService.save(new UbPeru("120501", "Junín", "Junín", "Junin"));
        if (ubPeruService.findOne(1234) == null)
            ubPeruService.save(new UbPeru("120502", "Junín", "Junín", "Carhuamayo"));
        if (ubPeruService.findOne(1235) == null) ubPeruService.save(new UbPeru("120503", "Junín", "Junín", "Ondores"));
        if (ubPeruService.findOne(1236) == null) ubPeruService.save(new UbPeru("120504", "Junín", "Junín", "Ulcumayo"));
        if (ubPeruService.findOne(1237) == null) ubPeruService.save(new UbPeru("120600", "Junín", "Satipo", ""));
        if (ubPeruService.findOne(1238) == null) ubPeruService.save(new UbPeru("120601", "Junín", "Satipo", "Satipo"));
        if (ubPeruService.findOne(1239) == null)
            ubPeruService.save(new UbPeru("120602", "Junín", "Satipo", "Coviriali"));
        if (ubPeruService.findOne(1240) == null) ubPeruService.save(new UbPeru("120603", "Junín", "Satipo", "Llaylla"));
        if (ubPeruService.findOne(1241) == null)
            ubPeruService.save(new UbPeru("120604", "Junín", "Satipo", "Mazamari"));
        if (ubPeruService.findOne(1242) == null)
            ubPeruService.save(new UbPeru("120605", "Junín", "Satipo", "Pampa Hermosa"));
        if (ubPeruService.findOne(1243) == null) ubPeruService.save(new UbPeru("120606", "Junín", "Satipo", "Pangoa"));
        if (ubPeruService.findOne(1244) == null)
            ubPeruService.save(new UbPeru("120607", "Junín", "Satipo", "Río Negro"));
        if (ubPeruService.findOne(1245) == null)
            ubPeruService.save(new UbPeru("120608", "Junín", "Satipo", "Río Tambo"));
        if (ubPeruService.findOne(1246) == null)
            ubPeruService.save(new UbPeru("120609", "Junín", "Satipo", "Vizcatan del Ene"));
        if (ubPeruService.findOne(1247) == null) ubPeruService.save(new UbPeru("120700", "Junín", "Tarma", ""));
        if (ubPeruService.findOne(1248) == null) ubPeruService.save(new UbPeru("120701", "Junín", "Tarma", "Tarma"));
        if (ubPeruService.findOne(1249) == null) ubPeruService.save(new UbPeru("120702", "Junín", "Tarma", "Acobamba"));
        if (ubPeruService.findOne(1250) == null)
            ubPeruService.save(new UbPeru("120703", "Junín", "Tarma", "Huaricolca"));
        if (ubPeruService.findOne(1251) == null)
            ubPeruService.save(new UbPeru("120704", "Junín", "Tarma", "Huasahuasi"));
        if (ubPeruService.findOne(1252) == null) ubPeruService.save(new UbPeru("120705", "Junín", "Tarma", "La Unión"));
        if (ubPeruService.findOne(1253) == null) ubPeruService.save(new UbPeru("120706", "Junín", "Tarma", "Palca"));
        if (ubPeruService.findOne(1254) == null)
            ubPeruService.save(new UbPeru("120707", "Junín", "Tarma", "Palcamayo"));
        if (ubPeruService.findOne(1255) == null)
            ubPeruService.save(new UbPeru("120708", "Junín", "Tarma", "San Pedro de Cajas"));
        if (ubPeruService.findOne(1256) == null) ubPeruService.save(new UbPeru("120709", "Junín", "Tarma", "Tapo"));
        if (ubPeruService.findOne(1257) == null) ubPeruService.save(new UbPeru("120800", "Junín", "Yauli", ""));
        if (ubPeruService.findOne(1258) == null) ubPeruService.save(new UbPeru("120801", "Junín", "Yauli", "La Oroya"));
        if (ubPeruService.findOne(1259) == null)
            ubPeruService.save(new UbPeru("120802", "Junín", "Yauli", "Chacapalpa"));
        if (ubPeruService.findOne(1260) == null)
            ubPeruService.save(new UbPeru("120803", "Junín", "Yauli", "Huay-Huay"));
        if (ubPeruService.findOne(1261) == null)
            ubPeruService.save(new UbPeru("120804", "Junín", "Yauli", "Marcapomacocha"));
        if (ubPeruService.findOne(1262) == null)
            ubPeruService.save(new UbPeru("120805", "Junín", "Yauli", "Morococha"));
        if (ubPeruService.findOne(1263) == null) ubPeruService.save(new UbPeru("120806", "Junín", "Yauli", "Paccha"));
        if (ubPeruService.findOne(1264) == null)
            ubPeruService.save(new UbPeru("120807", "Junín", "Yauli", "Santa Bárbara de Carhuacayan"));
        if (ubPeruService.findOne(1265) == null)
            ubPeruService.save(new UbPeru("120808", "Junín", "Yauli", "Santa Rosa de Sacco"));
        if (ubPeruService.findOne(1266) == null)
            ubPeruService.save(new UbPeru("120809", "Junín", "Yauli", "Suitucancha"));
        if (ubPeruService.findOne(1267) == null) ubPeruService.save(new UbPeru("120810", "Junín", "Yauli", "Yauli"));
        if (ubPeruService.findOne(1268) == null) ubPeruService.save(new UbPeru("120900", "Junín", "Chupaca", ""));
        if (ubPeruService.findOne(1269) == null)
            ubPeruService.save(new UbPeru("120901", "Junín", "Chupaca", "Chupaca"));
        if (ubPeruService.findOne(1270) == null) ubPeruService.save(new UbPeru("120902", "Junín", "Chupaca", "Ahuac"));
        if (ubPeruService.findOne(1271) == null)
            ubPeruService.save(new UbPeru("120903", "Junín", "Chupaca", "Chongos Bajo"));
        if (ubPeruService.findOne(1272) == null)
            ubPeruService.save(new UbPeru("120904", "Junín", "Chupaca", "Huachac"));
        if (ubPeruService.findOne(1273) == null)
            ubPeruService.save(new UbPeru("120905", "Junín", "Chupaca", "Huamancaca Chico"));
        if (ubPeruService.findOne(1274) == null)
            ubPeruService.save(new UbPeru("120906", "Junín", "Chupaca", "San Juan de Iscos"));
        if (ubPeruService.findOne(1275) == null)
            ubPeruService.save(new UbPeru("120907", "Junín", "Chupaca", "San Juan de Jarpa"));
        if (ubPeruService.findOne(1276) == null)
            ubPeruService.save(new UbPeru("120908", "Junín", "Chupaca", "Tres de Diciembre"));
        if (ubPeruService.findOne(1277) == null)
            ubPeruService.save(new UbPeru("120909", "Junín", "Chupaca", "Yanacancha"));
        if (ubPeruService.findOne(1278) == null) ubPeruService.save(new UbPeru("130000", "La Libertad", "", ""));
        if (ubPeruService.findOne(1279) == null)
            ubPeruService.save(new UbPeru("130100", "La Libertad", "Trujillo", ""));
        if (ubPeruService.findOne(1280) == null)
            ubPeruService.save(new UbPeru("130101", "La Libertad", "Trujillo", "Trujillo"));
        if (ubPeruService.findOne(1281) == null)
            ubPeruService.save(new UbPeru("130102", "La Libertad", "Trujillo", "El Porvenir"));
        if (ubPeruService.findOne(1282) == null)
            ubPeruService.save(new UbPeru("130103", "La Libertad", "Trujillo", "Florencia de Mora"));
        if (ubPeruService.findOne(1283) == null)
            ubPeruService.save(new UbPeru("130104", "La Libertad", "Trujillo", "Huanchaco"));
        if (ubPeruService.findOne(1284) == null)
            ubPeruService.save(new UbPeru("130105", "La Libertad", "Trujillo", "La Esperanza"));
        if (ubPeruService.findOne(1285) == null)
            ubPeruService.save(new UbPeru("130106", "La Libertad", "Trujillo", "Laredo"));
        if (ubPeruService.findOne(1286) == null)
            ubPeruService.save(new UbPeru("130107", "La Libertad", "Trujillo", "Moche"));
        if (ubPeruService.findOne(1287) == null)
            ubPeruService.save(new UbPeru("130108", "La Libertad", "Trujillo", "Poroto"));
        if (ubPeruService.findOne(1288) == null)
            ubPeruService.save(new UbPeru("130109", "La Libertad", "Trujillo", "Salaverry"));
        if (ubPeruService.findOne(1289) == null)
            ubPeruService.save(new UbPeru("130110", "La Libertad", "Trujillo", "Simbal"));
        if (ubPeruService.findOne(1290) == null)
            ubPeruService.save(new UbPeru("130111", "La Libertad", "Trujillo", "Victor Larco Herrera"));
        if (ubPeruService.findOne(1291) == null) ubPeruService.save(new UbPeru("130200", "La Libertad", "Ascope", ""));
        if (ubPeruService.findOne(1292) == null)
            ubPeruService.save(new UbPeru("130201", "La Libertad", "Ascope", "Ascope"));
        if (ubPeruService.findOne(1293) == null)
            ubPeruService.save(new UbPeru("130202", "La Libertad", "Ascope", "Chicama"));
        if (ubPeruService.findOne(1294) == null)
            ubPeruService.save(new UbPeru("130203", "La Libertad", "Ascope", "Chocope"));
        if (ubPeruService.findOne(1295) == null)
            ubPeruService.save(new UbPeru("130204", "La Libertad", "Ascope", "Magdalena de Cao"));
        if (ubPeruService.findOne(1296) == null)
            ubPeruService.save(new UbPeru("130205", "La Libertad", "Ascope", "Paijan"));
        if (ubPeruService.findOne(1297) == null)
            ubPeruService.save(new UbPeru("130206", "La Libertad", "Ascope", "Rázuri"));
        if (ubPeruService.findOne(1298) == null)
            ubPeruService.save(new UbPeru("130207", "La Libertad", "Ascope", "Santiago de Cao"));
        if (ubPeruService.findOne(1299) == null)
            ubPeruService.save(new UbPeru("130208", "La Libertad", "Ascope", "Casa Grande"));
        if (ubPeruService.findOne(1300) == null) ubPeruService.save(new UbPeru("130300", "La Libertad", "Bolívar", ""));
        if (ubPeruService.findOne(1301) == null)
            ubPeruService.save(new UbPeru("130301", "La Libertad", "Bolívar", "Bolívar"));
        if (ubPeruService.findOne(1302) == null)
            ubPeruService.save(new UbPeru("130302", "La Libertad", "Bolívar", "Bambamarca"));
        if (ubPeruService.findOne(1303) == null)
            ubPeruService.save(new UbPeru("130303", "La Libertad", "Bolívar", "Condormarca"));
        if (ubPeruService.findOne(1304) == null)
            ubPeruService.save(new UbPeru("130304", "La Libertad", "Bolívar", "Longotea"));
        if (ubPeruService.findOne(1305) == null)
            ubPeruService.save(new UbPeru("130305", "La Libertad", "Bolívar", "Uchumarca"));
        if (ubPeruService.findOne(1306) == null)
            ubPeruService.save(new UbPeru("130306", "La Libertad", "Bolívar", "Ucuncha"));
        if (ubPeruService.findOne(1307) == null) ubPeruService.save(new UbPeru("130400", "La Libertad", "Chepén", ""));
        if (ubPeruService.findOne(1308) == null)
            ubPeruService.save(new UbPeru("130401", "La Libertad", "Chepén", "Chepen"));
        if (ubPeruService.findOne(1309) == null)
            ubPeruService.save(new UbPeru("130402", "La Libertad", "Chepén", "Pacanga"));
        if (ubPeruService.findOne(1310) == null)
            ubPeruService.save(new UbPeru("130403", "La Libertad", "Chepén", "Pueblo Nuevo"));
        if (ubPeruService.findOne(1311) == null) ubPeruService.save(new UbPeru("130500", "La Libertad", "Julcán", ""));
        if (ubPeruService.findOne(1312) == null)
            ubPeruService.save(new UbPeru("130501", "La Libertad", "Julcán", "Julcan"));
        if (ubPeruService.findOne(1313) == null)
            ubPeruService.save(new UbPeru("130502", "La Libertad", "Julcán", "Calamarca"));
        if (ubPeruService.findOne(1314) == null)
            ubPeruService.save(new UbPeru("130503", "La Libertad", "Julcán", "Carabamba"));
        if (ubPeruService.findOne(1315) == null)
            ubPeruService.save(new UbPeru("130504", "La Libertad", "Julcán", "Huaso"));
        if (ubPeruService.findOne(1316) == null) ubPeruService.save(new UbPeru("130600", "La Libertad", "Otuzco", ""));
        if (ubPeruService.findOne(1317) == null)
            ubPeruService.save(new UbPeru("130601", "La Libertad", "Otuzco", "Otuzco"));
        if (ubPeruService.findOne(1318) == null)
            ubPeruService.save(new UbPeru("130602", "La Libertad", "Otuzco", "Agallpampa"));
        if (ubPeruService.findOne(1319) == null)
            ubPeruService.save(new UbPeru("130604", "La Libertad", "Otuzco", "Charat"));
        if (ubPeruService.findOne(1320) == null)
            ubPeruService.save(new UbPeru("130605", "La Libertad", "Otuzco", "Huaranchal"));
        if (ubPeruService.findOne(1321) == null)
            ubPeruService.save(new UbPeru("130606", "La Libertad", "Otuzco", "La Cuesta"));
        if (ubPeruService.findOne(1322) == null)
            ubPeruService.save(new UbPeru("130608", "La Libertad", "Otuzco", "Mache"));
        if (ubPeruService.findOne(1323) == null)
            ubPeruService.save(new UbPeru("130610", "La Libertad", "Otuzco", "Paranday"));
        if (ubPeruService.findOne(1324) == null)
            ubPeruService.save(new UbPeru("130611", "La Libertad", "Otuzco", "Salpo"));
        if (ubPeruService.findOne(1325) == null)
            ubPeruService.save(new UbPeru("130613", "La Libertad", "Otuzco", "Sinsicap"));
        if (ubPeruService.findOne(1326) == null)
            ubPeruService.save(new UbPeru("130614", "La Libertad", "Otuzco", "Usquil"));
        if (ubPeruService.findOne(1327) == null)
            ubPeruService.save(new UbPeru("130700", "La Libertad", "Pacasmayo", ""));
        if (ubPeruService.findOne(1328) == null)
            ubPeruService.save(new UbPeru("130701", "La Libertad", "Pacasmayo", "San Pedro de Lloc"));
        if (ubPeruService.findOne(1329) == null)
            ubPeruService.save(new UbPeru("130702", "La Libertad", "Pacasmayo", "Guadalupe"));
        if (ubPeruService.findOne(1330) == null)
            ubPeruService.save(new UbPeru("130703", "La Libertad", "Pacasmayo", "Jequetepeque"));
        if (ubPeruService.findOne(1331) == null)
            ubPeruService.save(new UbPeru("130704", "La Libertad", "Pacasmayo", "Pacasmayo"));
        if (ubPeruService.findOne(1332) == null)
            ubPeruService.save(new UbPeru("130705", "La Libertad", "Pacasmayo", "San José"));
        if (ubPeruService.findOne(1333) == null) ubPeruService.save(new UbPeru("130800", "La Libertad", "Pataz", ""));
        if (ubPeruService.findOne(1334) == null)
            ubPeruService.save(new UbPeru("130801", "La Libertad", "Pataz", "Tayabamba"));
        if (ubPeruService.findOne(1335) == null)
            ubPeruService.save(new UbPeru("130802", "La Libertad", "Pataz", "Buldibuyo"));
        if (ubPeruService.findOne(1336) == null)
            ubPeruService.save(new UbPeru("130803", "La Libertad", "Pataz", "Chillia"));
        if (ubPeruService.findOne(1337) == null)
            ubPeruService.save(new UbPeru("130804", "La Libertad", "Pataz", "Huancaspata"));
        if (ubPeruService.findOne(1338) == null)
            ubPeruService.save(new UbPeru("130805", "La Libertad", "Pataz", "Huaylillas"));
        if (ubPeruService.findOne(1339) == null)
            ubPeruService.save(new UbPeru("130806", "La Libertad", "Pataz", "Huayo"));
        if (ubPeruService.findOne(1340) == null)
            ubPeruService.save(new UbPeru("130807", "La Libertad", "Pataz", "Ongon"));
        if (ubPeruService.findOne(1341) == null)
            ubPeruService.save(new UbPeru("130808", "La Libertad", "Pataz", "Parcoy"));
        if (ubPeruService.findOne(1342) == null)
            ubPeruService.save(new UbPeru("130809", "La Libertad", "Pataz", "Pataz"));
        if (ubPeruService.findOne(1343) == null)
            ubPeruService.save(new UbPeru("130810", "La Libertad", "Pataz", "Pias"));
        if (ubPeruService.findOne(1344) == null)
            ubPeruService.save(new UbPeru("130811", "La Libertad", "Pataz", "Santiago de Challas"));
        if (ubPeruService.findOne(1345) == null)
            ubPeruService.save(new UbPeru("130812", "La Libertad", "Pataz", "Taurija"));
        if (ubPeruService.findOne(1346) == null)
            ubPeruService.save(new UbPeru("130813", "La Libertad", "Pataz", "Urpay"));
        if (ubPeruService.findOne(1347) == null)
            ubPeruService.save(new UbPeru("130900", "La Libertad", "Sánchez Carrión", ""));
        if (ubPeruService.findOne(1348) == null)
            ubPeruService.save(new UbPeru("130901", "La Libertad", "Sánchez Carrión", "Huamachuco"));
        if (ubPeruService.findOne(1349) == null)
            ubPeruService.save(new UbPeru("130902", "La Libertad", "Sánchez Carrión", "Chugay"));
        if (ubPeruService.findOne(1350) == null)
            ubPeruService.save(new UbPeru("130903", "La Libertad", "Sánchez Carrión", "Cochorco"));
        if (ubPeruService.findOne(1351) == null)
            ubPeruService.save(new UbPeru("130904", "La Libertad", "Sánchez Carrión", "Curgos"));
        if (ubPeruService.findOne(1352) == null)
            ubPeruService.save(new UbPeru("130905", "La Libertad", "Sánchez Carrión", "Marcabal"));
        if (ubPeruService.findOne(1353) == null)
            ubPeruService.save(new UbPeru("130906", "La Libertad", "Sánchez Carrión", "Sanagoran"));
        if (ubPeruService.findOne(1354) == null)
            ubPeruService.save(new UbPeru("130907", "La Libertad", "Sánchez Carrión", "Sarin"));
        if (ubPeruService.findOne(1355) == null)
            ubPeruService.save(new UbPeru("130908", "La Libertad", "Sánchez Carrión", "Sartimbamba"));
        if (ubPeruService.findOne(1356) == null)
            ubPeruService.save(new UbPeru("131000", "La Libertad", "Santiago de Chuco", ""));
        if (ubPeruService.findOne(1357) == null)
            ubPeruService.save(new UbPeru("131001", "La Libertad", "Santiago de Chuco", "Santiago de Chuco"));
        if (ubPeruService.findOne(1358) == null)
            ubPeruService.save(new UbPeru("131002", "La Libertad", "Santiago de Chuco", "Angasmarca"));
        if (ubPeruService.findOne(1359) == null)
            ubPeruService.save(new UbPeru("131003", "La Libertad", "Santiago de Chuco", "Cachicadan"));
        if (ubPeruService.findOne(1360) == null)
            ubPeruService.save(new UbPeru("131004", "La Libertad", "Santiago de Chuco", "Mollebamba"));
        if (ubPeruService.findOne(1361) == null)
            ubPeruService.save(new UbPeru("131005", "La Libertad", "Santiago de Chuco", "Mollepata"));
        if (ubPeruService.findOne(1362) == null)
            ubPeruService.save(new UbPeru("131006", "La Libertad", "Santiago de Chuco", "Quiruvilca"));
        if (ubPeruService.findOne(1363) == null)
            ubPeruService.save(new UbPeru("131007", "La Libertad", "Santiago de Chuco", "Santa Cruz de Chuca"));
        if (ubPeruService.findOne(1364) == null)
            ubPeruService.save(new UbPeru("131008", "La Libertad", "Santiago de Chuco", "Sitabamba"));
        if (ubPeruService.findOne(1365) == null)
            ubPeruService.save(new UbPeru("131100", "La Libertad", "Gran Chimú", ""));
        if (ubPeruService.findOne(1366) == null)
            ubPeruService.save(new UbPeru("131101", "La Libertad", "Gran Chimú", "Cascas"));
        if (ubPeruService.findOne(1367) == null)
            ubPeruService.save(new UbPeru("131102", "La Libertad", "Gran Chimú", "Lucma"));
        if (ubPeruService.findOne(1368) == null)
            ubPeruService.save(new UbPeru("131103", "La Libertad", "Gran Chimú", "Marmot"));
        if (ubPeruService.findOne(1369) == null)
            ubPeruService.save(new UbPeru("131104", "La Libertad", "Gran Chimú", "Sayapullo"));
        if (ubPeruService.findOne(1370) == null) ubPeruService.save(new UbPeru("131200", "La Libertad", "Virú", ""));
        if (ubPeruService.findOne(1371) == null)
            ubPeruService.save(new UbPeru("131201", "La Libertad", "Virú", "Viru"));
        if (ubPeruService.findOne(1372) == null)
            ubPeruService.save(new UbPeru("131202", "La Libertad", "Virú", "Chao"));
        if (ubPeruService.findOne(1373) == null)
            ubPeruService.save(new UbPeru("131203", "La Libertad", "Virú", "Guadalupito"));
        if (ubPeruService.findOne(1374) == null) ubPeruService.save(new UbPeru("140000", "Lambayeque", "", ""));
        if (ubPeruService.findOne(1375) == null) ubPeruService.save(new UbPeru("140100", "Lambayeque", "Chiclayo", ""));
        if (ubPeruService.findOne(1376) == null)
            ubPeruService.save(new UbPeru("140101", "Lambayeque", "Chiclayo", "Chiclayo"));
        if (ubPeruService.findOne(1377) == null)
            ubPeruService.save(new UbPeru("140102", "Lambayeque", "Chiclayo", "Chongoyape"));
        if (ubPeruService.findOne(1378) == null)
            ubPeruService.save(new UbPeru("140103", "Lambayeque", "Chiclayo", "Eten"));
        if (ubPeruService.findOne(1379) == null)
            ubPeruService.save(new UbPeru("140104", "Lambayeque", "Chiclayo", "Eten Puerto"));
        if (ubPeruService.findOne(1380) == null)
            ubPeruService.save(new UbPeru("140105", "Lambayeque", "Chiclayo", "José Leonardo Ortiz"));
        if (ubPeruService.findOne(1381) == null)
            ubPeruService.save(new UbPeru("140106", "Lambayeque", "Chiclayo", "La Victoria"));
        if (ubPeruService.findOne(1382) == null)
            ubPeruService.save(new UbPeru("140107", "Lambayeque", "Chiclayo", "Lagunas"));
        if (ubPeruService.findOne(1383) == null)
            ubPeruService.save(new UbPeru("140108", "Lambayeque", "Chiclayo", "Monsefu"));
        if (ubPeruService.findOne(1384) == null)
            ubPeruService.save(new UbPeru("140109", "Lambayeque", "Chiclayo", "Nueva Arica"));
        if (ubPeruService.findOne(1385) == null)
            ubPeruService.save(new UbPeru("140110", "Lambayeque", "Chiclayo", "Oyotun"));
        if (ubPeruService.findOne(1386) == null)
            ubPeruService.save(new UbPeru("140111", "Lambayeque", "Chiclayo", "Picsi"));
        if (ubPeruService.findOne(1387) == null)
            ubPeruService.save(new UbPeru("140112", "Lambayeque", "Chiclayo", "Pimentel"));
        if (ubPeruService.findOne(1388) == null)
            ubPeruService.save(new UbPeru("140113", "Lambayeque", "Chiclayo", "Reque"));
        if (ubPeruService.findOne(1389) == null)
            ubPeruService.save(new UbPeru("140114", "Lambayeque", "Chiclayo", "Santa Rosa"));
        if (ubPeruService.findOne(1390) == null)
            ubPeruService.save(new UbPeru("140115", "Lambayeque", "Chiclayo", "Saña"));
        if (ubPeruService.findOne(1391) == null)
            ubPeruService.save(new UbPeru("140116", "Lambayeque", "Chiclayo", "Cayalti"));
        if (ubPeruService.findOne(1392) == null)
            ubPeruService.save(new UbPeru("140117", "Lambayeque", "Chiclayo", "Patapo"));
        if (ubPeruService.findOne(1393) == null)
            ubPeruService.save(new UbPeru("140118", "Lambayeque", "Chiclayo", "Pomalca"));
        if (ubPeruService.findOne(1394) == null)
            ubPeruService.save(new UbPeru("140119", "Lambayeque", "Chiclayo", "Pucala"));
        if (ubPeruService.findOne(1395) == null)
            ubPeruService.save(new UbPeru("140120", "Lambayeque", "Chiclayo", "Tuman"));
        if (ubPeruService.findOne(1396) == null)
            ubPeruService.save(new UbPeru("140200", "Lambayeque", "Ferreñafe", ""));
        if (ubPeruService.findOne(1397) == null)
            ubPeruService.save(new UbPeru("140201", "Lambayeque", "Ferreñafe", "Ferreñafe"));
        if (ubPeruService.findOne(1398) == null)
            ubPeruService.save(new UbPeru("140202", "Lambayeque", "Ferreñafe", "Cañaris"));
        if (ubPeruService.findOne(1399) == null)
            ubPeruService.save(new UbPeru("140203", "Lambayeque", "Ferreñafe", "Incahuasi"));
        if (ubPeruService.findOne(1400) == null)
            ubPeruService.save(new UbPeru("140204", "Lambayeque", "Ferreñafe", "Manuel Antonio Mesones Muro"));
        if (ubPeruService.findOne(1401) == null)
            ubPeruService.save(new UbPeru("140205", "Lambayeque", "Ferreñafe", "Pitipo"));
        if (ubPeruService.findOne(1402) == null)
            ubPeruService.save(new UbPeru("140206", "Lambayeque", "Ferreñafe", "Pueblo Nuevo"));
        if (ubPeruService.findOne(1403) == null)
            ubPeruService.save(new UbPeru("140300", "Lambayeque", "Lambayeque", ""));
        if (ubPeruService.findOne(1404) == null)
            ubPeruService.save(new UbPeru("140301", "Lambayeque", "Lambayeque", "Lambayeque"));
        if (ubPeruService.findOne(1405) == null)
            ubPeruService.save(new UbPeru("140302", "Lambayeque", "Lambayeque", "Chochope"));
        if (ubPeruService.findOne(1406) == null)
            ubPeruService.save(new UbPeru("140303", "Lambayeque", "Lambayeque", "Illimo"));
        if (ubPeruService.findOne(1407) == null)
            ubPeruService.save(new UbPeru("140304", "Lambayeque", "Lambayeque", "Jayanca"));
        if (ubPeruService.findOne(1408) == null)
            ubPeruService.save(new UbPeru("140305", "Lambayeque", "Lambayeque", "Mochumi"));
        if (ubPeruService.findOne(1409) == null)
            ubPeruService.save(new UbPeru("140306", "Lambayeque", "Lambayeque", "Morrope"));
        if (ubPeruService.findOne(1410) == null)
            ubPeruService.save(new UbPeru("140307", "Lambayeque", "Lambayeque", "Motupe"));
        if (ubPeruService.findOne(1411) == null)
            ubPeruService.save(new UbPeru("140308", "Lambayeque", "Lambayeque", "Olmos"));
        if (ubPeruService.findOne(1412) == null)
            ubPeruService.save(new UbPeru("140309", "Lambayeque", "Lambayeque", "Pacora"));
        if (ubPeruService.findOne(1413) == null)
            ubPeruService.save(new UbPeru("140310", "Lambayeque", "Lambayeque", "Salas"));
        if (ubPeruService.findOne(1414) == null)
            ubPeruService.save(new UbPeru("140311", "Lambayeque", "Lambayeque", "San José"));
        if (ubPeruService.findOne(1415) == null)
            ubPeruService.save(new UbPeru("140312", "Lambayeque", "Lambayeque", "Tucume"));
        if (ubPeruService.findOne(1416) == null) ubPeruService.save(new UbPeru("150000", "Lima", "", ""));
        if (ubPeruService.findOne(1417) == null) ubPeruService.save(new UbPeru("150100", "Lima", "Lima", ""));
        if (ubPeruService.findOne(1418) == null) ubPeruService.save(new UbPeru("150101", "Lima", "Lima", "Lima"));
        if (ubPeruService.findOne(1419) == null) ubPeruService.save(new UbPeru("150102", "Lima", "Lima", "Ancón"));
        if (ubPeruService.findOne(1420) == null) ubPeruService.save(new UbPeru("150103", "Lima", "Lima", "Ate"));
        if (ubPeruService.findOne(1421) == null) ubPeruService.save(new UbPeru("150104", "Lima", "Lima", "Barranco"));
        if (ubPeruService.findOne(1422) == null) ubPeruService.save(new UbPeru("150105", "Lima", "Lima", "Breña"));
        if (ubPeruService.findOne(1423) == null) ubPeruService.save(new UbPeru("150106", "Lima", "Lima", "Carabayllo"));
        if (ubPeruService.findOne(1424) == null) ubPeruService.save(new UbPeru("150107", "Lima", "Lima", "Chaclacayo"));
        if (ubPeruService.findOne(1425) == null) ubPeruService.save(new UbPeru("150108", "Lima", "Lima", "Chorrillos"));
        if (ubPeruService.findOne(1426) == null)
            ubPeruService.save(new UbPeru("150109", "Lima", "Lima", "Cieneguilla"));
        if (ubPeruService.findOne(1427) == null) ubPeruService.save(new UbPeru("150110", "Lima", "Lima", "Comas"));
        if (ubPeruService.findOne(1428) == null)
            ubPeruService.save(new UbPeru("150111", "Lima", "Lima", "El Agustino"));
        if (ubPeruService.findOne(1429) == null)
            ubPeruService.save(new UbPeru("150112", "Lima", "Lima", "Independencia"));
        if (ubPeruService.findOne(1430) == null)
            ubPeruService.save(new UbPeru("150113", "Lima", "Lima", "Jesús María"));
        if (ubPeruService.findOne(1431) == null) ubPeruService.save(new UbPeru("150114", "Lima", "Lima", "La Molina"));
        if (ubPeruService.findOne(1432) == null)
            ubPeruService.save(new UbPeru("150115", "Lima", "Lima", "La Victoria"));
        if (ubPeruService.findOne(1433) == null) ubPeruService.save(new UbPeru("150116", "Lima", "Lima", "Lince"));
        if (ubPeruService.findOne(1434) == null) ubPeruService.save(new UbPeru("150117", "Lima", "Lima", "Los Olivos"));
        if (ubPeruService.findOne(1435) == null) ubPeruService.save(new UbPeru("150118", "Lima", "Lima", "Lurigancho"));
        if (ubPeruService.findOne(1436) == null) ubPeruService.save(new UbPeru("150119", "Lima", "Lima", "Lurin"));
        if (ubPeruService.findOne(1437) == null)
            ubPeruService.save(new UbPeru("150120", "Lima", "Lima", "Magdalena del Mar"));
        if (ubPeruService.findOne(1438) == null)
            ubPeruService.save(new UbPeru("150121", "Lima", "Lima", "Pueblo Libre"));
        if (ubPeruService.findOne(1439) == null) ubPeruService.save(new UbPeru("150122", "Lima", "Lima", "Miraflores"));
        if (ubPeruService.findOne(1440) == null) ubPeruService.save(new UbPeru("150123", "Lima", "Lima", "Pachacamac"));
        if (ubPeruService.findOne(1441) == null) ubPeruService.save(new UbPeru("150124", "Lima", "Lima", "Pucusana"));
        if (ubPeruService.findOne(1442) == null)
            ubPeruService.save(new UbPeru("150125", "Lima", "Lima", "Puente Piedra"));
        if (ubPeruService.findOne(1443) == null)
            ubPeruService.save(new UbPeru("150126", "Lima", "Lima", "Punta Hermosa"));
        if (ubPeruService.findOne(1444) == null)
            ubPeruService.save(new UbPeru("150127", "Lima", "Lima", "Punta Negra"));
        if (ubPeruService.findOne(1445) == null) ubPeruService.save(new UbPeru("150128", "Lima", "Lima", "Rímac"));
        if (ubPeruService.findOne(1446) == null)
            ubPeruService.save(new UbPeru("150129", "Lima", "Lima", "San Bartolo"));
        if (ubPeruService.findOne(1447) == null) ubPeruService.save(new UbPeru("150130", "Lima", "Lima", "San Borja"));
        if (ubPeruService.findOne(1448) == null) ubPeruService.save(new UbPeru("150131", "Lima", "Lima", "San Isidro"));
        if (ubPeruService.findOne(1449) == null)
            ubPeruService.save(new UbPeru("150132", "Lima", "Lima", "San Juan de Lurigancho"));
        if (ubPeruService.findOne(1450) == null)
            ubPeruService.save(new UbPeru("150133", "Lima", "Lima", "San Juan de Miraflores"));
        if (ubPeruService.findOne(1451) == null) ubPeruService.save(new UbPeru("150134", "Lima", "Lima", "San Luis"));
        if (ubPeruService.findOne(1452) == null)
            ubPeruService.save(new UbPeru("150135", "Lima", "Lima", "San Martín de Porres"));
        if (ubPeruService.findOne(1453) == null) ubPeruService.save(new UbPeru("150136", "Lima", "Lima", "San Miguel"));
        if (ubPeruService.findOne(1454) == null)
            ubPeruService.save(new UbPeru("150137", "Lima", "Lima", "Santa Anita"));
        if (ubPeruService.findOne(1455) == null)
            ubPeruService.save(new UbPeru("150138", "Lima", "Lima", "Santa María del Mar"));
        if (ubPeruService.findOne(1456) == null) ubPeruService.save(new UbPeru("150139", "Lima", "Lima", "Santa Rosa"));
        if (ubPeruService.findOne(1457) == null)
            ubPeruService.save(new UbPeru("150140", "Lima", "Lima", "Santiago de Surco"));
        if (ubPeruService.findOne(1458) == null) ubPeruService.save(new UbPeru("150141", "Lima", "Lima", "Surquillo"));
        if (ubPeruService.findOne(1459) == null)
            ubPeruService.save(new UbPeru("150142", "Lima", "Lima", "Villa El Salvador"));
        if (ubPeruService.findOne(1460) == null)
            ubPeruService.save(new UbPeru("150143", "Lima", "Lima", "Villa María del Triunfo"));
        if (ubPeruService.findOne(1461) == null) ubPeruService.save(new UbPeru("150200", "Lima", "Barranca", ""));
        if (ubPeruService.findOne(1462) == null)
            ubPeruService.save(new UbPeru("150201", "Lima", "Barranca", "Barranca"));
        if (ubPeruService.findOne(1463) == null)
            ubPeruService.save(new UbPeru("150202", "Lima", "Barranca", "Paramonga"));
        if (ubPeruService.findOne(1464) == null)
            ubPeruService.save(new UbPeru("150203", "Lima", "Barranca", "Pativilca"));
        if (ubPeruService.findOne(1465) == null) ubPeruService.save(new UbPeru("150204", "Lima", "Barranca", "Supe"));
        if (ubPeruService.findOne(1466) == null)
            ubPeruService.save(new UbPeru("150205", "Lima", "Barranca", "Supe Puerto"));
        if (ubPeruService.findOne(1467) == null) ubPeruService.save(new UbPeru("150300", "Lima", "Cajatambo", ""));
        if (ubPeruService.findOne(1468) == null)
            ubPeruService.save(new UbPeru("150301", "Lima", "Cajatambo", "Cajatambo"));
        if (ubPeruService.findOne(1469) == null) ubPeruService.save(new UbPeru("150302", "Lima", "Cajatambo", "Copa"));
        if (ubPeruService.findOne(1470) == null)
            ubPeruService.save(new UbPeru("150303", "Lima", "Cajatambo", "Gorgor"));
        if (ubPeruService.findOne(1471) == null)
            ubPeruService.save(new UbPeru("150304", "Lima", "Cajatambo", "Huancapon"));
        if (ubPeruService.findOne(1472) == null) ubPeruService.save(new UbPeru("150305", "Lima", "Cajatambo", "Manas"));
        if (ubPeruService.findOne(1473) == null) ubPeruService.save(new UbPeru("150400", "Lima", "Canta", ""));
        if (ubPeruService.findOne(1474) == null) ubPeruService.save(new UbPeru("150401", "Lima", "Canta", "Canta"));
        if (ubPeruService.findOne(1475) == null) ubPeruService.save(new UbPeru("150402", "Lima", "Canta", "Arahuay"));
        if (ubPeruService.findOne(1476) == null)
            ubPeruService.save(new UbPeru("150403", "Lima", "Canta", "Huamantanga"));
        if (ubPeruService.findOne(1477) == null) ubPeruService.save(new UbPeru("150404", "Lima", "Canta", "Huaros"));
        if (ubPeruService.findOne(1478) == null) ubPeruService.save(new UbPeru("150405", "Lima", "Canta", "Lachaqui"));
        if (ubPeruService.findOne(1479) == null)
            ubPeruService.save(new UbPeru("150406", "Lima", "Canta", "San Buenaventura"));
        if (ubPeruService.findOne(1480) == null)
            ubPeruService.save(new UbPeru("150407", "Lima", "Canta", "Santa Rosa de Quives"));
        if (ubPeruService.findOne(1481) == null) ubPeruService.save(new UbPeru("150500", "Lima", "Cañete", ""));
        if (ubPeruService.findOne(1482) == null)
            ubPeruService.save(new UbPeru("150501", "Lima", "Cañete", "San Vicente de Cañete"));
        if (ubPeruService.findOne(1483) == null) ubPeruService.save(new UbPeru("150502", "Lima", "Cañete", "Asia"));
        if (ubPeruService.findOne(1484) == null) ubPeruService.save(new UbPeru("150503", "Lima", "Cañete", "Calango"));
        if (ubPeruService.findOne(1485) == null)
            ubPeruService.save(new UbPeru("150504", "Lima", "Cañete", "Cerro Azul"));
        if (ubPeruService.findOne(1486) == null) ubPeruService.save(new UbPeru("150505", "Lima", "Cañete", "Chilca"));
        if (ubPeruService.findOne(1487) == null) ubPeruService.save(new UbPeru("150506", "Lima", "Cañete", "Coayllo"));
        if (ubPeruService.findOne(1488) == null) ubPeruService.save(new UbPeru("150507", "Lima", "Cañete", "Imperial"));
        if (ubPeruService.findOne(1489) == null)
            ubPeruService.save(new UbPeru("150508", "Lima", "Cañete", "Lunahuana"));
        if (ubPeruService.findOne(1490) == null) ubPeruService.save(new UbPeru("150509", "Lima", "Cañete", "Mala"));
        if (ubPeruService.findOne(1491) == null)
            ubPeruService.save(new UbPeru("150510", "Lima", "Cañete", "Nuevo Imperial"));
        if (ubPeruService.findOne(1492) == null) ubPeruService.save(new UbPeru("150511", "Lima", "Cañete", "Pacaran"));
        if (ubPeruService.findOne(1493) == null) ubPeruService.save(new UbPeru("150512", "Lima", "Cañete", "Quilmana"));
        if (ubPeruService.findOne(1494) == null)
            ubPeruService.save(new UbPeru("150513", "Lima", "Cañete", "San Antonio"));
        if (ubPeruService.findOne(1495) == null) ubPeruService.save(new UbPeru("150514", "Lima", "Cañete", "San Luis"));
        if (ubPeruService.findOne(1496) == null)
            ubPeruService.save(new UbPeru("150515", "Lima", "Cañete", "Santa Cruz de Flores"));
        if (ubPeruService.findOne(1497) == null) ubPeruService.save(new UbPeru("150516", "Lima", "Cañete", "Zúñiga"));
        if (ubPeruService.findOne(1498) == null) ubPeruService.save(new UbPeru("150600", "Lima", "Huaral", ""));
        if (ubPeruService.findOne(1499) == null) ubPeruService.save(new UbPeru("150601", "Lima", "Huaral", "Huaral"));
        if (ubPeruService.findOne(1500) == null)
            ubPeruService.save(new UbPeru("150602", "Lima", "Huaral", "Atavillos Alto"));
        if (ubPeruService.findOne(1501) == null)
            ubPeruService.save(new UbPeru("150603", "Lima", "Huaral", "Atavillos Bajo"));
        if (ubPeruService.findOne(1502) == null)
            ubPeruService.save(new UbPeru("150604", "Lima", "Huaral", "Aucallama"));
        if (ubPeruService.findOne(1503) == null) ubPeruService.save(new UbPeru("150605", "Lima", "Huaral", "Chancay"));
        if (ubPeruService.findOne(1504) == null) ubPeruService.save(new UbPeru("150606", "Lima", "Huaral", "Ihuari"));
        if (ubPeruService.findOne(1505) == null) ubPeruService.save(new UbPeru("150607", "Lima", "Huaral", "Lampian"));
        if (ubPeruService.findOne(1506) == null) ubPeruService.save(new UbPeru("150608", "Lima", "Huaral", "Pacaraos"));
        if (ubPeruService.findOne(1507) == null)
            ubPeruService.save(new UbPeru("150609", "Lima", "Huaral", "San Miguel de Acos"));
        if (ubPeruService.findOne(1508) == null)
            ubPeruService.save(new UbPeru("150610", "Lima", "Huaral", "Santa Cruz de Andamarca"));
        if (ubPeruService.findOne(1509) == null) ubPeruService.save(new UbPeru("150611", "Lima", "Huaral", "Sumbilca"));
        if (ubPeruService.findOne(1510) == null)
            ubPeruService.save(new UbPeru("150612", "Lima", "Huaral", "Veintisiete de Noviembre"));
        if (ubPeruService.findOne(1511) == null) ubPeruService.save(new UbPeru("150700", "Lima", "Huarochirí", ""));
        if (ubPeruService.findOne(1512) == null)
            ubPeruService.save(new UbPeru("150701", "Lima", "Huarochirí", "Matucana"));
        if (ubPeruService.findOne(1513) == null)
            ubPeruService.save(new UbPeru("150702", "Lima", "Huarochirí", "Antioquia"));
        if (ubPeruService.findOne(1514) == null)
            ubPeruService.save(new UbPeru("150703", "Lima", "Huarochirí", "Callahuanca"));
        if (ubPeruService.findOne(1515) == null)
            ubPeruService.save(new UbPeru("150704", "Lima", "Huarochirí", "Carampoma"));
        if (ubPeruService.findOne(1516) == null)
            ubPeruService.save(new UbPeru("150705", "Lima", "Huarochirí", "Chicla"));
        if (ubPeruService.findOne(1517) == null)
            ubPeruService.save(new UbPeru("150706", "Lima", "Huarochirí", "Cuenca"));
        if (ubPeruService.findOne(1518) == null)
            ubPeruService.save(new UbPeru("150707", "Lima", "Huarochirí", "Huachupampa"));
        if (ubPeruService.findOne(1519) == null)
            ubPeruService.save(new UbPeru("150708", "Lima", "Huarochirí", "Huanza"));
        if (ubPeruService.findOne(1520) == null)
            ubPeruService.save(new UbPeru("150709", "Lima", "Huarochirí", "Huarochiri"));
        if (ubPeruService.findOne(1521) == null)
            ubPeruService.save(new UbPeru("150710", "Lima", "Huarochirí", "Lahuaytambo"));
        if (ubPeruService.findOne(1522) == null)
            ubPeruService.save(new UbPeru("150711", "Lima", "Huarochirí", "Langa"));
        if (ubPeruService.findOne(1523) == null)
            ubPeruService.save(new UbPeru("150712", "Lima", "Huarochirí", "Laraos"));
        if (ubPeruService.findOne(1524) == null)
            ubPeruService.save(new UbPeru("150713", "Lima", "Huarochirí", "Mariatana"));
        if (ubPeruService.findOne(1525) == null)
            ubPeruService.save(new UbPeru("150714", "Lima", "Huarochirí", "Ricardo Palma"));
        if (ubPeruService.findOne(1526) == null)
            ubPeruService.save(new UbPeru("150715", "Lima", "Huarochirí", "San Andrés de Tupicocha"));
        if (ubPeruService.findOne(1527) == null)
            ubPeruService.save(new UbPeru("150716", "Lima", "Huarochirí", "San Antonio"));
        if (ubPeruService.findOne(1528) == null)
            ubPeruService.save(new UbPeru("150717", "Lima", "Huarochirí", "San Bartolomé"));
        if (ubPeruService.findOne(1529) == null)
            ubPeruService.save(new UbPeru("150718", "Lima", "Huarochirí", "San Damian"));
        if (ubPeruService.findOne(1530) == null)
            ubPeruService.save(new UbPeru("150719", "Lima", "Huarochirí", "San Juan de Iris"));
        if (ubPeruService.findOne(1531) == null)
            ubPeruService.save(new UbPeru("150720", "Lima", "Huarochirí", "San Juan de Tantaranche"));
        if (ubPeruService.findOne(1532) == null)
            ubPeruService.save(new UbPeru("150721", "Lima", "Huarochirí", "San Lorenzo de Quinti"));
        if (ubPeruService.findOne(1533) == null)
            ubPeruService.save(new UbPeru("150722", "Lima", "Huarochirí", "San Mateo"));
        if (ubPeruService.findOne(1534) == null)
            ubPeruService.save(new UbPeru("150723", "Lima", "Huarochirí", "San Mateo de Otao"));
        if (ubPeruService.findOne(1535) == null)
            ubPeruService.save(new UbPeru("150724", "Lima", "Huarochirí", "San Pedro de Casta"));
        if (ubPeruService.findOne(1536) == null)
            ubPeruService.save(new UbPeru("150725", "Lima", "Huarochirí", "San Pedro de Huancayre"));
        if (ubPeruService.findOne(1537) == null)
            ubPeruService.save(new UbPeru("150726", "Lima", "Huarochirí", "Sangallaya"));
        if (ubPeruService.findOne(1538) == null)
            ubPeruService.save(new UbPeru("150727", "Lima", "Huarochirí", "Santa Cruz de Cocachacra"));
        if (ubPeruService.findOne(1539) == null)
            ubPeruService.save(new UbPeru("150728", "Lima", "Huarochirí", "Santa Eulalia"));
        if (ubPeruService.findOne(1540) == null)
            ubPeruService.save(new UbPeru("150729", "Lima", "Huarochirí", "Santiago de Anchucaya"));
        if (ubPeruService.findOne(1541) == null)
            ubPeruService.save(new UbPeru("150730", "Lima", "Huarochirí", "Santiago de Tuna"));
        if (ubPeruService.findOne(1542) == null)
            ubPeruService.save(new UbPeru("150731", "Lima", "Huarochirí", "Santo Domingo de Los Olleros"));
        if (ubPeruService.findOne(1543) == null)
            ubPeruService.save(new UbPeru("150732", "Lima", "Huarochirí", "Surco"));
        if (ubPeruService.findOne(1544) == null) ubPeruService.save(new UbPeru("150800", "Lima", "Huaura", ""));
        if (ubPeruService.findOne(1545) == null) ubPeruService.save(new UbPeru("150801", "Lima", "Huaura", "Huacho"));
        if (ubPeruService.findOne(1546) == null) ubPeruService.save(new UbPeru("150802", "Lima", "Huaura", "Ambar"));
        if (ubPeruService.findOne(1547) == null)
            ubPeruService.save(new UbPeru("150803", "Lima", "Huaura", "Caleta de Carquin"));
        if (ubPeruService.findOne(1548) == null) ubPeruService.save(new UbPeru("150804", "Lima", "Huaura", "Checras"));
        if (ubPeruService.findOne(1549) == null) ubPeruService.save(new UbPeru("150805", "Lima", "Huaura", "Hualmay"));
        if (ubPeruService.findOne(1550) == null) ubPeruService.save(new UbPeru("150806", "Lima", "Huaura", "Huaura"));
        if (ubPeruService.findOne(1551) == null)
            ubPeruService.save(new UbPeru("150807", "Lima", "Huaura", "Leoncio Prado"));
        if (ubPeruService.findOne(1552) == null) ubPeruService.save(new UbPeru("150808", "Lima", "Huaura", "Paccho"));
        if (ubPeruService.findOne(1553) == null)
            ubPeruService.save(new UbPeru("150809", "Lima", "Huaura", "Santa Leonor"));
        if (ubPeruService.findOne(1554) == null)
            ubPeruService.save(new UbPeru("150810", "Lima", "Huaura", "Santa María"));
        if (ubPeruService.findOne(1555) == null) ubPeruService.save(new UbPeru("150811", "Lima", "Huaura", "Sayan"));
        if (ubPeruService.findOne(1556) == null) ubPeruService.save(new UbPeru("150812", "Lima", "Huaura", "Vegueta"));
        if (ubPeruService.findOne(1557) == null) ubPeruService.save(new UbPeru("150900", "Lima", "Oyón", ""));
        if (ubPeruService.findOne(1558) == null) ubPeruService.save(new UbPeru("150901", "Lima", "Oyón", "Oyon"));
        if (ubPeruService.findOne(1559) == null) ubPeruService.save(new UbPeru("150902", "Lima", "Oyón", "Andajes"));
        if (ubPeruService.findOne(1560) == null) ubPeruService.save(new UbPeru("150903", "Lima", "Oyón", "Caujul"));
        if (ubPeruService.findOne(1561) == null) ubPeruService.save(new UbPeru("150904", "Lima", "Oyón", "Cochamarca"));
        if (ubPeruService.findOne(1562) == null) ubPeruService.save(new UbPeru("150905", "Lima", "Oyón", "Navan"));
        if (ubPeruService.findOne(1563) == null) ubPeruService.save(new UbPeru("150906", "Lima", "Oyón", "Pachangara"));
        if (ubPeruService.findOne(1564) == null) ubPeruService.save(new UbPeru("151000", "Lima", "Yauyos", ""));
        if (ubPeruService.findOne(1565) == null) ubPeruService.save(new UbPeru("151001", "Lima", "Yauyos", "Yauyos"));
        if (ubPeruService.findOne(1566) == null) ubPeruService.save(new UbPeru("151002", "Lima", "Yauyos", "Alis"));
        if (ubPeruService.findOne(1567) == null) ubPeruService.save(new UbPeru("151003", "Lima", "Yauyos", "Allauca"));
        if (ubPeruService.findOne(1568) == null) ubPeruService.save(new UbPeru("151004", "Lima", "Yauyos", "Ayaviri"));
        if (ubPeruService.findOne(1569) == null) ubPeruService.save(new UbPeru("151005", "Lima", "Yauyos", "Azángaro"));
        if (ubPeruService.findOne(1570) == null) ubPeruService.save(new UbPeru("151006", "Lima", "Yauyos", "Cacra"));
        if (ubPeruService.findOne(1571) == null) ubPeruService.save(new UbPeru("151007", "Lima", "Yauyos", "Carania"));
        if (ubPeruService.findOne(1572) == null)
            ubPeruService.save(new UbPeru("151008", "Lima", "Yauyos", "Catahuasi"));
        if (ubPeruService.findOne(1573) == null) ubPeruService.save(new UbPeru("151009", "Lima", "Yauyos", "Chocos"));
        if (ubPeruService.findOne(1574) == null) ubPeruService.save(new UbPeru("151010", "Lima", "Yauyos", "Cochas"));
        if (ubPeruService.findOne(1575) == null) ubPeruService.save(new UbPeru("151011", "Lima", "Yauyos", "Colonia"));
        if (ubPeruService.findOne(1576) == null) ubPeruService.save(new UbPeru("151012", "Lima", "Yauyos", "Hongos"));
        if (ubPeruService.findOne(1577) == null) ubPeruService.save(new UbPeru("151013", "Lima", "Yauyos", "Huampara"));
        if (ubPeruService.findOne(1578) == null) ubPeruService.save(new UbPeru("151014", "Lima", "Yauyos", "Huancaya"));
        if (ubPeruService.findOne(1579) == null)
            ubPeruService.save(new UbPeru("151015", "Lima", "Yauyos", "Huangascar"));
        if (ubPeruService.findOne(1580) == null) ubPeruService.save(new UbPeru("151016", "Lima", "Yauyos", "Huantan"));
        if (ubPeruService.findOne(1581) == null) ubPeruService.save(new UbPeru("151017", "Lima", "Yauyos", "Huañec"));
        if (ubPeruService.findOne(1582) == null) ubPeruService.save(new UbPeru("151018", "Lima", "Yauyos", "Laraos"));
        if (ubPeruService.findOne(1583) == null) ubPeruService.save(new UbPeru("151019", "Lima", "Yauyos", "Lincha"));
        if (ubPeruService.findOne(1584) == null) ubPeruService.save(new UbPeru("151020", "Lima", "Yauyos", "Madean"));
        if (ubPeruService.findOne(1585) == null)
            ubPeruService.save(new UbPeru("151021", "Lima", "Yauyos", "Miraflores"));
        if (ubPeruService.findOne(1586) == null) ubPeruService.save(new UbPeru("151022", "Lima", "Yauyos", "Omas"));
        if (ubPeruService.findOne(1587) == null) ubPeruService.save(new UbPeru("151023", "Lima", "Yauyos", "Putinza"));
        if (ubPeruService.findOne(1588) == null) ubPeruService.save(new UbPeru("151024", "Lima", "Yauyos", "Quinches"));
        if (ubPeruService.findOne(1589) == null) ubPeruService.save(new UbPeru("151025", "Lima", "Yauyos", "Quinocay"));
        if (ubPeruService.findOne(1590) == null)
            ubPeruService.save(new UbPeru("151026", "Lima", "Yauyos", "San Joaquín"));
        if (ubPeruService.findOne(1591) == null)
            ubPeruService.save(new UbPeru("151027", "Lima", "Yauyos", "San Pedro de Pilas"));
        if (ubPeruService.findOne(1592) == null) ubPeruService.save(new UbPeru("151028", "Lima", "Yauyos", "Tanta"));
        if (ubPeruService.findOne(1593) == null)
            ubPeruService.save(new UbPeru("151029", "Lima", "Yauyos", "Tauripampa"));
        if (ubPeruService.findOne(1594) == null) ubPeruService.save(new UbPeru("151030", "Lima", "Yauyos", "Tomas"));
        if (ubPeruService.findOne(1595) == null) ubPeruService.save(new UbPeru("151031", "Lima", "Yauyos", "Tupe"));
        if (ubPeruService.findOne(1596) == null) ubPeruService.save(new UbPeru("151032", "Lima", "Yauyos", "Viñac"));
        if (ubPeruService.findOne(1597) == null) ubPeruService.save(new UbPeru("151033", "Lima", "Yauyos", "Vitis"));
        if (ubPeruService.findOne(1598) == null) ubPeruService.save(new UbPeru("160000", "Loreto", "", ""));
        if (ubPeruService.findOne(1599) == null) ubPeruService.save(new UbPeru("160100", "Loreto", "Maynas", ""));
        if (ubPeruService.findOne(1600) == null)
            ubPeruService.save(new UbPeru("160101", "Loreto", "Maynas", "Iquitos"));
        if (ubPeruService.findOne(1601) == null)
            ubPeruService.save(new UbPeru("160102", "Loreto", "Maynas", "Alto Nanay"));
        if (ubPeruService.findOne(1602) == null)
            ubPeruService.save(new UbPeru("160103", "Loreto", "Maynas", "Fernando Lores"));
        if (ubPeruService.findOne(1603) == null)
            ubPeruService.save(new UbPeru("160104", "Loreto", "Maynas", "Indiana"));
        if (ubPeruService.findOne(1604) == null)
            ubPeruService.save(new UbPeru("160105", "Loreto", "Maynas", "Las Amazonas"));
        if (ubPeruService.findOne(1605) == null) ubPeruService.save(new UbPeru("160106", "Loreto", "Maynas", "Mazan"));
        if (ubPeruService.findOne(1606) == null) ubPeruService.save(new UbPeru("160107", "Loreto", "Maynas", "Napo"));
        if (ubPeruService.findOne(1607) == null)
            ubPeruService.save(new UbPeru("160108", "Loreto", "Maynas", "Punchana"));
        if (ubPeruService.findOne(1608) == null)
            ubPeruService.save(new UbPeru("160110", "Loreto", "Maynas", "Torres Causana"));
        if (ubPeruService.findOne(1609) == null) ubPeruService.save(new UbPeru("160112", "Loreto", "Maynas", "Belén"));
        if (ubPeruService.findOne(1610) == null)
            ubPeruService.save(new UbPeru("160113", "Loreto", "Maynas", "San Juan Bautista"));
        if (ubPeruService.findOne(1611) == null)
            ubPeruService.save(new UbPeru("160200", "Loreto", "Alto Amazonas", ""));
        if (ubPeruService.findOne(1612) == null)
            ubPeruService.save(new UbPeru("160201", "Loreto", "Alto Amazonas", "Yurimaguas"));
        if (ubPeruService.findOne(1613) == null)
            ubPeruService.save(new UbPeru("160202", "Loreto", "Alto Amazonas", "Balsapuerto"));
        if (ubPeruService.findOne(1614) == null)
            ubPeruService.save(new UbPeru("160205", "Loreto", "Alto Amazonas", "Jeberos"));
        if (ubPeruService.findOne(1615) == null)
            ubPeruService.save(new UbPeru("160206", "Loreto", "Alto Amazonas", "Lagunas"));
        if (ubPeruService.findOne(1616) == null)
            ubPeruService.save(new UbPeru("160210", "Loreto", "Alto Amazonas", "Santa Cruz"));
        if (ubPeruService.findOne(1617) == null)
            ubPeruService.save(new UbPeru("160211", "Loreto", "Alto Amazonas", "Teniente Cesar López Rojas"));
        if (ubPeruService.findOne(1618) == null) ubPeruService.save(new UbPeru("160300", "Loreto", "Loreto", ""));
        if (ubPeruService.findOne(1619) == null) ubPeruService.save(new UbPeru("160301", "Loreto", "Loreto", "Nauta"));
        if (ubPeruService.findOne(1620) == null)
            ubPeruService.save(new UbPeru("160302", "Loreto", "Loreto", "Parinari"));
        if (ubPeruService.findOne(1621) == null) ubPeruService.save(new UbPeru("160303", "Loreto", "Loreto", "Tigre"));
        if (ubPeruService.findOne(1622) == null)
            ubPeruService.save(new UbPeru("160304", "Loreto", "Loreto", "Trompeteros"));
        if (ubPeruService.findOne(1623) == null)
            ubPeruService.save(new UbPeru("160305", "Loreto", "Loreto", "Urarinas"));
        if (ubPeruService.findOne(1624) == null)
            ubPeruService.save(new UbPeru("160400", "Loreto", "Mariscal Ramón Castilla", ""));
        if (ubPeruService.findOne(1625) == null)
            ubPeruService.save(new UbPeru("160401", "Loreto", "Mariscal Ramón Castilla", "Ramón Castilla"));
        if (ubPeruService.findOne(1626) == null)
            ubPeruService.save(new UbPeru("160402", "Loreto", "Mariscal Ramón Castilla", "Pebas"));
        if (ubPeruService.findOne(1627) == null)
            ubPeruService.save(new UbPeru("160403", "Loreto", "Mariscal Ramón Castilla", "Yavari"));
        if (ubPeruService.findOne(1628) == null)
            ubPeruService.save(new UbPeru("160404", "Loreto", "Mariscal Ramón Castilla", "San Pablo"));
        if (ubPeruService.findOne(1629) == null) ubPeruService.save(new UbPeru("160500", "Loreto", "Requena", ""));
        if (ubPeruService.findOne(1630) == null)
            ubPeruService.save(new UbPeru("160501", "Loreto", "Requena", "Requena"));
        if (ubPeruService.findOne(1631) == null)
            ubPeruService.save(new UbPeru("160502", "Loreto", "Requena", "Alto Tapiche"));
        if (ubPeruService.findOne(1632) == null)
            ubPeruService.save(new UbPeru("160503", "Loreto", "Requena", "Capelo"));
        if (ubPeruService.findOne(1633) == null)
            ubPeruService.save(new UbPeru("160504", "Loreto", "Requena", "Emilio San Martín"));
        if (ubPeruService.findOne(1634) == null)
            ubPeruService.save(new UbPeru("160505", "Loreto", "Requena", "Maquia"));
        if (ubPeruService.findOne(1635) == null)
            ubPeruService.save(new UbPeru("160506", "Loreto", "Requena", "Puinahua"));
        if (ubPeruService.findOne(1636) == null)
            ubPeruService.save(new UbPeru("160507", "Loreto", "Requena", "Saquena"));
        if (ubPeruService.findOne(1637) == null)
            ubPeruService.save(new UbPeru("160508", "Loreto", "Requena", "Soplin"));
        if (ubPeruService.findOne(1638) == null)
            ubPeruService.save(new UbPeru("160509", "Loreto", "Requena", "Tapiche"));
        if (ubPeruService.findOne(1639) == null)
            ubPeruService.save(new UbPeru("160510", "Loreto", "Requena", "Jenaro Herrera"));
        if (ubPeruService.findOne(1640) == null)
            ubPeruService.save(new UbPeru("160511", "Loreto", "Requena", "Yaquerana"));
        if (ubPeruService.findOne(1641) == null) ubPeruService.save(new UbPeru("160600", "Loreto", "Ucayali", ""));
        if (ubPeruService.findOne(1642) == null)
            ubPeruService.save(new UbPeru("160601", "Loreto", "Ucayali", "Contamana"));
        if (ubPeruService.findOne(1643) == null)
            ubPeruService.save(new UbPeru("160602", "Loreto", "Ucayali", "Inahuaya"));
        if (ubPeruService.findOne(1644) == null)
            ubPeruService.save(new UbPeru("160603", "Loreto", "Ucayali", "Padre Márquez"));
        if (ubPeruService.findOne(1645) == null)
            ubPeruService.save(new UbPeru("160604", "Loreto", "Ucayali", "Pampa Hermosa"));
        if (ubPeruService.findOne(1646) == null)
            ubPeruService.save(new UbPeru("160605", "Loreto", "Ucayali", "Sarayacu"));
        if (ubPeruService.findOne(1647) == null)
            ubPeruService.save(new UbPeru("160606", "Loreto", "Ucayali", "Vargas Guerra"));
        if (ubPeruService.findOne(1648) == null)
            ubPeruService.save(new UbPeru("160700", "Loreto", "Datem del Marañón", ""));
        if (ubPeruService.findOne(1649) == null)
            ubPeruService.save(new UbPeru("160701", "Loreto", "Datem del Marañón", "Barranca"));
        if (ubPeruService.findOne(1650) == null)
            ubPeruService.save(new UbPeru("160702", "Loreto", "Datem del Marañón", "Cahuapanas"));
        if (ubPeruService.findOne(1651) == null)
            ubPeruService.save(new UbPeru("160703", "Loreto", "Datem del Marañón", "Manseriche"));
        if (ubPeruService.findOne(1652) == null)
            ubPeruService.save(new UbPeru("160704", "Loreto", "Datem del Marañón", "Morona"));
        if (ubPeruService.findOne(1653) == null)
            ubPeruService.save(new UbPeru("160705", "Loreto", "Datem del Marañón", "Pastaza"));
        if (ubPeruService.findOne(1654) == null)
            ubPeruService.save(new UbPeru("160706", "Loreto", "Datem del Marañón", "Andoas"));
        if (ubPeruService.findOne(1655) == null) ubPeruService.save(new UbPeru("160800", "Loreto", "Putumayo", ""));
        if (ubPeruService.findOne(1656) == null)
            ubPeruService.save(new UbPeru("160801", "Loreto", "Putumayo", "Putumayo"));
        if (ubPeruService.findOne(1657) == null)
            ubPeruService.save(new UbPeru("160802", "Loreto", "Putumayo", "Rosa Panduro"));
        if (ubPeruService.findOne(1658) == null)
            ubPeruService.save(new UbPeru("160803", "Loreto", "Putumayo", "Teniente Manuel Clavero"));
        if (ubPeruService.findOne(1659) == null)
            ubPeruService.save(new UbPeru("160804", "Loreto", "Putumayo", "Yaguas"));
        if (ubPeruService.findOne(1660) == null) ubPeruService.save(new UbPeru("170000", "Madre de Dios", "", ""));
        if (ubPeruService.findOne(1661) == null)
            ubPeruService.save(new UbPeru("170100", "Madre de Dios", "Tambopata", ""));
        if (ubPeruService.findOne(1662) == null)
            ubPeruService.save(new UbPeru("170101", "Madre de Dios", "Tambopata", "Tambopata"));
        if (ubPeruService.findOne(1663) == null)
            ubPeruService.save(new UbPeru("170102", "Madre de Dios", "Tambopata", "Inambari"));
        if (ubPeruService.findOne(1664) == null)
            ubPeruService.save(new UbPeru("170103", "Madre de Dios", "Tambopata", "Las Piedras"));
        if (ubPeruService.findOne(1665) == null)
            ubPeruService.save(new UbPeru("170104", "Madre de Dios", "Tambopata", "Laberinto"));
        if (ubPeruService.findOne(1666) == null) ubPeruService.save(new UbPeru("170200", "Madre de Dios", "Manu", ""));
        if (ubPeruService.findOne(1667) == null)
            ubPeruService.save(new UbPeru("170201", "Madre de Dios", "Manu", "Manu"));
        if (ubPeruService.findOne(1668) == null)
            ubPeruService.save(new UbPeru("170202", "Madre de Dios", "Manu", "Fitzcarrald"));
        if (ubPeruService.findOne(1669) == null)
            ubPeruService.save(new UbPeru("170203", "Madre de Dios", "Manu", "Madre de Dios"));
        if (ubPeruService.findOne(1670) == null)
            ubPeruService.save(new UbPeru("170204", "Madre de Dios", "Manu", "Huepetuhe"));
        if (ubPeruService.findOne(1671) == null)
            ubPeruService.save(new UbPeru("170300", "Madre de Dios", "Tahuamanu", ""));
        if (ubPeruService.findOne(1672) == null)
            ubPeruService.save(new UbPeru("170301", "Madre de Dios", "Tahuamanu", "Iñapari"));
        if (ubPeruService.findOne(1673) == null)
            ubPeruService.save(new UbPeru("170302", "Madre de Dios", "Tahuamanu", "Iberia"));
        if (ubPeruService.findOne(1674) == null)
            ubPeruService.save(new UbPeru("170303", "Madre de Dios", "Tahuamanu", "Tahuamanu"));
        if (ubPeruService.findOne(1675) == null) ubPeruService.save(new UbPeru("180000", "Moquegua", "", ""));
        if (ubPeruService.findOne(1676) == null)
            ubPeruService.save(new UbPeru("180100", "Moquegua", "Mariscal Nieto", ""));
        if (ubPeruService.findOne(1677) == null)
            ubPeruService.save(new UbPeru("180101", "Moquegua", "Mariscal Nieto", "Moquegua"));
        if (ubPeruService.findOne(1678) == null)
            ubPeruService.save(new UbPeru("180102", "Moquegua", "Mariscal Nieto", "Carumas"));
        if (ubPeruService.findOne(1679) == null)
            ubPeruService.save(new UbPeru("180103", "Moquegua", "Mariscal Nieto", "Cuchumbaya"));
        if (ubPeruService.findOne(1680) == null)
            ubPeruService.save(new UbPeru("180104", "Moquegua", "Mariscal Nieto", "Samegua"));
        if (ubPeruService.findOne(1681) == null)
            ubPeruService.save(new UbPeru("180105", "Moquegua", "Mariscal Nieto", "San Cristóbal"));
        if (ubPeruService.findOne(1682) == null)
            ubPeruService.save(new UbPeru("180106", "Moquegua", "Mariscal Nieto", "Torata"));
        if (ubPeruService.findOne(1683) == null)
            ubPeruService.save(new UbPeru("180200", "Moquegua", "General Sánchez Cerro", ""));
        if (ubPeruService.findOne(1684) == null)
            ubPeruService.save(new UbPeru("180201", "Moquegua", "General Sánchez Cerro", "Omate"));
        if (ubPeruService.findOne(1685) == null)
            ubPeruService.save(new UbPeru("180202", "Moquegua", "General Sánchez Cerro", "Chojata"));
        if (ubPeruService.findOne(1686) == null)
            ubPeruService.save(new UbPeru("180203", "Moquegua", "General Sánchez Cerro", "Coalaque"));
        if (ubPeruService.findOne(1687) == null)
            ubPeruService.save(new UbPeru("180204", "Moquegua", "General Sánchez Cerro", "Ichuña"));
        if (ubPeruService.findOne(1688) == null)
            ubPeruService.save(new UbPeru("180205", "Moquegua", "General Sánchez Cerro", "La Capilla"));
        if (ubPeruService.findOne(1689) == null)
            ubPeruService.save(new UbPeru("180206", "Moquegua", "General Sánchez Cerro", "Lloque"));
        if (ubPeruService.findOne(1690) == null)
            ubPeruService.save(new UbPeru("180207", "Moquegua", "General Sánchez Cerro", "Matalaque"));
        if (ubPeruService.findOne(1691) == null)
            ubPeruService.save(new UbPeru("180208", "Moquegua", "General Sánchez Cerro", "Puquina"));
        if (ubPeruService.findOne(1692) == null)
            ubPeruService.save(new UbPeru("180209", "Moquegua", "General Sánchez Cerro", "Quinistaquillas"));
        if (ubPeruService.findOne(1693) == null)
            ubPeruService.save(new UbPeru("180210", "Moquegua", "General Sánchez Cerro", "Ubinas"));
        if (ubPeruService.findOne(1694) == null)
            ubPeruService.save(new UbPeru("180211", "Moquegua", "General Sánchez Cerro", "Yunga"));
        if (ubPeruService.findOne(1695) == null) ubPeruService.save(new UbPeru("180300", "Moquegua", "Ilo", ""));
        if (ubPeruService.findOne(1696) == null) ubPeruService.save(new UbPeru("180301", "Moquegua", "Ilo", "Ilo"));
        if (ubPeruService.findOne(1697) == null)
            ubPeruService.save(new UbPeru("180302", "Moquegua", "Ilo", "El Algarrobal"));
        if (ubPeruService.findOne(1698) == null) ubPeruService.save(new UbPeru("180303", "Moquegua", "Ilo", "Pacocha"));
        if (ubPeruService.findOne(1699) == null) ubPeruService.save(new UbPeru("190000", "Pasco", "", ""));
        if (ubPeruService.findOne(1700) == null) ubPeruService.save(new UbPeru("190100", "Pasco", "Pasco", ""));
        if (ubPeruService.findOne(1701) == null)
            ubPeruService.save(new UbPeru("190101", "Pasco", "Pasco", "Chaupimarca"));
        if (ubPeruService.findOne(1702) == null) ubPeruService.save(new UbPeru("190102", "Pasco", "Pasco", "Huachon"));
        if (ubPeruService.findOne(1703) == null) ubPeruService.save(new UbPeru("190103", "Pasco", "Pasco", "Huariaca"));
        if (ubPeruService.findOne(1704) == null) ubPeruService.save(new UbPeru("190104", "Pasco", "Pasco", "Huayllay"));
        if (ubPeruService.findOne(1705) == null) ubPeruService.save(new UbPeru("190105", "Pasco", "Pasco", "Ninacaca"));
        if (ubPeruService.findOne(1706) == null)
            ubPeruService.save(new UbPeru("190106", "Pasco", "Pasco", "Pallanchacra"));
        if (ubPeruService.findOne(1707) == null)
            ubPeruService.save(new UbPeru("190107", "Pasco", "Pasco", "Paucartambo"));
        if (ubPeruService.findOne(1708) == null)
            ubPeruService.save(new UbPeru("190108", "Pasco", "Pasco", "San Francisco de Asís de Yarusyacan"));
        if (ubPeruService.findOne(1709) == null)
            ubPeruService.save(new UbPeru("190109", "Pasco", "Pasco", "Simon Bolívar"));
        if (ubPeruService.findOne(1710) == null)
            ubPeruService.save(new UbPeru("190110", "Pasco", "Pasco", "Ticlacayan"));
        if (ubPeruService.findOne(1711) == null)
            ubPeruService.save(new UbPeru("190111", "Pasco", "Pasco", "Tinyahuarco"));
        if (ubPeruService.findOne(1712) == null) ubPeruService.save(new UbPeru("190112", "Pasco", "Pasco", "Vicco"));
        if (ubPeruService.findOne(1713) == null)
            ubPeruService.save(new UbPeru("190113", "Pasco", "Pasco", "Yanacancha"));
        if (ubPeruService.findOne(1714) == null)
            ubPeruService.save(new UbPeru("190200", "Pasco", "Daniel Alcides Carrión", ""));
        if (ubPeruService.findOne(1715) == null)
            ubPeruService.save(new UbPeru("190201", "Pasco", "Daniel Alcides Carrión", "Yanahuanca"));
        if (ubPeruService.findOne(1716) == null)
            ubPeruService.save(new UbPeru("190202", "Pasco", "Daniel Alcides Carrión", "Chacayan"));
        if (ubPeruService.findOne(1717) == null)
            ubPeruService.save(new UbPeru("190203", "Pasco", "Daniel Alcides Carrión", "Goyllarisquizga"));
        if (ubPeruService.findOne(1718) == null)
            ubPeruService.save(new UbPeru("190204", "Pasco", "Daniel Alcides Carrión", "Paucar"));
        if (ubPeruService.findOne(1719) == null)
            ubPeruService.save(new UbPeru("190205", "Pasco", "Daniel Alcides Carrión", "San Pedro de Pillao"));
        if (ubPeruService.findOne(1720) == null)
            ubPeruService.save(new UbPeru("190206", "Pasco", "Daniel Alcides Carrión", "Santa Ana de Tusi"));
        if (ubPeruService.findOne(1721) == null)
            ubPeruService.save(new UbPeru("190207", "Pasco", "Daniel Alcides Carrión", "Tapuc"));
        if (ubPeruService.findOne(1722) == null)
            ubPeruService.save(new UbPeru("190208", "Pasco", "Daniel Alcides Carrión", "Vilcabamba"));
        if (ubPeruService.findOne(1723) == null) ubPeruService.save(new UbPeru("190300", "Pasco", "Oxapampa", ""));
        if (ubPeruService.findOne(1724) == null)
            ubPeruService.save(new UbPeru("190301", "Pasco", "Oxapampa", "Oxapampa"));
        if (ubPeruService.findOne(1725) == null)
            ubPeruService.save(new UbPeru("190302", "Pasco", "Oxapampa", "Chontabamba"));
        if (ubPeruService.findOne(1726) == null)
            ubPeruService.save(new UbPeru("190303", "Pasco", "Oxapampa", "Huancabamba"));
        if (ubPeruService.findOne(1727) == null)
            ubPeruService.save(new UbPeru("190304", "Pasco", "Oxapampa", "Palcazu"));
        if (ubPeruService.findOne(1728) == null)
            ubPeruService.save(new UbPeru("190305", "Pasco", "Oxapampa", "Pozuzo"));
        if (ubPeruService.findOne(1729) == null)
            ubPeruService.save(new UbPeru("190306", "Pasco", "Oxapampa", "Puerto Bermúdez"));
        if (ubPeruService.findOne(1730) == null)
            ubPeruService.save(new UbPeru("190307", "Pasco", "Oxapampa", "Villa Rica"));
        if (ubPeruService.findOne(1731) == null)
            ubPeruService.save(new UbPeru("190308", "Pasco", "Oxapampa", "Constitución"));
        if (ubPeruService.findOne(1732) == null) ubPeruService.save(new UbPeru("200000", "Piura", "", ""));
        if (ubPeruService.findOne(1733) == null) ubPeruService.save(new UbPeru("200100", "Piura", "Piura", ""));
        if (ubPeruService.findOne(1734) == null) ubPeruService.save(new UbPeru("200101", "Piura", "Piura", "Piura"));
        if (ubPeruService.findOne(1735) == null) ubPeruService.save(new UbPeru("200104", "Piura", "Piura", "Castilla"));
        if (ubPeruService.findOne(1736) == null) ubPeruService.save(new UbPeru("200105", "Piura", "Piura", "Catacaos"));
        if (ubPeruService.findOne(1737) == null)
            ubPeruService.save(new UbPeru("200107", "Piura", "Piura", "Cura Mori"));
        if (ubPeruService.findOne(1738) == null)
            ubPeruService.save(new UbPeru("200108", "Piura", "Piura", "El Tallan"));
        if (ubPeruService.findOne(1739) == null) ubPeruService.save(new UbPeru("200109", "Piura", "Piura", "La Arena"));
        if (ubPeruService.findOne(1740) == null) ubPeruService.save(new UbPeru("200110", "Piura", "Piura", "La Unión"));
        if (ubPeruService.findOne(1741) == null)
            ubPeruService.save(new UbPeru("200111", "Piura", "Piura", "Las Lomas"));
        if (ubPeruService.findOne(1742) == null)
            ubPeruService.save(new UbPeru("200114", "Piura", "Piura", "Tambo Grande"));
        if (ubPeruService.findOne(1743) == null)
            ubPeruService.save(new UbPeru("200115", "Piura", "Piura", "Veintiseis de Octubre"));
        if (ubPeruService.findOne(1744) == null) ubPeruService.save(new UbPeru("200200", "Piura", "Ayabaca", ""));
        if (ubPeruService.findOne(1745) == null)
            ubPeruService.save(new UbPeru("200201", "Piura", "Ayabaca", "Ayabaca"));
        if (ubPeruService.findOne(1746) == null) ubPeruService.save(new UbPeru("200202", "Piura", "Ayabaca", "Frias"));
        if (ubPeruService.findOne(1747) == null) ubPeruService.save(new UbPeru("200203", "Piura", "Ayabaca", "Jilili"));
        if (ubPeruService.findOne(1748) == null)
            ubPeruService.save(new UbPeru("200204", "Piura", "Ayabaca", "Lagunas"));
        if (ubPeruService.findOne(1749) == null)
            ubPeruService.save(new UbPeru("200205", "Piura", "Ayabaca", "Montero"));
        if (ubPeruService.findOne(1750) == null)
            ubPeruService.save(new UbPeru("200206", "Piura", "Ayabaca", "Pacaipampa"));
        if (ubPeruService.findOne(1751) == null) ubPeruService.save(new UbPeru("200207", "Piura", "Ayabaca", "Paimas"));
        if (ubPeruService.findOne(1752) == null)
            ubPeruService.save(new UbPeru("200208", "Piura", "Ayabaca", "Sapillica"));
        if (ubPeruService.findOne(1753) == null)
            ubPeruService.save(new UbPeru("200209", "Piura", "Ayabaca", "Sicchez"));
        if (ubPeruService.findOne(1754) == null) ubPeruService.save(new UbPeru("200210", "Piura", "Ayabaca", "Suyo"));
        if (ubPeruService.findOne(1755) == null) ubPeruService.save(new UbPeru("200300", "Piura", "Huancabamba", ""));
        if (ubPeruService.findOne(1756) == null)
            ubPeruService.save(new UbPeru("200301", "Piura", "Huancabamba", "Huancabamba"));
        if (ubPeruService.findOne(1757) == null)
            ubPeruService.save(new UbPeru("200302", "Piura", "Huancabamba", "Canchaque"));
        if (ubPeruService.findOne(1758) == null)
            ubPeruService.save(new UbPeru("200303", "Piura", "Huancabamba", "El Carmen de la Frontera"));
        if (ubPeruService.findOne(1759) == null)
            ubPeruService.save(new UbPeru("200304", "Piura", "Huancabamba", "Huarmaca"));
        if (ubPeruService.findOne(1760) == null)
            ubPeruService.save(new UbPeru("200305", "Piura", "Huancabamba", "Lalaquiz"));
        if (ubPeruService.findOne(1761) == null)
            ubPeruService.save(new UbPeru("200306", "Piura", "Huancabamba", "San Miguel de El Faique"));
        if (ubPeruService.findOne(1762) == null)
            ubPeruService.save(new UbPeru("200307", "Piura", "Huancabamba", "Sondor"));
        if (ubPeruService.findOne(1763) == null)
            ubPeruService.save(new UbPeru("200308", "Piura", "Huancabamba", "Sondorillo"));
        if (ubPeruService.findOne(1764) == null) ubPeruService.save(new UbPeru("200400", "Piura", "Morropón", ""));
        if (ubPeruService.findOne(1765) == null)
            ubPeruService.save(new UbPeru("200401", "Piura", "Morropón", "Chulucanas"));
        if (ubPeruService.findOne(1766) == null)
            ubPeruService.save(new UbPeru("200402", "Piura", "Morropón", "Buenos Aires"));
        if (ubPeruService.findOne(1767) == null)
            ubPeruService.save(new UbPeru("200403", "Piura", "Morropón", "Chalaco"));
        if (ubPeruService.findOne(1768) == null)
            ubPeruService.save(new UbPeru("200404", "Piura", "Morropón", "La Matanza"));
        if (ubPeruService.findOne(1769) == null)
            ubPeruService.save(new UbPeru("200405", "Piura", "Morropón", "Morropon"));
        if (ubPeruService.findOne(1770) == null)
            ubPeruService.save(new UbPeru("200406", "Piura", "Morropón", "Salitral"));
        if (ubPeruService.findOne(1771) == null)
            ubPeruService.save(new UbPeru("200407", "Piura", "Morropón", "San Juan de Bigote"));
        if (ubPeruService.findOne(1772) == null)
            ubPeruService.save(new UbPeru("200408", "Piura", "Morropón", "Santa Catalina de Mossa"));
        if (ubPeruService.findOne(1773) == null)
            ubPeruService.save(new UbPeru("200409", "Piura", "Morropón", "Santo Domingo"));
        if (ubPeruService.findOne(1774) == null)
            ubPeruService.save(new UbPeru("200410", "Piura", "Morropón", "Yamango"));
        if (ubPeruService.findOne(1775) == null) ubPeruService.save(new UbPeru("200500", "Piura", "Paita", ""));
        if (ubPeruService.findOne(1776) == null) ubPeruService.save(new UbPeru("200501", "Piura", "Paita", "Paita"));
        if (ubPeruService.findOne(1777) == null) ubPeruService.save(new UbPeru("200502", "Piura", "Paita", "Amotape"));
        if (ubPeruService.findOne(1778) == null) ubPeruService.save(new UbPeru("200503", "Piura", "Paita", "Arenal"));
        if (ubPeruService.findOne(1779) == null) ubPeruService.save(new UbPeru("200504", "Piura", "Paita", "Colan"));
        if (ubPeruService.findOne(1780) == null) ubPeruService.save(new UbPeru("200505", "Piura", "Paita", "La Huaca"));
        if (ubPeruService.findOne(1781) == null)
            ubPeruService.save(new UbPeru("200506", "Piura", "Paita", "Tamarindo"));
        if (ubPeruService.findOne(1782) == null) ubPeruService.save(new UbPeru("200507", "Piura", "Paita", "Vichayal"));
        if (ubPeruService.findOne(1783) == null) ubPeruService.save(new UbPeru("200600", "Piura", "Sullana", ""));
        if (ubPeruService.findOne(1784) == null)
            ubPeruService.save(new UbPeru("200601", "Piura", "Sullana", "Sullana"));
        if (ubPeruService.findOne(1785) == null)
            ubPeruService.save(new UbPeru("200602", "Piura", "Sullana", "Bellavista"));
        if (ubPeruService.findOne(1786) == null)
            ubPeruService.save(new UbPeru("200603", "Piura", "Sullana", "Ignacio Escudero"));
        if (ubPeruService.findOne(1787) == null)
            ubPeruService.save(new UbPeru("200604", "Piura", "Sullana", "Lancones"));
        if (ubPeruService.findOne(1788) == null)
            ubPeruService.save(new UbPeru("200605", "Piura", "Sullana", "Marcavelica"));
        if (ubPeruService.findOne(1789) == null)
            ubPeruService.save(new UbPeru("200606", "Piura", "Sullana", "Miguel Checa"));
        if (ubPeruService.findOne(1790) == null)
            ubPeruService.save(new UbPeru("200607", "Piura", "Sullana", "Querecotillo"));
        if (ubPeruService.findOne(1791) == null)
            ubPeruService.save(new UbPeru("200608", "Piura", "Sullana", "Salitral"));
        if (ubPeruService.findOne(1792) == null) ubPeruService.save(new UbPeru("200700", "Piura", "Talara", ""));
        if (ubPeruService.findOne(1793) == null) ubPeruService.save(new UbPeru("200701", "Piura", "Talara", "Pariñas"));
        if (ubPeruService.findOne(1794) == null) ubPeruService.save(new UbPeru("200702", "Piura", "Talara", "El Alto"));
        if (ubPeruService.findOne(1795) == null) ubPeruService.save(new UbPeru("200703", "Piura", "Talara", "La Brea"));
        if (ubPeruService.findOne(1796) == null) ubPeruService.save(new UbPeru("200704", "Piura", "Talara", "Lobitos"));
        if (ubPeruService.findOne(1797) == null)
            ubPeruService.save(new UbPeru("200705", "Piura", "Talara", "Los Organos"));
        if (ubPeruService.findOne(1798) == null) ubPeruService.save(new UbPeru("200706", "Piura", "Talara", "Mancora"));
        if (ubPeruService.findOne(1799) == null) ubPeruService.save(new UbPeru("200800", "Piura", "Sechura", ""));
        if (ubPeruService.findOne(1800) == null)
            ubPeruService.save(new UbPeru("200801", "Piura", "Sechura", "Sechura"));
        if (ubPeruService.findOne(1801) == null)
            ubPeruService.save(new UbPeru("200802", "Piura", "Sechura", "Bellavista de la Unión"));
        if (ubPeruService.findOne(1802) == null) ubPeruService.save(new UbPeru("200803", "Piura", "Sechura", "Bernal"));
        if (ubPeruService.findOne(1803) == null)
            ubPeruService.save(new UbPeru("200804", "Piura", "Sechura", "Cristo Nos Valga"));
        if (ubPeruService.findOne(1804) == null) ubPeruService.save(new UbPeru("200805", "Piura", "Sechura", "Vice"));
        if (ubPeruService.findOne(1805) == null)
            ubPeruService.save(new UbPeru("200806", "Piura", "Sechura", "Rinconada Llicuar"));
        if (ubPeruService.findOne(1806) == null) ubPeruService.save(new UbPeru("210000", "Puno", "", ""));
        if (ubPeruService.findOne(1807) == null) ubPeruService.save(new UbPeru("210100", "Puno", "Puno", ""));
        if (ubPeruService.findOne(1808) == null) ubPeruService.save(new UbPeru("210101", "Puno", "Puno", "Puno"));
        if (ubPeruService.findOne(1809) == null) ubPeruService.save(new UbPeru("210102", "Puno", "Puno", "Acora"));
        if (ubPeruService.findOne(1810) == null) ubPeruService.save(new UbPeru("210103", "Puno", "Puno", "Amantani"));
        if (ubPeruService.findOne(1811) == null) ubPeruService.save(new UbPeru("210104", "Puno", "Puno", "Atuncolla"));
        if (ubPeruService.findOne(1812) == null) ubPeruService.save(new UbPeru("210105", "Puno", "Puno", "Capachica"));
        if (ubPeruService.findOne(1813) == null) ubPeruService.save(new UbPeru("210106", "Puno", "Puno", "Chucuito"));
        if (ubPeruService.findOne(1814) == null) ubPeruService.save(new UbPeru("210107", "Puno", "Puno", "Coata"));
        if (ubPeruService.findOne(1815) == null) ubPeruService.save(new UbPeru("210108", "Puno", "Puno", "Huata"));
        if (ubPeruService.findOne(1816) == null) ubPeruService.save(new UbPeru("210109", "Puno", "Puno", "Mañazo"));
        if (ubPeruService.findOne(1817) == null)
            ubPeruService.save(new UbPeru("210110", "Puno", "Puno", "Paucarcolla"));
        if (ubPeruService.findOne(1818) == null) ubPeruService.save(new UbPeru("210111", "Puno", "Puno", "Pichacani"));
        if (ubPeruService.findOne(1819) == null) ubPeruService.save(new UbPeru("210112", "Puno", "Puno", "Plateria"));
        if (ubPeruService.findOne(1820) == null)
            ubPeruService.save(new UbPeru("210113", "Puno", "Puno", "San Antonio"));
        if (ubPeruService.findOne(1821) == null) ubPeruService.save(new UbPeru("210114", "Puno", "Puno", "Tiquillaca"));
        if (ubPeruService.findOne(1822) == null) ubPeruService.save(new UbPeru("210115", "Puno", "Puno", "Vilque"));
        if (ubPeruService.findOne(1823) == null) ubPeruService.save(new UbPeru("210200", "Puno", "Azángaro", ""));
        if (ubPeruService.findOne(1824) == null)
            ubPeruService.save(new UbPeru("210201", "Puno", "Azángaro", "Azángaro"));
        if (ubPeruService.findOne(1825) == null) ubPeruService.save(new UbPeru("210202", "Puno", "Azángaro", "Achaya"));
        if (ubPeruService.findOne(1826) == null) ubPeruService.save(new UbPeru("210203", "Puno", "Azángaro", "Arapa"));
        if (ubPeruService.findOne(1827) == null) ubPeruService.save(new UbPeru("210204", "Puno", "Azángaro", "Asillo"));
        if (ubPeruService.findOne(1828) == null)
            ubPeruService.save(new UbPeru("210205", "Puno", "Azángaro", "Caminaca"));
        if (ubPeruService.findOne(1829) == null) ubPeruService.save(new UbPeru("210206", "Puno", "Azángaro", "Chupa"));
        if (ubPeruService.findOne(1830) == null)
            ubPeruService.save(new UbPeru("210207", "Puno", "Azángaro", "José Domingo Choquehuanca"));
        if (ubPeruService.findOne(1831) == null) ubPeruService.save(new UbPeru("210208", "Puno", "Azángaro", "Muñani"));
        if (ubPeruService.findOne(1832) == null) ubPeruService.save(new UbPeru("210209", "Puno", "Azángaro", "Potoni"));
        if (ubPeruService.findOne(1833) == null) ubPeruService.save(new UbPeru("210210", "Puno", "Azángaro", "Saman"));
        if (ubPeruService.findOne(1834) == null)
            ubPeruService.save(new UbPeru("210211", "Puno", "Azángaro", "San Anton"));
        if (ubPeruService.findOne(1835) == null)
            ubPeruService.save(new UbPeru("210212", "Puno", "Azángaro", "San José"));
        if (ubPeruService.findOne(1836) == null)
            ubPeruService.save(new UbPeru("210213", "Puno", "Azángaro", "San Juan de Salinas"));
        if (ubPeruService.findOne(1837) == null)
            ubPeruService.save(new UbPeru("210214", "Puno", "Azángaro", "Santiago de Pupuja"));
        if (ubPeruService.findOne(1838) == null)
            ubPeruService.save(new UbPeru("210215", "Puno", "Azángaro", "Tirapata"));
        if (ubPeruService.findOne(1839) == null) ubPeruService.save(new UbPeru("210300", "Puno", "Carabaya", ""));
        if (ubPeruService.findOne(1840) == null)
            ubPeruService.save(new UbPeru("210301", "Puno", "Carabaya", "Macusani"));
        if (ubPeruService.findOne(1841) == null)
            ubPeruService.save(new UbPeru("210302", "Puno", "Carabaya", "Ajoyani"));
        if (ubPeruService.findOne(1842) == null)
            ubPeruService.save(new UbPeru("210303", "Puno", "Carabaya", "Ayapata"));
        if (ubPeruService.findOne(1843) == null) ubPeruService.save(new UbPeru("210304", "Puno", "Carabaya", "Coasa"));
        if (ubPeruService.findOne(1844) == null) ubPeruService.save(new UbPeru("210305", "Puno", "Carabaya", "Corani"));
        if (ubPeruService.findOne(1845) == null)
            ubPeruService.save(new UbPeru("210306", "Puno", "Carabaya", "Crucero"));
        if (ubPeruService.findOne(1846) == null) ubPeruService.save(new UbPeru("210307", "Puno", "Carabaya", "Ituata"));
        if (ubPeruService.findOne(1847) == null)
            ubPeruService.save(new UbPeru("210308", "Puno", "Carabaya", "Ollachea"));
        if (ubPeruService.findOne(1848) == null)
            ubPeruService.save(new UbPeru("210309", "Puno", "Carabaya", "San Gaban"));
        if (ubPeruService.findOne(1849) == null)
            ubPeruService.save(new UbPeru("210310", "Puno", "Carabaya", "Usicayos"));
        if (ubPeruService.findOne(1850) == null) ubPeruService.save(new UbPeru("210400", "Puno", "Chucuito", ""));
        if (ubPeruService.findOne(1851) == null) ubPeruService.save(new UbPeru("210401", "Puno", "Chucuito", "Juli"));
        if (ubPeruService.findOne(1852) == null)
            ubPeruService.save(new UbPeru("210402", "Puno", "Chucuito", "Desaguadero"));
        if (ubPeruService.findOne(1853) == null)
            ubPeruService.save(new UbPeru("210403", "Puno", "Chucuito", "Huacullani"));
        if (ubPeruService.findOne(1854) == null)
            ubPeruService.save(new UbPeru("210404", "Puno", "Chucuito", "Kelluyo"));
        if (ubPeruService.findOne(1855) == null)
            ubPeruService.save(new UbPeru("210405", "Puno", "Chucuito", "Pisacoma"));
        if (ubPeruService.findOne(1856) == null) ubPeruService.save(new UbPeru("210406", "Puno", "Chucuito", "Pomata"));
        if (ubPeruService.findOne(1857) == null) ubPeruService.save(new UbPeru("210407", "Puno", "Chucuito", "Zepita"));
        if (ubPeruService.findOne(1858) == null) ubPeruService.save(new UbPeru("210500", "Puno", "El Collao", ""));
        if (ubPeruService.findOne(1859) == null) ubPeruService.save(new UbPeru("210501", "Puno", "El Collao", "Ilave"));
        if (ubPeruService.findOne(1860) == null)
            ubPeruService.save(new UbPeru("210502", "Puno", "El Collao", "Capazo"));
        if (ubPeruService.findOne(1861) == null)
            ubPeruService.save(new UbPeru("210503", "Puno", "El Collao", "Pilcuyo"));
        if (ubPeruService.findOne(1862) == null)
            ubPeruService.save(new UbPeru("210504", "Puno", "El Collao", "Santa Rosa"));
        if (ubPeruService.findOne(1863) == null)
            ubPeruService.save(new UbPeru("210505", "Puno", "El Collao", "Conduriri"));
        if (ubPeruService.findOne(1864) == null) ubPeruService.save(new UbPeru("210600", "Puno", "Huancané", ""));
        if (ubPeruService.findOne(1865) == null)
            ubPeruService.save(new UbPeru("210601", "Puno", "Huancané", "Huancane"));
        if (ubPeruService.findOne(1866) == null) ubPeruService.save(new UbPeru("210602", "Puno", "Huancané", "Cojata"));
        if (ubPeruService.findOne(1867) == null)
            ubPeruService.save(new UbPeru("210603", "Puno", "Huancané", "Huatasani"));
        if (ubPeruService.findOne(1868) == null)
            ubPeruService.save(new UbPeru("210604", "Puno", "Huancané", "Inchupalla"));
        if (ubPeruService.findOne(1869) == null) ubPeruService.save(new UbPeru("210605", "Puno", "Huancané", "Pusi"));
        if (ubPeruService.findOne(1870) == null)
            ubPeruService.save(new UbPeru("210606", "Puno", "Huancané", "Rosaspata"));
        if (ubPeruService.findOne(1871) == null) ubPeruService.save(new UbPeru("210607", "Puno", "Huancané", "Taraco"));
        if (ubPeruService.findOne(1872) == null)
            ubPeruService.save(new UbPeru("210608", "Puno", "Huancané", "Vilque Chico"));
        if (ubPeruService.findOne(1873) == null) ubPeruService.save(new UbPeru("210700", "Puno", "Lampa", ""));
        if (ubPeruService.findOne(1874) == null) ubPeruService.save(new UbPeru("210701", "Puno", "Lampa", "Lampa"));
        if (ubPeruService.findOne(1875) == null) ubPeruService.save(new UbPeru("210702", "Puno", "Lampa", "Cabanilla"));
        if (ubPeruService.findOne(1876) == null) ubPeruService.save(new UbPeru("210703", "Puno", "Lampa", "Calapuja"));
        if (ubPeruService.findOne(1877) == null) ubPeruService.save(new UbPeru("210704", "Puno", "Lampa", "Nicasio"));
        if (ubPeruService.findOne(1878) == null) ubPeruService.save(new UbPeru("210705", "Puno", "Lampa", "Ocuviri"));
        if (ubPeruService.findOne(1879) == null) ubPeruService.save(new UbPeru("210706", "Puno", "Lampa", "Palca"));
        if (ubPeruService.findOne(1880) == null) ubPeruService.save(new UbPeru("210707", "Puno", "Lampa", "Paratia"));
        if (ubPeruService.findOne(1881) == null) ubPeruService.save(new UbPeru("210708", "Puno", "Lampa", "Pucara"));
        if (ubPeruService.findOne(1882) == null)
            ubPeruService.save(new UbPeru("210709", "Puno", "Lampa", "Santa Lucia"));
        if (ubPeruService.findOne(1883) == null) ubPeruService.save(new UbPeru("210710", "Puno", "Lampa", "Vilavila"));
        if (ubPeruService.findOne(1884) == null) ubPeruService.save(new UbPeru("210800", "Puno", "Melgar", ""));
        if (ubPeruService.findOne(1885) == null) ubPeruService.save(new UbPeru("210801", "Puno", "Melgar", "Ayaviri"));
        if (ubPeruService.findOne(1886) == null) ubPeruService.save(new UbPeru("210802", "Puno", "Melgar", "Antauta"));
        if (ubPeruService.findOne(1887) == null) ubPeruService.save(new UbPeru("210803", "Puno", "Melgar", "Cupi"));
        if (ubPeruService.findOne(1888) == null) ubPeruService.save(new UbPeru("210804", "Puno", "Melgar", "Llalli"));
        if (ubPeruService.findOne(1889) == null) ubPeruService.save(new UbPeru("210805", "Puno", "Melgar", "Macari"));
        if (ubPeruService.findOne(1890) == null) ubPeruService.save(new UbPeru("210806", "Puno", "Melgar", "Nuñoa"));
        if (ubPeruService.findOne(1891) == null) ubPeruService.save(new UbPeru("210807", "Puno", "Melgar", "Orurillo"));
        if (ubPeruService.findOne(1892) == null)
            ubPeruService.save(new UbPeru("210808", "Puno", "Melgar", "Santa Rosa"));
        if (ubPeruService.findOne(1893) == null) ubPeruService.save(new UbPeru("210809", "Puno", "Melgar", "Umachiri"));
        if (ubPeruService.findOne(1894) == null) ubPeruService.save(new UbPeru("210900", "Puno", "Moho", ""));
        if (ubPeruService.findOne(1895) == null) ubPeruService.save(new UbPeru("210901", "Puno", "Moho", "Moho"));
        if (ubPeruService.findOne(1896) == null) ubPeruService.save(new UbPeru("210902", "Puno", "Moho", "Conima"));
        if (ubPeruService.findOne(1897) == null) ubPeruService.save(new UbPeru("210903", "Puno", "Moho", "Huayrapata"));
        if (ubPeruService.findOne(1898) == null) ubPeruService.save(new UbPeru("210904", "Puno", "Moho", "Tilali"));
        if (ubPeruService.findOne(1899) == null)
            ubPeruService.save(new UbPeru("211000", "Puno", "San Antonio de Putina", ""));
        if (ubPeruService.findOne(1900) == null)
            ubPeruService.save(new UbPeru("211001", "Puno", "San Antonio de Putina", "Putina"));
        if (ubPeruService.findOne(1901) == null)
            ubPeruService.save(new UbPeru("211002", "Puno", "San Antonio de Putina", "Ananea"));
        if (ubPeruService.findOne(1902) == null)
            ubPeruService.save(new UbPeru("211003", "Puno", "San Antonio de Putina", "Pedro Vilca Apaza"));
        if (ubPeruService.findOne(1903) == null)
            ubPeruService.save(new UbPeru("211004", "Puno", "San Antonio de Putina", "Quilcapuncu"));
        if (ubPeruService.findOne(1904) == null)
            ubPeruService.save(new UbPeru("211005", "Puno", "San Antonio de Putina", "Sina"));
        if (ubPeruService.findOne(1905) == null) ubPeruService.save(new UbPeru("211100", "Puno", "San Román", ""));
        if (ubPeruService.findOne(1906) == null)
            ubPeruService.save(new UbPeru("211101", "Puno", "San Román", "Juliaca"));
        if (ubPeruService.findOne(1907) == null)
            ubPeruService.save(new UbPeru("211102", "Puno", "San Román", "Cabana"));
        if (ubPeruService.findOne(1908) == null)
            ubPeruService.save(new UbPeru("211103", "Puno", "San Román", "Cabanillas"));
        if (ubPeruService.findOne(1909) == null)
            ubPeruService.save(new UbPeru("211104", "Puno", "San Román", "Caracoto"));
        if (ubPeruService.findOne(1910) == null) ubPeruService.save(new UbPeru("211200", "Puno", "Sandia", ""));
        if (ubPeruService.findOne(1911) == null) ubPeruService.save(new UbPeru("211201", "Puno", "Sandia", "Sandia"));
        if (ubPeruService.findOne(1912) == null) ubPeruService.save(new UbPeru("211202", "Puno", "Sandia", "Cuyocuyo"));
        if (ubPeruService.findOne(1913) == null) ubPeruService.save(new UbPeru("211203", "Puno", "Sandia", "Limbani"));
        if (ubPeruService.findOne(1914) == null)
            ubPeruService.save(new UbPeru("211204", "Puno", "Sandia", "Patambuco"));
        if (ubPeruService.findOne(1915) == null) ubPeruService.save(new UbPeru("211205", "Puno", "Sandia", "Phara"));
        if (ubPeruService.findOne(1916) == null) ubPeruService.save(new UbPeru("211206", "Puno", "Sandia", "Quiaca"));
        if (ubPeruService.findOne(1917) == null)
            ubPeruService.save(new UbPeru("211207", "Puno", "Sandia", "San Juan del Oro"));
        if (ubPeruService.findOne(1918) == null)
            ubPeruService.save(new UbPeru("211208", "Puno", "Sandia", "Yanahuaya"));
        if (ubPeruService.findOne(1919) == null)
            ubPeruService.save(new UbPeru("211209", "Puno", "Sandia", "Alto Inambari"));
        if (ubPeruService.findOne(1920) == null)
            ubPeruService.save(new UbPeru("211210", "Puno", "Sandia", "San Pedro de Putina Punco"));
        if (ubPeruService.findOne(1921) == null) ubPeruService.save(new UbPeru("211300", "Puno", "Yunguyo", ""));
        if (ubPeruService.findOne(1922) == null) ubPeruService.save(new UbPeru("211301", "Puno", "Yunguyo", "Yunguyo"));
        if (ubPeruService.findOne(1923) == null) ubPeruService.save(new UbPeru("211302", "Puno", "Yunguyo", "Anapia"));
        if (ubPeruService.findOne(1924) == null) ubPeruService.save(new UbPeru("211303", "Puno", "Yunguyo", "Copani"));
        if (ubPeruService.findOne(1925) == null)
            ubPeruService.save(new UbPeru("211304", "Puno", "Yunguyo", "Cuturapi"));
        if (ubPeruService.findOne(1926) == null)
            ubPeruService.save(new UbPeru("211305", "Puno", "Yunguyo", "Ollaraya"));
        if (ubPeruService.findOne(1927) == null)
            ubPeruService.save(new UbPeru("211306", "Puno", "Yunguyo", "Tinicachi"));
        if (ubPeruService.findOne(1928) == null)
            ubPeruService.save(new UbPeru("211307", "Puno", "Yunguyo", "Unicachi"));
        if (ubPeruService.findOne(1929) == null) ubPeruService.save(new UbPeru("220000", "San Martín", "", ""));
        if (ubPeruService.findOne(1930) == null)
            ubPeruService.save(new UbPeru("220100", "San Martín", "Moyobamba", ""));
        if (ubPeruService.findOne(1931) == null)
            ubPeruService.save(new UbPeru("220101", "San Martín", "Moyobamba", "Moyobamba"));
        if (ubPeruService.findOne(1932) == null)
            ubPeruService.save(new UbPeru("220102", "San Martín", "Moyobamba", "Calzada"));
        if (ubPeruService.findOne(1933) == null)
            ubPeruService.save(new UbPeru("220103", "San Martín", "Moyobamba", "Habana"));
        if (ubPeruService.findOne(1934) == null)
            ubPeruService.save(new UbPeru("220104", "San Martín", "Moyobamba", "Jepelacio"));
        if (ubPeruService.findOne(1935) == null)
            ubPeruService.save(new UbPeru("220105", "San Martín", "Moyobamba", "Soritor"));
        if (ubPeruService.findOne(1936) == null)
            ubPeruService.save(new UbPeru("220106", "San Martín", "Moyobamba", "Yantalo"));
        if (ubPeruService.findOne(1937) == null)
            ubPeruService.save(new UbPeru("220200", "San Martín", "Bellavista", ""));
        if (ubPeruService.findOne(1938) == null)
            ubPeruService.save(new UbPeru("220201", "San Martín", "Bellavista", "Bellavista"));
        if (ubPeruService.findOne(1939) == null)
            ubPeruService.save(new UbPeru("220202", "San Martín", "Bellavista", "Alto Biavo"));
        if (ubPeruService.findOne(1940) == null)
            ubPeruService.save(new UbPeru("220203", "San Martín", "Bellavista", "Bajo Biavo"));
        if (ubPeruService.findOne(1941) == null)
            ubPeruService.save(new UbPeru("220204", "San Martín", "Bellavista", "Huallaga"));
        if (ubPeruService.findOne(1942) == null)
            ubPeruService.save(new UbPeru("220205", "San Martín", "Bellavista", "San Pablo"));
        if (ubPeruService.findOne(1943) == null)
            ubPeruService.save(new UbPeru("220206", "San Martín", "Bellavista", "San Rafael"));
        if (ubPeruService.findOne(1944) == null)
            ubPeruService.save(new UbPeru("220300", "San Martín", "El Dorado", ""));
        if (ubPeruService.findOne(1945) == null)
            ubPeruService.save(new UbPeru("220301", "San Martín", "El Dorado", "San José de Sisa"));
        if (ubPeruService.findOne(1946) == null)
            ubPeruService.save(new UbPeru("220302", "San Martín", "El Dorado", "Agua Blanca"));
        if (ubPeruService.findOne(1947) == null)
            ubPeruService.save(new UbPeru("220303", "San Martín", "El Dorado", "San Martín"));
        if (ubPeruService.findOne(1948) == null)
            ubPeruService.save(new UbPeru("220304", "San Martín", "El Dorado", "Santa Rosa"));
        if (ubPeruService.findOne(1949) == null)
            ubPeruService.save(new UbPeru("220305", "San Martín", "El Dorado", "Shatoja"));
        if (ubPeruService.findOne(1950) == null) ubPeruService.save(new UbPeru("220400", "San Martín", "Huallaga", ""));
        if (ubPeruService.findOne(1951) == null)
            ubPeruService.save(new UbPeru("220401", "San Martín", "Huallaga", "Saposoa"));
        if (ubPeruService.findOne(1952) == null)
            ubPeruService.save(new UbPeru("220402", "San Martín", "Huallaga", "Alto Saposoa"));
        if (ubPeruService.findOne(1953) == null)
            ubPeruService.save(new UbPeru("220403", "San Martín", "Huallaga", "El Eslabón"));
        if (ubPeruService.findOne(1954) == null)
            ubPeruService.save(new UbPeru("220404", "San Martín", "Huallaga", "Piscoyacu"));
        if (ubPeruService.findOne(1955) == null)
            ubPeruService.save(new UbPeru("220405", "San Martín", "Huallaga", "Sacanche"));
        if (ubPeruService.findOne(1956) == null)
            ubPeruService.save(new UbPeru("220406", "San Martín", "Huallaga", "Tingo de Saposoa"));
        if (ubPeruService.findOne(1957) == null) ubPeruService.save(new UbPeru("220500", "San Martín", "Lamas", ""));
        if (ubPeruService.findOne(1958) == null)
            ubPeruService.save(new UbPeru("220501", "San Martín", "Lamas", "Lamas"));
        if (ubPeruService.findOne(1959) == null)
            ubPeruService.save(new UbPeru("220502", "San Martín", "Lamas", "Alonso de Alvarado"));
        if (ubPeruService.findOne(1960) == null)
            ubPeruService.save(new UbPeru("220503", "San Martín", "Lamas", "Barranquita"));
        if (ubPeruService.findOne(1961) == null)
            ubPeruService.save(new UbPeru("220504", "San Martín", "Lamas", "Caynarachi"));
        if (ubPeruService.findOne(1962) == null)
            ubPeruService.save(new UbPeru("220505", "San Martín", "Lamas", "Cuñumbuqui"));
        if (ubPeruService.findOne(1963) == null)
            ubPeruService.save(new UbPeru("220506", "San Martín", "Lamas", "Pinto Recodo"));
        if (ubPeruService.findOne(1964) == null)
            ubPeruService.save(new UbPeru("220507", "San Martín", "Lamas", "Rumisapa"));
        if (ubPeruService.findOne(1965) == null)
            ubPeruService.save(new UbPeru("220508", "San Martín", "Lamas", "San Roque de Cumbaza"));
        if (ubPeruService.findOne(1966) == null)
            ubPeruService.save(new UbPeru("220509", "San Martín", "Lamas", "Shanao"));
        if (ubPeruService.findOne(1967) == null)
            ubPeruService.save(new UbPeru("220510", "San Martín", "Lamas", "Tabalosos"));
        if (ubPeruService.findOne(1968) == null)
            ubPeruService.save(new UbPeru("220511", "San Martín", "Lamas", "Zapatero"));
        if (ubPeruService.findOne(1969) == null)
            ubPeruService.save(new UbPeru("220600", "San Martín", "Mariscal Cáceres", ""));
        if (ubPeruService.findOne(1970) == null)
            ubPeruService.save(new UbPeru("220601", "San Martín", "Mariscal Cáceres", "Juanjuí"));
        if (ubPeruService.findOne(1971) == null)
            ubPeruService.save(new UbPeru("220602", "San Martín", "Mariscal Cáceres", "Campanilla"));
        if (ubPeruService.findOne(1972) == null)
            ubPeruService.save(new UbPeru("220603", "San Martín", "Mariscal Cáceres", "Huicungo"));
        if (ubPeruService.findOne(1973) == null)
            ubPeruService.save(new UbPeru("220604", "San Martín", "Mariscal Cáceres", "Pachiza"));
        if (ubPeruService.findOne(1974) == null)
            ubPeruService.save(new UbPeru("220605", "San Martín", "Mariscal Cáceres", "Pajarillo"));
        if (ubPeruService.findOne(1975) == null) ubPeruService.save(new UbPeru("220700", "San Martín", "Picota", ""));
        if (ubPeruService.findOne(1976) == null)
            ubPeruService.save(new UbPeru("220701", "San Martín", "Picota", "Picota"));
        if (ubPeruService.findOne(1977) == null)
            ubPeruService.save(new UbPeru("220702", "San Martín", "Picota", "Buenos Aires"));
        if (ubPeruService.findOne(1978) == null)
            ubPeruService.save(new UbPeru("220703", "San Martín", "Picota", "Caspisapa"));
        if (ubPeruService.findOne(1979) == null)
            ubPeruService.save(new UbPeru("220704", "San Martín", "Picota", "Pilluana"));
        if (ubPeruService.findOne(1980) == null)
            ubPeruService.save(new UbPeru("220705", "San Martín", "Picota", "Pucacaca"));
        if (ubPeruService.findOne(1981) == null)
            ubPeruService.save(new UbPeru("220706", "San Martín", "Picota", "San Cristóbal"));
        if (ubPeruService.findOne(1982) == null)
            ubPeruService.save(new UbPeru("220707", "San Martín", "Picota", "San Hilarión"));
        if (ubPeruService.findOne(1983) == null)
            ubPeruService.save(new UbPeru("220708", "San Martín", "Picota", "Shamboyacu"));
        if (ubPeruService.findOne(1984) == null)
            ubPeruService.save(new UbPeru("220709", "San Martín", "Picota", "Tingo de Ponasa"));
        if (ubPeruService.findOne(1985) == null)
            ubPeruService.save(new UbPeru("220710", "San Martín", "Picota", "Tres Unidos"));
        if (ubPeruService.findOne(1986) == null) ubPeruService.save(new UbPeru("220800", "San Martín", "Rioja", ""));
        if (ubPeruService.findOne(1987) == null)
            ubPeruService.save(new UbPeru("220801", "San Martín", "Rioja", "Rioja"));
        if (ubPeruService.findOne(1988) == null)
            ubPeruService.save(new UbPeru("220802", "San Martín", "Rioja", "Awajun"));
        if (ubPeruService.findOne(1989) == null)
            ubPeruService.save(new UbPeru("220803", "San Martín", "Rioja", "Elías Soplin Vargas"));
        if (ubPeruService.findOne(1990) == null)
            ubPeruService.save(new UbPeru("220804", "San Martín", "Rioja", "Nueva Cajamarca"));
        if (ubPeruService.findOne(1991) == null)
            ubPeruService.save(new UbPeru("220805", "San Martín", "Rioja", "Pardo Miguel"));
        if (ubPeruService.findOne(1992) == null)
            ubPeruService.save(new UbPeru("220806", "San Martín", "Rioja", "Posic"));
        if (ubPeruService.findOne(1993) == null)
            ubPeruService.save(new UbPeru("220807", "San Martín", "Rioja", "San Fernando"));
        if (ubPeruService.findOne(1994) == null)
            ubPeruService.save(new UbPeru("220808", "San Martín", "Rioja", "Yorongos"));
        if (ubPeruService.findOne(1995) == null)
            ubPeruService.save(new UbPeru("220809", "San Martín", "Rioja", "Yuracyacu"));
        if (ubPeruService.findOne(1996) == null)
            ubPeruService.save(new UbPeru("220900", "San Martín", "San Martín", ""));
        if (ubPeruService.findOne(1997) == null)
            ubPeruService.save(new UbPeru("220901", "San Martín", "San Martín", "Tarapoto"));
        if (ubPeruService.findOne(1998) == null)
            ubPeruService.save(new UbPeru("220902", "San Martín", "San Martín", "Alberto Leveau"));
        if (ubPeruService.findOne(1999) == null)
            ubPeruService.save(new UbPeru("220903", "San Martín", "San Martín", "Cacatachi"));
        if (ubPeruService.findOne(2000) == null)
            ubPeruService.save(new UbPeru("220904", "San Martín", "San Martín", "Chazuta"));
        if (ubPeruService.findOne(2001) == null)
            ubPeruService.save(new UbPeru("220905", "San Martín", "San Martín", "Chipurana"));
        if (ubPeruService.findOne(2002) == null)
            ubPeruService.save(new UbPeru("220906", "San Martín", "San Martín", "El Porvenir"));
        if (ubPeruService.findOne(2003) == null)
            ubPeruService.save(new UbPeru("220907", "San Martín", "San Martín", "Huimbayoc"));
        if (ubPeruService.findOne(2004) == null)
            ubPeruService.save(new UbPeru("220908", "San Martín", "San Martín", "Juan Guerra"));
        if (ubPeruService.findOne(2005) == null)
            ubPeruService.save(new UbPeru("220909", "San Martín", "San Martín", "La Banda de Shilcayo"));
        if (ubPeruService.findOne(2006) == null)
            ubPeruService.save(new UbPeru("220910", "San Martín", "San Martín", "Morales"));
        if (ubPeruService.findOne(2007) == null)
            ubPeruService.save(new UbPeru("220911", "San Martín", "San Martín", "Papaplaya"));
        if (ubPeruService.findOne(2008) == null)
            ubPeruService.save(new UbPeru("220912", "San Martín", "San Martín", "San Antonio"));
        if (ubPeruService.findOne(2009) == null)
            ubPeruService.save(new UbPeru("220913", "San Martín", "San Martín", "Sauce"));
        if (ubPeruService.findOne(2010) == null)
            ubPeruService.save(new UbPeru("220914", "San Martín", "San Martín", "Shapaja"));
        if (ubPeruService.findOne(2011) == null) ubPeruService.save(new UbPeru("221000", "San Martín", "Tocache", ""));
        if (ubPeruService.findOne(2012) == null)
            ubPeruService.save(new UbPeru("221001", "San Martín", "Tocache", "Tocache"));
        if (ubPeruService.findOne(2013) == null)
            ubPeruService.save(new UbPeru("221002", "San Martín", "Tocache", "Nuevo Progreso"));
        if (ubPeruService.findOne(2014) == null)
            ubPeruService.save(new UbPeru("221003", "San Martín", "Tocache", "Polvora"));
        if (ubPeruService.findOne(2015) == null)
            ubPeruService.save(new UbPeru("221004", "San Martín", "Tocache", "Shunte"));
        if (ubPeruService.findOne(2016) == null)
            ubPeruService.save(new UbPeru("221005", "San Martín", "Tocache", "Uchiza"));
        if (ubPeruService.findOne(2017) == null) ubPeruService.save(new UbPeru("230000", "Tacna", "", ""));
        if (ubPeruService.findOne(2018) == null) ubPeruService.save(new UbPeru("230100", "Tacna", "Tacna", ""));
        if (ubPeruService.findOne(2019) == null) ubPeruService.save(new UbPeru("230101", "Tacna", "Tacna", "Tacna"));
        if (ubPeruService.findOne(2020) == null)
            ubPeruService.save(new UbPeru("230102", "Tacna", "Tacna", "Alto de la Alianza"));
        if (ubPeruService.findOne(2021) == null) ubPeruService.save(new UbPeru("230103", "Tacna", "Tacna", "Calana"));
        if (ubPeruService.findOne(2022) == null)
            ubPeruService.save(new UbPeru("230104", "Tacna", "Tacna", "Ciudad Nueva"));
        if (ubPeruService.findOne(2023) == null) ubPeruService.save(new UbPeru("230105", "Tacna", "Tacna", "Inclan"));
        if (ubPeruService.findOne(2024) == null) ubPeruService.save(new UbPeru("230106", "Tacna", "Tacna", "Pachia"));
        if (ubPeruService.findOne(2025) == null) ubPeruService.save(new UbPeru("230107", "Tacna", "Tacna", "Palca"));
        if (ubPeruService.findOne(2026) == null) ubPeruService.save(new UbPeru("230108", "Tacna", "Tacna", "Pocollay"));
        if (ubPeruService.findOne(2027) == null) ubPeruService.save(new UbPeru("230109", "Tacna", "Tacna", "Sama"));
        if (ubPeruService.findOne(2028) == null)
            ubPeruService.save(new UbPeru("230110", "Tacna", "Tacna", "Coronel Gregorio Albarracín Lanchipa"));
        if (ubPeruService.findOne(2029) == null)
            ubPeruService.save(new UbPeru("230111", "Tacna", "Tacna", "La Yarada los Palos"));
        if (ubPeruService.findOne(2030) == null) ubPeruService.save(new UbPeru("230200", "Tacna", "Candarave", ""));
        if (ubPeruService.findOne(2031) == null)
            ubPeruService.save(new UbPeru("230201", "Tacna", "Candarave", "Candarave"));
        if (ubPeruService.findOne(2032) == null)
            ubPeruService.save(new UbPeru("230202", "Tacna", "Candarave", "Cairani"));
        if (ubPeruService.findOne(2033) == null)
            ubPeruService.save(new UbPeru("230203", "Tacna", "Candarave", "Camilaca"));
        if (ubPeruService.findOne(2034) == null)
            ubPeruService.save(new UbPeru("230204", "Tacna", "Candarave", "Curibaya"));
        if (ubPeruService.findOne(2035) == null)
            ubPeruService.save(new UbPeru("230205", "Tacna", "Candarave", "Huanuara"));
        if (ubPeruService.findOne(2036) == null)
            ubPeruService.save(new UbPeru("230206", "Tacna", "Candarave", "Quilahuani"));
        if (ubPeruService.findOne(2037) == null) ubPeruService.save(new UbPeru("230300", "Tacna", "Jorge Basadre", ""));
        if (ubPeruService.findOne(2038) == null)
            ubPeruService.save(new UbPeru("230301", "Tacna", "Jorge Basadre", "Locumba"));
        if (ubPeruService.findOne(2039) == null)
            ubPeruService.save(new UbPeru("230302", "Tacna", "Jorge Basadre", "Ilabaya"));
        if (ubPeruService.findOne(2040) == null)
            ubPeruService.save(new UbPeru("230303", "Tacna", "Jorge Basadre", "Ite"));
        if (ubPeruService.findOne(2041) == null) ubPeruService.save(new UbPeru("230400", "Tacna", "Tarata", ""));
        if (ubPeruService.findOne(2042) == null) ubPeruService.save(new UbPeru("230401", "Tacna", "Tarata", "Tarata"));
        if (ubPeruService.findOne(2043) == null)
            ubPeruService.save(new UbPeru("230402", "Tacna", "Tarata", "Héroes Albarracín"));
        if (ubPeruService.findOne(2044) == null) ubPeruService.save(new UbPeru("230403", "Tacna", "Tarata", "Estique"));
        if (ubPeruService.findOne(2045) == null)
            ubPeruService.save(new UbPeru("230404", "Tacna", "Tarata", "Estique-Pampa"));
        if (ubPeruService.findOne(2046) == null)
            ubPeruService.save(new UbPeru("230405", "Tacna", "Tarata", "Sitajara"));
        if (ubPeruService.findOne(2047) == null)
            ubPeruService.save(new UbPeru("230406", "Tacna", "Tarata", "Susapaya"));
        if (ubPeruService.findOne(2048) == null)
            ubPeruService.save(new UbPeru("230407", "Tacna", "Tarata", "Tarucachi"));
        if (ubPeruService.findOne(2049) == null) ubPeruService.save(new UbPeru("230408", "Tacna", "Tarata", "Ticaco"));
        if (ubPeruService.findOne(2050) == null) ubPeruService.save(new UbPeru("240000", "Tumbes", "", ""));
        if (ubPeruService.findOne(2051) == null) ubPeruService.save(new UbPeru("240100", "Tumbes", "Tumbes", ""));
        if (ubPeruService.findOne(2052) == null) ubPeruService.save(new UbPeru("240101", "Tumbes", "Tumbes", "Tumbes"));
        if (ubPeruService.findOne(2053) == null)
            ubPeruService.save(new UbPeru("240102", "Tumbes", "Tumbes", "Corrales"));
        if (ubPeruService.findOne(2054) == null)
            ubPeruService.save(new UbPeru("240103", "Tumbes", "Tumbes", "La Cruz"));
        if (ubPeruService.findOne(2055) == null)
            ubPeruService.save(new UbPeru("240104", "Tumbes", "Tumbes", "Pampas de Hospital"));
        if (ubPeruService.findOne(2056) == null)
            ubPeruService.save(new UbPeru("240105", "Tumbes", "Tumbes", "San Jacinto"));
        if (ubPeruService.findOne(2057) == null)
            ubPeruService.save(new UbPeru("240106", "Tumbes", "Tumbes", "San Juan de la Virgen"));
        if (ubPeruService.findOne(2058) == null)
            ubPeruService.save(new UbPeru("240200", "Tumbes", "Contralmirante Villar", ""));
        if (ubPeruService.findOne(2059) == null)
            ubPeruService.save(new UbPeru("240201", "Tumbes", "Contralmirante Villar", "Zorritos"));
        if (ubPeruService.findOne(2060) == null)
            ubPeruService.save(new UbPeru("240202", "Tumbes", "Contralmirante Villar", "Casitas"));
        if (ubPeruService.findOne(2061) == null)
            ubPeruService.save(new UbPeru("240203", "Tumbes", "Contralmirante Villar", "Canoas de Punta Sal"));
        if (ubPeruService.findOne(2062) == null) ubPeruService.save(new UbPeru("240300", "Tumbes", "Zarumilla", ""));
        if (ubPeruService.findOne(2063) == null)
            ubPeruService.save(new UbPeru("240301", "Tumbes", "Zarumilla", "Zarumilla"));
        if (ubPeruService.findOne(2064) == null)
            ubPeruService.save(new UbPeru("240302", "Tumbes", "Zarumilla", "Aguas Verdes"));
        if (ubPeruService.findOne(2065) == null)
            ubPeruService.save(new UbPeru("240303", "Tumbes", "Zarumilla", "Matapalo"));
        if (ubPeruService.findOne(2066) == null)
            ubPeruService.save(new UbPeru("240304", "Tumbes", "Zarumilla", "Papayal"));
        if (ubPeruService.findOne(2067) == null) ubPeruService.save(new UbPeru("250000", "Ucayali", "", ""));
        if (ubPeruService.findOne(2068) == null)
            ubPeruService.save(new UbPeru("250100", "Ucayali", "Coronel Portillo", ""));
        if (ubPeruService.findOne(2069) == null)
            ubPeruService.save(new UbPeru("250101", "Ucayali", "Coronel Portillo", "Calleria"));
        if (ubPeruService.findOne(2070) == null)
            ubPeruService.save(new UbPeru("250102", "Ucayali", "Coronel Portillo", "Campoverde"));
        if (ubPeruService.findOne(2071) == null)
            ubPeruService.save(new UbPeru("250103", "Ucayali", "Coronel Portillo", "Iparia"));
        if (ubPeruService.findOne(2072) == null)
            ubPeruService.save(new UbPeru("250104", "Ucayali", "Coronel Portillo", "Masisea"));
        if (ubPeruService.findOne(2073) == null)
            ubPeruService.save(new UbPeru("250105", "Ucayali", "Coronel Portillo", "Yarinacocha"));
        if (ubPeruService.findOne(2074) == null)
            ubPeruService.save(new UbPeru("250106", "Ucayali", "Coronel Portillo", "Nueva Requena"));
        if (ubPeruService.findOne(2075) == null)
            ubPeruService.save(new UbPeru("250107", "Ucayali", "Coronel Portillo", "Manantay"));
        if (ubPeruService.findOne(2076) == null) ubPeruService.save(new UbPeru("250200", "Ucayali", "Atalaya", ""));
        if (ubPeruService.findOne(2077) == null)
            ubPeruService.save(new UbPeru("250201", "Ucayali", "Atalaya", "Raymondi"));
        if (ubPeruService.findOne(2078) == null)
            ubPeruService.save(new UbPeru("250202", "Ucayali", "Atalaya", "Sepahua"));
        if (ubPeruService.findOne(2079) == null)
            ubPeruService.save(new UbPeru("250203", "Ucayali", "Atalaya", "Tahuania"));
        if (ubPeruService.findOne(2080) == null)
            ubPeruService.save(new UbPeru("250204", "Ucayali", "Atalaya", "Yurua"));
        if (ubPeruService.findOne(2081) == null) ubPeruService.save(new UbPeru("250300", "Ucayali", "Padre Abad", ""));
        if (ubPeruService.findOne(2082) == null)
            ubPeruService.save(new UbPeru("250301", "Ucayali", "Padre Abad", "Padre Abad"));
        if (ubPeruService.findOne(2083) == null)
            ubPeruService.save(new UbPeru("250302", "Ucayali", "Padre Abad", "Irazola"));
        if (ubPeruService.findOne(2084) == null)
            ubPeruService.save(new UbPeru("250303", "Ucayali", "Padre Abad", "Curimana"));
        if (ubPeruService.findOne(2085) == null)
            ubPeruService.save(new UbPeru("250304", "Ucayali", "Padre Abad", "Neshuya"));
        if (ubPeruService.findOne(2086) == null)
            ubPeruService.save(new UbPeru("250305", "Ucayali", "Padre Abad", "Alexander Von Humboldt"));
        if (ubPeruService.findOne(2087) == null) ubPeruService.save(new UbPeru("250400", "Ucayali", "Purús", ""));
        if (ubPeruService.findOne(2088) == null) ubPeruService.save(new UbPeru("250401", "Ucayali", "Purús", "Purus"));
    }
}
