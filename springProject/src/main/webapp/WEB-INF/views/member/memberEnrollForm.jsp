<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	 <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/header.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="insert.me" method="post" id="enrollForm">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="Please Enter ID" required>
                    <div id="checkResult" style="font-size:0.8em; display:none"></div>
                    <br>
                    
                    
                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Please Enter Age"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger"> 초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>
    <script>
    	$(function(){
    		//아이디 입력받는 input요소객체 변수에 담아두기(자주접근할것)
    		const $idInput = $("#enrollForm input[name=userId]");
    		
    		$idInput.keyup(function(){
    			//최소 5글자 이상으로 입력되어있을때만 ajax요청해서 중복체크
    			if($idInput.val().length >= 5) {
    				$.ajax({
    					url:"idCheck.me",
    					data:{
    						checkId:$idInput.val()
    					},success: function(result){
    						console.log($idInput.val());
    						if(result=="NNNNN"){ //사용불가
    							$('#checkResult').show();
    							$('#checkResult').css("color","red").text("중복된 아이디입니다.");
    							$('#enrollForm :submit').attr("disabled", true);
    						}else if(result="NNNNY"){ //사용가능
    							$('#checkResult').show();
    							$('#checkResult').css("color","green").text("멋진 아이디네요!");
    							$('#enrollForm :submit').removeAttr("disabled");
    						}
    					},error: function(){
    						console.log('id중복체크용 ajax통신실패');
    					}
    					
    				});
    			}else { //5글자 미만 => 버튼 비활성화, 메세지 숨기기 
					$('#checkResult').hide();
    				$('#enrollForm :submit').attr("disabled", true);
    			}

    		})
    		
    	})
    </script>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>

</body>
</html>