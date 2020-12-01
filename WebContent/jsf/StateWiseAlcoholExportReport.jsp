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
						<h:outputText value="Report On State Wise Export Of Alcohol"
							style="TEXT-DECORATION: underline; FONT-STYLE: italic; COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: x-large;">
						</h:outputText>
					</div>
				<rich:spacer height="30px;"></rich:spacer>	
					
					
					<rich:spacer height="15px;"></rich:spacer>
					 <div align="center" class="col-md-12">
					 <div class="col-md-3"></div>
						<div align="right" class="col-md-3">
							Year:
							<h:selectOneMenu value="#{stateWiseAlcoholEportReportAction.year_id}">
								<f:selectItem itemLabel="--Select--" itemValue="" />
								<f:selectItem itemLabel="2020-21" itemValue="20_21" />
								<f:selectItem itemLabel="2019-20" itemValue="19_20" />
								
							</h:selectOneMenu>
						</div>
						
						<div align="left" class="col-md-3">
							Month:
							<h:selectOneMenu value="#{stateWiseAlcoholEportReportAction.month_id}">
								<f:selectItems value="#{stateWiseAlcoholEportReportAction.monthList}" />
								
								
							</h:selectOneMenu>
						</div>
						<div class="col-md-3"></div>
						</div>
						<div class="row"><rich:spacer height="30px;"></rich:spacer></div>
					
					<div class="row" align="center">
						<h:commandButton value="Print Report" styleClass="btn btn-success"
							action="#{stateWiseAlcoholEportReportAction.printReport}">
						</h:commandButton>

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp/MIS/pdf/#{stateWiseAlcoholEportReportAction.pdf_name}"
							target="_blank">
							<h:outputText styleClass="outputText" id="text223"
								value="View Report"
								rendered="#{stateWiseAlcoholEportReportAction.printFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>

						
						
					</div>
					
					<div class="col-md-12" align="center">
						<h:commandButton action="#{stateWiseAlcoholEportReportAction.excel}"
							styleClass="btn btn-primary" value="Generate Excel"
							rendered="true" />

						<h:outputLink styleClass="outputLinkEx"
							value="/doc/ExciseUp//MIS//Excel//#{stateWiseAlcoholEportReportAction.exlname}.xls"
							target="_blank">
							<h:outputText styleClass="outputText" value="Download Excel"
								rendered="#{stateWiseAlcoholEportReportAction.excelFlag==true}"
								style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
						</h:outputLink>
					</div>
					<rich:spacer height="50px;"></rich:spacer>
				</div>
			</div>
		</h:form>

	</f:view>
</ui:composition>
