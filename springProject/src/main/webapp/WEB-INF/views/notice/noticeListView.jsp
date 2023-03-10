<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    #boardList {
        text-align: center;
    }

    #boardList>tbody>tr:hover {
        cursor: pointer;
    }

    #pagingArea {
        width: fit-content;
        margin: auto;
    }

    #searchForm {
        width: 80%;
        margin: auto;
    }

    #searchForm>* {
        float: left;
        margin: 5px;
    }

    .select {
        width: 20%;
    }

    .text {
        width: 53%;
    }

    .searchBtn {
        Width: 20%;
    }
</style>
</head>
<body>
    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/header.jsp" />
    
    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>공지사항</h2>
            <br>
            <!-- 글쓰기는 관리자만 볼 수 있게 -->
            <c:if test="${ loginUser.userId eq 'admin' }">
                <a class="btn btn-secondary" style="float:right" href="enrollForm.no">글쓰기</a>
                <br>
            </c:if>
    
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="n" items="${ list }">
                        <tr>
                            <td class="nNo">${ n.noticeNo }</td>
                            <td>${ n.noticeTitle }</td>
                            <td>${ n.noticeWriter }</td>
                            <td>${ n.createDate }</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <script>
                $(function () {
                    $("#boardList>tbody>tr").click(function () {
                        location.href = 'detail.no?no=' + $(this).children(".nNo").text();
                    })
                })
            </script>
    
            <br>
    
            <div id="pagingArea">
                <ul class="pagination">
    
                    <c:choose>
                        <c:when test="${ pi.currentPage eq 1 }">
                            <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                    href="list.no?cpage=${ pi.currentPage-1 }">Previous</a></li>
                        </c:otherwise>
                    </c:choose>
    
                    <c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
                        <li class="page-item"><a class="page-link" href="list.no?cpage=${ p }">${ p }</a></li>
                    </c:forEach>
    
                    <c:choose>
                        <c:when test="${ pi.currentPage eq pi.maxPage }">
                            <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="list.no?cpage=${ pi.currentPage+1 }">Next</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
    
            <br clear="both"><br>
    

        </div>
        <br><br>
    </div>

    <jsp:include page="../common/footer.jsp" />
</body>
</html>