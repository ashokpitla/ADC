
<%@page import="com.epfo.passbook2.model.UserData"%>
<%@page import="com.epfo.passbook2.common.util"%>
<%@page import="com.epfo.passbook2.common.fun"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/WEB-INF/views/head1.jsp" %>

<html lang="en">
    <body>
        <%@include file="/WEB-INF/views/head_common.jsp" %>

        <jsp:include page="/WEB-INF/views/nav.jsp" >
            <jsp:param name="navtoken" value="profile" />
        </jsp:include>
        <div class="container content">

            <%                //UserModel usermodel1;
                UserData userData1 = (UserData) request.getAttribute("userData");
                UserData.BasicData bd = userData1.getBasicData();
                UserData.KycData kd = userData1.getKycData();
                UserData.VeriData vd = userData1.getVeriData();


            %>


            <section>
                <div class="container py-5">                   

                    <div class="row">

                        <div class="col-lg-4">
                            <div class="card mb-4">
                                <div class="card-body cbody10">
                                    <!--                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp" alt="avatar"
                                                                             class="rounded-circle img-fluid" style="width: 50px;">-->
                                    <h5 class="my-3" style="color: brown"><%=userData1.getName()%></h5>  
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <p class="mb-0">UAN</p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=userData1.getUan()%> </p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <p class="mb-0">Mobile Number</p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=bd.getMobile()%></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-5">
                                            <p class="mb-0">Email Id</p>
                                        </div>
                                        <div class="col-sm-7">
                                            <p class="text-muted mb-0"><%=bd.getEmail()%></p>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>

                        <div class="col-lg-8">
                            <div class="card mb-4">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/basic-details.png"> Basic Details
                                </div>
                                <div class="card-body cbody10">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Full Name</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=userData1.getName()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Date of Birth</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getDob()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Gender</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getGender()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Father/Husband Name</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getFh_name()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Relation</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getRelation()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Physically Handicapped</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getPh()%></p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">International Worker</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=bd.getIw()%></p>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="card mb-4">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/kyc.png"> KYC Details
                                </div>
                                <div class="card-body cbody10">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Aadhaar</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getAadhaar()%> </p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Name as on Aadhaar</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getAadhaarName()%> </p>
                                        </div>
                                    </div>  
                                    <hr class="hr1">                                    
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">PAN</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getPan()%> </p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Name as on PAN</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getPanName()%> </p>
                                        </div>
                                    </div> 

                                    <hr class="hr1">                                    
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">BANK A/C Number</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getBank()%> </p>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Name as on BANK</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getBankName()%> </p>
                                        </div>
                                    </div> 
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">BANK IFSC</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <p class="text-muted mb-0"><%=kd.getBankIfsc()%> </p>
                                        </div>
                                    </div> 

                                </div>
                            </div>

                            <div class="card mb-4">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/bank.png"> KYC Verification Details
                                </div>
                                <div class="card-body cbody10">
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <p class="mb-0">BIO-Metric Verification Status</p>
                                        </div>
                                        <div class="col-sm-8">
                                            <%=vd.getBio()%>
                                        </div>
                                    </div>
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <p class="mb-0">Demographic Verification Status</p>
                                        </div>
                                        <div class="col-sm-8">
                                            <p class="text-muted mb-0">
                                                <%=vd.getDmo()%>
                                            </p>
                                        </div>
                                    </div>                                    
                                    <hr class="hr1">
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <p class="mb-0">OTP based Verification Status</p>
                                        </div>
                                        <div class="col-sm-8">
                                            <p class="text-muted mb-0">
                                                <%=vd.getOtp()%>
                                            </p>
                                        </div>
                                    </div> 
                                </div>
                            </div>

                            <div class="card mb-4">
                                <div class="card-header cheader">
                                    <img height="20px" src="resources/images/icons/nominee.png"> Nominee Details
                                </div>
                                <!--                                <div class="card-body cbody10">
                                                                    <div class="row">
                                                                        <div class="col-sm-3">
                                                                            <p class="mb-0">Full Name</p>
                                                                        </div>
                                                                        <div class="col-sm-9">
                                                                            <p class="text-muted mb-0">Johnatan Smith</p>
                                                                        </div>
                                                                    </div>
                                                                    <hr class="hr1">
                                                                    <div class="row">
                                                                        <div class="col-sm-3">
                                                                            <p class="mb-0">Email</p>
                                                                        </div>
                                                                        <div class="col-sm-9">
                                                                            <p class="text-muted mb-0">example@example.com</p>
                                                                        </div>
                                                                    </div>                                    
                                                                </div>-->
                            </div>

                        </div>
                    </div>
                </div>
            </section>

        </div>
        <%@include file="/WEB-INF/views/foot1_common.jsp" %>
    </body>
</html>

<%@include file="/WEB-INF/views/foot2.jsp" %>
