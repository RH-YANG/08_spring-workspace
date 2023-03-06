<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table *{margin:5px;}
    table{width:100%;}
</style>
</head>
<body>
    <jsp:include page="../common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>공지사항 상세보기</h2>
            <br>
            
            <a class="btn btn-secondary" style="float:right" href="list.no">목록으로</a>
            <br><br>
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${ n.noticeTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${ n.noticeWriter }</td>
                    <th>작성일</th>
                    <td>${ n.createDate }</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px">${ n.noticeContent }</p></td>
                </tr>
            </table>
            <br>

			
            <c:if test="${ loginUser.userId eq n.noticeWriter }">
	            <div align="center">
	                <a class="btn btn-primary" onclick="postFormSubmit(1);">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2);">삭제하기</a>
	            </div><br><br>
	            
	            <form action="" method="post" id="postForm">
	            	<input type="hidden" name="no" value="${ n.noticeNo }">
	            </form>
	            
	            <script>
	            	function postFormSubmit(num){
	            		if(num == 1){ // 수정하기클릭시
	            			$("#postForm").attr("action", "updateForm.no").submit();
	            		}else{ // 삭제하기클릭시
	            			$("#postForm").attr("action", "delete.no").submit();
	            		}
	            	}
	            </script>
	            
            </c:if>

        </div>
        <br><br>
    </div>
    
  

    <jsp:include page="../common/footer.jsp"/>
</body>
</html>