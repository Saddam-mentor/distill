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


				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row">
					<div align="center" class="pghead">
						<h:outputText value="Consumption Of Spirit"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px;font-family:Comic Sans MS;">
						</h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>


				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>

				<div class="row col-md-12" align="center"
					style="FONT-STYLE: italic; FONT-WEIGHT: bold;">
					Between Dates :
					<rich:calendar value="#{spiritConsumptionAction.fromDate}"></rich:calendar>
					and :
					<rich:calendar value="#{spiritConsumptionAction.toDate}"></rich:calendar>
				</div>
				<div class="row " align="center">
					<rich:spacer height="40px"></rich:spacer>
				</div>
				<div align="center">
					<h:outputText value="Select Distillery Name: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu
						value="#{spiritConsumptionAction.distilleryID}"
						style="height: 28px; width : 300px;">
						<f:selectItems
							value="#{spiritConsumptionAction.distilleryList}" />
					</h:selectOneMenu>
				</div>

				

				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br /> 
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{spiritConsumptionAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{spiritConsumptionAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{spiritConsumptionAction.printFlag}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{spiritConsumptionAction.reset}">
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