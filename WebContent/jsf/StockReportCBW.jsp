<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">
<head>
<style>
.button {
  border: 1px solid black;
  background-color: white;
  color: black;
  padding: 5px 8px;
  font-size: 13px;
  cursor: pointer;
}
 .success {
  border-color: #4CAF50;
  color: green;
  border-radius: 3px;
}

.success:hover {
  background-color: #4CAF50;
  color: white;
}

.warning {
  border-color: #ffad33;
  color: #ffad33;
  border-radius: 3px;
}

.warning:hover {
  background-color: #ffad33;
  color: white;
}

</style>
</head>
			<div class="form-group">
				<div class="row">
					<a4j:outputPanel id="msg">
						<div class="row col-md-12 wow shake" style="margin-top: 10px;">
							<h:messages errorStyle="color:red"
								style="font-size: 14px;font-weight: bold"
								styleClass="generalExciseStyle" layout="table"
								infoStyle="color:green" />
						</div>
					</a4j:outputPanel>
				</div>

				<div class="row " align="center">
					<div align="center" width="100%" class="pghead">
					<h:inputHidden
									value="#{stockReportCBWAction.hidden}"></h:inputHidden>
						<h2>
							<h:outputText value=" Stock Report For CBW "
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
					</div>
					<div>
					<h:outputLabel
									value="#{stockReportCBWAction.applicant_name}"
									style="COLOR: #000000; FONT-SIZE: x-large;"></h:outputLabel>
					</div>
					
					
				</div>
				
				

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				
				<div align="center">
				 <h:selectOneRadio value="#{stockReportCBWAction.radio}"
				 onchange="this.form.submit();"
				 valueChangeListener="#{stockReportCBWAction.listener}">
				  <f:selectItem itemValue="B" itemLabel="Brand Wise"/>
				  <f:selectItem itemValue="D" itemLabel="Detailed"/>
				  
				 </h:selectOneRadio>
				</div>
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
            <h:panelGroup rendered="#{stockReportCBWAction.radio eq 'B'}">
				<div class="col-md-12" align="center">
					<div class="col-md-5" align="right">
						<h:outputText value="Select Brand Name:"
							style="FONT-SIZE: small; FONT-WEIGHT: bold;"></h:outputText>
					</div>
					<div class="col-md-3" align="left">
						<h:selectOneMenu
							value="#{stockReportCBWAction.packId}"
							style="width: 250px; height: 28px;" >
							<f:selectItems
								value="#{stockReportCBWAction.brandList}" />
						</h:selectOneMenu>
					</div>
				</div>

				<div class="row">
					<rich:spacer height="20px"></rich:spacer>
				</div>

				<div class="col-md=12" align="center">
					<div class="col-md-3"></div>
					<div class="col-md-1" align="right">From:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{stockReportCBWAction.fromDate}">
						</rich:calendar>
					</div>
					<div class="col-md-1" align="right">To:</div>
					<div class="col-md-2" align="left">
						<rich:calendar value="#{stockReportCBWAction.toDate}">
						</rich:calendar>
					</div>
					<div class="col-md-3"></div>
				</div>
                 </h:panelGroup>
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> <br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success btn-sm"
						action="#{stockReportCBWAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx" 
						value="/doc/ExciseUp/MIS/pdf/#{stockReportCBWAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{stockReportCBWAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning btn-sm"
						action="#{stockReportCBWAction.reset}">
					</h:commandButton>
				</div>
				<br />
				
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>