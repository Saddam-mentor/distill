 <ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">

<f:view>
<h:form>
<rich:spacer   height="20px;"></rich:spacer>
<rich:spacer   height="20px;"></rich:spacer>

<h3>
<h:messages errorStyle="color:red" layout="table"
id="messages" infoStyle="color:green" />
</h3>
<div class="row" align="center">

<h:outputText value="Special Fee Report" style="FONT-SIZE: x-large; FONT-WEIGHT: bold; color: green;"> </h:outputText>
</div>

<div class="row">

<div class="col-md-4" align="right">
<h:outputText value="From Date"></h:outputText>
</div>
<div class="col-md-4">
<rich:calendar value="#{specialFeeReportAction.fromDate}"></rich:calendar>
</div>

<div class="col-md-4" >
<h:outputText value="To Date"></h:outputText>
<rich:calendar value="#{specialFeeReportAction.toDate }"></rich:calendar>
</div>
<div class="col-md-4">
</div>
</div>

<div class="row">

<div class="col-md-12" align="center">
<h:selectOneRadio value="#{specialFeeReportAction.selectRadio}" onchange="this.form.submit();">

<f:selectItem itemLabel="Bottling" itemValue="B"/>
<f:selectItem itemLabel="PermitNo" itemValue="P"/>
<f:selectItem itemLabel="HBR" itemValue="H" itemDisabled="true"/>
</h:selectOneRadio>
</div>

</div>

<div class="col-md-12">
<div class="col-md-4"></div>

<div class="col-md-2">
<h:commandButton value="Print 2019-20 Report" action="#{specialFeeReportAction.printReport }" styleClass="btn btn-primary"></h:commandButton>
<h:commandButton value="Print 2020-21 Report" action="#{specialFeeReportAction.printReport2020 }" styleClass="btn btn-primary"></h:commandButton>
<h:outputLink target="_blank" value="/doc/ExciseUp/specialfee/pdf/#{specialFeeReportAction.pdfName}"  style="COLOR: #0000ff;"> 
<h:outputText value="DownloadReport" rendered="#{specialFeeReportAction.printFlag}"/>


										  </h:outputLink>
</div>
<div class="col-md-2">
<h:commandButton value="Reset" action="#{specialFeeReportAction.reset}" styleClass="btn btn-danger"></h:commandButton>
</div>
<div class="col-md-4"></div>
</div>


</h:form>
</f:view>
</ui:composition>