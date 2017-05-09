<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="csu.lzw.lmediaserver.pojo.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
   Admin admin= (Admin) session.getAttribute("admin");
%>
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
   <!-- ANIMATE.CSS-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/animate.css/animate.min.css">
   <!-- WHIRL (spinners)-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/whirl/dist/whirl.css">
   <!-- =============== PAGE VENDOR STYLES ===============-->
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/bootstrap.css" id="bscss">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/app.css" id="maincss">
</head>

<body>
   <div class="wrapper">
      <!-- top navbar-->
      <header class="topnavbar-wrapper">
         <!-- START Top Navbar-->
         <nav role="navigation" class="navbar topnavbar">
            <!-- START navbar header-->
            <div class="navbar-header">
               <a href="#/" class="navbar-brand">
                  <div class="brand-logo">
                     <img src="<%=request.getContextPath()%>/app/img/logo.png" alt="App Logo" class="img-responsive">
                  </div>
                  <div class="brand-logo-collapsed">
                     <img src="<%=request.getContextPath()%>/app/img/logo-single.png" alt="App Logo" class="img-responsive">
                  </div>
               </a>
            </div>
            <!-- END navbar header-->
            <!-- START Nav wrapper-->
            <div class="nav-wrapper">
               <!-- START Left navbar-->
               <ul class="nav navbar-nav">
                  <li>
                     <!-- Button used to collapse the left sidebar. Only visible on tablet and desktops-->
                     <a href="#" data-toggle-state="aside-collapsed" class="hidden-xs">
                        <em class="fa fa-navicon"></em>
                     </a>
                     <!-- Button to show/hide the sidebar on mobile. Visible on mobile only.-->
                     <a href="#" data-toggle-state="aside-toggled" data-no-persist="true" class="visible-xs sidebar-toggle">
                        <em class="fa fa-navicon"></em>
                     </a>
                  </li>
               </ul>
               <!-- END Left navbar-->

               <ul class="nav navbar-nav navbar-right">
                  <!-- Search icon-->
                  <li>
                     <a href="<%=request.getContextPath()%>/admins/logout">
                        <em class="fa fa-sign-out"></em>
                     </a>
                  </li>

                  <!-- START Offsidebar button-->
                  <li>
                     <a href="#" data-toggle-state="offsidebar-open" data-no-persist="true">
                        <em class="icon-notebook"></em>
                     </a>
                  </li>
                  <!-- END Offsidebar menu-->
               </ul>
            </div>
            <!-- END Nav wrapper-->
         
         </nav>
         <!-- END Top Navbar-->
      </header>
      <!-- sidebar-->
      <aside class="aside">
         <!-- START Sidebar (left)-->
         <div class="aside-inner">
            <nav data-sidebar-anyclick-close="" class="sidebar">
               <!-- START sidebar nav-->
               <ul class="nav">
                  <!-- Iterates over all sidebar items-->
                  <li class="nav-heading ">
                     <span data-localize="sidebar.heading.HEADER">欢迎 <%=admin.getNickName()%></span>
                  </li>
                  <li class="">
                     <a href="<%=request.getContextPath()%>/admins/main" title="Single View">
                        <em class="fa fa-long-arrow-right"></em>
                        <span data-localize="sidebar.nav.SINGLEVIEW">快捷操作</span>
                     </a>
                  </li>

                  <li class="nav-heading ">
                     <span data-localize="sidebar.heading.HEADER">媒体库管理</span>
                  </li>
                  <li class=" ">
                     <a href="#menuid0" title="Menu" data-toggle="collapse">
                        <em class="fa fa-music"></em>
                        <span data-localize="sidebar.nav.menu.MENU">音频</span>
                     </a>
                     <ul id="menuid0" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">音频</li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/musics/manage" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">音频库管理</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/musics/add" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">新增音频</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#menuid1" title="Menu" data-toggle="collapse">
                        <em class="fa fa-film"></em>
                        <span data-localize="sidebar.nav.menu.MENU">视频</span>
                     </a>
                     <ul id="menuid1" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">视频</li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/videos/manage" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">视频库管理</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/videos/add" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">新增视频</span>
                           </a>
                        </li>
                     </ul>
                  </li>

                  <c:if test="${admin.isSuperAdmin=='1'}">
                     　　<li class="nav-heading ">
                     <span data-localize="sidebar.heading.HEADER">更多</span>
                     </li>
                     <li class=" ">
                        <a href="#menuid2" title="Menu" data-toggle="collapse">
                           <em class="fa fa-user"></em>
                           <span data-localize="sidebar.nav.menu.MENU">账号管理</span>
                        </a>
                        <ul id="menuid2" class="nav sidebar-subnav collapse">
                           <li class="sidebar-subnav-header">账号管理</li>
                           <li class="active">
                              <a href="<%=request.getContextPath()%>/admins/manage" title="Sub Menu">
                                 <span data-localize="sidebar.nav.menu.SUBMENU">管理员账号</span>
                              </a>
                           </li>
                           <li class="">
                              <a href="<%=request.getContextPath()%>/admins/add" title="Sub Menu">
                                 <span data-localize="sidebar.nav.menu.SUBMENU">新增管理员</span>
                              </a>
                           </li>
                        </ul>
                     </li>
                  </c:if>

               </ul>
               <!-- END sidebar nav-->
            </nav>
         </div>
         <!-- END Sidebar (left)-->
      </aside>
      <!-- offsidebar-->
      <aside class="offsidebar hide">
         <!-- START Off Sidebar (right)-->
         <nav>
            <div role="tabpanel">
               <!-- Tab panes-->
               <div class="tab-content">
                  <div id="app-settings" role="tabpanel" class="tab-pane fade in active">
                     <h3 class="text-center text-thin">样式设置</h3>
                     <div class="p">
                        <h4 class="text-muted text-thin">布局</h4>
                        <div class="clearfix">
                           <p class="pull-left">居中</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-boxed" type="checkbox" data-toggle-state="layout-boxed">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                     </div>
                     <div class="p">
                        <h4 class="text-muted text-thin">侧栏</h4>
                        <div class="clearfix">
                           <p class="pull-left">缩略</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-collapsed" type="checkbox" data-toggle-state="aside-collapsed">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">浮动</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-float" type="checkbox" data-toggle-state="aside-float">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                        <div class="clearfix">
                           <p class="pull-left">悬停</p>
                           <div class="pull-right">
                              <label class="switch">
                                 <input id="chk-hover" type="checkbox" data-toggle-state="aside-hover">
                                 <span></span>
                              </label>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </nav>
         <!-- END Off Sidebar (right)-->
      </aside>
      <!-- Main section-->
      <section>
         <!-- Page content-->
         <div class="content-wrapper">
            <h3>权限管理
               <small>管理员账号</small>
            </h3>
            <div class="row">

                  <div class="panel panel-default">
                     <div class="panel-heading">普通管理员列表</div>
                     <div class="panel-body">
                        <!-- START table-responsive-->
                        <div class="table-responsive">
                           <table class="table table-striped table-bordered table-hover">
                              <thead>
                              <tr>
                                 <th>#</th>
                                 <th>账号</th>
                                 <th>昵称</th>
                                 <th>创建时间</th>
                                  <th>授权</th>
                                  <th>删除</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach var="litem" items="${adminList}">
                                <tr>
                                  <td>${litem.id}</td>
                                  <td>${litem.account}</td>
                                  <td>${litem.nickName}</td>
                                  <td>${litem.createTime}</td>
                                  <td><a class="btn btn-primary" href="<%=request.getContextPath()%>/admins/super?id=${litem.id}">授权为超级管理员</a></td>
                                  <td><a class="btn btn-danger" href="<%=request.getContextPath()%>/admins/delete?id=${litem.id}">删除此管理员</a></td>
                                <tr>
                              </c:forEach>
                              </tbody>
                           </table>
                        </div>
                        <!-- END table-responsive-->
                     </div>
                  </div>
         </div>
         </div>


      </section>
      <!-- Page footer-->
      <footer>
         <span>&copy; 2017 - Li Zhaowei</span>
      </footer>
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
   <!-- JQUERY EASING-->
   <script src="<%=request.getContextPath()%>/vendor/jquery.easing/js/jquery.easing.js"></script>
   <!-- ANIMO-->
   <script src="<%=request.getContextPath()%>/vendor/animo.js/animo.js"></script>
   
   
   <!-- =============== PAGE VENDOR SCRIPTS ===============-->
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<%=request.getContextPath()%>/app/js/app.js"></script>
</body>

</html>