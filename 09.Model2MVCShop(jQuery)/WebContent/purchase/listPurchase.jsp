<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<script type="text/javascript">

	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
	
	$(function(){
		

		$(".ct_list_pop td:nth-child(1)").bind("click",function(){
			
			self.location = "/purchase/getPurchase?tranNo="+$(this).text().trim();
		}).css("color","red")
		
		$(".ct_list_pop td:nth-child(3)").bind("click",function(){
			self.location = "/user/getUser?userId="+$(this).text().trim();
		}).css("color","blue")
		
		$(".ct_list_pop td:nth-child(11)").bind("click",function(){
			
			var index = $(".ct_list_pop td:nth-child(11)").index(this);
						
			self.location ="/purchase/updateTranCode?prodNo="+$($("input[name='prodNo']")[$(".ct_list_pop td:nth-child(11)").index(this)]).val()
			+"&proTranCode="+$($("input[name='tranCode']")[$(".ct_list_pop td:nth-child(11)").index(this)]).val()+"&menu=${menu}&page=1"
			
			
		})
				
	});
	
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listPurchase" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회 ${menu} </td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<c:set var = "i" value = "0"></c:set>
	<c:forEach var = "purchase" items = "${list }">
		<input type="hidden" name="tranNo" value="${product.proTranCode.trim() }">
		<input type="hidden" name="tranCode" value="${purchase.tranCode.trim() }">
		<input type="hidden" name="prodNo" value="${purchase.purchaseProd.prodNo }">
		<c:set var = "i" value = "${i+1 }" />
	
	
		<tr class="ct_list_pop">
			<td align="center">${purchase.tranNo } </td>
			<td></td>
			<td align="left">
				<!-- <a href="/user/getUser?userId=${purchase.buyer.userId }">${purchase.buyer.userId }</a> -->
				${purchase.buyer.userId }
			</td>
			<td></td>
			<td align="left">${purchase.receiverName }</td>
			<td></td>
			<td align="left">${purchase.receiverPhone }</td>
			<td></td>
			<td align="left">
				
				<c:if test = "${purchase.tranCode.trim().equals('01')}">
					현재 구매 완료 상태입니다.
				</c:if>
				
				<c:if test = "${purchase.tranCode.trim().equals('02')}">
					현재 배송 중입니다.
				</c:if>
				
				<c:if test = "${purchase.tranCode.trim().equals('03')}">
					현재 배송 완료 상태 입니다. 
				</c:if>		
	
			</td>
			<td></td>
			<td align="left">
				<c:if test= "${purchase.tranCode.trim().equals('02')}">
					<!-- <a href="/purchase/updateTranCode?prodNo=${purchase.purchaseProd.prodNo}&proTranCode=${purchase.tranCode.trim()}&menu=${requestScope.menu}&page=1">물품도착</a> -->
					<font color="red">물품도착</font>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	
	</c:forEach>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
			 
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   <input type="hidden" id="menu" name="menu" value="${menu}"/>
		   <jsp:include page="../common/pageNavigator.jsp"/>	
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>