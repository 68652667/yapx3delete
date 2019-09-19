package com.kh.yapx3.champion.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.kh.yapx3.common.URLConnection;

@Controller
@RequestMapping("/api")
public class TestAPI{

	URLConnection connection; 
	Logger logger = LoggerFactory.getLogger(getClass());
	private String encrypteSummonerName;
	
	
	
	@RequestMapping("/champion/info")
	public void championInf(HttpServletRequest request, HttpServletResponse response, Model model) {
		connection = new URLConnection();
		try {
			//한 게임의 match정보
			JSONObject mathch = connection.matchGame();
			
			//총 실행된 게임의 정보(게임 아이디 추출)
			JSONObject matchAllId = connection.matchGameSearch();
			JSONArray matchIdArr = matchAllId.getJSONArray("matches");
			
			for(int i = 0; i < matchIdArr.length(); i++) {
				System.out.println(matchIdArr.getJSONObject(i).get("gameId").toString());
			}
			
//			JSONArray participants = mathch.
//			for(int i = 0; ;i++ ) {
//				
//			}
			
			
			
//			JSONArray participants = mathch.getJSONArray("participants");
//			List<String> itemlist = new ArrayList<String>();
//			for(int i = 0; i<participants.length(); i++) {
//				System.out.println(participants.getJSONObject(i).get("championId"));//챔피언 번호
//				if(!participants.getJSONObject(i).getJSONObject("stats").has("message")) {
//					System.out.println("없음");
//				}
//				for(int j = 0; j < 7; j ++) {
//					System.out.println("item번호" + j + ": " + participants.getJSONObject(i).getJSONObject("stats").getInt("item"+j));
//				}
//			}
			
			new Gson().toJson(mathch, response.getWriter());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	@RequestMapping("/mastery")
	public void chMastery(HttpServletRequest request, HttpServletResponse response, Model model) {
		String searchMastery = request.getParameter("searchMastery");
		
		try {
			//url연결
			connection = new URLConnection();
			System.out.println(searchMastery);
			JSONObject jobj = connection.searhName(searchMastery);
			
			//암호화된 소환사 닉네임
			encrypteSummonerName =  (String) jobj.get("id");
            System.out.println(jobj);
            
            //모든 챔피언 가져오기
            JSONObject championJobj = connection.championData();
            JSONObject championKeyJobj = (JSONObject) championJobj.get("data");
            
			JSONArray jarray = connection.chMatery(encrypteSummonerName);
			
			
			Map<String, String> map = new LinkedHashMap<String, String>();
			
			Iterator iter = (Iterator) championKeyJobj.keys();
			
			while(iter.hasNext()) {
				String dataKey = iter.next().toString();
				JSONObject data = championKeyJobj.getJSONObject(dataKey);
				JSONObject data_ = data.getJSONObject("image");
				String img = data_.getString("full");
				String key = data.getString("key");
				
				for(int i = 0 ; i < jarray.length(); i++) {
	                if(key.equals(jarray.getJSONObject(i).get("championId").toString())) {	                	
	                	map.put(img, jarray.getJSONObject(i).get("championPoints").toString());
	                }
				}
			}
			response.setCharacterEncoding("utf-8");
			new Gson().toJson(map, response.getWriter());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/allChampion")
	public void championAll(HttpServletResponse response) {
		
		try {
			connection = new URLConnection();
			JSONObject championAllObj = connection.championData();
			JSONObject championAllObjKey = (JSONObject) championAllObj.get("data");
			
			Iterator iter = championAllObjKey.keys();
			List<String> list = new ArrayList<String>();
			while(iter.hasNext()) {
				String dataKey = iter.next().toString();
				JSONObject champion = (JSONObject) championAllObjKey.get(dataKey);
				String championName = champion.getString("name");
				list.add(championName);
			}
			Collections.sort(list);
			System.out.println(list);
			
			List<String> list1 = new ArrayList<String>();
			for(int i = 0; i < list.size(); i++) {
				Iterator iter2 = championAllObjKey.keys();
				while(iter2.hasNext()) {
					String dataKey1 = iter2.next().toString();
					JSONObject champion1 = (JSONObject) championAllObjKey.get(dataKey1);
					String championId = champion1.getString("id");
					String championName = champion1.getString("name");
					
					if(championName.equals(list.get(i))) {
						list1.add(championId);
						System.out.print(championId);
					}else {
						continue;
					}
					
				}
			}
			
			new Gson().toJson(list1, response.getWriter());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/search")
	public void apiTest(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("test");
		
		String searchName = request.getParameter("summonerName");
		try {
			connection = new URLConnection();
			JSONObject jobj = connection.searhName(searchName);
			
			Map<String, String> map = new HashMap<>();
			System.out.println(jobj.get(("summonerLevel")));
			map.put("name", jobj.getString("name"));
			map.put("summonerLevel", jobj.get(("summonerLevel")).toString());
			
			
			response.setCharacterEncoding("utf-8");
			new Gson().toJson(map, response.getWriter());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/champion")
	public void champion(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(encrypteSummonerName);
		try {
			connection = new URLConnection();
			
			JSONObject jobj = connection.lotation();
			JSONArray jar = (JSONArray) jobj.get("freeChampionIds");
			
			JSONObject jobj2 = connection.championData();
            JSONObject dataObject = jobj2.getJSONObject("data");
            
            Iterator num = dataObject.keys();
            
            List<String> list = new ArrayList<String>();
            
            
            while(num.hasNext()) {
                String dataKey = num.next().toString();
                JSONObject data = dataObject.getJSONObject(dataKey);
                JSONObject data_ = data.getJSONObject("image");
                String img = data_.getString("full");
                String key = data.getString("key");
                for(int i = 0; i<jar.length(); i++) {
                    String chap = jar.get(i).toString();
                    if(key.equals(chap)) {
                        list.add(img);
                    }
                }
            }
            
            
            response.setCharacterEncoding("utf-8");
            
            new Gson().toJson(list, response.getWriter());
            
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
