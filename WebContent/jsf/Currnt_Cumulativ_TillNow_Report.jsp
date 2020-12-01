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
					<div align="center">
						<h:outputText value="Cumulative Sale and Duty"
							style="COLOR: #0000a0; FONT-WEIGHT: bold; FONT-SIZE: 35px; font-family:Comic Sans MS;"></h:outputText>
					</div>
				</div>
				<hr style="border-top: 7px #D0D3D4; border-top-style: dashed;"></hr>
				<div class="row " align="center">
					<rich:spacer height="20px"></rich:spacer>
				</div>
				<div align="center">
					<h:outputText value="Select Year: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu
						value="#{currnt_Cumulativ_TillNow_ReportAction.year}"
						style="height: 25px; width : 150px;">
						<f:selectItems
							value="#{currnt_Cumulativ_TillNow_ReportAction.getAll_List}"/>
					</h:selectOneMenu>
				</div>
				<rich:spacer height="20px"></rich:spacer>

				<div align="center">
					<h:outputText value="Select Month: "
						style="FONT-SIZE:small; FONT-WEIGHT: bold;"></h:outputText>

					<h:selectOneMenu
						value="#{currnt_Cumulativ_TillNow_ReportAction.monthID}"
						style="height: 25px; width : 150px;">
						<f:selectItems
							value="#{currnt_Cumulativ_TillNow_ReportAction.monthList}" />
					</h:selectOneMenu>
				</div>
				<div class="row " align="center">
					<rich:spacer height="30px"></rich:spacer>
				</div>
				<br />
				<div class="row" align="center">
					<h:commandButton value="Print Report" styleClass="btn btn-success"
						action="#{currnt_Cumulativ_TillNow_ReportAction.print}">
					</h:commandButton>

					<h:outputLink styleClass="outputLinkEx"
						value="/doc/ExciseUp/MIS/pdf/#{currnt_Cumulativ_TillNow_ReportAction.pdfName}"
						target="_blank">
						<h:outputText styleClass="outputText" id="text223"
							value="View Report"
							rendered="#{currnt_Cumulativ_TillNow_ReportAction.printFlag==true}"
							style="color: blue; font-family: serif; font-size: 12pt"></h:outputText>
					</h:outputLink><rich:spacer height="20px"></rich:spacer>

					<rich:spacer width="10px;"></rich:spacer>
					<h:commandButton value="Reset" styleClass="btn btn-warning"
						action="#{currnt_Cumulativ_TillNow_ReportAction.reset}">
					</h:commandButton>
				</div>


				<div class="row " align="center">
					
				</div>
				<br />
			</div>
		</h:form>
	</f:view>
</ui:composition>