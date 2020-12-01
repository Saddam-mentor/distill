<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	
	
	<f:view>
	
	<h:form>
	
	
	
	
	<br/><br/>
	
	
	<hr style="border-top:7px #D0D3D4;border-top-style:dashed;"></hr>
	<div class="row">
	<div align="center">
	<h:outputText value="OilCompanyPermitWiseReport" style="font-size: 40px;font-style: italic bold;color:black;"></h:outputText>
	</div>"
	</div>
	<hr style="border-top:7px #D0D3D4;border-top-style:dashed;"></hr>
	<div class="form-group">
	
	<div class="row" align="center">
	
	<div class="col-md-2">
	</div>
	
	<div class="col-md-2">
	<h:outputText value="FromDate" align="right"></h:outputText>
	</div>
	<div class="col-md-2">
	<rich:calendar value="#{fl41OilCompanyReportAction.fromDate}" align="left"></rich:calendar>
	</div>
	<div class="col-md-2" align="right">
	<h:outputText value="ToDate"></h:outputText>
	</div>
	
	
	<div class="col-md-2" align="left">
	<rich:calendar value="#{fl41OilCompanyReportAction.toDate}"></rich:calendar>
	</div>
<div class="col-md-2">
	
	</div>
	</div>
	</div>
	
	<div class="row" align="center">
	
	<h:commandButton value="Print" styleClass="btn btn-primary" action="#{fl41OilCompanyReportAction.printReport}"></h:commandButton>
	<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{fl41OilCompanyReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{fl41OilCompanyReportAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>
	<h:commandButton value="Reset" styleClass="btn btn-default"></h:commandButton>
	</div>
	
	
	
	</h:form>
	</f:view>
	
	
	
	
	
</ui:composition>