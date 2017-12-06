<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<meta charset="EUC-KR">
	<title> 상품 목록조회</title>
	
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		
		function fncGetUserList(currentPage) {
			//document.getElementById("currentPage").value = currentPage;
			$("#currentPage").val(currentPage)
		   	//document.detailForm.submit();
			$("form").attr("method","POST").attr("action","/product/listProduct?menu=${menu}").submit();
		}
		
		$(function(){
			
			//var index = $(this).index().val();
			
			$("td.ct_btn01:contains('검색')").on("click",function(){
				fncGetUserList(1);
			});
			
			$("td.ct_list_b:contains('No')").bind("click",function(){
				self.location = "/product/listProduct?sort=no&menu=${menu }&order=${search.order}"
			})
			
			$("td.ct_list_b:contains('상품명')").bind("click",function(){
				self.location = "/product/listProduct?sort=prodName&menu=${menu }&order=${search.order}"
			})
			
			$("td.ct_list_b:contains('가격')").bind("click",function(){
				self.location = "/product/listProduct?sort=price&menu=${menu }&order=${search.order}"
			})
			
			$("td.ct_list_b:contains('제조일')").bind("click",function(){
				self.location = "/product/listProduct?sort=manuDate&menu=${menu }&order=${search.order}"
			})
			
			$("td.ct_list_b:contains('현재상태')").bind("click",function(){
				self.location = "/product/listProduct?sort=tranCode&menu=${menu }&order=${search.order}"
			})
			
			
			$(".ct_list_pop td:nth-child(3)").bind("click",function(){
				
				self.location = "/product/getProduct?prodNo="+$($("input[name='prodNo']")[$(".ct_list_pop td:nth-child(3)").index(this)]).val()+"&menu=${menu}";
			});
			
			$(".ct_list_pop td:nth-child(9)").bind("click",function(){
				
				self.location ="/purchase/updateTranCode?prodNo="+$($("input[name='prodNo']")[$(".ct_list_pop td:nth-child(9)").index(this)]).val()
						+"&proTranCode="+$($("input[name='tranCode']")[$(".ct_list_pop td:nth-child(9)").index(this)]).val()+"&menu=manage";
			})
					
						
		});
		
		function locProduct(){
			$("form").attr("method","GET").attr("action","/product/updateProduct?prodNo="+$($("input[name='prodNo']")[$(".ct_list_pop td:nth-child(3)").index(this)]).val()+"&menu=+${menu}").submit();
		}
	
	
	</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<%-- <form name="detailForm" action="/product/listProduct?menu=${menu}" method="post"> --%>

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					${requestScope.menu == 'search' ? "상품목록조회" : "상품관리"}
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>

		<td align="right">
		
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품명</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품가격</option>
			</select>
			
			<input 	type="text" name="searchKeyword" 
			value="${search.searchKeyword}" 
			 class="ct_input_g" style="width:200px; height:20px" >
							
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<!-- <a href="javascript:fncGetUserList('1');">검색</a> -->
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">
		<!-- <a href="/product/listProduct?sort=no&menu=${menu }&order=${search.order} ">No</a> -->
		No
		</td>
		
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">
		<!-- <a href="/product/listProduct?sort=prodName&menu=${menu }&order=${search.order}">상품명</a> -->
		상품명
		</td>
		
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">
		<!-- <a href="/product/listProduct?sort=price&menu=${menu }&order=${search.order}">가격</a> -->
		가격
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">
		<!-- <a href="/product/listProduct?sort=manuDate&menu=${menu }&order=${search.order}">제조일</a> -->
		제조일
		</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">
		<!-- <a href="/product/listProduct?sort=tranCode&menu=${menu }&order=${search.order}">현재상태</a> -->
		현재상태
		</td>	
	</tr>

	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var = "i" value = "0" />
	<c:forEach var = "product" items = "${list}">
		<input type="hidden"name="prodNo" value="${product.prodNo }"/>
		<input type ="hidden" name="tranCode" value="${product.proTranCode.trim() }"/>
		<c:set var = "i" value = "${i+1}" />
		<tr class="ct_list_pop">
		<td align="center">${ i }</td>
			<td></td>
			<td align="left">
				<!--  <c:if test="${empty product.proTranCode }">-->
				<!-- 	<c:if test = "${menu == 'search'}">
						<a href="/product/getProduct?prodNo=${product.prodNo}&menu=search">${product.prodName}</a>
					</c:if>
					<c:if test = "${menu == 'manage'}">
						 <a href="/product/updateProduct?prodNo=${product.prodNo}&menu=manage">${product.prodName}</a>
					</c:if> -->
				<!--</c:if> --> 
				${product.prodName}
				
				<c:if test="${! empty product.proTranCode }">
					${product.prodName}
				</c:if>
		</td>
		
			
			<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.manuDate}</td>
		<td></td>
		<td align="left" id="completePurchase">
			
			<c:if test = "${requestScope.menu == 'search'}">
		  	
			  	<c:if test = "${sessionScope.user.role == 'admin'}">
			  			
					<c:if test = "${empty product.proTranCode }">
						판매중
					</c:if>
					
					<c:if test = "${product.proTranCode.trim() == '01'}">
						구매완료 
					</c:if>
				
					<c:if test = "${product.proTranCode.trim() == '02'}">
						배송중 
					</c:if>
					
					<c:if test = "${product.proTranCode.trim() == '03'}">
						배송완료
					</c:if>
					
				</c:if>
				
			
			
			
				<c:if test = "${sessionScope.user.role == 'user'}">
			  			
					<c:if test = "${empty product.proTranCode }">
						판매중
					</c:if>
					
					<c:if test = "${product.proTranCode.trim() == '01'}">
						재고없음
					</c:if>
					
					<c:if test = "${product.proTranCode.trim() == '02'}">
						재고없음
					</c:if>
					
					<c:if test = "${product.proTranCode.trim() == '03'}">
						재고없음
					</c:if>
				
				</c:if>
				
			</c:if>
		  
		 <!--판매상품관리 ----------------------------------------------------------- -->
			<c:if test = "${requestScope.menu == 'manage'}">
				  
				<c:if test = "${product.proTranCode == null }">
					판매중  
				</c:if>
				
				<c:if test = "${product.proTranCode.trim() == '01' }">	
					<c:if test = "${! empty product.proTranCode}">
						구매완료  
						<!-- <a href="/purchase/updateTranCode?prodNo=${product.prodNo}&proTranCode=${product.proTranCode.trim()}&menu=manage">배송하기</a> -->
						<font id="changeTranCode" color="red">배송하기</font>
					</c:if>
				</c:if>
					  
				<c:if test = "${product.proTranCode.trim() == '02' }">	
					<c:if test = "${! empty product.proTranCode}">
						배송중
					</c:if>
				</c:if>
					  
				<c:if test = "${product.proTranCode.trim() == '03' }">	
					<c:if test = "${! empty product.proTranCode}">
						배송완료  
					</c:if>
				</c:if>
					  
			</c:if>
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   <input type="hidden" name="menu" value="${requestScope.menu}"/>
		  <jsp:include page="../common/pageNavigator.jsp"/>	
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>