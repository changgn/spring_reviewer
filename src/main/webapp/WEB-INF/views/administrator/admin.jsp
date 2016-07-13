<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
   <head>
      <style>
         #allBoardManagement{margin-top: 150px;padding-top: 30px; padding-bottom: 30px;}
         #allMemberManagement{margin-top: 20px;     padding-top: 30px;padding-bottom: 30px;}
         #name{font-family: '나눔고딕', 'Nanum Gothic', sans-serif;   font: 23px '나눔고딕', 'Nanum Gothic', sans-serif;   color: #828282;}
         .MemberManageTitle{
            text-align: center;
            font-size: 20px;
            border: 1px solid;
            border-color: #d5d5d5;
            padding: 20px;
            
            color: #4c4c4c;
            width: 37%;   
            height: auto;
            margin: 50 auto;
         }

      </style>
      <script>
      $(document).ready(function(){
         $("#allBoardManagement").hover(function(){
            $("#allBoardManagement").css("background-color","#f6f6f6");
            $("#allBoardManagement").css("color","white");
         }, function(){
            $("#allBoardManagement").css("background-color","white");
            });            
      });
      $(document).ready(function(){
      $("#allMemberManagement").hover(function(){
            $("#allMemberManagement").css("background-color","#f6f6f6");
         
         }, function(){
            $("#allMemberManagement").css("background-color","white");
            });
         });
      $(function(){
    	$(".adminMain").on("click", ".MemberManageTitle", function(){
  			$(location).attr("href", "/administrator/adminBoard.do");
  		}); 
      });
      </script>
   </head>
   
   <body>
      <div id="adminMain" align="center">
         <div id="allBoardManagement" class="MemberManageTitle">
            <a id="name" href="/administrator/adminBoard.do" >
               	게시글 관리  (${listcount})
            </a>
         </div>
         <div id="allMemberManagement" class="MemberManageTitle">
            <a id="name" href="/administrator/adminMem.do">
              	 회원 관리 (${count})
            </a>
         </div>
      </div>
   </body>
</html>