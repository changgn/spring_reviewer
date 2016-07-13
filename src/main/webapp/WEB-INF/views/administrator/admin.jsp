<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
   <head>
      <style>
         #BoardManagement{margin-top: 150px;padding-top: 30px; padding-bottom: 30px; cursor:pointer;}
         #MemberManagement{margin-top: 20px; padding-top: 30px;padding-bottom: 30px; cursor:pointer;}
         #name{font-family: '나눔고딕', 'Nanum Gothic', sans-serif;   font: 23px '나눔고딕', 'Nanum Gothic', sans-serif;   color: #828282;}
         .Title{
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
		$("#BoardManagement").mouseover(function(){
			$(this).css("background-color", '#EAEAEA');
		});
		$("#BoardManagement").mouseout(function(){
			$(this).css("background-color", '#FFFFFF');
		})
		$("#MemberManagement").mouseover(function(){
			$(this).css("background-color", '#EAEAEA');
		});
		$("#MemberManagement").mouseout(function(){
			$(this).css("background-color", '#FFFFFF');
		})
      });
      $(function(){
    	$("#BoardManagement").click(function(){
  			$(location).attr("href", "/administrator/adminBoard.do");
  		}); 
    	$("#MemberManagement").click(function(){
    		$(location).attr("href", "/administrator/adminMem.do");
    	});
      });
      </script>
   </head>
   
   <body>
      <div id="adminMain" align="center">
         <div id="BoardManagement" class="Title">
               	게시글 관리  (${listcount})
         </div>
         <div id="MemberManagement" class="Title">
              	 회원 관리 (${count})
         </div>
      </div>
   </body>
</html>