package com.yst.onecity.utils;


import com.yst.onecity.bean.CityModel;
import com.yst.onecity.bean.DistrictModel;
import com.yst.onecity.bean.ProvinceModel;
import com.yst.onecity.config.Const;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * xml解析工具类
 *
 * @author lixiangchao
 * @version 3.2.1
 * @date 2017/9/21.
 */
public class XmlParserHandler extends DefaultHandler {

	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();
	 	  
	public XmlParserHandler() {
		
	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
	}

	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (Const.CONS_STR_PROVINCE.equals(qName)) {
			provinceModel = new ProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setCityList(new ArrayList<CityModel>());
		} else if (Const.CONS_STR_CITY.equals(qName)) {
			cityModel = new CityModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setDistrictList(new ArrayList<DistrictModel>());
		} else if (Const.CONS_STR_DISTRICT.equals(qName)) {
			districtModel = new DistrictModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setZipcode(attributes.getValue(1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (Const.CONS_STR_DISTRICT.equals(qName)) {
			cityModel.getDistrictList().add(districtModel);
        } else if (Const.CONS_STR_CITY.equals(qName)) {
        	provinceModel.getCityList().add(cityModel);
        } else if (Const.CONS_STR_PROVINCE.equals(qName)) {
        	provinceList.add(provinceModel);
        }
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
