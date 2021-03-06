<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@page import="com.huacainfo.ace.weui.service.*"%>
<%@page import="com.huacainfo.ace.common.result.ListResult"%>
<%@ page import="java.util.*"%>
<%
javax.servlet.ServletContext servletContext = request.getSession()
.getServletContext();
org.springframework.web.context.WebApplicationContext webApplicationContext = org.springframework.web.context.support.WebApplicationContextUtils
.getRequiredWebApplicationContext(servletContext);

AnalysisService analysisService = (AnalysisService) webApplicationContext
.getBean("analysisService");
Map<String,Object> params=new HashMap<String,Object>();
params.put("id",request.getParameter("id"));
ListResult rst=analysisService.query(params,"loadWriting",1,1);
request.setAttribute("o", rst.getValue().get(0));
%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8" />



    <meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>常德优秀文艺作品展【${o.author} ${o.name}】</title>

<jsp:include page="../../common/common-www.jsp" />
<script>
var id='${param.id}';
</script>

</head>
<body ontouchstart style="padding:5px">


<a href="javascript:return false" class="weui-media-box weui-media-box_appmsg">
    <div class="text-line"></div>
    <div class="weui-media-box__bd">

        <h2 class="weui-media-box__title" id="name"></h2>
        <p class="weui-media-box__desc"><span id="dateOfPublication"></span>  作者：<span id="author"></span>  阅读：<span id="reading"><span></p>

    </div>
</a>

<div class="topdiv">
    本“作品库”由常德本土纯文艺微信公众号《走向》收集、整理和维护，联系邮箱：cdwyxzx@163.com
</div>

<div id="docText" class="my-gallery" data-pswp-uid="1" style="padding:12px">
</div>
<!-- Root element of PhotoSwipe. Must have class pswp. -->
<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

    <!-- Background of PhotoSwipe.
         It's a separate element as animating opacity is faster than rgba(). -->
    <div class="pswp__bg"></div>

    <!-- Slides wrapper with overflow:hidden. -->
    <div class="pswp__scroll-wrap">

        <!-- Container that holds slides.
            PhotoSwipe keeps only 3 of them in the DOM to save memory.
            Don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

            <div class="pswp__top-bar">

                <!--  Controls are self-explanatory. Order can be changed. -->

                <div class="pswp__counter"></div>

                <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>

                <button class="pswp__button pswp__button--share" title="Share"></button>

                <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>

                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

                <!-- element will get class pswp__preloader--active when preloader is running -->
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                        <div class="pswp__preloader__cut">
                            <div class="pswp__preloader__donut"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div>
            </div>

            <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
            </button>

            <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
            </button>

            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>

        </div>

    </div>

</div>
<style>
.swiper {
  height: 160px;
  object-fit: fill;
  width: 100%;
  margin:2px;
  padding:5px;
}
.topdiv{
width: 100%; padding: 5px ;
 font-size: 12px;
 color: #999;
}
</style>

<link rel="stylesheet prefetch" href="${pageContext.request.contextPath}/content/common/photoview/photoswipe.css">
<link rel="stylesheet prefetch" href="${pageContext.request.contextPath}/content/common/photoview/default-skin/default-skin.css">
<script src="${pageContext.request.contextPath}/content/common/photoview/photoswipe.js"></script>
<script src="${pageContext.request.contextPath}/content/common/photoview/photoswipe-ui-default.min.js"></script>
 <jsp:include page="../../common/footer-1-www.jsp" />
 <script src="${pageContext.request.contextPath}/content/common/lib/fastclick.js"></script>
 <script src="${pageContext.request.contextPath}/content/www/js/writing/preview.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>


</body>
</html>