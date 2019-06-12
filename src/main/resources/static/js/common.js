//表单区域下拉  点击slideBtn调用  传入slide-container
function SlideFn(ele){
    return function(){
        var open = true;
        var sliderCon = ele;
        var tableMenu = ele.siblings('.table-menu');
        var slideBtn = ele.children('.toggle-slide');
        var sliderReg = ele.children('.select-region');
        var rightCon = ele.parent();
        var h = sliderCon.height();
        var maxHeight = 122;//max-height

        sliderCon.css('height',h);

        window.addEventListener('resize',function () {
            h = sliderReg.height();
            if(h > maxHeight && open==true){
                sliderCon.css('height',h+10+'px');
                slideBtn.css('bottom',0);
            }
            if(slideBtn.css('display')=='none'){
                sliderCon.css('height',h+10+'px');
                tableMenu.css('marginTop', (h+20)+'px');
            }
        });

        if(h < maxHeight){
            slideBtn.hide();
            tableMenu.css('marginTop', (h+10)+'px');
            sliderCon.css('paddingBottom',0);
        }else{
            tableMenu.css('marginTop', (maxHeight+20)+'px');
            slideBtn.show();
        }

        slideBtn.click(function(){
            if(open){
                sliderCon.css('height', maxHeight+'px');
                open = false;
            }else{
                h = sliderReg.height();
                sliderCon.css('height', (h+15)+'px');
                open = true;
            }
        })
    }
}

//layui带来的bug，计算table宽度
function ComputeWidth(ele){
    console.log(ele)
    return function(){
        var $layuiTable = ele;
        if($layuiTable.width()){
            setTimeout(function(){
//				console.log($layuiTable.width())
                $('.layui-table-view').css('width',$layuiTable.width()+1+'px');
            },10)
        }else{
            setTimeout(function(){
                computeWidth();
            },10)
        }
    }
}
//调用common.js里ComputeWidth方法
//	var $layuiTable = $('.layui-table-main table');
//	ComputeWidth($layuiTable);

//出现滚动条固定在底部
$(".table-content").mCustomScrollbar({
    theme: 'minimal-dark',
    axis: 'x',
    scrollbarPosition: 'outside',
    scrollInertia: 0,
    autoDraggerLength: true, // 是否自动调整滚动条的长度 true(default)|false
    autoHideScrollbar: false, // 是否自动隐藏滚动条
    autoExpandScrollbar: true,
    mouseWheel: { // 滚轮设置
        enable: false, // 是否可以用滚轮进行滚动
        scrollAmount: 'auto', // 内容滚动的距离， "auto"将根据情况自动调整滚动量
        preventDefault: true //当 当前容器的滚动条在两端时，是否接着滚动父级容器的滚动条 true(default) | false
    },
    advanced: { // 高级设置
        updateOnContentResize: true, // 当内容，容器或窗口发生变化时，是否自动更新滚动条
        updateOnSelectorChange: 'td', // 当特定元素的个数发生变化时，是否自动更新滚动条
        autoUpdateTimeout: 40 // 设置自动更新的时间（单位:ms），默认是60ms
    },
    callbacks: {
        // 当水平方向出现滚动条时
        onOverflowX: function() {
            console.log('水平方向出现了滚动条');
            $('#mCSB_1_scrollbar_horizontal').css({ 'position': 'fixed', 'bottom': '16px', 'left': '10px' })
        }
    }
});


var heightClient=0;
var documentHeight=0;
$("body").on("click","table a",function(){
    //弹框居中
     documentHeight = $(document).height()
    $('.qinch').css({
        height: documentHeight + "px"
    })

    heightClient = document.documentElement.clientHeight;

    var mTop = (heightClient - parseInt($('.qinch .contentAt').css('height'))) / 2;
    var mTop1 = (heightClient - parseInt($('.qinch .alBg').css('height'))) / 2;

    $('.qinch .contentAt').css({
        marginTop: mTop + "px"
    })
    $('.qinch .alBg').css({
        marginTop: mTop1 + "px"
    })
})

$(window).scroll(function() {
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollTop + heightClient <= documentHeight) {
        var mTop = (heightClient - parseInt($('.qinch .contentAt').css('height'))) / 2 + scrollTop;
        var mTop1 = (heightClient - parseInt($('.qinch .alBg').css('height'))) / 2 + scrollTop;
        $('.qinch .contentAt').css({
            marginTop: mTop + "px"
        });
        $('.qinch .alBg').css({
            marginTop: mTop1 + "px"
        });
    }
})

// 关闭弹框
$(".qinch .btn button , .alBg .top span:nth-child(2) , .contentAt .top span:nth-child(2)").click(function(){
    $(".qinch").hide();
})



