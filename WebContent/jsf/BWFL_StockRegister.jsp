<ui:composition xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:a4j="http://richfaces.org/a4j"
	xmlns:rich="http://richfaces.org/rich">
	<f:view>
		<h:form style="background-color:whitesmoke;">

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
						<h2>
							<h:outputText value="BWFL Stock Register"
								styleClass="generalHeaderOutputTable"
								style="font-weight: bold; font-size: 40px;text-decoration: underline;margin-top: 2px;font-family:Monotype Corsiva;font-size:40px;" />
						</h2>
						 
					</div>
				</div>
				 

			


				<div class="row " align="center">
					<rich:spacer height="10px"></rich:spacer>
				</div>
				<div class="row col-md-12" align="center">
					From Date :
					<rich:calendar value="#{bWFL_StockRegisterAction.fromDate}"></rich:calendar>
					To Date :
					<rich:calendar value="#{bWFL_StockRegisterAction.toDate}" ></rich:calendar>
				</div>

				<rich:spacer height="20px"></rich:spacer>




				 
				

				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{bWFL_StockRegisterAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{bWFL_StockRegisterAction.pdfname}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{bWFL_StockRegisterAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{bWFL_StockRegisterAction.reset}">
					</h:commandButton>
				</div>
				<br />

			</div>
		</h:form>
	</f:view>
</ui:composition>