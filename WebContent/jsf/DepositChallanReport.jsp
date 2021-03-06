<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>


		<h:form>
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="row" align="left">
						<h:messages errorStyle="color:red" layout="table" id="messages"
							infoStyle="color:green">
						</h:messages>

					</div>
					<rich:spacer height="30px;"></rich:spacer>
					<div class="row" align="center">
						<h:outputText value="Deposit Challan Report"
							style="FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;">
						</h:outputText>
					</div>
				<rich:spacer height="30px;"></rich:spacer>	
					<div class="row" align="center">
						From Date :
						<rich:calendar value="#{depositChallanReportAction.fromDate}"></rich:calendar>
						To Date :
						<rich:calendar value="#{depositChallanReportAction.toDate}"></rich:calendar>
					</div>
					<rich:spacer height="15px;"></rich:spacer>
					<div class="row" align="center">
						<h:selectOneRadio value="#{depositChallanReportAction.radio}"
							style="width: 16%"
							valueChangeListener="#{depositChallanReportAction.radioListnr}">
							<f:selectItem itemValue="S" itemLabel="Summary" />
							<f:selectItem itemValue="D" itemLabel="Detail" />
						</h:selectOneRadio>
					</div>
					<rich:spacer height="15px;"></rich:spacer>
					<h:panelGroup rendered="false">
						<div class="row" align="center">
							Type:
							<h:selectOneMenu value="#{depositChallanReportAction.unitType}">
								<f:selectItem itemLabel="-----Select-----" itemValue="" />
								<f:selectItem itemLabel="Sugarmill" itemValue="S" />
								<f:selectItem itemLabel="Distillery" itemValue="D" />
								<f:selectItem itemLabel="Brewery" itemValue="B" />
								<f:selectItem itemLabel="BWFL" itemValue="BWFL" />
								<f:selectItem itemLabel="FL2D" itemValue="FL2D" />
								<f:selectItem itemLabel="Shop" itemValue="Shop" />
								<f:selectItem itemValue="HBR" itemLabel="HBR" />
								<f:selectItem itemLabel="Other" itemValue="Other" />
							</h:selectOneMenu>
						</div>
						<rich:spacer height="15px;"></rich:spacer>
					</h:panelGroup>
					<div class="row" align="center">
						<h:commandButton value="Print Report" styleClass="btn btn-success"
							action="#{depositChallanReportAction.printReport}">
						</h:commandButton>

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/MIS/pdf/#{depositChallanReportAction.pdf_name}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{depositChallanReportAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>

						<rich:spacer width="10px;"></rich:spacer>
						<h:commandButton value="Reset" styleClass="btn btn-warning"
							action="#{depositChallanReportAction.reset}">
						</h:commandButton>
					</div>
					<rich:spacer height="15px;"></rich:spacer>
					<div class="col-md-12" align="center">
						<h:commandButton action="#{depositChallanReportAction.excel}"
							styleClass="btn btn-primary" value="Generate Excel"
							rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{depositChallanReportAction.exlname}.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{depositChallanReportAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
					</div>
					<rich:spacer height="50px;"></rich:spacer>
				</div>
			</div>
		</h:form>

	</f:view>
</ui:composition>
