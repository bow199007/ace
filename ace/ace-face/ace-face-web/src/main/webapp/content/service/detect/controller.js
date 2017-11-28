var init=false;
var image_url1,image_url2;
jQuery(function($) {
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			var $title = this.options.title || '&nbsp;'
			if (("title_html" in this.options)
					&& this.options.title_html == true)
				title.html($title);
			else
				title.text($title);
		}
	}));


    $("#file-uploader-btn-one").on('click',function(e) {
        e.preventDefault();
        lookViewUpload({category:"5"});
    });
    $("#btn-query").on('click',function(e) {
            e.preventDefault();
            facesearch();

     });
    initData();
});
function initData(){
    loadSwiperData1("#swiper1");
}
function loadSwiperData1(el){
	$.ajax({
		type : "get",
		url : contextPath+'/person/findPersonList.do',
		data : {
			limit : 10,
			category:'5'
		},
		beforeSend : function(XMLHttpRequest) {
		},
		success : function(rst, textStatus) {
            var html=[];
            $(rst.rows).each(function(i,o){
                if(o.photo.indexOf("?")>0){
                                o.photo=o.photo.split("?")[0];
                            }
                html.push('<div  class="swiper-slide" data="'+fastdfs_server+o.photo+'" style="background-image:url('+fastdfs_server+o.photo+')"></div>');
            });
            $(el).html(html.join("\n\r"));
            initSwiper1();
		},
		error : function() {
			alert("HTTP错误！");
		}
	});
}

function initSwiper1(){
    var swiper1 = new Swiper('.swiper-container1', {
      effect: 'coverflow',
      grabCursor: true,
      centeredSlides: true,
      slidesPerView: 'auto',
      coverflowEffect: {
        rotate: 50,
        stretch: 0,
        depth: 100,
        modifier: 1,
        slideShadows : true,
      },
      pagination: {
        el: '.swiper-pagination',
      },
      on: {
        transitionEnd: function () {
          var image_url=$(".swiper-container1").find(".swiper-slide-active").attr("data");
          $("#box1").html('<a href="javascript:detectTwo(\''+image_url+'\')"><img src="'+image_url+'" class="photo"/></a>');
          image_url1=image_url;
          detectTwo(image_url);
        }
      }
    });
}



function loadView(o,el) {
			if(o){
			    $(el).find('#gender').html(gender[o.gender.value]);
                $(el).find('#age').html(o.age.value);
                $(el).find('#smile').html(o.smile.threshold);
                $(el).find('#glass').html(glass[o.glass.value]);
                //$(el).find('#headpose').html("抬头:"+o.headpose.pitch_angle+" 旋转:"+o.headpose.roll_angle+" 摇头:"+o.headpose.yaw_angle);
                if(o.emotion.anger>=50){
                    o.emotion="anger";
                }
                if(o.emotion.disgust>=50){
                    o.emotion="disgust";
                }
                if(o.emotion.surprise>=50){
                    o.emotion="surprise";
                }
                if(o.emotion.fear>=50){
                    o.emotion="fear";
                }
                if(o.emotion.happiness>=50){
                    o.emotion="happiness";
                }
                if(o.emotion.neutral>=50){
                    o.emotion="neutral";
                }
                if(o.emotion.sadness>=50){
                    o.emotion="sadness";
                }
                $(el).find('#emotion').html(emotion[o.emotion]);
                $(el).find('#ethnicity').html(ethnicity[o.ethnicity.value]);
                var beauty=o.beauty.female_score;
                if(o.gender.value=="Male"){
                    beauty=o.beauty.male_score;
                }
                $(el).find('#beauty').html(beauty);

                $(el).find('#health').html(o.skinstatus.health);
                $(el).find('#stain').html(o.skinstatus.stain);
                $(el).find('#acne').html(o.skinstatus.acne);
                $(el).find('#dark_circle').html(o.skinstatus.dark_circle);
                $("#rst-view").removeClass("hide");

			}
}

function lookViewUpload(multipart_params){
    var config={
        extensions : "jpg,gif,png,bmp,jpeg",
        url : contextPath + '/person/uploadFile.do',
        multipart_params : multipart_params
    };
    reset_uploader(config);
    var dialog = $("#dialog-message").removeClass('hide').dialog({
        modal : true,
        width : 750,
        title : "<div class='widget-header widget-header-small'><div class='widget-header-pd'>文件上传</div></div>",
        title_html : true,
        buttons : [{
                    html : "<i class='ace-icon fa fa-check bigger-110'></i>&nbsp; 确定",
                    "class" : "btn btn-info btn-xs",
                    click : function() {
                        $(this).dialog(
                                "close");
                    }
                }
        ]
    });
}
