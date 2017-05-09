<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="csu.lzw.lmediaserver.pojo.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<html lang="en">

<head>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
   <meta name="description" content="Bootstrap Admin App + jQuery">
   <meta name="keywords" content="app, responsive, jquery, bootstrap, dashboard, admin">
   <title>L多媒体管理系统</title>
   <!-- =============== VENDOR STYLES ===============-->
   <!-- FONT AWESOME-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/fontawesome/css/font-awesome.min.css">
   <!-- SIMPLE LINE ICONS-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/simple-line-icons/css/simple-line-icons.css">
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/app.css" id="maincss">
</head>

<body>
   <div class="wrapper">
      <div class="block-center mt-xl wd-xl">
         <!-- START panel-->
         <div class="panel panel-dark panel-flat">
            <div class="panel-heading text-center">
               <a href="#">
                  <img src="<%=request.getContextPath()%>/app/img/logo.png" alt="Image" class="block-center img-rounded">
               </a>
            </div>
            <div class="panel-body">
               <p class="text-center pv">管理员登录</p>
               <form id="loginForm" role="form" data-parsley-validate="" novalidate="" class="mb-lg" method="POST" action="<%=request.getContextPath()%>/admins/login">
                  <div class="form-group has-feedback">
                     <input id="accountInput" name="account" placeholder="Enter account" autocomplete="off" required class="form-control">
                     <span class="fa fa-user form-control-feedback text-muted"></span>
                  </div>
                  <div class="form-group has-feedback">
                     <input id="passwordInput" name="password" type="password" placeholder="Enter Password" required class="form-control">
                     <span class="fa fa-lock form-control-feedback text-muted"></span>
                  </div>
                  <input name="encyptPassword" type="hidden" id="encyptPasswordInput">
                  <button class="btn btn-block btn-primary mt-lg" onclick="OnSubmit()">登录</button>
               </form>
            </div>
         </div>
         <!-- END panel-->
         <div class="p-lg text-center">
            <span>&copy;</span>
            <span>2017</span>
            <span>-</span>
            <span>Allenzwli</span>
            <br>
            <span>LMediaServer</span>
         </div>
      </div>
   </div>
   <!-- =============== VENDOR SCRIPTS ===============-->
   <!-- MODERNIZR-->
   <script src="<%=request.getContextPath()%>/vendor/modernizr/modernizr.custom.js"></script>
   <!-- JQUERY-->
   <script src="<%=request.getContextPath()%>/vendor/jquery/dist/jquery.js"></script>
   <!-- BOOTSTRAP-->
   <script src="<%=request.getContextPath()%>/vendor/bootstrap/dist/js/bootstrap.js"></script>
   <!-- STORAGE API-->
   <script src="<%=request.getContextPath()%>/vendor/jQuery-Storage-API/jquery.storageapi.js"></script>
   <!-- PARSLEY-->
   <script src="<%=request.getContextPath()%>/vendor/parsleyjs/dist/parsley.min.js"></script>
   <!--md5-->
   <script src="<%=request.getContextPath()%>/vendor/md5/md5.js"></script>
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<%=request.getContextPath()%>/app/js/app.js"></script>

   <script>
       function OnSubmit() {
           $("#encyptPasswordInput").val(hex_md5($("#passwordInput").val()).toUpperCase());
           $("#loginForm").submit();
       }
   </script>
</body>

</html>