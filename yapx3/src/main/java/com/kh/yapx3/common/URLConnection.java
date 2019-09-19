package com.kh.yapx3.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class URLConnection {

	private String urlStr = "";
	public static String api = "?api_key=RGAPI-65b2e42a-3890-4260-a232-ddb56b611074";
	
	//반환값이 object일때({} -> 이걸로 시작할때)
	public JSONObject urlInput(String urlStr) throws IOException {

		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		String sb = br.readLine();
		JSONObject jobj = new JSONObject(sb.toString());
		
		return jobj;
	}
	
	//반환값이 Array일때([] -> 이걸로 시작할때)
	public JSONArray urlInputArray(String urlStr) throws IOException {

		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
		String sb = br.readLine();
		JSONArray jobj = new JSONArray(sb.toString());
		
		return jobj;
	}
	
	//Match 게임 검색
	public JSONObject matchGameSearch() throws IOException{
		urlStr = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/8KKjkdbSMbdI_c7PMarrf7OVKZC2E_83fqZwwJooX13O?api_key=RGAPI-65b2e42a-3890-4260-a232-ddb56b611074";
		JSONObject jobj = urlInput(urlStr);
		return jobj;
	}
	
	//Match
	public JSONObject matchGame() throws IOException{
		urlStr = "https://kr.api.riotgames.com/lol/match/v4/matches/3838862365?api_key=RGAPI-65b2e42a-3890-4260-a232-ddb56b611074";
		JSONObject jobj = urlInput(urlStr);
		return jobj;
	}
	
	//챔피언 마스터리 url
	public JSONArray chMatery(String encrypteSummoonerName) throws IOException {
		urlStr = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"+encrypteSummoonerName + api ;
		JSONArray jarray = urlInputArray(urlStr);
		return jarray; 
	}
	
	//소환사 검색 url
	public JSONObject searhName(String searchName) throws IOException{
		urlStr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+searchName + api;
		JSONObject jobj = urlInput(urlStr);
		return jobj;
	}
	
	//이번주 로테이션 url
	public JSONObject lotation() throws IOException{
		urlStr = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations"+api;
		JSONObject jobj = urlInput(urlStr);
		return jobj;
	}

	//챔피언 데이터 url
	public JSONObject championData() throws IOException{
		urlStr = "http://ddragon.leagueoflegends.com/cdn/9.18.1/data/ko_KR/champion.json";
		JSONObject jobj = urlInput(urlStr);
		return jobj;
	}
}
