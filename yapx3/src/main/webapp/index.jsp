<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table, td, th{
	border: 1px solid;
}
.allChampion{
	padding: 4px;
}
#championAll{
	width: 800px;
}
</style>
</head>
<body>
<!-- 소환사 정보 -->
	<input type="text" name="summonerName" id="summonerName" />
	<button id="searchBtn">소환사검색</button>
	
	<div id="summonerInfo">
		<table id="summonerInfoResult">
			<tr>
				<th>닉네임</th>
				<th>소환사 레벨</th>
			</tr>
		</table>
	</div>
	<input type="text" name="searchMastery" id="searchMastery" />
	<button id="searchMasteryBtn">소환사검색(championMastery)</button>
	<div id="MasteryInfo">
		<table id="searchMasteryInfoResult">
			<tr>
				<th>챔피언</th>
				<th>숙련도 포인트</th>
			</tr>
		</table>
	</div>
<!-- 이번주 챔피언 로테이션 -->
	<input type="text" name="championLotation" id="championLotation" />
	<button id="championBtn">이번주 로테이션 챔피언 보기</button>
	<div id="championInfo">
		<table id="championTable">
			<tr>
				<th>번호</th>
			</tr>
		</table>
	</div>
	
	<!-- 챔피언 전부 불러오기 -->
	<button id="championAllBtn">모든 챔피언 보기</button>
	<div id = "championAll">
		
	</div>
</body>
<script>
function info(){
	$.ajax({
		url: "${pageContext.request.contextPath}/api/champion/info",
		type: "GET",
		dataType : "json",
		success: function(data){
			console.log(data);
		},
		error: function(err){
			console.log("안됨");
		}
	});
}
	$(()=>{
		$("#championAllBtn").on("click", ()=>{
			$.ajax({
				url: "${pageContext.request.contextPath}/api/allChampion",
				type : "GET",
				dataType : "json",
				success : function(data){
					$.each(data, function(d, id){
						//var html = "<a href='${pageContext.request.contextPath}/api/champion/info?championId='"+id+"'><img class='allChampion' src = 'http://ddragon.leagueoflegends.com/cdn/9.18.1/img/champion/"+id+".png'/></a>";
						var html = "<img class='allChampion' onclick='info()' id="+ id +" src = 'http://ddragon.leagueoflegends.com/cdn/9.18.1/img/champion/"+id+".png'/>";
						$("#championAll").append(html);
					});
				}
			});
			
		});
		
		
		$("#searchBtn").on("click", ()=>{
			var summonerName = $("#summonerName").val();
			
			$.ajax({
				url : "api/search?summonerName="+summonerName,
				//url: "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key=RGAPI-65b2e42a-3890-4260-a232-ddb56b611074",
				type :"GET",
				dataType : "json",
				headers: {
				   'Access-Control-Allow-Credentials' : true,
				   'Access-Control-Allow-Origin':'*',
				   'Access-Control-Allow-Methods':'GET,POST,PUT,DELETE',
				   'Access-Control-Allow-Headers':'application/json', 
				},
				
				success : function(data){
					console.log(data);
					var table = $("#summonerInfoResult");
					var html = "<tr>" +
							   "<td>" + data.name + "</td>"+
							   "<td>" + data.summonerLevel + "</td>"+
							   "</tr>";
					table.append(html);
					$("#summonerInfo").html(table);
				},
				error : function(err){
					console.log("fail");
				}
			});
		});
		
		$("#searchMasteryBtn").on("click", ()=>{
			var searchMastery = $("#searchMastery").val();
			//location.href = "${pageContext.request.contextPath}/api/mastery?searchMastery="+searchMastery;
			$.ajax({
				url: "${pageContext.request.contextPath}/api/mastery?searchMastery="+searchMastery,
				type : "get",
				dataType : "json",
				success : function(data){
					var html = "";
					$("#searchMasteryInfoResult tr:gt(0)").remove();
					var table = $("#searchMasteryInfoResult");
					console.log(data);
					$.each(data, function(img, point){
							html = "<tr>" +
								    "<td style='text-align: center;'><img src='http://ddragon.leagueoflegends.com/cdn/9.18.1/img/champion/" + img +"'/></td>" +
								    "<td>" + point + "</td>" +
								    "</tr>";
							table.append(html);
						}); 
					$("#MasteryInfo").html(table);
				}
			})
		});
		
		$("#championBtn").on("click", ()=>{
			
			$.ajax({
				url: "${pageContext.request.contextPath}/api/champion",
				type : "get",
				dataType : "json",
				success : function(data){
					var table = $("#championTable");
					$.each(data, function(i, c){
					$("#championTable").append("<img src='http://ddragon.leagueoflegends.com/cdn/9.18.1/img/champion/"+ c +"'/>");
						var html = "<tr>" +
								   "</tr>";
						table.append(html);
					}); 
					$("#championInfo").html(table);
				},
				error : function(err){
					console.log("ajax호출 실패");
				}
			});
		});
	});
</script>
</html>