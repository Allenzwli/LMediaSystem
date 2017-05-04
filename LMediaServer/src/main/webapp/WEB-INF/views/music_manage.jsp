<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

   <!-- =============== PAGE VENDOR STYLES ===============-->
   <!-- =============== BOOTSTRAP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/bootstrap.css" id="bscss">
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/datatable/css/bootstrap.min.css">
   <!-- =============== APP STYLES ===============-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/app/css/app.css" id="maincss">

   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/datatable/css/bootstrap-responsive.min.css" media="screen">

   <!-- DataTables CSS start-->
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/datatable/css/dataTables.bootstrap.css">
   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/datatable/css/dataTables.fontAwesome.css">
   <!-- DataTables CSS end-->

   <link rel="stylesheet" href="<%=request.getContextPath()%>/vendor/datatable/css/music-manage.css">
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
                     <a href="http://www.baidu.com">
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
                     <span data-localize="sidebar.heading.HEADER">欢迎 李钊伟</span>
                  </li>
                  <li class="">
                     <a href="<%=request.getContextPath()%>/main" title="Single View">
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
                        <li class="active">
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
                        <li class="">
                           <a href="<%=request.getContextPath()%>/admins/manage" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">管理员账号</span>
                           </a>
                        </li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/admins/add" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">新增管理员</span>
                           </a>
                        </li>
                     </ul>
                  </li>
                  <li class=" ">
                     <a href="#menuid3" title="Menu" data-toggle="collapse">
                        <em class="fa fa-file-text"></em>
                        <span data-localize="sidebar.nav.menu.MENU">日志统计</span>
                     </a>
                     <ul id="menuid3" class="nav sidebar-subnav collapse">
                        <li class="sidebar-subnav-header">日志统计</li>
                        <li class=" ">
                           <a href="<%=request.getContextPath()%>/logs/manage" title="Sub Menu">
                              <span data-localize="sidebar.nav.menu.SUBMENU">查看日志</span>
                           </a>
                        </li>
                     </ul>
                  </li>

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
         <div class="content-wrapper" style="background-color: white">
            <h3>音乐
               <small>音频库管理</small>
            </h3>
            <!-- START panel-->
            <div class="container-fluid">
               <div class="row-fluid">
                  <div class="span12" id="content">
                     <div class="row-fluid">
                        <div class="span12">
                           <div class="btn-toolbar">
                              <div class="pull-right">
                                 <div class="input-append">
                                    <input type="text" placeholder="模糊查询" id="fuzzy-search">
                                    <div class="btn-group">
                                       <button type="button" class="btn" id="btn-simple-search"><i class="fa fa-search"></i></button>
                                       <button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
                                          <i class="fa fa-angle-double-down"></i>
                                       </button>
                                    </div>
                                 </div>
                              </div>
                              <!-- <a type="button" class="btn btn-primary" id="btn-add" href="addBook.action"><i class="fa fa-plus"></i> 添加</a> -->
                              <button type="button" class="btn btn-danger" id="btn-del"><i class="fa fa-remove"></i> 批量删除</button>
                           </div>
                        </div>
                     </div>
                     <div class="row-fluid" style="display:none;" id="div-advanced-search">
                        <form class="form-inline well">
                           <span>文件名:</span>
                           <input type="text" class="input-medium" placeholder="文件名" id="fileName-search">
                           <span>歌曲名:</span>
                           <input type="text" class="input-medium" placeholder="歌曲名" id="songName-search">
                           <span>歌手:</span>
                           <input type="text" class="input-medium" placeholder="歌手" id="artist-search">
                           <span>专辑:</span>
                           <input type="text" class="input-medium" placeholder="专辑" id="album-search">
                           <span>年份:</span>
                           <input type="text" class="input-medium" placeholder="年份" id="year-search">
                           <button type="button" class="btn" id="btn-advanced-search"><i class="fa fa-search"></i> 查询</button>
                        </form>
                     </div>

                     <div class="block info-block" id="user-view">
                        <div class="navbar navbar-inner block-header">
                           <div class="block-title">歌曲详情</div>
                           <div class="header-buttons">
                              <button type="button" class="btn btn-primary" id="btn-view-edit">修改</button>
                           </div>
                        </div>
                        <div class="block-content info-content clearfix">
                           <div class="row-fluid">
                              <div class="span5">
                                 <label class="prop-name">歌曲名:</label>
                                 <div class="prop-value" id="songName-view"></div>
                              </div>
                              <div class="span3">
                                 <label class="prop-name">歌手:</label>
                                 <div class="prop-value" id="aritst-view"></div>
                              </div>
                              <div class="span3">
                                 <label class="prop-name">专辑:</label>
                                 <div class="prop-value" id="album-view"></div>
                              </div>
                           </div>

                           <div class="row-fluid">
                              <div class="span2">
                                 <label class="prop-name">年份:</label>
                                 <div class="prop-value" id="year-view"></div>
                              </div>
                              <div class="span3">
                                 <label class="prop-name">时长:</label>
                                 <div class="prop-value" id="duration-view"></div>
                              </div>

                              <div class="span3">
                                 <label class="prop-name">添加时间:</label>
                                 <div class="prop-value" id="uploadTime-view"></div>
                              </div>
                              <div class="span3">
                                 <label class="prop-name">文件大小:</label>
                                 <div class="prop-value" id="fileSize-view"></div>
                              </div>
                           </div>

                           <div class="row-fluid">
                              <div class="span5">
                                 <label class="prop-name">文件名:</label>
                                 <div class="prop-value" id="fileName-view"></div>
                              </div>

                              <div class="span5">
                                 <label class="prop-name">文件Url:</label>
                                 <div class="prop-value" id="fileUrl-view"></div>
                              </div>
                           </div>

                           <div class="row-fluid">
                              <div class="span8">
                                 <label class="prop-name">专辑封面:</label>
                                 <div class="prop-value" id="pic_url-view"></div>
                              </div>
                           </div>
                        </div>


                     </div>

                     <div class="block info-block" id="user-edit" style="display:none;">
                        <div class="navbar navbar-inner block-header">
                           <div class="block-title">修改歌曲信息:<span id="title-edit"></span></div>
                           <div class="header-buttons">
                              <button type="button" class="btn btn-primary" id="btn-save-edit">保存更改</button>
                              <button type="button" class="btn btn-cancel">取消</button>
                           </div>
                        </div>
                        <div class="block-content info-content clearfix">
                           <form id="form-edit">
                              <div class="control-group" style="display:none">
                                 <label class="control-label" for="id-edit"><span
                                         class="red-asterisk">*</span>ID:</label>
                                 <div class="controls">
                                    <input type="text" id="id-edit" name="id-edit">
                                 </div>
                              </div>
                              <div class="control-group">
                                 <label class="control-label" for="songName-edit"><span
                                         class="red-asterisk">*</span>歌曲名:</label>
                                 <div class="controls">
                                    <input type="text" id="songName-edit" name="songName-edit">
                                 </div>
                              </div>
                              <div class="control-group">
                                 <label class="control-label" for="artist-edit">歌手:</label>
                                 <div class="controls">
                                    <input type="text" id="artist-edit" name="artist-edit">
                                 </div>
                              </div>
                              <div class="control-group">
                                 <label class="control-label" for="album-edit">专辑:</label>
                                 <div class="controls">
                                    <input type="text" id="album-edit" name="album-edit">
                                 </div>
                              </div>
                              <div class="control-group">
                                 <label class="control-label" for="year-edit">年份</label>
                                 <div class="controls">
                                    <input type="text" id="year-edit" name="year-edit">
                                 </div>
                              </div>
                              <div class="control-group">
                                 <label class="control-label" for="pic_url-edit">封面URL:</label>
                                 <div class="controls">
                                    <input type="text" id="pic_url-edit" name="pic_url-edit">
                                    <input type="file" id="pic_file-edit" name="pic_file-edit" onchange="checkedFile()">
                                 </div>

                              </div>
                           </form>
                        </div>
                     </div>

                     <div class="row-fluid">
                        <div class="span12" id="div-table-container">
                           <table class="table table-striped table-hover" id="table-user">
                              <thead>
                              <tr>
                                 <th>
                                    <input type="checkbox" name="cb-check-all">
                                 </th>
                                 <th>歌曲名</th>
                                 <th>歌手</th>
                                 <th>专辑</th>
                                 <th>年份</th>
                                 <th>文件名</th>
                                 <th>时长</th>
                                 <th>文件大小</th>
                                 <th>文件URL</th>
                                 <th>封面</th>
                                 <th>添加时间</th>
                              </tr>
                              </thead>
                              <tbody>
                              </tbody>
                           </table>

                        </div>
                     </div>
                  </div>
               </div>
            </div>
            <!-- END panel-->
         </div>
      </section>
      <!-- Page footer-->
      <footer>

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
   <!-- Datatable-->

   <script src="<%=request.getContextPath()%>/vendor/datatable/js/json2.js"></script>
   <!-- JQueryFileUpload -->
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/ajaxfileupload.js"></script>
   <!-- SpinJS-->
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/jquery.spin.merge.js"></script>
   <!-- lhgdialog -->
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/lhgdialog.js?skin=bootstrap2"></script>
   <!-- DataTables JS-->
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/jquery.dataTables.js"></script>
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/dataTables.bootstrap.js"></script>
   <!-- DataTables JS end-->
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/constant.js"></script>
   <script src="<%=request.getContextPath()%>/vendor/datatable/js/music-manage.js"></script>
   
   <!-- =============== PAGE VENDOR SCRIPTS ===============-->
   <!-- =============== APP SCRIPTS ===============-->
   <script src="<%=request.getContextPath()%>/app/js/app.js"></script>

   <script type="text/javascript">
       function checkedFile()
       {
           var picPath = document.getElementById("pic_file-edit").value;
           var type = picPath.substring(picPath.lastIndexOf(".") + 1, picPath.length).toLowerCase();
           if (type != "jpg" && type != "bmp" && type != "png") {
               alert("请上传正确的图片格式");
               $("#pic_file-edit").val("");
               $("#pic_url-edit").attr("disabled",false);
               //$("#pic_url-edit").val("");
               return false;
           }
           $("#pic_url-edit").attr("disabled",true);
           //$("#pic_url-edit").val("");
           return true;
       }
   </script>
</body>

</html>