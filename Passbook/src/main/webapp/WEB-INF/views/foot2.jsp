<%@page import="com.epfo.passbook2.common.util"%>
<%@page import="com.epfo.passbook2.common.fun"%>



<script src="<c:url value="<%=util.jscss.jquery_js%>" />"></script>
<script src="<c:url value="<%=util.jscss.jquery_ui_js%>" />"></script>
<script src="<c:url value="<%=util.jscss.bootstrap_js%>" />"></script>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


<script type="text/javascript">

    $(document).on("click", "#logout", function () {
        console.log(window.location);
        window.location.replace('logout');
    });


    $(document).ready(function () {
        var navbar = $('.navbar');
        var origOffsetY = navbar.offset().top;
        function scroll() {
            if ($(window).scrollTop() >= origOffsetY) {
                $('.navbar').addClass('sticky');
                $('.content').addClass('menu-padding');
            } else {
                $('.navbar').removeClass('sticky');
                $('.content').removeClass('menu-padding');
            }
        }
        document.onscroll = scroll;
    });

    $(document).ready(function () {

        var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
        var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
            return new bootstrap.Popover(popoverTriggerEl);
        });

        var navbar = $('.navbar');
        var origOffsetY = navbar.offset().top;
        if ($(window).scrollTop() >= origOffsetY) {
            $('.navbar').addClass('sticky');
            $('.content').addClass('menu-padding');
        } else {
            $('.navbar').removeClass('sticky');
            $('.content').removeClass('menu-padding');
        }
    });

</script>
